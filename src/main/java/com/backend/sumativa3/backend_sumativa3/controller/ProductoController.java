package com.backend.sumativa3.backend_sumativa3.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.backend.sumativa3.backend_sumativa3.model.Producto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    private static final String FILE_PATH = "json/productos.json";
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public List<Producto> obtenerProductos() throws IOException{
        File file = this.obtenerArchivoDesdeResource();
        return objectMapper.readValue(file,new TypeReference<List<Producto>>() {});
    }

    @GetMapping("/{id}")
    public Producto obtenerProductosbyID(@PathVariable int id) throws IOException{
        List<Producto> productos = obtenerProductos();
        return productos.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    @GetMapping("/tipo/{tipoProducto}")
    public List<Producto> obtenerProductosByTipo(@PathVariable String tipoProducto) throws IOException{
        List<Producto> productos = obtenerProductos();
        return productos.stream().filter(p -> p.getTipoProducto().equals(tipoProducto)).collect(Collectors.toList());
    }

    @PostMapping
    public Producto agregarProducto(@RequestBody Producto producto) throws IOException{
        List<Producto> productos = obtenerProductos();
        productos.add(producto);
        escribirEnArchivo(productos);
        return producto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto (@PathVariable int id) throws IOException{
        List<Producto> productos = obtenerProductos();
        boolean removed = productos.removeIf(p -> p.getId() == id);
        if (removed) {
            escribirEnArchivo(productos);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable int id, @RequestBody Producto actualizarProducto) throws IOException{
        List<Producto> productos = obtenerProductos();
        Optional<Producto> prodOtp = productos.stream().filter(p -> p.getId() == id).findFirst();
        if(prodOtp.isPresent()){
            Producto producto = prodOtp.get();
            producto.setNombre(actualizarProducto.getNombre());
            producto.setPrecio(actualizarProducto.getPrecio());
            producto.setDescripcion(actualizarProducto.getDescripcion());
            producto.setTipoProducto(actualizarProducto.getTipoProducto());
            if(!actualizarProducto.getUrlImg().isEmpty()){
                producto.setUrlImg(actualizarProducto.getUrlImg());
            }
            escribirEnArchivo(productos);
            return producto;

        }else{
            return null;
        }
    }



    private void escribirEnArchivo(List<Producto> productos) throws IOException{
        File file = obtenerArchivoDesdeResource();
        objectMapper.writeValue(file, productos);
    }

    private File obtenerArchivoDesdeResource() throws IOException{
        ClassPathResource resource = new ClassPathResource(FILE_PATH);
        return resource.getFile();
    }
}
