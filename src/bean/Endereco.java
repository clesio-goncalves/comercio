package bean;

public class Endereco {

	private int codigo_endereco;
	private String cep;
	private String nome_logradouro;
	private Integer numero_logradouro;
	private String bairro;
	private String uf;
	private String cidade;
	private String complemento;

	public int getCodigo_endereco() {
		return codigo_endereco;
	}

	public void setCodigo_endereco(int codigo_endereco) {
		this.codigo_endereco = codigo_endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNome_logradouro() {
		return nome_logradouro;
	}

	public void setNome_logradouro(String nome_logradouro) {
		this.nome_logradouro = nome_logradouro;
	}

	public Integer getNumero_logradouro() {
		return numero_logradouro;
	}

	public void setNumero_logradouro(Integer numero_logradouro) {
		this.numero_logradouro = numero_logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

}
