package entita;

import java.sql.Date;

public class Biglietto {
	
	private Cliente cliente;
	private String codice;
	private float prezzo;
	private Date dataAcquisto;
	
	public Biglietto(Cliente cliente, String codice, float prezzo, Date dataAcquisto) {
		super();
		this.cliente = cliente;
		this.codice = codice;
		this.prezzo = prezzo;
		this.dataAcquisto = dataAcquisto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public Date getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(Date dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}
	
	
}
