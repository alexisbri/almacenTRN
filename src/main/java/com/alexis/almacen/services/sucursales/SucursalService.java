package com.alexis.almacen.services.sucursales;

import com.alexis.almacen.dto.sucursales.SucursalRequest;
import com.alexis.almacen.dto.sucursales.SucursalResponse;
import com.alexis.almacen.entities.Sucursal;

import java.util.List;

public interface SucursalService {

    List<SucursalResponse> listar();

    SucursalResponse obtenerPorId(long id);

    SucursalResponse registrar(SucursalRequest request);

    SucursalResponse actualizar(SucursalRequest request, Long id);

    void eliminar(Long id);

}
