package com.alexis.almacen.enums;

import com.alexis.almacen.exceptions.RecursoNoEncontradoException;
import com.alexis.almacen.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/// Para evitar el constructor de la forma tradicional en codigo
/// se agrega la anotación @RequiredArgsConstructor

@RequiredArgsConstructor

/// Con esto ya puedes obtener los datos privados de getter
@Getter

/// Esto requiere un constructor para funcionar,
/// ademas de define un atributo para los valores de la descripción

public enum Categoria {
    ALIMENTO("Alimento"),
    HIGIENE("Higiene"),
    JUGUETE("Juguete"),
    ELECTRONICA("Electronica"),
    ROPA("Ropa"),
    ACCESORIO("Accesorio"),
    FARMACIA("Farmacia");


    /// Atributo:
    private final String descripcion;

    public static Categoria obtenerCategoriaPorDescripción(String descripcion) {

        /// Alerta para errores del cliente
        StringCustomUtils.validarNoVacio(descripcion, "La descripción es requerida");

        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion.trim());

        for (Categoria categoria : values()) {
            if (StringCustomUtils.quitarAcentos(categoria.descripcion).equalsIgnoreCase(descripcionNormalizada))
                return categoria;
        }


        /// Alerta para errores 500 del servidor
        throw new RecursoNoEncontradoException("No existe una categoría con la descripción: " + descripcion);

    }

}
