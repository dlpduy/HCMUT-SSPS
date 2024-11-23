package com.project.SSPS.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.util.Base64;
import com.project.SSPS.filter.JwtAuthFilter;
import com.project.SSPS.service.UserDetailCustom;
import com.project.SSPS.util.SecurityUtil;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    private final UserDetailCustom userDetailCustom;

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfiguration(UserDetailCustom userDetailCustom, JwtAuthFilter jwtAuthFilter) {
        this.userDetailCustom = userDetailCustom;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http
    /* CustomAuthenticationEntryPoint customAuthenticationEntryPoint */) throws Exception {
        return http
                .csrf(c -> c.disable())
                // .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        authz -> authz
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                                .requestMatchers("/api/v1/student/*").hasAnyAuthority("STUDENT")
                                .requestMatchers("/api/v1/spso/*").hasAnyAuthority("SPSO")
                                .anyRequest().authenticated())
                .userDetailsService(userDetailCustom)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
        // .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())
        // .authenticationEntryPoint(customAuthenticationEntryPoint))
        // .exceptionHandling(
        // exceptions -> exceptions
        // .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint()) // 401
        // .accessDeniedHandler(new BearerTokenAccessDeniedHandler())) // 403
        // .formLogin(Customizer.withDefaults())

        // .logout(Customizer.withDefaults());

        // .sessionManagement(session ->
        // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Value("${project.jwt.base64-secret}")
    private String jwtKey;

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length,
                SecurityUtil.JWT_ALGORITHM.getName());
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
                getSecretKey()).macAlgorithm(SecurityUtil.JWT_ALGORITHM).build();
        return token -> {
            try {
                return jwtDecoder.decode(token);
            } catch (Exception e) {
                System.out.println(">>> JWT error: " + e.getMessage());
                throw e;
            }
        };
    }

    // @Bean
    // public JwtAuthenticationConverter jwtAuthenticationConverter() {
    // JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new
    // JwtGrantedAuthoritiesConverter();
    // grantedAuthoritiesConverter.setAuthorityPrefix("");
    // grantedAuthoritiesConverter.setAuthoritiesClaimName("permissions");
    // JwtAuthenticationConverter jwtAuthenticationConverter = new
    // JwtAuthenticationConverter();
    // jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    // return jwtAuthenticationConverter;
    // }

    // @Bean
    // public RestTemplate restTemplate() {
    // return new RestTemplate();
    // }

}
