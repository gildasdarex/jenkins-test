package com.pej;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import com.pej.services.MyAccessDecisionManager;
import com.pej.services.MySecurityMetadataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D O N A T I E N on 28/12/2016.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private MySecurityMetadataSource mySecurityMetadataSource;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/vendors/**","/build/**","/script/**","/img/**","/fonts/**").permitAll()
                .antMatchers("/pej/lots").hasAuthority("ADMIN")
                .antMatchers("/pej/lots/add").hasAuthority("ADMIN")
                .antMatchers("/pej/agents").hasAuthority("ADMIN")
                .antMatchers("/pej/agents/add").hasAuthority("ADMIN")
                .antMatchers("/pej/antennes").hasAuthority("ADMIN")
                .antMatchers("/pej/antennes/add").hasAuthority("ADMIN")
                .antMatchers("/pej/cooperatives").hasAuthority("ADMIN")
                .antMatchers("/pej/cooperatives/add").hasAuthority("ADMIN")
                .antMatchers("/pej/statutcandidats").hasAuthority("ADMIN")
                .antMatchers("/pej/statutcandidats/add").hasAuthority("ADMIN")
                .antMatchers("/pej/cabinets").hasAuthority("ADMIN")
                .antMatchers("/pej/cabinets/add").hasAuthority("ADMIN")
                .antMatchers("/pej/typeformations").hasAuthority("ADMIN")
                .antMatchers("/pej/typeformations/add").hasAuthority("ADMIN")
                .antMatchers("/pej/formateurs").hasAuthority("ADMIN")
                .antMatchers("/pej/formateurs/add").hasAuthority("ADMIN")
                .antMatchers("/pej/candidats").hasAuthority("ADMIN")
                .antMatchers("/pej/candidats/add").hasAuthority("ADMIN")
                .antMatchers("/pej/candidats/tirage").hasAuthority("ADMIN")
                .antMatchers("/pej/usermamagement").hasAuthority("ADMIN")
                .antMatchers("/pej/users").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/pej/formations", true)
                .loginPage("/pej/login")
                .permitAll()
                .and()
                //.logout()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/pej/logout")).logoutSuccessUrl("/pej/login")
                .permitAll();

        http.exceptionHandling().accessDeniedPage("/pej/403");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
        auth.inMemoryAuthentication()
                .withUser("pejadmin").password("pejadmin?000Dddd@123").authorities("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**","/fonts/**","/vendors/**", "/dandelion-assets/*");
    }


    @Bean(name = "accessDecisionManager")
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
              decisionVoters.add(new RoleVoter());
               decisionVoters.add(new AuthenticatedVoter());
                 decisionVoters.add(webExpressionVoter());
               MyAccessDecisionManager accessDecisionManager =  new MyAccessDecisionManager(decisionVoters);
               return accessDecisionManager;

    }


    @Bean(name = "authenticationManager")
     @Override
    public AuthenticationManager authenticationManagerBean(){

         AuthenticationManager authenticationManager = null;
         try {
                authenticationManager = super.authenticationManagerBean();
            } catch (Exception e) {
               e.printStackTrace();
           }
         return authenticationManager;

    }



    @Bean(name = "expressionHandler")
     public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
                 DefaultWebSecurityExpressionHandler webSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
                return webSecurityExpressionHandler;

    }



    @Bean(name = "expressionVoter")
     public WebExpressionVoter webExpressionVoter() {
                WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
                 webExpressionVoter.setExpressionHandler(webSecurityExpressionHandler());
                return webExpressionVoter;
            }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
        accessDeniedHandler.setErrorPage("/pej/accessdenied");
        return accessDeniedHandler;
    }
    @Bean
    UsernamePasswordAuthenticationFilter MyUsernamePasswordAuthenticationFilter() {
        UsernamePasswordAuthenticationFilter myUsernamePasswordAuthenticationFilter = new UsernamePasswordAuthenticationFilter();
        myUsernamePasswordAuthenticationFilter.setPostOnly(true);
        myUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        myUsernamePasswordAuthenticationFilter.setUsernameParameter("name_key");
        myUsernamePasswordAuthenticationFilter.setPasswordParameter("pwd_key");
        myUsernamePasswordAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/pej/login", "POST"));
        myUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(simpleUrlAuthenticationFailureHandler());
        return myUsernamePasswordAuthenticationFilter;
    }
    @Bean(name = "failureHandler")
    public SimpleUrlAuthenticationFailureHandler simpleUrlAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/pej/login?error");
    }


}
