package br.com.cafebinario.register.vo.user;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserAuthenticationVO implements Serializable {

	private static final long serialVersionUID = 4529559441140157881L;
	
	public static UserAuthenticationVO createUserVOJUnitValidTest(final String securePassword) {
		final NewUserVO newUserVO = NewUserVO.createUserVOJUnitInvalidTest();
		final UserAuthenticationVO userAuthenticationVO = new UserAuthenticationVO();
		userAuthenticationVO.setDomain(newUserVO.getDomain());
		userAuthenticationVO.setEmail(newUserVO.getEmail());
		userAuthenticationVO.setNick(newUserVO.getNick());
		userAuthenticationVO.setPassword(securePassword);
		return userAuthenticationVO;
	}
	
	public static UserAuthenticationVO create(final NewUserVO newUserVO, final String securePassword) {
		final UserAuthenticationVO userAuthenticationVO = new UserAuthenticationVO();
		userAuthenticationVO.setDomain(newUserVO.getDomain());
		userAuthenticationVO.setEmail(newUserVO.getEmail());
		userAuthenticationVO.setNick(newUserVO.getNick());
		userAuthenticationVO.setPassword(securePassword);
		return userAuthenticationVO;
	}
	
	public UserAuthenticationVO(){
		
	}
	
	public UserAuthenticationVO(UserAuthenticationVO original, String decriptyUserPassword) {
		this.setDomain(original.getDomain());
		this.setEmail(original.getEmail());
		this.setNick(original.getNick());
		this.setPassword(decriptyUserPassword);
	}

	@NotBlank(message = "invalid blank value")
	@Pattern(regexp = "[a-zA-Z]+", message = "invalid caracter, expected [a-zA-Z]+")
	@Size(min = 1, max = 20, message = "invalid length, expected min=1 and max=20")
	private String domain;

	@Email(message = "invalid email, expected valid e-mail format")
	private String email;

	@Pattern(regexp = "[a-zA-Z]+", message = "invalid caracter, expected [a-zA-Z]+")
	@Size(min = 1, max = 20, message = "invalid length, expected min=1 and max=20")
	private String nick;

	@NotBlank(message = "invalid blank value")
	@Size(min = 6, max = 20, message = "invalid length, expected min=6 and max=20")
	private String password;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		UserAuthenticationVO other = (UserAuthenticationVO) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
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
		return true;
	}

	@Override
	public String toString() {
		return "UserAuthenticationVO [domain=" + domain + ", email=" + email + ", nick=" + nick + ", password="
				+ password + "]";
	}
}
