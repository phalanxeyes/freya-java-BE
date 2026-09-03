package com.salest.freya.entities;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name= "covers")
@EntityListeners(AuditingEntityListener.class)
public class Cover {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer duration;	
	private Integer playCount;
	private String youtubeUrl;


	@ManyToOne
	@JoinColumn(name = "uploader_user_id")
	private User uploader;

	@ManyToOne
	@JoinColumn(name = "song_id")
	private Song song;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime updatedAt;
    
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


