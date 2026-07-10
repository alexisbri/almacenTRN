package com.alexis.almacen.services.venta;

import com.alexis.almacen.dto.reportes.ReporteVentasSucursalResponse;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
        for (DetalleVentaRequest detalleVentaRequest : request.productos()) {

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

        }

        ventaRepository.save(venta);

        log.info("Venta registrada con ID: {}", venta.getId());

        return ventaMapper.ventaResponse(venta);
    }

    @Override
    @Transactional
    public VentaResponse cancelar(Long id) {

        log.info("Cancelando venta con ID: {}", id);

        // Obtenemos la venta
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con ID: " + id));

        // Validar que no esté ya cancelada
        venta.cancelar();

        // Devolver stock a cada producto
        for (DetalleVenta detalle : venta.getDetalleVenta()) {
            Producto producto = detalle.getProducto();
            producto.aumentarCantidad(detalle.getCantidadProducto());
        }

        // Guardamos la venta
        ventaRepository.save(venta);

        log.info("Venta cancelada con ID: {}", venta.getId());

        return ventaMapper.ventaResponse(venta);

    }


    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarActivas() {
        log.info("Listando ventas activas...");

        List<Venta> ventas = ventaRepository.findByEstadoVenta(EstadoVenta.REGISTRADA);

        return ventas.stream()
                .map(ventaMapper::ventaResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaResponse> listarCanceladas() {
        log.info("Listando ventas canceladas...");

        List<Venta> ventas = ventaRepository.findByEstadoVenta(EstadoVenta.CANCELADA);

        return ventas.stream()
                .map(ventaMapper::ventaResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<ReporteVentasSucursalResponse> obtenerReportePorSucursal() {

        log.info("Generando reporte de ventas por sucursal...");

        List<Object[]> resultados = ventaRepository.obtenerReporteVentasPorSucursal();

        return resultados.stream()
                .map(row -> new ReporteVentasSucursalResponse(
                        ((Number) row[0]).longValue(),        // idSucursal
                        (String) row[1],                      // nombreSucursal
                        (BigDecimal) row[2],                  // totalFacturado
                        ((Number) row[3]).longValue()         // cantidadProductosVendidos
                ))
                .toList();
    }



}
