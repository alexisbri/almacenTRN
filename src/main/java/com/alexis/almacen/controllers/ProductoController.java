package com.alexis.almacen.controllers;

import com.alexis.almacen.dto.productos.ProductoRequest;
import com.alexis.almacen.dto.productos.ProductoResponse;
import com.alexis.almacen.services.productos.ProductoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// Administra las peticiones y respuestas HTTP

@RestController
@RequestMapping("/api/productos")
@AllArgsConstructor
@Validated
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    // Al agregar @PathVariable hace uno referencia objetos en concreto como id que ira
    // en /{id}. Sin eso no lo indentificara como variable.
    // @Positive es una validación para que sea positivo.

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(
            @PathVariable
            @Positive(message = "El ID debe ser positivo") Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    // @Valid debe agregaarse antes para que funcione y que se haga valido

    @PostMapping
    public ResponseEntity<ProductoResponse> registrar(@Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.registrar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id,
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(productoService.actualizar(request, id));
    }


    // En este caso se usa void, lo cual la respuesta se agrega de la siguiente forma:
    // return ResponseEntity.noContent().build();
    // Esto indica que la respuesta no regresa nada, solo se ejecuta bien. 204

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable @Positive(message = "El ID debe ser positivo") Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }




}
