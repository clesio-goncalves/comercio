package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import jdbc.dao.Item_pedidoDao;
import jdbc.dao.PedidoDao;
import jdbc.dao.PesquisaVendaDao;
import bean.Item_pedido;
import bean.Pedido;

public class JanListagemVenda extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer quantidade_itens = 0;
	private Double total_lucro = 0.0;
	private String nome = null;
	private List<Pedido> listaPedido;
	private JButton buttonExcluir;
	private JButton buttonPesquisar;
	private JButton buttonFechar;
	private JTextField textCodigo;
	private JTextField textFuncionario;
	private JTextField textUsuario;
	private JTextField textCliente;
	private JTextField textAcrescimo;
	private JTextField textDesconto;
	private JTextField textLucro;
	private JTextField textQuantidade;
	private JLabel labelCodigo;
	private JLabel labelFuncionario;
	private JLabel labelUsuario;
	private JLabel labelCliente;
	private JLabel labelAcrescimo;
	private JLabel labelDesconto;
	private JLabel labelTotalVenda;
	private JLabel labelQuantidade;
	private JLabel labelLucro;
	private JPanel panelPedido;
	private JPanel panelTotalVenda;
	private JScrollPane scrollPaneData;
	private JScrollPane scrollPaneInformacoes;
	private JTable tableData;
	private JTable tableInformacoes;

	public JanListagemVenda(String nome) {
		super("Listagem de vendas");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		this.nome = nome;
		PedidoDao daoPedido = new PedidoDao();

		if (this.nome.equals("pesquisa")) {
			PesquisaVendaDao dao = new PesquisaVendaDao();
			this.listaPedido = dao.listar();

			if (this.listaPedido.size() == 0) {
				this.listaPedido = daoPedido.listar();
			}
		} else {
			this.listaPedido = daoPedido.listar();
		}

		// Item_pedido
		Item_pedidoDao daoItemPedido = new Item_pedidoDao();
		List<Item_pedido> listaItemPedido = daoItemPedido.listar(0);

		preencherJTableData(this.listaPedido);
		preencherJTableInformacoes(listaItemPedido);
		Invisivel();
		desativarBotoes();
	}

	public void Invisivel() {
		this.textCodigo.setEditable(false);
		this.textFuncionario.setEditable(false);
		this.textUsuario.setEditable(false);
		this.textCliente.setEditable(false);
		this.textAcrescimo.setEditable(false);
		this.textDesconto.setEditable(false);
		this.textQuantidade.setEditable(false);
		this.textLucro.setEditable(false);
	}

	public void esvasiarCampos() {
		this.textCodigo.setText("");
		this.textFuncionario.setText("");
		this.textUsuario.setText("");
		this.textCliente.setText("");
		this.textAcrescimo.setText("");
		this.textDesconto.setText("");
		this.textQuantidade.setText("");
		this.textLucro.setText("");

		this.labelTotalVenda.setText("");
	}

	public void desativarBotoes() {
		this.buttonExcluir.setEnabled(false);
	}

	// teste campos vaisios
	public Boolean testeCampos() {
		if (this.textCodigo.getText().equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTableData(List<Pedido> listaPedido) {

		this.tableData.getColumnModel().getColumn(0).setPreferredWidth(5);

		DefaultTableModel tabela = (DefaultTableModel) this.tableData
				.getModel();
		tabela.setNumRows(0);

		for (Pedido pedido : listaPedido) {

			String dataFormatada = new SimpleDateFormat("dd-MM-yyyy")
					.format(pedido.getData_pedido().getTime());

			tabela.addRow(new Object[] { dataFormatada });
		}

		this.tableData.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanListagemVenda.this.tableDataTableChanged(e);
					}
				});

	}

	public void preencherJTableInformacoes(List<Item_pedido> listaItemPedido) {

		this.tableInformacoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacoes.getColumnModel().getColumn(1)
				.setPreferredWidth(5);
		this.tableInformacoes.getColumnModel().getColumn(2)
				.setPreferredWidth(150);
		this.tableInformacoes.getColumnModel().getColumn(3)
				.setPreferredWidth(10);
		this.tableInformacoes.getColumnModel().getColumn(4)
				.setPreferredWidth(10);
		this.tableInformacoes.getColumnModel().getColumn(5)
				.setPreferredWidth(10);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacoes
				.getModel();
		tabela.setNumRows(0);

		DecimalFormat df = new DecimalFormat("0.00");

		this.quantidade_itens = 0;
		this.total_lucro = 0.0;

		for (Item_pedido item_pedido : listaItemPedido) {

			tabela.addRow(new Object[] {
					item_pedido.getQuatidade_item(),
					item_pedido.getProduto().getCodigo_produto(),
					item_pedido.getProduto().getNome_produto(),
					df.format(item_pedido.getValor_unitario_pedido()),
					df.format(item_pedido.getQuatidade_item()
							* item_pedido.getValor_unitario_pedido()),
					df.format(item_pedido.getLucro()) });

			this.quantidade_itens++;
			this.total_lucro += item_pedido.getLucro();
		}

	}

	private void Componentes() {
		this.buttonExcluir = new JButton();
		this.buttonPesquisar = new JButton();
		this.buttonFechar = new JButton();
		this.textCodigo = new JTextField();
		this.textFuncionario = new JTextField();
		this.textUsuario = new JTextField();
		this.textCliente = new JTextField();
		this.textAcrescimo = new JTextField();
		this.textDesconto = new JTextField();
		this.textQuantidade = new JTextField();
		this.textLucro = new JTextField();
		this.labelCodigo = new JLabel();
		this.labelFuncionario = new JLabel();
		this.labelUsuario = new JLabel();
		this.labelCliente = new JLabel();
		this.labelAcrescimo = new JLabel();
		this.labelDesconto = new JLabel();
		this.labelTotalVenda = new JLabel();
		this.labelQuantidade = new JLabel();
		this.labelLucro = new JLabel();
		this.panelPedido = new JPanel();
		this.panelTotalVenda = new JPanel();

		// Data
		this.scrollPaneData = new JScrollPane();
		scrollPaneData
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableData = new JTable();
		tableData.setColumnSelectionAllowed(true);

		// Informações
		this.scrollPaneInformacoes = new JScrollPane();
		scrollPaneInformacoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacoes = new JTable();
		tableInformacoes.setColumnSelectionAllowed(true);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL Produto ----------- //
		this.panelPedido.setBorder(BorderFactory.createTitledBorder(""));

		// Botão Excluir
		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanListagemVenda.this.buttonExcluirActionPerformed(evt);
			}
		});

		// Botão pesquisar
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanListagemVenda.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Botão Fechar
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/fechar.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanListagemVenda.this.buttonFecharActionPerformed(evt);
			}
		});

		// Código
		this.labelCodigo.setText("Código venda");
		this.labelCodigo.setFont(new Font("Tahoma", 1, 11));
		this.textCodigo.setFont(new Font("Tahoma", 1, 11));

		// Funcionário
		this.labelFuncionario.setText("Funcionário");
		this.labelFuncionario.setFont(new Font("Tahoma", 1, 11));
		this.textFuncionario.setFont(new Font("Tahoma", 1, 11));

		// Usuário
		this.labelUsuario.setText("Usuário");
		this.labelUsuario.setFont(new Font("Tahoma", 1, 11));
		this.textUsuario.setFont(new Font("Tahoma", 1, 11));

		// Cliente
		this.labelCliente.setText("Cliente");
		this.labelCliente.setFont(new Font("Tahoma", 1, 11));
		this.textCliente.setFont(new Font("Tahoma", 1, 11));

		// Acrescimo
		this.labelAcrescimo.setText("Acréscimo");
		this.labelAcrescimo.setFont(new Font("Tahoma", 1, 11));
		this.textAcrescimo.setFont(new Font("Tahoma", 1, 11));

		// Desconto
		this.labelDesconto.setText("Desconto");
		this.labelDesconto.setFont(new Font("Tahoma", 1, 11));
		this.textDesconto.setFont(new Font("Tahoma", 1, 11));

		// Quantidade
		this.labelQuantidade.setText("Quantidade de itens:");
		this.labelQuantidade.setFont(new Font("Tahoma", 1, 11));
		this.textQuantidade.setFont(new Font("Tahoma", 1, 11));

		// Lucro
		this.labelLucro.setText("Lucro total:");
		this.labelLucro.setFont(new Font("Tahoma", 1, 11));
		this.textLucro.setFont(new Font("Tahoma", 1, 11));

		// ---------- PANEL TOTAL PEDIDO ----------- //
		this.panelTotalVenda.setBorder(BorderFactory
				.createTitledBorder("Total Venda R$"));
		this.labelTotalVenda.setFont(new Font("Tahoma", 1, 25));
		this.panelTotalVenda.add(labelTotalVenda);

		GroupLayout gl_panelTotalVenda = new GroupLayout(panelTotalVenda);
		gl_panelTotalVenda.setHorizontalGroup(gl_panelTotalVenda
				.createParallelGroup(Alignment.TRAILING).addGroup(
						gl_panelTotalVenda
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(labelTotalVenda,
										GroupLayout.DEFAULT_SIZE, 135,
										Short.MAX_VALUE).addContainerGap()));
		gl_panelTotalVenda.setVerticalGroup(gl_panelTotalVenda
				.createParallelGroup(Alignment.TRAILING).addGroup(
						gl_panelTotalVenda
								.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(labelTotalVenda)
								.addContainerGap()));
		panelTotalVenda.setLayout(gl_panelTotalVenda);

		GroupLayout gl_panelPedido = new GroupLayout(panelPedido);
		gl_panelPedido
				.setHorizontalGroup(gl_panelPedido
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panelPedido
										.createSequentialGroup()
										.addGap(18)
										.addComponent(scrollPaneData,
												GroupLayout.PREFERRED_SIZE, 95,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_panelPedido
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelPedido
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelPedido
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								textAcrescimo,
																								GroupLayout.DEFAULT_SIZE,
																								102,
																								Short.MAX_VALUE)
																						.addComponent(
																								labelAcrescimo,
																								GroupLayout.DEFAULT_SIZE,
																								102,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panelPedido
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelPedido
																										.createSequentialGroup()
																										.addGap(20)
																										.addComponent(
																												labelDesconto))
																						.addGroup(
																								gl_panelPedido
																										.createSequentialGroup()
																										.addGap(18)
																										.addComponent(
																												textDesconto,
																												GroupLayout.PREFERRED_SIZE,
																												100,
																												GroupLayout.PREFERRED_SIZE)))
																		.addGap(50)
																		.addComponent(
																				panelTotalVenda,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panelPedido
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelPedido
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelPedido
																										.createSequentialGroup()
																										.addGroup(
																												gl_panelPedido
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																labelCliente,
																																GroupLayout.PREFERRED_SIZE,
																																76,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																textFuncionario,
																																GroupLayout.DEFAULT_SIZE,
																																311,
																																Short.MAX_VALUE))
																										.addGap(12))
																						.addGroup(
																								gl_panelPedido
																										.createSequentialGroup()
																										.addComponent(
																												labelFuncionario)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)))
																		.addGroup(
																				gl_panelPedido
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelUsuario,
																								GroupLayout.PREFERRED_SIZE,
																								76,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textUsuario,
																								GroupLayout.PREFERRED_SIZE,
																								116,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																gl_panelPedido
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelPedido
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelCodigo,
																								GroupLayout.PREFERRED_SIZE,
																								113,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textCodigo,
																								GroupLayout.PREFERRED_SIZE,
																								100,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				65,
																				Short.MAX_VALUE)
																		.addComponent(
																				buttonExcluir)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				buttonPesquisar,
																				GroupLayout.PREFERRED_SIZE,
																				142,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																textCliente,
																GroupLayout.PREFERRED_SIZE,
																439,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		gl_panelPedido
				.setVerticalGroup(gl_panelPedido
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panelPedido
										.createSequentialGroup()
										.addGroup(
												gl_panelPedido
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																Alignment.LEADING,
																gl_panelPedido
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				scrollPaneData,
																				GroupLayout.DEFAULT_SIZE,
																				241,
																				Short.MAX_VALUE))
														.addGroup(
																Alignment.LEADING,
																gl_panelPedido
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelPedido
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelPedido
																										.createSequentialGroup()
																										.addContainerGap()
																										.addGroup(
																												gl_panelPedido
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																buttonPesquisar,
																																GroupLayout.PREFERRED_SIZE,
																																33,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																buttonExcluir,
																																GroupLayout.PREFERRED_SIZE,
																																33,
																																GroupLayout.PREFERRED_SIZE)))
																						.addGroup(
																								gl_panelPedido
																										.createSequentialGroup()
																										.addGap(20)
																										.addComponent(
																												labelCodigo,
																												GroupLayout.PREFERRED_SIZE,
																												14,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textCodigo,
																												GroupLayout.PREFERRED_SIZE,
																												18,
																												GroupLayout.PREFERRED_SIZE)
																										.addGap(14)
																										.addGroup(
																												gl_panelPedido
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																labelUsuario,
																																GroupLayout.PREFERRED_SIZE,
																																14,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																labelFuncionario,
																																GroupLayout.PREFERRED_SIZE,
																																14,
																																GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_panelPedido
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																textFuncionario,
																																GroupLayout.PREFERRED_SIZE,
																																18,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																textUsuario,
																																GroupLayout.PREFERRED_SIZE,
																																18,
																																GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												ComponentPlacement.UNRELATED)
																										.addComponent(
																												labelCliente,
																												GroupLayout.PREFERRED_SIZE,
																												14,
																												GroupLayout.PREFERRED_SIZE)))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				textCliente,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(93))
														.addGroup(
																Alignment.LEADING,
																gl_panelPedido
																		.createSequentialGroup()
																		.addGap(173)
																		.addGroup(
																				gl_panelPedido
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								gl_panelPedido
																										.createSequentialGroup()
																										.addGroup(
																												gl_panelPedido
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																labelDesconto,
																																GroupLayout.PREFERRED_SIZE,
																																14,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																labelAcrescimo,
																																GroupLayout.PREFERRED_SIZE,
																																14,
																																GroupLayout.PREFERRED_SIZE))
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_panelPedido
																														.createParallelGroup(
																																Alignment.BASELINE)
																														.addComponent(
																																textAcrescimo,
																																GroupLayout.PREFERRED_SIZE,
																																18,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																textDesconto,
																																GroupLayout.PREFERRED_SIZE,
																																18,
																																GroupLayout.PREFERRED_SIZE)))
																						.addComponent(
																								panelTotalVenda,
																								GroupLayout.DEFAULT_SIZE,
																								80,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		panelPedido.setLayout(gl_panelPedido);

		// ---------- TABLE DATA ----------- //
		this.tableData.setModel(new DefaultTableModel(new Object[][] { { null,
				null, null, null, null } }, new String[] { "Data" }));

		this.tableData.setFocusable(false);
		this.scrollPaneData.setViewportView(this.tableData);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																scrollPaneInformacoes,
																GroupLayout.DEFAULT_SIZE,
																582,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				labelQuantidade)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textQuantidade,
																				GroupLayout.PREFERRED_SIZE,
																				48,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelLucro)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textLucro,
																				GroupLayout.PREFERRED_SIZE,
																				100,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				66,
																				Short.MAX_VALUE)
																		.addComponent(
																				buttonFechar,
																				GroupLayout.PREFERRED_SIZE,
																				128,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																panelPedido,
																GroupLayout.PREFERRED_SIZE,
																582,
																Short.MAX_VALUE))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(panelPedido,
												GroupLayout.PREFERRED_SIZE,
												262, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(scrollPaneInformacoes,
												GroupLayout.DEFAULT_SIZE, 132,
												Short.MAX_VALUE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				buttonFechar)
																		.addContainerGap())
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								textQuantidade,
																								GroupLayout.PREFERRED_SIZE,
																								18,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								labelQuantidade,
																								GroupLayout.PREFERRED_SIZE,
																								14,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								labelLucro,
																								GroupLayout.PREFERRED_SIZE,
																								14,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textLucro,
																								GroupLayout.PREFERRED_SIZE,
																								18,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(24)))));

		getContentPane().setLayout(groupLayout);

		// ---------- TABLE INFORMAÇÕES ----------- //
		this.tableInformacoes.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null } }, new String[] { "Qnte",
				"Código", "Produto", "Valor Unit.", "Total", "Lucro" }));

		this.tableInformacoes.setFocusable(false);
		this.scrollPaneInformacoes.setViewportView(this.tableInformacoes);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 531) / 2, (screenSize.height - 545) / 2,
				608, 527);

	}

	// Data
	private void tableDataTableChanged(TableModelEvent e) {

		int column = e.getColumn();
		int row = e.getFirstRow();

		if (column < 0 || row < 0)
			return;

		// Código
		Integer codigo_pedido = this.listaPedido.get(row).getCodigo_pedido();

		PedidoDao daoPedido = new PedidoDao();
		DecimalFormat df = new DecimalFormat("0.00");

		// Pedido
		List<String> listaPedido = daoPedido.listagemPedidoVenda(codigo_pedido);

		this.textCodigo.setText(listaPedido.get(0).toString());
		this.textAcrescimo.setText(df.format(Double.parseDouble(listaPedido
				.get(1).toString())));
		this.textDesconto.setText(df.format(Double.parseDouble(listaPedido.get(
				2).toString())));
		this.labelTotalVenda.setText(df.format(Double.parseDouble(listaPedido
				.get(3).toString())));
		this.textFuncionario.setText(listaPedido.get(4).toString());
		this.textUsuario.setText(listaPedido.get(5).toString());
		this.textCliente.setText(listaPedido.get(6).toString());

		// Item_pedido
		Item_pedidoDao daoItemPedido = new Item_pedidoDao();
		List<Item_pedido> listaItemPedido = daoItemPedido.listar(codigo_pedido);

		preencherJTableInformacoes(listaItemPedido);

		// Quantidade itens
		this.textQuantidade.setText(this.quantidade_itens.toString());
		this.textLucro.setText(df.format(this.total_lucro).toString());

		this.buttonExcluir.setEnabled(true);

	}

	// ButtonPesquisar.
	private void buttonPesquisarActionPerformed(ActionEvent evt) {
		dispose();
		JanPesquisaVenda janP = new JanPesquisaVenda();
		janP.setVisible(true);
	}

	// ButtonFechar.
	private void buttonFecharActionPerformed(ActionEvent evt) {
		switch (this.nome) {
		case "venda":
			dispose();
			JanVenda janPe = new JanVenda();
			janPe.setVisible(true);
			break;

		case "principal":
			dispose();
			JanPrincipal janP = new JanPrincipal();
			janP.setVisible(true);
			break;

		case "pesquisa":
			dispose();
			JanPrincipal janPr = new JanPrincipal();
			janPr.setVisible(true);
			break;
		default:
			break;
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum pedido selecionado!");
		} else {
			Pedido pedido = new Pedido();
			pedido.setCodigo_pedido(Integer.parseInt((this.textCodigo.getText())));

			String mensagem = "Deseja excluir o pedido de código "
					+ pedido.getCodigo_pedido() + "?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				PedidoDao dao = new PedidoDao();
				dao.remover(pedido);

				// Item_pedido
				Item_pedidoDao daoItemPedido = new Item_pedidoDao();
				List<Item_pedido> listaItemPedido = daoItemPedido.listar(pedido
						.getCodigo_pedido());

				daoItemPedido.remover(listaItemPedido);

				this.listaPedido = dao.listar();
				preencherJTableData(this.listaPedido);

				// Item_pedido
				listaItemPedido = daoItemPedido.listar(0);
				preencherJTableInformacoes(listaItemPedido);

				esvasiarCampos();
				Invisivel();
				desativarBotoes();
			}
		}
	}

	// Ok_cancel
	private int okcancel(String mensagem) {
		int resultado = JOptionPane.showConfirmDialog((Component) null,
				mensagem, "Exclusão", JOptionPane.OK_CANCEL_OPTION);
		return resultado;
	}
}
