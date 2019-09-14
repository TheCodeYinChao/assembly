package com.oauth.dao;

import com.oauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Admin on 2019/6/8.
 */

@Repository
public interface UserRepositoty extends JpaRepository<User,Long> {
    @Query("select u from User u where u.name = ?1")
    User findByName(String name);
}
