package com.ERP.invOperativa.Entities;

import com.ERP.invOperativa.Enum.EstadoOrdenCompra;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;


@Entity
@Table(name = "OrdenCompra")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class OrdenCompra extends Base{
//    @NotNull
    //Falta el calculo
    @Column(name = "fecha_Entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;

//    @NotNull
    @Column(name = "fecha_Inicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

//    @NotNull
    @Column(name = "fecha_Ultimo_Cambio")
    @Temporal(TemporalType.DATE)
    private Date fechaUltimoCambio;

    @NotNull
    @Column(name = "total_Orden_Compra")
    private double totalOrden;

    @NotNull
    @Column(name = "Estado_Orden")
    @Enumerated(EnumType.STRING)
    private EstadoOrdenCompra estadoOrdenCompra;

    @NotNull
    @Column(name = "Cantidad")
    private int cantidad;


    @ManyToOne()
    @JoinColumn(name = "id_articulo")
    private Articulo articulo;


    @ManyToOne()
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

}
