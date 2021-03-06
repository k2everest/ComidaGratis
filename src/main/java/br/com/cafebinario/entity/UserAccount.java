package br.com.cafebinario.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import br.com.cafebinario.cache.BeanCacheable;
import br.com.cafebinario.register.vo.user.NewUserVO;

@Entity
public class UserAccount implements Serializable, BeanCacheable<UserAccount> {

	private static final long serialVersionUID = -1611667244738168361L;

	@Id
	@Column(length = 35, nullable = false)
	private String email;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="domain")
	private DomainAccount domainAccount;

	@Column(length = 20, unique = true, nullable = false)
	private String nick;

	@Column(length = 96, nullable = false)
	private String password;

	@Column(length = 1024, nullable = false)
	private String secureKey;

	@Column(nullable = false)
	private Boolean authorized;

	@Column(nullable = false)
	private Date creationDate;

	@Column(nullable = true)
	private Date alterDate;

	public UserAccount(final NewUserVO userVO, final String secureKey, String securePassword) {
		super();
		final DomainAccount accountDomain = new DomainAccount();
		accountDomain.setDomain(userVO.getDomain());
		
		this.setAccountDomain(accountDomain);
		this.setEmail(userVO.getEmail());
		this.setNick(userVO.getNick());
		this.setPassword(securePassword);
		this.setSecureKey(secureKey);
		this.setAuthorized(Boolean.FALSE);
		this.setCreationDate(new Date(System.currentTimeMillis()));
	}

	public UserAccount() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DomainAccount getDomainAccount() {
		return domainAccount;
	}

	public void setAccountDomain(DomainAccount domainAccount) {
		this.domainAccount = domainAccount;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecureKey() {
		return secureKey;
	}

	public void setSecureKey(String secureKey) {
		this.secureKey = secureKey;
	}

	public Boolean getAuthorized() {
		return authorized;
	}

	public void setAuthorized(Boolean authorized) {
		this.authorized = authorized;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getAlterDate() {
		return alterDate;
	}

	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alterDate == null) ? 0 : alterDate.hashCode());
		result = prime * result + ((authorized == null) ? 0 : authorized.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((domainAccount == null) ? 0 : domainAccount.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((secureKey == null) ? 0 : secureKey.hashCode());
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
		UserAccount other = (UserAccount) obj;
		if (alterDate == null) {
			if (other.alterDate != null)
				return false;
		} else if (!alterDate.equals(other.alterDate))
			return false;
		if (authorized == null) {
			if (other.authorized != null)
				return false;
		} else if (!authorized.equals(other.authorized))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (domainAccount == null) {
			if (other.domainAccount != null)
				return false;
		} else if (!domainAccount.equals(other.domainAccount))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (nick == null) {
			if (other.nick != null)
				return false;
		} else if (!nick.equals(other.nick))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (secureKey == null) {
			if (other.secureKey != null)
				return false;
		} else if (!secureKey.equals(other.secureKey))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", domainAccount=" + domainAccount + ", nick=" + nick + ", authorized=" + authorized
				+ ", creationDate=" + creationDate + ", alterDate=" + alterDate + "]";
	}

	public static Order sortByUserAlterDate() {
		return new Order(Direction.DESC, "alterDate");
	}
}