package com.ERP.invOperativa.DTO;

import com.ERP.invOperativa.Entities.DetalleVenta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DTOVenta {
    private ArrayList<DTODetalleVenta> detalleVentas;
    private Date fechaFacturacion;
}
