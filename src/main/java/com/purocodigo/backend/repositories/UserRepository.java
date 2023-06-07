package com.purocodigo.backend.repositories;

import com.purocodigo.backend.entity.PostEntity;
import com.purocodigo.backend.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

    List<PostEntity> getByUserIdOrderCreatedAtDesc(long id);
}
