package br.com.cafebinario.register.vo.user;

import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Base64Utils;

public class NewUserVO implements Serializable {

	private static final long serialVersionUID = 7287308776815133697L;

	public static NewUserVO createUserVOJUnitValidTest() {
		final NewUserVO userVO = new NewUserVO();
		userVO.setDomain("cafebinario");
		userVO.setEmail("alexandre.msl@gmail.com");
		userVO.setNick("Jim");
		userVO.setPassword("123456");
		userVO.setChanllerger(123456);
		return userVO;
	}
	
	public static NewUserVO createUserVOJUnitValidTest(String email, String nick, String password) {
		final NewUserVO userVO = new NewUserVO();
		userVO.setDomain("cafebinario");
		userVO.setEmail(email);
		userVO.setNick(nick);
		userVO.setPassword(password);
		userVO.setChanllerger(123456);
		return userVO;
	}
	
	public static NewUserVO createUserVOJUnitInvalidTest() {
		final NewUserVO userVO = new NewUserVO();
		userVO.setDomain(null);
		userVO.setEmail(null);
		userVO.setNick(null);
		userVO.setPassword(null);
		userVO.setChanllerger(null);
		return userVO;
	}

	@NotBlank(message = "invalid blank value")
	@Pattern(regexp = "[a-zA-Z]+", message = "invalid caracter, expected [a-zA-Z]+")
	@Size(min = 1, max = 20, message = "invalid length, expected min=1 and max=20")
	private String domain;

	@NotBlank(message = "invalid blank value")
	@Email(message = "invalid email, expected valid e-mail format")
	private String email;

	@NotBlank(message = "invalid blank value")
	@Pattern(regexp = "[a-zA-Z]+", message = "invalid caracter, expected [a-zA-Z]+")
	@Size(min = 1, max = 20, message = "invalid length, expected min=1 and max=20")
	private String nick;

	@NotBlank(message = "invalid blank value")
	@Size(min = 6, max = 20, message = "invalid length, expected min=6 and max=20")
	private String password;

	@Digits(integer = 8, fraction = 0, message = "invalid number digit or length, valid number 0-9 and expected max=8")
	@NotNull(message = "invalid value null, expected Integer")
	private Integer chanllerger;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public Integer getChanllerger() {
		return chanllerger;
	}

	public void setChanllerger(Integer chanllerger) {
		this.chanllerger = chanllerger;
	}

	public String getInternalString() {
		return new String(Base64Utils.encode(
				(toString() + " HashCode [" + hashCode() + "] Chanllerger [" + getChanllerger() + "]").getBytes()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chanllerger == null) ? 0 : chanllerger.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
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
		NewUserVO other = (NewUserVO) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "NewUserVO [domain=" + domain + ", email=" + email + ", nick=" + nick + ", chanllerger=" + chanllerger
				+ "]";
	}
}
