package com.egg.biblioteca.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
   * MÉTODO MODIFICAR USUARIOS (desde tabla) >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @GetMapping("/modificar/{id}") // GET = Para trabajar con el HTML
  public String actualizaUser(@PathVariable String id, ModelMap modelo) {

    modelo.put("usuario", usuarioServicio.getOne(id));

    List<Usuario> listaUsuarios = usuarioServicio.listarUsuarios();

    modelo.addAttribute("usuarios", listaUsuarios);

    return "usuario_modificar.html";
  }

  // LO USO PARA EL BOTÓN ACTUALIZAR
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PostMapping("/modificado/{id}") // POST Modifica la base de datos
  public String actualizado(MultipartFile archivo, @PathVariable String id,
      String nombre, String email, String password,
      String password2, ModelMap modelo) {

    try {
      List<Usuario> listaUsuarios = usuarioServicio.listarUsuarios();

      modelo.addAttribute("usuarios", listaUsuarios);

      usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);

      return "redirect:/admin/usuarios";

    } catch (MiException ex) {

      List<Usuario> listaUsuarios = usuarioServicio.listarUsuarios();

      modelo.put("error", ex.getMessage());

      modelo.addAttribute("usuarios", listaUsuarios);

      return "usuario_modificar.html";
    }
  }

  /*
   * MÉTODO ELIMINAR USUARIO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @PostMapping("/eliminar/{id}")
  public String eliminar(@PathVariable String id, ModelMap modelo) {

    try {
      usuarioServicio.eliminarUsuario(id);

    } catch (Exception e) {
      modelo.put("error", e.getMessage());
    }
    return "redirect:/admin/usuarios";
  }
}