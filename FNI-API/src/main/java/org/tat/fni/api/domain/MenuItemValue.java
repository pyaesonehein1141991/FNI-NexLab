package org.tat.fni.api.domain;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.tat.fni.api.domain.MenuItem;
import org.tat.fni.api.common.IDInterceptor;
import org.tat.fni.api.common.TableName;
import org.tat.fni.api.common.UserRecorder;



@Entity
@Table(name = TableName.MENUITEMVALUE)
@TableGenerator(name = "MENUITEMVALUE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MENUITEMVALUE_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "MenuItemValue.findAll", query = "SELECT m FROM MenuItemValue m "),
		@NamedQuery(name = "MenuItemValue.findByMenuItemId", query = "SELECT m FROM MenuItemValue m WHERE m.menuItem.id = :menuItemId"),
		@NamedQuery(name = "MenuItemValue.findById", query = "SELECT m FROM MenuItemValue m WHERE m.id = :id") })
@EntityListeners(IDInterceptor.class)
public class MenuItemValue implements Serializable, Comparable<MenuItemValue> {
	private static final long serialVersionUID = -6321603825355259602L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MENUITEMVALUE_GEN")
	private String id;
	private boolean flag;

	@OneToOne
	@JoinColumn(name = "MENUITEMID", referencedColumnName = "ID")
	private MenuItem menuItem;

	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public MenuItemValue() {
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

	public MenuItem getMenuItem() {
		return menuItem;
	}

	public void setMenuItem(MenuItem menuItem) {
		this.menuItem = menuItem;
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
		result = prime * result + ((menuItem == null) ? 0 : menuItem.hashCode());
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
		MenuItemValue other = (MenuItemValue) obj;
		if (flag != other.flag)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (menuItem == null) {
			if (other.menuItem != null)
				return false;
		} else if (!menuItem.equals(other.menuItem))
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
	public int compareTo(MenuItemValue other) {
		return menuItem.getPriority() - other.getMenuItem().getPriority();
	}

}
