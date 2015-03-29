package demo.mybatis.pojo;

import java.io.Serializable;

import org.mybatis.extension.auto.annotation.Entity;
import org.mybatis.extension.auto.annotation.Field;
import org.mybatis.extension.auto.annotation.ForeignKey;
import org.mybatis.extension.auto.annotation.Id;

@Entity
public class User implements Serializable {

	@Field
	@Id
	private int userId;

	@Field
	private String userName;

	@Field
	private String password;

	@Field
	private String comment;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
