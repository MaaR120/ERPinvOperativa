package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import lombok.Builder;
import java.util.Date;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Venta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Venta extends Base {
    @NotNull
    @Column(name = "fecha_Venta")
    @Temporal(TemporalType.DATE)
    private Date fechaFacturacion;

    @NotNull
    @Column(name = "total_venta")
    private double totalVenta;


    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Builder.Default
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public void agregarDetalleVenta(DetalleVenta detalleVenta) {
        detalleVenta.setVenta(this);
        detalleVentas.add(detalleVenta);
    }

    public double getTotalVenta () {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }
    public List<DetalleVenta> getDetalleVentas() {
        return detalleVentas;
    }
    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setDetalleVentas(List<DetalleVenta> detallesVenta) {
        this.detalleVentas = detallesVenta;}

    public void setFechaFacturacion(Date fechaFactuacion) {
        this.fechaFacturacion = fechaFactuacion;
    }

}
