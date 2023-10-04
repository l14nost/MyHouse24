package lab.space.my_house_24.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.UserStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {

    @InjectMocks
    private JwtServiceImpl jwtService;

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
        when(decodedJWT.getSubject()).thenReturn("Test");
        when(JWT.decode(anyString())).thenReturn(decodedJWT);

        String username = jwtService.extractUsername("token");
        assertEquals("Test", username);
    }

    @Test
    void generateToken() {
        Staff staff = Staff.builder().password("pass").email("test@gmail.com").build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                staff.getUsername(),
                staff.getPassword(),
                Collections.singletonList(authority)
        );

        jwtService.setJWT_SECRET("SV9ET04nVF9LTk9XX1RIRV9TRUNSRVRfVE9LRU4=");
        byte[] t = {1, 2, 3, 4, 5};

        Algorithm algorithm = mock(Algorithm.class);
        Base64.Decoder decoder = mock(Base64.Decoder.class);
        when(Algorithm.HMAC256(t)).thenReturn(algorithm);
        when(Base64.getDecoder()).thenReturn(decoder);
        when(Base64.getDecoder().decode(anyString())).thenReturn(t);

        JWTCreator.Builder builder = mock(JWTCreator.Builder.class);
        when(JWT.create()).thenReturn(builder);
        when(JWT.create().withSubject(anyString())).thenReturn(builder);
        when(JWT.create().withExpiresAt(any(Date.class))).thenReturn(builder);
        when(JWT.create().withIssuedAt(any(Date.class))).thenReturn(builder);
        when(JWT.create().sign(any(Algorithm.class))).thenReturn("Test");

        assertEquals("Test", jwtService.generateToken(userDetails));
    }

    @Test
    void isActivateTokenValid() {
        Staff staff = Staff.builder().password("pass").email("test@gmail.com").build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                staff.getUsername(),
                staff.getPassword(),
                Collections.singletonList(authority)
        );

        jwtService.setJWT_SECRET("SV9ET04nVF9LTk9XX1RIRV9TRUNSRVRfVE9LRU4=");
        byte[] t = {1, 2, 3, 4, 5};

        Algorithm algorithm = mock(Algorithm.class);
        Base64.Decoder decoder = mock(Base64.Decoder.class);
        when(Algorithm.HMAC256(t)).thenReturn(algorithm);
        when(Base64.getDecoder()).thenReturn(decoder);
        when(Base64.getDecoder().decode(anyString())).thenReturn(t);

        Verification verification = mock(Verification.class);
        when(JWT.require(any(Algorithm.class))).thenReturn(verification);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername())).thenReturn(verification);

        JWTVerifier jwtVerifier = mock(JWTVerifier.class);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername()).build()).thenReturn(jwtVerifier);

        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername()).build().verify("token")).thenReturn(decodedJWT);

        staff.setTokenUsage(false);
        staff.setForgotTokenUsage(true);
        staff.setToken("token");

        assertTrue(jwtService.isTokenValid("token", userDetails, staff, "activate"));
    }

    @Test
    void isForgotTokenValid() {
        Staff staff = Staff.builder().password("pass").email("test@gmail.com").build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                staff.getUsername(),
                staff.getPassword(),
                Collections.singletonList(authority)
        );

        jwtService.setJWT_SECRET("SV9ET04nVF9LTk9XX1RIRV9TRUNSRVRfVE9LRU4=");
        byte[] t = {1, 2, 3, 4, 5};

        Algorithm algorithm = mock(Algorithm.class);
        Base64.Decoder decoder = mock(Base64.Decoder.class);
        when(Algorithm.HMAC256(t)).thenReturn(algorithm);
        when(Base64.getDecoder()).thenReturn(decoder);
        when(Base64.getDecoder().decode(anyString())).thenReturn(t);

        Verification verification = mock(Verification.class);
        when(JWT.require(any(Algorithm.class))).thenReturn(verification);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername())).thenReturn(verification);

        JWTVerifier jwtVerifier = mock(JWTVerifier.class);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername()).build()).thenReturn(jwtVerifier);

        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername()).build().verify("token")).thenReturn(decodedJWT);

        staff.setTokenUsage(true);
        staff.setForgotTokenUsage(false);
        staff.setForgotToken("token");
        staff.setToken("token");

        assertTrue(jwtService.isTokenValid("token", userDetails, staff, "forgot"));
    }

    @Test
    void isTokenValidFail() {
        Staff staff = Staff.builder().password("pass").email("test@gmail.com").build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                staff.getUsername(),
                staff.getPassword(),
                Collections.singletonList(authority)
        );

        jwtService.setJWT_SECRET("SV9ET04nVF9LTk9XX1RIRV9TRUNSRVRfVE9LRU4=");
        byte[] t = {1, 2, 3, 4, 5};

        Algorithm algorithm = mock(Algorithm.class);
        Base64.Decoder decoder = mock(Base64.Decoder.class);
        when(Algorithm.HMAC256(t)).thenReturn(algorithm);
        when(Base64.getDecoder()).thenReturn(decoder);
        when(Base64.getDecoder().decode(anyString())).thenReturn(t);

        Verification verification = mock(Verification.class);
        when(JWT.require(any(Algorithm.class))).thenReturn(verification);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername())).thenReturn(verification);

        JWTVerifier jwtVerifier = mock(JWTVerifier.class);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername()).build()).thenReturn(jwtVerifier);

        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername()).build().verify("token")).thenReturn(decodedJWT);

        staff.setTokenUsage(true);
        staff.setForgotTokenUsage(false);
        staff.setForgotToken("token");
        staff.setToken("token");

        assertFalse(jwtService.isTokenValid("token", userDetails, staff, ""));
    }

    @Test
    void isForgotTokenValidJWTError() {
        Staff staff = Staff.builder().password("pass").email("test@gmail.com").build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                staff.getUsername(),
                staff.getPassword(),
                Collections.singletonList(authority)
        );

        jwtService.setJWT_SECRET("SV9ET04nVF9LTk9XX1RIRV9TRUNSRVRfVE9LRU4=");
        byte[] t = {1, 2, 3, 4, 5};

        Algorithm algorithm = mock(Algorithm.class);
        Base64.Decoder decoder = mock(Base64.Decoder.class);
        when(Algorithm.HMAC256(t)).thenReturn(algorithm);
        when(Base64.getDecoder()).thenReturn(decoder);
        when(Base64.getDecoder().decode(anyString())).thenReturn(t);

        Verification verification = mock(Verification.class);
        when(JWT.require(any(Algorithm.class))).thenReturn(verification);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername())).thenReturn(verification);

        JWTVerifier jwtVerifier = mock(JWTVerifier.class);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername()).build()).thenReturn(jwtVerifier);
        when(JWT.require(algorithm).withSubject(userDetails.getUsername()).build().verify(anyString())).thenThrow(new JWTVerificationException("Jwt error"));

        staff.setTokenUsage(true);
        staff.setForgotTokenUsage(false);
        staff.setForgotToken("token");

        assertFalse(jwtService.isTokenValid("token", userDetails, staff, "forgot"));
    }
}