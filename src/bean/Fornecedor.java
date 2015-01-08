package bean;

public class Fornecedor {

	private Integer codigo_fornecedor;
	private String cnpj_fornecedor;
	private String nome_fornecedor;
	private String telefone_fornecedor;
	private String email_fornecedor;
	private Endereco endereco_fornecedor;

	public Fornecedor() {
		this.endereco_fornecedor = new Endereco();
	}

	public Integer getCodigo_fornecedor() {
		return codigo_fornecedor;
	}

	public void setCodigo_fornecedor(Integer codigo_fornecedor) {
		this.codigo_fornecedor = codigo_fornecedor;
	}

	public String getCnpj_fornecedor() {
		return cnpj_fornecedor;
	}

	public void setCnpj_fornecedor(String cnpj_fornecedor) {
		this.cnpj_fornecedor = cnpj_fornecedor;
	}

	public String getNome_fornecedor() {
		return nome_fornecedor;
	}

	public void setNome_fornecedor(String nome_fornecedor) {
		this.nome_fornecedor = nome_fornecedor;
	}

	public String getTelefone_fornecedor() {
		return telefone_fornecedor;
	}

	public void setTelefone_fornecedor(String telefone_fornecedor) {
		this.telefone_fornecedor = telefone_fornecedor;
	}

	public String getEmail_fornecedor() {
		return email_fornecedor;
	}

	public void setEmail_fornecedor(String email_fornecedor) {
		this.email_fornecedor = email_fornecedor;
	}

	public Endereco getEndereco_fornecedor() {
		return endereco_fornecedor;
	}

	public void setEndereco_fornecedor(Endereco endereco_fornecedor) {
		this.endereco_fornecedor = endereco_fornecedor;
	}

}
