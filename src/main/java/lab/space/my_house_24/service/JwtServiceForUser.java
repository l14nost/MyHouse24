package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.User;

public interface JwtServiceForUser {
    String extractUsername(String token);

    String generateToken(String email);

    boolean isTokenValid(String token, String email, User user);
}
