package com.truquinio.estancias.servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.truquinio.estancias.entidades.Familia;
import com.truquinio.estancias.enumeraciones.Rol;
import com.truquinio.estancias.exception.MiException;
import com.truquinio.estancias.repositorios.FamiliaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class FamiliaServicio {

  @Autowired
  FamiliaRepositorio familiaRepositorio;

  @Autowired
  ValidacionServicio validacionServicio;

  /*
   * MÉTODO CREAR FAMILIAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  public void crearFamilia(String nombre, String email, String password, String password2) throws MiException {

    validacionServicio.validarNombre(nombre);
    validacionServicio.validarEmail(email);
    validacionServicio.validarPassword(password);
    validacionServicio.validarPassword2(password, password2);

    Familia familia = new Familia();

    // seteo los parametros
    familia.setNombre(nombre);
    familia.setEmail(email);
    familia.setClave(new BCryptPasswordEncoder().encode(password));
    familia.setRol(Rol.USER);

    // persisto - guardo en BD
    familiaRepositorio.save(familia);
  }

  /*
   * MÉTODO LISTAR FAMILIAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  public List<Familia> listarFamilias() {

    List<Familia> listaFamilias = new ArrayList<>();

    // Encuentra familias dentro de repositorio, los mete en arraylist listaFamilias
    listaFamilias = familiaRepositorio.findAll();

    return listaFamilias;
  }

  /*
   * MÉTODO MODIFICAR FAMILIAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  public void modificarFamilia(String nombre, String email, String password, String password2) throws MiException {

    validacionServicio.validarNombre(nombre);
    validacionServicio.validarEmail(email);
    validacionServicio.validarPassword(password);
    validacionServicio.validarPassword2(password, password2);

    Familia familia = new Familia();

    // seteo los parametros
    familia.setNombre(nombre);
    familia.setEmail(email);
    familia.setClave(password);
    familia.setClave(new BCryptPasswordEncoder().encode(password));
    familia.setRol(Rol.USER);

    // persisto - guardo en BD
    familiaRepositorio.save(familia);
  }

  /*
   * MÉTODO ELIMINAR FAMILIAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Transactional
  public void eliminarFamilias(String id) throws MiException {
    Familia familia = familiaRepositorio.getById(id);
    familiaRepositorio.delete(familia);
  }

  /*
   * MÉTODO getOne >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   * Trae primer resultado de database que coincida con id
   */
  public Familia getOne(String id) {
    return familiaRepositorio.getOne(id);
  }
}

/*
 * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
 * necesarias para administrar familias (creación, consulta, modificación y
 * eliminación).
 */
