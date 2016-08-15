package br.com.cafebinario.register.vo.result.api;

import java.io.Serializable;

import br.com.cafebinario.register.vo.result.ResultVO;

public class RegisterApiResult implements Serializable {

	private static final long serialVersionUID = 3195016061141382335L;
	private ResultVO result;

	private FlowLinkVO flowLink;

	public ResultVO getResult() {
		return result;
	}

	public void setResult(ResultVO result) {
		this.result = result;
	}

	public FlowLinkVO getFlowLink() {
		return flowLink;
	}

	public void setFlowLink(FlowLinkVO flowLink) {
		this.flowLink = flowLink;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flowLink == null) ? 0 : flowLink.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
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
		RegisterApiResult other = (RegisterApiResult) obj;
		if (flowLink == null) {
			if (other.flowLink != null)
				return false;
		} else if (!flowLink.equals(other.flowLink))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegisterApiResult [result=" + result + ", flowLink=" + flowLink + "]";
	}
}