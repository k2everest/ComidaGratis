package br.com.cafebinario.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import br.com.cafebinario.cache.BeanCacheable;
import br.com.cafebinario.register.vo.domain.NewDomainVO;

@Entity
public class DomainAccount implements Serializable, BeanCacheable<DomainAccount> {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 30, nullable = false)
	private String domain;

	@Column(length = 250, nullable = false)
	private String description;

	@Column(length = 35, nullable = false)
	private String emailOwner;

	@Column(nullable = false)
	private Date createDate;

	@Column
	private Date alterDate;

	@OneToMany(mappedBy = "domainAccount")
	private List<UserAccount> usersAccount;

	public DomainAccount(NewDomainVO domainVO) {
		this.setDomain(domainVO.getDomain());
		this.setDescription(domainVO.getDescription());
		this.setEmailOwner(domainVO.getEmailOwner());
		this.setCreateDate(new Date(System.currentTimeMillis()));
	}

	public DomainAccount() {

	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmailOwner() {
		return emailOwner;
	}

	public void setEmailOwner(String emailOwner) {
		this.emailOwner = emailOwner;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getAlterDate() {
		return alterDate;
	}

	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}

	public List<UserAccount> getUsersAccount() {
		return usersAccount;
	}

	public void setUsersAccount(List<UserAccount> usersAccount) {
		this.usersAccount = usersAccount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alterDate == null) ? 0 : alterDate.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((emailOwner == null) ? 0 : emailOwner.hashCode());
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
		DomainAccount other = (DomainAccount) obj;
		if (alterDate == null) {
			if (other.alterDate != null)
				return false;
		} else if (!alterDate.equals(other.alterDate))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (emailOwner == null) {
			if (other.emailOwner != null)
				return false;
		} else if (!emailOwner.equals(other.emailOwner))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DomainAccount [domain=" + domain + ", description=" + description + ", emailOwner=" + emailOwner
				+ ", createDate=" + createDate + ", alterDate=" + alterDate + "]";
	}

	public static Order sortByUserAlterDate() {
		return new Order(Direction.DESC, "alterDate");
	}
}
