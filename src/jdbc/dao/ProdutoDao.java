package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jdbc.Conexao;
import bean.Item_compra;
import bean.Item_pedido;
import bean.Produto;

public class ProdutoDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public ProdutoDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Produto';");
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

	public void inserir(Produto produto) {
		String sql = "INSERT INTO Produto (nome_produto, estoque_minimo, ativo, categoria_produto)  VALUES (?,?,?,(select codigo_categoria as categoria_produto from Categoria where nome_categoria like '"
				+ produto.getCategoria_produto().getNome_categoria() + "'));";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setString(1, produto.getNome_produto());
			stmt.setInt(2, produto.getEstoque_minimo());
			stmt.setBoolean(3, produto.getAtivo());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Produto> pesquisar(String pesquisa, String acao) {
		try {
			List<Produto> produtos = new ArrayList<Produto>();

			PreparedStatement stmt = null;

			switch (acao) {

			case "codigo_produto":
				stmt = this.conexao
						.prepareStatement("select p.*, c.nome_categoria from Produto as p inner join Categoria as c on c.codigo_categoria = p.categoria_produto where p.codigo_produto="
								+ pesquisa + " order by p.codigo_produto;");
				break;

			case "nome_produto":
				stmt = this.conexao
						.prepareStatement("select p.*, c.nome_categoria from Produto as p inner join Categoria as c on c.codigo_categoria = p.categoria_produto where p.nome_produto like '%"
								+ pesquisa + "%' order by p.nome_produto;");
				break;

			case "valor_unitario":
				stmt = this.conexao
						.prepareStatement("select p.*, c.nome_categoria from Produto as p inner join Categoria as c on c.codigo_categoria = p.categoria_produto where p.valor_unitario="
								+ pesquisa + " order by p.valor_unitario;");
				break;

			case "categoria_produto":
				stmt = this.conexao
						.prepareStatement("select p.*, c.nome_categoria from Produto as p inner join Categoria as c on c.codigo_categoria = p.categoria_produto where c.nome_categoria like '%"
								+ pesquisa + "%' order by c.nome_categoria;");
				break;

			case "fornecedor_produto":
				stmt = this.conexao
						.prepareStatement("select distinct(co.fornecedor_compra), p.*, c.nome_categoria from Produto as p inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto inner join Compra as co on co.codigo_compra = ic.codigo_compra inner join Categoria as c on c.codigo_categoria = p.categoria_produto where co.fornecedor_compra="
								+ pesquisa + ";");
				break;
			default:
				break;

			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setCodigo_produto(rs.getInt("codigo_produto"));
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setValor_unitario(rs.getDouble("valor_unitario"));
				produto.setQuantidade_atual(rs.getInt("quantidade_atual"));
				produto.setEstoque_minimo(rs.getInt("estoque_minimo"));
				produto.setAtivo(rs.getBoolean("ativo"));
				produto.getCategoria_produto().setNome_categoria(
						rs.getString("nome_categoria"));

				// adicionando o objeto à lista
				produtos.add(produto);
			}

			rs.close();
			stmt.close();
			return produtos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean nomeProduto(Produto produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT nome_produto FROM Produto;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// criando o objeto categoria
				String nomeProduto = rs.getString("nome_produto");

				if (produto.getNome_produto().equals(nomeProduto)) {
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

	public ArrayList<String> comboProduto() {
		try {
			ArrayList<String> listaProduto = new ArrayList<String>();
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT nome_produto FROM Produto where ativo=true ORDER BY nome_produto;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				listaProduto.add(rs.getString("nome_produto"));
			}
			rs.close();
			stmt.close();
			return listaProduto;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int estoqueMinimoProduto(Integer codigo_produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select estoque_minimo from Produto where codigo_produto="
							+ codigo_produto + ";");
			ResultSet rs = stmt.executeQuery();

			int estoque_minimo = 0;

			while (rs.next()) {
				estoque_minimo = rs.getInt("estoque_minimo");
			}
			rs.close();
			stmt.close();
			return estoque_minimo;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> listaProduto(Integer codigo_produto) {
		String sql = "select * from Produto where codigo_produto=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, codigo_produto);
			ResultSet rs = stmt.executeQuery();

			List<String> produtos = new ArrayList<String>();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setValor_unitario(rs.getDouble("valor_unitario"));

				// adicionando o objeto à lista
				produtos.add(produto.getNome_produto());
				produtos.add(produto.getValor_unitario().toString());
			}

			rs.close();
			stmt.close();
			return produtos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> listaProduto(String nome_produto) {
		String sql = "select * from Produto where nome_produto like '"
				+ nome_produto + "';";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			List<String> produtos = new ArrayList<String>();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setCodigo_produto(rs.getInt("codigo_produto"));
				produto.setValor_unitario(rs.getDouble("valor_unitario"));

				// adicionando o objeto à lista
				produtos.add(produto.getCodigo_produto().toString());
				produtos.add(produto.getValor_unitario().toString());
			}

			rs.close();
			stmt.close();
			return produtos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Produto> listar() {
		try {
			List<Produto> produtos = new ArrayList<Produto>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select p.*, c.nome_categoria from Produto as p inner join Categoria as c on c.codigo_categoria = p.categoria_produto;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setCodigo_produto(rs.getInt("codigo_produto"));
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setValor_unitario(rs.getDouble("valor_unitario"));
				produto.setQuantidade_atual(rs.getInt("quantidade_atual"));
				produto.setEstoque_minimo(rs.getInt("estoque_minimo"));
				produto.setAtivo(rs.getBoolean("ativo"));
				produto.getCategoria_produto().setNome_categoria(
						rs.getString("nome_categoria"));

				// adicionando o objeto à lista
				produtos.add(produto);
			}

			rs.close();
			stmt.close();
			return produtos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean ativoNome(Produto produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT ativo from Produto where nome_produto='"
							+ produto.getNome_produto() + "';");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Boolean ativo = rs.getBoolean("ativo");

				if (ativo) {
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

	public boolean ativoCodigo(Produto produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SELECT ativo from Produto where codigo_produto="
							+ produto.getCodigo_produto() + ";");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Boolean ativo = rs.getBoolean("ativo");

				if (ativo) {
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

	public int countAtivoInativo(Boolean valor) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Produto where ativo="
							+ valor + ";");
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

	public int countProdutoVenda(Integer codigo_produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Produto as p inner join Item_pedido as ip on ip.codigo_produto = p.codigo_produto where p.codigo_produto="
							+ codigo_produto + ";");
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

	public int countProdutoCompra(Integer codigo_produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select count(*) from Produto as p inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto where p.codigo_produto="
							+ codigo_produto + ";");
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

	public List<Produto> consultaInativo() {
		try {
			List<Produto> produtos = new ArrayList<Produto>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select p.*, c.nome_categoria from Produto as p inner join Categoria as c on c.codigo_categoria = p.categoria_produto where p.ativo=false;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setCodigo_produto(rs.getInt("codigo_produto"));
				produto.setNome_produto(rs.getString("nome_produto"));
				produto.setValor_unitario(rs.getDouble("valor_unitario"));
				produto.setEstoque_minimo(rs.getInt("estoque_minimo"));
				produto.setAtivo(rs.getBoolean("ativo"));
				produto.getCategoria_produto().setNome_categoria(
						rs.getString("nome_categoria"));

				// adicionando o objeto à lista
				produtos.add(produto);
			}

			rs.close();
			stmt.close();
			return produtos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Não permite alterar o nome, quantidade_atual e o valor unitário
	public void alterar(Produto produto) {
		String sql = "UPDATE Produto SET estoque_minimo=?, ativo=?, categoria_produto=(select codigo_categoria as categoria_produto from Categoria where nome_categoria like '"
				+ produto.getCategoria_produto().getNome_categoria()
				+ "') WHERE codigo_produto=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setInt(1, produto.getEstoque_minimo());
			stmt.setBoolean(2, produto.getAtivo());
			stmt.setInt(3, produto.getCodigo_produto());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int quantidadeProduto(Integer codigo_produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select quantidade_atual from Produto where codigo_produto="
							+ codigo_produto + ";");
			ResultSet rs = stmt.executeQuery();

			int quantidade = 0;

			while (rs.next()) {
				quantidade = rs.getInt("quantidade_atual");
			}
			rs.close();
			stmt.close();
			return quantidade;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void diminuiQuantidade(Item_pedido item_pedido) {
		String sql = "update Produto set quantidade_atual=? where codigo_produto=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			int codigoProduto = item_pedido.getProduto().getCodigo_produto();

			int novaQuantidade = quantidadeProduto(codigoProduto)
					- item_pedido.getQuatidade_item();

			// preenche os valores
			stmt.setInt(1, novaQuantidade);
			stmt.setInt(2, codigoProduto);

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void aumentaQuantidade(Item_pedido item_pedido) {
		String sql = "update Produto set quantidade_atual=? where codigo_produto=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			int codigoProduto = item_pedido.getProduto().getCodigo_produto();
			int novaQuantidade = quantidadeProduto(codigoProduto)
					+ item_pedido.getQuatidade_item();

			// preenche os valores
			stmt.setInt(1, novaQuantidade);
			stmt.setInt(2, codigoProduto);

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void aumentaQuantidade(Item_compra item_compra) {
		String sql = "update Produto set quantidade_atual=? where codigo_produto=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			int codigoProduto = item_compra.getProduto().getCodigo_produto();
			int novaQuantidade = quantidadeProduto(codigoProduto)
					+ item_compra.getQuatidade_item();

			// preenche os valores
			stmt.setInt(1, novaQuantidade);
			stmt.setInt(2, codigoProduto);

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Double valorUnitario(Integer codigo_produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select ic.valor_unitario_compra + (ic.valor_unitario_compra * (c.percentual_lucro/100)) as valor_unitario  from Produto as p inner join Categoria as c on c.codigo_categoria = p.categoria_produto inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto where ic.codigo_produto="
							+ codigo_produto
							+ " and ic.codigo_compra = (select max(codigo_compra) from Item_compra);");
			ResultSet rs = stmt.executeQuery();

			Double valor_unitario = 0.0;

			while (rs.next()) {
				valor_unitario = rs.getDouble("valor_unitario");
			}
			rs.close();
			stmt.close();
			return valor_unitario;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Double valorCusto(Integer codigo_produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select ic.valor_unitario_compra from Produto as p inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto where p.codigo_produto="
							+ codigo_produto
							+ " and ic.codigo_compra = (select max(codigo_compra) from Item_compra where codigo_produto="
							+ codigo_produto + ");");
			ResultSet rs = stmt.executeQuery();

			Double valor_custo = 0.0;

			while (rs.next()) {
				valor_custo = rs.getDouble("valor_unitario_compra");
			}
			rs.close();
			stmt.close();
			return valor_custo;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Calendar dataCompraProduto(Integer codigo_produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("select c.data_compra from Produto as p inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto inner join Compra as c on c.codigo_compra = ic.codigo_compra where p.codigo_produto="
							+ codigo_produto
							+ " and c.codigo_compra = (select max(c.codigo_compra) from Compra as c inner join Item_compra as ic on ic.codigo_compra = c.codigo_compra where ic.codigo_produto="
							+ codigo_produto + ");");
			ResultSet rs = stmt.executeQuery();

			Calendar data = Calendar.getInstance();

			while (rs.next()) {
				data.setTime(rs.getDate("data_compra"));
			}
			rs.close();
			stmt.close();
			return data;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void reajustePreco(Item_compra item_compra) {
		String sql = "update Produto set valor_unitario=? where codigo_produto=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			int codigo_produto = item_compra.getProduto().getCodigo_produto();

			// preenche os valores
			stmt.setDouble(1, valorUnitario(codigo_produto));
			stmt.setInt(2, codigo_produto);

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(Produto produto) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Produto WHERE codigo_produto=?;");

			// preenche os valores
			stmt.setInt(1, produto.getCodigo_produto());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
