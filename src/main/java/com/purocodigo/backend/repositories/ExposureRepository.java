package com.purocodigo.backend.repositories;

import com.purocodigo.backend.entity.ExposureEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface ExposureRepository extends CrudRepository<ExposureEntity, Long> {

    static ExposureEntity findById(long id);

}
