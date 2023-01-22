package com.egg.biblioteca.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.UsuarioServicio;

@Controller // @Controller = Indica al framework de Spring que la clase es tipo controladora
@RequestMapping("/admin") // @RequestMapping = Configura la URL de clase controladora
public class AdminControlador {

  @Autowired // @Autowired = Inyección de dependencias, vincula al JPA
  private UsuarioServicio usuarioServicio;

  @GetMapping("/dashboard") // @GetMapping = Se accede a travez de una operación GET de HTTP
  public String panelAdministrativo() {
    return "panel.html"; // localhost:8080/admin/dashboard
  }

  /*
   * MÉTODO LISTAR USUARIOS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @GetMapping("/usuarios")
  public String listar(ModelMap modelo) {
    List<Usuario> usuarios = usuarioServicio.listarUsuarios();
    modelo.addAttribute("usuarios", usuarios);

    return "usuario_list";
  }

  /*
   * MÉTODO CAMBIAR ROL >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @GetMapping("/modificarRol/{id}")
  public String cambiarRol(@PathVariable String id) {
    usuarioServicio.cambiarRol(id);

    return "redirect:/admin/usuarios";
  }

  /*
   * MÉTODO ELIMINAR AUTOR >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @GetMapping("/eliminar/{id}")
  public String eliminar(@PathVariable String id, ModelMap modelo) {

    try {
      usuarioServicio.eliminarUsuario(id);;

    } catch (MiException e) {
      modelo.put("error", e.getMessage());
    }
    return "redirect:/admin/usuarios";
  }
}