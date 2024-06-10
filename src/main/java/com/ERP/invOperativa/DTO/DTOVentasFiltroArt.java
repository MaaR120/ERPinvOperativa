package com.ERP.invOperativa.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DTOVentasFiltroArt {
    private Date fechaFactruacion;
    private int cantidad;
    private Long idArt;

    public Date getFechaFactruacion() {
        return fechaFactruacion;
    }

    public void setFechaFactruacion(Date fechaFactruacion) {
        this.fechaFactruacion = fechaFactruacion;
    }



    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Long getIdArt() {
        return idArt;
    }

    public void setIdArt(Long idArt) {
        this.idArt = idArt;
    }


}
