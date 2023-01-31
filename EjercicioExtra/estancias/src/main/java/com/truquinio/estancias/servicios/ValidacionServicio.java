package com.truquinio.estancias.servicios;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.truquinio.estancias.exception.MiException;

//  Clase creada solo para validar todos los parámetros/attr de Autor, Editorial y Libro.
//  En caso de que los valores sean nulos o vacíos, muestro una excepción personalizada

@Service
public class ValidacionServicio {

  /*
   * VALIDAR ESTANCIA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  public void validarId(String id) throws MiException {
    if (id.isEmpty() || id == null) {
      throw new MiException("El id no puede estar vacio o ser nulo");
    }
  }

  public void validarHuesped(String huesped) throws MiException {
    if (huesped.isEmpty() || huesped == null) {
      throw new MiException("El huesped no puede estar vacio o ser nulo");
    }
  }

  public void validarFecha(Date fecha) throws MiException {
    if (fecha == null) {
      throw new MiException("La fecha no puede ser nula");
    }
  }

  /*
   * VALIDAR CASA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  public void validarCalle(String calle) throws MiException {
    if (calle.isEmpty() || calle == null) {
      throw new MiException("La calle no puede estar vacio o ser nulo");
    }
  }

  public void validarNumero(Integer numero) throws MiException {
    if (numero == null) {
      throw new MiException("El numero no puede ser nulo");
    }
  }

  public void validarCodPostal(String codPostal) throws MiException {
    if (codPostal.isEmpty() || codPostal == null) {
      throw new MiException("El codigo postal no puede estar vacio o ser nulo");
    }
  }

  public void validarCiudad(String ciudad) throws MiException {
    if (ciudad.isEmpty() || ciudad == null) {
      throw new MiException("La ciudad no puede estar vacio o ser nulo");
    }
  }

  public void validarPais(String pais) throws MiException {
    if (pais.isEmpty() || pais == null) {
      throw new MiException("El pais no puede estar vacio o ser nulo");
    }
  }

  public void validarDias(Date dias) throws MiException {
    if (dias == null) {
      throw new MiException("Los dias no pueden ser nulos");
    }
  }

  public void validarPrecio(Double precio) throws MiException {
    if (precio == null) {
      throw new MiException("El  precio no puede ser nulos");
    }
  }

  public void validarTipoVivienda(String tipoVivienda) throws MiException {
    if (tipoVivienda.isEmpty() || tipoVivienda == null) {
      throw new MiException("El tipo vivienda no puede estar vacio o ser nulo");
    }
  }

  /*
   * VALIDAR FAMILIA >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
    public void validarAlias(String nombre) throws MiException {
    if (nombre.isEmpty() || nombre == null) {
      throw new MiException("El nombre no puede estar vacio o ser nulo");
    }
  }

  public void validarEmail(String email) throws MiException {
    if (email.isEmpty() || email == null) {
      throw new MiException("El email no puede estar vacio o ser nulo");
    }
  }

  public void validarPassword(String password) throws MiException {
    if (password.isEmpty() || password == null || password.length() <= 5) {
      throw new MiException("La contraseña no puede estar vacia/nula y debe ser mayor a 5 digitos");
    }
  }

  public void validarPassword2(String password, String password2) throws MiException {
    if (!password.equals(password2)) {
      throw new MiException("Las contraseñas deben ser iguales");
    }
  }


  /*
   * VALIDAR USUARIO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  public void validarNombre(String nombre) throws MiException {
    if (nombre.isEmpty() || nombre == null) {
      throw new MiException("El alias no puede estar vacio o ser nulo");
    }
  }

}