package com.truquinio.estancias.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truquinio.estancias.entidades.Casa;
import com.truquinio.estancias.entidades.Cliente;
import com.truquinio.estancias.entidades.Reserva;
import com.truquinio.estancias.exception.MiException;
import com.truquinio.estancias.repositorios.CasaRepositorio;
import com.truquinio.estancias.repositorios.ClienteRepositorio;
import com.truquinio.estancias.repositorios.FamiliaRepositorio;
import com.truquinio.estancias.repositorios.ReservaRepositorio;

import jakarta.transaction.Transactional;

@Service
public class ReservaServicio {

  @Autowired
  ReservaRepositorio reservaRepositorio;

  @Autowired
  ClienteRepositorio clienteRepositorio;

  @Autowired
  CasaRepositorio casaRepositorio;

  @Autowired
  ValidacionServicio validacion;

  /*
   * MÉTODO CREAR RESERVACION
   */

  @Transactional // @Transactional = Si falla modificación en database hace rollback, no modifica
  public void crearReserva(String huesped, Date fechaDesde, Date fechaHasta, String idCliente, String idCasa)
      throws MiException {

    // validar todo de Reserva, desde ValidacionServicio
    validacion.validarHuesped(huesped);
    validacion.validarFecha(fechaDesde);
    validacion.validarFecha(fechaHasta);

    // Encuentra por id Cliente y Familia, los trae y guarda en objetos
    Optional<Cliente> respuestaCliente = clienteRepositorio.findById(idCliente);
    Optional<Casa> respuestaCasa = casaRepositorio.findById(idCasa);
    // Optional = Por si id existe o no y si contiene algún error

    // Objetos Cliente y Casa
    Cliente cliente = new Cliente();
    Casa casa = new Casa();

    // Valido si respuesta está presente
    if (respuestaCliente.isPresent()) {
      cliente = respuestaCliente.get();
    }

    if (respuestaCasa.isPresent()) {
      casa = respuestaCasa.get();
    }

    // Creo objeto Reserva
    Reserva reserva = new Reserva();

    // Seteo los parámetros
    reserva.setHuesped(huesped);
    reserva.setFechaDesde(fechaDesde);
    reserva.setFechaHasta(fechaHasta);
    reserva.setCliente(cliente);
    reserva.setCasa(casa);

    // Persisto / Guardo Reserva en base de datos
    reservaRepositorio.save(reserva);
  }

  /*
   * MÉTODO LISTAR RESERVAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  public List<Reserva> listarReservas() {

    List<Reserva> listaReservas = new ArrayList<>();

    // Encuentra reservas dentro de repositorio, los mete en arraylist listaReservas
    listaReservas = reservaRepositorio.findAll();

    return listaReservas;
  }

  /*
   * MÉTODO MODIFICAR RESERVAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Transactional
  public void modificarReserva(String huesped, Date fechaDesde, Date fechaHasta, String idCliente, String idCasa)
      throws MiException {

    // validar todo de Reserva, desde ValidacionServicio
    validacion.validarHuesped(huesped);
    validacion.validarFecha(fechaDesde);
    validacion.validarFecha(fechaHasta);

    // Encuentra por id Cliente y Familia, los trae y guarda en objetos
    Optional<Cliente> respuestaCliente = clienteRepositorio.findById(idCliente);
    Optional<Casa> respuestaCasa = casaRepositorio.findById(idCasa);
    // Optional = Por si id existe o no y si contiene algún error

    // Objetos Cliente y Casa
    Cliente cliente = new Cliente();
    Casa casa = new Casa();

    // Valida si la respuesta está presente
    if (respuestaCliente.isPresent()) {
      cliente = respuestaCliente.get();
    }

    if (respuestaCasa.isPresent()) {
      casa = respuestaCasa.get();
    }
  }

    /*
   * MÉTODO ELIMINAR RESERVAS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Transactional
  public void eliminarReserva( String id) throws MiException{
    Reserva estancia = reservaRepositorio.getById(id);
    reservaRepositorio.delete(estancia);
  }

}

/*
 * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
 * necesarias para realizar las reservas de viviendas (reservar, consultar
 * reservas realizadas, modificación y eliminación).
 */