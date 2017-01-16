package entita;

import java.sql.Date;

public class Offerta {

	private String codice;
	private String nome;
	private String descrizione;
	private Date dataInizio;
	private Date dataFine;
	private int percentualeSconto;
	
	public Offerta(String codice, String nome, String descrizione, Date dataInizio, Date dataFine,
			int percentualeSconto) {
		super();
		this.codice = codice;
		this.nome = nome;
		this.descrizione = descrizione;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.percentualeSconto = percentualeSconto;
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public int getPercentualeSconto() {
		return percentualeSconto;
	}

	public void setPercentualeSconto(int percentualeSconto) {
		this.percentualeSconto = percentualeSconto;
	}
	
}
