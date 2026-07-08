package com.alexis.almacen.dto.sucursales;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record SucursalRequest(

        @NotBlank(message = "El nombre es requerido")
        @Size(min = 5, max = 50, message = "El nombre es requerido y debe tener entre 5 y 50 caracteres")
        String nombre,

        @NotBlank(message = "La sucursal es requerido")
        @Size(min = 5, max = 150, message = "El nombre es requerido y debe tener entre 5 y 150 caracteres")
        String direccion

) {
}
