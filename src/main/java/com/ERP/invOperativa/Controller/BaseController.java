package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Base;
import org.springframework.http.ResponseEntity;

import java.awt.print.Pageable;
import java.io.Serializable;

public interface BaseController <E extends Base, ID extends Serializable> {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getAll(Pageable pageable);

    ResponseEntity<?> getOne(ID id);

    ResponseEntity<?> save(E entity);

    ResponseEntity<?> update(ID id, E entity);

    ResponseEntity<?> delete(ID id);

}
