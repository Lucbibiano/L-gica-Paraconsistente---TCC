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
	boolean teste = false; // Vari�vel para execu��o de testes de retorno do banco.
	//Este m�todo realiza o controle dos elementos da tela para inser��o de doen�as no sistema.
	public void inserirDoenca(TelaPrincipal tela){
		if(tela.campodoenca.getText().equals("")||tela.campodoencadesc.getText().equals("")){ // verifica campo nulo.
			JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos para cadastro da doen�a. Por favor verifique e tente novamente.");
		}
		else{
			// Enviando valores para as classes do pacote Modelo
			doenca.setNomeDoenca(tela.campodoenca.getText());
			doenca.setDescricaoDoenca(tela.campodoencadesc.getText());
			teste = comBD.inserirDoencas(doenca); 
			if(teste = true){ // Verifica se a doen�a foi cadastrada.
			tela.campodoenca.setText(null);
			tela.campodoencadesc.setText(null);
			}
			else{// Retorna um erro informando o usu�rio que n�o foi poss�vel realizar o cadastro com sucesso.
				JOptionPane.showMessageDialog(null, "N�o foi poss�vel inserir a doen�a, contate o administrador!");
			}
		}
	}
	//Este m�todo realiza o controle dos elementos da tela para inser��o de sintomas no sistema.
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
			else{// Retorna um erro informando o usu�rio que n�o foi poss�vel realizar o cadastro com sucesso.
				JOptionPane.showMessageDialog(null, "N�o foi poss�vel inserir o sintoma, contate o administrador!");
			}
		}
	}
	// M�dodo para edi��o de doen�as.
	public void editarDoenca(TelaPrincipal tela){
	    ComandosBD comandos = new ComandosBD();
	    tela.doenca.removeAllItems();
	    comandos.PreencherOpcoesDoenca(tela);
	    tela.doenca.setSelectedItem(null);
	}
	//Selecionar doenca e preencher informa��es na tela
	public void selecionarDoenca(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();// instancia da classe ComandosBD.
		try{ // try para tratar a excess�o caso o combobox esteja vazio
			if(tela.doenca.getSelectedItem().toString() == ""){//verifica se foi selecionado uma doen�a
			}
			else{// se foi escolhido doen�a realizar o preenchimento dos campos abaixo.
				String valor = tela.doenca.getSelectedItem().toString();
				String[] separa = valor.split(Pattern.quote("�"));
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
				JOptionPane.showMessageDialog(null, "Por favor, escolha uma doen�a para sele��o.");
			}
	}
	//M�doto para salvar a edi��o de uma doen�a
	public void salvarEdicaoDoenca(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		if(tela.confirmarSelecao== false){// Verificar se todos os campos foram preenchidos.
			JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos para edi��o da doen�a.");
		}
		else{//Realizar atualiza��o de doen�a.
			doenca.setNomeDoenca(tela.campodoenca.getText());
			doenca.setDescricaoDoenca(tela.campodoencadesc.getText());
			teste = comandos.editarDoenca(doenca);
			if(teste = true){
				JOptionPane.showMessageDialog(null, "A doen�a foi atualizada com sucesso.");	
				tela.campodoenca.setText(null);
				tela.campodoencadesc.setText(null);
			    tela.doenca.removeAllItems();
			    comandos.PreencherOpcoesDoenca(tela);
			    tela.doenca.setSelectedItem(null);
			}
		}
	}
	//M�todo para verificar tela para exclus�o da doen�a
	public void excluirDoenca(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		if(tela.confirmarSelecao == false){
			JOptionPane.showMessageDialog(null, "Para exclus�o de uma doen�a, a doen�a deve ser selecionada anteriormente!");
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
			JOptionPane.showMessageDialog(null, "A exclus�o da doenca "+doenca.getNomeDoenca()+" foi realizada com sucesso!");
			}
			else if(tela.confirmaExclusao == false){
				JOptionPane.showMessageDialog(null, "A doen�a "+doenca.getNomeDoenca()+" j� foi associada a sintomas e n�o pode ser excluida. ");
			}
		}
	}
	//M�todo para edi��o de sintoma
	public void editarSintoma(TelaPrincipal tela){
	    ComandosBD comandos = new ComandosBD();
	    tela.sintoma.removeAllItems();
	    tela.sintomasel.removeAllItems();
	    comandos.PreencherOpcoesSintoma(tela);
	    tela.sintoma.setSelectedItem(null);
	    tela.sintomasel.setSelectedItem(null);

	}	
	//M�todo para selecionar sintoma na tela de edi��o
	public void selecionarSintoma(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		try{ // Try para tratar a excess�o caso o combobox esteja vazio
			if(tela.sintoma.getSelectedItem().toString() == ""){				
			}
			else{
				String valor = tela.sintoma.getSelectedItem().toString();
				String[] separa = valor.split(Pattern.quote("�"));
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
				JOptionPane.showMessageDialog(null, "Por favor, escolha um sintoma para sele��o.");
			}
	}
	//M�todo para salvar edi��o de um sintoma
	public void salvarEdicaoSintoma(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		if(tela.confirmarSelecao== false){
			JOptionPane.showMessageDialog(null, "Todos os campos devem estar preenchidos para edi��o do sintoma.");
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
	//M�doto para exclus�o de um sintoma
	public void excluirSintoma(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		if(tela.confirmarSelecao== false){
			JOptionPane.showMessageDialog(null, "Para exclus�o de um sintoma, o sintoma deve ser selecionado anteriormente!");
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
			//Atualizando informa��es da tela de cadastro do paciente
			PrepararTelaPaciente(tela);
			JOptionPane.showMessageDialog(null, "A exclus�o do sintoma "+sintoma.getNomeSintoma()+" foi realizada com sucesso!");
		}
	}
	//M�todo prepara a tela de envio de pacientes
	public void PrepararTelaPaciente(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		tela.sintoma.removeAllItems();
		comandos.PreencherOpcoesSintoma(tela);
		tela.sintoma.setSelectedItem(null);
	}
	//M�todo adiciona os sintomas escolhidos na tabela de sintomas do paciente
	public void adicionarSintomaTabela(TelaPrincipal tela){
		ModeloSintoma sintoma = new ModeloSintoma();
		try{//tratamento de exce��o para quando o usu�rio n�o selecionar um sintoma
		if(tela.sintomasel.getSelectedItem().toString() == ""){
		}
		else{
		String valor = tela.sintomasel.getSelectedItem().toString();
		String[] separa = valor.split(Pattern.quote("�"));
		String  nomesintoma = separa[1];
		int idsintoma = Integer.parseInt(separa[0]);
		
		if(tela.sintomasinseridos.contains(idsintoma)){
			JOptionPane.showMessageDialog(null, "O sintoma j� foi selecionado!");
		}
			else{
			tela.sintomasinseridos.add(idsintoma);
		sintoma.setNomeSintoma(nomesintoma);
		sintoma.setCodSintoma(idsintoma);
		String escolha = tela.sintomasel.getSelectedItem().toString(); // jogando valor e vari�vel		
		// adicionando nova linha na tabela
			
			tela.modelo = (DefaultTableModel) tela.tabelaSintomaPaciente.getModel();
			
			tela.modelo.addRow(new String[] {escolha});
			escolha = ""; // limpando vari�vel.
			tela.sintomasel.requestFocus(); // pega o foco
			tela.tabelaSintomaPaciente.repaint();
			}
		}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "� necess�rio selecionar um sintoma para adicionar.");
		}	
	}
	//M�todo remove os sintomas escolhidos da tabela do paciente.
	public void removerSintomaTabela(TelaPrincipal tela){ // removendo item selecionado da tabela
		try{
			String valor = (String)tela.tabelaSintomaPaciente.getValueAt(tela.tabelaSintomaPaciente.getSelectedRow(), 0);
			String[] separa = valor.split(Pattern.quote("�"));
			String  nomesintoma = separa[1];
			int idsintoma = Integer.parseInt(separa[0]);
			((DefaultTableModel) tela.tabelaSintomaPaciente.getModel()).removeRow(tela.tabelaSintomaPaciente.getSelectedRow());
			int index = tela.sintomasinseridos.indexOf(idsintoma);
			tela.sintomasinseridos.remove(index);
			for(int i = 0; i<tela.sintomasinseridos.size(); i++){
				System.out.println("Na lista"+tela.sintomasinseridos.get(i));
			}
			
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Para remover um sintoma, � necess�rio selecionar na tabela.");			
		}	
	}
	//M�todo prepara a tela para enviar o paciente	
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
			JOptionPane.showMessageDialog(null, "O campo idade deve ser preenchido num�ricamente.");
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
					JOptionPane.showMessageDialog(null, "O campo idade deve ser preenchido num�ricamente.");
					tela.idade.setText(null);
					teste = false;
				}
		}
		if(teste == true){
			for(int inicia = 0; inicia < tela.tabelaSintomaPaciente.getRowCount(); inicia++)
				{
				String valor = tela.modelo.getValueAt(inicia, 0).toString();
				String[] separa = valor.split(Pattern.quote("�"));
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
	//Este m�todo prepara a tela de associa��o de Sintomas e Doen�as. 
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
	//M�todo verifica a doen�a na tela de associa��o antes de realizar o salvamento.
	public void verificaTelaAssociacao(TelaPrincipal tela){
		ModeloDoenca doenca = new ModeloDoenca();
		try{
		if(tela.doencaAssociada.getSelectedItem().equals(null)){
		}
		else
		{
			if(tela.testeConfirmaDoenca == true){
		// pega ID da doen�a que � selecionada
		String[] idDoencaAssociada = tela.doencaAssociada.getSelectedItem().toString().split(Pattern.quote("�"));
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
			JOptionPane.showMessageDialog(null, "� necess�rio selecionar uma doen�a para confirma-la ou cancela-la.");
		}
	}
	//M�todo respons�vel por verificar a tela de associa��o antes de realizar o salvamento.
	public void salvarAssociacoes(TelaPrincipal tela){

		ComandosBD comandos = new ComandosBD();
		ModeloDoenca doenca = new ModeloDoenca();
		
		if((tela.doencaSelecionada == false) && (tela.sintomasEscolhidos.getRowCount() == 0)){
			JOptionPane.showMessageDialog(null, "� necess�rio preencher as informa��es antes de salvar a associa��o.");
			tela.confirmar = false;
		}
		else if(tela.doencaSelecionada == false){
			JOptionPane.showMessageDialog(null, "� necess�rio escolher uma doen�a para salvar a associa��o.");
			tela.confirmar = false;
		}
		else if(tela.sintomasEscolhidos.getRowCount() == 0){
			JOptionPane.showMessageDialog(null, "� necess�rio escolher os sintomas que ser�o associados � doen�a antes de salvar a associa��o.");
			tela.confirmar = false;
		}
		else{
			// separa ID do Nome e armazena na vari�vel da classe ModeloDoenca
			String doencaEscolhida = tela.doencaAssociada.getSelectedItem().toString();
			String[] separa = doencaEscolhida.split(Pattern.quote("�"));
			doenca.setCoddoenca(Integer.parseInt(separa[0]));
			//pega todos os sintomas escolhidos para associa-los  e guarda em vari�veis para jogar no banco
			for(int inicia = 0; inicia< tela.sintomasEscolhidos.getRowCount(); inicia++){
			String sintoma = tela.sintomasEscolhidos.getValueAt(inicia, 0).toString();
			String[] separar = sintoma.split(Pattern.quote("�"));
			int idSintoma = Integer.parseInt(separar[0]);
			tela.valsintomas.add(idSintoma);
			}	
			comandos.SalvarAssociacao(tela, doenca);
			tela.valsintomas.clear();
		}
			if(tela.confirmar == true){
				JOptionPane.showMessageDialog(null, "As associa��es foram realizadas com sucesso.");
			}
	}
	//M�todo respons�vel por limpar tabela de doen�as pendentes para atualiza��o
	public void tabelaDoencasPendentes(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		try{
		((DefaultTableModel) tela.doencasPendentes.getModel()).setNumRows(0);
		tela.doencasPendentes.updateUI();
		}catch(Exception ex){
			System.out.println("N�o foi necess�rio remover os elementos pois a tabela doencas pendentes est� vazia.");
		}
		comandos.preencherDoencasPendentes(tela);
	}
	//M�todo respos�vel por preencher os sintomas na tabela associa��o.
	public void preencherSintomas(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		String doenca = "";
		doenca = tela.inddoenca.getText();
		String [] separa = doenca.split(Pattern.quote("�"));
		tela.iddoenca = Integer.parseInt(separa[0]);
		separa = null;
		comandos.preencherSintomas(tela);
	}
	//M�todo para inserir especialista no momento da associa��o.
	public void inserirEspecialista(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		if(tela.campoEspecialista.getText().equals(null)||tela.campoEspecialista.getText().equals("Nome especialista")){
			JOptionPane.showMessageDialog(null, "� necess�rio inserir um nome no campo para adicionar o especialista.");
		}
		else{
			boolean teste = comandos.inserirEspecialista(tela);
			if(teste == true){
				JOptionPane.showMessageDialog(null, "O especialista "+tela.campoEspecialista.getText()+" foi criado com sucesso.");
			}
		}
	}
	//M�todo para preencher combo de especialistas para tela de associa��o.
	public void preencherEspecialistas(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		comandos.preencherComboEspecialista(tela);
	}
	//M�todo para verificar os valores de evid�ncias e especialistas antes de salvar as evid�ncias
	public void salvarEvidencias(TelaPrincipal tela){
		ModeloEspecialista espec = new ModeloEspecialista();
		ModeloDoenca doen = new ModeloDoenca();
		ComandosBD comandos = new ComandosBD();
		boolean retorno = false;
		//Verificar se alguma celula est� vazia
		//garantir que todos os valores da tabela est�o de acordo com 
		if(tela.verificacao == true){
		for(int inicia = 0; inicia < tela.tabelaAssociacao.getRowCount(); inicia ++){
			if(tela.tabelaAssociacao.getValueAt(inicia, 0).equals(" . ")||tela.tabelaAssociacao.getValueAt(inicia, 0).equals("")){
				JOptionPane.showMessageDialog(null, "� necess�rio preencher todos os valores de Evid�ncia Favor�vel.");
				tela.verificacao = false;
				break;
			}
			else if(tela.tabelaAssociacao.getValueAt(inicia, 1).equals(" . ")||tela.tabelaAssociacao.getValueAt(inicia, 1).equals("")){
				JOptionPane.showMessageDialog(null, "� necess�rio preencher todos os valores de Evid�ncia Contr�ria.");
				tela.verificacao = false;
				break;
			}
			if(Float.parseFloat((String)tela.tabelaAssociacao.getValueAt(inicia, 1)) >= 1.01){
				JOptionPane.showMessageDialog(null, "Os valores de Evid�ncia Favor�vel devem estar entre 0.00 e 1.00");
				tela.verificacao = false;
				break;
			}
			else if(Float.parseFloat((String)tela.tabelaAssociacao.getValueAt(inicia, 2)) >= 1.01){
				JOptionPane.showMessageDialog(null, "Os valores de Evid�ncia Contr�ria devem estar entre 0.00 e 1.00");
				tela.verificacao = false;
				break;
			}
			
			else if(tela.especialista.getSelectedIndex() == -1){
				JOptionPane.showMessageDialog(null, "� necess�rio escolher um especialista na lista de op��es dispon�veis.");
				tela.verificacao = false;
				break;
			}
		}
		//colocando valores nos array lists
		if(tela.verificacao == true){
			for(int i = 0; i <tela.tabelaAssociacao.getRowCount();i++){
				String sintoma = tela.tabelaAssociacao.getValueAt(i, 0).toString();
				String[] separar = sintoma.split(Pattern.quote("�"));
				//pegando valores de evid�ncia favor�vel, evid�ncia contr�ria e Idsintomas
				tela.sintomas.add(Integer.parseInt(separar[0]));
				tela.evidenciasFav.add(tela.tabelaAssociacao.getValueAt(i, 1));
				tela.evidenciasCont.add(tela.tabelaAssociacao.getValueAt(i, 2));
				separar = null;
			}
			//ID especislista
			String especialista = tela.especialista.getSelectedItem().toString();
			String[] separarEspe = especialista.split(Pattern.quote("�"));
			espec.setCodespecialista(Integer.parseInt(separarEspe[0]));	
			//ID Doen�a
			separarEspe = null;
			String doenca = tela.inddoenca.getText();
			String[] separarDoenca = doenca.split(Pattern.quote("�"));
			doen.setCoddoenca(Integer.parseInt(separarDoenca[0]));
			separarDoenca = null;
			//m�todo para entrada no banco de dados
			
			retorno = comandos.verificaEspecialistaAssociado(tela, espec, doen);
			if(retorno == true){
			retorno = comandos.verificarEvidencias(tela, espec, doen);
			this.tabelaDoencasPendentes(tela);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "� necess�rio escolher outro especialista, pois o especialista escolhido j� evid�nciou valores sobre a associa��o.");
				tela.verificacao = false;
			}
				}
			}
					if(retorno == true){
			JOptionPane.showMessageDialog(null, "As evid�ncias foram cadastradas com sucesso.");
		}		
	}
	//M�todo para carregar pacientes para inclu�-los na tela de an�lise m�dica.
	public void carregarPacientes(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		comandos.carregarPacientes(tela);
	}
	//M�todo exibe o coment�[ario da enfermeira
	public void comentarioEnfermeira(TelaPrincipal tela){
		JOptionPane.showMessageDialog(null, tela.comentarioEnfer);
	}
	//M�todo que aplica o MPD (L�gica paraconsistente) atrav�s de uma Thread no momento da inclus�o do paciente
	public void aplicacaoMPD(TelaPrincipal tela, ModeloPaciente paci){
		new Thread(){//THREAD PARA EXECU��O EM PARALELO
		public void run(){
			ComandosBD comandos = new ComandosBD();
			comandos.calculosLogicaParaconsistente(tela, paci);
			// ADICIONANDO O PACIENTE NA TABELA DE AN�LISE
			tela.modeloAnalise = (DefaultTableModel) tela.tabelaPacienteAnalise.getModel();
			tela.modeloAnalise.addRow(new String[] {paci.getCodPaciente()+"�"+paci.getNomePaciente()}); 
			}
		}.start();
	}
	//M�todo carrega as informa��es da tela de an�lise do paciente.
	public void carregarAnalisePaciente(TelaPrincipal tela){
		ComandosBD comandos = new ComandosBD();
		ModeloPaciente paci = new ModeloPaciente();
		String paciente = (String) tela.tabelaPacienteAnalise.getValueAt(tela.tabelaPacienteAnalise.getSelectedRow(), 0);
		//ID Paciente
		String[] separarPaciente = paciente.split(Pattern.quote("�"));
		paci.setCodPaciente((Integer.parseInt(separarPaciente[0])));	
		comandos.carregarAnalisePaciente(tela, paci);
		//ADICIONANDO SINTOMAS NA TABELA DE SINTOMAS
		tela.modeloTabelaPS = (DefaultTableModel) tela.tabelaPS.getModel(); // modelo
		for(int i = 0; i<tela.sintomasPaciente.size();i++){ // preencher tabela de sintomas do paciente
			tela.modeloTabelaPS.addRow(new String[] {(String) tela.sintomasPaciente.get(i)});
	}	
		//ADICIONANDO INFORMA��ES B�SICAS DO PACIENTE NA TELA DE AN�LISE
		tela.nomePaciente.setText(paci.getNomePaciente());
		tela.idadePaciente.setText(""+paci.getIdadePaciente());
		tela.idPaciente.setText(""+paci.getCodPaciente());
		tela.comentarioEnfer = paci.getComentario();
		comandos.carregarPossiveisDoencas(tela, paci);	
	}
}
