package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;


@Entity
@Table(name = "ArticuloProveedor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArticuloProveedor extends Base{
    @Column(name = "fecha_Vigencia")
    @Temporal(TemporalType.DATE)
    private Date fechaVigencia;



    @Column(name = "Tiempo_Demora")
    private int tiempoDemora;

    //precio compra del producto

    @NotNull
    @Column(name = "precio_articulo_Proveedor")
    private double precioArticuloProveedor;

    @NotNull
    @Column(name = "predeterminado")
    private boolean predeterminado;

    @ManyToOne()
    @JoinColumn(name = "id_Articulo")  //Clave foranea de la entidad articulo
    private Articulo articulo;



    @ManyToOne()
    @JoinColumn(name = "id_proveedor") //Clave foranea de la entidad proveedor
    private Proveedor proveedor;

}
