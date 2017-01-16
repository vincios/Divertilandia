package entita;

public class Ristorante {
	
	private String partitaIva;
	private String nome;
	private String città;
	private String via;
	private String nCivico;
	
	public Ristorante(String partitaIva, String nome, String città, String via, String nCivico) {
		super();
		this.partitaIva = partitaIva;
		this.nome = nome;
		this.città = città;
		this.via = via;
		this.nCivico = nCivico;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCittà() {
		return città;
	}

	public void setCittà(String città) {
		this.città = città;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getnCivico() {
		return nCivico;
	}

	public void setnCivico(String nCivico) {
		this.nCivico = nCivico;
	}
	
	

}
