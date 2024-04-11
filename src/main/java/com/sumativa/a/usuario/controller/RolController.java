package com.sumativa.a.usuario.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sumativa.a.usuario.model.Rol;
import com.sumativa.a.usuario.service.RolService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/roles")
public class RolController{

    private static final Logger log = LoggerFactory.getLogger(RolController.class);

    @Autowired
    private RolService rolService;

    @GetMapping
    public List<Rol> getAllRoles() {
        return rolService.getAllRoles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRolByID(@PathVariable Long id) {
        Optional<Rol> rol = rolService.getRolById(id);
        if(rol.isEmpty()){
            log.error("No se encontro ningun Rol con ese ID {} ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun rol con ese ID"));
        }
        return ResponseEntity.ok(rol);
    }

    @PostMapping
    public ResponseEntity<Object> crearRol(@RequestBody Rol rol){
        Rol rolCreado = rolService.crearRol(rol);
        if(rolCreado == null){
            log.error("Error al crear el rol");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error al crear el rol"));
        }
        return ResponseEntity.ok(rolCreado);
    }

    @PutMapping
    public ResponseEntity<Object> actualizarRol(@PathVariable Long id, @RequestBody Rol rol){
        Optional<Rol> rolbuscado = rolService.getRolById(id);
        if(rolbuscado.isEmpty()){
            log.error("No se encontro ningun rol con ese ID {} ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun rol con ese ID"));
        }else{
            if(rol.getNombreRol().isEmpty()){
                log.error("No se pueden definir un rol sin nombre", id);
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse("El rol debe tener un nombre"));
            }else{
                rolService.actualizarRol(id, rol);
                rol.setId(id);
            }
        }
        return ResponseEntity.ok(rol);
    }

    @DeleteMapping
    public ResponseEntity<Object> eliminarRol(@PathVariable Long id){
        Optional<Rol> rolbuscado = rolService.getRolById(id);
        if(rolbuscado.isEmpty()){
            log.error("No se encontro ningun rol con ese ID {} ", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No se encontro ningun rol con ese ID"));
        }

        rolService.eliminarRol(id);
        return ResponseEntity.ok("Rol Eliminado");
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

