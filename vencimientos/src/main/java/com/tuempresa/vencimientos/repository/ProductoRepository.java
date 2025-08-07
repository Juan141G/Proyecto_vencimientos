package com.tuempresa.vencimientos.repository;

import com.tuempresa.vencimientos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, UUID> {
    List<Producto> findByFechaVencimientoBeforeAndDescuentoAplicadoFalse(LocalDate fecha);
}
