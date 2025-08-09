package com.tuempresa.vencimientos.controller;

import com.tuempresa.vencimientos.model.Producto;
import com.tuempresa.vencimientos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Crear producto
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        producto.setId(UUID.randomUUID());
        return productoRepository.save(producto);
    }

    // Listar productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public Producto obtenerProductoPorId(@PathVariable UUID id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto no encontrado con id: " + id
                ));
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable UUID id, @RequestBody Producto productoActualizado) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecioOriginal(productoActualizado.getPrecioOriginal());
            producto.setFechaVencimiento(productoActualizado.getFechaVencimiento());
            producto.setDescuentoAplicado(productoActualizado.isDescuentoAplicado());
            producto.setPrecioConDescuento(productoActualizado.getPrecioConDescuento());
            return productoRepository.save(producto);
        }).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Producto no encontrado con id: " + id
        ));
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable UUID id) {
        productoRepository.deleteById(id);
        return "Producto eliminado con ID: " + id;
    }
}




