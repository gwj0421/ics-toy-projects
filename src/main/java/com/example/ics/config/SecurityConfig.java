package com.example.ics.config;

import com.example.ics.config.properties.AppProperty;
import com.example.ics.config.properties.CorsProperty;
import com.example.ics.dto.user.RoleType;
import com.example.ics.handler.CustomAuthenticationFailureHandler;
import com.example.ics.oauth.dto.token.AuthTokenProvider;
import com.example.ics.oauth.exception.RestAuthenticationEntryPoint;
import com.example.ics.oauth.filter.TokenAuthenticationFilter;
import com.example.ics.oauth.handler.OAuth2AuthenticationFailureHandler;
import com.example.ics.oauth.handler.OAuth2AuthenticationSuccessHandler;
import com.example.ics.oauth.handler.TokenAccessDeniedHandler;
import com.example.ics.oauth.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.ics.oauth.repository.UserRefreshTokenRepository;
import com.example.ics.oauth.service.CustomOAuth2UserService;
import com.example.ics.repository.SiteUserRepository;
import com.example.ics.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsProperty corsProperty;
    private final AppProperty appProperty;
    private final AuthTokenProvider tokenProvider;
    private final SiteUserRepository userRepository;
    private final CustomOAuth2UserService oAuth2Service;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    private final ClientRegistrationRepository clientRegistrationRepository;
    private final CustomAuthenticationFailureHandler customAuthFailureHandler;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.httpBasic(HttpBasicConfigurer::disable);

        http.authorizeHttpRequests(auth ->
                auth.requestMatchers("/text/**", "/image/**").hasAnyAuthority(RoleType.ADMIN.getCode(), RoleType.USER.getCode())
                        .anyRequest().permitAll()
        );

        http.formLogin(login -> login.loginPage("/user/login")
                .defaultSuccessUrl("/")
                .usernameParameter("userID")
                .failureHandler(customAuthFailureHandler)
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 URL 설정
                .logoutSuccessUrl("/") // 로그아웃 성공 후 리다이렉트할 URL 설정
                .permitAll()
        );

        http.exceptionHandling(handler -> handler.authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(tokenAccessDeniedHandler));

        http.oauth2Login(oauth -> oauth
                .authorizationEndpoint(
                        authorizationEndpointConfig -> authorizationEndpointConfig
                                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                                .authorizationRequestResolver(new CustomAuthorizationRequestResolver(
                                        this.clientRegistrationRepository
                                ))
                )
                .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig
                        .baseUri("/*/oauth2/code/*"))
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(oAuth2Service))
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler())
        );

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailService());
        return new ProviderManager(provider);
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(corsProperty.getAllowedOrigins());
        corsConfiguration.setAllowedHeaders(corsProperty.getAllowedHeaders());
        corsConfiguration.setAllowedMethods(corsProperty.getAllowedMethods());
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(corsConfiguration.getMaxAge());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperty,
                userRefreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    @Bean
    public UserDetailsService userDetailService() {
        return new CustomUserDetailsService(userRepository);
    }

    public static class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {
        private final OAuth2AuthorizationRequestResolver defaultAuthorizationRequestResolver;

        public CustomAuthorizationRequestResolver(
                ClientRegistrationRepository clientRegistrationRepository) {

            this.defaultAuthorizationRequestResolver =
                    new DefaultOAuth2AuthorizationRequestResolver(
                            clientRegistrationRepository, "/oauth2/authorization");
        }

        @Override
        public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
            OAuth2AuthorizationRequest authorizationRequest =
                    this.defaultAuthorizationRequestResolver.resolve(request);

            return authorizationRequest != null
                    ? customAuthorizationRequest(authorizationRequest) :
                    null;
        }

        @Override
        public OAuth2AuthorizationRequest resolve(
                HttpServletRequest request, String clientRegistrationId) {

            OAuth2AuthorizationRequest authorizationRequest =
                    this.defaultAuthorizationRequestResolver.resolve(
                            request, clientRegistrationId);

            return authorizationRequest != null ?
                    customAuthorizationRequest(authorizationRequest) :
                    null;
        }

        private OAuth2AuthorizationRequest customAuthorizationRequest(
                OAuth2AuthorizationRequest authorizationRequest) {

            Map<String, Object> additionalParameters =
                    new LinkedHashMap<>(authorizationRequest.getAdditionalParameters());
            additionalParameters.put("prompt", "consent");

            return OAuth2AuthorizationRequest.from(authorizationRequest)
                    .additionalParameters(additionalParameters)
                    .build();
        }

    }
}
