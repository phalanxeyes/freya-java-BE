package com.salest.freya.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name= "covers")
public class Cover {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer duration;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer playCount;
	private String youtubeUrl;

	@ManyToOne
	@JoinColumn(name = "uploader_user_id")
	private User uploader;

	@ManyToOne
	@JoinColumn(name = "song_id")
	private Song song;

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}


	public User getUploader() {
		return uploader;
	}

	public void setUploader(User createdBy) {
		this.uploader = createdBy;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDuration() {
		return duration;
	}
	public void setDuration(Integer duracion) {
		this.duration = duracion;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime fechaHora) {
		this.createdAt = fechaHora;
	}
	public Integer getPlayCount() {
		return playCount;
	}
	public void setPlayCount(Integer reproducciones) {
		this.playCount = reproducciones;
	}

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }
}


