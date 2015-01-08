package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;

import jdbc.dao.CargoDao;
import jdbc.dao.CategoriaDao;
import jdbc.dao.ClienteDao;
import jdbc.dao.ComercioDao;
import jdbc.dao.EnderecoDao;
import jdbc.dao.FornecedorDao;
import jdbc.dao.FuncionarioDao;
import jdbc.dao.ProdutoDao;
import jdbc.dao.UsuarioDao;
import bean.Comercio;
import bean.Usuario;

public class JanPrincipal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel labelCnpj;
	private JLabel labelInscricaoEstadual;
	private JLabel labelComercio;
	private JLabel labelTelefone;
	private JLabel labelEmail;
	private JLabel labelUsuario;
	private JLabel labelFuncionario;
	private JLabel labelCargo;
	private JLabel labelCnpj1;
	private JLabel labelInscricaoEstadual1;
	private JLabel labelComercio1;
	private JLabel labelTelefone1;
	private JLabel labelEmail1;
	private JLabel labelUsuario1;
	private JLabel labelFuncionario1;
	private JLabel labelCargo1;
	private JButton buttonFechar;
	private JMenuBar menuBar;
	private JMenu menuEstoque;
	private JMenuItem subControleEstoque;
	private JMenuItem subPedidoCompra;
	private JMenuItem subListagemCompra;
	private JMenu menuVendas;
	private JMenuItem subJanVenda;
	private JMenuItem subJanListagemVendas;
	private JMenu menuCadastro;
	private JMenuItem subCadCargo;
	private JMenuItem subCadCategoria;
	private JMenuItem subCadCliente;
	private JMenuItem subCadComercio;
	private JMenuItem subCadFornecedor;
	private JMenuItem subCadFuncionario;
	private JMenuItem subCadProduto;
	private JMenuItem subCadUsuario;
	private JMenu menuSair;
	private JPanel panelComercio;
	private JPanel panelFuncionario;

	public JanPrincipal() {
		super("Comério");

		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		UsuarioDao daoUsuario = new UsuarioDao();
		if (daoUsuario.consultarNivelLogado() != 3) {
			this.subCadFuncionario.setEnabled(false);
			this.subCadUsuario.setEnabled(false);
			this.subCadComercio.setEnabled(false);
		}

	}

	private void Componentes() {
		this.buttonFechar = new JButton();
		this.labelCnpj = new JLabel();
		this.labelInscricaoEstadual = new JLabel();
		this.labelComercio = new JLabel();
		this.labelTelefone = new JLabel();
		this.labelEmail = new JLabel();
		this.labelUsuario = new JLabel();
		this.labelFuncionario = new JLabel();
		this.labelCargo = new JLabel();
		this.labelCnpj1 = new JLabel();
		this.labelInscricaoEstadual1 = new JLabel();
		this.labelComercio1 = new JLabel();
		this.labelTelefone1 = new JLabel();
		this.labelEmail1 = new JLabel();
		this.labelUsuario1 = new JLabel();
		this.labelFuncionario1 = new JLabel();
		this.labelCargo1 = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuEstoque = new JMenu();
		this.subControleEstoque = new JMenuItem();
		this.subPedidoCompra = new JMenuItem();
		this.subListagemCompra = new JMenuItem();
		this.menuVendas = new JMenu();
		this.subJanVenda = new JMenuItem();
		this.subJanListagemVendas = new JMenuItem();
		this.menuCadastro = new JMenu();
		this.subCadCargo = new JMenuItem();
		this.subCadCategoria = new JMenuItem();
		this.subCadCliente = new JMenuItem();
		this.subCadComercio = new JMenuItem();
		this.subCadFornecedor = new JMenuItem();
		this.subCadFuncionario = new JMenuItem();
		this.subCadProduto = new JMenuItem();
		this.subCadUsuario = new JMenuItem();
		this.menuSair = new JMenu();
		this.panelComercio = new JPanel();
		this.panelFuncionario = new JPanel();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// Botão Fechar
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/fechar.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.fecharActionPerformed(evt);
			}
		});

		// ---------- PANEL COMÉRCIO ----------- //
		this.panelComercio.setBorder(BorderFactory
				.createTitledBorder("Comércio"));

		ComercioDao daoComercio = new ComercioDao();

		for (Comercio comercio : daoComercio.listar()) {
			this.labelCnpj1.setText(comercio.getCnpj_comercio());
			this.labelInscricaoEstadual1.setText(comercio
					.getInscricao_estadual());
			this.labelComercio1.setText(comercio.getNome_comercio());
			this.labelTelefone1.setText(comercio.getTelefone_comercio());
			this.labelEmail1.setText(comercio.getEmail_comercio());
		}

		// CNPJ.
		this.labelCnpj.setText("CNPJ:");

		// IE.
		this.labelInscricaoEstadual.setText("IE:");

		// Comércio.
		this.labelComercio.setText("Comércio:");

		// Telefone.
		this.labelTelefone.setText("Telefone:");

		// Email.
		this.labelEmail.setText("E-mail:");

		// ---------- PANEL USUÁRIO ----------- //
		this.panelFuncionario.setBorder(BorderFactory
				.createTitledBorder("Funcionário"));

		// Usuário.
		UsuarioDao daoUsuario = new UsuarioDao();
		this.labelUsuario.setText("Usuário:");
		this.labelUsuario1.setText(daoUsuario.consultarNomeLogado());

		// Funcionário.
		FuncionarioDao daoFuncionario = new FuncionarioDao();
		this.labelFuncionario.setText("Funcionário:");
		this.labelFuncionario1.setText(daoFuncionario
				.consultarNomeFuncionario());

		// Cargo.
		CargoDao daoCargo = new CargoDao();
		this.labelCargo.setText("Cargo:");
		this.labelCargo1.setText(daoCargo.consultarNomeCargo());

		GroupLayout gl_panelFuncionario = new GroupLayout(panelFuncionario);
		gl_panelFuncionario
				.setHorizontalGroup(gl_panelFuncionario
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelFuncionario
										.createSequentialGroup()
										.addGap(31)
										.addGroup(
												gl_panelFuncionario
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																labelUsuario)
														.addComponent(
																labelFuncionario)
														.addComponent(
																labelCargo,
																GroupLayout.PREFERRED_SIZE,
																71,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelFuncionario
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																labelCargo1)
														.addComponent(
																labelFuncionario1)
														.addComponent(
																labelUsuario1))
										.addContainerGap(176, Short.MAX_VALUE)));
		gl_panelFuncionario
				.setVerticalGroup(gl_panelFuncionario
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelFuncionario
										.createSequentialGroup()
										.addGroup(
												gl_panelFuncionario
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelFuncionario
																		.createSequentialGroup()
																		.addGap(45)
																		.addGroup(
																				gl_panelFuncionario
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								labelFuncionario)
																						.addComponent(
																								labelFuncionario1)))
														.addGroup(
																gl_panelFuncionario
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				gl_panelFuncionario
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								labelUsuario)
																						.addComponent(
																								labelUsuario1))))
										.addGroup(
												gl_panelFuncionario
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelFuncionario
																		.createSequentialGroup()
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				21,
																				Short.MAX_VALUE)
																		.addComponent(
																				labelCargo1)
																		.addContainerGap())
														.addGroup(
																gl_panelFuncionario
																		.createSequentialGroup()
																		.addGap(18)
																		.addComponent(
																				labelCargo)
																		.addContainerGap()))));
		panelFuncionario.setLayout(gl_panelFuncionario);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								groupLayout.createSequentialGroup()
										.addContainerGap(403, Short.MAX_VALUE)
										.addComponent(buttonFechar).addGap(41))
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(81)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																panelComercio,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																399,
																Short.MAX_VALUE)
														.addComponent(
																panelFuncionario,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																399,
																Short.MAX_VALUE))
										.addGap(85)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.TRAILING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(23)
						.addComponent(panelComercio,
								GroupLayout.PREFERRED_SIZE, 193,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelFuncionario,
								GroupLayout.PREFERRED_SIZE, 130,
								GroupLayout.PREFERRED_SIZE).addGap(50)
						.addComponent(buttonFechar).addGap(37)));

		GroupLayout gl_panelComercio = new GroupLayout(panelComercio);
		gl_panelComercio
				.setHorizontalGroup(gl_panelComercio
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelComercio
										.createSequentialGroup()
										.addGap(27)
										.addGroup(
												gl_panelComercio
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_panelComercio
																		.createParallelGroup(
																				Alignment.TRAILING)
																		.addComponent(
																				labelComercio)
																		.addGroup(
																				gl_panelComercio
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelEmail)
																						.addComponent(
																								labelTelefone,
																								Alignment.TRAILING,
																								GroupLayout.PREFERRED_SIZE,
																								67,
																								GroupLayout.PREFERRED_SIZE)))
														.addComponent(
																labelInscricaoEstadual)
														.addComponent(labelCnpj))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_panelComercio
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																labelCnpj1)
														.addComponent(
																labelInscricaoEstadual1)
														.addComponent(
																labelComercio1)
														.addComponent(
																labelTelefone1)
														.addComponent(
																labelEmail1))
										.addContainerGap(152, Short.MAX_VALUE)));
		gl_panelComercio
				.setVerticalGroup(gl_panelComercio
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_panelComercio
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panelComercio
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(labelCnpj)
														.addComponent(
																labelCnpj1))
										.addGap(18)
										.addGroup(
												gl_panelComercio
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelInscricaoEstadual)
														.addComponent(
																labelInscricaoEstadual1))
										.addGap(18)
										.addGroup(
												gl_panelComercio
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelComercio)
														.addComponent(
																labelComercio1))
										.addGap(18)
										.addGroup(
												gl_panelComercio
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelTelefone)
														.addComponent(
																labelTelefone1))
										.addGap(18)
										.addGroup(
												gl_panelComercio
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelEmail)
														.addComponent(
																labelEmail1))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		panelComercio.setLayout(gl_panelComercio);
		getContentPane().setLayout(groupLayout);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 531) / 2, (screenSize.height - 545) / 2,
				567, 545);

		// ---------- BARRA DE FERRAMENTAS ----------- //
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();

		// Menu Cadastro.
		this.menuCadastro.setMnemonic('C');
		this.menuCadastro.setText("Cadastro");

		// Sub-menu Cargo.
		this.subCadCargo.setText("Cargo");
		subCadCargo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				SHORTCUT_MASK));
		this.subCadCargo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subCadCargoActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadCargo);

		// Sub-menu Categoria.
		this.subCadCategoria.setText("Categoria");
		subCadCategoria.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				SHORTCUT_MASK));
		this.subCadCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subCadCategoriaActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadCategoria);

		// Sub-menu Cliente.
		this.subCadCliente.setText("Cliente");
		subCadCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				SHORTCUT_MASK));
		this.subCadCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subCadClienteActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadCliente);

		// Sub-menu Comércio.
		this.subCadComercio.setText("Comércio");
		subCadComercio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				SHORTCUT_MASK));
		this.subCadComercio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subCadComercioActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadComercio);

		// Sub-menu Fornecedor.
		this.subCadFornecedor.setText("Fornecedor");
		subCadFornecedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
				SHORTCUT_MASK));
		this.subCadFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subCadFornecedorActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadFornecedor);

		// Sub-menu Funcionario
		this.subCadFuncionario.setText("Funcionário");
		subCadFuncionario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				SHORTCUT_MASK));
		this.subCadFuncionario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subCadFuncionarioActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadFuncionario);

		// Sub-menu Produto.
		this.subCadProduto.setText("Produto");
		subCadProduto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				SHORTCUT_MASK));
		this.subCadProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subCadProdutoActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadProduto);

		// Sub-menu Usuário
		this.subCadUsuario.setText("Usuário");
		subCadUsuario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				SHORTCUT_MASK));
		this.subCadUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subCadUsuarioActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCadUsuario);

		this.menuBar.add(this.menuCadastro);

		// Menu Estoque.
		this.menuEstoque.setMnemonic('E');
		this.menuEstoque.setText("Estoque");

		// Sub-menu subControleEstoque.
		this.subControleEstoque.setText("Controle de estoque");
		subControleEstoque.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				SHORTCUT_MASK));
		this.subControleEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subCadProdutoActionPerformed(evt);
			}
		});
		this.menuEstoque.add(this.subControleEstoque);

		// Sub-menu subPedidoCompra.
		this.subPedidoCompra.setText("Compra");
		subPedidoCompra.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				SHORTCUT_MASK));
		this.subPedidoCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subJanCompraActionPerformed(evt);
			}
		});
		this.menuEstoque.add(this.subPedidoCompra);

		// Sub-menu subPedidoCompra.
		this.subListagemCompra.setText("Listagem compras");
		subListagemCompra.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				SHORTCUT_MASK));
		this.subListagemCompra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subJanListagemComprasActionPerformed(evt);
			}
		});
		this.menuEstoque.add(this.subListagemCompra);

		this.menuBar.add(this.menuEstoque);

		// Menu Pedido
		this.menuVendas.setMnemonic('V');
		this.menuVendas.setText("Vendas");

		// Sub-menu Venda.
		this.subJanVenda.setText("Venda");
		subJanVenda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				SHORTCUT_MASK));
		this.subJanVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subJanVendaActionPerformed(evt);
			}
		});
		this.menuVendas.add(this.subJanVenda);

		// Sub-menu Listagem de Venda.
		this.subJanListagemVendas.setText("Listagem de vendas");
		subJanListagemVendas.setAccelerator(KeyStroke.getKeyStroke(
				KeyEvent.VK_L, SHORTCUT_MASK));
		this.subJanListagemVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.subJanListagemVendasActionPerformed(evt);
			}
		});
		this.menuVendas.add(this.subJanListagemVendas);

		this.menuBar.add(this.menuVendas);

		this.menuVendas = new JMenu();
		this.subJanVenda = new JMenuItem();
		this.subJanListagemVendas = new JMenuItem();

		// Menu Sair.
		this.menuSair.setMnemonic('S');
		this.menuSair.setText("Sair");
		this.menuSair.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JanPrincipal.this.sairActionPerformed(evt);
			}
		});
		this.menuSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPrincipal.this.sairActionPerformed(evt);
			}
		});
		this.menuBar.add(this.menuSair);

		// Insere MenuBar.
		setJMenuBar(this.menuBar);

	}

	// SubJanCompra.
	private void subJanCompraActionPerformed(ActionEvent evt) {
		// FuncionarioDao
		FuncionarioDao daoFuncionario = new FuncionarioDao();

		// FornecedorDao
		FornecedorDao daoFornecedor = new FornecedorDao();

		// ProdutoDao
		ProdutoDao daoProduto = new ProdutoDao();

		if (daoFuncionario.countFuncionarioAtivoUsuarioAtivo3() > 0) {
			if (daoFornecedor.count() > 0) {
				if (daoProduto.countAtivoInativo(true) > 0) {
					dispose();
					JanCompra janC = new JanCompra();
					janC.setVisible(true);
				} else {
					dispose();
					JanCadProduto cadPr = new JanCadProduto("principal");
					cadPr.setVisible(true);
				}
			} else {
				dispose();
				JanCadFornecedor cadF = new JanCadFornecedor("principal");
				cadF.setVisible(true);
			}
		} else {
			dispose();
			JanCadFuncionario cadF = new JanCadFuncionario("principal");
			cadF.setVisible(true);
		}
	}

	// JanListagemCompra.
	private void subJanListagemComprasActionPerformed(ActionEvent evt) {
		dispose();
		JanListagemCompra janC = new JanListagemCompra("principal");
		janC.setVisible(true);
	}

	// SubJanVenda.
	private void subJanVendaActionPerformed(ActionEvent evt) {
		// FuncionarioDao
		FuncionarioDao daoFuncionario = new FuncionarioDao();

		// ClienteDao
		ClienteDao daoCliente = new ClienteDao();

		// ProdutoDao
		ProdutoDao daoProduto = new ProdutoDao();

		if (daoFuncionario.countFuncionarioAtivoUsuarioAtivo3() > 0) {
			if (daoCliente.countAtivoInativo(true) > 0) {
				if (daoProduto.countAtivoInativo(true) > 0) {
					dispose();
					JanVenda janP = new JanVenda();
					janP.setVisible(true);
				} else {
					dispose();
					JanCadProduto cadPr = new JanCadProduto("principal");
					cadPr.setVisible(true);
				}
			} else {
				dispose();
				JanCadCliente cadC = new JanCadCliente("principal");
				cadC.setVisible(true);
			}
		} else {
			dispose();
			JanCadFuncionario cadF = new JanCadFuncionario("principal");
			cadF.setVisible(true);
		}
	}

	// JanListagemVenda.
	private void subJanListagemVendasActionPerformed(ActionEvent evt) {
		dispose();
		JanListagemVenda janL = new JanListagemVenda("principal");
		janL.setVisible(true);
	}

	// SubCadCargo.
	private void subCadCargoActionPerformed(ActionEvent evt) {
		dispose();
		JanCadCargo cadC = new JanCadCargo("principal");
		cadC.setVisible(true);
	}

	// SubCadCategoria.
	private void subCadCategoriaActionPerformed(ActionEvent evt) {
		dispose();
		JanCadCategoria cadC = new JanCadCategoria("principal");
		cadC.setVisible(true);
	}

	// SubCadCliente.
	private void subCadClienteActionPerformed(ActionEvent evt) {

		// EnderecoDao
		EnderecoDao dao = new EnderecoDao();

		if (dao.count() > 0) {
			dispose();
			JanCadCliente cadC = new JanCadCliente("principal");
			cadC.setVisible(true);
		} else {
			dispose();
			JanCadEndereco cadE = new JanCadEndereco("Cliente");
			cadE.setVisible(true);
		}
	}

	// SubCadComercio.
	private void subCadComercioActionPerformed(ActionEvent evt) {

		// EnderecoDao
		EnderecoDao daoEndereco = new EnderecoDao();

		if (daoEndereco.count() > 0) {
			dispose();
			JanCadComercio cadC = new JanCadComercio("principal");
			cadC.setVisible(true);

		} else {
			dispose();
			JanCadEndereco cadE = new JanCadEndereco("Comercio");
			cadE.setVisible(true);
		}
	}

	// SubCadFornecedor.
	private void subCadFornecedorActionPerformed(ActionEvent evt) {

		// EnderecoDao
		EnderecoDao dao = new EnderecoDao();

		if (dao.count() > 0) {
			dispose();
			JanCadFornecedor cadF = new JanCadFornecedor("principal");
			cadF.setVisible(true);
		} else {
			dispose();
			JanCadEndereco cadE = new JanCadEndereco("Fornecedor");
			cadE.setVisible(true);
		}
	}

	// SubCadFuncionário.
	private void subCadFuncionarioActionPerformed(ActionEvent evt) {

		// CargoDao
		CargoDao daoCargo = new CargoDao();

		// ComercioDao
		ComercioDao daoComercio = new ComercioDao();

		if (daoCargo.count() > 0) {
			if (daoComercio.count() > 0) {
				dispose();
				JanCadFuncionario cadF = new JanCadFuncionario("principal");
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
				JanCadProduto cadP = new JanCadProduto("principal");
				cadP.setVisible(true);
			} else {
				dispose();
				JanCadCategoria cadC = new JanCadCategoria("produto");
				cadC.setVisible(true);
			}
		} else {
			dispose();
			JanCadFornecedor cadF = new JanCadFornecedor("produto");
			cadF.setVisible(true);
		}
	}

	// SubCadUsuário.
	private void subCadUsuarioActionPerformed(ActionEvent evt) {
		dispose();
		JanCadUsuario cadU = new JanCadUsuario("principal");
		cadU.setVisible(true);
	}

	// Sair.
	private void sairActionPerformed(MouseEvent evt) {
		dispose();

		Usuario usuario = new Usuario();
		UsuarioDao dao = new UsuarioDao();
		usuario.setNome_usuario(dao.consultarNomeLogado());
		usuario.setLogado(false);

		dao.alterarLogado(usuario);

		JanLogin login = new JanLogin();
		login.setVisible(true);

	}

	// Sair.
	private void sairActionPerformed(ActionEvent evt) {
		sairActionPerformed(evt);

	}

	// Fechar.
	private void fecharActionPerformed(ActionEvent evt) {

		dispose();

		Usuario usuario = new Usuario();
		UsuarioDao dao = new UsuarioDao();
		usuario.setNome_usuario(dao.consultarNomeLogado());
		usuario.setLogado(false);

		dao.alterarLogado(usuario);

	}
}
