package it.uniroma3.siw.siweventsservice.auth;

import it.uniroma3.siw.siweventsservice.auth.models.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

//https://www.baeldung.com/spring-security-login
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource datasource;

	@Override
	protected void configure (HttpSecurity http) throws Exception {
		http.authorizeRequests() // authorization paragraph: qui definiamo chi può accedere a cosa
				.antMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/css/**", "/images/**", "/public/**").permitAll() 	//chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
				.antMatchers(HttpMethod.POST, "/login", "/register").permitAll()
				.antMatchers(HttpMethod.GET, "/protected/**").hasAnyAuthority(Credentials.ADMIN_ROLE,Credentials.DEFAULT_ROLE)// chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
				.antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE) 							// solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
				.antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(Credentials.ADMIN_ROLE)
				.anyRequest().authenticated() // tutti gli utenti autenticati possono accere alle pagine rimanenti
										// login paragraph: qui definiamo come è gestita l'autenticazione
				.and().formLogin() 		// usiamo il protocollo formlogin
				.loginPage("/login") 	// la pagina di login si trova a /login
				.defaultSuccessUrl("/default", false) // se il login ha successo, si viene rediretti al path /default
				// logout paragraph: qui definiamo il logout
				.and()
				.logout()
				.logoutUrl("/logout") 		// il logout è attivato con una richiesta GET a "/logout"
				.logoutSuccessUrl("/") // in caso di successo, si viene reindirizzati alla /index page
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.clearAuthentication(true).permitAll();
	}

	/**
	 * This method provides the SQL queries to get username and password.
	 */
	@Override
	public void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
				.dataSource(this.datasource) //use the autowired datasource to access the saved credentials
				//retrieve username and role
				.authoritiesByUsernameQuery("SELECT username, role FROM event_credentials_table WHERE username=?")
				//retrieve username, password and a bool specifying whether the user is enabled (always enabled for us)
				.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM event_credentials_table WHERE username=?");
	}

	/**
	 * This method defines a "passwordEncoder" Component (a Bean in the EE slang).
	 * The passwordEncoder Bean is used to encrypt and decrpyt the Credentials passwords.
	 */
	@Bean
	PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}

}