package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jdbc.Conexao;
import bean.Compra;

public class CompraDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public CompraDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Compra';");
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

	public void inserir(Compra compra) {
		String sql = "INSERT INTO Compra (data_compra, valor_total_compra, desconto_compra, funcionario_compra, fornecedor_compra) VALUES (?,?,?,?,?);";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setDate(1, new Date(compra.getData_compra().getTimeInMillis()));
			stmt.setDouble(2, compra.getValor_total_compra());
			stmt.setDouble(3, compra.getDesconto_compra());
			stmt.setInt(4, compra.getFuncionario_compra()
					.getCodigo_funcionario());
			stmt.setInt(5, compra.getFornecedor_compra().getCodigo_fornecedor());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public List<Compra> listar() {
		try {
			List<Compra> compras = new ArrayList<Compra>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select codigo_compra, data_compra from Compra order by data_compra desc, codigo_compra desc;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Compra compra = new Compra();
				compra.setCodigo_compra(rs.getInt("codigo_compra"));

				// montando a data através do Calendar
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("data_compra"));
				compra.setData_compra(data);

				// adicionando o objeto à lista
				compras.add(compra);
			}

			rs.close();
			stmt.close();
			return compras;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> listagemCompra(Integer codigo_compra) {
		String sql = "select c.codigo_compra, c.desconto_compra, c.valor_total_compra, f.nome_funcionario, u.nome_usuario, fo.nome_fornecedor from Compra as c inner join Funcionario as f on f.codigo_funcionario = c.funcionario_compra inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario inner join Fornecedor as fo on fo.codigo_fornecedor = c.fornecedor_compra where c.codigo_compra=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, codigo_compra);
			ResultSet rs = stmt.executeQuery();

			List<String> compras = new ArrayList<String>();

			while (rs.next()) {
				Compra compra = new Compra();
				compra.setCodigo_compra(rs.getInt("codigo_compra"));
				compra.setDesconto_compra(rs.getDouble("desconto_compra"));
				compra.setValor_total_compra(rs.getDouble("valor_total_compra"));
				compra.getFuncionario_compra().setNome_funcionario(
						rs.getString("nome_funcionario"));
				compra.getFuncionario_compra().getUsuario_funcionario()
						.setNome_usuario(rs.getString("nome_usuario"));
				compra.getFornecedor_compra().setNome_fornecedor(
						rs.getString("nome_fornecedor"));

				// adicionando o objeto à lista
				compras.add(compra.getCodigo_compra().toString());
				compras.add(compra.getDesconto_compra().toString());
				compras.add(compra.getValor_total_compra().toString());
				compras.add(compra.getFuncionario_compra()
						.getNome_funcionario().toString());
				compras.add(compra.getFuncionario_compra()
						.getUsuario_funcionario().getNome_usuario().toString());
				compras.add(compra.getFornecedor_compra().getNome_fornecedor()
						.toString());

			}

			rs.close();
			stmt.close();
			return compras;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Não permite alterar o valor total da compra
	public void alterar(Compra compra) {
		String sql = "UPDATE Compra SET data_compra=?, desconto_compra=?, funcionario_compra=?, fornecedor_compra=? WHERE codigo_compra=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setDate(1, new Date(compra.getData_compra().getTimeInMillis()));
			stmt.setDouble(2, compra.getDesconto_compra());
			stmt.setInt(3, compra.getFuncionario_compra()
					.getCodigo_funcionario());
			stmt.setInt(4, compra.getFornecedor_compra().getCodigo_fornecedor());
			stmt.setInt(5, compra.getCodigo_compra());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(Compra compra) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Compra WHERE codigo_compra=?;");

			// preenche os valores
			stmt.setInt(1, compra.getCodigo_compra());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
