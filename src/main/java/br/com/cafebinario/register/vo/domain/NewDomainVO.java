package br.com.cafebinario.register.vo.domain;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class NewDomainVO implements Serializable {

	private static final long serialVersionUID = 7856300122404507564L;

	@NotBlank(message = "invalid blank value")
	@Pattern(regexp = "[a-zA-Z]+", message = "invalid caracter, expected [a-zA-Z]+")
	@Size(min = 1, max = 20, message = "invalid length, expected min=1 and max=20")
	private String domain;

	@NotBlank(message = "invalid blank value")
	@Size(min = 1, max = 250, message = "invalid length, expected min=1 and max=250")
	private String description;

	@NotBlank(message = "invalid blank value")
	@Email(message = "invalid email, expected valid e-mail format")
	private String emailOwner;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		NewDomainVO other = (NewDomainVO) obj;
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
		return "NewDomainVO [domain=" + domain + ", description=" + description + ", emailOwner=" + emailOwner + "]";
	}
}
