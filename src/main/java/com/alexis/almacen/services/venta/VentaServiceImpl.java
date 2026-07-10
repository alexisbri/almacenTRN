package com.alexis.almacen.services.venta;

import com.alexis.almacen.dto.ventas.DetalleVentaRequest;
import com.alexis.almacen.dto.ventas.VentaRequest;
import com.alexis.almacen.dto.ventas.VentaResponse;
import com.alexis.almacen.entities.DetalleVenta;
import com.alexis.almacen.entities.Producto;
import com.alexis.almacen.entities.Sucursal;
import com.alexis.almacen.entities.Venta;
import com.alexis.almacen.enums.EstadoVenta;
import com.alexis.almacen.exceptions.RecursoNoEncontradoException;
import com.alexis.almacen.mappers.VentaMapper;
import com.alexis.almacen.repositories.ProductoRepository;
import com.alexis.almacen.repositories.SucursalRepository;
import com.alexis.almacen.repositories.VentaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    private final VentaMapper ventaMapper;

    @Override
    public VentaResponse registrar(VentaRequest request) {
        log.info("Registrando nueva venta...");

        // Obtengo sucursal
        Sucursal sucursal = sucursalRepository.findById(request.idSucursal())
                        .orElseThrow(() -> new RecursoNoEncontradoException("Sucursal no encontrada"));

        // Creo venta
        Venta venta = Venta.builder()
                .sucursal(sucursal)
                .fecha(LocalDate.now())
                .estadoVenta(EstadoVenta.REGISTRADA)
                .detalleVenta(new ArrayList<>())
                .build();

        // Obtener el producto
        DetalleVentaRequest detalleVentaRequest = request.productos().get(0);

        Producto producto = productoRepository.findById(detalleVentaRequest.idProducto())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Producto no encontrado: " + detalleVentaRequest.idProducto()
                ));

            // Descontar stock
            producto.descontarCantidad(detalleVentaRequest.cantidadProducto());


            DetalleVenta detalle = DetalleVenta.builder()
                    .venta(venta)
                    .producto(producto)
                    .cantidadProducto(detalleVentaRequest.cantidadProducto())
                    .precioProducto(producto.getPrecio())
                    .build();

            venta.getDetalleVenta().add(detalle);

        ventaRepository.save(venta);

        log.info("Venta registrada con ID: {}", venta.getId());

        return ventaMapper.ventaResponse(venta);
    }


}
