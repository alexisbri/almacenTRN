package com.alexis.almacen.services.sucursales;

import com.alexis.almacen.dto.sucursales.SucursalRequest;
import com.alexis.almacen.dto.sucursales.SucursalResponse;
import com.alexis.almacen.entities.Sucursal;
import com.alexis.almacen.exceptions.RecursoNoEncontradoException;

import com.alexis.almacen.mappers.SucursalMapper;
import com.alexis.almacen.repositories.SucursalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository sucursalRepository;

    private final SucursalMapper sucursalMapper;


    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listar() {

        log.info("Listar todas las sucursales");

        return sucursalRepository.findAll().stream()
                .map(sucursalMapper::entidadResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SucursalResponse obtenerPorId(long id) {
        return sucursalMapper.entidadResponse(obtenerSucursalOException(id));
    }


    @Override
    public SucursalResponse registrar(SucursalRequest request) {
        log.info("Registrando nueva sucursal...");

        validarDatosUnicos(request);

        Sucursal sucursal = sucursalMapper.requestAEntidad(request);

        sucursalRepository.save(sucursal);

        log.info("Nueva sucursal {} registrada", sucursal.getNombre());

        return sucursalMapper.entidadResponse(sucursal);
    }

    @Override
    public SucursalResponse actualizar(SucursalRequest request, Long id) {

        Sucursal sucursal = obtenerSucursalOException(id);

        log.info("Actualizar suursal con id: {}", id);

        validarCambiosUnicos(request, id);

        sucursal.actualizar(
                request.nombre(),
                request.direccion()
        );

        // La clase seguira actualizando sin nececidad de indicarle, solo porque el @Transactional esta colocado.
        // @Transactional hace commit al terminar pero si falla hace rollback
        /// productoRepository.save(producto);

        log.info("Sucursal con id {} actualizada", id);

        return sucursalMapper.entidadResponse(sucursal);

    }

    @Override
    public void eliminar(Long id) {
        Sucursal sucursal = obtenerSucursalOException(id);

        sucursalRepository.delete(sucursal);

        log.info("Sucursal con id {} eliminar", id);

    }

    private Sucursal obtenerSucursalOException(Long id) {
        log.info("Buscando sucursal con id: {}", id);

        return sucursalRepository.findById(id).orElseThrow(
                () -> new RecursoNoEncontradoException("Sucursal no encontrada con id: " + id));
    }

    private void validarDatosUnicos(SucursalRequest request) {

        log.info("Validando nombre único...");
        if (sucursalRepository.existsByNombreIgnoreCase(request.nombre().trim()))
            throw new IllegalArgumentException("Ya se encuentra una sucursal con el nombre de: " + request.nombre());

    }

    private void validarCambiosUnicos(SucursalRequest request, Long id) {

        log.info("Validando nombre único...");
        if (sucursalRepository.existsByNombreIgnoreCaseAndIdNot(request.nombre().trim(), id))
            throw new IllegalArgumentException("Ya se encuentra una sucursal con el nombre de: " + request.nombre());

    }


}
