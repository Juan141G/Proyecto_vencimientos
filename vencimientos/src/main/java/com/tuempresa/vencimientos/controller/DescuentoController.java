package com.tuempresa.vencimientos.controller;

import com.tuempresa.vencimientos.service.DescuentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/descuentos")
public class DescuentoController {

    @Autowired
    private DescuentoService descuentoService;

    // Endpoint para aplicar descuento manual a un producto por ID
    @PostMapping("/{id}")
    public String aplicarDescuentoManual(@PathVariable UUID id) {
        descuentoService.aplicarDescuentoManual(id);
        return "✅ Descuento aplicado al producto con ID: " + id;
    }
}
