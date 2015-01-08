package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Categoria;

public class CategoriaDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public CategoriaDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Categoria';");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				return rs.getLong("auto_increment");
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return -1;
	}

	public void inserir(Categoria categoria) {
		String sql = "INSERT INTO Categoria (nome_categoria, descricao_categoria, percentual_lucro) VALUES (?,?,?);";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, categoria.getNome_categoria());
			stmt.setString(2, categoria.getDescricao_categoria());
			stmt.setDouble(3, categoria.getPercentual_lucro());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public ArrayList<String> comboCategoria() {
		try {
			ArrayList<String> listaCategoria = new ArrayList<String>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT nome_categoria FROM Categoria ORDER BY nome_categoria;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				listaCategoria.add(rs.getString("nome_categoria"));
			}
			rs.close();
			stmt.close();
			return listaCategoria;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Double> listaCategoria(String nome_categoria) {
		String sql = "select * from Categoria where nome_categoria like '"
				+ nome_categoria + "';";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			List<Double> categorias = new ArrayList<Double>();

			while (rs.next()) {
				Categoria categoria = new Categoria();
				categoria.setPercentual_lucro(rs.getDouble("percentual_lucro"));

				// adicionando o objeto à lista
				categorias.add(categoria.getPercentual_lucro());
			}

			rs.close();
			stmt.close();
			return categorias;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int count() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Categoria;");
			ResultSet rs = stmt.executeQuery();

			int cont = 0;

			while (rs.next()) {
				cont = rs.getInt("count(*)");
			}
			rs.close();
			stmt.close();
			return cont;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int countCategoriaProdutoVenda(Integer codigo_categoria) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Categoria as c inner join Produto as p on p.categoria_produto = c.codigo_categoria inner join Item_pedido as ip on ip.codigo_produto = p.codigo_produto where c.codigo_categoria="
							+ codigo_categoria + ";");
			ResultSet rs = stmt.executeQuery();

			int cont = 0;

			while (rs.next()) {
				cont = rs.getInt("count(*)");
			}
			rs.close();
			stmt.close();
			return cont;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int countCategoriaProdutoCompra(Integer codigo_categoria) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Categoria as c inner join Produto as p on p.categoria_produto = c.codigo_categoria inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto where c.codigo_categoria="
							+ codigo_categoria + ";");
			ResultSet rs = stmt.executeQuery();

			int cont = 0;

			while (rs.next()) {
				cont = rs.getInt("count(*)");
			}
			rs.close();
			stmt.close();
			return cont;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Categoria> pesquisar(String pesquisa, String acao) {
		try {
			List<Categoria> categorias = new ArrayList<Categoria>();

			PreparedStatement stmt = null;

			switch (acao) {

			case "codigo_categoria":
				stmt = this.conexao
						.prepareStatement("select * from Categoria where codigo_categoria="
								+ pesquisa + " order by codigo_categoria;");
				break;

			case "nome_categoria":
				stmt = this.conexao
						.prepareStatement("select * from Categoria where nome_categoria like '%"
								+ pesquisa + "%' order by nome_categoria;");
				break;

			case "percentual_lucro":
				stmt = this.conexao
						.prepareStatement("select * from Categoria where percentual_lucro="
								+ pesquisa + " order by percentual_lucro;");
				break;

			case "descricao_categoria":
				stmt = this.conexao
						.prepareStatement("select * from Categoria where descricao_categoria like '%"
								+ pesquisa + "%' order by descricao_categoria;");
				break;
			default:
				break;

			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Categoria categoria = new Categoria();
				categoria.setCodigo_categoria(rs.getInt("codigo_categoria"));
				categoria.setNome_categoria(rs.getString("nome_categoria"));
				categoria.setPercentual_lucro(rs.getDouble("percentual_lucro"));
				categoria.setDescricao_categoria(rs
						.getString("descricao_categoria"));

				// adicionando o objeto à lista
				categorias.add(categoria);
			}

			rs.close();
			stmt.close();
			return categorias;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean nomeCategoria(Categoria categoria) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT nome_categoria FROM Categoria;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto categoria
				String nomeCategoria = rs.getString("nome_categoria");

				if (categoria.getNome_categoria().equals(nomeCategoria)) {
					return true;
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return false;
	}

	public List<Categoria> listar() {
		try {
			List<Categoria> categorias = new ArrayList<Categoria>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT * from Categoria;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Categoria categoria = new Categoria();
				categoria.setCodigo_categoria(rs.getInt("codigo_categoria"));
				categoria.setNome_categoria(rs.getString("nome_categoria"));
				categoria.setDescricao_categoria(rs
						.getString("descricao_categoria"));
				categoria.setPercentual_lucro(rs.getDouble("percentual_lucro"));

				// adicionando o objeto à lista
				categorias.add(categoria);
			}

			rs.close();
			stmt.close();
			return categorias;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(Categoria categoria) {
		String sql = "UPDATE Categoria SET percentual_lucro=?, descricao_categoria=? WHERE codigo_categoria=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setDouble(1, categoria.getPercentual_lucro());
			stmt.setString(2, categoria.getDescricao_categoria());
			stmt.setInt(3, categoria.getCodigo_categoria());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public void remover(Categoria categoria) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Categoria WHERE codigo_categoria=?;");

			// preenche os valores
			stmt.setInt(1, categoria.getCodigo_categoria());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
