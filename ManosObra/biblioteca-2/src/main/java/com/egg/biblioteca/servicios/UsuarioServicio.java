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
import org.springframework.stereotype.Service;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.UsuarioRepositorio;

import jakarta.transaction.Transactional;

@Service // @Service = Construir una clase Servicio que conecta a varios repositorios
public class UsuarioServicio implements UserDetailsService {
  // implements UserDetailsService = Para que implemente una interfaz especial

  @Autowired // @Autowired = Inyección de dependencias, vincula al JPA
  private UsuarioRepositorio usuarioRepositorio;

  @Transactional // @Transactional = Si falla modificación en database hace rollback, no modifica
  // Recibe por parámetros, desde formulario, los datos para setear los attr.
  public void registrar(String nombre, String email, String password, String password2) throws MiException {

    // Método REGISTRAR, llama a método VALIDAR
    validar(nombre, email, password, password2);

    // Instancio objeto "usuario" de clase USUARIO
    Usuario usuario = new Usuario();

    // Seteo Nombre, Email, Password
    usuario.setNombre(nombre);
    usuario.setEmail(email);
    usuario.setPassword(password);

    // Se da ROL de USER x defecto, para que tenga privilegios comunes y no ADMIN
    usuario.setRol(Rol.USER);

    // Persisto / Guardo usuario en base de datos
    usuarioRepositorio.save(usuario);
  }

  // VALIDAR que todos los parámetros ingresados no sean nulos o estén vacíos.-
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

  // Método Abstracto por implements UserDetailsService en class UsuarioServicio
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    
    Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

    if (usuario != null){

      // Creo la LISTA de PERMISOS de usuario
      List<GrantedAuthority> listaPermisos = new ArrayList();

      // Instancio objeto "permisos" y concateno el ROL del usuario
      GrantedAuthority permisos = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()); // ROLE_USER (el rol viene del ENUM)
      // GrantedAuthority y SimpleGrantedAuthority = Otorgan permisos de usuario

      // Agrego a la ListaPermisos, el objeto "permisos"
      listaPermisos.add(permisos);

      // Retorno un NUEVO USUARIO y me traigo Email, Pass y lista de Permisos para crearlo.
      return new User(usuario.getEmail(), usuario.getPassword(), listaPermisos);
    
    } else {

      return null;
    }
  }
}