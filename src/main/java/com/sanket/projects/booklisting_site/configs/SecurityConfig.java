package com.sanket.projects.booklisting_site.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
	
	// rsa
	private final RsaKeyProperties rsaKeys;
	private final UserDetailsService customUserDetailsService;

	public SecurityConfig(RsaKeyProperties rsaKeys, UserDetailsService customUserDetailsService) {
		super();
		this.rsaKeys = rsaKeys;
		this.customUserDetailsService = customUserDetailsService;
	}
	
	// another bean of securityfilterchain for just testing multiple securityfilterchains
//	@Bean
////	@Order(2)
//	public SecurityFilterChain anotherSecurityFilterChain(HttpSecurity http) throws Exception
//	{
//		return http
//				.csrf(csrf->csrf.disable())
//				.securityMatcher("/test")
//				.authorizeHttpRequests(auth -> {					
////					auth.requestMatchers("/error").permitAll();
//					auth.anyRequest().permitAll();
//				})
//				.build();
//	}
	
	// custom authentication manager, since want to use only jwts
	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
	{
		
		var authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(authProvider);
		
	}
	
	@Bean
//	@Order(1)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		return http
				// disable csrf
				.csrf(csrf->csrf.disable())
//				.securityMatcher("**")
				.authorizeHttpRequests(auth->auth
//						.anyRequest().authenticated()
						// allow everyone to create account
						.requestMatchers(HttpMethod.POST, "/create-account").permitAll()
						.requestMatchers(HttpMethod.POST, "/send-otp").permitAll()
						.requestMatchers(HttpMethod.POST, "/token").permitAll()
						.requestMatchers("/error").permitAll()
						.anyRequest().authenticated()
				)
				// in built oauth2 resource server to authenticate against
				.oauth2ResourceServer((oauth2ResourceServer) ->
 					oauth2ResourceServer
 					.jwt((jwt) ->
 							jwt
 							.decoder(jwtDecoder())
 					)
				)
				// since no need of basic auth, no need to specify userdetailservice to spring
//				.userDetailsService(customUserDetailsService)
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// no need to basic auth
//				.httpBasic(Customizer.withDefaults())

				.build();
	}
	
	// bean for decoding jwts
	@Bean
 	public JwtDecoder jwtDecoder() {
 		return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
 	}
	
	// for encoding
	@Bean
	public JwtEncoder jwtEncoder()
	{
		JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
		
		JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
		return new NimbusJwtEncoder(jwks);
	}
	
	// bean for password encoder
	 @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
