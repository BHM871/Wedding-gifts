package com.example.wedding_gifts.infra.pix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentGatewayException;

@Component
public class PixSslConfig {

    @Value("${server.ssl.alias}")
    private String alias;

    @Value("${server.ssl.enabled-protocols}")
    private String sslProtocol;

    @Value("${server.ssl.key-store-type}")
    private String keyStoreType;
    @Value("${api.ssl.trust-store-type}")
    private String trustStoreType;
    @Value("${api.ssl.cert-type}")
    private String certType;

    @Value("${server.ssl.key-store-password}")
    private String storePass;

    @Value("${server.ssl.key-store}")
    private String keyStorePath;
    @Value("${api.ssl.trust-store}")
    private String trustStoreAllPath;
    
    @Value("${api.ssl.cert-oauth}")
    private String certOauthPath;
    @Value("${api.ssl.cert-api-pix}")
    private String certApiPixPath;

    public SSLSocketFactory createSslSocketFactory() throws Exception {
        InputStream keyStoreIs = null;
        InputStream trustStoreIs = null;
        KeyStore keyStore;
        KeyStore trustStore;

        try{
            keyStoreIs = new FileInputStream(new File(keyStorePath));
            trustStoreIs = new FileInputStream(new File(trustStoreAllPath));

            keyStore = KeyStore.getInstance(keyStoreType);
            trustStore = KeyStore.getInstance(trustStoreType);

            keyStore.load(keyStoreIs, storePass.toCharArray());
            trustStore.load(trustStoreIs, storePass.toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keyStore, storePass.toCharArray());
            KeyManager[] keyManagers = kmf.getKeyManagers();

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            TrustManager[] trustManagers = tmf.getTrustManagers();

            SSLContext sslContext = SSLContext.getInstance(sslProtocol);
            sslContext.init(keyManagers, trustManagers, null);

            return sslContext.getSocketFactory();
        } catch (FileNotFoundException e){
            throw new PaymentGatewayException(e.getMessage(), e);
        }catch (Exception e) {
            throw new PaymentGatewayException("Some error in generated SSL Socket for Gateway request", e);
        } finally {
            if (keyStoreIs != null) keyStoreIs.close();
            if (trustStoreIs != null) trustStoreIs.close();
        }
    }

    public TrustManager getOauthTrust() throws Exception {
        try {
            KeyStore store = KeyStore.getInstance(trustStoreType);
            store.load(null, storePass.toCharArray());

            store.setCertificateEntry(alias, getCertificate(certApiPixPath));

            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(store);

            return tmf.getTrustManagers()[0];
        } catch (FileNotFoundException e){
            throw new PaymentGatewayException(e.getMessage(), e);
        } catch (Exception e) {
            throw new PaymentGatewayException("Some error in searcher KeyStore or store", e);
        }
    }

    private Certificate getCertificate(String path) throws Exception {
        try {
            InputStream certIs = new FileInputStream(new File(path));

            return CertificateFactory.getInstance(certType).generateCertificate(certIs);
        } catch (FileNotFoundException e) {
            throw new PaymentGatewayException(e.getMessage(), e);
        } catch (CertificateException e) {
            throw new PaymentGatewayException(e.getMessage(), e);
        } catch (Exception e) {
            throw new PaymentGatewayException("Failure in load certificate " + path.split("/")[path.split("/").length-1], e);
        }
    }

}
