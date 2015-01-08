package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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
import javax.swing.text.MaskFormatter;

import regraTextField.JtextFieldEmail;
import regraTextField.JtextFieldSomenteLetras;
import regraTextField.JtextFieldSomenteNumeros;
import jdbc.dao.CargoDao;
import jdbc.dao.ComercioDao;
import jdbc.dao.EnderecoDao;
import jdbc.dao.FuncionarioDao;
import jdbc.dao.UsuarioDao;
import bean.Funcionario;

public class JanCadFuncionario extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String inicioCpf = null;
	MaskFormatter maskCpf;
	String inicioTelefone = null;
	MaskFormatter maskTelefone;
	private String nome;
	private JTextField textPesquisar;
	private JTextField textCodigo;
	private JTextField textCpf;
	private JTextField textNome;
	private JTextField textTelefone;
	private JTextField textEmail;
	private JComboBox<Object> comboSexo;
	private JCheckBox checkBoxAtivo;
	private JComboBox<Object> comboCargo;
	private JTextField textComercio;
	private JComboBox<Object> comboUsuario;
	private JButton buttonPesquisar;
	private JButton buttonNovo;
	private JButton buttonAlterar;
	private JButton buttonExcluir;
	private JButton buttonAtualizar;
	private JButton buttonSalvar;
	private JButton buttonFechar;
	private JLabel labelCodigo;
	private JLabel labelCpf;
	private JLabel labelNome;
	private JLabel labelTelefone;
	private JLabel labelEmail;
	private JLabel labelSexo;
	private JLabel labelCargo;
	private JLabel labelComercio;
	private JLabel labelUsuario;
	private JMenuBar menuBar;
	private JMenu menuArquivo;
	private JMenuItem subNovo;
	private JMenuItem subFechar;
	private JMenu menuCadastro;
	private JMenuItem subCargo;
	private JMenuItem subComercio;
	private JMenuItem subUsuario;
	private JMenu menuInativo;
	private JPanel panelPesquisarFuncionario;
	private JPanel panelDadosCadastrais;
	private JPanel panelBotoes;
	private JScrollPane scrollPaneInformacaoes;
	private JTable tableInformacaoes;
	private JRadioButton radioCodigo;
	private JRadioButton radioCpf;
	private JRadioButton radioNome;
	private JRadioButton radioTelefone;
	private JRadioButton radioEmail;
	private JRadioButton radioSexo;
	private JRadioButton radioCargo;
	private JRadioButton radioUsuario;

	public JanCadFuncionario(String nome) {
		super("Cadastro - Funcionário");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		this.nome = nome;

		FuncionarioDao dao = new FuncionarioDao();
		List<Funcionario> listaFuncionario = dao.listar();

		preencherJTable(listaFuncionario);
		Invisivel();
		desativarBotoes();

		// Sexo
		this.comboSexo.removeAllItems();
		this.comboSexo.addItem("");
		this.comboSexo.addItem("M");
		this.comboSexo.addItem("F");

		// Cargo
		this.comboCargo.removeAllItems();
		this.comboCargo.addItem("");

		CargoDao daoCargo = new CargoDao();
		ArrayList<String> cargo = daoCargo.comboCargo();
		Iterator<String> y = cargo.iterator();

		while (y.hasNext()) {
			this.comboCargo.addItem(String.valueOf(y.next()));
		}
	}

	public void Invisivel() {
		this.textCpf.setEditable(false);
		this.textNome.setEditable(false);
		this.textTelefone.setEditable(false);
		this.textEmail.setEditable(false);
		this.comboSexo.setEnabled(false);
		this.checkBoxAtivo.setEnabled(false);
		this.comboCargo.setEnabled(false);
		this.textComercio.setEditable(false);
		this.comboUsuario.setEnabled(false);
	}

	public void Visivel() {
		this.textNome.setEditable(true);
		this.textTelefone.setEditable(true);
		this.textEmail.setEditable(true);
		this.comboSexo.setEnabled(true);
		this.comboCargo.setEnabled(true);

		FuncionarioDao dao = new FuncionarioDao();
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf_funcionario(this.textCpf.getText().toString());
		funcionario.getUsuario_funcionario().setNome_usuario(
				this.comboUsuario.getSelectedItem().toString());

		if ((dao.countAtivoInativo(true) >= 1 && dao
				.cpfFuncionario(funcionario) == false)
				|| (dao.countFuncionarioAtivoUsuarioAtivo3() > 1 && dao
						.cpfFuncionario(funcionario) == true)
				|| (dao.countFuncionarioAtivoUsuarioAtivo3() == 1 && dao
						.usuarioFuncionario(funcionario) == false)
				|| (dao.cpfFuncionario(funcionario) == true && dao
						.ativo(funcionario) == false)) {
			this.checkBoxAtivo.setEnabled(true);
			this.comboUsuario.setEnabled(true);
		}
	}

	public void desativarBotoes() {
		this.buttonSalvar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonAtualizar.setEnabled(false);
		this.buttonAlterar.setEnabled(false);
	}

	public void esvasiarCampos() {
		this.textCpf.setText("");
		this.textNome.setText("");
		this.textTelefone.setText("");
		this.textEmail.setText("");
		this.comboSexo.setSelectedItem("");
		this.comboCargo.setSelectedItem("");
		this.textComercio.setText("");
		this.comboUsuario.setSelectedItem("");
	}

	public void funcoes() {
		desativarBotoes();
		esvasiarCampos();
		Invisivel();
		this.textCodigo.setText("");
	}

	// teste campos vaisios
	public Boolean testeCampos() {
		if ((this.textCodigo.getText().equals(""))
				|| (this.textCpf.getText().equals("   .   .   -  "))
				|| (this.textNome.getText().equals(""))
				|| (this.textTelefone.getText().equals("  .   -   "))
				|| (this.comboSexo.getSelectedItem().equals(""))
				|| (this.comboCargo.getSelectedItem().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTable(List<Funcionario> listaFuncionario) {
		this.tableInformacaoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(1)
				.setPreferredWidth(70);
		this.tableInformacaoes.getColumnModel().getColumn(2)
				.setPreferredWidth(70);
		this.tableInformacaoes.getColumnModel().getColumn(3)
				.setPreferredWidth(60);
		this.tableInformacaoes.getColumnModel().getColumn(4)
				.setPreferredWidth(60);
		this.tableInformacaoes.getColumnModel().getColumn(5)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(6)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(7)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(8)
				.setPreferredWidth(5);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacaoes
				.getModel();
		tabela.setNumRows(0);

		int codigo = 0;

		UsuarioDao daoUsuario = new UsuarioDao();

		for (Funcionario funcionario : listaFuncionario) {

			codigo = funcionario.getUsuario_funcionario().getCodigo_usuario();

			funcionario.getUsuario_funcionario().setNome_usuario(
					daoUsuario.nomeUsuario(codigo));

			tabela.addRow(new Object[] { funcionario.getCodigo_funcionario(),
					funcionario.getCpf_funcionario(),
					funcionario.getNome_funcionario(),
					funcionario.getTelefone_funcionario(),
					funcionario.getEmail_funcionario(),
					funcionario.getSexo_funcionario(),
					funcionario.getCargo_funcionario().getNome_cargo(),
					funcionario.getUsuario_funcionario().getNome_usuario(),
					funcionario.getAtivo() });
		}

		this.tableInformacaoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanCadFuncionario.this.tableInformacaoesTableChanged(e);
					}
				});

	}

	private void Componentes() {

		try {
			this.maskCpf = new MaskFormatter("###.###.###-##");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel inserir a mascara");
		}

		try {
			this.maskTelefone = new MaskFormatter("(##)####-####");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel inserir a mascara");
		}

		this.textPesquisar = new JTextField();
		this.textCpf = new JFormattedTextField(this.maskCpf);
		this.textCodigo = new JtextFieldSomenteNumeros(7);
		this.textNome = new JtextFieldSomenteLetras(100);
		this.textTelefone = new JFormattedTextField(this.maskTelefone);
		this.textEmail = new JtextFieldEmail(50);
		this.comboSexo = new JComboBox<>();
		this.checkBoxAtivo = new JCheckBox();
		this.comboCargo = new JComboBox<>();
		this.textComercio = new JTextField();
		this.comboUsuario = new JComboBox<>();
		this.buttonPesquisar = new JButton();
		this.buttonNovo = new JButton();
		this.buttonAlterar = new JButton();
		this.buttonExcluir = new JButton();
		this.buttonAtualizar = new JButton();
		this.buttonSalvar = new JButton();
		this.buttonFechar = new JButton();
		this.labelCodigo = new JLabel();
		this.labelCpf = new JLabel();
		this.labelNome = new JLabel();
		this.labelTelefone = new JLabel();
		this.labelEmail = new JLabel();
		this.labelSexo = new JLabel();
		this.labelCargo = new JLabel();
		this.labelComercio = new JLabel();
		this.labelUsuario = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuArquivo = new JMenu();
		this.subNovo = new JMenuItem();
		this.subFechar = new JMenuItem();
		this.menuCadastro = new JMenu();
		this.subCargo = new JMenuItem();
		this.subComercio = new JMenuItem();
		this.subUsuario = new JMenuItem();
		this.menuInativo = new JMenu();
		this.panelPesquisarFuncionario = new JPanel();
		this.panelDadosCadastrais = new JPanel();
		this.panelBotoes = new JPanel();
		this.scrollPaneInformacaoes = new JScrollPane();
		scrollPaneInformacaoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacaoes = new JTable();
		tableInformacaoes.setColumnSelectionAllowed(true);
		this.radioCodigo = new JRadioButton();
		this.radioCpf = new JRadioButton();
		this.radioNome = new JRadioButton();
		this.radioTelefone = new JRadioButton();
		this.radioEmail = new JRadioButton();
		this.radioSexo = new JRadioButton();
		this.radioCargo = new JRadioButton();
		this.radioUsuario = new JRadioButton();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL PESQUISAR CARGOS ----------- //
		this.panelPesquisarFuncionario.setBorder(BorderFactory
				.createTitledBorder("Pesquisar Funcionários"));

		this.textPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.textPesquisarActionPerformed(evt);
			}
		});
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Radio Codigo.
		this.radioCodigo.setText("Código");
		this.radioCodigo.setFont(new Font("Tahoma", 1, 11));
		this.radioCodigo.setActionCommand("codigo_funcionario");
		this.radioCodigo.setSelected(true);

		this.radioCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.actionPerformed(evt);
			}
		});

		// Radio CPF.
		this.radioCpf.setText("CPF");
		this.radioCpf.setFont(new Font("Tahoma", 1, 11));
		this.radioCpf.setActionCommand("cpf_funcionario");

		this.radioCpf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.actionPerformed(evt);
			}
		});

		// Radio Nome.
		this.radioNome.setText("Nome");
		this.radioNome.setFont(new Font("Tahoma", 1, 11));
		this.radioNome.setActionCommand("nome_funcionario");

		this.radioNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.actionPerformed(evt);
			}
		});

		// Radio Telefone.
		this.radioTelefone.setText("Telefone");
		this.radioTelefone.setFont(new Font("Tahoma", 1, 11));
		this.radioTelefone.setActionCommand("telefone_funcionario");

		this.radioTelefone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.actionPerformed(evt);
			}
		});

		// Radio Email.
		this.radioEmail.setText("E-mail");
		this.radioEmail.setFont(new Font("Tahoma", 1, 11));
		this.radioEmail.setActionCommand("email_funcionario");

		this.radioNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.actionPerformed(evt);
			}
		});

		// Radio Sexo.
		this.radioSexo.setText("Sexo");
		this.radioSexo.setFont(new Font("Tahoma", 1, 11));
		this.radioSexo.setActionCommand("sexo_funcionario");

		this.radioSexo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.actionPerformed(evt);
			}
		});

		// Radio Cargo.
		this.radioCargo.setText("Cargo");
		this.radioCargo.setFont(new Font("Tahoma", 1, 11));
		this.radioCargo.setActionCommand("cargo_funcionario");

		this.radioCargo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.actionPerformed(evt);
			}
		});

		// Radio Usuário.
		this.radioUsuario.setText("Usuário");
		this.radioUsuario.setFont(new Font("Tahoma", 1, 11));
		this.radioUsuario.setActionCommand("usuario_funcionario");

		this.radioUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.actionPerformed(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioCodigo);
		group.add(this.radioCpf);
		group.add(this.radioNome);
		group.add(this.radioTelefone);
		group.add(this.radioEmail);
		group.add(this.radioSexo);
		group.add(this.radioCargo);
		group.add(this.radioUsuario);

		GroupLayout layoutPesquisarFuncionario = new GroupLayout(
				this.panelPesquisarFuncionario);
		layoutPesquisarFuncionario
				.setHorizontalGroup(layoutPesquisarFuncionario
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutPesquisarFuncionario
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutPesquisarFuncionario
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutPesquisarFuncionario
																		.createSequentialGroup()
																		.addComponent(
																				radioCodigo)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioCpf)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioNome)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioTelefone)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioEmail)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioSexo)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioCargo)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioUsuario))
														.addGroup(
																layoutPesquisarFuncionario
																		.createSequentialGroup()
																		.addComponent(
																				textPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				444,
																				Short.MAX_VALUE)
																		.addGap(18)
																		.addComponent(
																				buttonPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				211,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		layoutPesquisarFuncionario
				.setVerticalGroup(layoutPesquisarFuncionario
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								layoutPesquisarFuncionario
										.createSequentialGroup()
										.addGroup(
												layoutPesquisarFuncionario
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textPesquisar,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																buttonPesquisar,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addGap(1)
										.addGroup(
												layoutPesquisarFuncionario
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(radioNome)
														.addComponent(
																radioTelefone,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																radioEmail,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(radioSexo)
														.addComponent(
																radioCargo)
														.addComponent(
																radioCodigo)
														.addComponent(
																radioCpf,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																radioUsuario,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE))));
		this.panelPesquisarFuncionario.setLayout(layoutPesquisarFuncionario);

		// ---------- PANEL DADOS CADASTRAIS ----------- //
		this.panelDadosCadastrais.setBorder(BorderFactory
				.createTitledBorder("Dados Cadastrais"));

		// Codigo.
		this.labelCodigo.setText("Código:");
		this.textCodigo.setFont(new Font("Tahoma", 1, 11));
		this.textCodigo.setEnabled(false);

		// CPF.
		this.labelCpf.setText("CPF:");
		this.textCpf.setFont(new Font("Tahoma", 1, 11));

		// Nome.
		this.labelNome.setText("Nome:");
		this.textNome.setFont(new Font("Tahoma", 1, 11));

		// Telefone.
		this.labelTelefone.setText("Telefone:");
		this.textTelefone.setFont(new Font("Tahoma", 1, 11));

		// E-mail.
		this.labelEmail.setText("E-mail:");
		this.textEmail.setFont(new Font("Tahoma", 1, 11));

		// Sexo.
		this.labelSexo.setText("Sexo:");
		this.comboSexo.setFont(new Font("Tahoma", 1, 11));

		// Ativo.
		this.checkBoxAtivo.setText("Ativo");
		this.checkBoxAtivo.setFont(new Font("Tahoma", 1, 11));

		// Cargo
		this.labelCargo.setText("Cargo:");
		this.comboCargo.setFont(new Font("Tahoma", 1, 11));

		// Comércio.
		this.labelComercio.setText("Comércio:");
		this.textComercio.setFont(new Font("Tahoma", 1, 11));

		// Usuário
		this.labelUsuario.setText("Usuário:");
		this.comboUsuario.setFont(new Font("Tahoma", 1, 11));

		GroupLayout layoutDadosCadastrais = new GroupLayout(
				this.panelDadosCadastrais);
		layoutDadosCadastrais
				.setHorizontalGroup(layoutDadosCadastrais
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutDadosCadastrais
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.TRAILING)
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelTelefone,
																				GroupLayout.PREFERRED_SIZE,
																				67,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textTelefone,
																				GroupLayout.DEFAULT_SIZE,
																				176,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelEmail)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textEmail,
																				GroupLayout.PREFERRED_SIZE,
																				217,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelSexo)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				comboSexo,
																				GroupLayout.PREFERRED_SIZE,
																				67,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																Alignment.LEADING,
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelCargo)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				comboCargo,
																				GroupLayout.PREFERRED_SIZE,
																				219,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelComercio)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textComercio,
																				GroupLayout.DEFAULT_SIZE,
																				301,
																				Short.MAX_VALUE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelCodigo,
																				GroupLayout.PREFERRED_SIZE,
																				54,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textCodigo,
																				GroupLayout.PREFERRED_SIZE,
																				79,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelCpf)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textCpf,
																				GroupLayout.PREFERRED_SIZE,
																				168,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelNome,
																				GroupLayout.PREFERRED_SIZE,
																				45,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textNome,
																				GroupLayout.DEFAULT_SIZE,
																				236,
																				Short.MAX_VALUE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelUsuario)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				comboUsuario,
																				GroupLayout.PREFERRED_SIZE,
																				226,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				318,
																				Short.MAX_VALUE)
																		.addComponent(
																				checkBoxAtivo,
																				GroupLayout.PREFERRED_SIZE,
																				57,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		layoutDadosCadastrais
				.setVerticalGroup(layoutDadosCadastrais
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutDadosCadastrais
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelCodigo)
														.addComponent(
																textCodigo,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textNome,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelCpf)
														.addComponent(
																textCpf,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelNome))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(labelSexo)
														.addComponent(
																comboSexo,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelEmail)
														.addComponent(
																textEmail,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textTelefone,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelTelefone))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelComercio)
														.addComponent(
																comboCargo,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelCargo)
														.addComponent(
																textComercio,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																checkBoxAtivo,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelUsuario)
														.addComponent(
																comboUsuario,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE))
										.addGap(4)));
		this.panelDadosCadastrais.setLayout(layoutDadosCadastrais);

		// ---------- PANEL DOS BOTÕES ----------- //
		this.panelBotoes.setBorder(BorderFactory.createCompoundBorder());

		// Botões.
		this.buttonNovo.setBackground(new Color(255, 255, 255));
		this.buttonNovo.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/novo_registro.gif")));
		this.buttonNovo.setText("Novo");
		this.buttonNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.buttonNovoActionPerformed(evt);
			}
		});
		this.buttonAlterar.setBackground(new Color(255, 255, 255));
		this.buttonAlterar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/alterar_registro.gif")));
		this.buttonAlterar.setText("Alterar");
		this.buttonAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.buttonAlterarActionPerformed(evt);
			}
		});

		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.buttonExcluirActionPerformed(evt);
			}
		});

		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.buttonAtualizarActionPerformed(evt);
			}
		});
		this.buttonSalvar.setBackground(new Color(255, 255, 255));
		this.buttonSalvar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/gravar_registro.gif")));
		this.buttonSalvar.setText("Salvar");
		this.buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.buttonSalvarActionPerformed(evt);
			}
		});
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/sair.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.buttonFecharActionPerformed(evt);
			}
		});

		GroupLayout layoutBotoes = new GroupLayout(this.panelBotoes);
		layoutBotoes.setHorizontalGroup(layoutBotoes.createParallelGroup(
				Alignment.LEADING).addGroup(
				layoutBotoes
						.createSequentialGroup()
						.addComponent(buttonNovo, GroupLayout.DEFAULT_SIZE, 85,
								Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonAlterar, GroupLayout.DEFAULT_SIZE,
								93, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonExcluir, GroupLayout.DEFAULT_SIZE,
								93, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonAtualizar,
								GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonSalvar, GroupLayout.DEFAULT_SIZE,
								91, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(buttonFechar, GroupLayout.DEFAULT_SIZE,
								99, Short.MAX_VALUE)));
		layoutBotoes
				.setVerticalGroup(layoutBotoes
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutBotoes
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutBotoes
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																buttonNovo)
														.addComponent(
																buttonFechar)
														.addComponent(
																buttonAlterar)
														.addComponent(
																buttonSalvar)
														.addComponent(
																buttonAtualizar)
														.addComponent(
																buttonExcluir,
																GroupLayout.PREFERRED_SIZE,
																33,
																GroupLayout.PREFERRED_SIZE))
										.addGap(0, 0, Short.MAX_VALUE)));
		this.panelBotoes.setLayout(layoutBotoes);

		// ---------- TABLE INFORMAÇÕES ----------- //
		this.tableInformacaoes.setModel(new DefaultTableModel(new Object[][] {
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null },
				{ null, null, null, null, null } }, new String[] { "Código",
				"CPF", "Nome", "Telefone", "E-mail", "Sexo", "Cargo",
				"Usuário", "Ativo" }));

		this.tableInformacaoes.setFocusable(false);
		this.scrollPaneInformacaoes.setViewportView(this.tableInformacaoes);

		// ---------- BARRA DE FERRAMENTAS ----------- //
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();

		// Menu Arquivo
		this.menuArquivo.setMnemonic('A');
		this.menuArquivo.setText("Arquivo");

		this.subNovo.setText("Novo");
		subNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				SHORTCUT_MASK));
		this.subNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.subNovoActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subNovo);

		this.subFechar.setText("Fechar");
		subFechar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
				SHORTCUT_MASK));
		this.subFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.subFecharActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subFechar);

		this.menuBar.add(this.menuArquivo);

		// Menu Cadastro.
		this.menuCadastro.setMnemonic('C');
		this.menuCadastro.setText("Cadastro");

		this.subCargo.setText("Cargo");
		subCargo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				SHORTCUT_MASK));
		this.subCargo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.subCargoActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subCargo);

		// Comércio
		this.subComercio.setText("Comércio");
		subComercio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				SHORTCUT_MASK));
		this.subComercio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.subComercioActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subComercio);

		// Usuário
		this.subUsuario.setText("Usuário");
		subUsuario.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,
				SHORTCUT_MASK));
		this.subUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.subUsuarioActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subUsuario);

		this.menuBar.add(this.menuCadastro);

		// Inativo
		this.menuInativo.setMnemonic('I');
		this.menuInativo.setText("Inativo");
		this.menuInativo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JanCadFuncionario.this.inativoActionPerformed(evt);
			}
		});
		this.menuInativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFuncionario.this.inativoActionPerformed(evt);
			}
		});
		this.menuBar.add(this.menuInativo);

		setJMenuBar(this.menuBar);

		// ---------- LAYOULT ----------- //
		GroupLayout layout = new GroupLayout(getContentPane());
		layout.setHorizontalGroup(layout
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												Alignment.TRAILING)
												.addComponent(
														panelDadosCadastrais,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														scrollPaneInformacaoes,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														707, Short.MAX_VALUE)
												.addComponent(
														panelPesquisarFuncionario,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														707, Short.MAX_VALUE)
												.addComponent(
														panelBotoes,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														707, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelPesquisarFuncionario,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(3)
								.addComponent(panelDadosCadastrais,
										GroupLayout.PREFERRED_SIZE, 175,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelBotoes,
										GroupLayout.DEFAULT_SIZE, 53,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPaneInformacaoes,
										GroupLayout.PREFERRED_SIZE, 122,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		getContentPane().setLayout(layout);

		pack();
	}

	// tableInformacaoesTableChanged
	private void tableInformacaoesTableChanged(TableModelEvent e) {
		funcoes();

		int column = e.getColumn();
		int row = e.getFirstRow();

		if (column < 0 || row < 0)
			return;

		Integer codigo_funcionario = Integer.parseInt(tableInformacaoes
				.getValueAt(row, 0).toString());
		String cpf = tableInformacaoes.getValueAt(row, 1).toString();
		String nome = tableInformacaoes.getValueAt(row, 2).toString();
		String telefone = tableInformacaoes.getValueAt(row, 3).toString();
		String email = tableInformacaoes.getValueAt(row, 4).toString();
		String sexo = tableInformacaoes.getValueAt(row, 5).toString();
		String nomeCargo = tableInformacaoes.getValueAt(row, 6).toString();
		String nomeUsuario = tableInformacaoes.getValueAt(row, 7).toString();
		Boolean ativo = Boolean.parseBoolean(tableInformacaoes.getValueAt(row,
				8).toString());

		// Usuário
		this.comboUsuario.removeAllItems();
		this.comboUsuario.addItem("");

		UsuarioDao daoUsuario = new UsuarioDao();
		ArrayList<String> usuario = daoUsuario.comboInsereUsuario();
		Iterator<String> j = usuario.iterator();

		while (j.hasNext()) {
			this.comboUsuario.addItem(String.valueOf(j.next()));
		}

		if (!nomeUsuario.equals("")) {
			this.comboUsuario.addItem(nomeUsuario.toString());
			this.comboUsuario.setSelectedItem(nomeUsuario.toString());
		}

		ComercioDao daoComercio = new ComercioDao();

		this.textCodigo.setText(codigo_funcionario.toString());
		this.textCpf.setText(cpf.toString());
		this.textNome.setText(nome.toString());
		this.textTelefone.setText(telefone.toString());
		this.textEmail.setText(email.toString());
		this.comboSexo.setSelectedItem(sexo.toString());
		this.comboCargo.setSelectedItem(nomeCargo.toString());
		this.textComercio.setText(daoComercio.retornaComercio());
		this.comboUsuario.setSelectedItem(nomeUsuario.toString());
		this.checkBoxAtivo.setSelected(ativo);

		this.buttonAlterar.setEnabled(true);

		FuncionarioDao dao = new FuncionarioDao();
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf_funcionario(this.textCpf.getText().toString());
		funcionario.getUsuario_funcionario().setNome_usuario(
				this.comboUsuario.getSelectedItem().toString());

		if ((dao.countAtivoInativo(true) >= 1 && dao
				.cpfFuncionario(funcionario) == false)
				|| (dao.countFuncionarioAtivoUsuarioAtivo3() > 1 && dao
						.cpfFuncionario(funcionario) == true)
				|| (dao.countFuncionarioAtivoUsuarioAtivo3() == 1 && dao
						.usuarioFuncionario(funcionario) == false)
				|| (dao.cpfFuncionario(funcionario) == true && dao
						.ativo(funcionario) == false)) {
			if (dao.countFuncionarioCompra(codigo_funcionario) == 0
					&& dao.countFuncionarioVenda(codigo_funcionario) == 0) {
				this.buttonExcluir.setEnabled(true);
			}
		}

	}

	String acao = "codigo_funcionario";

	private void actionPerformed(ActionEvent evt) {
		acao = evt.getActionCommand();
	}

	// TextPesquisar.
	private void textPesquisarActionPerformed(ActionEvent evt) {
		buttonPesquisarActionPerformed(evt);
	}

	// ButtonPesquisar.
	private void buttonPesquisarActionPerformed(ActionEvent evt) {
		funcoes();
		if (!(this.textPesquisar.getText().equals(""))) {
			FuncionarioDao dao = new FuncionarioDao();

			List<Funcionario> listaFuncionario = dao.pesquisar(
					this.textPesquisar.getText(), acao);

			if (listaFuncionario.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Nenhum registro encontrado");
				listaFuncionario = dao.listar();
				preencherJTable(listaFuncionario);
			} else {
				preencherJTable(listaFuncionario);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Preencha o campo da pesquisa");
		}
	}

	// ButtonNovo.
	private void buttonNovoActionPerformed(ActionEvent evt) {

		// CargoDao
		CargoDao daoCargo = new CargoDao();

		// ComercioDao
		ComercioDao daoComercio = new ComercioDao();

		if (daoCargo.count() > 0) {
			if (daoComercio.count() > 0) {
				esvasiarCampos();
				this.checkBoxAtivo.setSelected(true);

				// Usuário
				this.comboUsuario.removeAllItems();
				this.comboUsuario.addItem("");

				UsuarioDao daoUsuario = new UsuarioDao();
				ArrayList<String> usuario = daoUsuario.comboInsereUsuario();
				Iterator<String> j = usuario.iterator();

				while (j.hasNext()) {
					this.comboUsuario.addItem(String.valueOf(j.next()));
				}

				if (usuario.size() == 1) {
					this.comboUsuario.setSelectedItem(usuario.get(0));
				}

				desativarBotoes();
				this.buttonSalvar.setEnabled(true);
				this.textCpf.setEditable(true);

				Visivel();
				FuncionarioDao daoFuncionario = new FuncionarioDao();
				this.textCodigo.setText(Long.toString(daoFuncionario
						.retornaAutoIncrement()));

				this.textComercio.setText(daoComercio.retornaComercio());
				this.textCpf.grabFocus();
			} else {
				JOptionPane.showMessageDialog(null,
						"Nenhum comércio cadastrado!");
				dispose();
				JanCadComercio cadC = new JanCadComercio("funcionario");
				cadC.setVisible(true);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum cargo cadastrado!");
			dispose();
			JanCadCargo cadC = new JanCadCargo("funcionario");
			cadC.setVisible(true);
		}
	}

	// ButtonAlterar.
	private void buttonAlterarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Nenhum funcionário selecionado!");
		} else {
			desativarBotoes();
			Visivel();
			// ComercioDao
			ComercioDao daoComercio = new ComercioDao();

			this.textComercio.setText(daoComercio.retornaComercio());
			this.buttonAtualizar.setEnabled(true);
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Nenhum funcionário selecionado!");
		} else {
			Funcionario funcionario = new Funcionario();
			funcionario.setCodigo_funcionario(Integer.parseInt((this.textCodigo
					.getText())));
			String mensagem = "Deseja excluir o funcionário de código "
					+ funcionario.getCodigo_funcionario() + "?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				FuncionarioDao dao = new FuncionarioDao();
				dao.remover(funcionario);

				List<Funcionario> listaFuncionario = dao.listar();

				preencherJTable(listaFuncionario);

				esvasiarCampos();
				this.textCodigo.setText("");
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

	// ButtonAtualisar.
	private void buttonAtualizarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Todos os campos são obrigatórios!");
		} else {
			if (!this.textEmail.getText().equals("")
					&& !(this.textEmail.getText().contains("@") && this.textEmail
							.getText().contains("."))) {
				JOptionPane.showMessageDialog(null, "E-mail inválido!");
				this.textEmail.setText("");
				this.textEmail.grabFocus();
			} else {
				Funcionario funcionario = new Funcionario();
				funcionario.setCodigo_funcionario(Integer
						.parseInt((this.textCodigo.getText())));
				funcionario.setNome_funcionario(this.textNome.getText());
				funcionario
						.setTelefone_funcionario(this.textTelefone.getText());
				funcionario.setEmail_funcionario(this.textEmail.getText());
				funcionario.setSexo_funcionario(this.comboSexo
						.getSelectedItem().toString());
				funcionario.setAtivo(this.checkBoxAtivo.isSelected());

				// Cargo
				funcionario.getCargo_funcionario().setNome_cargo(
						this.comboCargo.getSelectedItem().toString());

				// Usuário
				funcionario.getUsuario_funcionario().setNome_usuario(
						this.comboUsuario.getSelectedItem().toString());

				// Atualiza!!!
				FuncionarioDao dao = new FuncionarioDao();
				dao.alterar(funcionario);

				JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
				List<Funcionario> listaFuncionario = dao.listar();
				preencherJTable(listaFuncionario);

				esvasiarCampos();
				this.textCodigo.setText("");

				Invisivel();

				desativarBotoes();
			}
		}
	}

	// ButtonSalvar.
	private void buttonSalvarActionPerformed(ActionEvent evt) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCpf_funcionario(this.textCpf.getText());

		FuncionarioDao dao = new FuncionarioDao();
		if (dao.cpfFuncionario(funcionario)) {
			JOptionPane.showMessageDialog(null,
					"CPF do Funcionário já está cadastrado!");
			this.textCpf.setText("");
			this.textCpf.grabFocus();
		} else {
			if (this.testeCampos()) {
				JOptionPane.showMessageDialog(null,
						"Todos os campos são obrigatórios!");
			} else {
				if (!this.textEmail.getText().equals("")
						&& !(this.textEmail.getText().contains("@") && this.textEmail
								.getText().contains("."))) {
					JOptionPane.showMessageDialog(null, "E-mail inválido!");
					this.textEmail.setText("");
					this.textEmail.grabFocus();
				} else {
					funcionario.setCodigo_funcionario(Integer
							.parseInt((this.textCodigo.getText())));
					funcionario.setCpf_funcionario(this.textCpf.getText());
					funcionario.setNome_funcionario(this.textNome.getText());
					funcionario.setTelefone_funcionario(this.textTelefone
							.getText());
					funcionario.setEmail_funcionario(this.textEmail.getText());
					funcionario.setSexo_funcionario(this.comboSexo
							.getSelectedItem().toString());
					funcionario.setAtivo(this.checkBoxAtivo.isSelected());

					// Cargo
					funcionario.getCargo_funcionario().setNome_cargo(
							this.comboCargo.getSelectedItem().toString());

					// Comércio
					funcionario.getComercio_funcionario().setNome_comercio(
							this.textComercio.getText().toString());

					// Usuário
					funcionario.getUsuario_funcionario().setNome_usuario(
							this.comboUsuario.getSelectedItem().toString());

					// grave nessa conexão!!!
					dao.inserir(funcionario);

					JOptionPane.showMessageDialog(
							null,
							"O cadastro do funcionário:\n"
									+ this.textCodigo.getText() + " - "
									+ funcionario.getNome_funcionario()
									+ "\nFoi realizado com sucesso!");
					List<Funcionario> listaFuncionario = dao.listar();
					preencherJTable(listaFuncionario);

					esvasiarCampos();
					this.textCodigo.setText("");

					Invisivel();

					desativarBotoes();
				}
			}
		}
	}

	// ButtonFechar.
	private void buttonFecharActionPerformed(ActionEvent evt) {
		FuncionarioDao dao = new FuncionarioDao();
		if (dao.countFuncionarioAtivoUsuarioAtivo3() == 0) {
			JOptionPane
					.showMessageDialog(null,
							"Cadastre um funcionário ativo ligado a um usuário ativo e de nível 3");
		} else {
			switch (this.nome) {
			case "venda":
				dispose();
				JanVenda janPe = new JanVenda();
				janPe.setVisible(true);
				break;

			case "principal":
				dispose();
				JanPrincipal janPr = new JanPrincipal();
				janPr.setVisible(true);
				break;

			case "Comércio":
				dispose();
				JanCadComercio cadC = new JanCadComercio("principal");
				cadC.setVisible(true);
				break;

			case "login":
				dispose();
				JanLogin janL = new JanLogin();
				janL.setVisible(true);
				break;

			case "usuario":
				dispose();
				JanCadUsuario cadU = new JanCadUsuario("principal");
				cadU.setVisible(true);
				break;

			case "cargo":
				dispose();
				JanCadCargo cadCa = new JanCadCargo("principal");
				cadCa.setVisible(true);
				break;
			default:
				break;
			}
		}
	}

	// JMenuItemNovo.
	private void subNovoActionPerformed(ActionEvent evt) {
		buttonNovoActionPerformed(evt);
	}

	// JMenuItemFechar.
	private void subFecharActionPerformed(ActionEvent evt) {
		buttonFecharActionPerformed(evt);
	}

	// JMenuItemCargo
	private void subCargoActionPerformed(ActionEvent evt) {
		FuncionarioDao dao = new FuncionarioDao();
		if (dao.countFuncionarioAtivoUsuarioAtivo3() == 0) {
			JOptionPane
					.showMessageDialog(null,
							"Cadastre um funcionário ativo ligado a um usuário ativo e de nível 3");
		} else {

			dispose();
			JanCadCargo cadC = new JanCadCargo("funcionario");
			cadC.setVisible(true);
		}

	}

	// JMenuItemComercio
	private void subComercioActionPerformed(ActionEvent evt) {

		FuncionarioDao dao = new FuncionarioDao();
		if (dao.countFuncionarioAtivoUsuarioAtivo3() == 0) {
			JOptionPane
					.showMessageDialog(null,
							"Cadastre um funcionário ativo ligado a um usuário ativo e de nível 3");
		} else {

			// EnderecoDao
			EnderecoDao daoEndereco = new EnderecoDao();

			if (daoEndereco.count() > 0) {
				dispose();
				JanCadComercio cadC = new JanCadComercio("funcionario");
				cadC.setVisible(true);

			} else {
				dispose();
				JanCadEndereco cadE = new JanCadEndereco("Comercio");
				cadE.setVisible(true);
			}
		}
	}

	// JMenuItemUsuario
	private void subUsuarioActionPerformed(ActionEvent evt) {
		FuncionarioDao dao = new FuncionarioDao();
		if (dao.countFuncionarioAtivoUsuarioAtivo3() == 0) {
			JOptionPane
					.showMessageDialog(null,
							"Cadastre um funcionário ativo ligado a um usuário ativo e de nível 3");
		} else {
			dispose();
			JanCadUsuario cadU = new JanCadUsuario("funcionario");
			cadU.setVisible(true);
		}

	}

	// Inativo.
	private void inativoActionPerformed(MouseEvent evt) {
		funcoes();
		FuncionarioDao dao = new FuncionarioDao();
		if (dao.countFuncionarioAtivoUsuarioAtivo3() > 0) {

			if (dao.countAtivoInativo(false) > 0) {
				List<Funcionario> listaFuncionario = dao.consultaInativo();

				preencherJTable(listaFuncionario);

			} else {
				JOptionPane.showMessageDialog(null,
						"Nehum funcionário inativo cadastrado!");
			}
		} else {
			JOptionPane
					.showMessageDialog(null,
							"Nehum funcionário ativo cadastrado ligado a um usuário ativo de nível 3");
		}
	}

	// Inativo.
	private void inativoActionPerformed(ActionEvent evt) {
		inativoActionPerformed(evt);
	}
}
