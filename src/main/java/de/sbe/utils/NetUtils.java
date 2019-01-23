/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.utils;

import de.sbe.utils.logging.LogUtils;
import java.io.Closeable;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class NetUtils {
    private static final Logger LOGGER = LogUtils.getLogger(NetUtils.class);

    public static int byteArrayToInt(byte[] _bytes) throws NumberFormatException {
        if (_bytes == null || _bytes.length != 4) {
            throw new NumberFormatException();
        }
        return _bytes[0] << 24 | (_bytes[1] & 255) << 16 | (_bytes[2] & 255) << 8 | _bytes[3] & 255;
    }

    public static void closeQuietly(Closeable _closeable) {
        if (_closeable == null) {
            return;
        }
        try {
            _closeable.close();
        }
        catch (IOException _ioe) {
            LOGGER.log(Level.WARNING, "", _ioe);
        }
    }

    public static void closeQuietly(DatagramSocket _socket) {
        if (_socket == null) {
            return;
        }
        try {
            _socket.close();
        }
        catch (Throwable _t) {
            LOGGER.log(Level.WARNING, "", _t);
        }
    }

    public static void closeQuietly(Socket _socket) {
        if (_socket == null) {
            return;
        }
        try {
            _socket.close();
        }
        catch (Throwable _t) {
            LOGGER.log(Level.WARNING, "", _t);
        }
    }

    public static String getHostName() {
        String hostName = null;
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException _uhe) {
            LOGGER.log(Level.SEVERE, "", _uhe);
        }
        return hostName;
    }

    public static List<InetAddress> getLocalInetAddresses() {
        Enumeration<NetworkInterface> nis;
        try {
            nis = NetworkInterface.getNetworkInterfaces();
        }
        catch (SocketException _se) {
            LOGGER.log(Level.WARNING, "", _se);
            return Collections.emptyList();
        }
        HashSet<InetAddress> list = new HashSet<InetAddress>();
        while (nis.hasMoreElements()) {
            Enumeration<InetAddress> ias = nis.nextElement().getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress address = ias.nextElement();
                if (address.isLinkLocalAddress() || address.isLoopbackAddress()) continue;
                list.add(address);
            }
        }
        return Collections.list(Collections.enumeration(list));
    }

    public static List<String> getLocalIPs() {
        List<InetAddress> addresses = NetUtils.getLocalInetAddresses();
        if (addresses == null || addresses.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList<String> ips = new ArrayList<String>(addresses.size());
        for (InetAddress address : addresses) {
            ips.add(address.getHostAddress());
        }
        return ips;
    }

    public static byte[] intToByteArray(int _integer) {
        return new byte[]{(byte)(_integer >> 24), (byte)(_integer >> 16), (byte)(_integer >> 8), (byte)_integer};
    }

    private NetUtils() {
    }
}

