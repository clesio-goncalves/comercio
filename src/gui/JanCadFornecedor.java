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

import jdbc.dao.EnderecoDao;
import jdbc.dao.FornecedorDao;
import regraTextField.JtextFieldEmail;
import regraTextField.JtextFieldSomenteLetrasNumero;
import regraTextField.JtextFieldSomenteNumeros;
import bean.Fornecedor;

public class JanCadFornecedor extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String inicioTelefone = null;
	MaskFormatter maskTelefone;
	String inicioCnpj = null;
	MaskFormatter maskCnpj;
	String nome = null;
	private JTextField textPesquisar;
	private JTextField textCodigo;
	private JTextField textCnpj;
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
	private JButton buttonPesquisar;
	private JButton buttonNovo;
	private JButton buttonAlterar;
	private JButton buttonExcluir;
	private JButton buttonAtualizar;
	private JButton buttonSalvar;
	private JButton buttonFechar;
	private JLabel labelCodigo;
	private JLabel labelCnpj;
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
	private JMenu menuEndereco;
	private JMenuItem subNovo;
	private JMenuItem subFechar;
	private JPanel panelPesquisarFornecedor;
	private JPanel panelDadosCadastrais;
	private JPanel panelBotoes;
	private JScrollPane scrollPaneInformacaoes;
	private JTable tableInformacaoes;
	private JRadioButton radioCodigo;
	private JRadioButton radioCnpj;
	private JRadioButton radioNome;
	private JRadioButton radioTelefone;
	private JRadioButton radioEmail;
	private JRadioButton radioEndereco;

	public JanCadFornecedor(String nome) {
		super("Cadastro - Fornecedor");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

		this.nome = nome;

		FornecedorDao dao = new FornecedorDao();
		List<Fornecedor> listaFornecedor = dao.listar();

		preencherJTable(listaFornecedor);
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
		this.textCnpj.setEditable(false);
		this.textNome.setEditable(false);
		this.textTelefone.setEditable(false);
		this.textEmail.setEditable(false);
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
	}

	public void desativarBotoes() {
		this.buttonSalvar.setEnabled(false);
		this.buttonExcluir.setEnabled(false);
		this.buttonAtualizar.setEnabled(false);
		this.buttonAlterar.setEnabled(false);
	}

	public void esvasiarCampos() {
		this.textCnpj.setText("");
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
				|| (this.textCnpj.getText().equals("  .   .   /    -  "))
				|| (this.textNome.getText().equals(""))
				|| (this.textTelefone.getText().equals("(  )    -    "))
				|| (this.comboEndereco.getSelectedItem().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	public void preencherJTable(List<Fornecedor> listaFornecedor) {
		this.tableInformacaoes.getColumnModel().getColumn(0)
				.setPreferredWidth(5);
		this.tableInformacaoes.getColumnModel().getColumn(1)
				.setPreferredWidth(100);
		this.tableInformacaoes.getColumnModel().getColumn(2)
				.setPreferredWidth(150);
		this.tableInformacaoes.getColumnModel().getColumn(3)
				.setPreferredWidth(70);
		this.tableInformacaoes.getColumnModel().getColumn(4)
				.setPreferredWidth(100);
		this.tableInformacaoes.getColumnModel().getColumn(5)
				.setPreferredWidth(30);

		DefaultTableModel tabela = (DefaultTableModel) this.tableInformacaoes
				.getModel();
		tabela.setNumRows(0);

		for (Fornecedor fornecedor : listaFornecedor) {
			tabela.addRow(new Object[] { fornecedor.getCodigo_fornecedor(),
					fornecedor.getCnpj_fornecedor(),
					fornecedor.getNome_fornecedor(),
					fornecedor.getTelefone_fornecedor(),
					fornecedor.getEmail_fornecedor(),
					fornecedor.getEndereco_fornecedor().getCodigo_endereco() });
		}

		this.tableInformacaoes.getModel().addTableModelListener(
				new TableModelListener() {
					public void tableChanged(TableModelEvent e) {
						JanCadFornecedor.this.tableInformacaoesTableChanged(e);
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
			this.maskCnpj = new MaskFormatter("##.###.###/####-##");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel inserir a mascara");
		}
		this.textPesquisar = new JTextField();
		this.textCnpj = new JFormattedTextField(this.maskCnpj);
		this.textCodigo = new JtextFieldSomenteNumeros(7);
		this.textNome = new JtextFieldSomenteLetrasNumero(100);
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
		this.buttonPesquisar = new JButton();
		this.buttonNovo = new JButton();
		this.buttonAlterar = new JButton();
		this.buttonExcluir = new JButton();
		this.buttonAtualizar = new JButton();
		this.buttonSalvar = new JButton();
		this.buttonFechar = new JButton();
		this.labelCodigo = new JLabel();
		this.labelCnpj = new JLabel();
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
		this.menuEndereco = new JMenu();
		this.subNovo = new JMenuItem();
		this.subFechar = new JMenuItem();
		this.panelPesquisarFornecedor = new JPanel();
		this.panelDadosCadastrais = new JPanel();
		this.panelBotoes = new JPanel();
		this.scrollPaneInformacaoes = new JScrollPane();
		scrollPaneInformacaoes
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.tableInformacaoes = new JTable();
		tableInformacaoes.setColumnSelectionAllowed(true);
		this.radioCodigo = new JRadioButton();
		this.radioCnpj = new JRadioButton();
		this.radioNome = new JRadioButton();
		this.radioTelefone = new JRadioButton();
		this.radioEmail = new JRadioButton();
		this.radioEndereco = new JRadioButton();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// ---------- PANEL PESQUISAR FORNECEDOR ----------- //
		this.panelPesquisarFornecedor.setBorder(BorderFactory
				.createTitledBorder("Pesquisar Fornecedores"));

		this.textPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.textPesquisarActionPerformed(evt);
			}
		});
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/localizar.gif")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.buttonPesquisarActionPerformed(evt);
			}
		});

		// Radio Codigo.
		this.radioCodigo.setText("Código");
		this.radioCodigo.setFont(new Font("Tahoma", 1, 11));
		this.radioCodigo.setActionCommand("codigo_fornecedor");
		this.radioCodigo.setSelected(true);

		this.radioCodigo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.actionPerformed(evt);
			}
		});

		// Radio Cnpj.
		this.radioCnpj.setText("CNPJ");
		this.radioCnpj.setFont(new Font("Tahoma", 1, 11));
		this.radioCnpj.setActionCommand("cnpj_fornecedor");

		this.radioCnpj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.actionPerformed(evt);
			}
		});

		// Radio Nome.
		this.radioNome.setText("Nome");
		this.radioNome.setFont(new Font("Tahoma", 1, 11));
		this.radioNome.setActionCommand("nome_fornecedor");

		this.radioNome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.actionPerformed(evt);
			}
		});

		// Radio Telefone.
		this.radioTelefone.setText("Telefone");
		this.radioTelefone.setFont(new Font("Tahoma", 1, 11));
		this.radioTelefone.setActionCommand("telefone_fornecedor");

		this.radioTelefone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.actionPerformed(evt);
			}
		});

		// Radio E-mail.
		this.radioEmail.setText("E-mail");
		this.radioEmail.setFont(new Font("Tahoma", 1, 11));
		this.radioEmail.setActionCommand("email_fornecedor");

		this.radioEmail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.actionPerformed(evt);
			}
		});

		// Radio Endereco.
		this.radioEndereco.setText("Endereço");
		this.radioEndereco.setFont(new Font("Tahoma", 1, 11));
		this.radioEndereco.setActionCommand("endereco_fornecedor");

		this.radioEndereco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.actionPerformed(evt);
			}
		});

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioCodigo);
		group.add(this.radioCnpj);
		group.add(this.radioNome);
		group.add(this.radioTelefone);
		group.add(this.radioEmail);
		group.add(this.radioEndereco);

		GroupLayout layoutPesquisarFornecedor = new GroupLayout(
				this.panelPesquisarFornecedor);
		layoutPesquisarFornecedor
				.setHorizontalGroup(layoutPesquisarFornecedor
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								layoutPesquisarFornecedor
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layoutPesquisarFornecedor
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutPesquisarFornecedor
																		.createSequentialGroup()
																		.addComponent(
																				textPesquisar,
																				GroupLayout.DEFAULT_SIZE,
																				445,
																				Short.MAX_VALUE)
																		.addGap(18))
														.addGroup(
																layoutPesquisarFornecedor
																		.createSequentialGroup()
																		.addComponent(
																				radioCodigo)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				radioCnpj)
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
		layoutPesquisarFornecedor
				.setVerticalGroup(layoutPesquisarFornecedor
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								layoutPesquisarFornecedor
										.createSequentialGroup()
										.addGroup(
												layoutPesquisarFornecedor
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
								layoutPesquisarFornecedor
										.createSequentialGroup()
										.addContainerGap(28, Short.MAX_VALUE)
										.addGroup(
												layoutPesquisarFornecedor
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																radioCodigo)
														.addComponent(radioCnpj)
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
		this.panelPesquisarFornecedor.setLayout(layoutPesquisarFornecedor);

		// ---------- PANEL DADOS CADASTRAIS ----------- //
		this.panelDadosCadastrais.setBorder(BorderFactory
				.createTitledBorder("Dados Cadastrais"));

		// Codigo.
		this.labelCodigo.setText("Código:");
		this.textCodigo.setFont(new Font("Tahoma", 1, 11));
		this.textCodigo.setEnabled(false);

		// CNPJ.
		this.labelCnpj.setText("CNPJ:");
		this.textCnpj.setFont(new Font("Tahoma", 1, 11));

		// Nome.
		this.labelNome.setText("Nome:");
		this.textNome.setFont(new Font("Tahoma", 1, 11));

		// Telefone.
		this.labelTelefone.setText("Telefone:");
		this.textTelefone.setFont(new Font("Tahoma", 1, 11));

		// E-mail.
		this.labelEmail.setText("E-mail:");
		this.textEmail.setFont(new Font("Tahoma", 1, 11));

		// Endereco.
		this.labelEndereco.setText("Endereço:");
		this.comboEndereco.setFont(new Font("Tahoma", 1, 11));
		this.comboEndereco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.comboEnderecoActionPerformed(evt);
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
																Alignment.LEADING)
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								labelTelefone)
																						.addComponent(
																								labelCodigo))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addGroup(
																								layoutDadosCadastrais
																										.createSequentialGroup()
																										.addComponent(
																												textCodigo,
																												GroupLayout.PREFERRED_SIZE,
																												66,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												labelCnpj)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textCnpj,
																												GroupLayout.PREFERRED_SIZE,
																												169,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												labelNome)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textNome,
																												GroupLayout.DEFAULT_SIZE,
																												231,
																												Short.MAX_VALUE))
																						.addGroup(
																								layoutDadosCadastrais
																										.createSequentialGroup()
																										.addComponent(
																												textTelefone,
																												GroupLayout.PREFERRED_SIZE,
																												140,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												labelEmail)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												textEmail,
																												GroupLayout.PREFERRED_SIZE,
																												202,
																												GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												labelEndereco)
																										.addPreferredGap(
																												ComponentPlacement.RELATED,
																												GroupLayout.DEFAULT_SIZE,
																												Short.MAX_VALUE)
																										.addComponent(
																												comboEndereco,
																												GroupLayout.PREFERRED_SIZE,
																												88,
																												GroupLayout.PREFERRED_SIZE))))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
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
																				258,
																				Short.MAX_VALUE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelNumero,
																				GroupLayout.PREFERRED_SIZE,
																				60,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textNumero,
																				GroupLayout.PREFERRED_SIZE,
																				65,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
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
																				212,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(
																				labelUf,
																				GroupLayout.PREFERRED_SIZE,
																				23,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				textUf,
																				GroupLayout.PREFERRED_SIZE,
																				75,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelCidade,
																				GroupLayout.PREFERRED_SIZE,
																				60,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(3)
																		.addComponent(
																				textCidade,
																				GroupLayout.PREFERRED_SIZE,
																				206,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
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
																				299,
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
																textCodigo)
														.addComponent(labelCnpj)
														.addComponent(
																textCnpj,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textNome,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(labelNome))
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addGap(18)
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								labelTelefone)
																						.addComponent(
																								textTelefone,
																								GroupLayout.PREFERRED_SIZE,
																								18,
																								GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																layoutDadosCadastrais
																		.createSequentialGroup()
																		.addGap(19)
																		.addGroup(
																				layoutDadosCadastrais
																						.createParallelGroup(
																								Alignment.BASELINE)
																						.addComponent(
																								labelEmail)
																						.addComponent(
																								textEmail,
																								GroupLayout.PREFERRED_SIZE,
																								18,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								comboEndereco,
																								GroupLayout.PREFERRED_SIZE,
																								18,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								labelEndereco))))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(labelCep)
														.addComponent(
																textCep,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelLogradouro)
														.addComponent(
																textLogradouro,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																textNumero,
																GroupLayout.PREFERRED_SIZE,
																18,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																labelNumero))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																layoutDadosCadastrais
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				labelBairro)
																		.addComponent(
																				textBairro,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(labelUf)
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
																				textUf,
																				GroupLayout.PREFERRED_SIZE,
																				18,
																				GroupLayout.PREFERRED_SIZE)))
										.addGap(18)
										.addGroup(
												layoutDadosCadastrais
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																labelComplemento)
														.addComponent(
																textComplemento,
																GroupLayout.PREFERRED_SIZE,
																18,
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
				JanCadFornecedor.this.buttonNovoActionPerformed(evt);
			}
		});
		this.buttonAlterar.setBackground(new Color(255, 255, 255));
		this.buttonAlterar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/alterar_registro.gif")));
		this.buttonAlterar.setText("Alterar");
		this.buttonAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.buttonAlterarActionPerformed(evt);
			}
		});

		this.buttonExcluir.setBackground(new Color(255, 255, 255));
		this.buttonExcluir.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/excluir.png")));
		this.buttonExcluir.setText("Excluir");
		this.buttonExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.buttonExcluirActionPerformed(evt);
			}
		});

		this.buttonAtualizar.setBackground(new Color(255, 255, 255));
		this.buttonAtualizar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/atualizar.png")));
		this.buttonAtualizar.setText("Atualizar");
		this.buttonAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.buttonAtualizarActionPerformed(evt);
			}
		});
		this.buttonSalvar.setBackground(new Color(255, 255, 255));
		this.buttonSalvar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/gravar_registro.gif")));
		this.buttonSalvar.setText("Salvar");
		this.buttonSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.buttonSalvarActionPerformed(evt);
			}
		});
		this.buttonFechar.setBackground(new Color(255, 255, 255));
		this.buttonFechar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/sair.gif")));
		this.buttonFechar.setText("Fechar");
		this.buttonFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.buttonFecharActionPerformed(evt);
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
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null },
				{ null, null, null, null, null, null } }, new String[] {
				"Código", "CNPJ", "Nome", "Telefone", "E-mail", "Endereço" }));

		this.tableInformacaoes.setFocusable(false);
		this.scrollPaneInformacaoes.setViewportView(this.tableInformacaoes);

		// ---------- BARRA DE FERRAMENTAS ----------- //
		final int SHORTCUT_MASK = Toolkit.getDefaultToolkit()
				.getMenuShortcutKeyMask();

		this.menuArquivo.setMnemonic('A');
		this.menuArquivo.setText("Arquivo");

		this.subNovo.setText("Novo");
		subNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				SHORTCUT_MASK));
		this.subNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.subNovoActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subNovo);

		this.subFechar.setText("Fechar");
		subFechar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
				SHORTCUT_MASK));
		this.subFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanCadFornecedor.this.subFecharActionPerformed(evt);
			}
		});
		this.menuArquivo.add(this.subFechar);

		this.menuBar.add(this.menuArquivo);

		// Menu Fornecedor.
		this.menuEndereco.setMnemonic('E');
		this.menuEndereco.setText("Endereço");
		this.menuEndereco.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				dispose();
				JanCadEndereco cadE = new JanCadEndereco("Fornecedor");
				cadE.setVisible(true);

			}
		});
		this.menuEndereco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
				JanCadEndereco cadE = new JanCadEndereco("Fornecedor");
				cadE.setVisible(true);
			}
		});
		this.menuBar.add(this.menuEndereco);

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
														panelPesquisarFornecedor,
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
								.addComponent(panelPesquisarFornecedor,
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

		Integer codigo_fornecedor = Integer.parseInt(tableInformacaoes
				.getValueAt(row, 0).toString());
		String cnpj = tableInformacaoes.getValueAt(row, 1).toString();
		String nome = tableInformacaoes.getValueAt(row, 2).toString();
		String telefone = tableInformacaoes.getValueAt(row, 3).toString();
		String email = tableInformacaoes.getValueAt(row, 4).toString();
		Integer endereco = Integer.parseInt(tableInformacaoes
				.getValueAt(row, 5).toString());

		this.textCodigo.setText(codigo_fornecedor.toString());
		this.textCnpj.setText(cnpj.toString());
		this.textNome.setText(nome.toString());
		this.textTelefone.setText(telefone.toString());
		this.textEmail.setText(email.toString());
		this.comboEndereco.setSelectedItem(endereco.toString());

		this.buttonAlterar.setEnabled(true);

		FornecedorDao daoFornecedor = new FornecedorDao();

		if (daoFornecedor.countFornecedorCompra(codigo_fornecedor) == 0) {
			this.buttonExcluir.setEnabled(true);
		}
	}

	String acao = "codigo_fornecedor";

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
			FornecedorDao dao = new FornecedorDao();

			List<Fornecedor> listaFornecedor = dao.pesquisar(
					this.textPesquisar.getText(), acao);

			if (listaFornecedor.size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Nenhum registro encontrado");
				listaFornecedor = dao.listar();
				preencherJTable(listaFornecedor);
			} else {
				preencherJTable(listaFornecedor);
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
			desativarBotoes();
			this.buttonSalvar.setEnabled(true);

			Visivel();
			this.textCnpj.setEditable(true);
			FornecedorDao daoFornecedor = new FornecedorDao();
			this.textCodigo.setText(Long.toString(daoFornecedor
					.retornaAutoIncrement()));
			esvasiarCampos();
			this.textCnpj.grabFocus();
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum endereço cadastrado!");
			dispose();
			JanCadEndereco cadE = new JanCadEndereco("Fornecedor");
			cadE.setVisible(true);
		}

	}

	// ButtonAlterar.
	private void buttonAlterarActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Nenhum fornecedor selecionado!");
		} else {
			Visivel();
			desativarBotoes();
			this.buttonAtualizar.setEnabled(true);
		}
	}

	// ButtonExcluir.
	private void buttonExcluirActionPerformed(ActionEvent evt) {
		if (this.testeCampos()) {
			JOptionPane.showMessageDialog(null,
					"Nenhum fornecedor selecionado!");
		} else {
			Fornecedor fornecedor = new Fornecedor();
			fornecedor.setCodigo_fornecedor(Integer.parseInt((this.textCodigo
					.getText())));
			String mensagem = "Deseja excluir o fornecedor de código "
					+ fornecedor.getCodigo_fornecedor() + "?";
			int i = okcancel(mensagem);

			if (i == JOptionPane.OK_OPTION) {
				FornecedorDao dao = new FornecedorDao();
				dao.remover(fornecedor);

				List<Fornecedor> listaFornecedor = dao.listar();

				preencherJTable(listaFornecedor);

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

				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setCodigo_fornecedor(Integer
						.parseInt((this.textCodigo.getText())));
				fornecedor.setNome_fornecedor(this.textNome.getText());
				fornecedor.setTelefone_fornecedor(this.textTelefone.getText());
				fornecedor.setEmail_fornecedor(this.textEmail.getText());
				fornecedor.getEndereco_fornecedor().setCodigo_endereco(
						Integer.parseInt(this.comboEndereco.getSelectedItem()
								.toString()));

				// Atualiza!!!
				FornecedorDao dao = new FornecedorDao();
				dao.alterar(fornecedor);

				JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
				List<Fornecedor> listaFornecedor = dao.listar();
				preencherJTable(listaFornecedor);

				esvasiarCampos();
				this.textCodigo.setText("");

				Invisivel();

				desativarBotoes();
			}
		}
	}

	// ButtonSalvar.
	private void buttonSalvarActionPerformed(ActionEvent evt) {
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setCnpj_fornecedor(this.textCnpj.getText());

		FornecedorDao dao = new FornecedorDao();
		if (dao.cnpjFornecedor(fornecedor)) {
			JOptionPane.showMessageDialog(null,
					"CNPJ do Fornecedor já está cadastrado!");
			this.textCnpj.setText("");
			this.textCnpj.grabFocus();
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
					fornecedor.setCodigo_fornecedor(Integer
							.parseInt((this.textCodigo.getText())));
					fornecedor.setCnpj_fornecedor(this.textCnpj.getText());
					fornecedor.setNome_fornecedor(this.textNome.getText());
					fornecedor.setTelefone_fornecedor(this.textTelefone
							.getText());
					fornecedor.setEmail_fornecedor(this.textEmail.getText());
					fornecedor.getEndereco_fornecedor().setCodigo_endereco(
							Integer.parseInt(this.comboEndereco
									.getSelectedItem().toString()));

					// grave nessa conexão!!!
					dao.inserir(fornecedor);

					JOptionPane.showMessageDialog(
							null,
							"O cadastro do fornecedor:\n"
									+ this.textCodigo.getText() + " - "
									+ fornecedor.getNome_fornecedor()
									+ "\nFoi realizado com sucesso!");
					List<Fornecedor> listaFornecedor = dao.listar();
					preencherJTable(listaFornecedor);

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
		case "produto":
			dispose();
			JanCadProduto cadP = new JanCadProduto("principal");
			cadP.setVisible(true);
			break;

		case "principal":
			dispose();
			JanPrincipal janP = new JanPrincipal();
			janP.setVisible(true);
			break;

		case "compra":
			dispose();
			JanCompra janC = new JanCompra();
			janC.setVisible(true);
			break;

		case "endereco":
			dispose();
			JanCadEndereco janE = new JanCadEndereco("principal");
			janE.setVisible(true);
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
