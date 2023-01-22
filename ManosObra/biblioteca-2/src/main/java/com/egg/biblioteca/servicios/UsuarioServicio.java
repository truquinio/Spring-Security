package com.egg.biblioteca.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.egg.biblioteca.entidades.Imagen;
import com.egg.biblioteca.entidades.Usuario;
import com.egg.biblioteca.enumeraciones.Rol;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.UsuarioRepositorio;
// import static com.sun.jmx.snmp.SnmpStatusException.readOnly;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;

@Service // @Service = Construir una clase Servicio que conecta a varios repositorios
// implements UserDetailsService = Para que implemente una interfaz especial
public class UsuarioServicio implements UserDetailsService {

  @Autowired // @Autowired = Inyección de dependencias, vincula al JPA
  // UsuarioRepositorio = @Query("SELECT u FROM Usuario u WHERE u.email = :email")
  private UsuarioRepositorio usuarioRepositorio;

  @Autowired
  private ImagenServicio imagenServicio;

  /*
   * MÉTODO REGISTRAR >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @Transactional // @Transactional = Si falla modificación en database hace rollback, no modifica
  // Recibe por parámetros, desde formulario, los datos para setear los attr.
  // MultipartFile archivo = Formato de archivo
  public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2)
      throws MiException {

    // Método REGISTRAR, llama a método VALIDAR
    validar(nombre, email, password, password2);

    // Instancio objeto "usuario" de clase USUARIO
    Usuario usuario = new Usuario();

    // Seteo Nombre, Email
    usuario.setNombre(nombre);
    usuario.setEmail(email);

    // Seteo el password, pero la encripto con BCryptPasswordEncoder
    usuario.setPassword(new BCryptPasswordEncoder().encode(password));

    // Se da ROL de USER x defecto, para que tenga privilegios comunes y no ADMIN
    usuario.setRol(Rol.USER);

    // Antes de guardar al usuario, guardo la img desde el servicio
    Imagen imagen = imagenServicio.guardar(archivo);

    // Seteo la img al usuario desde entidad usuario
    usuario.setImagen(imagen);

    // Persisto / Guardo usuario en base de datos
    usuarioRepositorio.save(usuario);
  }

  /*
   * MÉTODO ACTUALIZAR USUARIO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @Transactional
  public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String password,
      String password2) throws MiException {

    validar(nombre, email, password, password2);

    // Busco usuario x Id
    Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);

      // Valido si está presente
      if (respuesta.isPresent()) {

        // Reemplazo objeto Usuario con respuesta del Optional
        Usuario usuario = respuesta.get();

        // Seteo ATTR de usuario
        usuario.setNombre(nombre);
        usuario.setEmail(email);

        // Encripto el password
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        // Seteo el ROL
        usuario.setRol(Rol.USER);

        // Antes de guardar creo variable q guarda idImagen
        String idImagen = null; // null para q no de error

        // Valido si img de Usuario existe
        if (usuario.getImagen() != null) {

          // Guardo en idImagen el id de la img q ya trae el usuario
          idImagen = usuario.getImagen().getId();
        }

        // Instancio objeto imagen y la actualizo (recibo archivo + id)
        Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

        // Seteo la img al usuario
        usuario.setImagen(imagen);

        // Persisto / guardo el usuario en Base de Datos
        usuarioRepositorio.save(usuario);
    }
  }

  /*
   * MÉTODO LISTAR USUARIO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @Transactional(readOnly = true)
  public List<Usuario> listarUsuarios() {

    List<Usuario> usuarios = new ArrayList();

    usuarios = usuarioRepositorio.findAll();

    return usuarios;
  }

  /*
   * MÉTODO CAMBIAR ROL >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  @Transactional
  public void cambiarRol(String id) {
    Optional<Usuario> respuesta = usuarioRepositorio.findById(id);

    if (respuesta.isPresent()) {

      Usuario usuario = respuesta.get();

      if (usuario.getRol().equals(Rol.USER)) {

        usuario.setRol(Rol.ADMIN);

      } else if (usuario.getRol().equals(Rol.ADMIN)) {
        usuario.setRol(Rol.USER);
      }
    }
  }

  /*
   * MÉTODO VALIDAR >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  // VALIDO que todos los parámetros ingresados no sean nulos o estén vacíos.-
  private void validar(String nombre, String email, String password, String password2) throws MiException {

    if (nombre.isEmpty() || nombre == null) {
        throw new MiException("el nombre no puede ser nulo o estar vacío");
    }
    if (email.isEmpty() || email == null) {
        throw new MiException("el email no puede ser nulo o estar vacio");
    }
    if (password.isEmpty() || password == null || password.length() <= 5) {
        throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
    }

    if (!password.equals(password2)) {
        throw new MiException("Las contraseñas ingresadas deben ser iguales");
    }
}


  /*
   * MÉTODO CARGAR x NOMBRE USUARIO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */

  // Sobrescribo método Abstracto x implements UserDetailsService en class
  // UsuarioServicio
  @Override
  // loadUserByUsername = Carga usuario por nombre de usuario (email)
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    // Guardo el mail del usuarioRepositorio en un objeto "usuario" de clase USUARIO
    Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

    if (usuario != null) {

      // Creo la LISTA de PERMISOS de usuario
      List<GrantedAuthority> listaPermisos = new ArrayList<>();

      // GrantedAuthority / SimpleGrantedAuthority = Otorgan permisos de usuario
      // Instancio objeto "permisos" y concateno el ROL del usuario
      // ROLE_USER (rol viene del ENUM)
      GrantedAuthority permisos = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());

      // Agrego a la ListaPermisos, el objeto "permisos"
      listaPermisos.add(permisos);

      // Capturo la información del USUARIO logueado, para recuperarla y usarla en las
      // vistas
      ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

      // Guardo el attr y la sesión de solicitud en objeto de interfaz HttpSession
      HttpSession session = attr.getRequest().getSession(true);

      // Seteo los attr usuariosession que contiene todos los valores de usuario en la
      // sesión
      session.setAttribute("usuariosession", usuario);

      // Retorno un NUEVO USUARIO y traigo Email, Pass y listaPermisos para crearlo
      return new User(usuario.getEmail(), usuario.getPassword(), listaPermisos);

    } else {

      return null;
    }
  }

  /*
   * MÉTODO ELIMINAR USUARIO >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   */
  @Transactional
  public void eliminarUsuario(String id) throws MiException {
    Usuario usuario = usuarioRepositorio.getById(id);
    usuarioRepositorio.delete(usuario);
  }

  /*
   * MÉTODO getOne >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
   * Trae primer resultado de database que coincida con id
   */
  public Usuario getOne(String id) {
    return usuarioRepositorio.getOne(id);
  }
}