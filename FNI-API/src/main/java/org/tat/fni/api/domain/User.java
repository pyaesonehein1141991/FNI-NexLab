/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.tat.fni.api.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;
import org.tat.fni.api.common.emumdata.Role;
import org.tat.fni.api.common.emumdata.WorkFlowType;
import org.tat.fni.api.common.emumdata.WorkflowTask;


@Entity
@Table(name = TableName.USER)
@TableGenerator(name = "USER_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "USER_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u ORDER BY u.name ASC"),
		@NamedQuery(name = "User.findByUsercode", query = "SELECT u FROM User u WHERE u.usercode = :usercode"),
		@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
		@NamedQuery(name = "User.changePassword", query = "UPDATE User u SET u.password = :newPassword WHERE u.usercode = :usercode"),
		@NamedQuery(name = "User.resetPassword", query = "UPDATE User u SET u.password = :defaultPassowrd WHERE u.usercode = :usercode") })
@EntityListeners(IDInterceptor.class)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String DEFAULT_PASSWORD = "password";
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "USER_GEN")
	private String id;

	private String usercode;
	private String password;
	private String name;
	private boolean disabled;
	private double authority;

	@Temporal(TemporalType.TIMESTAMP)
	private Date disabledDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@Transient
	private Branch loginBranch;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USER_BRANCH", joinColumns = @JoinColumn(name = "USERID"), inverseJoinColumns = @JoinColumn(name = "BRANCHID"))
	private List<Branch> accessBranchList;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLEID", referencedColumnName = "ID")
	private Role role;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USERID", referencedColumnName = "ID")
	private List<Authority> authorityList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "USERID", referencedColumnName = "ID")
	private List<AuthorityPermission> authorityPermissionList;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public User() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Date getDisabledDate() {
		return disabledDate;
	}

	public void setDisabledDate(Date disabledDate) {
		this.disabledDate = disabledDate;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public double getAuthority() {
		return authority;
	}

	public void setAuthority(double authority) {
		this.authority = authority;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Authority> getAuthorityList() {
		return authorityList;
	}

	public void setAuthorityList(List<Authority> authorityList) {
		this.authorityList = authorityList;
	}

	public List<AuthorityPermission> getAuthorityPermissionList() {
		return authorityPermissionList;
	}

	public void setAuthorityPermissionList(List<AuthorityPermission> authorityPermissionList) {
		this.authorityPermissionList = authorityPermissionList;
	}

	public List<WorkflowTask> getPermissions(WorkFlowType workFlowType) {
		for (Authority auth : authorityList) {
			if (auth.getWorkFlowType().equals(workFlowType)) {
				return auth.getPermissionList();
			}
		}
		return null;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public static String getDefaultPassword() {
		return DEFAULT_PASSWORD;
	}

	public Branch getLoginBranch() {
		return loginBranch;
	}

	public void setLoginBranch(Branch loginBranch) {
		this.loginBranch = loginBranch;
	}

	public List<Branch> getAccessBranchList() {
		return accessBranchList;
	}

	public void setAccessBranchList(List<Branch> accessBranchList) {
		this.accessBranchList = accessBranchList;
	}

	public boolean needChangePassword() {
		if ("admin".equals(usercode)) {
			return false;
		}
		if ("cGFzc3dvcmQ=".equals(password)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(authority);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + (disabled ? 1231 : 1237);
		result = prime * result + ((disabledDate == null) ? 0 : disabledDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((usercode == null) ? 0 : usercode.hashCode());
		result = prime * result + version;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Double.doubleToLongBits(authority) != Double.doubleToLongBits(other.authority))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (disabled != other.disabled)
			return false;
		if (disabledDate == null) {
			if (other.disabledDate != null)
				return false;
		} else if (!disabledDate.equals(other.disabledDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (usercode == null) {
			if (other.usercode != null)
				return false;
		} else if (!usercode.equals(other.usercode))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
