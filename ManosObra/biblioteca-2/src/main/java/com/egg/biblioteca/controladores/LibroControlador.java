package com.egg.biblioteca.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;

import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.servicios.AutorServicio;
import com.egg.biblioteca.servicios.EditorialServicio;
import com.egg.biblioteca.servicios.LibroServicio;

import jakarta.persistence.Id;

@Controller // @Controller = Indica al framework de Spring que la clase es de tipo
            // controladora
@RequestMapping("/libro") // @RequestMapping = Configura la URL q va a escuchar clase controladora
public class LibroControlador {

  @Autowired // @Autowired = Inyección de dependencias, vincula al JPA
  private LibroServicio libroServicio;

  @Autowired
  private AutorServicio autorServicio;

  @Autowired
  private EditorialServicio editorialServicio;

  /*
   * MÉTODO CREAR EDITORIALES >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @GetMapping("/registrar") // @GetMapping = Se accede a travez de una operación GET de HTTP
  public String registrar(ModelMap modelo) { // localhost:8080/libro/registrar

    // Guardo en listas todo lo que nos trae el repositorio
    List<Autor> autores = autorServicio.listarAutores();
    List<Editorial> editoriales = editorialServicio.listarEditoriales();

    // Anclo cada lista a cada modelo
    modelo.addAttribute("autores", autores);
    modelo.addAttribute("editoriales", editoriales);

    return "libro_form.html"; // Retorna un String "archivo.html" que debo crear dentro de resources/templates
  }

  @PostMapping("/registro") // @PostMapping("/registro") = Se accede al action de HTML a travez de un método
                            // POST
  public String registro(@RequestParam(required = false) Long isbn, @RequestParam String titulo,
      @RequestParam(required = false) Integer ejemplares, @RequestParam String idAutor,
      @RequestParam String idEditorial, ModelMap modelo) {
    // Método registro = Recibe parámetro llamado igual q atributo name de INPUT de
    // HTML
    // @RequestParam = Indica a controlador q parámetro viaja en la URL y se ejecuta
    // cuando llene formulario
    // ModelMap = Inserta información q vamos a mostrar en interface del usuario

    // System.out.println("Nombre: " + nombre);

    try {

      // Llamo al método crearLibro, de la clase LibroServicio
      libroServicio.crearLibro(isbn, titulo, ejemplares, idAutor, idEditorial);

      // En caso de cargar todo correctamente, el usuario verá el sig. mensaje, bajo
      // la llave "exito"
      modelo.put("exito", "El libro fue cargado correctamente!");

      return "panel.html"; // Si todo sale bien, nos envía al index

    } catch (MiException exception) {

      List<Autor> autores = autorServicio.listarAutores();
      List<Editorial> editoriales = editorialServicio.listarEditoriales();

      modelo.addAttribute("autores", autores);
      modelo.addAttribute("editoriales", editoriales);

      // En caso de NO cargar correctamente, el usuario verá el sig. mensaje, bajo la
      // llave "error"
      modelo.put("error", exception.getMessage());

      return "libro_form.html"; // Si hay un error, se vuelve a cargar la página del formulario
    }
  }

  /*
   * MÉTODO LISTAR LIBROS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @GetMapping("/lista")
  public String listar(ModelMap modelo) {

    List<Libro> libros = libroServicio.listarLibros();

    modelo.addAttribute("libros", libros);

    return "libro_list";
  }

  /*
   * MÉTODO MODIFICAR LIBROS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @GetMapping("/modificar/{isbn}") // GET = Para trabajar con el HTML
  public String modificar(@PathVariable Long isbn, ModelMap modelo) {

    modelo.put("libro", libroServicio.getOne(isbn));

    List<Autor> autores = autorServicio.listarAutores();
    List<Editorial> editoriales = editorialServicio.listarEditoriales();

    modelo.addAttribute("autores", autores);
    modelo.addAttribute("editoriales", editoriales);

    return "libro_modificar.html";
  }

  // LO USO PARA EL BOTÓN ACTUALIZAR
  @PostMapping("/modificado/{isbn}") // POST = Modificar BASE de DATOS
  public String modificado(@PathVariable Long isbn, String titulo, Integer ejemplares, String idAutor,
      String idEditorial, ModelMap modelo) {

    try {
      List<Autor> autores = autorServicio.listarAutores();
      List<Editorial> editoriales = editorialServicio.listarEditoriales();

      modelo.addAttribute("autores", autores);
      modelo.addAttribute("editoriales", editoriales);

      libroServicio.modificarLibro(isbn, titulo, idAutor, idEditorial, ejemplares);

      return "redirect:/libro/lista";

    } catch (MiException ex) {

      List<Autor> autores = autorServicio.listarAutores();
      List<Editorial> editoriales = editorialServicio.listarEditoriales();

      modelo.put("error", ex.getMessage());

      modelo.addAttribute("autores", autores);
      modelo.addAttribute("editoriales", editoriales);

      return "libro_modificar.html";
    }
  }

  /*
   * MÉTODO ELIMINAR LIBRO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @PostMapping("/eliminar/{id}")
  public String eliminar(@PathVariable Long id, ModelMap modelo) {
    try {
      libroServicio.eliminarLibro(id);

    } catch (MiException e) {
      modelo.put("error", e.getMessage());
    }
    return "redirect:/libro/lista";
  }
}
