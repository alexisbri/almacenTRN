package com.alexis.almacen.controllers;

import com.alexis.almacen.dto.reportes.ReporteVentasSucursalResponse;
import com.alexis.almacen.dto.sucursales.SucursalRequest;
import com.alexis.almacen.dto.sucursales.SucursalResponse;
import com.alexis.almacen.dto.ventas.VentaRequest;
import com.alexis.almacen.dto.ventas.VentaResponse;
import com.alexis.almacen.services.venta.VentaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Slf4j
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponse>> listarActivas() {
        List<VentaResponse> ventas = ventaService.listarActivas();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/canceladas")
    public ResponseEntity<List<VentaResponse>> listarCanceladas() {
        List<VentaResponse> ventas = ventaService.listarCanceladas();
        return ResponseEntity.ok(ventas);
    }

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(@Valid @RequestBody VentaRequest request) {

        VentaResponse response = ventaService.registrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PutMapping("/cancelar/{id}")
    public ResponseEntity<VentaResponse> cancelar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id
    ) {
        VentaResponse response = ventaService.cancelar(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/reporte-por-sucursal")
    public ResponseEntity<List<ReporteVentasSucursalResponse>> obtenerReportePorSucursal() {
        List<ReporteVentasSucursalResponse> reporte = ventaService.obtenerReportePorSucursal();
        return ResponseEntity.ok(reporte);
    }



}
