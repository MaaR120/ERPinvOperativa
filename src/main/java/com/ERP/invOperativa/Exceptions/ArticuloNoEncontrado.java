package com.ERP.invOperativa.Exceptions;

public class ArticuloNoEncontrado extends RuntimeException {
    public ArticuloNoEncontrado(Long id) {
        super("Artículo no encontrado con id: " + id);
    }
}