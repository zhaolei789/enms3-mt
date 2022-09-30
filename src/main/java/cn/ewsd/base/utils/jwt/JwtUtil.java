package cn.ewsd.base.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Date;

public class JwtUtil {
    public JwtUtil() {
    }

    public static Claims parseJWT(String jsonWebToken, String base64Security) {
        try {
            Claims var2 = (Claims) Jwts.parser().setAllowedClockSkewSeconds(300L).setSigningKey(DatatypeConverter.parseBase64Binary(base64Security)).parseClaimsJws(jsonWebToken).getBody();
            return var2;
        } catch (Exception var3) {
            System.out.println(var3);
            System.out.println(var3.getMessage());
            return null;
        }
    }

    public static String createJWT(String uuid, String userNameId, String userName, String orgId, String roleId,String tenantId, String audience, String issuer, long TTLMillis, String base64Security) {
        SignatureAlgorithm var10 = SignatureAlgorithm.HS256;
        long var11 = System.currentTimeMillis();
        Date var13 = new Date(var11);
        byte[] var14 = DatatypeConverter.parseBase64Binary(base64Security);
        SecretKeySpec var15 = new SecretKeySpec(var14, var10.getJcaName());
        JwtBuilder var16 = Jwts.builder().setHeaderParam("type", "JWT").claim("uuid", uuid).claim("userNameId", userNameId).claim("userName", userName).claim("orgId", orgId).claim("roleId", roleId).claim("tenantId", tenantId).setIssuer(issuer).setAudience(audience).signWith(var10, var15);
        if (TTLMillis >= 0L) {
            long var17 = var11 + TTLMillis;
            Date var19 = new Date(var17);
            var16.setExpiration(var19).setNotBefore(var13);
        }

        return var16.compact();
    }
}
