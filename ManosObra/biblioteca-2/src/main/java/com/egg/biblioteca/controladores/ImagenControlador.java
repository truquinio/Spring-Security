package com.egg.biblioteca.controladores;

import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // @Controller = Indica al framework de Spring que la clase es de tipo controladora
@RequestMapping("/imagen") // @RequestMapping = Configura la URL q va a escuchar clase controladora
public class ImagenControlador {

  @Autowired // @Autowired = Inyección de dependencias, vincula al JPA
  UsuarioServicio usuarioServicio;

  @GetMapping("/perfil/{id}") // @GetMapping = Se accede a travez de una operación GET de HTTP
  public ResponseEntity<byte[]> imagenUsuario(@PathVariable String id) {

    // Instancio un usuario y lo igualo al q trae el servicio x id
    Usuario usuario = usuarioServicio.getOne(id);

    // Guardo contenido de img en un Array de byte
    byte[] imagen = usuario.getImagen().getContenido();

    // HttpHeaders = le dice al navegador q devuelvo una img
    HttpHeaders headers = new HttpHeaders();

    // Seteo el contenido de img como JPEG
    headers.setContentType(MediaType.IMAGE_JPEG);

    // Retorno una imagen, la cabecera y el estado Http (OK=200) en un ResponseEntity 
    return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
  }
}