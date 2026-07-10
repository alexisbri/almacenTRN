package com.alexis.almacen.repositories;

import com.alexis.almacen.entities.Venta;
import com.alexis.almacen.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByEstadoVenta(EstadoVenta estadoVenta);

    @Query(value = """
    SELECT 
        s.ID_SUCURSAL AS idSucursal,
        s.NOMBRE AS nombreSucursal,
        SUM(d.CANTIDAD_PRODUCTO * d.PRECIO_PRODUCTO) AS totalFacturado,
        SUM(d.CANTIDAD_PRODUCTO) AS cantidadProductosVendidos
    FROM VENTAS v
    JOIN DETALLES_VENTAS d ON v.ID_VENTA = d.ID_VENTA
    JOIN SUCURSALES s ON v.ID_SUCURSAL = s.ID_SUCURSAL
    WHERE v.ESTADO = 'REGISTRADA'
    GROUP BY s.ID_SUCURSAL, s.NOMBRE
    """, nativeQuery = true)
    List<Object[]> obtenerReporteVentasPorSucursal();

}
