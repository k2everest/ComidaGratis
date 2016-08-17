package br.com.cafebinario.register.vo.result;

import java.io.Serializable;
import java.util.List;

import br.com.cafebinario.register.vo.result.hateos.NavigationVO;

public class ResultVO implements Serializable {

	private static final long serialVersionUID = 6611980961754781112L;

	private final int returnCode;
	private final String message;
	private final List<NavigationVO> navigations;

	public ResultVO(){
		this.returnCode = -1;
		this.message = null;
		this.navigations = null;
	}
	
	public ResultVO(final int returnCode, final String message, final List<NavigationVO> navigations) {
		super();
		this.returnCode = returnCode;
		this.message = message;
		this.navigations = navigations;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public String getMessage() {
		return message;
	}
	
	public List<NavigationVO> getNavigations() {
		return navigations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((navigations == null) ? 0 : navigations.hashCode());
		result = prime * result + returnCode;
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
		ResultVO other = (ResultVO) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (navigations == null) {
			if (other.navigations != null)
				return false;
		} else if (!navigations.equals(other.navigations))
			return false;
		if (returnCode != other.returnCode)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResultVO [returnCode=" + returnCode + ", message=" + message + ", navigations=" + navigations + "]";
	}
}