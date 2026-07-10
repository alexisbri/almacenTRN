package com.alexis.almacen.controllers;

import com.alexis.almacen.dto.ventas.VentaRequest;
import com.alexis.almacen.dto.ventas.VentaResponse;
import com.alexis.almacen.services.venta.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Slf4j
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(@Valid @RequestBody VentaRequest request) {
        log.info("Registrando venta...");

        VentaResponse response = ventaService.registrar(request);

        log.info("Venta registrada con ID: {}", response.id());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
