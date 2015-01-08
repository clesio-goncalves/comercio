package bean;

public class Item_compra {

	private Compra compra;
	private Produto produto;
	private Integer quatidade_item;
	private Double valor_unitario_compra;

	public Item_compra() {
		this.compra = new Compra();
		this.produto = new Produto();
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Integer getQuatidade_item() {
		return quatidade_item;
	}

	public void setQuatidade_item(Integer quatidade_item) {
		this.quatidade_item = quatidade_item;
	}

	public Double getValor_unitario_compra() {
		return valor_unitario_compra;
	}

	public void setValor_unitario_compra(Double valor_unitario_compra) {
		this.valor_unitario_compra = valor_unitario_compra;
	}

	public boolean equals(Object o) {
		Item_compra outro = (Item_compra) o;
		return this.produto.getNome_produto().equals(
				outro.produto.getNome_produto());
	}

}
