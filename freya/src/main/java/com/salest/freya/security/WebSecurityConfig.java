package com.salest.freya.security;

import com.salest.freya.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
@Configuration
@EnableMethodSecurity
public class  WebSecurityConfig {
    @Autowired
    CustomUserDetailsService userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.authenticationEntryPoint(unauthorizedHandler)
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // Swagger / OpenAPI: público, si no la UI no carga
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                                // Auth: público, si no nadie puede hacer login/registro
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/configs/**").permitAll()
                                // Salud / demo públicos
                                .requestMatchers(HttpMethod.GET, "/api/health", "/api/health/**", "/catpat", "/api/test/all").permitAll()
                                .requestMatchers("/error").permitAll()
                                // Catálogo público: ver canciones y covers aprobadas + detalle por id
                                // OJO: "/all" va antes y exige login, porque "/**" lo taparía si estuviera después
                                .requestMatchers(HttpMethod.GET, "/api/songs/all", "/api/covers/all").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/songs", "/api/songs/", "/api/songs/*",
                                        "/api/covers", "/api/covers/", "/api/covers/*").permitAll()
                                // Escrituras: requieren login.
                                // TODO: cuando User tenga rol y CustomUserDetailsService devuelva authorities,
                                // cambiar authenticated() por hasRole("ADMIN") en songs y en listings /all.
                                .requestMatchers(HttpMethod.POST, "/api/songs/**", "/api/covers/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/api/songs/**", "/api/covers/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/songs/**", "/api/covers/**").authenticated()
                                // Usuarios y endpoints de prueba con usuario: requieren login
                                .requestMatchers("/api/users/**").authenticated()
                                .requestMatchers("/api/test/user").authenticated()
                                // Cualquier otro endpoint: requiere login (evita exponer por descuido)
                                .anyRequest().authenticated()
                );
       
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}