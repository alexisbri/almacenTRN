package com.alexis.almacen.mappers;

import com.alexis.almacen.dto.sucursales.SucursalRequest;
import com.alexis.almacen.dto.sucursales.SucursalResponse;
import com.alexis.almacen.entities.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public Sucursal requestAEntidad(SucursalRequest request) {

        if (request == null) return null;

        return Sucursal.builder()
                .nombre(request.nombre().trim())
                .direccion(request.direccion().trim())
                .build();

    }

    public SucursalResponse entidadResponse(Sucursal sucursal) {

        if (sucursal == null) return null;

        return new SucursalResponse(

                sucursal.getId(),
                sucursal.getNombre(),
                sucursal.getDireccion()

        );

    }

}

