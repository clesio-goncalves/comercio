package bean;

public class Funcionario {

	private Integer codigo_funcionario;
	private String cpf_funcionario;
	private String nome_funcionario;
	private String telefone_funcionario;
	private String email_funcionario;
	private String sexo_funcionario;
	private Boolean ativo;
	private Cargo cargo_funcionario;
	private Comercio comercio_funcionario;
	private Usuario usuario_funcionario;

	public Funcionario() {
		this.cargo_funcionario = new Cargo();
		this.comercio_funcionario = new Comercio();
		this.usuario_funcionario = new Usuario();
	}

	public Integer getCodigo_funcionario() {
		return codigo_funcionario;
	}

	public void setCodigo_funcionario(Integer codigo_funcionario) {
		this.codigo_funcionario = codigo_funcionario;
	}

	public String getCpf_funcionario() {
		return cpf_funcionario;
	}

	public void setCpf_funcionario(String cpf_funcionario) {
		this.cpf_funcionario = cpf_funcionario;
	}

	public String getNome_funcionario() {
		return nome_funcionario;
	}

	public void setNome_funcionario(String nome_funcionario) {
		this.nome_funcionario = nome_funcionario;
	}

	public String getTelefone_funcionario() {
		return telefone_funcionario;
	}

	public void setTelefone_funcionario(String telefone_funcionario) {
		this.telefone_funcionario = telefone_funcionario;
	}

	public String getEmail_funcionario() {
		return email_funcionario;
	}

	public void setEmail_funcionario(String email_funcionario) {
		this.email_funcionario = email_funcionario;
	}

	public String getSexo_funcionario() {
		return sexo_funcionario;
	}

	public void setSexo_funcionario(String sexo_funcionario) {
		this.sexo_funcionario = sexo_funcionario;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Cargo getCargo_funcionario() {
		return cargo_funcionario;
	}

	public void setCargo_funcionario(Cargo cargo_funcionario) {
		this.cargo_funcionario = cargo_funcionario;
	}

	public Comercio getComercio_funcionario() {
		return comercio_funcionario;
	}

	public void setComercio_funcionario(Comercio comercio_funcionario) {
		this.comercio_funcionario = comercio_funcionario;
	}

	public Usuario getUsuario_funcionario() {
		return usuario_funcionario;
	}

	public void setUsuario_funcionario(Usuario usuario_funcionario) {
		this.usuario_funcionario = usuario_funcionario;
	}

}
