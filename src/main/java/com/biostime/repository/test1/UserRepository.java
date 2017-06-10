package com.biostime.repository.test1;

import com.biostime.domain.test1.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 13006 on 2017/6/8.
 */
public interface UserRepository extends JpaRepository<User, Long> {


}
