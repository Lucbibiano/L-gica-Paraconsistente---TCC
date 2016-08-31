package Modelo;

public class ModeloSintoma {
	
	private int codSintoma;
	private String nomeSintoma;
	private String descricaoSintoma;
	private int status_cadastro;
	
	public int getCodSintoma() {
		return codSintoma;
	}
	
	public void setCodSintoma(int codSintoma) {
		this.codSintoma = codSintoma;
	}
	
	public String getNomeSintoma() {
		return nomeSintoma;
	}
	
	public void setNomeSintoma(String nomeSintoma) {
		this.nomeSintoma = nomeSintoma;
	}
	
	public String getDescricaoSintoma() {
		return descricaoSintoma;
	}
	
	public void setDescricaoSintoma(String descricaoSintoma) {
		this.descricaoSintoma = descricaoSintoma;
	}

	public int getStatus_cadastro() {
		return status_cadastro;
	}

	public void setStatus_cadastro(int status_cadastro) {
		this.status_cadastro = status_cadastro;
	}
	
}
