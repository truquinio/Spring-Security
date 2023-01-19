package com.egg.biblioteca.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.egg.biblioteca.entidades.Imagen;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.ImagenRepositorio;

@Service
public class ImagenServicio {

  @Autowired
  private ImagenRepositorio imagenRepositorio;


  /*
   * MÉTODO GUARDAR IMG >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  // MultipartFile = tipo de archivo en q se guarda la img
  public Imagen guardar(MultipartFile archivo) throws MiException {

    // Si archivo existe
    if (archivo != null) {

      try {

        // Instancio objeto vacío imagen desde entidad Imagen
        Imagen imagen = new Imagen();

        // Seteo ATTR de imagen
        imagen.setMime(archivo.getContentType());
        // archivo.getContentType() = Trae tipo de archivo

        imagen.setNombre(archivo.getName());

        // archivo.getBytes() = Trae tipo de archivo
        imagen.setContenido(archivo.getBytes());

        // Si está OK, persisto imagen en Base de Datos
        return imagenRepositorio.save(imagen);

      } catch (Exception e) {

        // System.err.println = Imprime SOUT en color ROJO
        System.err.println(e.getMessage());

      }
    }
    // Si hay un error RETORNA null
    return null;
  }


  /*
   * MÉTODO ACTUALIZAR IMG >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  public Imagen actualizar(MultipartFile archivo, String idImagen) throws MiException {

    // Si archivo existe
    if (archivo != null) {

      try {

        // Instancio objeto vacío imagen desde entidad Imagen
        Imagen imagen = new Imagen();

        // Valido si id de img existe
        if (idImagen != null) {

          // Optional nos trae la img desde repositorio y la encuentra x id
          Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);

          // Valido si respuesta existe
          if (respuesta.isPresent()) {

            // Reemplazo objeto img con respuesta del Optional
            imagen = respuesta.get();
          }
        }

        // Seteo ATTR de imagen
        imagen.setMime(archivo.getContentType());
        // archivo.getContentType() = Trae tipo de archivo

        imagen.setNombre(archivo.getName());

        // archivo.getBytes() = Trae tipo de archivo
        imagen.setContenido(archivo.getBytes());

        // Si está OK, persisto imagen en Base de Datos
        return imagenRepositorio.save(imagen);

      } catch (Exception e) {

        // System.err.println = Imprime SOUT en color ROJO
        System.err.println(e.getMessage());
      }
    }
    // Si hay un error RETORNA null
    return null;
  }
}