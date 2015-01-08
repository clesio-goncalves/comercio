package bean;

public class Produto {

	private Integer codigo_produto;
	private String nome_produto;
	private Double valor_unitario;
	private Integer quantidade_atual;
	private Integer estoque_minimo;
	private Boolean ativo;
	private Categoria categoria_produto;

	public Produto() {
		this.categoria_produto = new Categoria();
	}

	public Integer getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(Integer codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	public String getNome_produto() {
		return nome_produto;
	}

	public void setNome_produto(String nome_produto) {
		this.nome_produto = nome_produto;
	}

	public Double getValor_unitario() {
		return valor_unitario;
	}

	public void setValor_unitario(Double valor_unitario) {
		this.valor_unitario = valor_unitario;
	}

	public Integer getQuantidade_atual() {
		return quantidade_atual;
	}

	public void setQuantidade_atual(Integer quantidade_atual) {
		this.quantidade_atual = quantidade_atual;
	}

	public Integer getEstoque_minimo() {
		return estoque_minimo;
	}

	public void setEstoque_minimo(Integer estoque_minimo) {
		this.estoque_minimo = estoque_minimo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Categoria getCategoria_produto() {
		return categoria_produto;
	}

	public void setCategoria_produto(Categoria categoria_produto) {
		this.categoria_produto = categoria_produto;
	}

}
