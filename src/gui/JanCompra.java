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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
import jdbc.dao.ComercioDao;
import jdbc.dao.CompraDao;
import jdbc.dao.EnderecoDao;
import jdbc.dao.FornecedorDao;
import jdbc.dao.FuncionarioDao;
import jdbc.dao.Item_compraDao;
import jdbc.dao.ProdutoDao;
import jdbc.dao.UsuarioDao;
import bean.Compra;
import bean.Fornecedor;
import bean.Item_compra;
import bean.Produto;

public class JanCompra extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String percentual = "";
	String inicioData = null;
	String acao = "adicionar";
	private ArrayList<Item_compra> itemCompra;
	private int numero;
	private Double total_compra = 0.0;
	private JTextField textCodigoFornecedor;
	private JTextField textCnpjFornecedor;
	private JTextField textNomeFornecedor;
	private JTextField textQuantidadeProduto;
	private JTextField textCodigoProduto;
	private JComboBox<Object> comboNomeProduto;
	private JTextField textValorUnitario;
	private JTextField textValorTotal;
	private JTextField textDesconto;
	private JButton buttonAdicionar;
	private JButton buttonAtualizar;
	private JButton buttonExcluir;
	private JButton buttonFaturarCompra;
	private JButton buttonFechar;
	private JCheckBox checkBoxReajuste;
	private JLabel labelCodigoFornecedor;
	private JLabel labelCnpjFornecedor;
	private JLabel labelNomeFornecedor;
	private JLabel labelQuantidadeProduto;
	private JLabel labelCodigoProduto;
	private JLabel labelNomeProduto;
	private JLabel labelValorUnitario;
	private JLabel labelValorTotal;
	private JLabel labelTotalCompra;
	private JLabel labelDesconto;
	private JMenuBar menuBar;
	private JMenu menuCadastro;
	private JMenuItem subCadFornecedor;
	private JMenuItem subCadFuncionario;
	private JMenuItem subCadProduto;
	private JMenu menuListagem;
	private JPanel panelFornecedor;
	private JPanel panelProduto;
	private JPanel panelTotalCompra;
	private JScrollPane scrollPaneInformacoes;
	private JTable tableInformacoes;

	public JanCompra() {
		super("Compra");
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
		this.textCnpjFornecedor.setEditable(false);
		this.textNomeFornecedor.setEditable(false);
		this.textQuantidadeProduto.setEditable(false);
		this.textCodigoProduto.setEditable(false);
		this.comboNomeProduto.setEnabled(false);
		this.textValorUnitario.setEditable(false);
		this.textValorTotal.setEditable(false);
		this.textDesconto.setEditable(false);
	}

	public void VisivelProduto() {
		this.textCodigoProduto.setEditable(true);
		this.comboNomeProduto.setEnabled(true);
		this.textValorUnitario.setEditable(true);
	}

	public void desativarBotoes() {
		this.buttonAdicionar.setEnabled(false);
		this.buttonAtualizar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonFaturarCompra.setEnabled(false);
	}

	public void botoesJTable() {
		this.buttonAtualizar.setEnabled(true);
		this.buttonExcluir.setEnabled(true);
		this.buttonAdicionar.setEnabled(false);
		this.buttonFaturarCompra.setEnabled(false);
	}

	public void botoes() {
		this.buttonAtualizar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonAdicionar.setEnabled(false);

		if (itemCompra.size() != 0) {
			this.buttonFaturarCompra.setEnabled(true);
		}

	}

	public void funcoesProduto() {
		this.textQuantidadeProduto.setText("");
		esvasiarCamposProduto();
		botoes();

		Invisivel();
		this.textQuantidadeProduto.setEditable(true);
		this.textQuantidadeProduto.grabFocus();
		if (itemCompra.size() != 0) {
			this.textDesconto.setEditable(true);
		} else {
			this.textDesconto.setEditable(false);
		}

	}

	public void esvasiarCamposFornecedor() {
		this.textCodigoFornecedor.setText("");
		this.textCnpjFornecedor.setText("");
		this.textNomeFornecedor.setText("");

		this.textCodigoFornecedor.grabFocus();
	}

	public void esvasiarCamposProduto() {
		this.textCodigoProduto.setText("");
		this.comboNomeProduto.setSelectedItem("");
		this.textValorUnitario.setText("");
		this.textValorTotal.setText("");
	}

	// testeCampos
	public Boolean testeCampos() {
		if ((this.textCodigoFornecedor.getText().equals(""))
				|| (this.textQuantidadeProduto.getText().equals(""))
				|| (this.textCodigoProduto.getText().equals(""))
				|| (this.textValorUnitario.getText().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	// testeCamposFaturar
	public Boolean testeCamposFaturar() {
		if ((this.textCodigoFornecedor.getText().equals(""))
				|| (this.itemCompra.size() == 0)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean contem(Item_compra item_compra) {
		for (int i = 0; i < this.itemCompra.size(); i++) {
			if (item_compra.equals(this.itemCompra.get(i))) {
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

		this.total_compra = 0.0;

		for (Item_compra item_compra : this.itemCompra) {
			this.total_compra += item_compra.getValor_unitario_compra()
					* item_compra.getQuatidade_item();
			tabela.addRow(new Object[] {
					item_compra.getQuatidade_item(),
					item_compra.getProduto().getNome_produto(),
					item_compra.getValor_unitario_compra(),
					item_compra.getValor_unitario_compra()
							* item_compra.getQuatidade_item() });
		}

		DecimalFormat df = new DecimalFormat("0.00");

		if (this.total_compra != 0.0) {
			this.labelTotalCompra.setText(df.format(this.total_compra));
		} else {
			this.labelTotalCompra.setText("");
		}

		this.tableInformacoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanCompra.this.tableInformacoesTableChanged(e);
					}
				});

	}

	private void Componentes() {
		this.itemCompra = new ArrayList<Item_compra>();
		this.textCodigoFornecedor = new JtextFieldSomenteNumeros(7);
		this.textCnpjFornecedor = new JTextField();
		this.textNomeFornecedor = new JTextField();
		this.textQuantidadeProduto = new JtextFieldSomenteNumeros(7);
		this.textCodigoProduto = new JtextFieldSomenteNumeros(7);
		this.comboNomeProduto = new JComboBox<>();
		this.textValorUnitario = new JtextFieldDecimal(10);
		this.textValorTotal = new JtextFieldDecimal(10);
		this.textDesconto = new JtextFieldDecimal(10);
		this.buttonAtualizar = new JButton();
		this.buttonExcluir = new JButton();
		this.buttonAdicionar = new JButton();
		this.buttonFaturarCompra = new JButton();
		this.buttonFechar = new JButton();
		this.checkBoxReajuste = new JCheckBox();
		this.labelCodigoFornecedor = new JLabel();
		this.labelCnpjFornecedor = new JLabel();
		this.labelNomeFornecedor = new JLabel();
		this.labelQuantidadeProduto = new JLabel();
		this.labelCodigoProduto = new JLabel();
		this.labelNomeProduto = new JLabel();
		this.labelValorUnitario = new JLabel();
		this.labelValorTotal = new JLabel();
		this.labelTotalCompra = new JLabel();
		this.labelDesconto = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuCadastro = new JMenu();
		this.subCadFornecedor = new JMenuItem();
		this.subCadFuncionario = new JMenuItem();
		this.subCadProduto = new JMenuItem();
		this.menuListagem = new JMenu();
		this.panelFornecedor = new JPanel();
		this.panelProduto = new JPanel();
		this.panelTotalCompra = new JPanel();
		this.scrollPaneInformacoes = new JScrollPane();
		scrollPaneInformacoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacoes = new JTable();
		tableInformacoes.setColumnSelectionAllowed(true);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL CLIENTE ----------- //
		this.panelFornecedor.setBorder(BorderFactory
				.createTitledBorder("Fornecedor"));

		// Código
		this.labelCodigoFornecedor.setText("Código");
		this.labelCodigoFornecedor.setFont(new Font("Tahoma", 1, 11));
		this.textCodigoFornecedor.setFont(new Font("Tahoma", 1, 11));
		this.textCodigoFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.textCodigoFornecedorActionPerformed(evt);
			}
		});

		// Cnpj
		this.labelCnpjFornecedor.setText("CNPJ");
		this.labelCnpjFornecedor.setFont(new Font("Tahoma", 1, 11));
		this.textCnpjFornecedor.setFont(new Font("Tahoma", 1, 11));

		// NOME
		this.labelNomeFornecedor.setText("Nome");
		this.labelNomeFornecedor.setFont(new Font("Tahoma", 1, 11));
		this.textNomeFornecedor.setFont(new Font("Tahoma", 1, 11));

		GroupLayout gl_panelFornecedor = new GroupLayout(panelFornecedor);
		gl_panelFornecedor
				.setHorizontalGroup(gl_panelFornecedor
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelFornecedor
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelFornecedor
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addComponent(
																textCodigoFornecedor)
														.addComponent(
																labelCodigoFornecedor,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelFornecedor
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																labelCnpjFornecedor)
														.addComponent(
																textCnpjFornecedor,
																GroupLayout.PREFERRED_SIZE,
																173,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelFornecedor
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																labelNomeFornecedor)
														.addComponent(
																textNomeFornecedor,
																GroupLayout.PREFERRED_SIZE,
																306,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		gl_panelFornecedor
				.setVerticalGroup(gl_panelFornecedor
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelFornecedor
										.createSequentialGroup()
										.addGap(5)
										.addGroup(
												gl_panelFornecedor
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelCodigoFornecedor)
														.addComponent(
																labelCnpjFornecedor)
														.addComponent(
																labelNomeFornecedor,
																GroupLayout.DEFAULT_SIZE,
																14,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelFornecedor
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textCodigoFornecedor,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textCnpjFornecedor,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textNomeFornecedor,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE))
										.addGap(19)));
		panelFornecedor.setLayout(gl_panelFornecedor);

		// ---------- PANEL Produto ----------- //
		this.panelProduto
				.setBorder(BorderFactory.createTitledBorder("Produto"));

		// Quantidade
		this.labelQuantidadeProduto.setText("Qnte");
		this.labelQuantidadeProduto.setFont(new Font("Tahoma", 1, 11));
		this.textQuantidadeProduto.setFont(new Font("Tahoma", 1, 11));
		this.textQuantidadeProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.textQuantidadeProdutoActionPerformed(evt);
			}
		});

		// Código
		this.labelCodigoProduto.setText("Código");
		this.labelCodigoProduto.setFont(new Font("Tahoma", 1, 11));
		this.textCodigoProduto.setFont(new Font("Tahoma", 1, 11));
		this.textCodigoProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.textCodigoProdutoActionPerformed(evt);
			}
		});

		// NOME
		this.labelNomeProduto.setText("Discriminação");
		this.labelNomeProduto.setFont(new Font("Tahoma", 1, 11));
		this.comboNomeProduto.setFont(new Font("Tahoma", 1, 11));
		this.comboNomeProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.comboNomeProdutoActionPerformed(evt);
			}
		});

		// Valor unitário
		this.labelValorUnitario.setText("Valor Unit.");
		this.labelValorUnitario.setFont(new Font("Tahoma", 1, 11));
		this.textValorUnitario.setFont(new Font("Tahoma", 1, 11));
		this.textValorUnitario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.textValorUnitarioActionPerformed(evt);
			}
		});

		// Valor Total
		this.labelValorTotal.setText("Valor Total");
		this.labelValorTotal.setFont(new Font("Tahoma", 1, 11));
		this.textValorTotal.setFont(new Font("Tahoma", 1, 11));

		// Desconto
		this.labelDesconto.setText("Desconto");
		this.labelDesconto.setFont(new Font("Tahoma", 1, 11));
		this.textDesconto.setFont(new Font("Tahoma", 1, 11));
		this.textDesconto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.textDescontoActionPerformed(evt);
			}
		});

		// Reajuste
		this.checkBoxReajuste.setText("Reajustar preço de venda");
		this.checkBoxReajuste.setFont(new Font("Tahoma", 1, 11));
		this.checkBoxReajuste.setSelected(true);

		// BOTÕES

		// Botão Adicionar
		this.buttonAdicionar.setBackground(new Color(255, 255, 255));
		this.buttonAdicionar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/add.gif")));
		this.buttonAdicionar.setText("Adicionar");
		this.buttonAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.buttonAdicionarActionPerformed(evt);
			}
		});

		// Botão Atualizar
		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.buttonAtualizarActionPerformed(evt);
			}
		});

		// Botão Excluir
		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.buttonExcluirActionPerformed(evt);
			}
		});

		// Botão Faturar Compra
		this.buttonFaturarCompra.setBackground(new Color(255, 255, 255));
		this.buttonFaturarCompra.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/dinheiro.png")));
		this.buttonFaturarCompra.setText("Faturar Compra");
		this.buttonFaturarCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.buttonFaturarCompraActionPerformed(evt);
			}
		});

		// Botão Fechar
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/fechar.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.buttonFecharActionPerformed(evt);
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
																scrollPaneInformacoes,
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
										.addComponent(scrollPaneInformacoes,
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
		this.scrollPaneInformacoes.setViewportView(this.tableInformacoes);

		// ---------- PANEL TOTAL PEDIDO ----------- //
		this.panelTotalCompra.setBorder(BorderFactory
				.createTitledBorder("Total Compra R$"));
		this.labelTotalCompra.setFont(new Font("Tahoma", 1, 25));
		this.panelTotalCompra.add(labelTotalCompra);

		GroupLayout gl_panelTotalCompra = new GroupLayout(panelTotalCompra);
		gl_panelTotalCompra.setHorizontalGroup(gl_panelTotalCompra
				.createParallelGroup(Alignment.TRAILING).addGroup(
						gl_panelTotalCompra
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(labelTotalCompra,
										GroupLayout.DEFAULT_SIZE, 135,
										Short.MAX_VALUE).addContainerGap()));
		gl_panelTotalCompra.setVerticalGroup(gl_panelTotalCompra
				.createParallelGroup(Alignment.TRAILING).addGroup(
						gl_panelTotalCompra
								.createSequentialGroup()
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(labelTotalCompra)
								.addContainerGap()));
		panelTotalCompra.setLayout(gl_panelTotalCompra);

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
																Alignment.TRAILING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(12)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelDesconto)
																						.addComponent(
																								textDesconto,
																								GroupLayout.PREFERRED_SIZE,
																								96,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								checkBoxReajuste,
																								GroupLayout.PREFERRED_SIZE,
																								204,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				197,
																				Short.MAX_VALUE)
																		.addComponent(
																				panelTotalCompra,
																				GroupLayout.PREFERRED_SIZE,
																				169,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																panelProduto,
																GroupLayout.PREFERRED_SIZE,
																582,
																Short.MAX_VALUE)
														.addComponent(
																panelFornecedor,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				buttonFaturarCompra,
																				GroupLayout.PREFERRED_SIZE,
																				195,
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
										.addComponent(panelFornecedor,
												GroupLayout.PREFERRED_SIZE, 84,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(panelProduto,
												GroupLayout.PREFERRED_SIZE,
												251, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				panelTotalCompra,
																				GroupLayout.PREFERRED_SIZE,
																				75,
																				GroupLayout.PREFERRED_SIZE)
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
																								buttonFaturarCompra,
																								GroupLayout.PREFERRED_SIZE,
																								45,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(23))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				labelDesconto,
																				GroupLayout.PREFERRED_SIZE,
																				14,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textDesconto,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18)
																		.addComponent(
																				checkBoxReajuste,
																				GroupLayout.PREFERRED_SIZE,
																				22,
																				GroupLayout.PREFERRED_SIZE)
																		.addContainerGap()))));
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

		// Sub-menu Fornecedor
		this.subCadFornecedor.setText("Fornecedor");
		subCadFornecedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				SHORTCUT_MASK));
		this.subCadFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.subCadFornecedorActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadFornecedor);

		// Sub-menu Funcionário
		this.subCadFuncionario.setText("Funcionário");
		subCadFuncionario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				SHORTCUT_MASK));
		this.subCadFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.subCadFuncionarioActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadFuncionario);

		// Sub-menu Produto
		this.subCadProduto.setText("Produto");
		subCadProduto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				SHORTCUT_MASK));
		this.subCadProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.subCadProdutoActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadProduto);

		this.menuBar.add(this.menuCadastro);

		// Menu Listagem.
		this.menuListagem.setMnemonic('L');
		this.menuListagem.setText("Listagem");
		this.menuListagem.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JanCompra.this.menuListagemActionPerformed(evt);
			}
		});
		this.menuListagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCompra.this.menuListagemActionPerformed(evt);
			}
		});

		this.menuBar.add(this.menuListagem);

		setJMenuBar(this.menuBar);

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
		Double valor_unitario = Double.parseDouble(tableInformacoes.getValueAt(
				row, 2).toString());
		Double valor_total = Double.parseDouble(tableInformacoes.getValueAt(
				row, 3).toString());

		this.textQuantidadeProduto.setText(quantidade.toString());
		this.comboNomeProduto.setSelectedItem(nome.toString());
		this.textValorUnitario.setText(valor_unitario.toString());
		this.textValorTotal.setText(valor_total.toString());

		this.textQuantidadeProduto.grabFocus();
		this.textValorUnitario.setEditable(true);
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
			Item_compra item_compra = new Item_compra();

			// Pega o código do compra
			CompraDao daoCompra = new CompraDao();
			String auto_incremento = Long.toString(daoCompra
					.retornaAutoIncrement());
			item_compra.getCompra().setCodigo_compra(
					Integer.parseInt(auto_incremento));

			// Pega o código do produto
			item_compra.getProduto().setCodigo_produto(
					Integer.parseInt(this.textCodigoProduto.getText()));

			ProdutoDao daoProduto = new ProdutoDao();

			// Verifica se o código do produto não está presente em nenhuma
			// compra
			if (daoProduto.countProdutoCompra(item_compra.getProduto()
					.getCodigo_produto()) == 0) {
				this.checkBoxReajuste.setSelected(true);
				this.checkBoxReajuste.setEnabled(false);
			}

			item_compra.getProduto().setNome_produto(
					this.comboNomeProduto.getSelectedItem().toString());

			// Quantidade
			item_compra.setQuatidade_item(Integer
					.parseInt(this.textQuantidadeProduto.getText()));

			// Valor unitário
			item_compra.setValor_unitario_compra(Double
					.parseDouble(this.textValorUnitario.getText()));

			if (this.contem(item_compra)) {
				JOptionPane.showMessageDialog(null,
						"Produto já adicionado na compra");
			} else {
				// Adiciona na lista
				this.itemCompra.add(item_compra);
				preencherJTable();
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

			Item_compra item_compra = new Item_compra();

			// Pega o código do compra
			CompraDao daoCompra = new CompraDao();
			String auto_incremento = Long.toString(daoCompra
					.retornaAutoIncrement());
			item_compra.getCompra().setCodigo_compra(
					Integer.parseInt(auto_incremento));

			// Pega o código do produto
			item_compra.getProduto().setCodigo_produto(
					Integer.parseInt(this.textCodigoProduto.getText()));

			item_compra.getProduto().setNome_produto(
					this.comboNomeProduto.getSelectedItem().toString());

			// Quantidade
			item_compra.setQuatidade_item(Integer
					.parseInt(this.textQuantidadeProduto.getText()));

			// Valor unitário
			item_compra.setValor_unitario_compra(Double
					.parseDouble(this.textValorUnitario.getText()));

			// Altera na lista
			this.itemCompra.set(this.numero, item_compra);
			getRootPane().setDefaultButton(buttonAdicionar);

			preencherJTable();
			funcoesProduto();
			this.acao = "adicionar";
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
				this.itemCompra.remove(this.numero);

				this.total_compra = 0.0;

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

	// buttonFaturarCompra.
	private void buttonFaturarCompraActionPerformed(ActionEvent evt) {
		if (this.testeCamposFaturar()) {
			JOptionPane.showMessageDialog(null,
					"Todos os campos são obrigatórios!");
		} else {

			// Compra
			CompraDao daoCompra = new CompraDao();
			Compra compra = new Compra();

			compra.setData_compra(Calendar.getInstance());
			compra.setValor_total_compra(this.total_compra);

			if (!this.textDesconto.getText().equals("")) {
				compra.setDesconto_compra(Double.parseDouble(this.textDesconto
						.getText()));
			} else {
				compra.setDesconto_compra(0.0);
			}

			// Funcionário.
			FuncionarioDao daoFuncionario = new FuncionarioDao();
			compra.getFuncionario_compra().setCodigo_funcionario(
					daoFuncionario.consultarCodigoFuncionario());

			// Fornecedor
			compra.getFornecedor_compra().setCodigo_fornecedor(
					Integer.parseInt(this.textCodigoFornecedor.getText()));

			// grave nessa conexão!!! -- Compra
			daoCompra.inserir(compra);

			// grave nessa conexão!!! -- Item compra
			Item_compraDao daoItemCompra = new Item_compraDao();
			daoItemCompra.inserir(this.itemCompra,
					this.checkBoxReajuste.isSelected());

			JOptionPane.showMessageDialog(null, "Compra faturado com sucesso!");

			dispose();
			JanListagemCompra cadC = new JanListagemCompra("principal");
			cadC.setVisible(true);

		}
	}

	// SubCadFornecedor.
	private void subCadFornecedorActionPerformed(ActionEvent evt) {
		// EnderecoDao
		EnderecoDao dao = new EnderecoDao();

		if (dao.count() > 0) {
			dispose();
			JanCadFornecedor cadC = new JanCadFornecedor("compra");
			cadC.setVisible(true);
		} else {
			dispose();
			JanCadEndereco cadE = new JanCadEndereco("Fornecedor");
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
				JanCadFuncionario cadF = new JanCadFuncionario("compra");
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
				JanCadProduto cadP = new JanCadProduto("compra");
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
		JanListagemCompra cadC = new JanListagemCompra("compra");
		cadC.setVisible(true);
	}

	// Listagem.
	private void menuListagemActionPerformed(ActionEvent evt) {
		menuListagemActionPerformed(evt);
	}

	// textCodigoFornecedor
	private void textCodigoFornecedorActionPerformed(ActionEvent evt) {
		if (!this.textCodigoFornecedor.getText().equals("")) {
			FornecedorDao dao = new FornecedorDao();
			Fornecedor fornecedor = new Fornecedor();

			fornecedor.setCodigo_fornecedor(Integer
					.parseInt(this.textCodigoFornecedor.getText()));

			List<String> listaFornecedor = dao.listaFornecedor(fornecedor
					.getCodigo_fornecedor());

			if (listaFornecedor.size() != 0) {

				this.textCnpjFornecedor.setText(listaFornecedor.get(0));
				this.textNomeFornecedor.setText(listaFornecedor.get(1));

				this.textQuantidadeProduto.setEditable(true);
				this.textQuantidadeProduto.grabFocus();
				this.textCodigoFornecedor.setEditable(false);
			} else {
				JOptionPane
						.showMessageDialog(null, "Fornecedor não cadastrado");
				esvasiarCamposFornecedor();
			}
		} else {
			esvasiarCamposFornecedor();
		}
	}

	// textQuantidadeProduto
	private void textQuantidadeProdutoActionPerformed(ActionEvent evt) {
		if (!this.textQuantidadeProduto.getText().equals("")) {
			if (acao.equals("adicionar")) {
				VisivelProduto();
				this.textCodigoProduto.grabFocus();
			} else {
				this.textValorUnitario.grabFocus();
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

					this.textValorUnitario.grabFocus();
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

			this.textValorUnitario.grabFocus();

		} else {
			esvasiarCamposProduto();
		}
	}

	// textValorUnitario
	private void textValorUnitarioActionPerformed(ActionEvent evt) {
		if (!this.textValorUnitario.getText().equals("")) {
			Double resultado = Double.parseDouble(this.textQuantidadeProduto
					.getText())
					* Double.parseDouble(this.textValorUnitario.getText());

			this.textValorTotal.setText(resultado.toString());

			if (acao.equals("adicionar")) {
				this.buttonAdicionar.setEnabled(true);
				this.buttonAdicionar.grabFocus();
				getRootPane().setDefaultButton(buttonAdicionar);
			} else {
				this.buttonAtualizar.grabFocus();
				getRootPane().setDefaultButton(buttonAtualizar);
			}
		}
	}

	private void textDescontoActionPerformed(ActionEvent evt) {
		if (!this.textDesconto.getText().equals("")) {

			Double valor = Double.parseDouble(this.textDesconto.getText());

			if (valor <= this.total_compra) {
				this.total_compra -= valor;
			} else {
				JOptionPane.showMessageDialog(null,
						"Desconto maior que o valor total da compra");
			}

			DecimalFormat df = new DecimalFormat("0.00");

			this.labelTotalCompra.setText(df.format(this.total_compra));

			getRootPane().setDefaultButton(buttonFaturarCompra);
			this.buttonFaturarCompra.grabFocus();
		}
	}
}
