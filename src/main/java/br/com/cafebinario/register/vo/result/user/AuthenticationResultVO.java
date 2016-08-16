package br.com.cafebinario.register.vo.result.user;

import java.io.Serializable;

import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;

public class AuthenticationResultVO implements Serializable {

	private static final long serialVersionUID = 2229600077307767651L;
	
	public static AuthenticationResultVO createUserBlanck() {
		AuthenticationResultVO authenticationResultVO = new AuthenticationResultVO("", ResultVOBuilder.BLANCK());
		return authenticationResultVO;
	}

	private final ResultVO result;
	private final String token;

	public AuthenticationResultVO(final String token) {
		this.result = ResultVOBuilder.SUCCESS();
		this.token = token;
	}

	public AuthenticationResultVO() {
		this.result = ResultVOBuilder.ERROR_AUTHENTICANTION();
		this.token = null;
	}

	public AuthenticationResultVO(final String token, ResultVO result) {
		this.token = token;
		this.result = result;
	}
	
	public ResultVO getResult() {
		return result;
	}

	public String getToken() {
		return token;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		AuthenticationResultVO other = (AuthenticationResultVO) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuthenticationResultVO [result=" + result + "]";
	}
}