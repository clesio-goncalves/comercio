package bean;

public class Usuario {

	private Integer codigo_usuario;
	private String nome_usuario;
	private String senha_usuario;
	private Integer nivel_usuario;
	private Boolean ativo;
	private Boolean logado;

	public Integer getCodigo_usuario() {
		return codigo_usuario;
	}

	public void setCodigo_usuario(Integer codigo_usuario) {
		this.codigo_usuario = codigo_usuario;
	}

	public String getNome_usuario() {
		return nome_usuario;
	}

	public void setNome_usuario(String nome_usuario) {
		this.nome_usuario = nome_usuario;
	}

	public String getSenha_usuario() {
		return senha_usuario;
	}

	public void setSenha_usuario(String senha_usuario) {
		this.senha_usuario = senha_usuario;
	}

	public Integer getNivel_usuario() {
		return nivel_usuario;
	}

	public void setNivel_usuario(Integer nivel_usuario) {
		this.nivel_usuario = nivel_usuario;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean getLogado() {
		return logado;
	}

	public void setLogado(Boolean logado) {
		this.logado = logado;
	}

}
