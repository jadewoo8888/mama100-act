package com.biostime.repository.test2;

import com.biostime.domain.test2.Message;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 13006 on 2017/6/8.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {


}