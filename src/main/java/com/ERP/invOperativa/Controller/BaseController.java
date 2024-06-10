package com.ERP.invOperativa.Controller;

import com.ERP.invOperativa.Entities.Base;
import com.ERP.invOperativa.Services.BaseService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;
import java.util.List;

public interface BaseController <E extends Base, ID extends Serializable> {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getAll(Pageable pageable);

    ResponseEntity<?> getOne(ID id);

    ResponseEntity<?> save(E entity);

    ResponseEntity<?> update(ID id, E entity);

    ResponseEntity<?> delete(ID id);


}
