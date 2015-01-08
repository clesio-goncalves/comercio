package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import jdbc.Conexao;
import bean.Pedido;
import bean.PesquisaVenda;

public class PesquisaVendaDao {

	// Conexão com o banco de dados
	private Connection conexao;

	public PesquisaVendaDao() {
		this.conexao = new Conexao().getConnection();
	}

	public void inserir(PesquisaVenda pesquisaVenda) {

		String sql = "insert into Pesquisa select distinct(p.codigo_pedido) as codigo, p.data_pedido as data from Pedido as p inner join Item_pedido as ip on ip.codigo_pedido = p.codigo_pedido where";

		Boolean anterior = false;

		if (pesquisaVenda.getData_inicial() != null
				&& pesquisaVenda.getData_final() != null) {
			String data_inicial = new SimpleDateFormat("yyyy-MM-dd")
					.format(pesquisaVenda.getData_inicial().getTime());

			String data_final = new SimpleDateFormat("yyyy-MM-dd")
					.format(pesquisaVenda.getData_final().getTime());
			sql += " p.data_pedido between '" + data_inicial + "' and '"
					+ data_final + "'";
			anterior = true;
		}

		if (pesquisaVenda.getCodigo_pedido() != 0) {
			if (anterior) {
				sql += " and p.codigo_pedido="
						+ pesquisaVenda.getCodigo_pedido() + "";
			} else {
				sql += " p.codigo_pedido=" + pesquisaVenda.getCodigo_pedido()
						+ "";
			}
			anterior = true;
		}

		if (pesquisaVenda.getCodigo_cliente() != 0) {
			if (anterior) {
				sql += " and p.cliente_pedido="
						+ pesquisaVenda.getCodigo_cliente() + "";
			} else {
				sql += " p.cliente_pedido=" + pesquisaVenda.getCodigo_cliente()
						+ "";
			}
			anterior = true;
		}

		if (pesquisaVenda.getCodigo_produto() != 0) {
			if (anterior) {
				sql += " and ip.codigo_produto="
						+ pesquisaVenda.getCodigo_produto() + "";
			} else {
				sql += " ip.codigo_produto="
						+ pesquisaVenda.getCodigo_produto() + "";
			}
		}

		sql += " order by p.data_pedido desc, p.codigo_pedido desc;";

		try {
			PreparedStatement stmt = this.conexao.prepareStatement(sql);

			// executa
			remover();
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Pedido> listar() {
		try {
			List<Pedido> pedidosPesquisados = new ArrayList<Pedido>();

			PreparedStatement stmt = this.conexao
					.prepareStatement("select * from Pesquisa;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Pedido pedidos = new Pedido();
				pedidos.setCodigo_pedido(rs.getInt("codigo"));

				// montando a data através do Calendar
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate("data"));
				pedidos.setData_pedido(data);

				// adicionando o objeto à lista
				pedidosPesquisados.add(pedidos);
			}

			rs.close();
			stmt.close();
			return pedidosPesquisados;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void remover() {
		try {
			PreparedStatement stmt = this.conexao
					.prepareStatement("delete from Pesquisa;");

			// executa
			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
