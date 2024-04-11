package com.sumativa.a.usuario.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.sumativa.a.usuario.model.Rol;
import com.sumativa.a.usuario.model.Usuario;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
public class UsuarioController {
    
    List<Usuario> usuarios = new ArrayList<>();

    public UsuarioController(){
        
        String[] roles1 = new String[] {"Usuario", "VIP"};
        String[] roles2 = new String[] {"Usuario"};
        String[] roles3 = new String[] {"Usuario", "Administrador"};

        String[] direcciones1 = new String[] {"Calle 123, La Reina", "Calle 4, La Cisterna"};
        String[] direcciones2 = new String[] {"Avenida Central, Providencia", "Avenida Norte, Las Condes", "Calle 7, Ñuñoa"};
        String[] direcciones3 = new String[] {"Avenida Sur, Puente Alto", "Calle 10, La Florida"};
        String[] direcciones4 = new String[] {"Calle 22, La Cisterna", "Calle 15, La Pintana"};
        String[] direcciones5 = new String[] {"Calle 30, Santiago Centro"};
        String[] direcciones6 = new String[] {"Avenida 15 Norte, Viña del Mar", "Avenida 20, Valparaíso"};
        String[] direcciones7 = new String[] {"Calle 2, San Bernardo"};
        String[] direcciones8 = new String[] {"Avenida 25, Maipú", "Calle 6, Pudahuel"};



        usuarios.add(new Usuario(1, "Juanito Perez", "junito.perez@gmail.com", LlenarListaRoles(roles1), direcciones1));
        usuarios.add(new Usuario(2, "Maria Sanchez", "maria.sanchez@gmail.com", LlenarListaRoles(roles2), direcciones2));
        usuarios.add(new Usuario(3, "Pedro Rodriguez", "pedro.rodriguez@gmail.com", LlenarListaRoles(roles3), direcciones3));
        usuarios.add(new Usuario(4, "Ana Gómez", "ana.gomez@gmail.com", LlenarListaRoles(roles1), direcciones4));
        usuarios.add(new Usuario(5, "Luisa Torres", "luisa.torres@gmail.com", LlenarListaRoles(roles2), direcciones5));
        usuarios.add(new Usuario(6, "Carlos López", "carlos.lopez@gmail.com", LlenarListaRoles(roles3), direcciones6));
        usuarios.add(new Usuario(7, "Sofía Martínez", "sofia.martinez@gmail.com", LlenarListaRoles(roles1), direcciones7));
        usuarios.add(new Usuario(8, "Martín Ramírez", "martin.ramirez@gmail.com", LlenarListaRoles(roles2), direcciones8));
    }


    private List<Rol> LlenarListaRoles(String[]  roles){
    
        List<Rol> r = new ArrayList<>();
        for(int i = 0; i < roles.length; i++){
            Rol rol = new Rol(i +1, roles[i], "Descripcion de ejemplo "+ i+1);
            r.add(rol);
        }
    
        return r;
    }

    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    @GetMapping("/usuarios/{id}")
    public Usuario getUsuarioByID(@PathVariable int id) {
        
        for (Usuario u : usuarios){
            if(u.getId() == id){
                return u;
            }
        }

        return new Usuario();
    }
    
    @GetMapping("/usuarios/{id}/direcciones")
    public String[] getUsuarioDirecciones(@PathVariable int id) {
        
        for (Usuario u : usuarios){
            if(u.getId() == id){
                return u.getDirecciones();
            }
        }

        return new String[] {};
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("/usuarios/{id}/roles")
    public List<Rol> getUsuariosRoles(@PathVariable int id) {
        
        for (Usuario u : usuarios){
            if(u.getId() == id){
                return u.getRoles();
            }
        }
        
        return new ArrayList();
    }

    @GetMapping("/usuarios/contar")
    public HashMap<String, Integer> getCantidadUsuarios() {
        HashMap<String, Integer> cantidad = new HashMap<String, Integer>();
        cantidad.put("Cantidad Usuarios", usuarios.size());
        return cantidad;
    }

}






