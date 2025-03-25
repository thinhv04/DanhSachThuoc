// package com.example.quanlybenhvien.Security;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// @Order(2) // Đảm bảo chạy sau QuanLySecurityConfig
// public class BacSiSecurity {
//     private final PasswordEncoder passwordEncoder;

//     public BacSiSecurity(PasswordEncoder passwordEncoder) {
//         this.passwordEncoder = passwordEncoder;
//     }

//     @Bean
//     public SecurityFilterChain bacSiSecurityFilterChain(HttpSecurity http) throws Exception {
//         http
//             .securityMatcher("/bacsi/**","/api/**","/js/**") // Áp dụng bảo mật cho "/bacsi/**"
//             .authorizeHttpRequests(auth -> auth
//                 .requestMatchers("/bacsi/**","/api/**","/js/**","/quanly/**").hasAnyRole("VT01", "VT00") // Chỉ Bác sĩ & Quản lý vào được
//                 .anyRequest().authenticated())
//             .formLogin(form -> form
//                 .loginPage("/bacsi/login") // Trang đăng nhập riêng cho bác sĩ
//                 .loginProcessingUrl("/bacsi/login")
//                 .defaultSuccessUrl("/bacsi/trangchu", true)
//                 .failureUrl("/bacsi/login?error=true")
//                 .permitAll())
//             .logout(logout -> logout
//                 .logoutUrl("/bacsi/logout")
//                 .logoutSuccessUrl("/bacsi/login?logout=true")
//                 .invalidateHttpSession(true)
//                 .clearAuthentication(true))
//             .csrf(csrf -> csrf.disable()); // Tạm tắt CSRF (cân nhắc bật lại trong môi trường thực)

//         return http.build();
//     }
// }
