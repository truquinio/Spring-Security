package com.truquinio.estancias.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truquinio.estancias.exception.MiException;
import com.truquinio.estancias.repositorios.CasaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class CasaServicio {

    @Autowired
    CasaRepositorio casaRepositorio;

    /*
   * MÉTODO CREAR CASAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @Transactional // @Transactional = Si falla modificación en database hace rollback, no modifica
  public void crearCasa() throws MiException {

/*
 * private String id;
    private String calle;
    private int numero;
    private String ciudad;
    private String pais;

    @Temporal(TemporalType.DATE)
    private Date fechaDesde;

    @Temporal(TemporalType.DATE)
    private Date fechaHasta;

    private int minDias;
    private int maxDias;
    private Double precio;
    private String tipoVivienda;
 */

}

}

/*
 * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
 * necesarias para administrar las casas (creación, consulta, modificación y
 * eliminación).
 */