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
@Table(name = TableName.MAINMENUVALUE)
@TableGenerator(name = "MAINMENUVALUE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "MAINMENUVALUE_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "MainMenuValue.findAll", query = "SELECT m FROM MainMenuValue m "),
		@NamedQuery(name = "MainMenuValue.findByMainMenuId", query = "SELECT m FROM MainMenuValue m WHERE m.mainMenu.id = :mainMenuId"),
		@NamedQuery(name = "MainMenuValue.findById", query = "SELECT m FROM MainMenuValue m WHERE m.id = :id") })
@EntityListeners(IDInterceptor.class)
public class MainMenuValue implements Serializable, Comparable<MainMenuValue> {

	private static final long serialVersionUID = 1983941086506677606L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "MAINMENUVALUE_GEN")
	private String id;
	private boolean flag;

	@OneToOne
	@JoinColumn(name = "MAINMENUID", referencedColumnName = "ID")
	private MainMenu mainMenu;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "MAINMENUVALUEID", referencedColumnName = "ID")
	private List<SubMenuValue> subMenuValueList;

	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public MainMenuValue() {

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

	public MainMenu getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public List<SubMenuValue> getSubMenuValueList() {
		return subMenuValueList;
	}

	public void setSubMenuValueList(List<SubMenuValue> subMenuValueList) {
		this.subMenuValueList = subMenuValueList;
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
		result = prime * result + ((mainMenu == null) ? 0 : mainMenu.hashCode());
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
		MainMenuValue other = (MainMenuValue) obj;
		if (flag != other.flag)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mainMenu == null) {
			if (other.mainMenu != null)
				return false;
		} else if (!mainMenu.equals(other.mainMenu))
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
	public int compareTo(MainMenuValue other) {
		return mainMenu.getPriority() - other.getMainMenu().getPriority();
	}
}
