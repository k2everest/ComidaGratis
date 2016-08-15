package br.com.cafebinario.register.vo.result.domain;

import java.io.Serializable;
import java.util.List;

import br.com.cafebinario.register.vo.domain.NewDomainVO;
import br.com.cafebinario.register.vo.result.ResultVO;

public class DomainListResultVO implements Serializable{

	private static final long serialVersionUID = 8629117189536073032L;

	private final ResultVO result;
	private final List<NewDomainVO> userList;
	private final Long registerCount;

	public DomainListResultVO(ResultVO result, List<NewDomainVO> userList, Long registerCount) {
		super();
		this.result = result;
		this.userList = userList;
		this.registerCount = registerCount;
	}

	public ResultVO getResult() {
		return result;
	}

	public List<NewDomainVO> getUserList() {
		return userList;
	}
	
	public Long getRegisterCount() {
		return registerCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((userList == null) ? 0 : userList.hashCode());
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
		DomainListResultVO other = (DomainListResultVO) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (userList == null) {
			if (other.userList != null)
				return false;
		} else if (!userList.equals(other.userList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DomainListResultVO [result=" + result + ", userList=" + userList + "]";
	}
}
