package entita;

import java.sql.Date;

public class Attivita {

	private String nome;
	private String orarioApertura;
	private String orarioChiusura;
	private float costo;
	
	
	public Attivita(String nome, String orarioApertura, String orarioChiusura, float costo) {
		super();
		this.nome = nome;
		this.orarioApertura = orarioApertura;
		this.orarioChiusura = orarioChiusura;
		this.costo = costo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getOrarioApertura() {
		return orarioApertura;
	}


	public void setOrarioApertura(String orarioApertura) {
		this.orarioApertura = orarioApertura;
	}


	public String getOrarioChiusura() {
		return orarioChiusura;
	}


	public void setOrarioChiusura(String orarioChiusura) {
		this.orarioChiusura = orarioChiusura;
	}


	public float getCosto() {
		return costo;
	}


	public void setCosto(float costo) {
		this.costo = costo;
	}
	
	
	
}