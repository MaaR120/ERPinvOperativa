package com.ERP.invOperativa.DTO;

import lombok.*;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTOVentaBACK {
    private Date fechaFacturacion;
    private List<DTODetalleVentaBACK> detalleVentas;
}
