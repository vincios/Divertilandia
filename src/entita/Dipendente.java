package entita;

import java.sql.Date;

public class Dipendente {

	private String cf;
	private String nome;
	private String cognome;
	private Date dataDiNascita;
	private String tipoContratto;
	private int durataContratto;
	
	public Dipendente(String cf, String nome, String cognome, Date dataDiNascita, String tipoContratto,
			int durataContratto) {
		super();
		this.cf = cf;
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = dataDiNascita;
		this.tipoContratto = tipoContratto;
		this.durataContratto = durataContratto;
		dataDiNascita.after(dataDiNascita);
	}

	public String getCf() {
		return cf;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public Date getDataDiNascita() {
		return dataDiNascita;
	}

	public String getTipoContratto() {
		return tipoContratto;
	}

	public int getDurataContratto() {
		return durataContratto;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}

	public void setTipoContratto(String tipoContratto) {
		this.tipoContratto = tipoContratto;
	}

	public void setDurataContratto(int durataContratto) {
		this.durataContratto = durataContratto;
	}
	
}
