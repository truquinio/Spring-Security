package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // @Controller = Indica al framework de Spring que la clase es tipo controladora
@RequestMapping("/editorial") // @RequestMapping("/editorial") = Configura la URL de clase controladora
public class EditorialControlador { // localhost:8080/autor

  @Autowired // @Autowired = Inyección de dependencias, vincula al JPA
  private EditorialServicio editorialServicio;

  /*
   * MÉTODO CREAR EDITORIALES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @GetMapping("/registrar") // @GetMapping = Se accede a travez de una operación GET de HTTP
  public String registrar() { // localhost:8080/editorial/registrar

    return "editorial_form.html"; // Retorna un String "archivo.html" que debo crear dentro de resources/templates
  }

  @PostMapping("/registro") // @PostMapping = Se accede al action de HTML a travez de un método POST
  public String registro(@RequestParam String nombre, ModelMap modelo) {
    // Método registro = Recibe parámetro llamado igual q atributo name de INPUT de HTML
    // @RequestParam = Indica a controlador q parámetro viaja en la URL y se ejecuta cuando llene formulario
    // ModelMap = Inserta información q vamos a mostrar en interface del usuario

    try {
      editorialServicio.crearEditorial(nombre);

      // Si carga todo correctamente, usuario ve mensaje de llave "exito"
      modelo.put("exito", "La Editorial fue registrada correctamente!");

    } catch (MiException ex) {

      // Si NO carga todo correctamente, usuario ve mensaje de llave "error"
      modelo.put("error", ex.getMessage());

      return "editorial_form.html"; // Si hay error, vuelve a cargar página de formulario
    }

    return "index.html"; // Si no hay error, muestra el index
  }

  /*
   * MÉTODO LISTAR EDITORIALES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @GetMapping("/lista")
  public String listar(ModelMap modelo) {

    List<Editorial> editoriales = editorialServicio.listarEditoriales();

    modelo.addAttribute("editoriales", editoriales);

    return "editorial_list.html";
  }

  /*
   * MÉTODO MODIFICAR EDITORIALES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @GetMapping("/modificar/{id}")
  public String modificar(@PathVariable String id, ModelMap modelo) {
    modelo.put("editorial", editorialServicio.getOne(id));

    return "editorial_modificar.html";
  }

  @PostMapping("/modificar/{id}")
  public String modificar(@PathVariable String id, @RequestParam String nombre, ModelMap modelo) {
    try {

      editorialServicio.modificarEditorial(id, nombre);

      return "redirect:../lista";
    } catch (MiException ex) {
      modelo.put("error", ex.getMessage());
      return "editorial_modificar.html";
    }
  }

  /*
   * MÉTODO ELIMINAR EDITORIALES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @GetMapping("/eliminar/{id}")
  public String eliminar(@PathVariable String id, ModelMap modelo) {
    try {
      editorialServicio.eliminarEditorial(id);

    } catch (MiException e) {
      modelo.put("error", e.getMessage());
    }
    return "redirect:/editorial/lista";
  }
}
