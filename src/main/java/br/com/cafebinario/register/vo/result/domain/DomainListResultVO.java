package br.com.cafebinario.register.vo.result.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import br.com.cafebinario.register.vo.domain.NewDomainVO;
import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;

public class DomainListResultVO implements Serializable {

	private static final long serialVersionUID = 8629117189536073032L;

	private final ResultVO result;
	private final List<NewDomainVO> domainList;
	private final Long registerCount;

	public DomainListResultVO(final ResultVO result, final List<NewDomainVO> domainList, final Long registerCount) {
		super();
		this.result = result;
		this.domainList = domainList;
		this.registerCount = registerCount;
	}

	public ResultVO getResult() {
		return result;
	}

	public List<NewDomainVO> getDomainList() {
		return domainList;
	}

	public Long getRegisterCount() {
		return registerCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((domainList == null) ? 0 : domainList.hashCode());
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
		if (domainList == null) {
			if (other.domainList != null)
				return false;
		} else if (!domainList.equals(other.domainList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DomainListResultVO [result=" + result + ", domainList=" + domainList + "]";
	}

	public static DomainListResultVO createUserBlanck() {
		return new DomainListResultVO(ResultVOBuilder.BLANCK(), Arrays.asList(NewDomainVO.createUserBlanck()), 0L);
	}
}
