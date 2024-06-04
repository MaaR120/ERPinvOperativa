package com.ERP.invOperativa.DTO;

import com.ERP.invOperativa.DTO.DTODetalleVenta;
import lombok.*;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTOVenta {
    private List<DTODetalleVenta> detalleVentas;
    private Date fechaFacturacion;

    public List<DTODetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }
    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setDetalleVentas(List<DTODetalleVenta> detallesVenta) {
        this.detalleVentas = detallesVenta;}

    public void setFechaFacturacion(Date fechaFactuacion) {
        this.fechaFacturacion = fechaFactuacion;
    }
}