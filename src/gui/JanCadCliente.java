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
import jdbc.dao.ClienteDao;
import jdbc.dao.EnderecoDao;
import bean.Cliente;

public class JanCadCliente extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String inicioTelefone = null;
	MaskFormatter maskTelefone;
	String inicioCpf = null;
	MaskFormatter maskCpf;
	String nome = null;
	private JTextField textPesquisar;
	private JTextField textCodigo;
	private JTextField textCpf;
	private JTextField textNome;
	private JTextField textTelefone;
	private JTextField textEmail;
	private JComboBox<Object> comboEndereco;
	private JTextField textCep;
	private JTextField textLogradouro;
	private JTextField textNumero;
	private JTextField textBairro;
	private JTextField textUf;
	private JTextField textCidade;
	private JTextField textComplemento;
	private JCheckBox checkBoxAtivo;
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
	private JLabel labelEndereco;
	private JLabel labelCep;
	private JLabel labelUf;
	private JLabel labelNumero;
	private JLabel labelLogradouro;
	private JLabel labelBairro;
	private JLabel labelCidade;
	private JLabel labelComplemento;
	private JMenuBar menuBar;
	private JMenu menuArquivo;
	private JMenuItem subNovo;
	private JMenuItem subFechar;
	private JMenu menuCadastro;
	private JMenuItem subContato;
	private JMenuItem subEndereco;
	private JMenu menuInativo;
	private JPanel panelPesquisarCliente;
	private JPanel panelDadosCadastrais;
	private JPanel panelBotoes;
	private JScrollPane scrollPaneInformacaoes;
	private JTable tableInformacaoes;
	private JRadioButton radioCodigo;
	private JRadioButton radioCpf;
	private JRadioButton radioNome;
	private JRadioButton radioTelefone;
	private JRadioButton radioEmail;
	private JRadioButton radioEndereco;

	public JanCadCliente(String nome) {
		super("Cadastro - Cliente");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		this.nome = nome;

		ClienteDao dao = new ClienteDao();
		List<Cliente> listaCliente = dao.listar();

		preencherJTable(listaCliente);
		Invisivel();
		desativarBotoes();

		this.comboEndereco.removeAllItems();
		this.comboEndereco.addItem("");

		EnderecoDao daoEndereco = new EnderecoDao();
		ArrayList<Integer> endereco = daoEndereco.comboEndereco();
		Iterator<Integer> i = endereco.iterator();

		while (i.hasNext()) {
			this.comboEndereco.addItem(String.valueOf(i.next()));
		}
	}

	public void Invisivel() {
		this.textCpf.setEditable(false);
		this.textNome.setEditable(false);
		this.textTelefone.setEditable(false);
		this.textEmail.setEditable(false);
		this.checkBoxAtivo.setEnabled(false);
		this.comboEndereco.setEnabled(false);
		this.textCep.setEditable(false);
		this.textLogradouro.setEditable(false);
		this.textNumero.setEditable(false);
		this.textBairro.setEditable(false);
		this.textUf.setEditable(false);
		this.textCidade.setEditable(false);
		this.textComplemento.setEditable(false);
	}

	public void Visivel() {
		this.textNome.setEditable(true);
		this.textTelefone.setEditable(true);
		this.textEmail.setEditable(true);
		this.comboEndereco.setEnabled(true);

		ClienteDao dao = new ClienteDao();
		Cliente cliente = new Cliente();
		cliente.setCpf_cliente(this.textCpf.getText().toString());

		if ((dao.countAtivoInativo(true) >= 1 && dao.cpfCliente(cliente) == false)
				|| (dao.countAtivoInativo(true) > 1 && dao.cpfCliente(cliente) == true)
				|| (dao.countAtivoInativo(true) == 1 && dao.cpfCliente(cliente) == false)
				|| (dao.cpfCliente(cliente) == true && dao.ativoCpf(cliente) == false)) {
			this.checkBoxAtivo.setEnabled(true);
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
		this.comboEndereco.setSelectedItem("");
		esvasiarCamposEndereco();
	}

	public void esvasiarCamposEndereco() {
		this.textCep.setText("");
		this.textLogradouro.setText("");
		this.textNumero.setText("");
		this.textBairro.setText("");
		this.textUf.setText("");
		this.textCidade.setText("");
		this.textComplemento.setText("");
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
				|| (this.textTelefone.getText().equals("(  )    -    "))
				|| (this.comboEndereco.getSelectedItem().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTable(List<Cliente> listaCliente) {
		this.tableInformacaoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(1)
				.setPreferredWidth(70);
		this.tableInformacaoes.getColumnModel().getColumn(2)
				.setPreferredWidth(150);
		this.tableInformacaoes.getColumnModel().getColumn(3)
				.setPreferredWidth(70);
		this.tableInformacaoes.getColumnModel().getColumn(4)
				.setPreferredWidth(50);
		this.tableInformacaoes.getColumnModel().getColumn(5)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(6)
				.setPreferredWidth(5);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacaoes
				.getModel();
		tabela.setNumRows(0);

		for (Cliente cliente : listaCliente) {
			tabela.addRow(new Object[] { cliente.getCodigo_cliente(),
					cliente.getCpf_cliente(), cliente.getNome_cliente(),
					cliente.getTelefone_cliente(), cliente.getEmail_cliente(),
					cliente.getEndereco_cliente().getCodigo_endereco(),
					cliente.getAtivo() });
		}

		this.tableInformacaoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanCadCliente.this.tableInformacaoesTableChanged(e);
					}
				});
	}

	private void Componentes() {
		try {
			this.maskTelefone = new MaskFormatter("(##)####-####");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel inserir a mascara");
		}

		try {
			this.maskCpf = new MaskFormatter("###.###.###-##");
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
		this.comboEndereco = new JComboBox<>();
		this.textCep = new JTextField();
		this.textLogradouro = new JTextField();
		this.textNumero = new JTextField();
		this.textBairro = new JTextField();
		this.textUf = new JTextField();
		this.textCidade = new JTextField();
		this.textComplemento = new JTextField();
		this.checkBoxAtivo = new JCheckBox();
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
		this.labelEndereco = new JLabel();
		this.labelCep = new JLabel();
		this.labelLogradouro = new JLabel();
		this.labelNumero = new JLabel();
		this.labelBairro = new JLabel();
		this.labelUf = new JLabel();
		this.labelCidade = new JLabel();
		this.labelComplemento = new JLabel();
		this.menuBar = new JMenuBar();
		this.menuArquivo = new JMenu();
		this.subNovo = new JMenuItem();
		this.subFechar = new JMenuItem();
		this.menuCadastro = new JMenu();
		this.subContato = new JMenuItem();
		this.subEndereco = new JMenuItem();
		this.menuInativo = new JMenu();
		this.panelPesquisarCliente = new JPanel();
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
		this.radioEndereco = new JRadioButton();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL PESQUISAR CLIENTES ----------- //
		this.panelPesquisarCliente.setBorder(BorderFactory
				.createTitledBorder("Pesquisar Clientes"));

		this.textPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.textPesquisarActionPerformed(evt);
			}
		});
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Radio Codigo.
		this.radioCodigo.setText("Código");
		this.radioCodigo.setFont(new Font("Tahoma", 1, 11));
		this.radioCodigo.setActionCommand("codigo_cliente");
		this.radioCodigo.setSelected(true);

		this.radioCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.actionPerformed(evt);
			}
		});

		// Radio Cpf.
		this.radioCpf.setText("CPF");
		this.radioCpf.setFont(new Font("Tahoma", 1, 11));
		this.radioCpf.setActionCommand("cpf_cliente");

		this.radioCpf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.actionPerformed(evt);
			}
		});

		// Radio Nome.
		this.radioNome.setText("Nome");
		this.radioNome.setFont(new Font("Tahoma", 1, 11));
		this.radioNome.setActionCommand("nome_cliente");

		this.radioNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.actionPerformed(evt);
			}
		});

		// Radio Telefone.
		this.radioTelefone.setText("Telefone");
		this.radioTelefone.setFont(new Font("Tahoma", 1, 11));
		this.radioTelefone.setActionCommand("telefone_cliente");

		this.radioTelefone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.actionPerformed(evt);
			}
		});

		// Radio E-mail.
		this.radioEmail.setText("E-mail");
		this.radioEmail.setFont(new Font("Tahoma", 1, 11));
		this.radioEmail.setActionCommand("email_cliente");

		this.radioEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.actionPerformed(evt);
			}
		});

		// Radio Endereco.
		this.radioEndereco.setText("Endereço");
		this.radioEndereco.setFont(new Font("Tahoma", 1, 11));
		this.radioEndereco.setActionCommand("endereco_cliente");

		this.radioEndereco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.actionPerformed(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioCodigo);
		group.add(this.radioCpf);
		group.add(this.radioNome);
		group.add(this.radioTelefone);
		group.add(this.radioEmail);
		group.add(this.radioEndereco);

		GroupLayout layoutPesquisarCliente = new GroupLayout(
				this.panelPesquisarCliente);
		layoutPesquisarCliente
				.setHorizontalGroup(layoutPesquisarCliente
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutPesquisarCliente
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutPesquisarCliente
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutPesquisarCliente
																		.createSequentialGroup()
																		.addComponent(
																				textPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				445,
																				Short.MAX_VALUE)
																		.addGap(18))
														.addGroup(
																layoutPesquisarCliente
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
																				radioEndereco)))
										.addComponent(buttonPesquisar,
												GroupLayout.DEFAULT_SIZE, 212,
												Short.MAX_VALUE)
										.addContainerGap()));
		layoutPesquisarCliente
				.setVerticalGroup(layoutPesquisarCliente
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								layoutPesquisarCliente
										.createSequentialGroup()
										.addGroup(
												layoutPesquisarCliente
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
										.addGap(24))
						.addGroup(
								layoutPesquisarCliente
										.createSequentialGroup()
										.addContainerGap(28, Short.MAX_VALUE)
										.addGroup(
												layoutPesquisarCliente
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																radioCodigo)
														.addComponent(radioCpf)
														.addComponent(
																radioTelefone)
														.addComponent(
																radioEmail)
														.addComponent(
																radioEndereco)
														.addComponent(
																radioNome,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		this.panelPesquisarCliente.setLayout(layoutPesquisarCliente);

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

		// Ativo.
		this.checkBoxAtivo.setText("Ativo");
		this.checkBoxAtivo.setFont(new Font("Tahoma", 1, 11));

		// Endereco.
		this.labelEndereco.setText("Endereço:");
		this.comboEndereco.setFont(new Font("Tahoma", 1, 11));
		this.comboEndereco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.comboEnderecoActionPerformed(evt);
			}
		});

		// CEP.
		this.labelCep.setText("CEP:");
		this.textCep.setFont(new Font("Tahoma", 1, 11));

		// Logradouro.
		this.labelLogradouro.setText("Logradouro:");
		this.textLogradouro.setFont(new Font("Tahoma", 1, 11));

		// Número.
		this.labelNumero.setText("Número:");
		this.textNumero.setFont(new Font("Tahoma", 1, 11));

		// Bairro.
		this.labelBairro.setText("Bairro:");
		this.textBairro.setFont(new Font("Tahoma", 1, 11));

		// UF.
		this.labelUf.setText("UF:");
		this.textUf.setFont(new Font("Tahoma", 1, 11));

		// Cidade.
		this.labelCidade.setText("Cidade:");
		this.textCidade.setFont(new Font("Tahoma", 1, 11));

		// Complemento.
		this.labelComplemento.setText("Complemento:");
		this.textComplemento.setFont(new Font("Tahoma", 1, 11));

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
																Alignment.LEADING,
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelTelefone)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textTelefone,
																				GroupLayout.PREFERRED_SIZE,
																				210,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelEmail)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textEmail,
																				GroupLayout.DEFAULT_SIZE,
																				337,
																				Short.MAX_VALUE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelEndereco)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				comboEndereco,
																				GroupLayout.PREFERRED_SIZE,
																				88,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelCep,
																				GroupLayout.PREFERRED_SIZE,
																				31,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textCep,
																				GroupLayout.PREFERRED_SIZE,
																				113,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelLogradouro,
																				GroupLayout.PREFERRED_SIZE,
																				88,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textLogradouro,
																				GroupLayout.DEFAULT_SIZE,
																				246,
																				Short.MAX_VALUE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								layoutDadosCadastrais
																										.createSequentialGroup()
																										.addComponent(
																												labelCidade,
																												GroupLayout.DEFAULT_SIZE,
																												66,
																												Short.MAX_VALUE)
																										.addGap(6))
																						.addComponent(
																								labelNumero,
																								GroupLayout.PREFERRED_SIZE,
																								60,
																								GroupLayout.PREFERRED_SIZE))
																		.addGap(0)
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addGroup(
																								Alignment.LEADING,
																								layoutDadosCadastrais
																										.createSequentialGroup()
																										.addComponent(
																												textCidade,
																												GroupLayout.PREFERRED_SIZE,
																												166,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												labelComplemento,
																												GroupLayout.PREFERRED_SIZE,
																												102,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textComplemento,
																												GroupLayout.PREFERRED_SIZE,
																												268,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.UNRELATED)
																										.addComponent(
																												checkBoxAtivo,
																												GroupLayout.DEFAULT_SIZE,
																												57,
																												Short.MAX_VALUE))
																						.addGroup(
																								layoutDadosCadastrais
																										.createSequentialGroup()
																										.addComponent(
																												textNumero,
																												GroupLayout.DEFAULT_SIZE,
																												75,
																												Short.MAX_VALUE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												labelBairro,
																												GroupLayout.PREFERRED_SIZE,
																												48,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textBairro,
																												GroupLayout.PREFERRED_SIZE,
																												356,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												labelUf,
																												GroupLayout.PREFERRED_SIZE,
																												23,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textUf,
																												GroupLayout.DEFAULT_SIZE,
																												75,
																												Short.MAX_VALUE))))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addComponent(
																				labelCodigo)
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
																				labelCpf,
																				GroupLayout.PREFERRED_SIZE,
																				31,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textCpf,
																				GroupLayout.PREFERRED_SIZE,
																				197,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelNome)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				textNome,
																				GroupLayout.PREFERRED_SIZE,
																				231,
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
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelCpf)
														.addComponent(
																textCpf,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textNome,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelNome))
										.addGap(19)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textEmail,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelEmail)
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
																textLogradouro,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelEndereco)
														.addComponent(
																comboEndereco,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelCep)
														.addComponent(
																textCep,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelLogradouro))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																textUf,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelUf)
														.addComponent(
																labelNumero)
														.addComponent(
																textNumero,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelBairro)
														.addComponent(
																textBairro,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED, 18,
												Short.MAX_VALUE)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelCidade)
														.addComponent(
																textCidade,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelComplemento)
														.addComponent(
																textComplemento,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																checkBoxAtivo,
																GroupLayout.PREFERRED_SIZE,
																22,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
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
				JanCadCliente.this.buttonNovoActionPerformed(evt);
			}
		});
		this.buttonAlterar.setBackground(new Color(255, 255, 255));
		this.buttonAlterar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/alterar_registro.gif")));
		this.buttonAlterar.setText("Alterar");
		this.buttonAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.buttonAlterarActionPerformed(evt);
			}
		});

		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.buttonExcluirActionPerformed(evt);
			}
		});

		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.buttonAtualizarActionPerformed(evt);
			}
		});
		this.buttonSalvar.setBackground(new Color(255, 255, 255));
		this.buttonSalvar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/gravar_registro.gif")));
		this.buttonSalvar.setText("Salvar");
		this.buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.buttonSalvarActionPerformed(evt);
			}
		});
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/sair.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.buttonFecharActionPerformed(evt);
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
				{ null, null, null, null, null } }, new String[] { "Código",
				"CPF", "Nome", "Telefone", "E-mail", "Endereço", "Ativo" }));

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
				JanCadCliente.this.subNovoActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subNovo);

		this.subFechar.setText("Fechar");
		subFechar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				SHORTCUT_MASK));
		this.subFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.subFecharActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subFechar);

		this.menuBar.add(this.menuArquivo);

		// Menu Cadastro.
		this.menuCadastro.setMnemonic('C');
		this.menuCadastro.setText("Cadastro");

		this.subContato.setText("Contato");
		subContato.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				SHORTCUT_MASK));
		this.subContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.subContatoActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subContato);

		this.subEndereco.setText("Endereço");
		subEndereco.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				SHORTCUT_MASK));
		this.subEndereco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.subEnderecoActionPerformed(evt);
			}
		});
		this.menuCadastro.add(this.subEndereco);

		this.menuBar.add(this.menuCadastro);

		// Inativo
		this.menuInativo.setMnemonic('I');
		this.menuInativo.setText("Inativo");
		this.menuInativo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				JanCadCliente.this.inativoActionPerformed(evt);
			}
		});
		this.menuInativo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadCliente.this.inativoActionPerformed(evt);
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
														709, Short.MAX_VALUE)
												.addComponent(
														panelPesquisarCliente,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														709, Short.MAX_VALUE)
												.addComponent(
														panelBotoes,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														709, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(panelPesquisarCliente,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGap(3)
								.addComponent(panelDadosCadastrais,
										GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panelBotoes,
										GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(scrollPaneInformacaoes,
										GroupLayout.PREFERRED_SIZE, 116,
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

		Integer codigo_cliente = Integer.parseInt(tableInformacaoes.getValueAt(
				row, 0).toString());
		String cpf = tableInformacaoes.getValueAt(row, 1).toString();
		String nome = tableInformacaoes.getValueAt(row, 2).toString();
		String telefone = tableInformacaoes.getValueAt(row, 3).toString();
		String email = tableInformacaoes.getValueAt(row, 4).toString();
		Integer endereco = Integer.parseInt(tableInformacaoes
				.getValueAt(row, 5).toString());
		Boolean ativo = Boolean.parseBoolean(tableInformacaoes.getValueAt(row,
				6).toString());

		this.textCodigo.setText(codigo_cliente.toString());
		this.textCpf.setText(cpf.toString());
		this.textNome.setText(nome.toString());
		this.textTelefone.setText(telefone.toString());
		this.textEmail.setText(email.toString());
		this.comboEndereco.setSelectedItem(endereco.toString());
		this.checkBoxAtivo.setSelected(ativo);

		this.buttonAlterar.setEnabled(true);

		ClienteDao dao = new ClienteDao();
		Cliente cliente = new Cliente();
		cliente.setCpf_cliente(this.textCpf.getText().toString());

		if ((dao.countAtivoInativo(true) >= 1 && dao.cpfCliente(cliente) == false)
				|| (dao.countAtivoInativo(true) > 1 && dao.cpfCliente(cliente) == true)
				|| (dao.countAtivoInativo(true) == 1 && dao.cpfCliente(cliente) == false)
				|| (dao.cpfCliente(cliente) == true && dao.ativoCpf(cliente) == false)) {
			if (dao.countClienteVenda(codigo_cliente) == 0) {
				this.buttonExcluir.setEnabled(true);
			}
		}
	}

	String acao = "codigo_cliente";

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
			ClienteDao dao = new ClienteDao();

			List<Cliente> listaCliente = dao.pesquisar(
					this.textPesquisar.getText(), acao);

			if (listaCliente.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Nenhum registro encontrado");
				listaCliente = dao.listar();
				preencherJTable(listaCliente);
			} else {
				preencherJTable(listaCliente);
			}

		} else {
			JOptionPane.showMessageDialog(null, "Preencha o campo da pesquisa");
		}
	}

	// ButtonNovo.
	private void buttonNovoActionPerformed(ActionEvent evt) {

		// EnderecoDao
		EnderecoDao dao = new EnderecoDao();

		if (dao.count() > 0) {
			esvasiarCampos();
			desativarBotoes();
			this.checkBoxAtivo.setSelected(true);
			this.buttonSalvar.setEnabled(true);

			Visivel();
			this.textCpf.setEditable(true);
			ClienteDao daoCliente = new ClienteDao();
			this.textCodigo.setText(Long.toString(daoCliente
					.retornaAutoIncrement()));

			this.textCpf.grabFocus();
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum endereço cadastrado!");
			dispose();
			JanCadEndereco cadE = new JanCadEndereco("Cliente");
			cadE.setVisible(true);
		}

	}

	// ButtonAlterar.
	private void buttonAlterarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado!");
		} else {
			Visivel();
			desativarBotoes();
			this.buttonAtualizar.setEnabled(true);
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null, "Nenhum cliente selecionado!");
		} else {
			Cliente cliente = new Cliente();
			cliente.setCodigo_cliente(Integer.parseInt((this.textCodigo
					.getText())));
			String mensagem = "Deseja excluir o cliente de código "
					+ cliente.getCodigo_cliente() + "?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				ClienteDao dao = new ClienteDao();
				dao.remover(cliente);

				List<Cliente> listaCliente = dao.listar();

				preencherJTable(listaCliente);

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
				Cliente cliente = new Cliente();
				cliente.setCodigo_cliente(Integer.parseInt((this.textCodigo
						.getText())));
				cliente.setNome_cliente(this.textNome.getText());
				cliente.setTelefone_cliente(this.textTelefone.getText());
				cliente.setEmail_cliente(this.textEmail.getText());
				cliente.setAtivo(this.checkBoxAtivo.isSelected());
				cliente.getEndereco_cliente().setCodigo_endereco(
						Integer.parseInt(this.comboEndereco.getSelectedItem()
								.toString()));

				// Atualiza!!!
				ClienteDao dao = new ClienteDao();
				dao.alterar(cliente);

				JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
				List<Cliente> listaCliente = dao.listar();
				preencherJTable(listaCliente);

				esvasiarCampos();
				this.textCodigo.setText("");

				Invisivel();

				desativarBotoes();
			}
		}
	}

	// ButtonSalvar.
	private void buttonSalvarActionPerformed(ActionEvent evt) {
		Cliente cliente = new Cliente();
		cliente.setCpf_cliente(this.textCpf.getText());

		ClienteDao dao = new ClienteDao();
		if (dao.cpfCliente(cliente)) {
			JOptionPane.showMessageDialog(null,
					"CPF do Cliente já está cadastrado!");
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
					cliente.setCodigo_cliente(Integer.parseInt((this.textCodigo
							.getText())));
					cliente.setCpf_cliente(this.textCpf.getText());
					cliente.setNome_cliente(this.textNome.getText());
					cliente.setTelefone_cliente(this.textTelefone.getText());
					cliente.setEmail_cliente(this.textEmail.getText());
					cliente.setAtivo(this.checkBoxAtivo.isSelected());
					cliente.getEndereco_cliente().setCodigo_endereco(
							Integer.parseInt(this.comboEndereco
									.getSelectedItem().toString()));

					// grave nessa conexão!!!
					dao.inserir(cliente);

					JOptionPane.showMessageDialog(
							null,
							"O cadastro do cliente:\n"
									+ this.textCodigo.getText() + " - "
									+ cliente.getNome_cliente()
									+ "\nFoi realizado com sucesso!");
					List<Cliente> listaCliente = dao.listar();
					preencherJTable(listaCliente);

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
		switch (this.nome) {
		case "venda":
			dispose();
			JanVenda janPe = new JanVenda();
			janPe.setVisible(true);
			break;

		case "endereco":
			dispose();
			JanCadEndereco cadE = new JanCadEndereco("principal");
			cadE.setVisible(true);
			break;

		case "principal":
			dispose();
			JanPrincipal janPr = new JanPrincipal();
			janPr.setVisible(true);
			break;
		default:
			break;
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

	// JMenuItemContato
	private void subContatoActionPerformed(ActionEvent evt) {

		// ClienteDao
		ClienteDao dao = new ClienteDao();

		if (dao.countAtivoInativo(true) > 0) {
			dispose();
			JanCadContato cadC = new JanCadContato();
			cadC.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null,
					"Nenhum cliente ativo cadastrado!");
		}
	}

	// JMenuItemEndereco
	private void subEnderecoActionPerformed(ActionEvent evt) {
		dispose();
		JanCadEndereco cadE = new JanCadEndereco("Cliente");
		cadE.setVisible(true);
	}

	// Inativo.
	private void inativoActionPerformed(MouseEvent evt) {
		funcoes();
		ClienteDao dao = new ClienteDao();
		if (dao.countAtivoInativo(false) > 0) {
			List<Cliente> listaCliete = dao.consultaInativo();

			preencherJTable(listaCliete);
		} else {
			JOptionPane.showMessageDialog(null,
					"Nehum cliente inativo cadastrado!");
		}
	}

	// Inativo.
	private void inativoActionPerformed(ActionEvent evt) {
		inativoActionPerformed(evt);
	}

	// comboEndereco
	private void comboEnderecoActionPerformed(ActionEvent evt) {
		if (!this.comboEndereco.getSelectedItem().equals("")) {
			try {
				EnderecoDao dao = new EnderecoDao();
				List<String> listaEndereco = dao.listaEndereco(Integer
						.parseInt(this.comboEndereco.getSelectedItem()
								.toString()));
				this.textCep.setText(listaEndereco.get(0));
				this.textLogradouro.setText(listaEndereco.get(1));
				this.textNumero.setText(listaEndereco.get(2));
				this.textBairro.setText(listaEndereco.get(3));
				this.textUf.setText(listaEndereco.get(4));
				this.textCidade.setText(listaEndereco.get(5));
				this.textComplemento.setText(listaEndereco.get(6));
			} catch (java.lang.IndexOutOfBoundsException indexOutOfBoundsException) {
				JOptionPane.showMessageDialog(null, "O endereço "
						+ this.comboEndereco.getSelectedItem()
						+ " não foi encontrado");
			}

		} else {
			esvasiarCamposEndereco();
		}

	}
}
