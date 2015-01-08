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
import bean.Pedido;

public class PedidoDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public PedidoDao() {
		this.conexao = new Conexao().getConnection();
	}

	public long retornaAutoIncrement() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("SHOW TABLE STATUS LIKE 'Pedido';");
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

	public void inserir(Pedido pedido) {
		String sql = "INSERT INTO Pedido (data_pedido, valor_total_pedido, acrescimo_pedido, desconto_pedido, funcionario_pedido, cliente_pedido) VALUES (?,?,?,?,?,?);";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setDate(1, new Date(pedido.getData_pedido().getTimeInMillis()));
			stmt.setDouble(2, pedido.getValor_total_pedido());
			stmt.setDouble(3, pedido.getAcrescimo_pedido());
			stmt.setDouble(4, pedido.getDesconto_pedido());
			stmt.setInt(5, pedido.getFuncionario_pedido()
					.getCodigo_funcionario());
			stmt.setInt(6, pedido.getCliente_pedido().getCodigo_cliente());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Pedido> listar() {
		try {
			List<Pedido> pedidos = new ArrayList<Pedido>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select codigo_pedido, data_pedido from Pedido order by data_pedido desc, codigo_pedido desc;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setCodigo_pedido(rs.getInt("codigo_pedido"));

				// montando a data através do Calendar
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("data_pedido"));
				pedido.setData_pedido(data);

				// adicionando o objeto à lista
				pedidos.add(pedido);
			}

			rs.close();
			stmt.close();
			return pedidos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<String> listagemPedidoVenda(Integer codigo_pedido) {
		String sql = "select p.codigo_pedido, p.acrescimo_pedido, p.desconto_pedido, p.valor_total_pedido,  f.nome_funcionario, u.nome_usuario, cl.nome_cliente from Pedido as p inner join Funcionario as f on f.codigo_funcionario = p.funcionario_pedido inner join Usuario as u on u.codigo_usuario = f.usuario_funcionario inner join Cliente as cl on cl.codigo_cliente = p.cliente_pedido where p.codigo_pedido=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);
			stmt.setInt(1, codigo_pedido);
			ResultSet rs = stmt.executeQuery();

			List<String> pedidos = new ArrayList<String>();

			while (rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setCodigo_pedido(rs.getInt("codigo_pedido"));
				pedido.setAcrescimo_pedido(rs.getDouble("acrescimo_pedido"));
				pedido.setDesconto_pedido(rs.getDouble("desconto_pedido"));
				pedido.setValor_total_pedido(rs.getDouble("valor_total_pedido"));
				pedido.getFuncionario_pedido().setNome_funcionario(
						rs.getString("nome_funcionario"));
				pedido.getFuncionario_pedido().getUsuario_funcionario()
						.setNome_usuario(rs.getString("nome_usuario"));
				pedido.getCliente_pedido().setNome_cliente(
						rs.getString("nome_cliente"));

				// adicionando o objeto à lista
				pedidos.add(pedido.getCodigo_pedido().toString());
				pedidos.add(pedido.getAcrescimo_pedido().toString());
				pedidos.add(pedido.getDesconto_pedido().toString());
				pedidos.add(pedido.getValor_total_pedido().toString());
				pedidos.add(pedido.getFuncionario_pedido()
						.getNome_funcionario().toString());
				pedidos.add(pedido.getFuncionario_pedido()
						.getUsuario_funcionario().getNome_usuario().toString());
				pedidos.add(pedido.getCliente_pedido().getNome_cliente()
						.toString());

			}

			rs.close();
			stmt.close();
			return pedidos;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// Não permite alterar o valor total da pedido
	public void alterar(Pedido pedido) {
		String sql = "UPDATE Pedido SET data_pedido=?, acrescimo_pedido=?, desconto_pedido=?, funcionario_pedido=?, cliente_pedido=? WHERE codigo_pedido=?;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// preenche os valores
			stmt.setDate(1, new Date(pedido.getData_pedido().getTimeInMillis()));
			stmt.setDouble(2, pedido.getAcrescimo_pedido());
			stmt.setDouble(3, pedido.getDesconto_pedido());
			stmt.setInt(4, pedido.getFuncionario_pedido()
					.getCodigo_funcionario());
			stmt.setInt(5, pedido.getCliente_pedido().getCodigo_cliente());
			stmt.setInt(6, pedido.getCodigo_pedido());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover(Pedido pedido) {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("DELETE FROM Pedido WHERE codigo_pedido=?;");

			// preenche os valores
			stmt.setInt(1, pedido.getCodigo_pedido());

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
