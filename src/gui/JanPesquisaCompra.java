package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.MaskFormatter;

import jdbc.dao.PesquisaCompraDao;
import regraTextField.JtextFieldSomenteNumeros;
import bean.PesquisaCompra;

public class JanPesquisaCompra extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String inicioData = null;
	MaskFormatter maskDataInicio;
	String fimData = null;
	MaskFormatter maskDataFim;
	private JTextField textDataInicial;
	private JTextField textDataFinal;
	private JTextField textCompra;
	private JTextField textFornecedor;
	private JTextField textProduto;
	private JLabel labelDataInicial;
	private JLabel labelDataFinal;
	private JLabel labelCompra;
	private JLabel labelFornecedor;
	private JLabel labelProduto;
	private JButton buttonSair;
	private JButton buttonPesquisar;
	private JPanel panel;

	public JanPesquisaCompra() {
		super("Pesquisa Compra");
		Componentes();
		setLocationRelativeTo(null);
		this.setResizable(false);

	}

	public void esvasiarCampos() {
		this.textDataInicial.setText("");
		this.textDataFinal.setText("");
		this.textCompra.setText("");
		this.textFornecedor.setText("");
		this.textProduto.setText("");

		this.textDataInicial.grabFocus();
	}

	// teste campos vaisios
	public Boolean testeCampos() {
		if (!((this.textDataInicial.getText().equals("  -  -    ")) && (this.textDataFinal
				.getText().equals("  -  -    ")))
				|| !(this.textCompra.getText().equals(""))
				|| !(this.textFornecedor.getText().equals(""))
				|| !(this.textProduto.getText().equals(""))) {
			return true;
		} else {
			return false;
		}
	}

	private void Componentes() {
		try {
			this.maskDataInicio = new MaskFormatter("##-##-####");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel inserir a mascara");
		}

		try {
			this.maskDataFim = new MaskFormatter("##-##-####");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					"Nao foi possivel inserir a mascara");
		}

		this.textDataInicial = new JFormattedTextField(this.maskDataInicio);
		this.textDataFinal = new JFormattedTextField(this.maskDataFim);
		this.textCompra = new JtextFieldSomenteNumeros(7);
		this.textFornecedor = new JtextFieldSomenteNumeros(7);
		this.textProduto = new JtextFieldSomenteNumeros(7);
		this.labelDataInicial = new JLabel();
		this.labelDataFinal = new JLabel();
		this.labelCompra = new JLabel();
		this.labelFornecedor = new JLabel();
		this.labelProduto = new JLabel();
		this.buttonSair = new JButton();
		this.buttonPesquisar = new JButton();
		this.panel = new JPanel();

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.panel.setBorder(BorderFactory.createTitledBorder(""));

		// Data inicial
		this.labelDataInicial.setText("Data inicial:");
		this.labelDataInicial.setFont(new Font("Tahoma", 1, 11));
		this.textDataInicial.setFont(new Font("Tahoma", 1, 11));
		this.textDataInicial.grabFocus();

		// Data inicial
		this.labelDataFinal.setText("Data final:");
		this.labelDataFinal.setFont(new Font("Tahoma", 1, 11));
		this.textDataFinal.setFont(new Font("Tahoma", 1, 11));

		// Compra.
		this.labelCompra.setText("Compra:");
		this.textCompra.setFont(new Font("Tahoma", 1, 11));

		// Fornecedor.
		this.labelFornecedor.setText("Fornecedor:");
		this.textFornecedor.setFont(new Font("Tahoma", 1, 11));

		// Produto.
		this.labelProduto.setText("Produto:");
		this.textProduto.setFont(new Font("Tahoma", 1, 11));

		// Sair
		this.buttonSair.setBackground(new Color(255, 255, 255));
		this.buttonSair.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/cancelar.png")));
		this.buttonSair.setText("Sair");
		this.buttonSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dispose();
				JanListagemCompra JanL = new JanListagemCompra("principal");
				JanL.setVisible(true);
			}
		});

		// Pesquisar
		this.buttonPesquisar.setBackground(new Color(255, 255, 255));
		this.buttonPesquisar.setIcon(new ImageIcon(getClass().getResource(
				"/imagens/entrar.png")));
		this.buttonPesquisar.setText("Pesquisar");
		this.buttonPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				JanPesquisaCompra.this.buttonPesquisarActionPerformed(evt);
			}
		});
		getRootPane().setDefaultButton(buttonPesquisar);

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(buttonSair, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
							.addGap(12)
							.addComponent(buttonPesquisar, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(labelProduto)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textProduto, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
							.addGap(93))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(labelFornecedor, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFornecedor, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(labelCompra, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textCompra, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
							.addGap(93))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(textDataInicial, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
								.addComponent(labelDataInicial, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(labelDataFinal, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
								.addComponent(textDataFinal, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(4)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelDataInicial)
						.addComponent(labelDataFinal))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textDataInicial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textDataFinal, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textCompra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelCompra))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelFornecedor)
						.addComponent(textFornecedor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelProduto)
						.addComponent(textProduto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonPesquisar, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(buttonSair, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 211,
								Short.MAX_VALUE).addContainerGap()));

		getContentPane().setLayout(groupLayout);
		pack();
	}

	// ButtonPesquisar.
	private void buttonPesquisarActionPerformed(ActionEvent evt) {
		if (this.testeCampos() == false) {
			JOptionPane.showMessageDialog(null,
					"Preencha pelo menos uma opção de pesquisa!");
			esvasiarCampos();
		} else {
			PesquisaCompra pesquisaCompra = new PesquisaCompra();

			// montando a data_inicial através do Calendar
			if (!((this.textDataInicial.getText().equals("  -  -    ")) && (this.textDataFinal
					.getText().equals("  -  -    ")))) {

				Date date = null;
				try {
					date = (Date) new SimpleDateFormat("dd-MM-yyyy")
							.parse(this.textDataInicial.getText().toString());

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Calendar data_inicial = Calendar.getInstance();
				data_inicial.setTime(date);
				pesquisaCompra.setData_inicial(data_inicial);

				// montando a data_final através do Calendar
				try {

					date = (Date) new SimpleDateFormat("dd-MM-yyyy")
							.parse(this.textDataFinal.getText().toString());

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Calendar data_final = Calendar.getInstance();
				data_final.setTime(date);
				pesquisaCompra.setData_final(data_final);

			} else {
				pesquisaCompra.setData_inicial(null);
				pesquisaCompra.setData_final(null);
			}

			// Compra
			if (!this.textCompra.getText().equals("")) {
				pesquisaCompra.setCodigo_compra(Integer.parseInt(this.textCompra
						.getText()));
			} else {
				pesquisaCompra.setCodigo_compra(0);
			}

			// CLiente
			if (!this.textFornecedor.getText().equals("")) {
				pesquisaCompra.setCodigo_fornecedor(Integer
						.parseInt(this.textFornecedor.getText()));
			} else {
				pesquisaCompra.setCodigo_fornecedor(0);
			}

			// Produto
			if (!this.textProduto.getText().equals("")) {
				pesquisaCompra.setCodigo_produto(Integer
						.parseInt(this.textProduto.getText()));
			} else {
				pesquisaCompra.setCodigo_produto(0);
			}

			// DAO
			PesquisaCompraDao dao = new PesquisaCompraDao();
			dao.inserir(pesquisaCompra);

			if (dao.listar().size() == 0) {
				JOptionPane.showMessageDialog(null,
						"Nenhum resultado encontrado!");
			}

			dispose();
			JanListagemCompra JanL = new JanListagemCompra("pesquisa");
			JanL.setVisible(true);
		}
	}
}
