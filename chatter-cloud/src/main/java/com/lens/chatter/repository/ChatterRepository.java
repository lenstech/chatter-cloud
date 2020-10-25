package com.lens.chatter.repository;

import org.mapstruct.Named;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ChatterRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

    @Override
    default Optional<T> findById(ID id){
        return Optional.ofNullable(findOneById(id));
    }

    @Named("findOneById")
    T findOneById(ID id);

    @Named("findAllByIdIn")
    List<T> findAllByIdIn(List<ID> ids);
    
    void deleteById(ID id);

    void delete(T entity);
}
