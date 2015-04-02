package demo.mybatis.pojo;

import java.io.Serializable;

import org.mybatis.extension.auto.annotation.Column;
import org.mybatis.extension.auto.annotation.ForeignKey;
import org.mybatis.extension.auto.annotation.Id;
import org.mybatis.extension.auto.annotation.Table;
import org.mybatis.extension.auto.type.ColumnType;
import org.mybatis.extension.auto.type.IdType;

@Table
public class Role implements Serializable {

	@Column
	private int roleId;

	@Column
	private String roleName;

	@Column
	private String password;

	@Column(type = ColumnType.INT, fKey = { @ForeignKey(columnName = "USERID", tableName = "USER") })
	private String comment;

	@Column
	private String roleType;

	@Column
	private String groupId;

	@Column(type = ColumnType.INT, fKey = { @ForeignKey(columnName = "USERIDt", tableName = "USERt") })
	private String groupType;

	@Column
	@Id(idType = IdType.AUTO_INCREMENT)
	private int rolekeyId;

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
