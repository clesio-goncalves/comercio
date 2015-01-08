package bean;

import java.util.Calendar;

public class PesquisaCompra {

	private Calendar data_inicial;
	private Calendar data_final;
	private Integer codigo_compra;
	private Integer codigo_fornecedor;
	private Integer codigo_produto;

	public Calendar getData_inicial() {
		return data_inicial;
	}

	public void setData_inicial(Calendar data_inicial) {
		this.data_inicial = data_inicial;
	}

	public Calendar getData_final() {
		return data_final;
	}

	public void setData_final(Calendar data_final) {
		this.data_final = data_final;
	}

	public Integer getCodigo_compra() {
		return codigo_compra;
	}

	public void setCodigo_compra(Integer codigo_compra) {
		this.codigo_compra = codigo_compra;
	}

	public Integer getCodigo_fornecedor() {
		return codigo_fornecedor;
	}

	public void setCodigo_fornecedor(Integer codigo_fornecedor) {
		this.codigo_fornecedor = codigo_fornecedor;
	}

	public Integer getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(Integer codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

}
