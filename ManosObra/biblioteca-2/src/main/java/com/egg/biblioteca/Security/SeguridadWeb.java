package com.egg.biblioteca.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.egg.biblioteca.servicios.UsuarioServicio;

@Configuration
@EnableWebSecurity
public class SeguridadWeb {

  @Autowired
  public UsuarioServicio usuarioServicio;

  /*
   * @Bean
   * public UserDetailsService userDetailsService(BCryptPasswordEncoder
   * bCryptPasswordEncoder) {
   * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
   * manager.createUser(User.withUsername("email")
   * .password(bCryptPasswordEncoder.encode("password"))
   * .roles("USER")
   * .build());
   * manager.createUser(User.withUsername("email")
   * .password(bCryptPasswordEncoder.encode("password"))
   * .roles("USER", "ADMIN")
   * .build());
   * return manager;
   * }
   */

  /*
   * @Bean
   * public AuthenticationManager authenticationManager(HttpSecurity http,
   * BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService
   * userDetailService)
   * throws Exception {
   * return http.getSharedObject(AuthenticationManagerBuilder.class)
   * .userDetailsService(usuarioServicio)
   * .passwordEncoder(new BCryptPasswordEncoder())
   * .build();
   * }
   */

  /*
   * CONFIGURAR AUTENTICACIÓN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

    // userDetailsService = Autentico el usuario cuando se registra
    auth.userDetailsService(usuarioServicio)
        .passwordEncoder(new BCryptPasswordEncoder());
    // Una vez autenticado, se encripta contraseña = BCryptPasswordEncoder

  }

  /*
   * CONFIGURAR SEGURIDAD HTTP >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // @Override
    // protected void configure(HttpSecurity http) throws Exception {

    // Recursos dentro de carpetas CSS/JS/IMG son accesibles a cualquier usuario
    http
        .authorizeHttpRequests()
        // Condiciono toda la clase ADMIN para que solo ADMIN pueda acceder
        .requestMatchers("/admin/*").hasRole("ADMIN") 
        .requestMatchers("/css/*", "/js/*", "/img/*", "/**")
        .permitAll()

        /*
         * CONFIGURACIÓN LOGIN para loguearnos (Form login)
         */
        .and().formLogin()
        .loginPage("/login") // URL del formulario LOGIN

        // No es necesario controlador para /logincheck
        .loginProcessingUrl("/logincheck") // URL para autenticar un usuario desde SpringSecurity
        .usernameParameter("email") // Credencial nombre de usuario
        .passwordParameter("password") // Credencial contraseña
        .defaultSuccessUrl("/inicio") // Si por DEFAULT se genera un LOGIN OK va a /inicio.html
        .permitAll()

        /*
         * CONFIGURACIÓN LOGOUT para desloguearnos
         */
        .and().logout()
        // No es necesario controlador para /logout
        .logoutUrl("/logout") // Cuando Usuario cierre sesión, retorna a /LOGOUT
        .logoutSuccessUrl("/login") // En caso que se cierre OK sesión, retorna al /login.html
        .permitAll()

        .and().csrf()
        .disable();

    return http.build();
  }
}
