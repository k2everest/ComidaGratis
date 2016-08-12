package br.com.cafebinario.register.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class PageVO implements Serializable {
	private static final long serialVersionUID = -8917094100496416739L;

	@NotNull
	private Integer pageNumber;

	@NotNull
	private Integer pageSize;

	public PageVO(){
		
	}
	
	public PageVO(final Integer pageNumber, final Integer pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pageNumber == null) ? 0 : pageNumber.hashCode());
		result = prime * result + ((pageSize == null) ? 0 : pageSize.hashCode());
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
		PageVO other = (PageVO) obj;
		if (pageNumber == null) {
			if (other.pageNumber != null)
				return false;
		} else if (!pageNumber.equals(other.pageNumber))
			return false;
		if (pageSize == null) {
			if (other.pageSize != null)
				return false;
		} else if (!pageSize.equals(other.pageSize))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PageVO [pageNumber=" + pageNumber + ", pageSize=" + pageSize + "]";
	}

}
