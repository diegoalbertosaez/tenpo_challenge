package tenpo.diegosaez.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import tenpo.diegosaez.security.TokenAuthorizationFilter;
import tenpo.diegosaez.security.UserAuthenticationEntryPoint;
import tenpo.diegosaez.security.service.AuthenticationUserServiceDetails;

/**
 * 
 * Clase de configuración de seguridad.
 * 
 * @author diegosaez
 *
 */
@EnableWebSecurity
@RequiredArgsConstructor
@Profile("!test")
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String[] AUTH_WHITELIST = { "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
			"/swagger-ui.html", "/swagger-ui/**", "/webjars/**", "/users/login", "/users/signup",
			"/execution_history" };

	private final AuthenticationUserServiceDetails userDetailService;
	private final TokenAuthorizationFilter tokenAuthorizationFilter;
	private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated().and()
				.addFilterBefore(tokenAuthorizationFilter, UsernamePasswordAuthenticationFilter.class).httpBasic()
				.authenticationEntryPoint(userAuthenticationEntryPoint);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
