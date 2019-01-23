/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  org.apache.commons.io.IOUtils
 */
package de.sbe.ldc.persistence.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.sbe.ldc.domain.AbstractBean;
import de.sbe.ldc.domain.Server;
import de.sbe.ldc.domain.Session;
import de.sbe.ldc.persistence.morpher.Encoder;
import de.sbe.ldc.persistence.morpher.deserializer.ServerOptionsDeserializer;
import de.sbe.ldc.persistence.net.LDCX509TrustManager;
import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.Processor;
import de.sbe.ldc.persistence.protocol.ProcessorAdapter;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.protocol.Response;
import de.sbe.ldc.persistence.protocol.ServerOptions;
import de.sbe.utils.NetUtils;
import de.sbe.utils.Settings;
import de.sbe.utils.StringUtils;
import de.sbe.utils.logging.LogUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.commons.io.IOUtils;

public abstract class AbstractConnection
extends AbstractBean {
    private static final Pattern MULTILINE_DOT = Pattern.compile("^\\.\\..*$");
    private static final Pattern MULTILINE_END = Pattern.compile("^\\.$");
    public static final String PROPERTYNAME_IDLE = "idle";
    public static final String PROPERTYNAME_RECEIVE = "receive";
    public static final String PROPERTYNAME_TRANSMIT = "transmit";
    private static final long serialVersionUID = 7528602981603085748L;
    private BufferedReader reader;
    private Server server;
    ServerOptions serverOptions;
    private Socket socket;
    private final int timeout;
    private boolean valid = true;
    private PrintWriter writer;

    public AbstractConnection(int _timeout) {
        this.timeout = _timeout;
    }

    private void connect() throws IOException {
        if (!this.valid || this.server == null) {
            return;
        }
        this.socket = new Socket();
        this.socket.connect(new InetSocketAddress(this.server.getIp(), Settings.getInt("net.server_port")), this.timeout);
        this.reconfigure(this.socket);
        this.dumpServerGreeting();
        Request helo = new Request(Command.HELO);
        helo.putEncodedData("host", this.server.getIp());
        helo.putEncodedData("lang", Locale.getDefault().getLanguage());
        helo.putEncodedData("type", "console");
        helo.putEncodedData("version", Settings.getString("version"));
        this.send(helo, new ProcessorAdapter(){

            @Override
            public void processLine(String _line) {
                AbstractConnection.this.serverOptions = (ServerOptions)new GsonBuilder().registerTypeAdapter(ServerOptions.class, (Object)new ServerOptionsDeserializer()).create().fromJson(_line, ServerOptions.class);
            }
        });
        Session.getInstance().setServerProfilesAllowed(this.serverOptions.isServerProfilesAllowed());
        if (this.serverOptions.isSSLSupported() && Request.RequestStatus.OK.equals((Object)this.send(new Request(Command.STARTTLS)).getRequest().getStatus())) {
            try {
                this.startTLS();
            }
            catch (GeneralSecurityException _gse) {
                throw new IOException(_gse);
            }
        }
        this.doConnect();
    }

    private void disconnect() {
        NetUtils.closeQuietly(this.socket);
        IOUtils.closeQuietly((Reader)this.reader);
        IOUtils.closeQuietly((Writer)this.writer);
        this.reader = null;
        this.writer = null;
        this.socket = null;
    }

    protected void doConnect() {
    }

    protected void doShutdown() {
    }

    private void dumpServerGreeting() throws IOException {
        String line = this.reader.readLine();
        System.out.println("Greeting: " + line);
        if (line.isEmpty()) {
            return;
        }
        if (line.endsWith(":")) {
            while ((line = this.reader.readLine()) != null && !MULTILINE_END.matcher(line).matches()) {
                System.out.println(line);
            }
        }
    }

    public final BufferedReader getReader() {
        return this.reader;
    }

    public Server getServer() {
        return this.server;
    }

    public final PrintWriter getWriter() {
        return this.writer;
    }

    public final boolean isValid() {
        return this.valid;
    }

    private void receive(Response _response) throws IOException {
        this.firePropertyChange(PROPERTYNAME_RECEIVE, false, true);
        String line = this.reader.readLine();
        if (StringUtils.isEmptyString(line)) {
            System.out.println("logging.persistence.connection.invalid_response");
        }
        _response.processHeader(line);
        if (line.endsWith(":")) {
            do {
                if (StringUtils.isEmptyString(line = this.reader.readLine())) {
                    System.out.println("logging.persistence.connection.invalid_response");
                }
                if (MULTILINE_END.matcher(line).matches()) break;
                if (MULTILINE_DOT.matcher(line).matches()) {
                    line = line.substring(1);
                }
                System.out.println(line);
                _response.getProcessor().processLine(line);
            } while (true);
        }
    }

    private void reconfigure(Socket _socket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
        this.writer = new PrintWriter(new OutputStreamWriter(_socket.getOutputStream()), true);
    }

    public final void reconnect(Server _server) throws IOException {
        this.disconnect();
        this.server = _server;
        this.connect();
    }

    public final Response send(Request _request) throws IOException {
        return this.send(_request, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final Response send(Request _request, Processor _processor) throws IOException {
        Response response;
        response = new Response(_request, _processor);
        this.getLock().lock();
        try {
            Encoder encoder;
            response.getProcessor().setUp();
            StringBuilder builder = new StringBuilder();
            builder.append(_request.getCommand().getTemplate());
            for (String option : _request.getPlainData()) {
                builder.append(" ");
                builder.append(option);
            }
            Encoder encoder2 = encoder = _request.getEncoder() == null ? new Encoder() : _request.getEncoder();
            if (!_request.getEncodedData().isEmpty()) {
                builder.append(" ");
                builder.append("data");
                builder.append("=");
                builder.append(encoder.encode(_request.getEncodedData()));
            }
            if (_request.getDataObject() != null) {
                builder.append(" ");
                builder.append("data");
                builder.append("=");
                builder.append(encoder.encode(_request.getDataObject()));
            }
            System.out.println(builder.toString());
            this.writer.println(builder.toString());
            System.out.println(builder.toString());
            this.receive(response);
            if (!response.isFailed() && _request.getIs() != null) {
                byte[] buffer = new byte[65536];
                int l = 0;
                while ((l = _request.getIs().read(buffer)) > 0) {
                    this.socket.getOutputStream().write(buffer, 0, l);
                }
                this.socket.getOutputStream().flush();
                this.receive(response);
            }
        }
        finally {
            this.getLock().unlock();
            response.getProcessor().tearDown();
        }
        return response;
    }

    public final void shutdown() {
        if (!this.valid) {
            return;
        }
        this.doShutdown();
        this.valid = false;
        this.disconnect();
    }

    private void startTLS() throws GeneralSecurityException, IOException {
        KeyStore keystore = KeyStore.getInstance("JKS");
        keystore.load(null, null);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keystore, new char[0]);
        SSLContext sslc = SSLContext.getInstance("SSLv3");
        sslc.init(kmf.getKeyManagers(), new TrustManager[]{new LDCX509TrustManager(keystore)}, new SecureRandom());
        this.socket = sslc.getSocketFactory().createSocket(this.socket, this.server.getIp(), Settings.getInt("net.server_port"), true);
        ((SSLSocket)this.socket).setEnabledProtocols(new String[]{"SSLv3", "TLSv1"});
        ((SSLSocket)this.socket).setUseClientMode(true);
        ((SSLSocket)this.socket).startHandshake();
        this.reconfigure(this.socket);
    }

}

