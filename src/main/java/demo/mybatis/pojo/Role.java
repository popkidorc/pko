package demo.mybatis.pojo;

import java.io.Serializable;

import org.mybatis.extension.auto.annotation.Entity;
import org.mybatis.extension.auto.annotation.Field;
import org.mybatis.extension.auto.annotation.ForeignKey;
import org.mybatis.extension.auto.annotation.Id;
import org.mybatis.extension.auto.annotation.IdType;

@Entity
public class Role implements Serializable {

	@Field
	@Id(idType = IdType.AUTO_INCREMENT)
	private int roleId;

	@Field
	private String roleName;

	@Field
	private String password;

	@Field(fKey = { @ForeignKey(fieldName = "USER", tableName = "COMMENT") })
	private String comment;

	@Field
	private String roleType;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

}
