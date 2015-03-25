package mybatis.demo.config;

import java.util.List;

import org.springframework.stereotype.Repository;

public interface UserDao {

	public int insert(User user);

	public int update(User user);

	public int delete(String userName);

	public List<User> selectAll();

	public int countAll();

	public User findByUserName(String userName);
}
