package br.com.cafebinario.notify.data;

import java.io.Serializable;

public class AttachEventData implements Serializable {

	private static final long serialVersionUID = 6108964043954440434L;

	private String identify;
	private byte[] atachment;
	private String contentType;

	public String getIdentify() {
		return identify;
	}

	public void setIdentify(String identify) {
		this.identify = identify;
	}

	public byte[] getAtachment() {
		return atachment;
	}

	public void setAtachment(byte[] atachment) {
		this.atachment = atachment;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
