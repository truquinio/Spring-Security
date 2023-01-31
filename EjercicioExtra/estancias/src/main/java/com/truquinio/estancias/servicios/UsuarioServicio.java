package com.truquinio.estancias.servicios;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.truquinio.estancias.entidades.Usuario;
import com.truquinio.estancias.enumeraciones.Rol;
import com.truquinio.estancias.exception.MiException;
import com.truquinio.estancias.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService {

  @Autowired(required = false)
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  ValidacionServicio validacionServicio;

  /*
   * MÉTODO CREAR USUARIOS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  public void crearUsuario(String alias, String email, String password, String password2) throws MiException {

    validacionServicio.validarAlias(alias);
    validacionServicio.validarEmail(email);
    validacionServicio.validarPassword(password);
    validacionServicio.validarPassword2(password, password2);

    Usuario usuario = new Usuario();

    usuario.setAlias(alias);
    usuario.setEmail(email);
    usuario.setClave(new BCryptPasswordEncoder().encode(password));
    usuario.setRol(Rol.USER);

    // persisto - guardo en BD
    usuarioRepositorio.save(usuario);
  }

  /*
   * MÉTODO LISTAR USUARIOS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  public List<Usuario> listarUsuarios() {

    List<Usuario> listaUsuarios = new ArrayList<>();

    // Encuentra reservas dentro de repositorio, los mete en arraylist listaReservas
    listaUsuarios = usuarioRepositorio.findAll();

    return listaUsuarios;
  }

  /*
   * MÉTODO CARGAR POR USERNAME >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

    if (usuario != null) {

      List<GrantedAuthority> permisos = new ArrayList<>();

      GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" +
          usuario.getRol().toString());

      permisos.add(p);

      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

      HttpSession session = attr.getRequest().getSession(true);

      session.setAttribute("usuariosession", usuario);

      return new User(usuario.getEmail(), usuario.getClave(), permisos);
    } else {
      return null;
    }

  }
}

/*
 * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
 * necesarias para administrar usuarios (alta de usuario, consultas, y baja o
 * eliminación).
 */
