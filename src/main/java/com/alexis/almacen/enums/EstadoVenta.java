package com.alexis.almacen.enums;

import com.alexis.almacen.exceptions.RecursoNoEncontradoException;
import com.alexis.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public enum EstadoVenta {

    REGISTRADA(1L, "Registrada"),
    CANCELADA(0L, "Cancelada");

    private final Long codigo;

    private final String descripcion;

    public static EstadoVenta obtenerEstadoVentaPorDescripcion(String descripcion) {

        StringCustomUtils.validarNoVacio(descripcion, "La descripción es requerida");

        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion.trim());

        for (EstadoVenta estadoVenta : values()) {
            if (StringCustomUtils.quitarAcentos(estadoVenta.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return estadoVenta;
        }

        throw new RecursoNoEncontradoException("No existe un estado de venta con la descripción: " + descripcion);

    }

    public static EstadoVenta obtenerEstadoVentaPorCodigo(Long codigo) {

        for (EstadoVenta estadoVenta : values()) {
            if (Objects.equals(estadoVenta.codigo, codigo))
                return estadoVenta;
        }

        throw new RecursoNoEncontradoException("No existe un estado de venta con el código: " + codigo);

    }

}
