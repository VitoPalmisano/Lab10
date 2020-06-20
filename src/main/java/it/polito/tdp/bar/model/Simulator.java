package it.polito.tdp.bar.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import it.polito.tdp.bar.model.Event.BancoOccupato;
import it.polito.tdp.bar.model.Event.EventType;

public class Simulator {
	
	private PriorityQueue<Event> queue = new PriorityQueue<Event>();
	
	// Parametri di simulazione
	private int tavoliDa10 = 2;
	private int tavoliDa8 = 4;
	private int tavoliDa6 = 4;
	private int tavoliDa4 = 5;
	private int numEventi = 2000;
	private double sogliaTolleranza = 0.5;
	
	private LocalDateTime oraApertura = LocalDateTime.of(2020, 06, 01, 00, 00);
	
	// Modello del mondo
	private int tavoliDa10Disp;
	private int tavoliDa8Disp;
	private int tavoliDa6Disp;
	private int tavoliDa4Disp;
	
	// Valori da calcolare
	private int clienti;
	private int soddisfatti;
	private int insoddisfatti;
	
	// Metodi per impostare i parametri
	public void setSogliaTolleranza(double sogliaTolleranza) {
		this.sogliaTolleranza = sogliaTolleranza;
	}	
	
	// Metodi per restituire i risultati
	public int getClienti() {
		return clienti;
	}
	public int getSoddisfatti() {
		return soddisfatti;
	}
	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	// Simulazione
	public void run() {
		tavoliDa10Disp = tavoliDa10;
		tavoliDa8Disp = tavoliDa8;
		tavoliDa6Disp = tavoliDa6;
		tavoliDa4Disp = tavoliDa4;
		clienti = soddisfatti = insoddisfatti = 0;
		
		queue.clear();
		
		LocalDateTime oraArrivoGruppo = oraApertura;
		
		int i = 1;
		do {
			Event e = new Event(EventType.ARRIVO_GRUPPO_CLIENTI, oraArrivoGruppo, (int)Math.round(Math.random()*9)+1, Duration.of(Math.round(Math.random()*60)+60, ChronoUnit.MINUTES), Math.random()*0.9);
			oraArrivoGruppo = oraArrivoGruppo.plus(Math.round(Math.random()*9)+1, ChronoUnit.MINUTES);
			queue.add(e);
			i++;
		}while(i<numEventi);
		
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			System.out.println(e);
			processEvent(e);
		}
	}
	
	private void processEvent(Event e) {
		
		int nc = e.getNumPersone();
		BancoOccupato banco;
		
		switch (e.getType()) {
		case ARRIVO_GRUPPO_CLIENTI:
			
			clienti += nc;
			banco = BancoOccupato.BANCONE;
			
			if(nc>=2 && nc<=4) {
				if(tavoliDa4Disp>0) {
					banco = BancoOccupato.DA_4;
					tavoliDa4Disp--;
				}else if(nc>=3) {
					if(tavoliDa6Disp>0) {
						banco = BancoOccupato.DA_6;
						tavoliDa6Disp--;
					}else if(nc==4 && tavoliDa8Disp>0) {
						banco = BancoOccupato.DA_8;
						tavoliDa8Disp--;
					}
				}
			}
			if(nc==5 || nc==6) {
				if(tavoliDa6Disp>0) {
					banco = BancoOccupato.DA_6;
					tavoliDa6Disp--;
				}else if(tavoliDa8Disp>0) {
					banco = BancoOccupato.DA_8;
					tavoliDa8Disp--;
				}else if(tavoliDa10Disp>0) {
					banco = BancoOccupato.DA_10;
					tavoliDa10Disp--;
				}
			}
			if(nc==7 || nc==8) {
				if(tavoliDa8Disp>0) {
					banco = BancoOccupato.DA_8;
					tavoliDa8Disp--;
				}else if(tavoliDa10Disp>0) {
					banco = BancoOccupato.DA_10;
					tavoliDa10Disp--;
				}
			}
			if(nc==9 || nc==10) {
				if(tavoliDa10Disp>0) {
					banco = BancoOccupato.DA_10;
					tavoliDa10Disp--;
				}
			}
			
			Event nuovo = new Event(EventType.GRUPPO_VA_VIA, e.getTime().plus(e.getDurata()), e.getNumPersone(), null, e.getTolleranza());
			nuovo.setBanco(banco);
//			queue.add(nuovo);
			
			System.out.println(banco+"\nda 10: "+tavoliDa10Disp+"\nda 8: "+tavoliDa8Disp+"\nda 6: "+tavoliDa6Disp+"\nda 4: "+tavoliDa4Disp);
			
			if(!banco.equals(BancoOccupato.BANCONE)) {
				soddisfatti += nc;
				System.out.println("soddisfatti"+soddisfatti);
			}else {
				if(Math.random()>e.getTolleranza()) {
					soddisfatti += nc;
					System.out.println("soddisfatti"+soddisfatti);
				}else {
					insoddisfatti += nc;
					System.out.println("insoddisfatti"+insoddisfatti);
				}
			}
			
		case GRUPPO_VA_VIA:
			banco = e.getBanco();
			if(banco == BancoOccupato.DA_4) {
				tavoliDa4Disp++;
			}else if(banco == BancoOccupato.DA_6) {
				tavoliDa6Disp++;
			}else if(banco == BancoOccupato.DA_8) {
				tavoliDa8Disp++;
			}else if(banco == BancoOccupato.DA_10) {
				tavoliDa10Disp++;
			}
			
		}
	}
}
