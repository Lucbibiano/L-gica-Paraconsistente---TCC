package Visao;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Controle.ControleTela;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

public class TelaPrincipal implements ActionListener{
	
	//Instanciando classes
	ControleTela controle = new ControleTela();
	public boolean confirmarSelecao = false; //Confirma seleção para exclusão da doença
	public int resultado = 0;
	public long[] codigos;
	JLabel retorno = new JLabel();
	public boolean confirmar = false;
	public int iddoenca;
	public boolean verificacao = true;	
	int construidoCad = 0;
	int construidoAnalise = 0;
	public boolean confirmaExclusao = false;
	int telaConstruida = 0;
	public ArrayList<Integer> doencasNAssociadas = new ArrayList<Integer>();
	public ArrayList doencasNAssociadasinserirSintoma = new ArrayList();
	
	//Criando Componentes - TELA PRINCIPAL
	JFrame telaPrincipal = new JFrame("Medical Analysis - Sistema de auxilio ao diagnóstico médico.");
	public JFrame grafico = new JFrame("Gráfico com as possíveis doenças.");
	public DefaultCategoryDataset dadosGrafico;
	JPanel analise = new JPanel(null);
	JPanel cadastro = new JPanel(new GridLayout(1,2));
	JPanel paciente = new JPanel(null);
	JPanel home = new JPanel(null);
	public JTabbedPane abas = new JTabbedPane();
	JLabel bemvindo = new JLabel("Bem-Vindo");
	
	// Criando Componentes - PERFIL DE ACESSO AO SISTEMA
	JFrame acessoInicial = new JFrame("Medical Analysis - Módulo de acesso.");
	JPanel perfil = new JPanel(new BorderLayout());
	JLabel logo = new JLabel();
	JPanel botoes = new JPanel(null);
	JLabel infAcesso = new JLabel("Escolha um perfil de acesso de acordo com sua necessidade.");
	JButton bpreatendimento = new JButton("Pré-atendimento");
	JButton bmedicoConsulta = new JButton("Consulta médica");
	JButton bmedicoCadastro = new JButton("Cadastros médicos");

	//Criando Componentes - TELA DE CADASTROS
	JButton bNovaDoenca = new JButton("Adicionar nova doença");
	JButton bEditarDoenca = new JButton("Editar uma doença");
	JButton bNovoSintoma = new JButton("Adicionar um novo sintoma");
	JButton bEditarSintoma = new JButton("Editar um sintoma");
	JButton bAssociacoesSxD = new JButton("Associar Doencas e sintomas");
	JPanel cadastros = new JPanel(new GridLayout(5,2));
	JPanel pendencias = new JPanel(null);
	
	//TABELA DE DOENÇAS DISPONÍVEIS PARA CADASTRO DA ASSOCIAÇÃO
	public JTable doencasPendentes;
	public DefaultTableModel modeloDoencasPendentes;
	JScrollPane rolagemDoencasPendentes;
	public String dados4[][] = {};
	public String colunas4[]={"Doenças Pendentes de Cadastro"};
	public int associacaoCriado;
	
	//Criando Componentes - TELA DE ENVIO DE DADOS O PACIENTE
	JLabel indpaciente = new JLabel("Paciente:");
	JLabel indidade = new JLabel("Idade:");
	JLabel indsintoma = new JLabel("Sintoma");
	JLabel indcoment = new JLabel("Comentários:");
	public JTextField nome = new JTextField();
	public JTextField idade = new JTextField();
	public JTextArea comentario = new JTextArea();
	public String comentarioEnfer;
	JButton badicionar = new JButton("Adicionar");
	JButton benviar = new JButton("Enviar");
	JButton bcancelar = new JButton("Cancelar");
	public JButton bremover = new JButton("Remover");
	JButton bcadPaciente = new JButton("Cadastrar paciente");
	public JComboBox sintomasel = new JComboBox();
	TextArea temp2 = new TextArea("TABELA2"); //temporária
	public int contadorSintoma;
	public DefaultTableModel modelo;
	public JTable tabelaSintomaPaciente;
	public String dados[][] = {};
	public String colunas[]={"Sintoma"};
	JScrollPane rolagem;
	JPanel cadpaciente = new JPanel(null);
	public ArrayList<Integer> valsintomas = new ArrayList<Integer>(); // guarda todos os Ids
	public ArrayList<Integer> sintomasinseridos = new ArrayList<Integer>(); // guarda todos os Ids
	JPanel cadprinc = new JPanel(new GridLayout(8,9));
	public int cadPaciConstruido;
   
	//Criando Componentes - TELA DE ANALISE
	JLabel indIdAna = new JLabel("Id");
	JLabel indpacienteAna = new JLabel("Paciente:");
	JLabel indidadeAna = new JLabel("Idade:");
	public JTextField nomePaciente = new JTextField();
	public JTextField idadePaciente = new JTextField();
	public JTextField idPaciente = new JTextField();
	JButton bcomentario = new JButton("Comentário da enfermeira");
	JButton bgrafico = new JButton("Exibir gráfico");
	JButton bvoltar = new JButton("Voltar");
	public JTable tabelaPS;
	public DefaultTableModel modeloTabelaPS;
	public JTable tabelaPD;
	public DefaultTableModel modeloTabelaPD;
	JScrollPane rolagemPS;
	JScrollPane rolagemPD;
	public String dadosPS[][] = {};
	public String colunasPS[]={"Sintomas relatados e evidênciados"};
	public String dadosPD[][] = {};
	public String colunasPD[]={"Possíveis Doencas","Grau de Certeza","Grau de Incerteza"};
	JFreeChart modeloGrafico;
	CategoryPlot plot;
	ChartPanel painelBarra;
	JPanel painelGrafico = new JPanel(null);
	
	// PA é igual a Paciente Analise
	public DefaultTableModel modeloAnalise;
	public JTable tabelaPacienteAnalise;
	public String dadosPA[][] = {};
	public String colunasPA[]={"Pacientes"};
	JScrollPane rolagemPA;
	JPanel analisecad = new JPanel(null);
	JPanel analiseprinc = new JPanel(null);
	public ArrayList sintomasPaciente = new ArrayList();
	
