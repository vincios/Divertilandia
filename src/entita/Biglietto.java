package entita;

import java.sql.Date;

public class Biglietto {
	
	private String codice;
	private float prezzo;
	private Date dataAcquisto;
	private String nomeParco;
	private String CFCliente;
	
	public Biglietto(String codice, float prezzo, Date dataAcquisto, String nomeParco, String cFCliente) {
		super();
		this.codice = codice;
		this.prezzo = prezzo;
		this.dataAcquisto = dataAcquisto;
		this.nomeParco = nomeParco;
		CFCliente = cFCliente;
	}
	
	public String getCodice() {
		return codice;
	}
	
	public float getPrezzo() {
		return prezzo;
	}
	
	public Date getDataAcquisto() {
		return dataAcquisto;
	}
	
	public String getNomeParco() {
		return nomeParco;
	}
	
	public String getCFCliente() {
		return CFCliente;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	
	public void setDataAcquisto(Date dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}
	
	public void setNomeParco(String nomeParco) {
		this.nomeParco = nomeParco;
	}
	
	public void setCFCliente(String cFCliente) {
		CFCliente = cFCliente;
	}

	@Override
	public String toString() {
		return codice + ", " + prezzo + ", " + dataAcquisto + ", " + nomeParco + ", " + CFCliente;
	}
	
	
}
