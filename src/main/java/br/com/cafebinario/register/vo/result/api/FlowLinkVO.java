package br.com.cafebinario.register.vo.result.api;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;
import br.com.cafebinario.register.vo.result.hateos.NavigationVO;

public class FlowLinkVO implements Serializable {

	private static final long serialVersionUID = 8364503629694145678L;

	private final ResultVO result = ResultVOBuilder.SUCCESS(Arrays.asList(new NavigationVO("", "", "")));
	private final List<LinkVO> links;

	public FlowLinkVO(final List<LinkVO> links) {
		super();
		this.links = links;
	}
	
	public ResultVO getResult() {
		return result;
	}

	public List<LinkVO> getLinks() {
		return links;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((links == null) ? 0 : links.hashCode());
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
		FlowLinkVO other = (FlowLinkVO) obj;
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FlowLinkVO [links=" + Arrays.toString(links.toArray()) + "]";
	}
}