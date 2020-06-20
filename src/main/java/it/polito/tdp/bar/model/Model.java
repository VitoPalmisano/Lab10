package it.polito.tdp.bar.model;

public class Model {
	
	private Simulator s;
	
	public Model() {
		s = new Simulator();
	}
	
	public String getNumClienti() {
		s.run();
		return "Clienti totali: "+s.getClienti()+"\nClienti soddisfatti: "+s.getSoddisfatti()+"\nClienti insoddisfatti: "+s.getInsoddisfatti();
	}
}
