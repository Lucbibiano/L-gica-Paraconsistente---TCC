package Modelo;

public class ModeloPaciente {
	private int codPaciente;
	private String nomePaciente;
	private String comentario;
	private int idadePaciente;
	
	public int getCodPaciente() {
		return codPaciente;
	}
	
	public void setCodPaciente(int codPaciente) {
		this.codPaciente = codPaciente;
	}
	
	public String getNomePaciente() {
		return nomePaciente;
	}
	
	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}
	
	public int getIdadePaciente() {
		return idadePaciente;
	}
	
	public void setIdadePaciente(int idadePaciente) {
		this.idadePaciente = idadePaciente;
	}
	
	public String getComentario() {
		return comentario;
	}
	
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
}
