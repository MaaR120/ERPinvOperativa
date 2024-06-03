package com.ERP.invOperativa.DTO;

import com.ERP.invOperativa.Entities.Articulo;
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
}
