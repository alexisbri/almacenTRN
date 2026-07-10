package com.alexis.almacen.dto.productos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/// se usa para CREAR o ACTUALIZAR productos
/// (métodos POST y PUT), donde los datos son obligatorios.
/// Para consultas, aqui no es el sitio.

public record ProductoRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 30, message = "El nombre es requerido y debe tener entre 5 y 30 caracteres")
        String nombre,

        @NotBlank(message = "La categoría es requerido")
        String categoria,

        @NotNull(message = "El precio es requerido")
        @Positive(message = "El presio debe ser positivo")
        BigDecimal precio,

        @NotNull(message = "La cantidad es requerida")
        @Positive(message = "La cantidad debe ser positiva")
        Integer cantidad

) { }
