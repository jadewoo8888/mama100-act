package com.biostime.repository.mysql;

import com.biostime.domain.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 13006 on 2017/6/8.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByIdAndNameAndAge(Long id, String name, Integer age);
}
