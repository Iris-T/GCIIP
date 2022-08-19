package cn.iris.gciip.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * JWT工具类
 * @author Iris 2022/8/6
 */
public class JwtUtil {
    /* 有效期 60*60*1000*24*7 一周 */
    public static final Long JWT_TTL = 7*24*60*60*1000L;
    /* 设置密钥明文 */
    public static final String JWT_KEY = "foxiris";

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成JWT令牌
     * @param subject token中存储的数据（JSON格式）
     * @return JWT令牌
     */
    public static String createJWT(String subject) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    /**
     * 生成JWT令牌
     * @param subject token中存储的数据（JSON格式）
     * @param ttlMillis token存活时间
     * @return JWT令牌
     */
    public static String createJWT(String subject, Long ttlMillis) {
        // 设置过期时间
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());
        return builder.compact();
    }

    /**
     * 生成JWT令牌
     * @param id 唯一ID
     * @param subject 存储数据（JSON格式）
     * @param ttlMillis token存活时间
     * @return JWT令牌
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);
        return builder.compact();
    }

    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm hs512 = SignatureAlgorithm.HS512;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder().setId(uuid)   // 唯一ID
                .setSubject(subject)    // 主题，可以是JSON数据
                .setIssuer("iris")      // 签发者
                .setIssuedAt(now)       // 签发时间
                // 加密算法（HS512对称加密算法）以及密钥
                .signWith(hs512, secretKey)
                .setExpiration(expDate);
    }

    /**
     * 生成加密后的密钥 secretKey
     * @return AES加密后的密钥
     */
    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
    }

    /**
     * 解析Token
     */
    public static Claims parseJwt(String jwt) throws Exception {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static void main(String[] args) {
        String jwt = createJWT("123456");
        System.out.println(jwt);
        Claims claims = null;
        try {
            claims = parseJwt(jwt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(claims);
    }
}
