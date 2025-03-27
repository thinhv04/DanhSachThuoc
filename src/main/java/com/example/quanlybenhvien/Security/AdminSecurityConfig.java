package com.example.quanlybenhvien.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class AdminSecurityConfig {
    private final PasswordEncoder passwordEncoder;

    public AdminSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain nhanvienSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/nhanvien/**") // Áp dụng bảo mật cho "/bacsi/**"
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/nhanvien/login", "/nhanvien/logout").permitAll()
                        .requestMatchers("/nhanvien/**", "/api/**").hasAnyRole("VT03", "VT00") // nhanvien và quản lý
                                                                                               // vào được
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/nhanvien/login")
                        .loginProcessingUrl("/nhanvien/login")
                        .defaultSuccessUrl("/nhanvien/trangchu", true)
                        .failureUrl("/nhanvien/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/nhanvien/logout")
                        .logoutSuccessUrl("/nhanvien/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/quanly/**", "/js/**") // Chỉ áp dụng cho "/admin/**"
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/quanly/**", "/api/**").hasRole("VT00") // Chỉ ADMIN mới vào được
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/quanly/login") // Trang đăng nhập riêng cho admin
                        .loginProcessingUrl("/quanly/login")
                        .defaultSuccessUrl("/quanly/trangchu", true) // Sau khi đăng nhập thành công chuyển vào
                                                                     // "/admin/home"
                        .failureUrl("/quanly/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/quanly/logout") // Đăng xuất dành riêng cho admin
                        .logoutSuccessUrl("/quanly/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain bacsiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/bacsi/**") // Áp dụng bảo mật cho "/bacsi/**"
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/bacsi/**").hasAnyRole("VT01", "VT00") // Bác sĩ và quản lý vào được
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/bacsi/login") // Trang đăng nhập riêng cho bác sĩ
                        .loginProcessingUrl("/bacsi/login")
                        .defaultSuccessUrl("/bacsi/trangchu", true)
                        .failureUrl("/bacsi/login?error=true")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/bacsi/logout")
                        .logoutSuccessUrl("/bacsi/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true))
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

}