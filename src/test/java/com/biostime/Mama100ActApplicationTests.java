package com.biostime;


import com.biostime.domain.mysql.User;
import com.biostime.domain.oracle.Message;
import com.biostime.repository.mysql.UserRepository;
import com.biostime.repository.oracle.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Mama100ActApplicationTests {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	@Qualifier("primaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate1;

	@Autowired
	@Qualifier("secondaryJdbcTemplate")
	protected JdbcTemplate jdbcTemplate2;

	@Before
	public void setUp() {
	}

	@Test
	public void test() throws Exception {
		//userRepository.save(new User("aa2a", 10));
		messageRepository.save(new Message("o1", "aaaaaa2aaaa"));
		/*userRepository.save(new User("aaa", 10));
		userRepository.save(new User("bbb", 20));
		userRepository.save(new User("ccc", 30));
		userRepository.save(new User("ddd", 40));
		userRepository.save(new User("eee", 50));

		Assert.assertEquals(5, userRepository.findAll().size());

		messageRepository.save(new Message("o1", "aaaaaaaaaa"));
		messageRepository.save(new Message("o2", "bbbbbbbbbb"));
		messageRepository.save(new Message("o3", "cccccccccc"));

		Assert.assertEquals(3, messageRepository.findAll().size());*/

		Integer count = jdbcTemplate1.queryForObject("select count(1) from user", Integer.class);
		System.out.println(count+"======================");
		//Assert.assertEquals("2", jdbcTemplate1.queryForObject("select count(1) from user", String.class));

	}

}
