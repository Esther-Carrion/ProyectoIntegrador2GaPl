package net.gecc.crudthymeleaf.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		
			.antMatchers(
				"/",
				"/registration**",
				"/js/**",
				"/css**",
				"/img/**",
				"/h2-console/**",
				"/webjars/**").permitAll()
			.and().csrf().ignoringAntMatchers("/h2-console/**")
            .and().headers().frameOptions().sameOrigin()
			.and()
			.formLogin()
			.loginPage("/juegos/login")
			.permitAll()
			.successForwardUrl("/juegos/private")
			.and()
			.logout()
			.permitAll()
			.logoutRequestMatcher(new AntPathRequestMatcher("/juegos/logout"))
			.logoutSuccessUrl("/juegos");
		//
		
	}
	
	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth)
	  throws Exception {
		auth
		  .inMemoryAuthentication()
		  .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");
	}
	
	
	protected void configuret(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		
			.antMatchers(
				"/",
				"/registration**",
				"/js/**",
				"/css**",
				"/img/**",
				"/h2-console/**",
				"/webjars/**").permitAll()
			.and().csrf().ignoringAntMatchers("/h2-console/**")
            .and().headers().frameOptions().sameOrigin()
			.and()
			.formLogin()
			.loginPage("/cliente/list")
			.permitAll()
			//.successForwardUrl("/cliente/private")
			.and()
			.logout()
			.permitAll()
			.logoutRequestMatcher(new AntPathRequestMatcher("/juegos/logout"))
			.logoutSuccessUrl("/cliente");
		//
		
	}
	@Autowired
	public void configuretGlobal (AuthenticationManagerBuilder auth)
	  throws Exception {		
		auth
		  .inMemoryAuthentication()
		  .withUser("usuario").password(passwordEncoder().encode("usuario")).roles("USUARIO");
		
		
	}
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
	}
}