package com.alexis.almacen.services;

import com.alexis.almacen.dto.ventas.VentaRequest;
import com.alexis.almacen.dto.ventas.VentaResponse;

import java.util.List;

public interface VentaService {

    List<VentaResponse> listarActivas();

    List<VentaResponse> listarCanceladas();

    VentaResponse obtenerPorIdActiva(long id);

    VentaResponse registrar(VentaRequest request);

    VentaResponse cancelar(Long id);

}
