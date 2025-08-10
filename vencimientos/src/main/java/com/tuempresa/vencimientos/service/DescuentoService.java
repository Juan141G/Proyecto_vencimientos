package com.tuempresa.vencimientos.service;

import com.tuempresa.vencimientos.model.Producto;
import com.tuempresa.vencimientos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class DescuentoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Descuento configurable: 30% (puedes cambiar este valor fácilmente)
    private static final BigDecimal PORCENTAJE_DESCUENTO = BigDecimal.valueOf(0.30);

    // 🔁 Método llamado por el scheduler
    public void aplicarDescuentosAutomaticamente() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = hoy.plusDays(5);

        List<Producto> productos = productoRepository
                .findByFechaVencimientoBeforeAndDescuentoAplicadoFalse(fechaLimite);

        for (Producto producto : productos) {
            aplicarDescuento(producto);
            System.out.println("✔ Descuento automático aplicado a: " + producto.getNombre());
        }
    }

    // ✋ Método llamado desde un endpoint manual
    public void aplicarDescuentoManual(UUID idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (!producto.isDescuentoAplicado()) {
            aplicarDescuento(producto);
            System.out.println("✔ Descuento manual aplicado a: " + producto.getNombre());
        }
    }

    // 🔧 Método interno que calcula y aplica el descuento
    private void aplicarDescuento(Producto producto) {
        BigDecimal precioOriginal = producto.getPrecioOriginal();
        BigDecimal descuento = precioOriginal.multiply(PORCENTAJE_DESCUENTO);
        BigDecimal nuevoPrecio = precioOriginal.subtract(descuento);

        producto.setPrecioConDescuento(nuevoPrecio);
        producto.setDescuentoAplicado(true);
        productoRepository.save(producto);
    }



    @Scheduled(fixedRate = 60000) // cada hora en punto
    public void ejecutarDescuentosPeriodicamente() {
        System.out.println("⏰ Ejecutando descuentos automáticos programados...");
        aplicarDescuentosAutomaticamente();
    }

}
