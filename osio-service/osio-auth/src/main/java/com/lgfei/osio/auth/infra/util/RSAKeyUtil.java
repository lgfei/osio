package com.lgfei.osio.auth.infra.util;

import com.lgfei.osio.auth.infra.exception.RSAKeyException;
import com.nimbusds.jose.jwk.RSAKey;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.UUID;

public final class RSAKeyUtil {

    private RSAKeyUtil(){
    }

    public static RSAKey generateRsa() {
        RSAPublicKey publicKey = null;
        RSAPrivateKey privateKey = null;
        try {
            publicKey = (RSAPublicKey) readPublicKey();
            privateKey = (RSAPrivateKey) readPrivateKey();
        } catch (Exception e) {
            throw new RSAKeyException(e);
        }
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                //.keyID("my_kid")
                .build();
        return rsaKey;
    }

    private static PublicKey readPublicKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("rsa/public_key.pem").toURI()));
        String publicKeyPEM = new String(keyBytes);
        publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "");
        publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");

        byte[] decoded = Base64.getDecoder().decode(publicKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        return keyFactory.generatePublic(keySpec);
    }

    private static PrivateKey readPrivateKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("rsa/private_key.pem").toURI()));
        String privateKeyPEM = new String(keyBytes);
        privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----", "");
        privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");

        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        return keyFactory.generatePrivate(keySpec);
    }
}
