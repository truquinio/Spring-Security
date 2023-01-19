package com.egg.biblioteca.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Service // @Service = Construir una clase Servicio que conecta a varios repositorios
// implements UserDetailsService = Para que implemente una interfaz especial
public class UsuarioServicio implements UserDetailsService {

  @Autowired // @Autowired = Inyección de dependencias, vincula al JPA
  // UsuarioRepositorio = @Query("SELECT u FROM Usuario u WHERE u.email = :email")
  private UsuarioRepositorio usuarioRepositorio;

  /*
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @Transactional // @Transactional = Si falla modificación en database hace rollback, no modifica
  // Recibe por parámetros, desde formulario, los datos para setear los attr.
  public void registrar(String nombre, String email, String password, String password2) throws MiException {

    // Método REGISTRAR, llama a método VALIDAR
    validar(nombre, email, password, password2);

    // Instancio objeto "usuario" de clase USUARIO
    Usuario usuario = new Usuario();

    // Seteo Nombre, Email
    usuario.setNombre(nombre);
    usuario.setEmail(email);

    // Seteo el password, pero la encripto con BCryptPasswordEncoder
    usuario.setPassword(new BCryptPasswordEncoder().encode(password));

    // Se da ROL de USER x defecto, para que tenga privilegios comunes y no ADMIN
    usuario.setRol(Rol.USER);

    // Persisto / Guardo usuario en base de datos
    usuarioRepositorio.save(usuario);
  }

  /*
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  // VALIDO que todos los parámetros ingresados no sean nulos o estén vacíos.-
  private void validar(String nombre, String email, String password, String password2) throws MiException {

    if (nombre.isEmpty() || nombre == null) {
      throw new MiException("El nombre no puede estar nulo/vacío");
    }
    if (email.isEmpty() || email == null) {
      throw new MiException("El e-mail no puede estar nulo/vacío");
    }
    if (password.isEmpty() || password == null || password.length() <= 5) {
      throw new MiException("La contraseña no puede estar nulo/vacío y debe tener más de 5 dígitos");
    }
    if (!password.equals(password2)) {
      throw new MiException("Las contraseñas ingresadas deben ser iguales");
    }
  }

  /*
   * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  // Sobrescribo método Abstracto x implements UserDetailsService en class UsuarioServicio
  @Override
  // loadUserByUsername = Carga usuario por nombre de usuario (email)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    // Guardo el mail del usuarioRepositorio en un objeto "usuario" de clase USUARIO
    Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

    if (usuario != null) {

      // Creo la LISTA de PERMISOS de usuario
      List<GrantedAuthority> listaPermisos = new ArrayList<>();

      // GrantedAuthority / SimpleGrantedAuthority = Otorgan permisos de usuario
      // Instancio objeto "permisos" y concateno el ROL del usuario
      // ROLE_USER (rol viene del ENUM)
      GrantedAuthority permisos = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

      // Agrego a la ListaPermisos, el objeto "permisos"
      listaPermisos.add(permisos);

      // Retorno un NUEVO USUARIO y traigo Email, Pass y listaPermisos para crearlo
      return new User(usuario.getEmail(), usuario.getPassword(), listaPermisos);

    } else {

      return null;
    }
  }
}