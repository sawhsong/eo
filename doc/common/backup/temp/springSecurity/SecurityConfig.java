package zebra.springsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ImportResource({ "classpath:zebra/resource/spring-security.xml" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	public SecurityConfig() {
		super();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
//			.withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
//			.and()
//			.withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
//			.and()
			.withUser("admdustin").password(passwordEncoder().encode("admdustin")).roles("ADMIN");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/anonymous*").anonymous()
			.antMatchers("/login*").permitAll()
			.anyRequest().authenticated()
			.and().formLogin().loginPage("/login/userLogin")
			.loginProcessingUrl("/login/login")
			.defaultSuccessUrl("/index", true)
//			.failureHandler(authenticationFailureHandler())
			.and()
			.logout()
			.logoutUrl("/login/logout")
			.deleteCookies("JSESSIONID");
//			.logoutSuccessHandler(logoutSuccessHandler());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}