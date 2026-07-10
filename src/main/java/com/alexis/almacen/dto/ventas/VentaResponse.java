package com.alexis.almacen.dto.ventas;

import com.alexis.almacen.dto.sucursales.SucursalResponse;

import java.math.BigDecimal;
import java.util.List;

public record VentaResponse(

        Long id,
        String estado,
        String fecha,
        SucursalResponse sucursal,
        List<DetalleVentaResponse> detalles,
        BigDecimal total

) {}
