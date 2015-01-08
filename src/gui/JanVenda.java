package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import regraTextField.JtextFieldDecimal;
import regraTextField.JtextFieldSomenteNumeros;
import jdbc.dao.CargoDao;
import jdbc.dao.CategoriaDao;
import jdbc.dao.ClienteDao;
import jdbc.dao.ComercioDao;
import jdbc.dao.EnderecoDao;
import jdbc.dao.FornecedorDao;
import jdbc.dao.FuncionarioDao;
import jdbc.dao.Item_pedidoDao;
import jdbc.dao.PedidoDao;
import jdbc.dao.ProdutoDao;
import jdbc.dao.UsuarioDao;
import bean.Cliente;
import bean.Item_pedido;
import bean.Pedido;
import bean.Produto;

public class JanVenda extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String valor = "";
	String inicioData = null;
	String acao = "adicionar";
	private ArrayList<Item_pedido> itemVenda;
	private int numero;
	private Double total_venda = 0.0;
	private JTextField textCodigoCliente;
	private JTextField textCpfCliente;
	private JTextField textNomeCliente;
	private JTextField textQuantidadeProduto;
	private JTextField textCodigoProduto;
	private JComboBox<Object> comboNomeProduto;
	private JTextField textValorUnitario;
	private JTextField textValorTotal;
	private JTextField textValor;
	private JButton buttonAdicionar;
	private JButton buttonAtualizar;
	private JButton buttonExcluir;
	private JButton buttonFaturarVenda;
	private JButton buttonFechar;
	private JLabel labelCodigoCliente;
	private JLabel labelCpfCliente;
	private JLabel labelNomeCliente;
	private JLabel labelQuantidadeProduto;
	private JLabel labelCodigoProduto;
	private JLabel labelNomeProduto;
	private JLabel labelValorUnitario;
	private JLabel labelValorTotal;
	private JLabel labelTotalVenda;
	private JLabel labelValor;
	private JMenuBar menuBar;
	private JMenu menuCadastro;
	private JMenuItem subCadCliente;
	private JMenuItem subCadFuncionario;
	private JMenuItem subCadProduto;
	private JMenu menuListagem;
	private JPanel panelCliente;
	private JPanel panelProduto;
	private JPanel panelTotalVenda;
	private JScrollPane scrollPaneInformacaoes;
	private JTable tableInformacoes;
	private JPanel panelValor;
	private JRadioButton radioAcrescimo;
	private JRadioButton radioDesconto;

	public JanVenda() {
		super("Venda");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		preencherJTable();
		Invisivel();
		desativarBotoes();

		// Produto
		this.comboNomeProduto.removeAllItems();
		this.comboNomeProduto.addItem("");

		ProdutoDao daoProduto = new ProdutoDao();
		ArrayList<String> produto = daoProduto.comboProduto();
		Iterator<String> i = produto.iterator();

		while (i.hasNext()) {
			this.comboNomeProduto.addItem(String.valueOf(i.next()));
		}
		
		UsuarioDao daoUsuario = new UsuarioDao();
		if (daoUsuario.consultarNivelLogado() != 3) {
			this.subCadFuncionario.setEnabled(false);
		}
	}

	public void Invisivel() {
		this.textCpfCliente.setEditable(false);
		this.textNomeCliente.setEditable(false);
		this.textQuantidadeProduto.setEditable(false);
		this.textCodigoProduto.setEditable(false);
		this.comboNomeProduto.setEnabled(false);
		this.textValorUnitario.setEditable(false);
		this.textValorTotal.setEditable(false);
		this.textValor.setEditable(false);
	}

	public void VisivelProduto() {
		this.textCodigoProduto.setEditable(true);
		this.comboNomeProduto.setEnabled(true);
	}

	public void desativarBotoes() {
		this.buttonAdicionar.setEnabled(false);
		this.buttonAtualizar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonFaturarVenda.setEnabled(false);
	}

	public void botoesJTable() {
		this.buttonAtualizar.setEnabled(true);
		this.buttonExcluir.setEnabled(true);
		this.buttonAdicionar.setEnabled(false);
		this.buttonFaturarVenda.setEnabled(false);
	}

	public void botoes() {
		this.buttonAtualizar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonAdicionar.setEnabled(false);

		if (itemVenda.size() != 0) {
			this.buttonFaturarVenda.setEnabled(true);
		}
	}

	public void funcoesProduto() {
		this.textQuantidadeProduto.setText("");
		esvasiarCamposProduto();
		botoes();

		Invisivel();
		this.textQuantidadeProduto.setEditable(true);
		this.textQuantidadeProduto.grabFocus();
	}

	public void esvasiarCamposCliente() {
		this.textCodigoCliente.setText("");
		this.textCpfCliente.setText("");
		this.textNomeCliente.setText("");

		this.textCodigoCliente.grabFocus();
	}

	public void esvasiarCamposProduto() {
		this.textCodigoProduto.setText("");
		this.comboNomeProduto.setSelectedItem("");
		this.textValorUnitario.setText("");
		this.textValorTotal.setText("");
	}

	// testeCampos
	public Boolean testeCampos() {
		if ((this.textCodigoCliente.getText().equals(""))
				|| (this.textQuantidadeProduto.getText().equals(""))
				|| (this.textCodigoProduto.getText().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	// testeCamposFaturar
	public Boolean testeCamposFaturar() {
		if ((this.textCodigoCliente.getText().equals(""))
				|| (this.itemVenda.size() == 0)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean contem(Item_pedido item_pedido) {
		for (int i = 0; i < this.itemVenda.size(); i++) {
			if (item_pedido.equals(this.itemVenda.get(i))) {
				return true;
			}
		}
		return false;
	}

	public void preencherJTable() {

		this.tableInformacoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacoes.getColumnModel().getColumn(1)
				.setPreferredWidth(150);
		this.tableInformacoes.getColumnModel().getColumn(2)
				.setPreferredWidth(10);
		this.tableInformacoes.getColumnModel().getColumn(3)
				.setPreferredWidth(10);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacoes
				.getModel();
		tabela.setNumRows(0);

		this.total_venda = 0.0;

		for (Item_pedido item_pedido : this.itemVenda) {
			this.total_venda += item_pedido.getValor_unitario_pedido()
					* item_pedido.getQuatidade_item();
			tabela.addRow(new Object[] {
					item_pedido.getQuatidade_item(),
					item_pedido.getProduto().getNome_produto(),
					item_pedido.getValor_unitario_pedido(),
					item_pedido.getValor_unitario_pedido()
							* item_pedido.getQuatidade_item() });
		}

		DecimalFormat df = new DecimalFormat("0.00");

		if (this.total_venda != 0.0) {
			this.labelTotalVenda.setText(df.format(this.total_venda));
		} else {
			this.labelTotalVenda.setText("");
		}

		this.tableInformacoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanVenda.this.tableInformacoesTableChanged(e);
					}
				});

	}

	private void Componentes() {
		this.itemVenda = new ArrayList<Item_pedido>();
		this.textCodigoCliente = new JtextFieldSomenteNumeros(7);
		this.textCpfCliente = new JTextField();
		this.textNomeCliente = new JTextField();
		this.textQuantidadeProduto = new JtextFieldSomenteNumeros(7);
		this.textCodigoProduto = new JtextFieldSomenteNumeros(7);
		this.comboNomeProduto = new JComboBox<>();
		this.textValorUnitario = new JtextFieldDecimal(10);
		this.textValorTotal = new JtextFieldDecimal(10);
		this.textValor = new JtextFieldDecimal(10);
		this.buttonAtualizar = new JButton();
		this.buttonExcluir = new JButton();
		this.buttonAdicionar = new JButton();
		this.buttonFaturarVenda = new JButton();
		this.buttonFechar = new JButton();
		this.labelCodigoCliente = new JLabel();
		this.labelCpfCliente = new JLabel();
		this.labelNomeCliente = new JLabel();
		this.labelQuantidadeProduto = new JLabel();
		this.labelCodigoProduto = new JLabel();
		this.labelNomeProduto = new JLabel();
		this.labelValorUnitario = new JLabel();
		this.labelValorTotal = new JLabel();
		this.labelTotalVenda = new JLabel();
		this.labelValor = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuCadastro = new JMenu();
		this.subCadCliente = new JMenuItem();
		this.subCadFuncionario = new JMenuItem();
		this.subCadProduto = new JMenuItem();
		this.menuListagem = new JMenu();
		this.panelCliente = new JPanel();
		this.panelProduto = new JPanel();
		this.panelTotalVenda = new JPanel();
		this.panelValor = new JPanel();
		this.scrollPaneInformacaoes = new JScrollPane();
		scrollPaneInformacaoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacoes = new JTable();
		tableInformacoes.setColumnSelectionAllowed(true);
		this.radioAcrescimo = new JRadioButton();
		this.radioDesconto = new JRadioButton();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL CLIENTE ----------- //
		this.panelCliente
				.setBorder(BorderFactory.createTitledBorder("Cliente"));

		// Código
		this.labelCodigoCliente.setText("Código");
		this.labelCodigoCliente.setFont(new Font("Tahoma", 1, 11));
		this.textCodigoCliente.setFont(new Font("Tahoma", 1, 11));
		this.textCodigoCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.textCodigoClienteActionPerformed(evt);
			}
		});

		// CPF
		this.labelCpfCliente.setText("CPF");
		this.labelCpfCliente.setFont(new Font("Tahoma", 1, 11));
		this.textCpfCliente.setFont(new Font("Tahoma", 1, 11));

		// NOME
		this.labelNomeCliente.setText("Nome");
		this.labelNomeCliente.setFont(new Font("Tahoma", 1, 11));
		this.textNomeCliente.setFont(new Font("Tahoma", 1, 11));

		GroupLayout gl_panelCliente = new GroupLayout(panelCliente);
		gl_panelCliente
				.setHorizontalGroup(gl_panelCliente
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelCliente
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelCliente
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(
																textCodigoCliente)
														.addComponent(
																labelCodigoCliente,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelCliente
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																labelCpfCliente)
														.addComponent(
																textCpfCliente,
																GroupLayout.PREFERRED_SIZE,
																173,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelCliente
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																labelNomeCliente)
														.addComponent(
																textNomeCliente,
																GroupLayout.PREFERRED_SIZE,
																306,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		gl_panelCliente
				.setVerticalGroup(gl_panelCliente
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelCliente
										.createSequentialGroup()
										.addGap(5)
										.addGroup(
												gl_panelCliente
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelCodigoCliente)
														.addComponent(
																labelCpfCliente)
														.addComponent(
																labelNomeCliente,
																GroupLayout.DEFAULT_SIZE,
																14,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelCliente
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textCodigoCliente,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textCpfCliente,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textNomeCliente,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE))
										.addGap(19)));
		panelCliente.setLayout(gl_panelCliente);

		// ---------- PANEL Produto ----------- //
		this.panelProduto
				.setBorder(BorderFactory.createTitledBorder("Produto"));

		// Quantidade
		this.labelQuantidadeProduto.setText("Qnte");
		this.labelQuantidadeProduto.setFont(new Font("Tahoma", 1, 11));
		this.textQuantidadeProduto.setFont(new Font("Tahoma", 1, 11));
		this.textQuantidadeProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.textQuantidadeProdutoActionPerformed(evt);
			}
		});

		// Código
		this.labelCodigoProduto.setText("Código");
		this.labelCodigoProduto.setFont(new Font("Tahoma", 1, 11));
		this.textCodigoProduto.setFont(new Font("Tahoma", 1, 11));
		this.textCodigoProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.textCodigoProdutoActionPerformed(evt);
			}
		});

		// NOME
		this.labelNomeProduto.setText("Discriminação");
		this.labelNomeProduto.setFont(new Font("Tahoma", 1, 11));
		this.comboNomeProduto.setFont(new Font("Tahoma", 1, 11));
		this.comboNomeProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.comboNomeProdutoActionPerformed(evt);
			}
		});

		// Valor unitário
		this.labelValorUnitario.setText("Valor Unit.");
		this.labelValorUnitario.setFont(new Font("Tahoma", 1, 11));
		this.textValorUnitario.setFont(new Font("Tahoma", 1, 11));

		// Valor Total
		this.labelValorTotal.setText("Valor Total");
		this.labelValorTotal.setFont(new Font("Tahoma", 1, 11));
		this.textValorTotal.setFont(new Font("Tahoma", 1, 11));

		// BOTÕES

		// Botão Adicionar
		this.buttonAdicionar.setBackground(new Color(255, 255, 255));
		this.buttonAdicionar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/add.gif")));
		this.buttonAdicionar.setText("Adicionar");
		this.buttonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.buttonAdicionarActionPerformed(evt);
			}
		});

		// Botão Atualizar
		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.buttonAtualizarActionPerformed(evt);
			}
		});

		// Botão Excluir
		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.buttonExcluirActionPerformed(evt);
			}
		});

		// Botão Faturar Venda
		this.buttonFaturarVenda.setBackground(new Color(255, 255, 255));
		this.buttonFaturarVenda.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/dinheiro.png")));
		this.buttonFaturarVenda.setText("Faturar Venda");
		this.buttonFaturarVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.buttonFaturarVendaActionPerformed(evt);
			}
		});

		// Botão Fechar
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/fechar.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.buttonFecharActionPerformed(evt);
			}
		});

		GroupLayout gl_panelProduto = new GroupLayout(panelProduto);
		gl_panelProduto
				.setHorizontalGroup(gl_panelProduto
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panelProduto
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelProduto
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																scrollPaneInformacaoes,
																GroupLayout.DEFAULT_SIZE,
																538,
																Short.MAX_VALUE)
														.addGroup(
																gl_panelProduto
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelProduto
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelQuantidadeProduto,
																								GroupLayout.PREFERRED_SIZE,
																								31,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textQuantidadeProduto,
																								GroupLayout.PREFERRED_SIZE,
																								60,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panelProduto
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								textCodigoProduto)
																						.addComponent(
																								labelCodigoProduto))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panelProduto
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelNomeProduto)
																						.addComponent(
																								comboNomeProduto,
																								GroupLayout.PREFERRED_SIZE,
																								239,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panelProduto
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								textValorUnitario,
																								Alignment.TRAILING)
																						.addComponent(
																								labelValorUnitario,
																								Alignment.TRAILING))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panelProduto
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelValorTotal)
																						.addComponent(
																								textValorTotal,
																								GroupLayout.PREFERRED_SIZE,
																								79,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																gl_panelProduto
																		.createSequentialGroup()
																		.addGap(49)
																		.addComponent(
																				buttonAdicionar,
																				GroupLayout.DEFAULT_SIZE,
																				158,
																				Short.MAX_VALUE)
																		.addGap(80)
																		.addComponent(
																				buttonAtualizar,
																				GroupLayout.PREFERRED_SIZE,
																				126,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				buttonExcluir,
																				GroupLayout.PREFERRED_SIZE,
																				107,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		gl_panelProduto
				.setVerticalGroup(gl_panelProduto
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelProduto
										.createSequentialGroup()
										.addGap(5)
										.addGroup(
												gl_panelProduto
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelProduto
																		.createSequentialGroup()
																		.addComponent(
																				labelQuantidadeProduto,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(6)
																		.addComponent(
																				textQuantidadeProduto,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_panelProduto
																		.createSequentialGroup()
																		.addGroup(
																				gl_panelProduto
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								labelCodigoProduto)
																						.addComponent(
																								labelNomeProduto))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				gl_panelProduto
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								comboNomeProduto,
																								GroupLayout.PREFERRED_SIZE,
																								18,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textCodigoProduto,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																gl_panelProduto
																		.createSequentialGroup()
																		.addGap(20)
																		.addComponent(
																				textValorTotal,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																labelValorTotal,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																gl_panelProduto
																		.createSequentialGroup()
																		.addComponent(
																				labelValorUnitario,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textValorUnitario,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)))
										.addGap(18)
										.addGroup(
												gl_panelProduto
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																buttonExcluir,
																GroupLayout.PREFERRED_SIZE,
																33,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																buttonAtualizar,
																GroupLayout.PREFERRED_SIZE,
																34,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																buttonAdicionar,
																GroupLayout.PREFERRED_SIZE,
																43,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addComponent(scrollPaneInformacaoes,
												GroupLayout.DEFAULT_SIZE, 95,
												Short.MAX_VALUE)
										.addContainerGap()));
		panelProduto.setLayout(gl_panelProduto);

		// ---------- TABLE INFORMAÇÕES ----------- //
		this.tableInformacoes.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null } }, new String[] { "Qnte",
				"Discriminação", "Valor Unit.", "Total" }));

		this.tableInformacoes.setFocusable(false);
		this.scrollPaneInformacaoes.setViewportView(this.tableInformacoes);

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

		this.panelValor.setBorder(BorderFactory.createTitledBorder("Valor"));

		// radioAcrescimo.
		this.radioAcrescimo.setText("Acréscimo");
		this.radioAcrescimo.setFont(new Font("Tahoma", 1, 11));
		this.radioAcrescimo.setActionCommand("acrescimo");

		this.radioAcrescimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.percentualActionPerformed(evt);
			}
		});

		// radioDesconto.
		this.radioDesconto.setText("Desconto");
		this.radioDesconto.setFont(new Font("Tahoma", 1, 11));
		this.radioDesconto.setActionCommand("desconto");

		this.radioDesconto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.percentualActionPerformed(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioAcrescimo);
		group.add(this.radioDesconto);

		// Percentual
		this.labelValor.setText("Valor:");
		this.labelValor.setFont(new Font("Tahoma", 1, 11));
		this.textValor.setFont(new Font("Tahoma", 1, 11));
		this.textValor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.textPercentualActionPerformed(evt);
			}
		});

		GroupLayout gl_panelValor = new GroupLayout(panelValor);
		gl_panelValor
				.setHorizontalGroup(gl_panelValor
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelValor
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelValor
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelValor
																		.createSequentialGroup()
																		.addComponent(
																				radioAcrescimo)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioDesconto))
														.addGroup(
																gl_panelValor
																		.createSequentialGroup()
																		.addComponent(
																				labelValor)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textValor,
																				GroupLayout.DEFAULT_SIZE,
																				122,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		gl_panelValor
				.setVerticalGroup(gl_panelValor
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelValor
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelValor
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																radioAcrescimo)
														.addComponent(
																radioDesconto))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_panelValor
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textValor,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelValor,
																GroupLayout.PREFERRED_SIZE,
																14,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		panelValor.setLayout(gl_panelValor);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																Alignment.TRAILING,
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				panelValor,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				200,
																				Short.MAX_VALUE)
																		.addComponent(
																				panelTotalVenda,
																				GroupLayout.PREFERRED_SIZE,
																				169,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																panelProduto,
																Alignment.TRAILING,
																GroupLayout.PREFERRED_SIZE,
																582,
																Short.MAX_VALUE)
														.addComponent(
																panelCliente,
																Alignment.TRAILING,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																Alignment.TRAILING,
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				buttonFaturarVenda,
																				GroupLayout.PREFERRED_SIZE,
																				179,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				buttonFechar)))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(panelCliente,
												GroupLayout.PREFERRED_SIZE, 84,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(panelProduto,
												GroupLayout.PREFERRED_SIZE,
												251, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																panelTotalVenda,
																GroupLayout.PREFERRED_SIZE,
																75,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																panelValor,
																GroupLayout.PREFERRED_SIZE,
																90,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																buttonFechar,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																buttonFaturarVenda,
																GroupLayout.PREFERRED_SIZE,
																45,
																GroupLayout.PREFERRED_SIZE))
										.addGap(23)));
		getContentPane().setLayout(groupLayout);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 531) / 2, (screenSize.height - 545) / 2,
				606, 572);

		// ---------- BARRA DE FERRAMENTAS ----------- //
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();

		// Menu Cadastro.
		this.menuCadastro.setMnemonic('C');
		this.menuCadastro.setText("Cadastro");

		// Sub-menu Cliente
		this.subCadCliente.setText("Cliente");
		subCadCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				SHORTCUT_MASK));
		this.subCadCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.subCadClienteActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadCliente);

		// Sub-menu Funcionário
		this.subCadFuncionario.setText("Funcionário");
		subCadFuncionario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				SHORTCUT_MASK));
		this.subCadFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.subCadFuncionarioActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadFuncionario);

		// Sub-menu Produto
		this.subCadProduto.setText("Produto");
		subCadProduto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				SHORTCUT_MASK));
		this.subCadProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.subCadProdutoActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadProduto);

		this.menuBar.add(this.menuCadastro);

		// Menu Listagem.
		this.menuListagem.setMnemonic('L');
		this.menuListagem.setText("Listagem");
		this.menuListagem.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JanVenda.this.menuListagemActionPerformed(evt);
			}
		});
		this.menuListagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanVenda.this.menuListagemActionPerformed(evt);
			}
		});

		this.menuBar.add(this.menuListagem);

		setJMenuBar(this.menuBar);

	}

	private void percentualActionPerformed(ActionEvent evt) {
		valor = evt.getActionCommand();
		this.textValor.setEditable(true);
		this.textValor.grabFocus();

		this.textQuantidadeProduto.setEditable(false);
		this.buttonAdicionar.setEnabled(false);
		getRootPane().setDefaultButton(buttonFaturarVenda);

	}

	// tableInformacoesTableChanged
	private void tableInformacoesTableChanged(TableModelEvent e) {

		int column = e.getColumn();
		int row = e.getFirstRow();

		if (column < 0 || row < 0)
			return;

		Integer quantidade = Integer.parseInt(tableInformacoes.getValueAt(row,
				0).toString());
		String nome = tableInformacoes.getValueAt(row, 1).toString();

		this.textQuantidadeProduto.setText(quantidade.toString());
		this.comboNomeProduto.setSelectedItem(nome.toString());

		this.textQuantidadeProduto.grabFocus();
		botoesJTable();

		this.numero = row;
		this.acao = "atualizar";
	}

	// ButtonAdicionar.
	private void buttonAdicionarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Todos os campos são obrigatórios!");

			this.textQuantidadeProduto.grabFocus();
		} else {
			Item_pedido item_pedido = new Item_pedido();

			// Pega o código do pedido
			PedidoDao daoPedido = new PedidoDao();
			String auto_incremento = Long.toString(daoPedido
					.retornaAutoIncrement());
			item_pedido.getPedido().setCodigo_pedido(
					Integer.parseInt(auto_incremento));

			// Pega o código do produto
			item_pedido.getProduto().setCodigo_produto(
					Integer.parseInt(this.textCodigoProduto.getText()));

			int codigo_produto = item_pedido.getProduto().getCodigo_produto();

			item_pedido.getProduto().setNome_produto(
					this.comboNomeProduto.getSelectedItem().toString());

			// Quantidade
			item_pedido.setQuatidade_item(Integer
					.parseInt(this.textQuantidadeProduto.getText()));

			// Valor unitário
			item_pedido.setValor_unitario_pedido(Double
					.parseDouble(this.textValorUnitario.getText()));

			ProdutoDao daoProduto = new ProdutoDao();

			if (this.contem(item_pedido)) {
				JOptionPane.showMessageDialog(null,
						"Produto já adicionado no pedido");
			} else if (daoProduto.quantidadeProduto(codigo_produto) >= item_pedido
					.getQuatidade_item()) {
				if (daoProduto.quantidadeProduto(codigo_produto) <= daoProduto
						.estoqueMinimoProduto(codigo_produto)) {
					JOptionPane.showMessageDialog(null,
							"Estoque mínimo atingido");
				}

				// Adiciona na lista
				this.itemVenda.add(item_pedido);
				preencherJTable();
			} else {
				JOptionPane.showMessageDialog(null, "Quantidade insuficiente");
			}
			funcoesProduto();
		}
		this.buttonAdicionar.setEnabled(false);
	}

	// ButtonAtualisar.
	private void buttonAtualizarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Todos os campos são obrigatórios!");
			this.textQuantidadeProduto.grabFocus();
		} else {

			Item_pedido item_pedido = new Item_pedido();

			// Pega o código do pedido
			PedidoDao daoPedido = new PedidoDao();
			String auto_incremento = Long.toString(daoPedido
					.retornaAutoIncrement());
			item_pedido.getPedido().setCodigo_pedido(
					Integer.parseInt(auto_incremento));

			// Pega o código do produto
			item_pedido.getProduto().setCodigo_produto(
					Integer.parseInt(this.textCodigoProduto.getText()));

			int codigo_produto = item_pedido.getProduto().getCodigo_produto();

			item_pedido.getProduto().setNome_produto(
					this.comboNomeProduto.getSelectedItem().toString());

			// Quantidade
			item_pedido.setQuatidade_item(Integer
					.parseInt(this.textQuantidadeProduto.getText()));

			// Valor unitário
			item_pedido.setValor_unitario_pedido(Double
					.parseDouble(this.textValorUnitario.getText()));

			ProdutoDao daoProduto = new ProdutoDao();

			if (daoProduto.quantidadeProduto(codigo_produto) >= item_pedido
					.getQuatidade_item()) {
				if (daoProduto.quantidadeProduto(codigo_produto) <= daoProduto
						.estoqueMinimoProduto(codigo_produto)) {
					JOptionPane.showMessageDialog(null,
							"Estoque mínimo atingido");
				}

				// Altera na lista
				this.itemVenda.set(this.numero, item_pedido);
				getRootPane().setDefaultButton(buttonAdicionar);
				
				preencherJTable();
				funcoesProduto();
				this.acao = "adicionar";
			} else {
				JOptionPane.showMessageDialog(null, "Quantidade insuficiente");
				this.textQuantidadeProduto.grabFocus();
			}
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum item selecionado!");
		} else {
			String mensagem = "Deseja excluir o item?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {

				// remove da lista
				this.itemVenda.remove(this.numero);

				this.total_venda = 0.0;

				preencherJTable();
				funcoesProduto();
			}
		}
	}

	// Ok_cancel
	private int okcancel(String mensagem) {
		int resultado = JOptionPane.showConfirmDialog((Component) null,
				mensagem, "Exclusão", JOptionPane.OK_CANCEL_OPTION);
		return resultado;
	}

	// ButtonFechar.
	private void buttonFecharActionPerformed(ActionEvent evt) {
		dispose();
		JanPrincipal janP = new JanPrincipal();
		janP.setVisible(true);
	}

	// buttonFaturarVenda.
	private void buttonFaturarVendaActionPerformed(ActionEvent evt) {
		if (this.testeCamposFaturar()) {
			JOptionPane.showMessageDialog(null,
					"Todos os campos são obrigatórios!");
		} else {

			// Pedido
			PedidoDao daoPedido = new PedidoDao();
			Pedido pedido = new Pedido();

			pedido.setData_pedido(Calendar.getInstance());
			pedido.setValor_total_pedido(this.total_venda);

			// Valor
			switch (this.valor) {
			case "acrescimo":
				pedido.setAcrescimo_pedido(Double.parseDouble(this.textValor
						.getText()));
				pedido.setDesconto_pedido(0.0);
				break;
			case "desconto":
				pedido.setDesconto_pedido(Double.parseDouble(this.textValor
						.getText()));
				pedido.setAcrescimo_pedido(0.0);
				break;

			default:
				pedido.setAcrescimo_pedido(0.0);
				pedido.setDesconto_pedido(0.0);
				break;
			}

			// Funcionário.
			FuncionarioDao daoFuncionario = new FuncionarioDao();
			pedido.getFuncionario_pedido().setCodigo_funcionario(
					daoFuncionario.consultarCodigoFuncionario());

			// Cliente
			pedido.getCliente_pedido().setCodigo_cliente(
					Integer.parseInt(this.textCodigoCliente.getText()));

			// grave nessa conexão!!! -- Pedido
			daoPedido.inserir(pedido);

			// grave nessa conexão!!! -- Item pedido
			Item_pedidoDao daoItemPedido = new Item_pedidoDao();
			daoItemPedido.inserir(this.itemVenda);

			JOptionPane.showMessageDialog(null, "Venda faturada com sucesso!");

			dispose();
			JanListagemVenda cadC = new JanListagemVenda("principal");
			cadC.setVisible(true);
		}
	}

	// SubCadCliente.
	private void subCadClienteActionPerformed(ActionEvent evt) {
		// EnderecoDao
		EnderecoDao dao = new EnderecoDao();

		if (dao.count() > 0) {
			dispose();
			JanCadCliente cadC = new JanCadCliente("venda");
			cadC.setVisible(true);
		} else {
			dispose();
			JanCadEndereco cadE = new JanCadEndereco("Cliente");
			cadE.setVisible(true);
		}
	}

	// SubCadFuncionario.
	private void subCadFuncionarioActionPerformed(ActionEvent evt) {
		// CargoDao
		CargoDao daoCargo = new CargoDao();

		// ComercioDao
		ComercioDao daoComercio = new ComercioDao();

		if (daoCargo.count() > 0) {
			if (daoComercio.count() > 0) {
				dispose();
				JanCadFuncionario cadF = new JanCadFuncionario("venda");
				cadF.setVisible(true);
			} else {
				dispose();
				JanCadComercio cadC = new JanCadComercio("funcionario");
				cadC.setVisible(true);
			}
		} else {
			dispose();
			JanCadCargo cadC = new JanCadCargo("funcionario");
			cadC.setVisible(true);
		}
	}

	// SubCadProduto.
	private void subCadProdutoActionPerformed(ActionEvent evt) {

		// FornecedorDao
		FornecedorDao daoFornecedor = new FornecedorDao();

		// CategoriaDao
		CategoriaDao daoCategoria = new CategoriaDao();

		if (daoFornecedor.count() > 0) {
			if (daoCategoria.count() > 0) {
				dispose();
				JanCadProduto cadP = new JanCadProduto("venda");
				cadP.setVisible(true);
			} else {
				dispose();
				JanCadCategoria cadC = new JanCadCategoria("produto");
				cadC.setVisible(true);
			}
		} else {
			dispose();
			JanCadFornecedor cadC = new JanCadFornecedor("produto");
			cadC.setVisible(true);
		}
	}

	// Listagem.
	private void menuListagemActionPerformed(MouseEvent evt) {
		dispose();
		JanListagemVenda cadC = new JanListagemVenda("venda");
		cadC.setVisible(true);
	}

	// Listagem.
	private void menuListagemActionPerformed(ActionEvent evt) {
		menuListagemActionPerformed(evt);
	}

	// textCodigoCliente
	private void textCodigoClienteActionPerformed(ActionEvent evt) {
		if (!this.textCodigoCliente.getText().equals("")) {
			ClienteDao dao = new ClienteDao();
			Cliente cliente = new Cliente();

			cliente.setCodigo_cliente(Integer.parseInt(this.textCodigoCliente
					.getText()));

			List<String> listaCliente = dao.listaCliente(cliente
					.getCodigo_cliente());

			if (listaCliente.size() != 0) {
				if (dao.ativoCodigo(cliente)) {

					this.textCpfCliente.setText(listaCliente.get(0));
					this.textNomeCliente.setText(listaCliente.get(1));

					this.textQuantidadeProduto.setEditable(true);
					this.textQuantidadeProduto.grabFocus();
					this.textCodigoCliente.setEditable(false);

				} else {
					JOptionPane.showMessageDialog(null, "Cliente inativo");
					esvasiarCamposCliente();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Cliente não cadastrado");
				esvasiarCamposCliente();
			}
		} else {
			esvasiarCamposCliente();
		}
	}

	// textQuantidadeProduto
	private void textQuantidadeProdutoActionPerformed(ActionEvent evt) {
		if (!this.textQuantidadeProduto.getText().equals("")) {
			if (acao.equals("adicionar")) {
				VisivelProduto();
				this.textCodigoProduto.grabFocus();
			} else {
				this.buttonAtualizar.grabFocus();
				getRootPane().setDefaultButton(buttonAtualizar);
			}
		}
	}

	// textCodigoProduto
	private void textCodigoProdutoActionPerformed(ActionEvent evt) {
		if (!this.textCodigoProduto.getText().equals("")) {
			ProdutoDao dao = new ProdutoDao();
			Produto produto = new Produto();

			produto.setCodigo_produto(Integer.parseInt(this.textCodigoProduto
					.getText()));

			List<String> listaProduto = dao.listaProduto(produto
					.getCodigo_produto());

			if (listaProduto.size() != 0) {
				if (dao.ativoCodigo(produto)) {
					this.comboNomeProduto.setSelectedItem(listaProduto.get(0));
					this.textValorUnitario.setText(listaProduto.get(1));

					Double resultado = Double
							.parseDouble(this.textQuantidadeProduto.getText())
							* Double.parseDouble(this.textValorUnitario
									.getText());

					this.textValorTotal.setText(resultado.toString());

					this.buttonAdicionar.setEnabled(true);
					this.buttonAdicionar.grabFocus();
					getRootPane().setDefaultButton(buttonAdicionar);
				} else {
					JOptionPane.showMessageDialog(null, "Produto inativo");
					funcoesProduto();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Produto não cadastrado");
				esvasiarCamposProduto();
			}
		} else {
			esvasiarCamposProduto();
		}
	}

	// comboNomeProduto.
	private void comboNomeProdutoActionPerformed(ActionEvent evt) {
		if (!this.comboNomeProduto.getSelectedItem().equals("")) {

			ProdutoDao dao = new ProdutoDao();
			Produto produto = new Produto();

			produto.setNome_produto(this.comboNomeProduto.getSelectedItem()
					.toString());

			List<String> listaProduto = dao.listaProduto(produto
					.getNome_produto());
			this.textCodigoProduto.setText(listaProduto.get(0));
			this.textValorUnitario.setText(listaProduto.get(1));

			Double resultado = Double.parseDouble(this.textQuantidadeProduto
					.getText())
					* Double.parseDouble(this.textValorUnitario.getText());

			this.textValorTotal.setText(resultado.toString());
			this.buttonAdicionar.setEnabled(true);
			this.buttonAdicionar.grabFocus();
			getRootPane().setDefaultButton(buttonAdicionar);

		} else {
			esvasiarCamposProduto();
		}
	}

	// textvalor
	private void textPercentualActionPerformed(ActionEvent evt) {
		if (!this.textValor.getText().equals("")) {

			Double valor = Double.parseDouble(this.textValor.getText());

			// Valor
			switch (this.valor) {
			case "acrescimo":
				this.total_venda += valor;
				break;
			case "desconto":

				if (valor <= this.total_venda) {
					this.total_venda -= valor;
				} else {
					JOptionPane.showMessageDialog(null,
							"Desconto maior que o valor total da venda");
				}
				break;

			default:
				break;
			}

			DecimalFormat df = new DecimalFormat("0.00");

			this.labelTotalVenda.setText(df.format(this.total_venda));

			getRootPane().setDefaultButton(buttonFaturarVenda);
			this.buttonFaturarVenda.grabFocus();
		}
	}
}
