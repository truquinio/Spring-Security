package com.truquinio.estancias.servicios;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.truquinio.estancias.exception.MiException;

//  Clase creada solo para validar todos los parámetros/attr de Autor, Editorial y Libro.
//  En caso de que los valores sean nulos o vacíos, muestro una excepción personalizada

@Service
public class ValidacionServicio {

  // CLIENTE + FAMILIA + USUARIO (comparten id + nombre + email )  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  public void validarId(String id) throws MiException {

    if (id.isEmpty() || id == null) {
      throw new MiException("id no puede ser nulo / vacío");
    }
  }

  public void validaNombre(String nombre) throws MiException {

    if (nombre.isEmpty() || nombre == null) {
      throw new MiException("El nombre no puede ser nulo / vacío");
    }
  }

  public void validaEmail(String email) throws MiException {

    if (email.isEmpty() || email == null) {
      throw new MiException("El e-mail no puede ser nulo / vacío");
    }
  }
  

// HUESPED  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
  public void validarHuesped(String huesped) throws MiException {

    if (huesped.isEmpty() || huesped == null) {
      throw new MiException("El huesped no puede ser nulo / vacío");
    }
  }

  public void validarFecha(Date fecha) throws MiException {

    if (fecha == null) {
      throw new MiException("La fecha no puede ser nula / vacía");
    }
  }
}
