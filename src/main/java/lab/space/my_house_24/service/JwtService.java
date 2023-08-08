package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.Staff;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails, Staff staff);
}
