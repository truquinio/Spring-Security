package com.egg.biblioteca.entidades;

import org.hibernate.annotations.GenericGenerator;
import com.egg.biblioteca.enumeraciones.Rol;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Usuario {

  // ATTR:
  @Id
  @GeneratedValue(generator = "uuid") // uuid = Identificador único universal
  @GenericGenerator(name = "uuid", strategy = "uuid2") // strategy = Evita que se repitan dos uuid
  private String id;

  private String nombre;
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private Rol rol;

  @OneToOne // @OneToOne = Relación USUARIO | IMG
  private Imagen imagen;

  
  // CONSTR:
  public Usuario() {
  }

  // G&S:
  public Imagen getImagen() {
    return imagen;
  }

  public void setImagen(Imagen imagen) {
    this.imagen = imagen;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }
}