package bean;

public class Item_pedido {

	private Pedido pedido;
	private Produto produto;
	private Integer quatidade_item;
	private Double valor_unitario_pedido;
	private Double lucro;

	public Item_pedido() {
		this.pedido = new Pedido();
		this.produto = new Produto();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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

	public Double getValor_unitario_pedido() {
		return valor_unitario_pedido;
	}

	public void setValor_unitario_pedido(Double valor_unitario_pedido) {
		this.valor_unitario_pedido = valor_unitario_pedido;
	}

	public Double getLucro() {
		return lucro;
	}

	public void setLucro(Double lucro) {
		this.lucro = lucro;
	}

	public boolean equals(Object o) {
		Item_pedido outro = (Item_pedido) o;
		return this.produto.getNome_produto().equals(
				outro.produto.getNome_produto());
	}

}
