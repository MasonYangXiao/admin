package com.admin.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import com.admin.common.response.BaseResponse;
import com.admin.security.CaptchaFilterConfig;
import com.admin.security.UrlSecurityInterceptor;
import com.admin.security.UserDetailService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mason
 *
 */
@Configuration
@EnableWebSecurity //注释掉可以既能享受到springboot的自动配置又能覆盖某些配置
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected UserDetailService userDetailService;
    @Autowired
    private CaptchaFilterConfig captchaFilterConfig;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Value("${remember.key}")
    private String key = "jonsychen@hotmail.com";


    @Bean
    public UrlSecurityInterceptor urlSecurityInterceptor() {
        return new UrlSecurityInterceptor();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
    	return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	  http
          // 关闭csrf防护
          .csrf().disable()
          .headers().frameOptions().disable()
          .and()
          .rememberMe()
          .userDetailsService(userDetailService)
          .key(key)
          .useSecureCookie(false)
          .alwaysRemember(true);
    	  //自定义url访问权限处理
    	  http.addFilterAt(urlSecurityInterceptor(), FilterSecurityInterceptor.class);//处理自定义的权限
          //登录处理
    	  http.addFilterBefore(captchaFilterConfig, UsernamePasswordAuthenticationFilter.class)
          .formLogin()
          .loginProcessingUrl("/login")
          .loginPage("/to-login")
          .defaultSuccessUrl("/")
          .successHandler(new AuthenticationSuccessHandler())
          .failureHandler(new LoginFailureHandlerConfig())
          //.permitAll()
          .and()
          //登出处理
          .logout()
          .logoutUrl("/logout")
          .logoutSuccessHandler(new LogoutSuccessHandler())
          .logoutSuccessUrl("/to-login");
    	
    	  http.exceptionHandling()
    	  .authenticationEntryPoint(new MyAuthenticationEntryPoint())
    	  .accessDeniedHandler(new MyAccessDeniedHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailService);
        auth.authenticationProvider(provider);

    }

    @Bean
    protected AccessDecisionManager accessDecisionManager() {
        RoleVoter roleVoter = new RoleVoter();
        roleVoter.setRolePrefix("");
        List voters = new ArrayList<>();
        voters.add(roleVoter);
        AccessDecisionManager accessDecisionManager = new AffirmativeBased(voters);
        return accessDecisionManager;
    }
    
  protected boolean isAjax(HttpServletRequest request) {
  return StringUtils.isNotBlank(request.getHeader("x-requested-with"));
}
    //由于springboot默认会将所要的servlet,filter,listenr等标准servlet组件自动加入到servlet的过滤器链中，自定义的UrlSecurityInterceptor只希望加入security的过滤器链，中，所以这里配置不向servlet容器中注册
    @Bean
    public FilterRegistrationBean registration(UrlSecurityInterceptor filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
  @Override
  public void configure(WebSecurity web) throws Exception {
      web.ignoring().antMatchers("/**/*.js", "/**/*.js.map", "/**/*.ts", "/**/*.css", "/**/*.css.map", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.fco", "/**/*.woff", "/**/*.woff2", "/**/*.font", "/**/*.svg", "/**/*.ttf", "/**/*.pdf","/*.ico", "/admin/api/**", "/404", "/401","/403", "/error");
  }
    
    private class LoginFailureHandlerConfig implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
            System.out.println("用户名或密码错误");
            //响应json
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = httpServletResponse.getWriter();
            out.print("{\"code\":\"500\",\"msg\":\"用户名或密码错误\"}");
            out.flush();
            out.close();
        }
    }
  private class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

      @Override
      public void onAuthenticationSuccess(HttpServletRequest request,
                                          HttpServletResponse response, Authentication authentication)
              throws ServletException, IOException {

          clearAuthenticationAttributes(request);
          if (!isAjax(request)) {
              super.onAuthenticationSuccess(request, response, authentication);
          }else{
        	  response.setCharacterEncoding("UTF-8");
        	  response.setContentType("application/json; charset=utf-8");
          	  response.getWriter().println("{\"code\":\"0\",\"msg\":\"登录成功\",\"url\":\"/index\"}");
          }
      }
  }
  private class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

      @Override
      public void onLogoutSuccess(HttpServletRequest request,
                                  HttpServletResponse response, Authentication authentication)
              throws IOException, ServletException {
          if (!isAjax(request)) {
              super.onLogoutSuccess(request, response, authentication);
          }else{
        	  response.setCharacterEncoding("UTF-8");
        	  response.setContentType("application/json; charset=utf-8");
          	  response.getWriter().println("{\"code\":\"0\",\"msg\":\"退出成功\",\"url\":\"/index\"}");
          }
      }
  }

  private class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

      @Override
      public void commence(HttpServletRequest request,
                           HttpServletResponse response,
                           AuthenticationException authException) throws IOException {
          response.setCharacterEncoding("utf-8");
          if (isAjax(request)) {
        	  response.setContentType("application/json; charset=utf-8");
          	  response.getWriter().println("{\"code\":\"500\",\"msg\":\"请登录\"}");
          } else {
              response.sendRedirect("/to-login");
          }

      }
  }

  private class MyAccessDeniedHandler implements AccessDeniedHandler {
      @Override
      public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
          response.setCharacterEncoding("utf-8");
          if (isAjax(request)) {
        	  response.setCharacterEncoding("UTF-8");
        	  response.setContentType("application/json; charset=utf-8");
          	  response.getWriter().println("{\"code\":\"403\",\"msg\":\"无权访问\"}");
          } else {
              response.sendRedirect("/403");
          }

      }
  }

    public static void main(String[] args) {
    	  PasswordEncoder ddds = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    	String ddd = ddds.encode("123456");
    	System.out.println(ddd);
    	System.out.println(ddds.matches("123456", ddd));
	}
}
