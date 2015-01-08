package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Item_pedido;

public class Item_pedidoDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public Item_pedidoDao() {
		this.conexao = new Conexao().getConnection();
	}

	public void inserir(List<Item_pedido> itemPedido) {
		String sql = "INSERT INTO Item_pedido (codigo_pedido, codigo_produto, quantidade_item, valor_unitario_pedido) VALUES (?,?,?,?);";

		try {

			ProdutoDao daoProduto = new ProdutoDao();

			for (Item_pedido item_pedido : itemPedido) {
				PreparedStatement stmt = this.conexao.prepareStatement(sql);

				// preenche os valores
				stmt.setInt(1, item_pedido.getPedido().getCodigo_pedido());
				stmt.setInt(2, item_pedido.getProduto().getCodigo_produto());
				stmt.setInt(3, item_pedido.getQuatidade_item());
				stmt.setDouble(4, item_pedido.getValor_unitario_pedido());

				// executa
				stmt.execute();
				stmt.close();

				daoProduto.diminuiQuantidade(item_pedido);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Item_pedido> listar(Integer codigo_pedido) {
		String sql = "select ip.codigo_produto, ip.codigo_pedido, ip.quantidade_item,  p.nome_produto, ip.valor_unitario_pedido from Item_pedido as ip inner join Produto as p on p.codigo_produto = ip.codigo_produto where ip.codigo_pedido=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, codigo_pedido);
			ResultSet rs = stmt.executeQuery();

			List<Item_pedido> item_pedidos = new ArrayList<Item_pedido>();

			while (rs.next()) {

				Item_pedido item_pedido = new Item_pedido();
				item_pedido.getProduto().setCodigo_produto(
						rs.getInt("codigo_produto"));
				item_pedido.getPedido().setCodigo_pedido(
						rs.getInt("codigo_pedido"));
				item_pedido.setQuatidade_item(rs.getInt("quantidade_item"));
				item_pedido.getProduto().setNome_produto(
						rs.getString("nome_produto"));
				item_pedido.setValor_unitario_pedido(rs
						.getDouble("valor_unitario_pedido"));
				item_pedido.setLucro(lucroProduto(item_pedido));

				// adicionando o objeto à lista
				item_pedidos.add(item_pedido);

			}

			rs.close();
			stmt.close();
			return item_pedidos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Double lucroProduto(Item_pedido item_pedido) {
		String sql = "select ip.quantidade_item * (ip.valor_unitario_pedido - ic.valor_unitario_compra) as lucro  from Item_pedido as ip inner join Produto as p on p.codigo_produto = ip.codigo_produto inner join Item_compra as ic on ic.codigo_produto = p.codigo_produto  where ip.codigo_pedido=? and ic.codigo_compra in (select max(codigo_compra) from Item_compra where codigo_produto=?) and p.codigo_produto=?;";
		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, item_pedido.getPedido().getCodigo_pedido());
			stmt.setInt(2, item_pedido.getProduto().getCodigo_produto());
			stmt.setInt(3, item_pedido.getProduto().getCodigo_produto());
			ResultSet rs = stmt.executeQuery();

			Double lucro = 0.0;

			while (rs.next()) {
				lucro = rs.getDouble("lucro");
			}
			rs.close();
			stmt.close();
			return lucro;
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

	public void alterar(Item_pedido item_pedido) {
		String sql = "UPDATE Item_pedido SET codigo_pedido=?, codigo_produto=?, quatidade_item=?, valor_unitario_pedido=? WHERE codigo_pedido=? AND codigo_produto=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setInt(1, item_pedido.getPedido().getCodigo_pedido());
			stmt.setInt(2, item_pedido.getProduto().getCodigo_produto());
			stmt.setInt(3, item_pedido.getQuatidade_item());
			stmt.setDouble(4, item_pedido.getValor_unitario_pedido());
			stmt.setInt(5, item_pedido.getPedido().getCodigo_pedido());
			stmt.setInt(6, item_pedido.getProduto().getCodigo_produto());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(List<Item_pedido> itemPedido) {
		String sql = "DELETE FROM Item_pedido WHERE codigo_pedido=? AND codigo_produto=?;";

		try {

			ProdutoDao daoProduto = new ProdutoDao();

			for (Item_pedido item_pedido : itemPedido) {
				PreparedStatement stmt = this.conexao.prepareStatement(sql);

				// preenche os valores
				stmt.setInt(1, item_pedido.getPedido().getCodigo_pedido());
				stmt.setInt(2, item_pedido.getProduto().getCodigo_produto());

				// executa
				stmt.execute();
				stmt.close();

				daoProduto.aumentaQuantidade(item_pedido);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}