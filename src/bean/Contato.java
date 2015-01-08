package bean;

public class Contato {
	private Cliente cliente_contato;
	private String nome_contato;
	private String telefone_contato;

	public Contato() {
		this.cliente_contato = new Cliente();
	}

	public Cliente getCliente_contato() {
		return cliente_contato;
	}

	public void setCliente_contato(Cliente cliente_contato) {
		this.cliente_contato = cliente_contato;
	}

	public String getNome_contato() {
		return nome_contato;
	}

	public void setNome_contato(String nome_contato) {
		this.nome_contato = nome_contato;
	}

	public String getTelefone_contato() {
		return telefone_contato;
	}

	public void setTelefone_contato(String telefone_contato) {
		this.telefone_contato = telefone_contato;
	}

}
