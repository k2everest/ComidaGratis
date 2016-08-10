package br.com.cafebinario.notify.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EventNotifyData implements Serializable {

	private static final long serialVersionUID = -7291457220287549328L;

	public static EventNotifyData newRegisterDefaultInstance(String to, String content) {
		EventNotifyData eventNotifyData = new EventNotifyData();
		eventNotifyData.setTo(new String[] { to });
		eventNotifyData.setContent(content);
		eventNotifyData.setCreationDate(new Date(System.currentTimeMillis()));
		eventNotifyData.setFrom("alexandre.msl@gmail.com");
		eventNotifyData.setSubject("confirmacao de registro www.cafebinario.com.br");
		return eventNotifyData;
	}

	private Date creationDate;
	private String from;
	private String[] to;
	private String subject;
	private String content;
	private List<AttachEventData> attachs;
	private EventNotifyData eventNotifyDataParent;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<AttachEventData> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<AttachEventData> attachs) {
		this.attachs = attachs;
	}

	public EventNotifyData getEventNotifyDataParent() {
		return eventNotifyDataParent;
	}

	public void setEventNotifyDataParent(EventNotifyData eventNotifyDataParent) {
		this.eventNotifyDataParent = eventNotifyDataParent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attachs == null) ? 0 : attachs.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + ((eventNotifyDataParent == null) ? 0 : eventNotifyDataParent.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result + Arrays.hashCode(to);
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
		EventNotifyData other = (EventNotifyData) obj;
		if (attachs == null) {
			if (other.attachs != null)
				return false;
		} else if (!attachs.equals(other.attachs))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (eventNotifyDataParent == null) {
			if (other.eventNotifyDataParent != null)
				return false;
		} else if (!eventNotifyDataParent.equals(other.eventNotifyDataParent))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (!Arrays.equals(to, other.to))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EventNotifyData [creationDate=" + creationDate + ", from=" + from + ", to=" + Arrays.toString(to)
				+ ", subject=" + subject + ", content=" + content + ", attachs=" + attachs + ", eventNotifyDataParent="
				+ eventNotifyDataParent + "]";
	}
}
