package com.egg.biblioteca.entidades;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Imagen {

  // ATTR:
  @Id
  @GeneratedValue(generator = "uuid") // uuid = Identificador único universal
  @GenericGenerator(name = "uuid", strategy = "uuid2") // strategy = Evita que se repitan dos uuid
  private String id;

  // mime = Attr q asigna el formato del archivo de la imagen
  private String mime;

  private String nombre;

  @Lob // Lob = informa a Spring q archivo puede ser pesado
  @Basic(fetch = FetchType.LAZY) // @Basic fecth = FetchType.LAZY (perezosa) = contenido carga sólo con GET
  private byte[] contenido; // byte = forma en q se guarda el contenido de la img


  // CONSTR:
  public Imagen() {
  }

// G & S:
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getMime() {
    return mime;
  }
  public void setMime(String mime) {
    this.mime = mime;
  }
  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public byte[] getContenido() {
    return contenido;
  }
  public void setContenido(byte[] contenido) {
    this.contenido = contenido;
  }
}
