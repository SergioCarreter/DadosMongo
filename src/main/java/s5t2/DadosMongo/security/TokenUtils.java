package s5t2.DadosMongo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRET = "4qhq8LrEBfYcaRHxhdb9zURb2rf8e7Ud";
    // 7 DIAs
    private final static Long ACCESS_TOKEN_VALIDITY_SECONDS = 604_800L;

    // metodo crea el token (String) y lo envia al cliente
    // fechaReg datos adicionales
    // El objeto del token es el nombre.
    public static String createToken(String fechaReg, String nombre) {
        long expirationTime = ACCESS_TOKEN_VALIDITY_SECONDS * 1000;
        Date expirationDate = new Date( System.currentTimeMillis() + expirationTime );

        Map<String, Object> extra = new HashMap<>();
        extra.put("fechaReg", fechaReg);

        return Jwts.builder()
                .setSubject(nombre)
                .setExpiration(expirationDate)
                .addClaims(extra)
                .signWith(Keys.hmacShaKeyFor( ACCESS_TOKEN_SECRET.getBytes() ) )
                .compact();
    }

    // Devuelve UsernamePasswordAuthenticationToken reconocido por Spring Security para dar Autorizacion de acceso
    // a los endpoints
    // Proceso inverso de creacion del token
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey( ACCESS_TOKEN_SECRET.getBytes() )
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // Extraemos el nombre que era el objeto del token
            String nombre = claims.getSubject();
            // el username de autenticacion es el nombre
            return new UsernamePasswordAuthenticationToken( nombre, null, Collections.emptyList() );

        } catch(JwtException e){
            return null;
        }

    }

}
