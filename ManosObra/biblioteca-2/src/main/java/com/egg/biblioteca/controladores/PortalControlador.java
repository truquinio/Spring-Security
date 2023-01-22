package com.egg.biblioteca.controladores;

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

import jakarta.servlet.http.HttpSession;

@Controller // @Controller = Indica al framework de Spring q clase es de tipo controladora
@RequestMapping("/") // @RequestMapping("/") = Configura URL q va a escuchar a clase controladora
public class PortalControlador { // localhost:8080/

  @Autowired // @Autowired = Inyección de dependencias, vincula al JPA
  private UsuarioServicio usuarioServicio;

  /*
   * INDEX >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @GetMapping("/") // @GetMapping("/") = Se accede a travez de una operación GET de HTTP
  public String index() { // localhost:8080/
    // Index = Primer método que se va a ejecutar cuando abramos la app en localhost

    return "index.html"; // Retorna un String "archivo.html" q debo crear dentro de resources/templates
  }

  /*
   * MÉTODO REGISTRAR >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  // CONTROLADOR q envía todos los datos de FORMULARIO por método GET
  @GetMapping("/registrar")
  public String registrar() {

    return "registro.html";
  }

  /*
   * MÉTODO REGISTRO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  // CONTROLADOR q recibe todos los datos de FORMULARIO por método POST
  @PostMapping("/registro")
  public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
      String password2, ModelMap modelo, MultipartFile archivo) {
    // Recibe todos los parámetros del formulario / MODELmap = interactúa con HTML
    // MultipartFile archivo = Recibe la img

    try {
      usuarioServicio.registrar(archivo, nombre, email, password, password2);

      // MSJ que muestra cuando se registra OK
      modelo.put("exito", "¡Usuario registrado correctamente!");

      // Si registra OK, dirige al INDEX
      return "login.html";

    } catch (MiException e) {

      // MSJ que muestra cuando NO se registra OK
      modelo.put("error", e.getMessage());

      // Para GUARDAR datos ingresados en caso de error en FORMULARIO
      modelo.put("nombre", nombre);
      modelo.put("email", email);

      // Si NO registra OK, vuelve a pedir REGISTRO
      return "registro.html";
    }
  }

  /*
   * MÉTODO LOGIN >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @GetMapping("/login")

  // @RequestParam required: false = En caso q formulario LOGIN pueda tener o no
  // error
  public String login(@RequestParam(required = false) String error, ModelMap modelo) {
    // MODELmap = nos permite imprimir mjs o interactúar con el HTML

    // Si nos llega un error
    if (error != null) {

      modelo.put("error", "¡Usuario o contraseña inválidos!");
    }
    return "login.html";
  }

  /*
   * MÉTODO INICIO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  // CONTROLADOR para devolver una vista al LOGIN

  // @PreAuthorize = Limito a q solo accedan a inicio.html usuarios logueados
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @GetMapping("/inicio")
  public String inicio(HttpSession session) {

    try {

      // Creo Usuario que recibe datos de sesión / usuariosession desde clase
      // UsuarioServicio
      Usuario logueado = (Usuario) session.getAttribute("usuariosession");

      // Valido si el rol de Usuario, en formato string, es = a ADMIN
      if (logueado.getRol().toString().equals("ADMIN")) {

        // Redirecciono al Dashboard /panel.html
        return "redirect:/admin/dashboard";
      }

      // Si desde SeguridadWeb el LOGIN es OK, nos lleva a URL /inicio.html
      return "inicio.html";

    } catch (Exception e) {
      // return "inicio.html";

      // En caso de NO estar logueado, lleva al LOGIN
      return "redirect:/login";
    }
  }

  /*
   * MÉTODO PERFIL >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   * Recupero datos de sesión del usuario
   */

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @GetMapping("/perfil")
  public String perfil(ModelMap modelo, HttpSession session) {

    // Instancio un usuario y guardo todos los datos de la sesión
    Usuario usuario = (Usuario) session.getAttribute("usuariosession");

    // Con modelo pongo los datos para q se autocomplete el form
    modelo.put("usuario", usuario);

    return "usuario_modificar.html";
  }

  /*
   * MÉTODO ACTUALIZAR USUARIO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
  @PostMapping("/perfil/{id}")
  public String actualizar(MultipartFile archivo, @PathVariable String id, @RequestParam String nombre,
      @RequestParam String email,
      @RequestParam String password, @RequestParam String password2, ModelMap modelo) {

    try {
      usuarioServicio.actualizar(archivo, id, nombre, email, password, password2);

      modelo.put("exito", "Usuario actualizado correctamente!");

      return "inicio.html";
      
    } catch (MiException ex) {

      modelo.put("error", ex.getMessage());
      modelo.put("nombre", nombre);
      modelo.put("email", email);

      return "usuario_modificar.html";
    }
  }
}