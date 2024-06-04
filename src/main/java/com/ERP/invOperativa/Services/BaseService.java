package com.ERP.invOperativa.Services;

import com.ERP.invOperativa.Entities.Base;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService <E extends Base, ID extends Serializable> {
    //Trae una lista de todas las entidades que se encuentran en nuestra base de datos
    List<E> findAll() throws Exception;
    //Busca entidades por Id que le pasamos

    public Page<E> findAll(Pageable pageable) throws Exception;
    //creamos nueva entidad
    public E save(E entity) throws Exception;
    //Actualizamos entidades
    public E update(ID id, E entity) throws Exception;
    //Eliminamos registros

    Boolean delete(ID id) throws Exception;

    Optional<E> findById(ID id);

}
