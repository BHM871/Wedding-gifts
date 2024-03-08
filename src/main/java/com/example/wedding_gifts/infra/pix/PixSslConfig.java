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
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.stereotype.Component;

import com.example.wedding_gifts.core.domain.exceptions.common.MyException;
import com.example.wedding_gifts.core.domain.exceptions.payment.PaymentGatewayException;

@Component
public class PixSslConfig {

    private String alias;

    private String sslProtocol;

    private String keyStoreType;
    private String trustStoreType;
    private String certType;

    private String storePass;

    private String keyStorePath;
    private String trustStoreAllPath;
    
    private String certApiPixPath;

    public SSLContext createSslContext() throws Exception {
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

            return sslContext;

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
        }  catch (MyException e){
            throw e;
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
