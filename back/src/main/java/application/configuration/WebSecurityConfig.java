package application.configuration;

import application.configuration.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                .antMatchers("/api/points").permitAll()
//                .antMatchers("/api/points/*").permitAll()
//                .antMatchers("/api/user").permitAll()
//                .antMatchers("/api/user/*").permitAll()
//                .antMatchers("/error").permitAll() // TODO: переделать
                .antMatchers("/api/user/*").permitAll()
                .antMatchers("/api/game/*").permitAll()
                .antMatchers("/api/forum/*").permitAll()
                .antMatchers("/api/shop/*").permitAll()
                //.antMatchers("/api/user/register").permitAll()
                .anyRequest().authenticated() //требуем авторизацию
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/*", "/js/**", "/css/**");
    }

}