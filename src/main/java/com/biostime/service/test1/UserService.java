package com.biostime.service.test1;

import com.biostime.domain.test1.User;
import com.biostime.repository.test1.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 13006 on 2017/6/9.
 */
@Service
public class UserService {

    @Autowired
    @Qualifier("primaryJdbcTemplate")
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    public Integer totalCount() {
        return jdbcTemplate.queryForObject("select count(1) from user", Integer.class);
    }

    public List<User> findAll() {
        String sql = "select * from user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
    }

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }
}
