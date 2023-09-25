package lab.space.my_house_24.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import lab.space.my_house_24.entity.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceForUserImplTest {
    @InjectMocks
    private JwtServiceForUserImpl jwtServiceForUser;

    private static MockedStatic<JWT> jwtMock;
    private static MockedStatic<Algorithm> algorithmMock;
    private static MockedStatic<Base64> base64Mock;

    @BeforeAll
    public static void setUp() {
        jwtMock = Mockito.mockStatic(JWT.class);
        algorithmMock = Mockito.mockStatic(Algorithm.class);
        base64Mock = Mockito.mockStatic(Base64.class);
    }

    @AfterAll
    public static void tearDown() {
        jwtMock.close();
        algorithmMock.close();
        base64Mock.close();
    }

    @Test
    void extractUsername() {
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(decodedJWT.getSubject()).thenReturn("test@gmail.com");
        when(JWT.decode("token")).thenReturn(decodedJWT);
        String username = jwtServiceForUser.extractUsername("token");
        assertEquals("test@gmail.com", username);
    }

    @Test
    void generateToken() {
        jwtServiceForUser.setJWT_SECRET("SV9ET04nVF9LTk9XX1RIRV9TRUNSRVRfVE9LRU4=");
        byte[] t =  {1, 2, 3, 4, 5};
        Algorithm algorithm = mock(Algorithm.class);
        Base64.Decoder decoder = mock(Base64.Decoder.class);
        when(Algorithm.HMAC256(t)).thenReturn(algorithm);
        when(Base64.getDecoder()).thenReturn(decoder);
        when(Base64.getDecoder().decode(anyString())).thenReturn(t);
        JWTCreator.Builder builder  = mock(JWTCreator.Builder.class);
        when(JWT.create()).thenReturn(builder);
        when(JWT.create().withSubject(anyString())).thenReturn(builder);
        when(JWT.create().withExpiresAt(any(Date.class))).thenReturn(builder);
        when(JWT.create().withIssuedAt(any(Date.class))).thenReturn(builder);
        when(JWT.create().sign(any(Algorithm.class))).thenReturn("token");
        assertEquals("token", jwtServiceForUser.generateToken("email@gmail.com"));
    }

    @Test
    void isTokenValid() {
        jwtServiceForUser.setJWT_SECRET("SV9ET04nVF9LTk9XX1RIRV9TRUNSRVRfVE9LRU4=");
        byte[] t =  {1, 2, 3, 4, 5};
        Algorithm algorithm = mock(Algorithm.class);
        Base64.Decoder decoder = mock(Base64.Decoder.class);
        when(Algorithm.HMAC256(t)).thenReturn(algorithm);
        when(Base64.getDecoder()).thenReturn(decoder);
        when(Base64.getDecoder().decode(anyString())).thenReturn(t);
        Verification verification = mock(Verification.class);
        when(JWT.require(any(Algorithm.class))).thenReturn(verification);
        when(JWT.require(algorithm).withSubject("mail@gmail.com")).thenReturn(verification);

        JWTVerifier jwtVerifier = mock(JWTVerifier.class);
        when(JWT.require(algorithm).withSubject("mail@gmail.com").build()).thenReturn(jwtVerifier);

        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(JWT.require(algorithm).withSubject("mail@gmail.com").build().verify("token")).thenReturn(decodedJWT);

        User user = User.builder().token("token").tokenUsage(false).build();

        boolean check = jwtServiceForUser.isTokenValid("token", "mail@gmail.com", user);
        assertTrue(check);
    }

    @Test
    void isTokenValid_Invalid() {
        jwtServiceForUser.setJWT_SECRET("SV9ET04nVF9LTk9XX1RIRV9TRUNSRVRfVE9LRU4=");
        byte[] t =  {1, 2, 3, 4, 5};
        Algorithm algorithm = mock(Algorithm.class);
        Base64.Decoder decoder = mock(Base64.Decoder.class);
        when(Algorithm.HMAC256(t)).thenReturn(algorithm);
        when(Base64.getDecoder()).thenReturn(decoder);
        when(Base64.getDecoder().decode(anyString())).thenReturn(t);
        Verification verification = mock(Verification.class);
        when(JWT.require(any(Algorithm.class))).thenReturn(verification);
        when(JWT.require(algorithm).withSubject("mail@gmail.com")).thenReturn(verification);

        JWTVerifier jwtVerifier = mock(JWTVerifier.class);
        when(JWT.require(algorithm).withSubject("mail@gmail.com").build()).thenReturn(jwtVerifier);

        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(JWT.require(algorithm).withSubject("mail@gmail.com").build().verify("token")).thenReturn(decodedJWT);

        User user = User.builder().token("token1").tokenUsage(false).build();

        boolean check = jwtServiceForUser.isTokenValid("token", "mail@gmail.com", user);
        assertFalse(check);
    }

    @Test
    void isTokenValid_Exception() {
        jwtServiceForUser.setJWT_SECRET("SV9ET04nVF9LTk9XX1RIRV9TRUNSRVRfVE9LRU4=");
        byte[] t =  {1, 2, 3, 4, 5};
        Algorithm algorithm = mock(Algorithm.class);
        Base64.Decoder decoder = mock(Base64.Decoder.class);
        when(Algorithm.HMAC256(t)).thenReturn(algorithm);
        when(Base64.getDecoder()).thenReturn(decoder);
        when(Base64.getDecoder().decode(anyString())).thenReturn(t);

        Verification verification = mock(Verification.class);
        when(JWT.require(any(Algorithm.class))).thenReturn(verification);
        when(JWT.require(algorithm).withSubject("mail@gmail.com")).thenReturn(verification);

        JWTVerifier jwtVerifier = mock(JWTVerifier.class);
        when(JWT.require(algorithm).withSubject("mail@gmail.com").build()).thenReturn(jwtVerifier);
        when(JWT.require(algorithm).withSubject("mail@gmail.com").build().verify("token")).thenThrow(new JWTVerificationException("qweqw"));

        User user = User.builder().token("token").tokenUsage(false).build();

        boolean check = jwtServiceForUser.isTokenValid("token", "mail@gmail.com", user);
        assertFalse(check);
    }


}