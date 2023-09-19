package lab.space.my_house_24.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("apartments").hasAuthority("APARTMENTS")
                        .requestMatchers("bank-books").hasAuthority("BANK_BOOKS")
                        .requestMatchers("bills").hasAuthority("BILLS")
                        .requestMatchers("cash-box").hasAuthority("CASH_BOX")
                        .requestMatchers("houses").hasAuthority("HOUSES")
                        .requestMatchers("master-call").hasAuthority("MASTERS_APPLICATIONS")
                        .requestMatchers("messages").hasAuthority("MESSAGES")
                        .requestMatchers("meter-readings").hasAuthority("METER_READING")
                        .requestMatchers("rates").hasAuthority("RATES")
                        .requestMatchers("payment-details").hasAuthority("REQUISITES")
                        .requestMatchers("role").hasAuthority("ROLES")
                        .requestMatchers("system-service").hasAuthority("SERVICES")
                        .requestMatchers("staff").hasAuthority("STAFF")
                        .requestMatchers("statistics").hasAuthority("STATISTICS")
                        .requestMatchers("users").hasAuthority("APARTMENTS_OWNERS")
                        .requestMatchers("site").hasAuthority("SETTINGS_PAGE")
                        .requestMatchers("/assets/css/**", "/assets/img/**",
                                "/assets/js/**", "/assets/vendor/**",
                                "/assets/images/**", "/auth/**",
                                "/files/**", "/login/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .rememberMe((rememberMe) -> rememberMe.alwaysRemember(true))
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
