package com.alex.excel.users.repository;

import com.alex.excel.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

}
