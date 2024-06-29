package com.ERP.invOperativa.DTO;
import com.ERP.invOperativa.Entities.Articulo;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTODetalleVenta {
    private Long idArticulo;
    private int cantidad;
    private Articulo articulo;
    private Double subtotal;

}