package com.qinglingsoft.changshuchannel.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Article {
	public enum Type {
		NEWS, NOTICE
	}

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	@Enumerated
	private Type type;
	@Temporal(TemporalType.TIMESTAMP)
	private Date publishTime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date latestModified;
	private boolean published;
	@Lob
	private String content;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date dateTime) {
		this.publishTime = dateTime;
	}

	public Date getLatestModified() {
		return latestModified;
	}

	public void setLatestModified(Date latestModified) {
		this.latestModified = latestModified;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Article other = (Article) obj;
		if (id == null) {
			return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
