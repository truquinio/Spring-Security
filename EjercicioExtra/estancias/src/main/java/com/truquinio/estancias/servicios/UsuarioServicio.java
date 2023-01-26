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
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.truquinio.estancias.entidades.Usuario;
import com.truquinio.estancias.repositorios.UsuarioRepositorio;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioServicio implements UserDetailsService {
  @Autowired(required = false)
  private UsuarioRepositorio usuarioRepositorio;

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
