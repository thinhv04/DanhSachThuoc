package com.example.quanlybenhvien.Security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(3)
public class SecurityConfig {
        private final ClientRegistrationRepository clientRegistrationRepository;
        private final PasswordEncoder passwordEncoder;

        public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository,
                        PasswordEncoder passwordEncoder) {
                this.clientRegistrationRepository = clientRegistrationRepository;
                this.passwordEncoder = passwordEncoder;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET, "/dangky")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.POST, "/dangky").permitAll()
                                                .requestMatchers("/dangky", "/index", "/login", "/css/**", "/js/**",
                                                                "/images/**", "/api/vaitro", "/nguoidung/chuyenkhoa",
                                                                "/nguoidung/chuyenkhoa/chitiet/**",
                                                                "/nguoidung/bacsi", "/nguoidung/bacsi/chitiet/**",
                                                                "/nguoidung/lichkham")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/login") // Trang đăng nhập chung
                                                .loginProcessingUrl("/login_1") // URL xử lý form login
                                                .usernameParameter("email") // Tên trường email
                                                .passwordParameter("matkhau") // Tên trường mật khẩu
                                                .defaultSuccessUrl("/index", true) // Chuyển hướng sau đăng nhập form
                                                                                   // thành công
                                                .failureUrl("/login?error=true") // Chuyển hướng khi đăng nhập form thất
                                                                                 // bại
                                                .permitAll())
                                .oauth2Login(oauth2 -> oauth2
                                                .loginPage("/login") // Trang đăng nhập chung
                                                .authorizationEndpoint(authEndpoint -> authEndpoint
                                                                .authorizationRequestResolver(
                                                                                customAuthorizationRequestResolver())) // Sử
                                                                                                                       // dụng
                                                                                                                       // resolver
                                                                                                                       // tùy
                                                                                                                       // chỉnh
                                                .defaultSuccessUrl("/loginSuccess", true)) // Chuyển hướng sau đăng nhập
                                                                                           // OAuth2 thành công
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout=true")
                                                .invalidateHttpSession(true)
                                                .clearAuthentication(true))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(false))
                                .csrf(csrf -> csrf.disable()); // Tạm thời tắt CSRF (nên bật lại trong sản phẩm thực tế)

                return http.build();
        }

        @Bean
        public OAuth2AuthorizationRequestResolver customAuthorizationRequestResolver() {
                DefaultOAuth2AuthorizationRequestResolver defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                                clientRegistrationRepository, "/oauth2/authorization");

                return new OAuth2AuthorizationRequestResolver() {
                        @Override
                        public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
                                OAuth2AuthorizationRequest originalRequest = defaultResolver.resolve(request);
                                if (originalRequest == null)
                                        return null;

                                // Thêm tham số `prompt=select_account`
                                return OAuth2AuthorizationRequest.from(originalRequest)
                                                .additionalParameters(params -> params.put("prompt", "select_account"))
                                                .build();
                        }

                        @Override
                        public OAuth2AuthorizationRequest resolve(HttpServletRequest request,
                                        String clientRegistrationId) {
                                OAuth2AuthorizationRequest originalRequest = defaultResolver.resolve(request,
                                                clientRegistrationId);
                                if (originalRequest == null)
                                        return null;

                                // Thêm tham số `prompt=select_account`
                                return OAuth2AuthorizationRequest.from(originalRequest)
                                                .additionalParameters(params -> params.put("prompt", "select_account"))
                                                .build();
                        }
                };
        }
}
