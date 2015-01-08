package bean;

public class Cliente {

	private Integer codigo_cliente;
	private String cpf_cliente;
	private String nome_cliente;
	private String telefone_cliente;
	private String email_cliente;
	private Boolean ativo;
	private Endereco endereco_cliente;

	public Cliente() {
		this.endereco_cliente = new Endereco();
	}

	public Integer getCodigo_cliente() {
		return codigo_cliente;
	}

	public void setCodigo_cliente(Integer codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}

	public String getCpf_cliente() {
		return cpf_cliente;
	}

	public void setCpf_cliente(String cpf_cliente) {
		this.cpf_cliente = cpf_cliente;
	}

	public String getNome_cliente() {
		return nome_cliente;
	}

	public void setNome_cliente(String nome_cliente) {
		this.nome_cliente = nome_cliente;
	}

	public String getTelefone_cliente() {
		return telefone_cliente;
	}

	public void setTelefone_cliente(String telefone_cliente) {
		this.telefone_cliente = telefone_cliente;
	}

	public String getEmail_cliente() {
		return email_cliente;
	}

	public void setEmail_cliente(String email_cliente) {
		this.email_cliente = email_cliente;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Endereco getEndereco_cliente() {
		return endereco_cliente;
	}

	public void setEndereco_cliente(Endereco endereco_cliente) {
		this.endereco_cliente = endereco_cliente;
	}

}