	//Criando Componentes - TELA DE CADASTRO DE DOENÇAS
	JPanel caddoenca = new JPanel(null);
	public JTextField campodoenca = new JTextField();
	public JTextArea campodoencadesc = new JTextArea();
	JLabel indCadDoenca = new JLabel("Nome da doença:"); // utilizado tanto na criação de uma nova doença quanto na edição.
	JLabel indCadDetalhe = new JLabel("Detalhes sobre a doença:"); // utilizado tanto na criação de uma nova doença quanto na edição.
	JButton salvardoenca = new JButton("Salvar");
	
	//componente que serve tanto para cadastro de doenças quanto para cadastro de sintomas
	JButton cancelar = new JButton("Cancelar");
	
	//Criando Componentes - TELA DE CADASTRO DE SINTOMAS
	JPanel cadSintoma = new JPanel(null);
	public JTextField camposintoma = new JTextField();
	public JTextArea camposintomadesc = new JTextArea();
	JLabel indCadSintoma = new JLabel("Nome do sintoma:");
	JLabel indCadDetalheSintoma = new JLabel("Detalhes sobre o sintoma:");
	JButton salvarSintoma = new JButton("Salvar");
	
	//Criando Componentes - TELA DE EDIÇÃO DE DOENCAS
	JPanel edtDoenca = new JPanel(null);
	JLabel indSelecionarDoenca = new JLabel("Selecione a doença");
	public JComboBox doenca = new JComboBox();
	JButton salvarEdtDoenca = new JButton("Salvar Alterações");
	JButton excluirDoenca = new JButton("Excluir Doença");
	JButton selecionar = new JButton("Selecione");
	
	//Criando Componentes - TELA DE EDIÇÃO DE SINTOMA
	JPanel edtSintoma = new JPanel(null);
	JLabel indSelecionarSintoma = new JLabel("Selecione o sintoma");
	public JComboBox sintoma = new JComboBox();
	JButton salvarEdtSintoma = new JButton("Salvar Alterações");
	JButton excluirSintoma = new JButton("Excluir Sintoma");
	JButton selecionarSintoma = new JButton("Selecione");
	
	//Criando Componentes - TELA DE ASSOCIAÇÃO SINTOMAS E DOENÇAS
	JLabel doencaAss = new JLabel("Doença:");
	public JComboBox doencaAssociada = new JComboBox();
	JButton confirmaDoenca = new JButton("Confirmar Doenca!");
	JButton cancelaDoenca = new JButton("Cancelar");
	JButton salvarAssociacoes = new JButton("Salvar Associações");
	public JTable sintomasDisponiveis;
	public DefaultTableModel ModeloSintomasDisponiveis;
	JButton limpar = new JButton("Limpar sintomas");
	JPanel associacoes = new JPanel(null);
	JScrollPane rolagemSintoma;
	JLabel sintomaAss = new JLabel ("Selecione os sintomas na tabela abaixo.");
	public String dados2[][] = {};
	public String colunas2[]={"Sintomas disponíveis"};
	public int associacao = 0;
	public ArrayList<String> todosSintomas = new ArrayList<String>();
	public boolean testeConfirmaDoenca = true;
	
	//Elementos que farão parte da tabela de sintomas escolhidos
	public JTable sintomasEscolhidos;
	public DefaultTableModel ModeloSintomasEscolhidos;
	JScrollPane rolagemSintEscolhidos;
	public String dados3[][] = {};
	public String colunas3[]={"Sintomas escolhidos"};
	JLabel infassociacao = new JLabel("Clique duas vezes sobre o sintoma para adiciona-lo ou removê-lo");
	JLabel informacaoAssoc = new JLabel("Atenção: Para todos os sintomas não associados, será considerado Evidência Favorável 0.00 e Evidência Contrária 1.00,");
	JLabel informacaoAssoc2 = new JLabel("desconsiderando assim, a existencia do sintoma na doença.");
	public boolean doencaSelecionada = false;
	
