package com.truquinio.estancias.controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// import jakarta.persistence.Id;

import com.truquinio.estancias.entidades.Familia;
import com.truquinio.estancias.exception.MiException;
import com.truquinio.estancias.servicios.FamiliaServicio;

@Controller // @Controller = Indica al framework de Spring que la clase es tipo controladora
@RequestMapping("/familias") // @RequestMapping = Configura la URL de clase controladora
public class FamiliaControlador {

  @Autowired
  FamiliaServicio familiaServicio;

  @GetMapping("") // (/familia lleva al indexFamilia)
  public String index() {
    return "familia_index.html";
  }

  /*
   * MÉTODO REGISTRAR FAMILIAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @GetMapping("/registro") // @GetMapping = Se accede a travez de una operación GET de HTTP
  public String registro() { // localhost:8080/familias/registro

    return "familia_registra.html"; // Retorna un String "archivo.html" creado dentro de templates
  }

  @PostMapping("/registrar") // @PostMapping("/registro") = Se accede al action de HTML a travez de POST
  public String registrar(@RequestParam String nombre, @RequestParam String email,
      @RequestParam String password, @RequestParam String password2,
      ModelMap modelo) {
    // Método registro = Recibe parámetro llamado igual q attr name de INPUT de HTML
    // @RequestParam = Indica a controlador q param viaja x URL y se ejecuta cuando
    // llene form
    // ModelMap = Inserta información q vamos a mostrar en interface del usuario

    try {

      // Llamo al método crearFamilia, de la clase familiaServicio
      familiaServicio.crearFamilia(nombre, email, password, password2);

      // En caso de cargar todo OK, el usuario ve el sig. mensaje de llave "exito"
      modelo.put("exito", "La familia fue registrada correctamente!");

      return "index.html"; // Si todo OK, nos envía al index

    } catch (MiException ex) {

      // En caso de NO cargar todo OK, el usuario ve el sig. mensaje de llave "error"
      modelo.put("error", ex.getMessage());
      modelo.put("nombre", nombre); // Guarda los valores ingresados p/ no volver a cargarlos
      modelo.put("email", email);

      return "redirect:/registro"; // Si hay error, vuelve a cargar la página del form
    }
  }

  /*
   * MÉTODO LISTAR FAMILIAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @GetMapping("/lista")
  public String listar(ModelMap modelo) {

    List<Familia> listaFamilias = familiaServicio.listarFamilias();

    modelo.addAttribute("listaFamilias", listaFamilias);

    return "familia_list.html";
  }

  /*
   * MÉTODO MODIFICAR FAMILIAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @GetMapping("/modificar/{id}") // GET = Para trabajar con el HTML
  public String modificar(@PathVariable String id, String nombre, String email, String password, String password2,
      ModelMap modelo) {

    modelo.put("familia", familiaServicio.getOne(id));

    return "familia_modificar.html";
  }

  // LO USO PARA EL BOTÓN ACTUALIZAR
  @PostMapping("/modificar/{id}") // POST Modifica la base de datos
  public String modificado(@PathVariable String id, String nombre, String email, String password, String password2,
      ModelMap modelo) {

    try {

      familiaServicio.modificarFamilia(id, nombre, email, password, password2);

      return "redirect:/familias/lista";

    } catch (MiException ex) {

      modelo.put("error", ex.getMessage());

      return "familia_modificar.html";
    }
  }

  /*
   * MÉTODO ELIMINAR FAMILIA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @PostMapping("/eliminar/{id}") // @PathVariable = Obtiene id desde URL
  public String eliminar(@PathVariable String id, ModelMap modelo) {
    try {
      familiaServicio.eliminarFamilias(id);
      ;

    } catch (MiException e) {
      modelo.put("error", e.getMessage());
    }
    return "redirect:/familias/lista";
  }
}
/*
 * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
 * necesarias para operar con la vista del usuario diseñada para la gestión de
 * familias (guardar/modificar datos de la familia, listar familias, eliminar).
 */