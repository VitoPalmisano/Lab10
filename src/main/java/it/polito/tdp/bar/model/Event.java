package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Event implements Comparable<Event>{

	public enum EventType {
		ARRIVO_GRUPPO_CLIENTI, GRUPPO_VA_VIA
	}
	
	public enum BancoOccupato {
		DA_4, DA_6, DA_8, DA_10, BANCONE
	}
	
	private EventType type;
	private LocalDateTime time;
	private Integer numPersone;
	private Duration durata;
	private double tolleranza;
	private BancoOccupato banco;
	
	public Event(EventType type, LocalDateTime time, Integer numPersone, Duration durata, double tolleranza) {
		super();
		this.type = type;
		this.time = time;
		this.numPersone = numPersone;
		this.durata = durata;
		this.tolleranza = tolleranza;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Integer getNumPersone() {
		return numPersone;
	}

	public void setNumPersone(Integer numPersone) {
		this.numPersone = numPersone;
	}

	public Duration getDurata() {
		return durata;
	}

	public void setDurata(Duration durata) {
		this.durata = durata;
	}

	public double getTolleranza() {
		return tolleranza;
	}

	public void setTolleranza(double tolleranza) {
		this.tolleranza = tolleranza;
	}

	public BancoOccupato getBanco() {
		return banco;
	}

	public void setBanco(BancoOccupato banco) {
		this.banco = banco;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.getTime());
	}

	@Override
	public String toString() {
		return "Event [type=" + type + ", banco=" + banco + ", time=" + time + ", numPersone=" + numPersone + ", durata=" + durata
				+ ", tolleranza=" + tolleranza + "]";
	}
	
	
}
