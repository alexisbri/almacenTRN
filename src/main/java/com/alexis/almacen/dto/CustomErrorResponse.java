package com.alexis.almacen.dto;

public record CustomErrorResponse(
        int codigo,
        String mensaje
) {}
