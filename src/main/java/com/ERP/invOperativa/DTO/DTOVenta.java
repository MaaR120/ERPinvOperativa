package com.ERP.invOperativa.DTO;

import com.ERP.invOperativa.DTO.DTODetalleVenta;
import com.ERP.invOperativa.Entities.DetalleVenta;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTOVenta {
    private Date fechaFacturacion;
    private double totalVenta;
    private ArrayList<DTODetalleVenta> detalleVentas;

    // Getters y Setters
    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public ArrayList<DTODetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }

    public void setDetalleVentas(ArrayList<DTODetalleVenta> detalles) {
        this.detalleVentas = detalles;
    }




}