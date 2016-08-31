package Controle;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import Modelo.ComandosBD;
import Modelo.ModeloDoenca;
import Modelo.ModeloEspecialista;
import Modelo.ModeloPaciente;
import Modelo.ModeloSintoma;
import Visao.TelaPrincipal;

public class ControleTela {
	ModeloSintoma sintoma = new ModeloSintoma(); // Instancia da classe ModeloSintoma - Pacote Modelo
	ModeloDoenca doenca = new ModeloDoenca(); // Instancia da classe ModeloDoenca - Pacote Modelo
	ComandosBD comBD = new ComandosBD(); // Instancia da classe ComandosBD - Pacote Controle
	boolean teste = false; // Variável para execução de testes de retorno do banco.
	//Este método realiza o controle dos elementos da tela para inserção de doenças no sistema.
	public void inserirDoenca(TelaPrincipal tela){
		if(tela.campodoenca.getText().equals("")||tela.campodoencadesc.getText().equals("")){ // verifica campo nulo.
			JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos para cadastro da doença. Por favor verifique e tente novamente.");
		}
		else{
			// Enviando valores para as classes do pacote Modelo
			doenca.setNomeDoenca(tela.campodoenca.getText());
			doenca.setDescricaoDoenca(tela.campodoencadesc.getText());
			teste = comBD.inserirDoencas(doenca); 
			if(teste = true){ // Verifica se a doença foi cadastrada.
			tela.campodoenca.setText(null);
			tela.campodoencadesc.setText(null);
			}
			else{// Retorna um erro informando o usuário que não foi possível realizar o cadastro com sucesso.
				JOptionPane.showMessageDialog(null, "Não foi possível inserir a doença, contate o administrador!");
			}
		}
	}
	//Este método realiza o controle dos elementos da tela para inserção de sintomas no sistema.
	public void inserirSintoma(TelaPrincipal tela){
		if(tela.camposintoma.getText().equals("")||tela.camposintomadesc.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos para cadastro do sintoma. Por favor verifique e tente novamente.");
		}
		else{
			// Enviando valores para as classes do pacote Modelos
			sintoma.setNomeSintoma(tela.camposintoma.getText());
			sintoma.setDescricaoSintoma(tela.camposintomadesc.getText());
			teste = comBD.inserirSintoma(sintoma); 
			if(teste = true){ // Verifica se o sintoma foi cadastrado.
			tela.camposintoma.setText(null);
			tela.camposintomadesc.setText(null);
			}
			else{// Retorna um erro informando o usuário que não foi possível realizar o cadastro com sucesso.
				JOptionPane.showMessageDialog(null, "Não foi possível inserir o sintoma, contate o administrador!");
			}
		}
	}
	// Médodo para edição de doenças.
	public void editarDoenca(TelaPrincipal tela){
	    ComandosBD comandos = new ComandosBD();
	    tela.doenca.removeAllItems();
	    comandos.PreencherOpcoesDoenca(tela);
	    tela.doenca.setSelectedItem(null);
	}
	//Selecionar doenca e preencher informações na tela
	public void selecionarDoenca(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();// instancia da classe ComandosBD.
		try{ // try para tratar a excessão caso o combobox esteja vazio
			if(tela.doenca.getSelectedItem().toString() == ""){//verifica se foi selecionado uma doença
			}
			else{// se foi escolhido doença realizar o preenchimento dos campos abaixo.
				String valor = tela.doenca.getSelectedItem().toString();
				String[] separa = valor.split(Pattern.quote("—"));
				String  nomedoenca = separa[1];
				int iddoenca = Integer.parseInt(separa[0]);	
				doenca.setNomeDoenca(nomedoenca);
				doenca.setCoddoenca(iddoenca);
				comandos.preencherEdicaoDoenca(doenca);
				tela.campodoenca.setText(doenca.getNomeDoenca());
				tela.campodoencadesc.setText(doenca.getDescricaoDoenca());
				
				tela.confirmarSelecao = true;
			}
			comandos.preencherEdicaoSintoma(sintoma);
			tela.camposintoma.setText(sintoma.getNomeSintoma());
			tela.camposintomadesc.setText(sintoma.getDescricaoSintoma());
			tela.confirmarSelecao = true;
			
		}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Por favor, escolha uma doença para seleção.");
			}
	}
	//Médoto para salvar a edição de uma doença
	public void salvarEdicaoDoenca(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		if(tela.confirmarSelecao== false){// Verificar se todos os campos foram preenchidos.
			JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos para edição da doença.");
		}
		else{//Realizar atualização de doença.
			doenca.setNomeDoenca(tela.campodoenca.getText());
			doenca.setDescricaoDoenca(tela.campodoencadesc.getText());
			teste = comandos.editarDoenca(doenca);
			if(teste = true){
				JOptionPane.showMessageDialog(null, "A doença foi atualizada com sucesso.");	
				tela.campodoenca.setText(null);
				tela.campodoencadesc.setText(null);
			    tela.doenca.removeAllItems();
			    comandos.PreencherOpcoesDoenca(tela);
			    tela.doenca.setSelectedItem(null);
			}
		}
	}
	//Método para verificar tela para exclusão da doença
	public void excluirDoenca(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		if(tela.confirmarSelecao == false){
			JOptionPane.showMessageDialog(null, "Para exclusão de uma doença, a doença deve ser selecionada anteriormente!");
		}
		else{
			teste = false;
			teste = comandos.excluirDoenca(tela,doenca);
			if(teste == true){
			tela.doenca.removeAllItems();
			comandos.PreencherOpcoesDoenca(tela);
		    tela.doenca.setSelectedItem(null);
			tela.campodoenca.setText(null);
			tela.campodoencadesc.setText(null);
			JOptionPane.showMessageDialog(null, "A exclusão da doenca "+doenca.getNomeDoenca()+" foi realizada com sucesso!");
			}
			else if(tela.confirmaExclusao == false){
				JOptionPane.showMessageDialog(null, "A doença "+doenca.getNomeDoenca()+" já foi associada a sintomas e não pode ser excluida. ");
			}
		}
	}
	//Método para edição de sintoma
	public void editarSintoma(TelaPrincipal tela){
	    ComandosBD comandos = new ComandosBD();
	    tela.sintoma.removeAllItems();
	    tela.sintomasel.removeAllItems();
	    comandos.PreencherOpcoesSintoma(tela);
	    tela.sintoma.setSelectedItem(null);
	    tela.sintomasel.setSelectedItem(null);

	}	
	//Método para selecionar sintoma na tela de edição
	public void selecionarSintoma(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		try{ // Try para tratar a excessão caso o combobox esteja vazio
			if(tela.sintoma.getSelectedItem().toString() == ""){				
			}
			else{
				String valor = tela.sintoma.getSelectedItem().toString();
				String[] separa = valor.split(Pattern.quote("—"));
				String  nomesintoma = separa[1];
				int idsintoma = Integer.parseInt(separa[0]);	
				sintoma.setNomeSintoma(nomesintoma);
				sintoma.setCodSintoma(idsintoma);
				comandos.preencherEdicaoSintoma(sintoma);
				tela.camposintoma.setText(sintoma.getNomeSintoma());
				tela.camposintomadesc.setText(sintoma.getDescricaoSintoma());
				tela.confirmarSelecao = true;
			}
		}catch(Exception e){
				JOptionPane.showMessageDialog(null, "Por favor, escolha um sintoma para seleção.");
			}
	}
	//Método para salvar edição de um sintoma
	public void salvarEdicaoSintoma(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		if(tela.confirmarSelecao== false){
			JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos para edição do sintoma.");
		}
		else{
			sintoma.setNomeSintoma(tela.camposintoma.getText());
			sintoma.setDescricaoSintoma(tela.camposintomadesc.getText());
			teste = comandos.editarSintoma(sintoma);
			PrepararTelaPaciente(tela);
			if(teste = true){
				JOptionPane.showMessageDialog(null, "O sintoma foi atualizada com sucesso.");	
				tela.camposintoma.setText(null);
				tela.camposintomadesc.setText(null);
			    tela.sintoma.removeAllItems();
			    comandos.PreencherOpcoesSintoma(tela);
			    tela.sintoma.setSelectedItem(null);
			}
		}
	}
	//Médoto para exclusão de um sintoma
	public void excluirSintoma(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		if(tela.confirmarSelecao== false){
			JOptionPane.showMessageDialog(null, "Para exclusão de um sintoma, o sintoma deve ser selecionado anteriormente!");
		}
		else{
			teste = false;
			teste = comandos.excluirSintoma(sintoma);
			if(teste = true)
			tela.sintoma.removeAllItems();
			comandos.PreencherOpcoesSintoma(tela);
		    tela.sintoma.setSelectedItem(null);
			tela.camposintoma.setText(null);
			tela.camposintomadesc.setText(null);
			//Atualizando informações da tela de cadastro do paciente
			PrepararTelaPaciente(tela);
			JOptionPane.showMessageDialog(null, "A exclusão do sintoma "+sintoma.getNomeSintoma()+" foi realizada com sucesso!");
		}
	}
	//Método prepara a tela de envio de pacientes
	public void PrepararTelaPaciente(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		tela.sintoma.removeAllItems();
		comandos.PreencherOpcoesSintoma(tela);
		tela.sintoma.setSelectedItem(null);
	}
	//Método adiciona os sintomas escolhidos na tabela de sintomas do paciente
	public void adicionarSintomaTabela(TelaPrincipal tela){
		ModeloSintoma sintoma = new ModeloSintoma();
		try{//tratamento de exceção para quando o usuário não selecionar um sintoma
		if(tela.sintomasel.getSelectedItem().toString() == ""){
		}
		else{
		String valor = tela.sintomasel.getSelectedItem().toString();
		String[] separa = valor.split(Pattern.quote("—"));
		String  nomesintoma = separa[1];
		int idsintoma = Integer.parseInt(separa[0]);
		
		if(tela.sintomasinseridos.contains(idsintoma)){
			JOptionPane.showMessageDialog(null, "O sintoma já foi selecionado!");
		}
			else{
			tela.sintomasinseridos.add(idsintoma);
		sintoma.setNomeSintoma(nomesintoma);
		sintoma.setCodSintoma(idsintoma);
		String escolha = tela.sintomasel.getSelectedItem().toString(); // jogando valor e variável		
		// adicionando nova linha na tabela
			
			tela.modelo = (DefaultTableModel) tela.tabelaSintomaPaciente.getModel();
			
			tela.modelo.addRow(new String[] {escolha});
			escolha = ""; // limpando variável.
			tela.sintomasel.requestFocus(); // pega o foco
			tela.tabelaSintomaPaciente.repaint();
			}
		}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "É necessário selecionar um sintoma para adicionar.");
		}	
	}
	//Método remove os sintomas escolhidos da tabela do paciente.
	public void removerSintomaTabela(TelaPrincipal tela){ // removendo item selecionado da tabela
		try{
			String valor = (String)tela.tabelaSintomaPaciente.getValueAt(tela.tabelaSintomaPaciente.getSelectedRow(), 0);
			String[] separa = valor.split(Pattern.quote("—"));
			String  nomesintoma = separa[1];
			int idsintoma = Integer.parseInt(separa[0]);
			((DefaultTableModel) tela.tabelaSintomaPaciente.getModel()).removeRow(tela.tabelaSintomaPaciente.getSelectedRow());
			int index = tela.sintomasinseridos.indexOf(idsintoma);
			tela.sintomasinseridos.remove(index);
			for(int i = 0; i<tela.sintomasinseridos.size(); i++){
				System.out.println("Na lista"+tela.sintomasinseridos.get(i));
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Para remover um sintoma, é necessário selecionar na tabela.");			
		}	
	}
	//Método prepara a tela para enviar o paciente	
	public boolean enviarPaciente(TelaPrincipal tela){	
		ComandosBD comandos = new ComandosBD();
		ModeloPaciente paciente = new ModeloPaciente();

		teste = true;
		if(tela.nome.getText().equals("")){
			JOptionPane.showMessageDialog(null, "Por favor, preencha o campo nome para prosseguir com o envio do paciente.");
			teste = false;
		}
		else if(tela.idade.getText().equals(""))
		{
		try{
		Integer.parseInt(tela.idade.getText());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "O campo idade deve ser preenchido numéricamente.");
			tela.idade.setText(null);
			teste = false;
		}
		}
		else if(tela.tabelaSintomaPaciente.getRowCount()==0){
			JOptionPane.showMessageDialog(null, "Por favor, insira pelo menos um sintoma apresentado para o paciente.");
			teste = false;
		}
		else{
			try{
				Integer.parseInt(tela.idade.getText());
				paciente.setNomePaciente(tela.nome.getText());
				paciente.setIdadePaciente(Integer.parseInt(tela.idade.getText()));
				paciente.setComentario(tela.comentario.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "O campo idade deve ser preenchido numéricamente.");
					tela.idade.setText(null);
					teste = false;
				}
		}
		if(teste == true){
			for(int inicia = 0; inicia < tela.tabelaSintomaPaciente.getRowCount(); inicia++)
				{
				String valor = tela.modelo.getValueAt(inicia, 0).toString();
				String[] separa = valor.split(Pattern.quote("—"));
				int idsintoma = Integer.parseInt(separa[0]);	
				tela.valsintomas.add(idsintoma); // guarda Ids
				}
			teste = comandos.enviarPaciente(tela, paciente);	
				if(teste == true){
				aplicacaoMPD(tela, paciente);
				JOptionPane.showMessageDialog(null, "O Paciente foi cadastrado com sucesso!");
				tela.valsintomas.clear();
			}
		}

		return teste;
	}
	//Este método prepara a tela de associação de Sintomas e Doenças. 
	public void PrepararTelaAssociacao(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		tela.associacao = 1;
		tela.doencaAssociada.removeAllItems();
		comandos.PreencherOpcoesDoenca(tela);
		comandos.PreencherOpcoesSintoma(tela);
		comandos.PreencherTabelaSintomas(tela);
		tela.sintoma.setSelectedItem(null);	
		// adicionando nova linha na tabela
		tela.ModeloSintomasDisponiveis = (DefaultTableModel) tela.sintomasDisponiveis.getModel(); // modelo
		for(int i = 0; i<tela.todosSintomas.size();i++){ // preencher tabela de sintomas
			tela.ModeloSintomasDisponiveis.addRow(new String[] {tela.todosSintomas.get(i)});
			System.out.println(tela.todosSintomas.get(i));
		}
		tela.todosSintomas.clear();
		tela.doencaAssociada.setSelectedItem(null);
	}
	//Método verifica a doença na tela de associação antes de realizar o salvamento.
	public void verificaTelaAssociacao(TelaPrincipal tela){
		ModeloDoenca doenca = new ModeloDoenca();
		try{
		if(tela.doencaAssociada.getSelectedItem().equals(null)){
		}
		else
		{
			if(tela.testeConfirmaDoenca == true){
		// pega ID da doença que é selecionada
		String[] idDoencaAssociada = tela.doencaAssociada.getSelectedItem().toString().split(Pattern.quote("—"));
		doenca.setCoddoenca(Integer.parseInt(idDoencaAssociada[0]));//convertendo o ID e colocando no JavaBean
		idDoencaAssociada[0] = null;//zerando vetor
		tela.doencaAssociada.setEnabled(false);
		tela.doencaSelecionada = true;
			}
			else{
				tela.doencaAssociada.setEnabled(true);//habilitando combobox
				tela.doencaSelecionada = false;
			}
		}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "É necessário selecionar uma doença para confirma-la ou cancela-la.");
		}
	}
	//Método responsável por verificar a tela de associação antes de realizar o salvamento.
	public void salvarAssociacoes(TelaPrincipal tela){

		ComandosBD comandos = new ComandosBD();
		ModeloDoenca doenca = new ModeloDoenca();
		
		if((tela.doencaSelecionada == false) && (tela.sintomasEscolhidos.getRowCount() == 0)){
			JOptionPane.showMessageDialog(null, "É necessário preencher as informações antes de salvar a associação.");
			tela.confirmar = false;
		}
		else if(tela.doencaSelecionada == false){
			JOptionPane.showMessageDialog(null, "É necessário escolher uma doença para salvar a associação.");
			tela.confirmar = false;
		}
		else if(tela.sintomasEscolhidos.getRowCount() == 0){
			JOptionPane.showMessageDialog(null, "É necessário escolher os sintomas que serão associados à doença antes de salvar a associação.");
			tela.confirmar = false;
		}
		else{
			// separa ID do Nome e armazena na variável da classe ModeloDoenca
			String doencaEscolhida = tela.doencaAssociada.getSelectedItem().toString();
			String[] separa = doencaEscolhida.split(Pattern.quote("—"));
			doenca.setCoddoenca(Integer.parseInt(separa[0]));
			//pega todos os sintomas escolhidos para associa-los  e guarda em variáveis para jogar no banco
			for(int inicia = 0; inicia< tela.sintomasEscolhidos.getRowCount(); inicia++){
			String sintoma = tela.sintomasEscolhidos.getValueAt(inicia, 0).toString();
			String[] separar = sintoma.split(Pattern.quote("—"));
			int idSintoma = Integer.parseInt(separar[0]);
			tela.valsintomas.add(idSintoma);
			}	
			comandos.SalvarAssociacao(tela, doenca);
			tela.valsintomas.clear();
		}
			if(tela.confirmar == true){
				JOptionPane.showMessageDialog(null, "As associações foram realizadas com sucesso.");
			}
	}
	//Método responsável por limpar tabela de doenças pendentes para atualização
	public void tabelaDoencasPendentes(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		try{
		((DefaultTableModel) tela.doencasPendentes.getModel()).setNumRows(0);
		tela.doencasPendentes.updateUI();
		}catch(Exception ex){
			System.out.println("Não foi necessário remover os elementos pois a tabela doencas pendentes está vazia.");
		}
		comandos.preencherDoencasPendentes(tela);
	}
	//Método resposável por preencher os sintomas na tabela associação.
	public void preencherSintomas(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		String doenca = "";
		doenca = tela.inddoenca.getText();
		String [] separa = doenca.split(Pattern.quote("—"));
		tela.iddoenca = Integer.parseInt(separa[0]);
		separa = null;
		comandos.preencherSintomas(tela);
	}
	//Método para inserir especialista no momento da associação.
	public void inserirEspecialista(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		if(tela.campoEspecialista.getText().equals(null)||tela.campoEspecialista.getText().equals("Nome especialista")){
			JOptionPane.showMessageDialog(null, "É necessário inserir um nome no campo para adicionar o especialista.");
		}
		else{
			boolean teste = comandos.inserirEspecialista(tela);
			if(teste == true){
				JOptionPane.showMessageDialog(null, "O especialista "+tela.campoEspecialista.getText()+" foi criado com sucesso.");
			}
		}
	}
	//Método para preencher combo de especialistas para tela de associação.
	public void preencherEspecialistas(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		comandos.preencherComboEspecialista(tela);
	}
	//Método para verificar os valores de evidências e especialistas antes de salvar as evidências
	public void salvarEvidencias(TelaPrincipal tela){
		ModeloEspecialista espec = new ModeloEspecialista();
		ModeloDoenca doen = new ModeloDoenca();
		ComandosBD comandos = new ComandosBD();
		boolean retorno = false;
		//Verificar se alguma celula está vazia
		//garantir que todos os valores da tabela estão de acordo com 
		if(tela.verificacao == true){
		for(int inicia = 0; inicia < tela.tabelaAssociacao.getRowCount(); inicia ++){
			if(tela.tabelaAssociacao.getValueAt(inicia, 0).equals(" . ")||tela.tabelaAssociacao.getValueAt(inicia, 0).equals("")){
				JOptionPane.showMessageDialog(null, "É necessário preencher todos os valores de Evidência Favorável.");
				tela.verificacao = false;
				break;
			}
			else if(tela.tabelaAssociacao.getValueAt(inicia, 1).equals(" . ")||tela.tabelaAssociacao.getValueAt(inicia, 1).equals("")){
				JOptionPane.showMessageDialog(null, "É necessário preencher todos os valores de Evidência Contrária.");
				tela.verificacao = false;
				break;
			}
			if(Float.parseFloat((String)tela.tabelaAssociacao.getValueAt(inicia, 1)) >= 1.01){
				JOptionPane.showMessageDialog(null, "Os valores de Evidência Favorável devem estar entre 0.00 e 1.00");
				tela.verificacao = false;
				break;
			}
			else if(Float.parseFloat((String)tela.tabelaAssociacao.getValueAt(inicia, 2)) >= 1.01){
				JOptionPane.showMessageDialog(null, "Os valores de Evidência Contrária devem estar entre 0.00 e 1.00");
				tela.verificacao = false;
				break;
			}
			
			else if(tela.especialista.getSelectedIndex() == -1){
				JOptionPane.showMessageDialog(null, "É necessário escolher um especialista na lista de opções disponíveis.");
				tela.verificacao = false;
				break;
			}
		}
		//colocando valores nos array lists
		if(tela.verificacao == true){
			for(int i = 0; i <tela.tabelaAssociacao.getRowCount();i++){
				String sintoma = tela.tabelaAssociacao.getValueAt(i, 0).toString();
				String[] separar = sintoma.split(Pattern.quote("—"));
				//pegando valores de evidência favorável, evidência contrária e Idsintomas
				tela.sintomas.add(Integer.parseInt(separar[0]));
				tela.evidenciasFav.add(tela.tabelaAssociacao.getValueAt(i, 1));
				tela.evidenciasCont.add(tela.tabelaAssociacao.getValueAt(i, 2));
				separar = null;
			}
			//ID especislista
			String especialista = tela.especialista.getSelectedItem().toString();
			String[] separarEspe = especialista.split(Pattern.quote("—"));
			espec.setCodespecialista(Integer.parseInt(separarEspe[0]));	
			//ID Doença
			separarEspe = null;
			String doenca = tela.inddoenca.getText();
			String[] separarDoenca = doenca.split(Pattern.quote("—"));
			doen.setCoddoenca(Integer.parseInt(separarDoenca[0]));
			separarDoenca = null;
			//método para entrada no banco de dados
			
			retorno = comandos.verificaEspecialistaAssociado(tela, espec, doen);
			if(retorno == true){
			retorno = comandos.verificarEvidencias(tela, espec, doen);
			this.tabelaDoencasPendentes(tela);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "É necessário escolher outro especialista, pois o especialista escolhido já evidênciou valores sobre a associação.");
				tela.verificacao = false;
			}
				}
			}
					if(retorno == true){
			JOptionPane.showMessageDialog(null, "As evidências foram cadastradas com sucesso.");
		}		
	}
	//Método para carregar pacientes para incluí-los na tela de análise médica.
	public void carregarPacientes(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		comandos.carregarPacientes(tela);
	}
	//Método exibe o coment´[ario da enfermeira
	public void comentarioEnfermeira(TelaPrincipal tela){
		JOptionPane.showMessageDialog(null, tela.comentarioEnfer);
	}
	//Método que aplica o MPD (Lógica paraconsistente) através de uma Thread no momento da inclusão do paciente
	public void aplicacaoMPD(TelaPrincipal tela, ModeloPaciente paci){
		new Thread(){//THREAD PARA EXECUÇÃO EM PARALELO
		public void run(){
			ComandosBD comandos = new ComandosBD();
			comandos.calculosLogicaParaconsistente(tela, paci);
			// ADICIONANDO O PACIENTE NA TABELA DE ANÁLISE
			tela.modeloAnalise = (DefaultTableModel) tela.tabelaPacienteAnalise.getModel();
			tela.modeloAnalise.addRow(new String[] {paci.getCodPaciente()+"—"+paci.getNomePaciente()}); 
			}
		}.start();
	}
	//Método carrega as informações da tela de análise do paciente.
	public void carregarAnalisePaciente(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		ModeloPaciente paci = new ModeloPaciente();
		String paciente = (String) tela.tabelaPacienteAnalise.getValueAt(tela.tabelaPacienteAnalise.getSelectedRow(), 0);
		//ID Paciente
		String[] separarPaciente = paciente.split(Pattern.quote("—"));
		paci.setCodPaciente((Integer.parseInt(separarPaciente[0])));	
		comandos.carregarAnalisePaciente(tela, paci);
		//ADICIONANDO SINTOMAS NA TABELA DE SINTOMAS
		tela.modeloTabelaPS = (DefaultTableModel) tela.tabelaPS.getModel(); // modelo
		for(int i = 0; i<tela.sintomasPaciente.size();i++){ // preencher tabela de sintomas do paciente
			tela.modeloTabelaPS.addRow(new String[] {(String) tela.sintomasPaciente.get(i)});
	}	
		//ADICIONANDO INFORMAÇÕES BÁSICAS DO PACIENTE NA TELA DE ANÁLISE
		tela.nomePaciente.setText(paci.getNomePaciente());
		tela.idadePaciente.setText(""+paci.getIdadePaciente());
		tela.idPaciente.setText(""+paci.getCodPaciente());
		tela.comentarioEnfer = paci.getComentario();
		comandos.carregarPossiveisDoencas(tela, paci);	
	}
}
