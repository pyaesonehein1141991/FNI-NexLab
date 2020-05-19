package org.tat.fni.api.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.tat.fni.api.common.emumdata.ActiveStatus;
import org.tat.fni.api.domain.Product;



@Entity
@Table(name = TableName.PRODUCTPROCESS)
@TableGenerator(name = "PRODUCTPROCESS_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "PRODUCTPROCESS_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ProductProcess.findById", query = "SELECT a FROM ProductProcess a WHERE a.id = :id"),
		@NamedQuery(name = "ProductProcess.findAll", query = "SELECT a FROM ProductProcess a") })
@EntityListeners(IDInterceptor.class)
public class ProductProcess implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "PRODUCTPROCESS_GEN")
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROCESSID", referencedColumnName = "ID")
	private Process process;

	@Embedded
	private ProductProcessCriteria productProcessCriteria;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "productProcess", orphanRemoval = true)
	private List<ProductProcessQuestionLink> productProcessQuestionLinkList;

	private String questionSetNo;

	@Enumerated(value = EnumType.STRING)
	private ActiveStatus activeStatus;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public ProductProcess() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public ProductProcessCriteria getProductProcessCriteria() {
		if (productProcessCriteria == null) {
			productProcessCriteria = new ProductProcessCriteria();
		}
		return productProcessCriteria;
	}

	public void setProductProcessCriteria(ProductProcessCriteria productProcessCriteria) {
		this.productProcessCriteria = productProcessCriteria;
	}

	public List<ProductProcessQuestionLink> getProductProcessQuestionLinkList() {
		if (productProcessQuestionLinkList == null) {
			productProcessQuestionLinkList = new ArrayList<ProductProcessQuestionLink>();
		}
		return productProcessQuestionLinkList;
	}

	public void setProductProcessQuestionLinkList(List<ProductProcessQuestionLink> productProcessQuestionLinkList) {
		this.productProcessQuestionLinkList = productProcessQuestionLinkList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getQuestionSetNo() {
		return questionSetNo;
	}

	public void setQuestionSetNo(String questionSetNo) {
		this.questionSetNo = questionSetNo;
	}

	public ActiveStatus getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(ActiveStatus activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void addProcessQuestionLink(ProductProcessQuestionLink processQuestionLink) {
		processQuestionLink.setProductProcess(this);
		getProductProcessQuestionLinkList().add(processQuestionLink);
	}

	public void removeProcessProductQuestionLink(ProductProcessQuestionLink processQuestionLink) {
		if (getProductProcessQuestionLinkList().contains(processQuestionLink)) {
			getProductProcessQuestionLinkList().remove(processQuestionLink);
		}
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	// public BuildingOccupationType getBuildingOccupationType() {
	// return buildingOccupationType;
	// }
	//
	// public void setBuildingOccupationType(BuildingOccupationType
	// buildingOccupationType) {
	// this.buildingOccupationType = buildingOccupationType;
	// }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((activeStatus == null) ? 0 : activeStatus.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((process == null) ? 0 : process.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((questionSetNo == null) ? 0 : questionSetNo.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		ProductProcess other = (ProductProcess) obj;
		if (activeStatus != other.activeStatus)
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (process == null) {
			if (other.process != null)
				return false;
		} else if (!process.equals(other.process))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (questionSetNo == null) {
			if (other.questionSetNo != null)
				return false;
		} else if (!questionSetNo.equals(other.questionSetNo))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
