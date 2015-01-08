package bean;

public class Cargo {

	private Integer codigo_cargo;
	private String nome_cargo;
	private Double salario;
	private Integer qnt_horas_semana;

	public Integer getCodigo_cargo() {
		return codigo_cargo;
	}

	public void setCodigo_cargo(Integer codigo_cargo) {
		this.codigo_cargo = codigo_cargo;
	}

	public String getNome_cargo() {
		return nome_cargo;
	}

	public void setNome_cargo(String nome_cargo) {
		this.nome_cargo = nome_cargo;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Integer getQnt_horas_semana() {
		return qnt_horas_semana;
	}

	public void setQnt_horas_semana(Integer qnt_horas_semana) {
		this.qnt_horas_semana = qnt_horas_semana;
	}

}
