package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdbc.Conexao;
import bean.Item_compra;

public class Item_compraDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public Item_compraDao() {
		this.conexao = new Conexao().getConnection();
	}

	public void inserir(List<Item_compra> itemCompra, Boolean reajuste) {
		String sql = "INSERT INTO Item_compra (codigo_compra, codigo_produto, quantidade_item, valor_unitario_compra) VALUES (?,?,?,?);";

		try {

			ProdutoDao daoProduto = new ProdutoDao();

			for (Item_compra item_compra : itemCompra) {
				PreparedStatement stmt = this.conexao.prepareStatement(sql);

				// preenche os valores
				stmt.setInt(1, item_compra.getCompra().getCodigo_compra());
				stmt.setInt(2, item_compra.getProduto().getCodigo_produto());
				stmt.setInt(3, item_compra.getQuatidade_item());
				stmt.setDouble(4, item_compra.getValor_unitario_compra());

				// executa
				stmt.execute();
				stmt.close();

				daoProduto.aumentaQuantidade(item_compra);

				if (reajuste) {
					daoProduto.reajustePreco(item_compra);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Item_compra> listar(Integer codigo_compra) {
		String sql = "select ic.codigo_produto, ic.codigo_compra, ic.quantidade_item,  p.nome_produto, ic.valor_unitario_compra from Item_compra as ic inner join Produto as p on p.codigo_produto = ic.codigo_produto where ic.codigo_compra=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, codigo_compra);
			ResultSet rs = stmt.executeQuery();

			List<Item_compra> item_compras = new ArrayList<Item_compra>();

			while (rs.next()) {

				Item_compra item_compra = new Item_compra();
				item_compra.getProduto().setCodigo_produto(
						rs.getInt("codigo_produto"));
				item_compra.getCompra().setCodigo_compra(
						rs.getInt("codigo_compra"));
				item_compra.setQuatidade_item(rs.getInt("quantidade_item"));
				item_compra.getProduto().setNome_produto(
						rs.getString("nome_produto"));
				item_compra.setValor_unitario_compra(rs
						.getDouble("valor_unitario_compra"));

				// adicionando o objeto à lista
				item_compras.add(item_compra);

			}

			rs.close();
			stmt.close();
			return item_compras;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void alterar(Item_compra item_compra) {
		String sql = "UPDATE Item_compra SET codigo_compra=?, codigo_produto=?, quatidade_item=?, valor_unitario_compra=? WHERE codigo_compra=? AND codigo_produto=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setInt(1, item_compra.getCompra().getCodigo_compra());
			stmt.setInt(2, item_compra.getProduto().getCodigo_produto());
			stmt.setInt(3, item_compra.getQuatidade_item());
			stmt.setDouble(4, item_compra.getValor_unitario_compra());
			stmt.setInt(5, item_compra.getCompra().getCodigo_compra());
			stmt.setInt(6, item_compra.getProduto().getCodigo_produto());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(List<Item_compra> itemCompra) {
		String sql = "DELETE FROM Item_compra WHERE codigo_compra=? AND codigo_produto=?;";

		try {

			ProdutoDao daoProduto = new ProdutoDao();

			for (Item_compra item_compra : itemCompra) {
				PreparedStatement stmt = this.conexao.prepareStatement(sql);

				// preenche os valores
				stmt.setInt(1, item_compra.getCompra().getCodigo_compra());
				stmt.setInt(2, item_compra.getProduto().getCodigo_produto());

				// executa
				stmt.execute();
				stmt.close();

				daoProduto.aumentaQuantidade(item_compra);
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}