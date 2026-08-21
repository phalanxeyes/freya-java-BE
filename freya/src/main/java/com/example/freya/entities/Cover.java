package com.example.freya.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Cover {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer duracion_cover;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private LocalDateTime fecha_hora_cover;
	private Integer reproducciones;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getDuracion_cover() {
		return duracion_cover;
	}
	public void setDuracion_cover(Integer duracion_cover) {
		this.duracion_cover = duracion_cover;
	}
	public LocalDateTime getFecha_hora_cover() {
		return fecha_hora_cover;
	}
	public void setFecha_hora_cover(LocalDateTime fecha_hora_cover) {
		this.fecha_hora_cover = fecha_hora_cover;
	}
	public Integer getReproducciones() {
		return reproducciones;
	}
	public void setReproducciones(Integer reproducciones) {
		this.reproducciones = reproducciones;
	}
}