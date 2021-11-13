package ru.reasy.crunch.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class HttpAuthUtils {

    public static String getSign(String secret, String ts, String httpMethod, String url, String body) {
        if ("POST".equals(httpMethod) && body == null) {
            log.error("Body must passed when method POST");
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb
                .append(ts)
                .append(httpMethod)
                .append(url)
                .append(body != null ? body : "");
        log.info(sb.toString());
        byte[] var = calcHmacSha256(
                secret.getBytes(StandardCharsets.UTF_8),
                sb.toString().getBytes(StandardCharsets.UTF_8)
        );
        return Tools.bytesToHex(var);
    }


    public static byte[] calcHmacSha256(byte[] secretKey, byte[] message) {
        byte[] hmacSha256 = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey, "HmacSHA256");
            mac.init(secretKeySpec);
            hmacSha256 = mac.doFinal(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate hmac-sha256", e);
        }
        return hmacSha256;
    }
}
