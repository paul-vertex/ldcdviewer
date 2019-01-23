/*
 * Decompiled with CFR 0.139.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.jgoodies.uif.application.Application
 *  com.jgoodies.uif.application.ApplicationContext
 */
package de.sbe.ldc.persistence.net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.sbe.ldc.persistence.protocol.Command;
import de.sbe.ldc.persistence.protocol.JsonProcessorAdapter;
import de.sbe.ldc.persistence.protocol.Processor;
import de.sbe.ldc.persistence.protocol.Request;
import de.sbe.ldc.persistence.protocol.Response;
import de.sbe.ldc.security.Auth;
import de.sbe.utils.StringUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

public class Authenticator {

    public void authenticate(List<? extends AbstractConnection> _connections) throws IOException {
        System.out.println("logging.auth.start");
        Auth.getInstance().resetFailure();
        Iterator<? extends AbstractConnection> it = _connections.iterator();
        AbstractConnection master = it.next();
        boolean authenticated = false;
        boolean authorized = false;
        if (!StringUtils.isEmptyString(Auth.getInstance().getSession())) {
            boolean bl = authenticated = !this.authenticateSession(master, Auth.getInstance().getSession()).isFailed();
            if (authenticated) {
                authorized = this.checkAuthorization();
            }
        }
        if (!(authenticated && authorized || StringUtils.isEmptyString(Auth.getInstance().getUser()))) {
            boolean bl = authenticated = !this.authenticateLogin(master, Auth.getInstance().getUser(), Auth.getInstance().getPassword()).isFailed();
            if (authenticated) {
                authorized = this.checkAuthorization();
            } else {
                Auth.getInstance().incrementFailure();
                System.out.println("logging.auth.authentication.failure");
            }
        }
        if (!(authenticated && authorized || !StringUtils.isEmptyString(Auth.getInstance().getUser()))) {
            boolean bl = authenticated = !this.authenticateAuto(master).isFailed();
            if (authenticated) {
                authorized = this.checkAuthorization();
            }
        }
        if (!authenticated || !authorized || WorkerValidator.isInvalid) {
            while (!(authenticated && authorized && !WorkerValidator.isInvalid || Auth.getInstance().isFailureExceeded())) {
                boolean bl = authenticated = !this.authenticateLogin(master, Auth.getInstance().getUser(), Auth.getInstance().getPassword()).isFailed();
                if (authenticated) {
                    authorized = this.checkAuthorization();
                    if (authorized) continue;
                    Auth.getInstance().incrementFailure();
                    continue;
                }
                Auth.getInstance().incrementFailure();
                System.out.println("logging.auth.authentication.failure");
            }
            if (!authenticated || !authorized) {
                System.out.println("logging.auth.login.failure.exceeded");
            }
        }
        if (authenticated && authorized) {
            Auth.getInstance().setSession(master.send(new Request(Command.SESSIONKEY)).getRequest().getInfo());
            while (it.hasNext()) {
                Response response = this.authenticateSession(it.next(), Auth.getInstance().getSession());
                authenticated = !response.isFailed();
                if (authenticated) continue;
                System.out.println("logging.auth.session.failure s=" + Auth.getInstance().getSession() + ", i=" + response.getRequest().getInfo());
            }
        }
    }

    Response authenticateAuto(AbstractConnection _connection) throws IOException {
        return this.send(_connection, new Request(Command.AUTH_AUTO));
    }

    Response authenticateLogin(AbstractConnection _connection, String _username, char[] _password) throws IOException {
        Request request = new Request(Command.AUTH_LOGIN);
        request.putEncodedData("user", _username);
        request.putEncodedData("password", _password != null ? new String(_password) : "");
        return this.send(_connection, request);
    }

    Response authenticateSession(AbstractConnection _connection, String _sessionKey) throws IOException {
        Request request = new Request(Command.AUTH_SESSION);
        request.putPlainData(_sessionKey);
        return this.send(_connection, request);
    }

    private boolean checkAuthorization() {
        return true;
    }

    private Response send(AbstractConnection _connection, Request _request) throws IOException {
        return _connection.send(_request, new AuthProcessor());
    }

    private static class AuthProcessor
    extends JsonProcessorAdapter {
        AuthProcessor() {
        }

        @Override
        public void processJson(JsonObject _object) {
            JsonElement user;
            JsonElement privs = _object.get("privs");
            if (privs != null) {
                Auth.getInstance().setPrivs(privs.getAsJsonObject());
            }
            if ((user = _object.get("user")) != null) {
                Auth.getInstance().setUser(user.getAsString());
            }
        }
    }

}

