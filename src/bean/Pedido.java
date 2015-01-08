package bean;

import java.util.Calendar;

public class Pedido {

	private Integer codigo_pedido;
	private Calendar data_pedido;
	private Calendar data_final;
	private Double valor_total_pedido;
	private Double acrescimo_pedido;
	private Double desconto_pedido;
	private Funcionario funcionario_pedido;
	private Cliente cliente_pedido;

	public Pedido() {
		this.funcionario_pedido = new Funcionario();
		this.cliente_pedido = new Cliente();
	}

	public Integer getCodigo_pedido() {
		return codigo_pedido;
	}

	public void setCodigo_pedido(Integer codigo_pedido) {
		this.codigo_pedido = codigo_pedido;
	}

	public Calendar getData_pedido() {
		return data_pedido;
	}

	public void setData_pedido(Calendar data_pedido) {
		this.data_pedido = data_pedido;
	}

	public Calendar getData_final() {
		return data_final;
	}

	public void setData_final(Calendar data_final) {
		this.data_final = data_final;
	}

	public Double getValor_total_pedido() {
		return valor_total_pedido;
	}

	public void setValor_total_pedido(Double valor_total_pedido) {
		this.valor_total_pedido = valor_total_pedido;
	}

	public Double getAcrescimo_pedido() {
		return acrescimo_pedido;
	}

	public void setAcrescimo_pedido(Double acrescimo_pedido) {
		this.acrescimo_pedido = acrescimo_pedido;
	}

	public Double getDesconto_pedido() {
		return desconto_pedido;
	}

	public void setDesconto_pedido(Double desconto_pedido) {
		this.desconto_pedido = desconto_pedido;
	}

	public Funcionario getFuncionario_pedido() {
		return funcionario_pedido;
	}

	public void setFuncionario_pedido(Funcionario funcionario_pedido) {
		this.funcionario_pedido = funcionario_pedido;
	}

	public Cliente getCliente_pedido() {
		return cliente_pedido;
	}

	public void setCliente_pedido(Cliente cliente_pedido) {
		this.cliente_pedido = cliente_pedido;
	}

}
