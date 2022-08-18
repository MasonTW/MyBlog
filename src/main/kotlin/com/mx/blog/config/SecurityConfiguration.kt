package com.mx.blog.config//package com.mx.blog.config;
//
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.builders.WebSecurity
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
//import javax.sql.DataSource
//
//
//@Configuration
//@EnableMethodSecurity(prePostEnabled = true)
//class SecurityConfiguration(private val ) : WebSecurityConfigurerAdapter() {
//
//
//    override fun configure(auth: AuthenticationManagerBuilder?) {
//        auth!!.jdbcAuthentication().dataSource(dataSource).passwordEncoder(BCryptPasswordEncoder())
//            .withUser("user1").password(BCryptPasswordEncoder().encode("123")).roles("ADMIN")
//            .and()
//            .withUser("user2")
//    }
//
//    override fun configure(web: WebSecurity?) {
//        super.configure(web)
//    }
//
//    override fun configure(http: HttpSecurity) {
//        http.authorizeRequests()
//            .antMatchers("/").permitAll()
//            .antMatchers("/article/*").permitAll()
//            .antMatchers("/article/{articleId}").hasRole("ADMIN")
//        //每个controller都要写？
//
//
//    }
//
//    override fun userDetailsService(): UserDetailsService {
//        return cuuse
//    }
//}
//
//class cuuse  impl UserDetailsService(){}
