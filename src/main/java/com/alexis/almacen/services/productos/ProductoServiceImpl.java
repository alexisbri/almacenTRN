package com.alexis.almacen.services.productos;

import com.alexis.almacen.dto.productos.ProductoRequest;
import com.alexis.almacen.dto.productos.ProductoResponse;
import com.alexis.almacen.entities.Producto;
import com.alexis.almacen.enums.Categoria;
import com.alexis.almacen.exceptions.RecursoNoEncontradoException;
import com.alexis.almacen.mappers.ProductoMapper;
import com.alexis.almacen.repositories.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override // indica que este metodo está sobrescribiendo un metodo de la clase padre o de una interfaz.
    @Transactional(readOnly = true)
    public List<ProductoResponse> listar(
            String nombre, String categoria,
            BigDecimal precioMin, BigDecimal precioMax
    ) {

        log.info("Listar todos los productos");

        return productoRepository.findAll().stream()
                .map(productoMapper::entidadResponse).toList();
    }


    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(long id) {
        return productoMapper.entidadResponse(obtenerProductoOException(id));
    }


    @Override
    public ProductoResponse registrar(ProductoRequest request) {

        log.info("Listar todos los productos");

        Categoria categoria = Categoria.obtenerCategoriaPorDescripcion(request.categoria());

        Producto producto = productoMapper.requestAEntidad(request, categoria);

        productoRepository.save(producto);

        log.info("Nuevo producto {} registrado", producto.getNombre());

        return productoMapper.entidadResponse(producto);
    }


    @Override
    public ProductoResponse actualizar(ProductoRequest request, Long id) {

        Producto producto = obtenerProductoOException(id);

        Categoria categoria = Categoria.obtenerCategoriaPorDescripcion(request.categoria());

        log.info("Actualizar producto con id: {}", id);

        producto.actualizar(
                request.nombre(),
                categoria,
                request.precio(),
                request.cantidad()
        );

        // La clase seguira actualizando sin nececidad de indicarle, solo porque el @Transactional esta colocado.
        // @Transactional hace commit al terminar pero si falla hace rollback
        /// productoRepository.save(producto);

        log.info("Producto con id {} actualizado", id);

        return productoMapper.entidadResponse(producto);

    }


    @Override
    public void eliminar(Long id) {

        Producto producto = obtenerProductoOException(id);

        productoRepository.delete(producto);

        log.info("Producto con id {} eliminar", id);

    }


    private Producto obtenerProductoOException(Long id) {
        log.info("Buscando producto con id: {}", id);

        return productoRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
    }


    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> busquedaProductoDinamico(
            String nombre,
            String categoria,
            BigDecimal precioMin,
            BigDecimal precioMax
    ) {
        log.info("Buscando producto dinamico...");

        Categoria categoriaEnum = Categoria.obtenerCategoriaPorDescripcion(categoria);

        List<Producto> productos = productoRepository.busquedaProductoDinamico(
                nombre, categoriaEnum, precioMin, precioMax
        );

        return productos.stream()
                .map(productoMapper::entidadResponse)
                .toList();

    }


}