	//Criando Compenentes - TELA DE CADASTRO DE EVIDENCIA FAVORAVEL E EVIDENCIA CONTRARIA - ASSOCIAÇÃO DOENÇAS E SINTOMAS
	JPanel cadEvidencia = new JPanel(null);
	public JLabel inddoenca = new JLabel();
	JLabel indespecialista = new JLabel("Especialista");
	public JComboBox especialista = new JComboBox();
	public JTextArea campoEspecialista = new JTextArea("Nome especialista");
	JButton adicionarEspec = new JButton("Adicionar Novo Especialista");	
	JButton salvarValores = new JButton("Salvar");
	public JTable tabelaAssociacao;
	public DefaultTableModel modeloTabelaAssociacao;
	JScrollPane rolagemTabelaAssociacao;
	public String dados5[][] = {};
	public String colunas5[]={"Sintomas","Evidencia Favorável","Evidencia Contrária"};
	public MaskFormatter mascara = null;
	public ArrayList sintomas = new ArrayList();
	public ArrayList evidenciasFav = new ArrayList();
	public ArrayList evidenciasCont = new ArrayList();
	public JFormattedTextField formato;
	// Componente para criar os componentes da guia de cadastros.
	public Component TelaCadastro(){ 
		ControleTela controle = new ControleTela();
		//para adicionar dentro da guia, preciso de um component
		//adicionando componentes nos painéis
		//Tabela doenças pendentes
		//Bloqueando tabela contra edição
		modeloDoencasPendentes = new DefaultTableModel(dados4, colunas4){
			public boolean isCellEditable(int linha, int coluna) {  
			    return false;  
			}
		};
		doencasPendentes = new JTable(modeloDoencasPendentes);
		doencasPendentes.setBounds(1, 1, 260, 300);
		pendencias.add(doencasPendentes);
		rolagemDoencasPendentes = new JScrollPane(doencasPendentes);
		rolagemDoencasPendentes.setBounds(doencasPendentes.getBounds());
		doencasPendentes.setFillsViewportHeight(true);
		pendencias.add(rolagemDoencasPendentes);
		cadastro.add(cadastros);
		cadastro.add(pendencias);
		cadastros.add(bNovaDoenca);
		cadastros.add(bEditarDoenca);
		cadastros.add(bNovoSintoma);
		cadastros.add(bEditarSintoma);
		cadastros.add(bAssociacoesSxD);
		controle.tabelaDoencasPendentes(this);	
		JLabel retorno = new JLabel();
		try{
		//DETECTAR DUPLO CLIQUE NA TABELA DE DOENÇAS PENDENTES
		doencasPendentes.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){	
				if(e.getClickCount() == 2){
					if(construidoCad >= 1){
						atualizarTelaEvidencias();
					}
					else{
					cadastroEvidencia();
					}
				}
			}
		});
		}catch(Exception ex){
			System.out.println("Não foi possível clicar da doença em questão.");
		}
		return retorno;	//retornando algo para o componente
	}
	// Componente para criar os componentes da guia de cadastro do paciente.
	public Component TelaPaciente(){ 
		paciente.add(cadprinc);
		cadprinc.setBounds(1, 1, 265, 600);
		cadprinc.add(bcadPaciente);
		return cadprinc.add(retorno); // variável(componente) para retorno do método
	}
	// Componente para criar os componentes da guia de analise médica.
	public Component TelaAnalise(){
		analise.add(analiseprinc);
		analiseprinc.setBounds(0,0,800,500);		
		//adicionando tabela na tela principal
		modeloAnalise = new DefaultTableModel(dadosPA, colunasPA){//bloquear edição de valores da tabela
		public boolean isCellEditable(int linha, int coluna) {  
		    return false;  
		}
		};
		tabelaPacienteAnalise = new JTable(modeloAnalise);
		tabelaPacienteAnalise.setBounds(10, 10, 300, 300);
		analiseprinc.add(tabelaPacienteAnalise);
		rolagemPA = new JScrollPane(tabelaPacienteAnalise);
		rolagemPA.setBounds(tabelaPacienteAnalise.getBounds());
		tabelaPacienteAnalise.setFillsViewportHeight(true);
		analiseprinc.add(rolagemPA);
		//DETECTAR DUPLO CLIQUE NA TABELA DE PACIENTES
		tabelaPacienteAnalise.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){	
				if(e.getClickCount() == 2){
					if(construidoAnalise < 1){
						analisePaciente();
					}
					else{
						AtualizarTelaAnalise();
					}
				}
			}
		});
		JLabel retorno = new JLabel(); // retornando um componente
		controle.carregarPacientes(this);
		return retorno; // variável(componente) para retorno do método		
	}
	// Método para criar a tela de análise do paciente.
	public void analisePaciente(){
				//para adicionar dentro da guia, preciso de um component
				//adicionando componentes na tela
				//TABELA DE SINTOMAS DO PACIENTE
				construidoAnalise = 1;
				this.dadosGrafico = new DefaultCategoryDataset();
				modeloTabelaPS = new DefaultTableModel(dadosPS, colunasPS){
					public boolean isCellEditable(int linha, int coluna) {  
					    return false;  
					}
				};
				tabelaPS = new JTable(modeloTabelaPS);
				tabelaPS.setBounds(20,70, 360, 250);
				analisecad.add(tabelaPS);
				rolagemPS = new JScrollPane(tabelaPS);
				rolagemPS.setBounds(tabelaPS.getBounds());
				tabelaPS.setFillsViewportHeight(true);
				analisecad.add(rolagemPS);
				//TABELA DE DOENÇAS DO PACIENTE
				modeloTabelaPD = new DefaultTableModel(dadosPD, colunasPD){
					public boolean isCellEditable(int linha, int coluna) {  
					    return false;  
					}
				};
				tabelaPD = new JTable(modeloTabelaPD);
				tabelaPD.setBounds(420,70, 360, 250);
				analisecad.add(tabelaPD);
				rolagemPD = new JScrollPane(tabelaPD);
				rolagemPD.setBounds(tabelaPD.getBounds());
				tabelaPD.setFillsViewportHeight(true);
				analisecad.add(rolagemPD);
				analisecad.add(indIdAna);
				analisecad.add(idPaciente);
				analisecad.add(indpacienteAna);
				analisecad.add(nomePaciente);
				analisecad.add(indidadeAna);
				analisecad.add(idadePaciente);
				analisecad.add(bcomentario);
				analisecad.add(bgrafico);
				analisecad.add(bvoltar);
				idPaciente.setEditable(false);
				nomePaciente.setEditable(false);
				idadePaciente.setEditable(false);
				//orientando componentes na tela
				indIdAna.setBounds(20, 30, 50, 30);
				idPaciente.setBounds(40, 30, 50, 30);
				indpacienteAna.setBounds(120,30,60,30);
				nomePaciente.setBounds(180, 30, 350, 30);
				indidadeAna.setBounds(560, 30, 80, 30);
				idadePaciente.setBounds(600,30,60,30);
				bcomentario.setBounds(20, 330, 200, 30);
				bgrafico.setBounds(560,330,200,30);
				bvoltar.setBounds(670,410,100,30);
				analisecad.setBounds(0, 0, 800, 500);
				controle.carregarAnalisePaciente(this);
				analise.removeAll();
				analise.add(analisecad);
				analise.updateUI();
	}
	// Método para atualizar tela de análise do paciente.
	public void AtualizarTelaAnalise(){
		controle.carregarAnalisePaciente(this);
		this.analise.removeAll();
		this.analise.add(analisecad);
		this.tabelaPS.updateUI();
		this.tabelaPD.updateUI();
		this.analise.updateUI();
	}
	//Método para criar a tela de cadastro do paciente.
	public void CadastroPaciente(){
		modelo = new DefaultTableModel(dados, colunas);
		tabelaSintomaPaciente = new JTable (modelo){
			//bloquear edição de valores da tabela
			public boolean isCellEditable(int linha, int coluna) {  
			    return false;  
			}
		};
		tabelaSintomaPaciente.setBounds(20, 130, 250, 250);
		cadpaciente.add(tabelaSintomaPaciente);
		rolagem =new JScrollPane(tabelaSintomaPaciente);
		rolagem.setBounds(tabelaSintomaPaciente.getBounds());
		tabelaSintomaPaciente.setFillsViewportHeight(true);
		//para adicionar dentro da guia, preciso de um component
		//adicionando componentes nos painéis
		cadpaciente.add(indpaciente);
		cadpaciente.add(indsintoma);
		cadpaciente.add(indidade);
		cadpaciente.add(indcoment);
		cadpaciente.add(nome);
		cadpaciente.add(idade);
		cadpaciente.add(comentario);
		cadpaciente.add(badicionar);
		cadpaciente.add(sintomasel);
		cadpaciente.add(benviar);
		cadpaciente.add(bcancelar);
		cadpaciente.add(rolagem);
		cadpaciente.add(bremover);
		//orientando componentes na tela
		indpaciente.setBounds(20, 30, 60, 30);
		nome.setBounds(80, 30, 350, 30);
		indidade.setBounds(470,30,60,30);
		idade.setBounds(510, 30, 60, 30);
		indsintoma.setBounds(20, 60, 60, 30);
		sintomasel.setBounds(20, 90,200,30);
		badicionar.setBounds(230, 90, 100, 30);
		bremover.setBounds(340, 90, 100, 30);
		indcoment.setBounds(470, 60, 90, 30);
		comentario.setBounds(470, 90, 300, 300);
		benviar.setBounds(560,410,100,30);
		bcancelar.setBounds(670,410,100,30);
	}
	//Método para criar tela de cadastro de doença no sistema.
	public void CadastroDoenca(){
		caddoenca.add(indCadDoenca);
		caddoenca.add(campodoenca);
		caddoenca.add(indCadDetalhe);
		caddoenca.add(campodoencadesc);
		caddoenca.add(salvardoenca);
		caddoenca.add(cancelar);
		indCadDoenca.setBounds(20, 30, 200, 30);
		campodoenca.setBounds(20,70, 300, 30);
		indCadDetalhe.setBounds(20, 110, 200, 30);
		campodoencadesc.setBounds(20,150, 300, 250);
		salvardoenca.setBounds(560,410,100,30);
		cancelar.setBounds(670,410,100,30);
	}
	//Método para criar tela de cadastro de sintomas no sistema
	public void CadastroSintoma(){ 
		cadSintoma.add(indCadSintoma);
		cadSintoma.add(camposintoma);
		cadSintoma.add(indCadDetalheSintoma);
		cadSintoma.add(camposintomadesc);
		cadSintoma.add(salvarSintoma);
		cadSintoma.add(cancelar);
		indCadSintoma.setBounds(20, 30, 200, 30);
		camposintoma.setBounds(20,70, 300, 30);
		indCadDetalheSintoma.setBounds(20, 110, 200, 30);
		camposintomadesc.setBounds(20,150, 300, 250);
		salvarSintoma.setBounds(560,410,100,30);
		cancelar.setBounds(670,410,100,30);
	}
	//Método para criar tela de edição de doenças no sistema.
	public void EdicaoDoenca(){
		//adicionando componentes na tela
		edtDoenca.add(indSelecionarDoenca);
		edtDoenca.add(doenca);
		edtDoenca.add(selecionar);
		edtDoenca.add(indCadDoenca);
		edtDoenca.add(campodoenca);
		edtDoenca.add(indCadDetalhe);
		edtDoenca.add(campodoencadesc);
		edtDoenca.add(salvarEdtDoenca);
		edtDoenca.add(excluirDoenca);
		edtDoenca.add(cancelar);
		//orientando componentes na tela
		indSelecionarDoenca.setBounds(20, 30, 140, 30);
		doenca.setBounds(190,30, 200, 30);
		selecionar.setBounds(410,30, 100, 30);
		indCadDoenca.setBounds(20, 70, 100, 30);
		campodoenca.setBounds(190,70, 320, 30);
		indCadDetalhe.setBounds(20, 110, 160, 30);
		campodoencadesc.setBounds(190, 100, 320, 250);
		salvarEdtDoenca.setBounds(340,410,140,30);
		excluirDoenca.setBounds(490,410,140,30);
		cancelar.setBounds(640,410,140,30);	
	}
	//Método para criar tela de edição de sintomas no sistema.
	public void EdicaoSintoma(){
		//adicionando componentes na tela
		edtSintoma.add(indSelecionarSintoma);
		edtSintoma.add(sintoma);
		edtSintoma.add(selecionarSintoma);
		edtSintoma.add(indCadSintoma);
		edtSintoma.add(camposintoma);
		edtSintoma.add(indCadDetalheSintoma);
		edtSintoma.add(camposintomadesc);
		edtSintoma.add(salvarEdtSintoma);
		edtSintoma.add(excluirSintoma);
		edtSintoma.add(cancelar);
		//orientando componentes na tela
		indSelecionarSintoma.setBounds(20, 30, 140, 30);
		sintoma.setBounds(190,30, 200, 30);
		selecionarSintoma.setBounds(410,30, 100, 30);
		indCadSintoma.setBounds(20, 70, 150, 30);
		camposintoma.setBounds(190,70, 320, 30);
		indCadDetalheSintoma.setBounds(20, 110, 160, 30);
		camposintomadesc.setBounds(190, 100, 320, 250);
		salvarEdtSintoma.setBounds(340,410,140,30);
		excluirSintoma.setBounds(490,410,140,30);
		cancelar.setBounds(640,410,140,30);	
	}
	
	
	//Método para construção da tela de ASSOCIAÇÃO DE DOENCAS E SINTOMAS
	public void AssociacoesSintomaDoenca(){
		ModeloSintomasDisponiveis = new DefaultTableModel(dados2, colunas2);
		sintomasDisponiveis = new JTable (ModeloSintomasDisponiveis){
			//bloquear edição de valores da tabela
			public boolean isCellEditable(int linha, int coluna) {  
			    return false;  
			}
		};	
		sintomasDisponiveis.setBounds(20, 100, 250, 250);
		associacoes.add(sintomasDisponiveis);
		rolagemSintoma = new JScrollPane(sintomasDisponiveis);
		rolagemSintoma.setBounds(sintomasDisponiveis.getBounds());
		sintomasDisponiveis.setFillsViewportHeight(true);
		//adicionando tabela que contemplará os sintomas escolhidos
		ModeloSintomasEscolhidos = new DefaultTableModel(dados3, colunas3);
		sintomasEscolhidos = new JTable (ModeloSintomasEscolhidos){
			//realizar bloqueio de celulas para edição de valores
			public boolean isCellEditable(int linha, int coluna){
				return false;
			}
		};
	//adicionando evento de dois clique na tabela sintomas disponíveis
		sintomasDisponiveis.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){	
				if(e.getClickCount() == 2){
					AdicionandoSintomasEscolhidos();
				}
			}
		});
		sintomasEscolhidos.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){	
				if(e.getClickCount() == 2){
					RemovendoSintomasEscolhidos();
				}
			}
		});
		sintomasEscolhidos.setBounds(300,100,250,250);
		associacoes.add(sintomasEscolhidos);
		rolagemSintEscolhidos = new JScrollPane(sintomasEscolhidos);
		rolagemSintEscolhidos.setBounds(sintomasEscolhidos.getBounds());
		sintomasEscolhidos.setFillsViewportHeight(true);	
		associacoes.add(doencaAss);
		associacoes.add(doencaAssociada);
		associacoes.add(confirmaDoenca);
		associacoes.add(cancelaDoenca);
		associacoes.add(salvarAssociacoes);
		associacoes.add(cancelar);
		associacoes.add(rolagemSintoma);
		associacoes.add(rolagemSintEscolhidos);
		associacoes.add(sintomaAss);
		associacoes.add(limpar);
		associacoes.add(infassociacao);
		associacoes.add(informacaoAssoc);
		associacoes.add(informacaoAssoc2);
		doencaAss.setBounds(20, 30, 60, 30);
		doencaAssociada.setBounds(70, 30, 200, 30);
		confirmaDoenca.setBounds(280, 30, 150, 30);
		cancelaDoenca.setBounds(440, 30, 150, 30);
		sintomaAss.setBounds(20, 70,500,30);
		salvarAssociacoes.setBounds(450,410,150,30);
		cancelar.setBounds(620,410,150,30);
		limpar.setBounds(280,410,150,30);
		infassociacao.setBounds(20, 340, 500, 50);
		informacaoAssoc.setBounds(20, 360, 800, 50);
		informacaoAssoc2.setBounds(20, 380, 800, 50);
		
	}
	// Este método tem por objetivo adicionar os sintomas escolhidos pelo usuário na tabela de sintomas escolhidos
	public void AdicionandoSintomasEscolhidos(){
		ArrayList sintomasIncluidos = new ArrayList();
		String valor = this.ModeloSintomasDisponiveis.getValueAt(this.sintomasDisponiveis.getSelectedRow(), this.sintomasDisponiveis.getSelectedColumn()).toString();
		for(int i = 0; i<sintomasEscolhidos.getRowCount();i++){
			sintomasIncluidos.add(sintomasEscolhidos.getValueAt(i, 0));
		}
		if(sintomasIncluidos.contains(valor)){
			JOptionPane.showMessageDialog(null, "O sintoma já foi escolhido.");
		}
		else
		{
			this.ModeloSintomasEscolhidos.addRow(new String[] {valor});
		}
		
		valor = null;
	}
	// Este método realiza a remoção dos sintomas escolhidos pelo usuário
	public void RemovendoSintomasEscolhidos(){
	try{
		((DefaultTableModel) sintomasEscolhidos.getModel()).removeRow(sintomasEscolhidos.getSelectedRow());
	}catch(Exception e){
		System.out.println("Não foi possível remover o sintoma da tabela - Verificar exceção");
	}
	}
	//construção da tela de cadastro de associação
	public void cadastroEvidencia(){
		construidoCad = 1;
		modeloTabelaAssociacao = new DefaultTableModel(dados5, colunas5);
		tabelaAssociacao = new JTable(modeloTabelaAssociacao){
		};
		tabelaAssociacao.setRowHeight(30
				);
		tabelaAssociacao.setCellSelectionEnabled(true);
		tabelaAssociacao.setSelectionBackground(Color.LIGHT_GRAY);
		tabelaAssociacao.setBounds(20,100,500,300);		
		cadEvidencia.add(tabelaAssociacao);
		rolagemTabelaAssociacao = new JScrollPane(tabelaAssociacao);
		rolagemTabelaAssociacao.setBounds(tabelaAssociacao.getBounds());
		tabelaAssociacao.setFillsViewportHeight(true);
		String doenca = (String) this.modeloDoencasPendentes.getValueAt(this.doencasPendentes.getSelectedRow(),this.doencasPendentes.getSelectedColumn());
		inddoenca = new JLabel(doenca);
		inddoenca.setFont(new Font("Arial", Font.BOLD,20));
		this.iddoenca = 0;
		
		//FORMATANDO MASCARA PARA JTABLE
		try{
			mascara = new MaskFormatter("#.##");
			mascara.setPlaceholder("0");	
		}catch(Exception e){
			System.out.println("Não foi possível criar a mascara");
		}	
		formato = new JFormattedTextField(mascara);
		tabelaAssociacao.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(formato));
		tabelaAssociacao.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(formato));
		cadEvidencia.add(rolagemTabelaAssociacao);
		cadEvidencia.add(inddoenca);
		cadEvidencia.add(indespecialista);
		cadEvidencia.add(especialista);
		cadEvidencia.add(campoEspecialista);
		cadEvidencia.add(adicionarEspec);
		cadEvidencia.add(salvarValores);
		cadEvidencia.add(cancelar);
		inddoenca.setBounds(20, 20, 300, 30);
		indespecialista.setBounds(20, 50, 100, 30);
		especialista.setBounds(120, 50 , 150, 30);
		campoEspecialista.setBounds(280, 50 , 180, 30);
		adicionarEspec.setBounds(470, 50, 200, 30);
		salvarValores.setBounds(450,410,150,30);
		cancelar.setBounds(620,410,150,30);
		controle.preencherSintomas(this);		
		controle.preencherEspecialistas(this);
		this.especialista.setSelectedItem(null);
		this.cadastro.removeAll();
		this.cadastro.add(cadEvidencia); //adicionando novo painel ao jframe
		this.cadastro.updateUI(); // atualizando dinamicamente o jframe
	}
	//Método para atualização da tela de evidencias
	public void atualizarTelaEvidencias(){
		String doenca = "";
		doenca = (String) this.modeloDoencasPendentes.getValueAt(this.doencasPendentes.getSelectedRow(),this.doencasPendentes.getSelectedColumn());
		inddoenca = new JLabel(doenca);
		System.out.println("DOENCA= "+doenca);
		inddoenca.setFont(new Font("Arial", Font.BOLD,20));
		this.iddoenca = 0;
		controle.preencherSintomas(this);	
		this.especialista.removeAllItems();
		controle.preencherEspecialistas(this);
		this.especialista.setSelectedItem(null);
		this.cadastro.removeAll();
		this.cadEvidencia.add(cancelar);
		cancelar.setBounds(620,410,150,30);
		inddoenca.setBounds(20, 20, 300, 30);
		this.cadEvidencia.add(inddoenca);
		this.cadastro.add(cadEvidencia); //adicionando novo painel ao jframe
		tabelaAssociacao.updateUI();
		this.cadastro.updateUI();
	}
	//Tela principal do sistema
	public TelaPrincipal(){
		acessoInicial.setLayout(new BorderLayout());
		acessoInicial.setResizable(false);
		acessoInicial.setLocation(280,150);
		acessoInicial.setSize(500, 380);
		logo.setIcon(new ImageIcon("Medical Analysis/logoMedical.png"));
		perfil.add(logo, BorderLayout.NORTH);
		perfil.add(infAcesso, BorderLayout.SOUTH);
		botoes.add(bpreatendimento);
		botoes.add(bmedicoConsulta);
		botoes.add(bmedicoCadastro);
		bpreatendimento.setBounds(5, 15, 240, 40);
		bmedicoConsulta.setBounds(250, 15, 240, 40);
		bmedicoCadastro.setBounds(5, 55, 240, 40);
		acessoInicial.add(perfil, BorderLayout.NORTH);
		acessoInicial.add(botoes, BorderLayout.CENTER);
		//Sair da aplicação
		acessoInicial.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						System.exit(0);
					}
				});
		bpreatendimento.addActionListener(this);
		bmedicoConsulta.addActionListener(this);
		bmedicoCadastro.addActionListener(this);
	}
	//Tela após a abertura de um dos módulos
	public void Principal(){ 
		//detalhes da tela		
		telaPrincipal.setLayout(new BorderLayout());
		telaPrincipal.setResizable(false);
		telaPrincipal.setLocation(280,150);
		telaPrincipal.setSize(800,500);
		//orientação dos componentes na tela
		//adicionando componentes na tela
		abas.add(analise,"Análise");
		abas.add(cadastro,"Cadastro - Doenças e Sintomas");
		abas.add(paciente, "Cadastro Paciente");
		telaPrincipal.add(abas, BorderLayout.CENTER);
		cadastro.add(TelaCadastro()); // adicionando um método do tipo componente	
		paciente.add(TelaPaciente()); // adicionando um método do tipo componente 
		analise.add(TelaAnalise()); // adicionando um método do tipo componente	
		//adicionando detectores de ação
		bNovaDoenca.addActionListener(this);
		bEditarDoenca.addActionListener(this);
		bNovoSintoma.addActionListener(this);
		bEditarSintoma.addActionListener(this);
		cancelar.addActionListener(this);
		salvardoenca.addActionListener(this);
		salvarSintoma.addActionListener(this);
		salvarEdtDoenca.addActionListener(this);
		excluirDoenca.addActionListener(this);
		selecionar.addActionListener(this);
		selecionarSintoma.addActionListener(this);
		salvarEdtSintoma.addActionListener(this);
		excluirSintoma.addActionListener(this);
		benviar.addActionListener(this);
		bcadPaciente.addActionListener(this);
		bcancelar.addActionListener(this);
		badicionar.addActionListener(this);
		bremover.addActionListener(this);
		bAssociacoesSxD.addActionListener(this);
		salvarAssociacoes.addActionListener(this);
		cancelaDoenca.addActionListener(this);
		confirmaDoenca.addActionListener(this);
		limpar.addActionListener(this);
		salvarValores.addActionListener(this);
		adicionarEspec.addActionListener(this);
		bvoltar.addActionListener(this);
		bcomentario.addActionListener(this);
		bgrafico.addActionListener(this);
		bpreatendimento.addActionListener(this);
		bmedicoConsulta.addActionListener(this);
		bmedicoCadastro.addActionListener(this);
		//Fechar a tela do sistema
		telaPrincipal.addWindowListener(
				new WindowAdapter(){
					public void windowClosing(WindowEvent e){
						ReabrirTelaEscolha();
					}
				});
		
	}
	
	public void ReabrirTelaEscolha(){
		this.acessoInicial.setState(Frame.NORMAL);
	}
	
	//Método para execução de ações nos botões.
	public void actionPerformed(ActionEvent e) {		
	Object elemento = new Object();
	elemento = e.getSource();;
	//CADASTRAR UMA NOVA DOENCA
	if(elemento.equals(bNovaDoenca)){
		this.cadastro.removeAll();
		this.CadastroDoenca(); //construindo tela
		this.cadastro.add(caddoenca); //adicionando novo painel ao jframe
		this.cadastro.updateUI(); // atualizando dinamicamente o jframe
	}
	//CANCELAR TELA DE CADASTROS
	else if(elemento.equals(cancelar)){ // reconstrução da tela inicial de cadastros
		//limpando campos dos cadastros
		this.campodoenca.setText(null);
		this.camposintoma.setText(null);
		this.campodoencadesc.setText(null);
		this.camposintomadesc.setText(null);
		this.inddoenca.setText(null);
		//removendo elementos das telas
		cadastro.removeAll();
		//adicionando elementos
		cadastro.add(cadastros);
		cadastro.add(pendencias);
		cadastro.add(retorno);
		//limpando tabela de sintomas escolhidos - Tela de associações
		try{
		((DefaultTableModel) this.sintomasEscolhidos.getModel()).setNumRows(0);
		this.sintomasEscolhidos.updateUI();
		this.doencaAssociada.setEnabled(true);
		}catch(Exception ex){
			System.out.println("Não foi necessário remover os elementos pois a tabela sintomas escolhidos estava vazia.");
		}
		try{
			((DefaultTableModel) this.tabelaAssociacao.getModel()).setNumRows(0);
			tabelaAssociacao.updateUI();
		}catch(Exception ex){
			System.out.println("Não foi necessário remover os elementos pois a tabela associação estava vazia.");
		}
		try{
			((DefaultTableModel) this.sintomasDisponiveis.getModel()).setNumRows(0);
			this.sintomasDisponiveis.updateUI();
		}catch(Exception exc){
			System.out.println("Não foi necessário remover os elementos pois a tabela sintomas disponiveis estava vazia.");
		}
		
		
		this.cadastro.updateUI();
	}
	//CANCELAR TELA DE CADASTRO DE PACIENTES
	else if(elemento.equals(bcancelar)){
		this.paciente.remove(cadpaciente);
		this.paciente.add(cadprinc);
		//limpando variáveis
		this.nome.setText(null);
		this.idade.setText(null);
		this.comentario.setText(null);
		cadprinc.setBounds(1, 1, 265, 600);
		//limpando arraylist de sintomas
		this.sintomasinseridos.removeAll(sintomasinseridos);
		//limpando tabela de sintomas do paciente
		((DefaultTableModel) this.tabelaSintomaPaciente.getModel()).setNumRows(0);
		this.tabelaSintomaPaciente.updateUI();		
		this.paciente.updateUI();
	}
	
	//ABRIR TELA DE CADASTRO DE NOVO SINTOMA
	else if(elemento.equals(bNovoSintoma)){
		cadastro.removeAll();
		this.CadastroSintoma(); //construindo tela
		this.cadastro.add(cadSintoma); //adicionando novo painel ao jframe
		this.cadastro.updateUI(); // atualizando dinamicamente o jframe
	}
	//SALVAR A DOENCA
	else if(elemento.equals(salvardoenca)){
		controle.inserirDoenca(this);
		}
	//SALVAR O SINTOMA
	else if(elemento.equals(salvarSintoma)){
		controle.inserirSintoma(this); 
		}
	//EDIÇÃO DOENÇA
	else if(elemento.equals(bEditarDoenca)){
		this.cadastro.removeAll();
		this.EdicaoDoenca();
		this.cadastro.add(edtDoenca);
		controle.editarDoenca(this);
		this.cadastro.updateUI();
	}
	else if(elemento.equals(selecionar)){
		controle.selecionarDoenca(this);	
	}
	//SALVAR A EDIÇÃO DE UMA DOENCAA
	else if(elemento.equals(salvarEdtDoenca)){
		controle.salvarEdicaoDoenca(this);
	}
	//EXCLUIR DOENCA
	else if(elemento.equals(excluirDoenca)){
		controle.excluirDoenca(this);
		confirmarSelecao = false;
	}
	// EDIÇÃO SINTOMA
	else if(elemento.equals(bEditarSintoma)){
		this.cadastro.removeAll();
		this.EdicaoSintoma();
		this.cadastro.add(edtSintoma);
		controle.editarSintoma(this);
		this.cadastro.updateUI();
	}
	else if(elemento.equals(selecionarSintoma)){
		controle.selecionarSintoma(this);	
	}
	//SALVA A EDIÇÃO DE UM SINTOMA
	else if(elemento.equals(salvarEdtSintoma)){
		controle.salvarEdicaoSintoma(this);
		
	}
	//EXCLUI UM SINTOMA
	else if(elemento.equals(excluirSintoma)){
		controle.excluirSintoma(this);
		confirmarSelecao = false;
	}
	// ABRE O CADASTRO DO PACIENTE
	else if(elemento.equals(bcadPaciente)){
		this.paciente.remove(cadprinc);
		if(this.cadPaciConstruido != 1){
			cadPaciConstruido = 1;
			this.CadastroPaciente();
		}
		controle.editarSintoma(this); // popular combobox
		this.paciente.add(cadpaciente);
		cadpaciente.setBounds(0, 0, 800, 500);
		cadpaciente.updateUI();
		this.paciente.updateUI();
	}
	// ENVIA DADOS DO PACIENTE
	else if(elemento.equals(benviar)){
		boolean teste = controle.enviarPaciente(this);
		if(teste==true){
		((DefaultTableModel) this.tabelaSintomaPaciente.getModel()).setNumRows(0);
		this.sintomasinseridos.removeAll(sintomasinseridos);
		valsintomas.clear();
		this.paciente.remove(cadpaciente);
		this.paciente.add(cadprinc);
		this.nome.setText(null);
		this.idade.setText(null);
		this.comentario.setText(null);
		cadprinc.setBounds(1, 1, 265, 600);
		this.paciente.updateUI();
		}
	}
	//ADICIONA SINTOMAS NA TABELA DO PACIENTE
	else if (elemento.equals(badicionar)){
		controle.adicionarSintomaTabela(this);
	}
	//REMOVE OS SINTOMAS SELECIONADOS DA TABELA DE SINTOMAS DO PACIENTE
	else if(elemento.equals(bremover)){
		controle.removerSintomaTabela(this);
	}
	//ABRE A TELA DE ASSOCIAÇÃO DE SINTOMAS E DOEÇAS
	else if(elemento.equals(bAssociacoesSxD)){
		this.cadastro.removeAll();
		if(associacaoCriado != 1){
			associacaoCriado = 1;
		this.AssociacoesSintomaDoenca();
		}
		controle.PrepararTelaAssociacao(this);
		this.sintomasDisponiveis.updateUI();
		this.associacoes.add(cancelar);
		cancelar.setBounds(620,410,150,30);
		this.cadastro.add(associacoes);
		this.cadastro.updateUI();
	}
	//CONFIRMA A DOENCA ESCOLHIDA
	else if(elemento.equals(confirmaDoenca)){
		testeConfirmaDoenca = true;
		controle.verificaTelaAssociacao(this);
	}
	//CANCELA A DOENCA ESCOLHIDA
	else if(elemento.equals(cancelaDoenca)){
		testeConfirmaDoenca = false;
		controle.verificaTelaAssociacao(this);
	}
	//SALVA AS ASSOCIAÇÕES
	else if(elemento.equals(salvarAssociacoes)){
		controle.salvarAssociacoes(this);
		if(confirmar == true){
		controle.tabelaDoencasPendentes(this);
		cadastro.removeAll();
		cadastro.add(cadastros);
		cadastro.add(pendencias);
		cadastro.add(retorno);
		//limpando tabela de sintomas escolhidos - Tela de associações
		((DefaultTableModel) this.sintomasEscolhidos.getModel()).setNumRows(0);
		this.sintomasEscolhidos.updateUI();
		this.doencaAssociada.setEnabled(true);
		cadastro.updateUI();
		}
	}
	// LIMPA TODOS OS ELEMENTOS DA TABELA DE SINTOMAS ESCOLHIDOS
	else if(elemento.equals(limpar)){
		//limpando a tabela de sintomas escolhidos
		((DefaultTableModel)sintomasEscolhidos.getModel()).setNumRows(0);
		sintomasEscolhidos.updateUI();
	}	
	//Adicionar especialista
	else if(elemento.equals(this.adicionarEspec)){
		controle.inserirEspecialista(this);
		this.especialista.removeAllItems();
		controle.preencherEspecialistas(this);
	}
	//Salvar valores atribuidos
	else if(elemento.equals(this.salvarValores)){
		controle.salvarEvidencias(this);
		if(verificacao == true){
		this.inddoenca.setText(null);
		((DefaultTableModel) this.tabelaAssociacao.getModel()).setNumRows(0);
		this.tabelaAssociacao.updateUI();
		cadastro.removeAll();
		cadastro.add(cadastros);
		cadastro.add(pendencias);
		cadastro.add(retorno);
		cadastro.updateUI();
		}
		verificacao = true;
	}
	//Voltar para tela principal do cadastro de pacientes
	else if(elemento.equals(this.bvoltar)){
		analise.removeAll();
		analise.add(analiseprinc);
		modeloGrafico = null;
		plot = null;
		painelBarra = null;
		painelGrafico.removeAll();
		//LIMPANDO TABELAS DE ANÁLISE DO PACIENTE
		try{
		((DefaultTableModel) this.tabelaPS.getModel()).setNumRows(0);
		tabelaPS.updateUI();
		((DefaultTableModel) this.tabelaPD.getModel()).setNumRows(0);
		tabelaPD.updateUI();
		analise.updateUI();
		}catch(Exception ex){
			System.out.println("Não foi necessário limpar as tabelas de análise do paciente.");
		}
	}
	//Exibir comentários da enfermeira
	else if(elemento.equals(this.bcomentario)){
		controle.comentarioEnfermeira(this);
	}
	//Constrói o gráfico de possíveis doenças
	else if(elemento.equals(this.bgrafico)){
		modeloGrafico = ChartFactory.createBarChart(
				"Representação gráfica das possíveis doenças", 
				"Possíveis Doenças",
				"Valores de certeza e incerteza.",
				dadosGrafico,
				PlotOrientation.VERTICAL,
				true,
				true, 
				false);
		plot = modeloGrafico.getCategoryPlot();
		plot.setDomainGridlinePaint(Color.white);		
		plot.setRangeGridlinePaint(Color.white);	
		plot.setBackgroundPaint(Color.white);
		plot.setOutlineVisible(false);
		BarRenderer render = (BarRenderer) plot.getRenderer();
		render.setGradientPaintTransformer(null);
		render.setBarPainter(new StandardBarPainter());
		Paint[] cores = {new Color(85, 177, 69), new Color(255, 0, 0)};
		for(int i = 0; i< 3; i++){
		render.setSeriesPaint(i, cores[i % cores.length]);
	}
		painelBarra = new ChartPanel(modeloGrafico);
		painelGrafico.add(painelBarra);
		painelBarra.setBounds(10, 10, 760, 440);
		painelGrafico.setBackground(Color.white);
		this.grafico.add(painelGrafico);
		grafico.setLocation(280,150);
		grafico.setSize(800,500);
		this.grafico.setVisible(true);
		this.grafico.validate();
		painelGrafico.updateUI();
	}
	//Abrir módulo de pré-atendimento
	else if(elemento.equals(this.bpreatendimento)){
		if(telaConstruida == 0){
			telaConstruida = 1;
			this.acessoInicial.setState(Frame.ICONIFIED);
		Principal();
		}
		this.acessoInicial.setState(Frame.ICONIFIED);
		abas.setEnabledAt(0,false);
		abas.setEnabledAt(1, false);
		abas.setEnabledAt(2,true);
		abas.setSelectedIndex(2);
		this.telaPrincipal.setVisible(true);
	}
	//Abrir módulo de consulta médica
	else if(elemento.equals(this.bmedicoConsulta)){
		if(telaConstruida == 0){
			telaConstruida = 1;
			this.acessoInicial.setState(Frame.ICONIFIED);
		Principal();
		}
		this.acessoInicial.setState(Frame.ICONIFIED);
		abas.setEnabledAt(0,true);
		abas.setEnabledAt(1,false);
		abas.setEnabledAt(2, false);
		abas.setSelectedIndex(0);
		this.telaPrincipal.setVisible(true);
	}
	//Abrir módulo de cadastros médicos
	else if(elemento.equals(this.bmedicoCadastro)){
		if(telaConstruida == 0){
			telaConstruida = 1;
			this.acessoInicial.setState(Frame.ICONIFIED);
		Principal();
		}
		this.acessoInicial.setState(Frame.ICONIFIED);
		abas.setEnabledAt(0,false);
		abas.setEnabledAt(1,true);
		abas.setEnabledAt(2, false);
		abas.setSelectedIndex(1);
		this.telaPrincipal.setVisible(true);
		
	}

}
	//Método principal
	public static void main(String[] args){
	InterfaceGrafica IG = new InterfaceGrafica();
	IG.aplicaInterfaceNimbus();
	TelaPrincipal tela = new TelaPrincipal();
	tela.acessoInicial.setVisible(true);
	}
}
