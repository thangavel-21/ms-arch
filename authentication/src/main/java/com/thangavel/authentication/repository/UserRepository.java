package com.thangavel.authentication.repository;

import com.thangavel.authentication.dao.UserDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserDao, Integer> {
    Optional<UserDao> findByEmail(String email);
//    Optional<UserDao> findById(UUID id);
}
