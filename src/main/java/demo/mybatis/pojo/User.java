package demo.mybatis.pojo;

import java.io.Serializable;

import org.mybatis.extension.auto.annotation.Column;
import org.mybatis.extension.auto.annotation.ForeignKey;
import org.mybatis.extension.auto.annotation.Id;
import org.mybatis.extension.auto.annotation.Table;
import org.mybatis.extension.auto.type.IdType;

@Table
public class User implements Serializable {

	@Column
	@Id
	private int userId;

	@Column(comment = "userName")
	private String userName;

	@Column
	private String password;

	@Column(fKey = { @ForeignKey(columnName = "IDTEST", tableName = "TEST") })
	private String comment;

	@Column
	private String userType;

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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
}
