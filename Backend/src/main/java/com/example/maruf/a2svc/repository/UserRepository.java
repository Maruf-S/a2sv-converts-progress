package com.example.maruf.a2svc.repository;

import com.example.maruf.a2svc.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    @Query("SELECT u FROM User u WHERE u.name = ?1")
    Optional<User> findByName(String name);

//    @Query("select u from User u where upper(u.name) = upper(?1)")
//    Optional<User> findByName(String name);
}
