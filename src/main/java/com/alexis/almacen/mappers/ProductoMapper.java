package com.alexis.almacen.mappers;

import com.alexis.almacen.dto.productos.ProductoRequest;
import com.alexis.almacen.dto.productos.ProductoResponse;
import com.alexis.almacen.entities.Producto;
import com.alexis.almacen.enums.Categoria;
import org.springframework.stereotype.Component;

// marca una clase como un Bean gestionado por Spring,
// permitiendo que Spring la detecte automáticamente y
// la administre en su contenedor de IoC (Inversión de Control).
@Component

public class ProductoMapper {

    // Metodo
    public Producto requestAEntidad(ProductoRequest request, Categoria categoria) {

        if (request == null) return null;

        /// Builder es un patron de diseño y evita errores de parametros faltantes
        return Producto.builder()
                .nombre(request.nombre().trim())
                .categoria(categoria)
                .precio(request.precio())
                .cantidad(request.cantidad())
                .build();

    }

    public ProductoResponse entidadResponse(Producto producto) {

        if (producto == null) return null;

        return new ProductoResponse(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria().getDescripcion(),
                producto.getPrecio(),
                producto.getCantidad()
        );

    }

}
