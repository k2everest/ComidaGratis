package br.com.cafebinario.register.vo.result.api;

import java.io.Serializable;
import java.util.List;

public class LinkVO implements Serializable {

	private static final long serialVersionUID = 8233594329230351282L;

	private final String method;
	private final String contentType;
	private final String url;
	private final List<LinkVO> links;
	private final Object objectRequestExample;
	private final Object objectResponseExample;

	public LinkVO(final String method, final String contentType, final String url, final List<LinkVO> links, final Object objectRequestExample, final Object objectResponseExample) {
		super();
		this.method = method;
		this.contentType = contentType;
		this.url = url;
		this.links = links;
		this.objectRequestExample = objectRequestExample;
		this.objectResponseExample = objectResponseExample;
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
	
	public Object getObjectRequestExample() {
		return objectRequestExample;
	}

	public Object getObjectResponseExample() {
		return objectResponseExample;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
		result = prime * result + ((links == null) ? 0 : links.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((objectRequestExample == null) ? 0 : objectRequestExample.hashCode());
		result = prime * result + ((objectResponseExample == null) ? 0 : objectResponseExample.hashCode());
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
		if (links == null) {
			if (other.links != null)
				return false;
		} else if (!links.equals(other.links))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (objectRequestExample == null) {
			if (other.objectRequestExample != null)
				return false;
		} else if (!objectRequestExample.equals(other.objectRequestExample))
			return false;
		if (objectResponseExample == null) {
			if (other.objectResponseExample != null)
				return false;
		} else if (!objectResponseExample.equals(other.objectResponseExample))
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
		return "LinkVO [method=" + method + ", contentType=" + contentType + ", url=" + url + ", links=" + links
				+ ", objectRequestExample=" + objectRequestExample + ", objectResponseExample=" + objectResponseExample
				+ "]";
	}

	
}