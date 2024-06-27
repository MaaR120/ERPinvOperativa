package com.ERP.invOperativa.DTO;

import com.ERP.invOperativa.Entities.Articulo;
import com.ERP.invOperativa.Entities.ArticuloProveedor;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTOInventario {

    public Long idArticulo;

    public Long idProveedor;

    public int tiempoDemora;

    public static final double DISTRUBUCION = 1.64; // z constante

    public String proveedor;

    //precio compra del prod
    public double precioArticuloProveedor;

    public int costoPedido;

    public static final double INTERES_ALMACENAMIENTO = 0.20; // Ejemplo de interés constante

    public double costoAlmacenamiento;

    public double loteOptimo;

    public double CGI;

    public double puntoPedido;

    public double stockSeguridad;

    public double media;

    public double demandaPromedio;

    public double desviacionDemanda;

    public int stock;
}
