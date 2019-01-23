/*
 * Decompiled with CFR 0.139.
 */
package de.sbe.ldc.persistence.net;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

class LDCX509TrustManager
implements X509TrustManager {
    private X509TrustManager defaultTrustManager;
    private final KeyStore ks;

    LDCX509TrustManager(KeyStore _ks) throws NoSuchAlgorithmException, KeyStoreException {
        this.ks = _ks;
        this.findDefaultTrusManager();
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
        try {
            this.defaultTrustManager.checkClientTrusted(chain, authType);
        }
        catch (CertificateException ce) {
            ce.printStackTrace();
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }

    private void findDefaultTrusManager() throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunPKIX");
        tmf.init(this.ks);
        for (TrustManager tm : tmf.getTrustManagers()) {
            if (!(tm instanceof X509TrustManager)) continue;
            this.defaultTrustManager = (X509TrustManager)tm;
            return;
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return this.defaultTrustManager.getAcceptedIssuers();
    }
}

