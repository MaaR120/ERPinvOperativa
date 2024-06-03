package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Venta")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class Venta extends Base{
    @NotNull
    @Column(name = "fecha_Venta")
    @Temporal(TemporalType.DATE)
    private Date fechaFacturacion;

    @NotNull
    @Column(name = "total_venta")
    private double totalVenta;

    @ManyToOne
    @JoinColumn(name = "venta_id")
    private Venta venta;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_venta")
    @Builder.Default
    private List<DetalleVenta> detalleVentas = new ArrayList<>();

    public void agregarDetalleVenta(DetalleVenta detalleVenta){

        detalleVentas.add(detalleVenta);
    }
}
