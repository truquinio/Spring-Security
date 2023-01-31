package com.truquinio.estancias.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.truquinio.estancias.entidades.Usuario;
import com.truquinio.estancias.exception.MiException;
import com.truquinio.estancias.servicios.UsuarioServicio;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

  @Autowired
  UsuarioServicio usuarioServicio;

  @GetMapping("")
  public String index() {
    return "index_usuarios.html";
  }

  @GetMapping("/registro")
  public String registro() {
    return "registro_usuarios.html";
  }

  @PostMapping("/registrar")
  public String registrar(@RequestParam String alias, @RequestParam String email,
      @RequestParam String clave1, @RequestParam String clave2,
      ModelMap modelo) {
    try {

      usuarioServicio.crearUsuario(alias, email, clave1, clave2);

      modelo.put("exito", "El usuario fue creado correctamente!");

      return "index.html";
    } catch (MiException ex) {

      modelo.put("error", ex.getMessage());
      modelo.put("nombre", alias);  // Guarda los valores ingresados para no volver a cargarlos
      modelo.put("email", email);

      return "redirect:/registro";
    }
  }

  @GetMapping("/lista")
  public String listar(ModelMap modelo) {

    List<Usuario> usuarios = usuarioServicio.listarUsuarios();
    modelo.addAttribute("usuarios", usuarios);

    return "usuarios_list.html";
  }
}

/*
 * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
 * necesarias para operar con la vista del usuario diseñada para la gestión de
 * usuarios (dar de alta un usuario, cambiar clave, listar usuarios registrados,
 * dar de baja un usuario).
 */