package demo.mybatis.service;

import demo.mybatis.pojo.User;

public interface IUserService {
	public User find(String username);
}
