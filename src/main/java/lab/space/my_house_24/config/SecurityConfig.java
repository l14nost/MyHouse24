package lab.space.my_house_24.config;

import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.UserStatus;
import lab.space.my_house_24.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final StaffRepository staffRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/apartments/**").hasAuthority("APARTMENTS")
                        .requestMatchers("/bank-books/**").hasAuthority("BANK_BOOKS")
                        .requestMatchers("/bills/**").hasAuthority("BILLS")
                        .requestMatchers("/cash-box/**").hasAuthority("CASH_BOX")
                        .requestMatchers("/houses/**").hasAuthority("HOUSES")
                        .requestMatchers("/master-call/**").hasAuthority("MASTERS_APPLICATIONS")
                        .requestMatchers("/messages/**").hasAuthority("MESSAGES")
                        .requestMatchers("/meter-readings/**").hasAuthority("METER_READING")
                        .requestMatchers("/rates/**").hasAuthority("RATES")
                        .requestMatchers("/payment-details/**").hasAuthority("REQUISITES")
                        .requestMatchers("/role/**").hasAuthority("ROLES")
                        .requestMatchers("/system-service/**").hasAuthority("SERVICES")
                        .requestMatchers("/staff/**").hasAuthority("STAFF")
                        .requestMatchers("/statistics/**").hasAuthority("STATISTICS")
                        .requestMatchers("/users/**").hasAuthority("APARTMENTS_OWNERS")
                        .requestMatchers("/site/**").hasAuthority("SETTINGS_PAGE")
                        .requestMatchers("/assets/css/**", "/assets/img/**",
                                "/assets/js/**", "/assets/vendor/**",
                                "/assets/images/**", "/auth/**",
                                "/files/**", "/login/**", "/error/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 ->
                        oauth2
                                .loginPage("/login")
                                .successHandler((request, response, authentication) -> {
                                    OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
                                    Optional<Staff> staff = staffRepository.findByEmail(oauthUser.getAttribute("email"));
                                    if (staff.isPresent() && staff.get().getStaffStatus().equals(UserStatus.ACTIVE)) {
                                        SecurityContextHolder.getContext().setAuthentication(
                                                new UsernamePasswordAuthenticationToken(
                                                        new org.springframework.security.core.userdetails.User(
                                                                staff.get().getUsername(), staff.get().getPassword(), staff.get().getAuthorities()
                                                        ),
                                                        staff.get().getPassword(),
                                                        staff.get().getAuthorities()
                                                )
                                        );
                                    } else {
                                        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
                                    }

                                    response.sendRedirect("/myhouse24-amirb-nikitaf/admin/");
                                })
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .rememberMe((rememberMe) -> rememberMe.rememberMeParameter("remember-me").tokenValiditySeconds(86400))
                .logout(form -> form
                        .logoutSuccessUrl("/login?logout")
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
