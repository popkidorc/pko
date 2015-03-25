package demo.mybatis.dao;

import java.util.List;

import demo.mybatis.pojo.User;

public interface IUserDao {

	public int insert(User user);

	public int update(User user);

	public int delete(String userName);

	public List<User> selectAll();

	public int countAll();

	public User findByUserName(String userName);
}
