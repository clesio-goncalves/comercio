package bean;

public class Categoria {

	private Integer codigo_categoria;
	private String nome_categoria;
	private Double percentual_lucro;
	private String descricao_categoria;

	public Integer getCodigo_categoria() {
		return codigo_categoria;
	}

	public void setCodigo_categoria(Integer codigo_categoria) {
		this.codigo_categoria = codigo_categoria;
	}

	public String getNome_categoria() {
		return nome_categoria;
	}

	public void setNome_categoria(String nome_categoria) {
		this.nome_categoria = nome_categoria;
	}

	public Double getPercentual_lucro() {
		return percentual_lucro;
	}

	public void setPercentual_lucro(Double percentual_lucro) {
		this.percentual_lucro = percentual_lucro;
	}

	public String getDescricao_categoria() {
		return descricao_categoria;
	}

	public void setDescricao_categoria(String descricao_categoria) {
		this.descricao_categoria = descricao_categoria;
	}

}
