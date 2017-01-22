package entita;

import java.util.ArrayList;

public class GruppoAttivita {
	private String Nome;
	private String NomeParco;
	private float costoPromozionale;
	private ArrayList<Attivita> attivita;

	public GruppoAttivita(String nome, String nomeParco, float costoPromozionale) {
		super();
		Nome = nome;
		NomeParco = nomeParco;
		this.costoPromozionale = costoPromozionale;
		this.attivita = new ArrayList<>();
	}
	
	public String getNome() {
		return Nome;
	}
	
	public String getNomeParco() {
		return NomeParco;
	}
	
	public float getCostoPromozionale() {
		return costoPromozionale;
	}
	
	public void setNome(String nome) {
		Nome = nome;
	}
	
	public void setNomeParco(String nomeParco) {
		NomeParco = nomeParco;
	}
	
	public void setCostoPromozionale(float costoPromozionale) {
		this.costoPromozionale = costoPromozionale;
	}

    public ArrayList<Attivita> getAttivita() {
        return attivita;
    }

    public void setAttivita(ArrayList<Attivita> attivita) {
        this.attivita = attivita;
    }

    public void addAttivita(Attivita a){
	    this.attivita.add(a);
    }

    @Override
	public String toString() {
		return Nome + ", " + NomeParco + ", " + costoPromozionale;
	}
}
