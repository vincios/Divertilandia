package entita;

import java.sql.Date;

public class Cliente {

	private String codiceFiscale;
	private String nome;
	private String cognome;
	private Date dataDiNascita;
	
	public Cliente(String codiceFiscale, String nome, String cognome, Date data) {
		super();
		this.codiceFiscale = codiceFiscale;
		this.nome = nome;
		this.cognome = cognome;
		this.dataDiNascita = data;
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getData() {
		return dataDiNascita;
	}

	public void setData(Date data) {
		this.dataDiNascita = data;
	}

	@Override
	public String toString() {
		return codiceFiscale + ", " + nome + ", " + cognome + ", " + dataDiNascita;
	}
	
	
}
