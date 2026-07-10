package com.alexis.almacen.mappers;

import com.alexis.almacen.dto.sucursales.SucursalResponse;
import com.alexis.almacen.dto.ventas.DetalleVentaResponse;
import com.alexis.almacen.dto.ventas.VentaResponse;
import com.alexis.almacen.entities.DetalleVenta;
import com.alexis.almacen.entities.Venta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class VentaMapper {

    public VentaResponse ventaResponse(Venta venta) {
        if (venta == null) return null;

        // Calcular total
        BigDecimal total = venta.getDetalleVenta().stream()
                .map(d -> d.getPrecioProducto().multiply(BigDecimal.valueOf(d.getCantidadProducto())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Convertir sucursal
        SucursalResponse sucursalResponse = new SucursalResponse(
                venta.getSucursal().getId(),
                venta.getSucursal().getNombre(),
                venta.getSucursal().getDireccion()
        );

        // Convertir detalles
        List<DetalleVentaResponse> detallesResponse = venta.getDetalleVenta().stream()
                .map(this::toDetalleResponse)
                .toList();

        // Retornar Venta
        return new VentaResponse(
                venta.getId(),
                venta.getEstadoVenta().getDescripcion(),
                venta.getFecha().toString(),
                sucursalResponse,
                detallesResponse,
                total
        );
    }

    private DetalleVentaResponse toDetalleResponse(DetalleVenta detalle) {
        BigDecimal subtotal = detalle.getPrecioProducto()
                .multiply(BigDecimal.valueOf(detalle.getCantidadProducto()));

        return new DetalleVentaResponse(
                detalle.getProducto().getId(),
                detalle.getProducto().getNombre(),
                detalle.getCantidadProducto(),
                detalle.getPrecioProducto(),
                subtotal
        );
    }
}