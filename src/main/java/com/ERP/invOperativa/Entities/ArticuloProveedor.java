package com.ERP.invOperativa.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaVigencia;

    @Column(name = "CostoAlmacenamiento")
    private Double CostoAlmacenamiento;

    @Column(name = "Tiempo_Demora")
    private int tiempoDemora;

    //precio compra del producto

    @NotNull
    @Column(name = "precio_articulo_Proveedor")
    private double precioArticuloProveedor;

    @NotNull
    @Column(name = "predeterminado")
    private boolean predeterminado;

    @Column(name = "cantidad_Predeterminada")
    private Integer cantidadPredeterminada;

    @ManyToOne()
    @JoinColumn(name = "id_Articulo")  //Clave foranea de la entidad articulo
    private Articulo articulo;


//    @Column(name = "lote_optimo")
//    private Double loteOptimo;
//
//    @Column(name = "punto_pedido")
//    private Double puntoPedido;
//
//    @Column(name = "stock_Seguridad")
//    private Double stockSeguridad;
//
//    @Column(name = "cgi")
//    private Double cgi;

    @ManyToOne()
    @JoinColumn(name = "id_proveedor") //Clave foranea de la entidad proveedor
    private Proveedor proveedor;

    // Método para calcular el costo de almacenamiento basado en el precio del producto y un interés constante
    public double calcularCostoAlmacenamiento() {
        setCostoAlmacenamiento(precioArticuloProveedor*0.20);
        return this.precioArticuloProveedor * 0.20;
    }


}
