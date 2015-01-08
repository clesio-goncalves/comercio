package bean;

import java.util.Calendar;

public class Compra {

	private Integer codigo_compra;
	private Double valor_total_compra;
	private Calendar data_compra;
	private Double desconto_compra;
	private Funcionario funcionario_compra;
	private Fornecedor fornecedor_compra;

	public Compra() {
		this.funcionario_compra = new Funcionario();
		this.fornecedor_compra = new Fornecedor();
	}

	public Integer getCodigo_compra() {
		return codigo_compra;
	}

	public void setCodigo_compra(Integer codigo_compra) {
		this.codigo_compra = codigo_compra;
	}

	public Double getValor_total_compra() {
		return valor_total_compra;
	}

	public void setValor_total_compra(Double valor_total_compra) {
		this.valor_total_compra = valor_total_compra;
	}

	public Calendar getData_compra() {
		return data_compra;
	}

	public void setData_compra(Calendar data_compra) {
		this.data_compra = data_compra;
	}

	public Double getDesconto_compra() {
		return desconto_compra;
	}

	public void setDesconto_compra(Double desconto_compra) {
		this.desconto_compra = desconto_compra;
	}

	public Funcionario getFuncionario_compra() {
		return funcionario_compra;
	}

	public void setFuncionario_compra(Funcionario funcionario_compra) {
		this.funcionario_compra = funcionario_compra;
	}

	public Fornecedor getFornecedor_compra() {
		return fornecedor_compra;
	}

	public void setFornecedor_compra(Fornecedor fornecedor_compra) {
		this.fornecedor_compra = fornecedor_compra;
	}

}
