package com.sumativa.a.usuario.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;

import com.sumativa.a.usuario.model.Rol;
import com.sumativa.a.usuario.model.Usuario;
import com.sumativa.a.usuario.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private static final Logger log = LoggerFactory.getLogger(RolController.class);

    @Autowired
    private UsuarioService usuarioService;
    
    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioService.getAllUsuario();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUsuarioByID(@PathVariable Long id) {
        Optional<Usuario> usr = usuarioService.getUsuarioById(id);
        if(usr.isEmpty()){
            log.error("No se encontro ningun Usuario con ese ID {} ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun Usuario con ese ID"));
        }
        return ResponseEntity.ok(usr);
    }
    
    @GetMapping("/{id}/direcciones")
    public ResponseEntity<Object> getUsuarioDirecciones(@PathVariable Long id) {
        Optional<Usuario> usr = usuarioService.getUsuarioById(id);
        if(usr.isEmpty()){
            log.error("No se encontro ningun Usuario con ese ID {} ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun Usuario con ese ID"));
        }
        
        return ResponseEntity.ok(usr.get().getDireccionesArray());
    
    }
    
    @GetMapping("/{id}/roles")
    public ResponseEntity<Object> getUsuariosRoles(@PathVariable Long id) {
        Optional<Usuario> usr = usuarioService.getUsuarioById(id);
        if(usr.isEmpty()){
            log.error("No se encontro ningun Usuario con ese ID {} ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun Usuario con ese ID"));
        }
        
        return ResponseEntity.ok(usr.get().getRoles());
        
    }

    @GetMapping("/contar")
    public int getCantidadUsuarios() {
        List<Usuario> usuarios = usuarioService.getAllUsuario();
        return usuarios.size();
    }

    @PostMapping
    public ResponseEntity<Object> crearUsuario(@RequestBody Usuario usr){
        Usuario usrCreado = usuarioService.crearUsuario(usr);
        if(usrCreado == null){
            log.error("Error al crear el Usuario");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al crear el Usuario"));
        }
        return ResponseEntity.ok(usrCreado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarUsuario(@PathVariable Long id){
        Optional<Usuario> usr = usuarioService.getUsuarioById(id);
        if(usr.isEmpty()){
            log.error("No se encontro ningun Usuario con ese ID {} ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun Usuario con ese ID"));
        }
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario Eliminado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usr){
       // Valida que el usuario exista
        Optional<Usuario> usrBuscado = usuarioService.getUsuarioById(id);
        if(usrBuscado.isEmpty()){
            log.error("No se encontro ningun Usuario con ese ID {} ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun Usuario con ese ID"));
        }
        //Valida que el correo sea unico
        List<Usuario> listaUsuarios = usuarioService.getAllUsuario();
        for(Usuario u : listaUsuarios){
            if(usr.getCorreo() == u.getCorreo()){
                log.error("Ya existe un usuario con el correo {} ", u.getCorreo());
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse("Ya existe un usuario con el correo "+ u.getCorreo()));
            }
        }
        if(usr.getNombreCompleto().isEmpty()){
            log.error("El nombre del usuario es obligatorio." );
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse("El nombre del usuario es obligatorio "));
        }

        if(usr.getContrasena().isEmpty()){
            log.error("La constraseña es obligatorio." );
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse("La constraseña es obligatoria "));
        }

        if(usr.getCorreo().isEmpty()){
            log.error("El correo es obligatorio." );
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse("El correo es obligatorio "));
        }

        usuarioService.actualizarUsuario(id, usr);
        usr.setId(id);
        return ResponseEntity.ok(usr);
    
    }



    static class ErrorResponse {
        private final String message;
    
        public ErrorResponse(String message){
            this.message = message;
        }
    
        public String getMessage(){
            return message;
        }
        
    }

}






