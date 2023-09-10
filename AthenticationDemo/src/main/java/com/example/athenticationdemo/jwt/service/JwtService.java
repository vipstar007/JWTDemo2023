package com.example.athenticationdemo.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.security.Security;

@Service
public class JwtService {
    //Key mã hóa
    @Value("${app.jwt.secret}")
    private String secretKey;
    //thời gian token hết hạn
    @Value("${app.jwt.expiration}")
    private long jwtExpiration;
    //thời gian refeshToken hết hạn
    @Value("${app.refresh-token.expiration}")
    private long refreshExpiration;
    @Value("${private.key.path}")
    private String privateKeyPath;
    @Value("${public.key.path}")
    private String publicKeyPath;


    public PrivateKey privateKey() throws Exception {
        FileReader reader = new FileReader(privateKeyPath);
        PEMParser pemParser = new PEMParser(reader);
        Object object = pemParser.readObject();
        pemParser.close();

        if (object instanceof org.bouncycastle.openssl.PEMKeyPair) {
            Security.addProvider(new BouncyCastleProvider());
            org.bouncycastle.openssl.PEMKeyPair keyPair = (org.bouncycastle.openssl.PEMKeyPair) object;
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            return converter.getPrivateKey(keyPair.getPrivateKeyInfo());
        } else {
            throw new IllegalArgumentException("Invalid private key format");
        }
    }

    public PublicKey publicKey() throws Exception {
        FileReader reader = new FileReader(publicKeyPath);
        PEMParser pemParser = new PEMParser(reader);
        Object object = pemParser.readObject();
        pemParser.close();
        if (object instanceof org.bouncycastle.asn1.x509.SubjectPublicKeyInfo) {
            Security.addProvider(new BouncyCastleProvider());
            org.bouncycastle.asn1.x509.SubjectPublicKeyInfo publicKeyInfo = (org.bouncycastle.asn1.x509.SubjectPublicKeyInfo) object;
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            return converter.getPublicKey(publicKeyInfo);
        } else {
            throw new IllegalArgumentException("Invalid public key format");
        }
    }
//tạo token
    public String generateJwtToken(String username, PrivateKey privateKey) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
    public String generateRefeshToken(String username, PrivateKey privateKey) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }
//giải token
public Claims parseJwtToken(String token, PublicKey publicKey) {
    return Jwts.parser()
            .setSigningKey(publicKey)
            .parseClaimsJws(token)
            .getBody();
}
//giải mã token trả về email
public String extractUsername2(String token, PublicKey publicKey) {

    return extractClaim2(token, Claims::getSubject,publicKey);
}
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public <T> T extractClaim2(String token, Function<Claims, T> claimsResolver,PublicKey publicKey) {
        final Claims claims =parseJwtToken(token,publicKey);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
// giải mã token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    //Tạo một khóa bí mật từ chuỗi đầu vào
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
