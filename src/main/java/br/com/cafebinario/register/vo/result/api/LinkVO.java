package br.com.cafebinario.register.vo.result.api;

import java.io.Serializable;
import java.util.List;

public class LinkVO implements Serializable {

	private static final long serialVersionUID = 8233594329230351282L;

	private final String method;
	private final String contentType;
	private final String url;
	private final List<LinkVO> links;

	public LinkVO(final String method, final String contentType, final String url, final List<LinkVO> links) {
		super();
		this.method = method;
		this.contentType = contentType;
		this.url = url;
		this.links = links;
	}

	public String getMethod() {
		return method;
	}

	public String getContentType() {
		return contentType;
	}

	public String getUrl() {
		return url;
	}
	
	public List<LinkVO> getLinks() {
		return links;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		LinkVO other = (LinkVO) obj;
		if (contentType == null) {
			if (other.contentType != null)
				return false;
		} else if (!contentType.equals(other.contentType))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LinkVO [method=" + method + ", contentType=" + contentType + ", url=" + url + "]";
	}
}