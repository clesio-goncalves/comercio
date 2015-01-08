package bean;

public class Comercio {

	private Integer codigo_comercio;
	private String cnpj_comercio;
	private String inscricao_estadual;
	private String nome_comercio;
	private String telefone_comercio;
	private String email_comercio;
	private Endereco endereco_comercio;

	public Comercio() {
		this.endereco_comercio = new Endereco();
	}

	public Integer getCodigo_comercio() {
		return codigo_comercio;
	}

	public void setCodigo_comercio(Integer codigo_comercio) {
		this.codigo_comercio = codigo_comercio;
	}

	public String getCnpj_comercio() {
		return cnpj_comercio;
	}

	public void setCnpj_comercio(String cnpj_comercio) {
		this.cnpj_comercio = cnpj_comercio;
	}

	public String getInscricao_estadual() {
		return inscricao_estadual;
	}

	public void setInscricao_estadual(String inscricao_estadual) {
		this.inscricao_estadual = inscricao_estadual;
	}

	public String getNome_comercio() {
		return nome_comercio;
	}

	public void setNome_comercio(String nome_comercio) {
		this.nome_comercio = nome_comercio;
	}

	public String getTelefone_comercio() {
		return telefone_comercio;
	}

	public void setTelefone_comercio(String telefone_comercio) {
		this.telefone_comercio = telefone_comercio;
	}

	public String getEmail_comercio() {
		return email_comercio;
	}

	public void setEmail_comercio(String email_comercio) {
		this.email_comercio = email_comercio;
	}

	public Endereco getEndereco_comercio() {
		return endereco_comercio;
	}

	public void setEndereco_comercio(Endereco endereco_comercio) {
		this.endereco_comercio = endereco_comercio;
	}

}
