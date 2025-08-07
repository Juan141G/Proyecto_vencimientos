package com.tuempresa.vencimientos.controller;

import com.tuempresa.vencimientos.model.Producto;
import com.tuempresa.vencimientos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;
//crear productos
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        producto.setId(UUID.randomUUID());
        return productoRepository.save(producto);
    }
//listar productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }
//para actualizar productos
    @PutMapping("/{id}")
    public Producto actualizarProducto(@PathVariable UUID id, @RequestBody Producto productoActualizado) {
        return productoRepository.findById(id).map(producto -> {
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecioOriginal(productoActualizado.getPrecioOriginal());
            producto.setFechaVencimiento(productoActualizado.getFechaVencimiento());
            producto.setDescuentoAplicado(productoActualizado.isDescuentoAplicado());
            producto.setPrecioConDescuento(productoActualizado.getPrecioConDescuento());
            return productoRepository.save(producto);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
    }
//para eliminar productos
    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable UUID id) {
        productoRepository.deleteById(id);
        return "Producto eliminado con ID: " + id;
    }

}



