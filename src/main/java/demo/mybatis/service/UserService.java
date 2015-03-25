package demo.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.mybatis.dao.IUserDao;
import demo.mybatis.pojo.User;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Override
	public User find(String username) {
		// TODO Auto-generated method stub
		return userDao.findByUserName(username);
	}
}
