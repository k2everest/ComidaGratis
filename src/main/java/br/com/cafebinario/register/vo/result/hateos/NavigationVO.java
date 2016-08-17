package br.com.cafebinario.register.vo.result.hateos;

import java.io.Serializable;

public class NavigationVO implements Serializable {

	private static final long serialVersionUID = -5139207883902813495L;

	private String method;
	private String rel;
	private String href;
	
	public NavigationVO(){
		
	}

	public NavigationVO(String method, String rel, String href) {
		super();
		this.method = method;
		this.rel = rel;
		this.href = href;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((rel == null) ? 0 : rel.hashCode());
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
		NavigationVO other = (NavigationVO) obj;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (rel == null) {
			if (other.rel != null)
				return false;
		} else if (!rel.equals(other.rel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Navigation [method=" + method + ", rel=" + rel + ", href=" + href + "]";
	}
}