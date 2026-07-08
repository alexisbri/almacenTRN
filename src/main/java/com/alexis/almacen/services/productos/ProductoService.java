package com.alexis.almacen.services.productos;

import com.alexis.almacen.dto.productos.ProductoRequest;
import com.alexis.almacen.dto.productos.ProductoResponse;

import java.util.List;

public interface ProductoService {

    List<ProductoResponse> listar();

    ProductoResponse obtenerPorId(long id);

    ProductoResponse registrar(ProductoRequest request);

    ProductoResponse actualizar(ProductoRequest request, Long id);

    void eliminar(Long id);
}