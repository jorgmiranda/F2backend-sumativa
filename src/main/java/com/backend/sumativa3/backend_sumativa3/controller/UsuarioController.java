package com.backend.sumativa3.backend_sumativa3.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.sumativa3.backend_sumativa3.model.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private static final String FILE_PATH = "json/usuarios.json";
   // private static final String FILE_PATH = "/home/ec2-user";
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public List<Usuario> obtenerUsuarios() throws IOException{
        File file = this.obtenerArchivoDesdeResource();
        return objectMapper.readValue(file,new TypeReference<List<Usuario>>() {});
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuariobyId(@PathVariable int id) throws IOException{
        List<Usuario> usuarios = obtenerUsuarios();
        return usuarios.stream().filter(p -> p.getId() == id).findFirst().orElse(null);

    }
    
    @PostMapping
    public Usuario agregarUsuario(@RequestBody Usuario usuario) throws IOException{
        List<Usuario> usuarios = obtenerUsuarios();
        usuarios.add(usuario);
        escribirEnArchivo(usuarios);
        System.out.println("-------------------------------------------");
        System.out.println(usuario);
        return usuario;
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable int id, @RequestBody Usuario actualizarUsuario) throws IOException{
        List<Usuario> usuarios = obtenerUsuarios();
        Optional<Usuario> usuarioOpt = usuarios.stream().filter(p -> p.getId() == id).findFirst();
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setNombreUsuario(actualizarUsuario.getNombreUsuario());
            usuario.setNombreCompleto(actualizarUsuario.getNombreCompleto());
            usuario.setContrasena(actualizarUsuario.getContrasena());
            usuario.setCorreoUsuario(actualizarUsuario.getCorreoUsuario());
            usuario.setDireccionDespacho(actualizarUsuario.getDireccionDespacho());
            usuario.setFechaNacimiento(actualizarUsuario.getFechaNacimiento());
            usuario.setRol(actualizarUsuario.getRol());
            usuario.setSesionIniciada(actualizarUsuario.isSesionIniciada());
            escribirEnArchivo(usuarios);
            return usuario;
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable int id) throws IOException{
        List<Usuario> usuarios = obtenerUsuarios();
        boolean removed = usuarios.removeIf(p -> p.getId() == id);
        if (removed) {
            escribirEnArchivo(usuarios);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
       
    }


    private void escribirEnArchivo(List<Usuario> usuarios) throws IOException{
        File file = obtenerArchivoDesdeResource();
        objectMapper.writeValue(file, usuarios);
    }

    private File obtenerArchivoDesdeResource() throws IOException{
        ClassPathResource resource = new ClassPathResource(FILE_PATH);
        return resource.getFile();
    }
}
