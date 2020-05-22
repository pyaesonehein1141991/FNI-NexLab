package org.tat.fni.api.domain;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;



@Entity
@Table(name = TableName.SUBMENUVALUE)
@TableGenerator(name = "SUBMENUVALUE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "SUBMENUVALUE_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "SubMenuValue.findAll", query = "SELECT m FROM SubMenuValue m "),
		@NamedQuery(name = "SubMenuValue.findBySubMenuId", query = "SELECT m FROM SubMenuValue m WHERE m.subMenu.id = :subMenuId"),
		@NamedQuery(name = "SubMenuValue.findById", query = "SELECT m FROM SubMenuValue m WHERE m.id = :id") })
@EntityListeners(IDInterceptor.class)
public class SubMenuValue implements Serializable, Comparable<SubMenuValue> {

	private static final long serialVersionUID = -5175053478980727255L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "SUBMENUVALUE_GEN")
	private String id;
	private boolean flag;

	@OneToOne
	@JoinColumn(name = "SUBMENUID", referencedColumnName = "ID")
	private SubMenu subMenu;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "SUBMENUVALUEID", referencedColumnName = "ID")
	private List<MenuItemValue> menuItemValueList;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public SubMenuValue() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public SubMenu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(SubMenu subMenu) {
		this.subMenu = subMenu;
	}

	public List<MenuItemValue> getMenuItemValueList() {
		return menuItemValueList;
	}

	public void setMenuItemValueList(List<MenuItemValue> menuItemValueList) {
		this.menuItemValueList = menuItemValueList;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (flag ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((subMenu == null) ? 0 : subMenu.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
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
		SubMenuValue other = (SubMenuValue) obj;
		if (flag != other.flag)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (subMenu == null) {
			if (other.subMenu != null)
				return false;
		} else if (!subMenu.equals(other.subMenu))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	@Override
	public int compareTo(SubMenuValue other) {
		return subMenu.getPriority() - other.getSubMenu().getPriority();
	}

}