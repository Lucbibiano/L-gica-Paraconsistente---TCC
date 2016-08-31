package Modelo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Visao.TelaPrincipal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import org.jfree.data.category.DefaultCategoryDataset;

import Controle.ConnectionFactory;
public class ComandosBD {
	
private Connection conexao;
String comandosSQL;
int ultimocod;
int resultado = 0;
int NumEvidencia = 0;
//M�todo para abrir conex�o com o banco de dados
public void abrirConexaoBD(){ 
	conexao = new ConnectionFactory().getConnection();
}
//M�todo para fechar conex�o com o banco de dados
public void fechaConexaoBD(){
	try{
		conexao.close();
		System.out.println("Conex�o com o banco est� fechada.");
	}catch (SQLException e){
		e.printStackTrace();
	}
}
//M�todo para inserir doen�as no banco de dados
public boolean inserirDoencas(ModeloDoenca doenca){
	boolean teste = false;
	abrirConexaoBD();
	comandosSQL = null;
	comandosSQL = "INSERT INTO doenca(NomeDoenca, DescDoenca) VALUES(?,?)";//comando
	try{
		//inserindo informa��es no banco
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		//dando valor aos registros da classe ModeloDoenca que faz representa informa��es do banco
		ca_conexao.setString(1, doenca.getNomeDoenca());
		ca_conexao.setString(2, doenca.getDescricaoDoenca());
		ca_conexao.execute();
		ca_conexao.close();
		teste = true;
	}catch(Exception e){ // erro ao tentar inserir doen�a
		JOptionPane.showMessageDialog(null, "N�o foi poss�vel inserir a doen�a, contate o administrador!");
	}
	if(teste == true){
	comandosSQL = null;
	comandosSQL = "SELECT * FROM doenca";
	try{
		//pegando informa��es no banco
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ResultSet result = ca_conexao.executeQuery();
		while(result.next()){
			ultimocod = result.getInt("idDoenca"); // pega o ultimo c�digo cadastrado.
		}
	}catch(Exception e){
			System.out.println("Erro ao buscar o c�digo da doen�a.");
	}
		doenca.setCoddoenca(ultimocod);
		fechaConexaoBD();
		JOptionPane.showMessageDialog(null, "A doen�a foi inserida com sucesso! O c�digo da doen�a � "+ doenca.getCoddoenca());
	}
	return teste;
}
//M�todo para inserir sintomas no banco de dados
public boolean inserirSintoma(ModeloSintoma sintoma){
	boolean teste = false;
	abrirConexaoBD();
	comandosSQL = null;
	comandosSQL = "INSERT INTO sintoma(NomeSintoma, DescSintoma, status_cadastro) VALUES(?,?,0)";//comando
	try{
		//inserindo informa��es no banco
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		//dando valor aos registros da classe javaBeans que faz representa informa��es do banco
		ca_conexao.setString(1, sintoma.getNomeSintoma());
		ca_conexao.setString(2, sintoma.getDescricaoSintoma());
		ca_conexao.execute();
		ca_conexao.close();
		teste = true;
	}catch(Exception e){ // erro ao tentar inserir doen�a
		System.out.println("N�o foi poss�vel inserir o sintoma.");
	}
	comandosSQL = null;
	comandosSQL = "SELECT * FROM sintoma";
	try{
		//pegando informa��es no banco
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ResultSet result = ca_conexao.executeQuery();
		while(result.next()){
			ultimocod = result.getInt("idSintoma"); // pega o ultimo c�digo cadastrado.
		}
	}catch(Exception e){
			System.out.println("Erro ao buscar o c�digo do sintoma.");
	}
		sintoma.setCodSintoma(ultimocod);
		fechaConexaoBD();
		JOptionPane.showMessageDialog(null, "O sintoma foi inserido com sucesso! O c�digo do sintoma � "+ sintoma.getCodSintoma());	
		return teste;
	}
//M�todo para preencher combos de doen�a e doen�as associadas.
public void PreencherOpcoesDoenca(TelaPrincipal tela){
			abrirConexaoBD();
			comandosSQL = null;
			comandosSQL = "SELECT * FROM doenca";
				try{
					//pegando informa��es no banco
					PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
					ResultSet result = ca_conexao.executeQuery();
					while(result.next()){
						tela.doenca.addItem(result.getInt("IdDoenca")+"�"+
								result.getString("NomeDoenca"));
						}
				}catch(Exception e){
					System.out.println("Ocorreu um erro no preenchimento do combobox");
				}
				comandosSQL = null;
				comandosSQL = "SELECT * FROM doenca WHERE status_cadastro IS NULL";
				try{
					PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
					ResultSet result = ca_conexao.executeQuery();
					while(result.next()){
						tela.doencaAssociada.addItem(result.getInt("IdDoenca")+"�"+
								result.getString("NomeDoenca"));
					}
				}catch(Exception e){
					System.out.println("N�o foi poss�vel preencher o combobox de doencas associadas.");
				}
		fechaConexaoBD();
	}
//M�todo realizar a busca dos dados para preencher os campos com os dados da doen�a no momento da edi��o.	
public void preencherEdicaoDoenca(ModeloDoenca doenca){
		abrirConexaoBD();
		comandosSQL = null;
		comandosSQL = "SELECT * FROM doenca WHERE NomeDoenca = ?";
		try{
			//pegando informa��es no banco
			PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
			ca_conexao.setString(1, doenca.getNomeDoenca());
			ResultSet result = ca_conexao.executeQuery();
			ca_conexao.execute();
			while(result.next()){
				doenca.setNomeDoenca(result.getString("NomeDoenca"));
				doenca.setDescricaoDoenca(result.getString("DescDoenca"));
				doenca.setCoddoenca(result.getInt("idDoenca"));	
			}
			ca_conexao.close();
		}catch(Exception e){
			System.out.println("Ocorreu um erro durante a consulta para preenchimento dos campos de edi��o de doen�a.");
		}	
		fechaConexaoBD();
	}
//M�todo para editar doen�as no banco de dados.
public boolean editarDoenca(ModeloDoenca doenca){
		abrirConexaoBD();
		boolean teste = false;
		comandosSQL = null;
		comandosSQL = "UPDATE doenca SET NomeDoenca = ? , DescDoenca = ? where idDoenca = ?";
		try{
			PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
			ca_conexao.setString(1, doenca.getNomeDoenca());
			ca_conexao.setString(2, doenca.getDescricaoDoenca());
			ca_conexao.setInt(3, doenca.getCoddoenca());
			ca_conexao.execute();
			ca_conexao.close();
			teste = true;
		}catch(Exception e){
			System.out.println("Erro ao atualizar informa��es sobre doen�as");
		}
		fechaConexaoBD();
		return teste;		
	}
//M�todo para excluir doen�a no banco de dados.
public boolean excluirDoenca(TelaPrincipal tela, ModeloDoenca doenca){
		abrirConexaoBD();
		boolean teste = false;
		comandosSQL = null;
		comandosSQL = "SELECT * FROM doenca WHERE idDoenca = ? AND status_cadastro IS NULL";
		 try{
			 PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
			 ca_conexao.setInt(1, doenca.getCoddoenca());
			 ResultSet result = ca_conexao.executeQuery();
			 while(result.next()){
				 tela.confirmaExclusao = true;
			 }
		 }catch(Exception e){
			 System.out.println("N�o foi poss�vel pesquisar a doen�a a ser excluida.");
		 }
		if(tela.confirmaExclusao == true){
		 comandosSQL = "DELETE FROM doenca WHERE idDoenca = ? AND status_cadastro IS NULL";
		try{
			PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
			ca_conexao.setInt(1, doenca.getCoddoenca());
			ca_conexao.execute();
			ca_conexao.close();
			teste = true;
		}catch(Exception e){
			System.out.println("N�o foi poss�vel excluir a doen�a da base de dados.");
		}
		}
		fechaConexaoBD();
		return teste;		
	}
//M�todo realiza o preenchimento do commbobox sintomas
public void PreencherOpcoesSintoma(TelaPrincipal tela){
		
		ModeloSintoma sintoma = new ModeloSintoma();
		abrirConexaoBD();
		comandosSQL = null;
		comandosSQL = "SELECT * FROM sintoma";
			try{
				//pegando informa��es no banco
				PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
				ResultSet result = ca_conexao.executeQuery();
				while(result.next()){ // preenchimento do combobox
					tela.sintoma.addItem(result.getString("idSintoma")+"�"+
					result.getString("NomeSintoma"));
					tela.sintomasel.addItem(result.getString("idSintoma")+"�"+result.getString("NomeSintoma"));
				}
			}catch(Exception e){
				System.out.println("Ocorreu um erro no preenchimento do combobox.");
			}
	fechaConexaoBD();
}
//M�todo realizar a busca dos dados para o preenchimento dos dados do sintoma na tela.
public void preencherEdicaoSintoma(ModeloSintoma sintoma){
	abrirConexaoBD();
	comandosSQL = null;
	comandosSQL = "SELECT * FROM sintoma WHERE NomeSintoma = ? and idSintoma = ?";
	try{
		//pegando informa��es no banco
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setString(1, sintoma.getNomeSintoma());
		ca_conexao.setInt(2, sintoma.getCodSintoma());
		ResultSet result = ca_conexao.executeQuery();
		ca_conexao.execute();
		while(result.next()){
			sintoma.setNomeSintoma(result.getString("NomeSintoma"));
			sintoma.setDescricaoSintoma(result.getString("DescSintoma"));
			sintoma.setCodSintoma(result.getInt("idSintoma"));	
		}
		
		ca_conexao.close();
		
	}catch(Exception e){
		System.out.println("Ocorreu um erro durante a consulta para preenchimento dos campos de edi��o de sintoma.");
	}	
	fechaConexaoBD();
}
//M�todo para realizar a edi��o de um sintoma
public boolean editarSintoma(ModeloSintoma sintoma){
	abrirConexaoBD();
	boolean teste = false;
	comandosSQL = null;
	comandosSQL = "UPDATE sintoma SET NomeSintoma = ? , DescSintoma = ? where idSintoma = ?";
	try{
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setString(1, sintoma.getNomeSintoma());
		ca_conexao.setString(2, sintoma.getDescricaoSintoma());
		ca_conexao.setInt(3, sintoma.getCodSintoma());
		ca_conexao.execute();
		ca_conexao.close();
		teste = true;
	}catch(Exception e){
		System.out.println("Erro ao atualizar informa��es sobre sintoma.");
	}
	fechaConexaoBD();
	return teste;		
}
//M�todo para exclus�o de sintoma no banco de dados.
public boolean excluirSintoma(ModeloSintoma sintoma){
	abrirConexaoBD();
	boolean teste = false;
	comandosSQL = null;
	comandosSQL = "DELETE FROM sintoma WHERE idSintoma = ? AND status_cadastro IS NULL";
	try{
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, sintoma.getCodSintoma());
		ca_conexao.execute();
		ca_conexao.close();
		teste = true;
	}catch(Exception e){
		System.out.println("N�o foi poss�vel excluir o sintoma da base de dados.");
	}
	fechaConexaoBD();
	return teste;		
}
//M�todo para enviar paciente para o banco de dados.
public boolean enviarPaciente(TelaPrincipal tela, ModeloPaciente paciente){
boolean teste = false;
comandosSQL = null; 
comandosSQL = "INSERT INTO paciente (NomePaciente, IdadePaciente, comentario) VALUES(?,?,?)";
String comandosSQL2 = null;
comandosSQL2 = "INSERT INTO paciente_sintoma (Paciente_idPaciente, Sintoma_idSintoma) VALUES(?,?) ";
abrirConexaoBD();
try{
	PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
	ca_conexao.setString(1, paciente.getNomePaciente());
	ca_conexao.setInt(2, paciente.getIdadePaciente());
	ca_conexao.setString(3, paciente.getComentario());
	ca_conexao.execute();
	ca_conexao.close();
	comandosSQL = null;
	comandosSQL = "SELECT idPaciente FROM paciente ORDER BY idPaciente DESC LIMIT 1";
	ca_conexao = conexao.prepareStatement(comandosSQL);
	ResultSet result = ca_conexao.executeQuery();
	while(result.next()){
	paciente.setCodPaciente(result.getInt("idPaciente"));
	}
	for(int inicia = 0; inicia < tela.tabelaSintomaPaciente.getRowCount();inicia ++){ // associando sintomas ao paciente
		ca_conexao = conexao.prepareStatement(comandosSQL2);
		ca_conexao.setInt(1, paciente.getCodPaciente());
		ca_conexao.setInt(2, tela.valsintomas.get(inicia));// pega o ID especifico do sintoma
		ca_conexao.execute();
		ca_conexao.close();
	}
	teste = true;
}catch(Exception e){
	System.out.println("Erro ao tentar enviar o paciente.");
}
fechaConexaoBD();	
return teste;
}
//Este m�todo realiza os calculos da L�gica paraconsistente
public void calculosLogicaParaconsistente(TelaPrincipal tela, ModeloPaciente paci){
	//APLICA��O DOS CALCULOS PARA REALIZA��O DO MPD
	//SELECIONANDO OS IDS DE ASSOCIA��O DOS SINTOMAS DO PACIENTE
	abrirConexaoBD();
	comandosSQL = null;
	ArrayList<Integer> associacoes = new ArrayList<Integer>();
	ArrayList<Integer> doencas = new ArrayList<Integer>();
	PreparedStatement ca_conexao;
	ResultSet result;
	comandosSQL = "SELECT A.idAssociacao AS 'IDAssociacao' FROM associacoes A "
			+ "INNER JOIN paciente_sintoma PS ON A.Sintoma_idSintoma = PS.Sintoma_idSintoma "
			+ "WHERE PS.Paciente_idPaciente = ?";
	try{
		ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, paci.getCodPaciente());
		result = ca_conexao.executeQuery();
		
		while(result.next()){
			associacoes.add(result.getInt("IDAssociacao"));
		}
	}catch(Exception e){
		System.out.println("Erro ao consultar as associacoes para aplica��o do MPD.");
		System.out.println(e);
	}
	//Cria��o da tabela tempor�ria para realiza��o dos calculos.
	comandosSQL = "CREATE TEMPORARY TABLE calculo_TEMP (idCalculo INTEGER NOT NULL AUTO_INCREMENT,  "
			+ "Associacoes_idAssociacao INTEGER NOT NULL,  MaxEFG1 FLOAT,  MaxECG1 FLOAT,  MaxEFG2 FLOAT,  "
			+ "MaxECG2 FLOAT,  MinGruposEF FLOAT,  MinGrupoEC FLOAT,  GrauCerteza FLOAT,  GrauIncerteza FLOAT,  "
			+ "PRIMARY KEY(idCalculo),  INDEX Associacao_FK(Associacoes_idAssociacao))";
			
	try{
		ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.execute();
		ca_conexao.close();	
	}catch(Exception e){
		System.out.println("N�o foi poss�vel criar a tabela tempor�ria.");
		System.out.println(e);
	}
	
	
	// Execu��o da procedure para preenchimento da tabela tempor�ria
	comandosSQL = "call tabelaCalculo(?)";
	try{ 
		for(int inicia = 0; inicia < associacoes.size();inicia++){
			ca_conexao = conexao.prepareStatement(comandosSQL);
			ca_conexao.setInt(1, associacoes.get(inicia));// pega o ID especifico da associacao
			ca_conexao.execute();
			ca_conexao.close();
		}
		}catch(Exception e){
			System.out.println("N�o foi poss�vel executar a procedure.");
			System.out.println(e);
		}
	//Selecionar as doen�as para aplicar o calculo do baricentro
	comandosSQL = "SELECT DISTINCT A.Doenca_idDoenca AS 'Doencas' FROM associacoes A INNER JOIN calculo_TEMP CT ON A.idAssociacao = CT.Associacoes_idAssociacao";
	try{
		ca_conexao = conexao.prepareStatement(comandosSQL);
		result = ca_conexao.executeQuery();
		while(result.next()){
			doencas.add(result.getInt("Doencas"));
		}
	}catch(Exception e){
		System.out.println("N�o foi poss�vel capturar as doen�as relacionadas �s associa��es.");
		System.out.println(e);
	}
	//Execu��o da procedure para realiza��o dos calculos do baricentro e busca para inserir valores para tomada de decis�o.
	comandosSQL = null;
	comandosSQL = "call calculoBaricentros(?,?)";
	try{
		for(int inicia = 0; inicia < doencas.size(); inicia++){
			ca_conexao = conexao.prepareStatement(comandosSQL);
			ca_conexao.setInt(1, paci.getCodPaciente());
			ca_conexao.setInt(2, doencas.get(inicia));
			ca_conexao.execute();
		}	
	}catch(Exception e){
		System.out.println("N�o foi poss�vel executar a procedure de calculo de baricentro.");
		System.out.println(e);
	}
	fechaConexaoBD();
	}
//M�todo carrega as poss�veis doen�as que o paciente ter�, juntamente com a cria��o do gr�fico
public void carregarPossiveisDoencas(TelaPrincipal tela, ModeloPaciente paci){ // m�todo que realiza o carregamento das poss�veis doen�as
	comandosSQL = null;
	comandosSQL = "SELECT GrauCenteza, GrauIncerteza, D.NomeDoenca FROM decisao INNER JOIN doenca D ON Doenca_idDoenca = D.idDoenca WHERE Paciente_idPaciente = ? AND GrauCenteza > 0.60 AND GrauIncerteza < 0.60 ORDER BY GrauCenteza DESC LIMIT 5";
	tela.modeloTabelaPD = (DefaultTableModel) tela.tabelaPD.getModel();
	ArrayList<String> ListapossiveisDoencas = new ArrayList<String>();
	ArrayList<Float> ListagrauCerteza = new ArrayList<Float>();
	ArrayList<Float> ListagrauIncerteza = new ArrayList<Float>();
	PreparedStatement ca_conexao;
	ResultSet result;
	tela.dadosGrafico = new DefaultCategoryDataset();
	try{
		ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, paci.getCodPaciente());
		result = ca_conexao.executeQuery();
		while(result.next()){
			//Incluindo valores na tabela e arredondando para 3 digitos depois do ponto.
			BigDecimal arredondar = new BigDecimal(result.getFloat("GrauCenteza")).setScale(3, RoundingMode.HALF_EVEN);
			String grauCerteza = arredondar.toString();
			arredondar = new BigDecimal(result.getFloat("GrauIncerteza")).setScale(3, RoundingMode.HALF_EVEN);
			String grauIncerteza = arredondar.toString();
			tela.modeloTabelaPD.addRow(new String[] {result.getString("NomeDoenca"),grauCerteza,grauIncerteza});
			ListapossiveisDoencas.add(result.getString("NomeDoenca"));
			ListagrauCerteza.add(Float.parseFloat(grauCerteza));
			ListagrauIncerteza.add(Float.parseFloat(grauIncerteza));			
		}
		for(int i = 0; i < ListapossiveisDoencas.size(); i++){
			tela.dadosGrafico.setValue(ListagrauCerteza.get(i)*100, "Grau de Certeza",ListapossiveisDoencas.get(i) );
			tela.dadosGrafico.setValue(ListagrauIncerteza.get(i)*100, "Grau de Incerteza", ListapossiveisDoencas.get(i));
		}
	}catch(Exception e){
		System.out.println("Erro ao preencher a tabela com as poss�veis doen�as.");
	}
}
//Este m�todo preenche os sintomas na tabela de sintomas para associa��o
public void PreencherTabelaSintomas(TelaPrincipal tela){
	ModeloSintoma sintoma = new ModeloSintoma();
	abrirConexaoBD();
	comandosSQL = null;
	comandosSQL = "SELECT * FROM sintoma";
		try{
			//pegando informa��es no banco
			PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
			ResultSet result = ca_conexao.executeQuery();
			while(result.next()){ // preenchimento da tabela de sintomas dispon�veis
				tela.todosSintomas.add(result.getString("idSintoma")+"�"+
				result.getString("NomeSintoma"));
			}
		}catch(Exception e){
			System.out.println("Ocorreu um erro no preenchimento da tabela.");
		}
		fechaConexaoBD();
}

//M�todo para salvar as associa��es realizadas de Doen�a e Sintomas.
public void SalvarAssociacao(TelaPrincipal tela, ModeloDoenca doenca){
	ArrayList<Integer> idSintomasStatus = new ArrayList<Integer>();
	abrirConexaoBD();
	comandosSQL = null;
	comandosSQL = "INSERT INTO associacoes (Doenca_idDoenca,Sintoma_idSintoma) values(?,?)";
	ArrayList sintomasNaoAssociados = new ArrayList();
	//Realizar associa��o entre doen�as e sintomas
	try{
		for(int inicia = 0; inicia < tela.valsintomas.size();inicia ++){
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, doenca.getCoddoenca());
		ca_conexao.setInt(2, tela.valsintomas.get(inicia));
		ca_conexao.execute();
		ca_conexao.close();
		}
		tela.confirmar = true;
	}catch(Exception e){
		System.out.println("N�o foi poss�vel salvar a associa��o realizada.");
	}
	//Selecionar sintomas associados a doen�a em quest�o que n�o foram associados a outras doen�as.
	comandosSQL = null;
	comandosSQL = "SELECT * FROM sintoma WHERE idSintoma = ? AND status_cadastro = 0";
	try{
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ResultSet result;
		for(int i = 0; i<tela.valsintomas.size();i++){
			ca_conexao.setInt(1, tela.valsintomas.get(i));
			result = ca_conexao.executeQuery();
			while(result.next()){ 
				idSintomasStatus.add(result.getInt("idSintoma"));
				System.out.println("Status zerado. OK");
				System.out.println("SINTOMAZERADO="+result.getString("idSintoma"));
			}
		}
	}catch(Exception e){
		System.out.println("N�o foi poss�vel encontrar sintomas com status_cadastro zerado.");
	}
	//Doen�as j� associadas para adicionar uma nova associa��o com novos sintomas.
	comandosSQL = null;
	comandosSQL = "SELECT * FROM doenca WHERE NOT idDoenca = ? AND status_cadastro IN(0,1)";
	try{
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, doenca.getCoddoenca());
		ResultSet result = ca_conexao.executeQuery();
		while(result.next()){ 
			tela.doencasNAssociadas.add(result.getInt("idDoenca"));
			System.out.println("Doen�as 0 e 1. OK");
			System.out.println("DOENCAS0e1="+result.getString("idDoenca"));
		}
	}catch(Exception e){
		System.out.println("N�o foi poss�vel encontrar doen�as para associar.");
	}

	if(idSintomasStatus.isEmpty()){
		System.out.println("N�o h� novos sintoma para associar as doen�as.");
	}
	
	else{
		comandosSQL = null;
		comandosSQL = "INSERT INTO associacoes (Doenca_idDoenca,Sintoma_idSintoma) values(?,?)";
		//Inserindo associa��o dos sintomas novos para as doen�as j� associadas.
	try{
		for(int inicia = 0; inicia <tela.doencasNAssociadas.size();inicia ++){
			for(int i = 0; i<idSintomasStatus.size();i++){
				PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
				System.out.println("DOENCANASSO="+ tela.doencasNAssociadas.get(inicia));
				System.out.println("SINTOMASSTAUTS)="+idSintomasStatus.get(i));
				ca_conexao.setInt(1, tela.doencasNAssociadas.get(inicia));
				ca_conexao.setInt(2, idSintomasStatus.get(i));
				ca_conexao.execute();
				ca_conexao.close();
			}
		}
			tela.confirmar = true;
		}catch(Exception e){
		System.out.println("N�o foi poss�vel associar as doen�as n�o escolhidas aos novos sintomas.");
		}
	}
	//Realizar mudan�a de status de sintomas associados.
	try{
	comandosSQL = null;
	comandosSQL = "UPDATE sintoma SET status_cadastro = 1 WHERE idSintoma = ?";
	
	for(int i = 0; i< tela.valsintomas.size(); i++){
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, tela.valsintomas.get(i));
		ca_conexao.execute();
		ca_conexao.close();
	}
	}catch(Exception e){
		System.out.println("N�o foi poss�vel inserir um status para os sintomas associados.");
	}
	
	//Realizar mudan�a de status da doen�a.
	try{
		comandosSQL = null;
		comandosSQL = "UPDATE doenca SET status_cadastro = 0 WHERE idDoenca = ?";
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, doenca.getCoddoenca());
		ca_conexao.execute();
		ca_conexao.close();
	}catch(Exception e){
		System.out.println("N�o foi poss�vel inserir um status para doenca associada.");
	}			
	
	//REALIZAR A INSER��O DE EC E EF NOS SINTOMAS N�O ESCOLHIDOS
	comandosSQL = null;
	comandosSQL = "SELECT * FROM sintoma WHERE NOT idSintoma IN (";
	//CONSTRU��O DA STRING DINAMICAMENTE
	for(int i = 0; i<tela.valsintomas.size();i++){
		if(i == tela.valsintomas.size()-1){
			System.out.println("entrou, ultimo"+tela.valsintomas.get(i));
			comandosSQL = ""+comandosSQL+tela.valsintomas.get(i)+")";
		}
		else
		comandosSQL = comandosSQL+tela.valsintomas.get(i)+",";
	}
	
	//PESQUISANDO SINTOMAS N�O ASSOCIADOS
	try{
	PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
	ResultSet result = ca_conexao.executeQuery();
	while(result.next()){
		sintomasNaoAssociados.add(result.getInt("idSintoma"));
	}
	}catch(Exception e){
		System.out.println("N�o foi poss�vel encontrar os sintomas n�o associados.");
	}
	//ASSOCIANDO SINTOMAS N�O ASSOCIADOS � DOEN�A
	comandosSQL = null;
	comandosSQL = "INSERT INTO associacoes (Doenca_idDoenca,Sintoma_idSintoma) values(?,?)";	
	try{
		for(int inicia = 0; inicia < sintomasNaoAssociados.size();inicia ++){
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, doenca.getCoddoenca());
		ca_conexao.setInt(2, (int) sintomasNaoAssociados.get(inicia));
		ca_conexao.execute();
		ca_conexao.close();
		}
		tela.confirmar = true;
	}catch(Exception e){
		System.out.println("N�o foi poss�vel salvar a associa��o dos sintomas n�o associados pelo usu�rio.");
	}
	//Atribuindo evid�ncias favor�veis e evid�ncias contr�rias aos sintomas n�o escolhidos pelo usu�rio.
	comandosSQL = null;
	comandosSQL = "UPDATE associacoes SET EvidFavo1 = 0.0, EvidContr1 = 1.0, EvidFavo2 = 0.0, EvidContr2 = 1.0, EvidFavo3 = 0.0, EvidContr3 = 1.0, EvidFavo4 = 0.0, EvidContr4 = 1.0 WHERE Doenca_idDoenca = ? AND Sintoma_idSintoma = ?";
	try{
	for(int i = 0; i < sintomasNaoAssociados.size();i++){
	PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
	ca_conexao.setInt(1, doenca.getCoddoenca());
	ca_conexao.setInt(2, (int) sintomasNaoAssociados.get(i));
	ca_conexao.execute();
	ca_conexao.close();	
	}
	//Incluindo evid�ncias favor�veis e evid�ncias contr�rias nos sintomas associados as doen�as n�o escolhidas.
	for(int inicia = 0; inicia <tela.doencasNAssociadas.size();inicia ++){
		for(int i = 0; i<idSintomasStatus.size();i++){
			PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
			ca_conexao.setInt(1, tela.doencasNAssociadas.get(inicia));
			ca_conexao.setInt(2, idSintomasStatus.get(i));
			ca_conexao.execute();
			ca_conexao.close();
		}
	}
}catch(Exception e){
	System.out.println("N�o foi poss�vel realizar a inclus�o de evid�ncias para os sintomas e doen�as n�o relacionados.");
}
	
}

//M�todo para preenchimento da tabela de doen�as pendentes de cadastro de evid�ncias
public void preencherDoencasPendentes(TelaPrincipal tela){
	comandosSQL = null;
	comandosSQL = "SELECT * FROM doenca WHERE status_cadastro = 0";
	abrirConexaoBD();
	tela.modeloDoencasPendentes = (DefaultTableModel) tela.doencasPendentes.getModel();
	try{
		//pegando informa��es no banco
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ResultSet result = ca_conexao.executeQuery();
		while(result.next()){ // preenchimento da tabela de sintomas dispon�veis
			tela.modeloDoencasPendentes.addRow(new String[] {result.getInt("idDoenca")+"�"+
			result.getString("NomeDoenca")});
		}
	}catch(Exception e){
		System.out.println("Ocorreu um erro no preenchimento da tabela.");
	}	
	fechaConexaoBD();
}
//M�todo para preencher os sintomas na tabela de associa��o
public void preencherSintomas(TelaPrincipal tela){
	comandosSQL = null;
	comandosSQL = "SELECT S.NomeSintoma AS 'Sintomas', S.idSintoma AS 'IDSintomas' FROM sintoma S INNER JOIN associacoes A ON A.Sintoma_idSintoma = S.idSintoma "
			+ "WHERE Doenca_idDoenca = ? AND A.EvidFavo4 IS NULL AND A.EvidContr4 IS NULL";
	abrirConexaoBD();
	
	try{

		tela.modeloTabelaAssociacao = (DefaultTableModel) tela.tabelaAssociacao.getModel();
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, tela.iddoenca);
		ResultSet result = ca_conexao.executeQuery();
		while(result.next()){
			tela.modeloTabelaAssociacao.addRow(new String[] {result.getInt("IDSintomas")+"�"+result.getString("Sintomas")});
		}
	}catch(Exception e){
		System.out.println("Ocorreu um erro no preenchimento da tabela de associa��o.");
	}
	fechaConexaoBD();
}
//M�todo para inser��o de especialistas
public boolean inserirEspecialista(TelaPrincipal tela){
	comandosSQL = null;
	comandosSQL = "INSERT INTO especialista (NomeEspecialista) values(?)";
	abrirConexaoBD();
	try{
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setString(1, tela.campoEspecialista.getText());
		ca_conexao.execute();
		ca_conexao.close();
		fechaConexaoBD();
		return true;
		
	}catch(Exception e){
		System.out.println("N�o foi poss�vel inserir os especialista.");
		fechaConexaoBD();
		return false;
	}
}
//M�todo para preencher o combo de especialistas.
public void preencherComboEspecialista(TelaPrincipal tela){
	comandosSQL = null;
	comandosSQL = "SELECT * FROM especialista";
	abrirConexaoBD();
	try{
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ResultSet result = ca_conexao.executeQuery();
		while(result.next()){ // preenchimento do combobox
		tela.especialista.addItem(result.getInt("idEspecialista")+"�"+result.getString("NomeEspecialista"));
		}
	}catch(Exception e){
		System.out.println("Ocorreu um erro durante o preenchimento do combobox de especialistas.");
	}
	fechaConexaoBD();
}
//Este m�todo verifica as evid�ncias que ainda precisam ser preenchidas.
public boolean verificarEvidencias(TelaPrincipal tela, ModeloEspecialista espec, ModeloDoenca doen){
	comandosSQL = null;
	comandosSQL = "SELECT EvidFavo1, EvidContr1 FROM associacoes WHERE Doenca_idDoenca = ? and Sintoma_idSintoma = ?";
	boolean retorno = false;
	boolean verificaEvidencia = false;
	abrirConexaoBD();
	try{
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, doen.getCoddoenca());
		ca_conexao.setInt(2, (int) tela.sintomas.get(0));
		ResultSet result = ca_conexao.executeQuery();
		while(result.next()){ // verifica qual evidencia favor�vel ser� incluida
		if(result.getString("EvidFavo1") == null && result.getString("EvidContr1") == null){
				verificaEvidencia = true;	
				NumEvidencia = 1;
				retorno = inserirEvidencias(tela,doen,espec);				
			}
		}
		if(verificaEvidencia == false){
			comandosSQL = null;
			comandosSQL = "SELECT EvidFavo2, EvidContr2 FROM associacoes WHERE Doenca_idDoenca = ? and Sintoma_idSintoma = ?";
			ca_conexao = conexao.prepareStatement(comandosSQL);
			ca_conexao.setInt(1, doen.getCoddoenca());
			ca_conexao.setInt(2, (int) tela.sintomas.get(0));
			result = ca_conexao.executeQuery();
			while(result.next()){ // verifica qual evidencia favor�vel ser� incluida
				if(result.getString("EvidFavo2") == null && result.getString("EvidContr2") == null){
					verificaEvidencia = true;
					NumEvidencia = 2;
					retorno = inserirEvidencias(tela,doen,espec);
				}
			}
		}
		if(verificaEvidencia == false){
				comandosSQL = null;
				comandosSQL = "SELECT EvidFavo3, EvidContr3 FROM associacoes WHERE Doenca_idDoenca = ? and Sintoma_idSintoma = ?";
				ca_conexao = conexao.prepareStatement(comandosSQL);
				ca_conexao.setInt(1, doen.getCoddoenca());
				ca_conexao.setInt(2, (int) tela.sintomas.get(0));
				result = ca_conexao.executeQuery();
				while(result.next()){ // verifica qual evidencia favor�vel ser� incluida
					if(result.getString("EvidFavo3") == null && result.getString("EvidContr3") == null){
						verificaEvidencia = true;
						NumEvidencia = 3;
						retorno = inserirEvidencias(tela,doen,espec);
					}
			}
		}
		if(verificaEvidencia == false){
			comandosSQL = null;
			comandosSQL = "SELECT EvidFavo4, EvidContr4 FROM associacoes WHERE Doenca_idDoenca = ? and Sintoma_idSintoma = ?";
			ca_conexao = conexao.prepareStatement(comandosSQL);
			ca_conexao.setInt(1, doen.getCoddoenca());
			ca_conexao.setInt(2, (int) tela.sintomas.get(0));
			result = ca_conexao.executeQuery();
			while(result.next()){ // verifica qual evidencia favor�vel ser� incluida
				if(result.getString("EvidFavo4") == null && result.getString("EvidContr4") == null){
					verificaEvidencia = true;
					System.out.println("chegou4");
					NumEvidencia = 4;
					retorno = inserirEvidencias(tela,doen,espec);
				}
			}
		}
		}catch(Exception e){
		System.out.println("Ocorreu um erro durante a consulta das evid�ncias.");
		retorno = false;
	}	
	return retorno;
}
//M�todo insere evid�ncias para as associa��es
public boolean inserirEvidencias(TelaPrincipal tela, ModeloDoenca doen, ModeloEspecialista espec){
	
	//INSERINDO EVIDENCIAS
	comandosSQL = null;
	System.out.println("inserindo");
	System.out.println("valor de NumEvidencia �: "+NumEvidencia);
	boolean retorno;
	comandosSQL = "UPDATE associacoes SET EvidFavo"+NumEvidencia+" = ?, EvidContr"+NumEvidencia+" = ? WHERE Doenca_idDoenca = ? AND Sintoma_idSintoma = ?";
	abrirConexaoBD();
	try{
	for(int i = 0; i < tela.tabelaAssociacao.getRowCount();i++){
	PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
	ca_conexao.setFloat(1, Float.parseFloat(tela.evidenciasFav.get(i).toString()));
	ca_conexao.setFloat(2, Float.parseFloat(tela.evidenciasCont.get(i).toString()));
	ca_conexao.setInt(3, doen.getCoddoenca());
	ca_conexao.setInt(4, (int) tela.sintomas.get(i));
	ca_conexao.execute();
	ca_conexao.close();
	}
	retorno = true;
	if(NumEvidencia == 4){// todas as evidencias foram caddastradas e n�o � mais necess�rio que a doen�a fique pendente
		comandosSQL = "UPDATE doenca SET status_cadastro = 1 WHERE idDoenca = ?";
		try{
			PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
			ca_conexao.setInt(1, doen.getCoddoenca());
			ca_conexao.execute();
			ca_conexao.close();
			fechaConexaoBD();
		}catch(Exception e){
			System.out.println("N�o foi poss�vel tirar a flag de doen�a pendente da doenca.");
		}
	}
		}catch(Exception e){
			System.out.println("N�o foi poss�vel inserir as evidencias.");
			fechaConexaoBD();
		retorno = false;;
		}
	inserindoRelacaoAssociacaoEspecialista(tela,doen,espec);
	return retorno;
	}
//M�todo realiza a inser��o da associa��o de evid�ncia e especialista
public void inserindoRelacaoAssociacaoEspecialista(TelaPrincipal tela, ModeloDoenca doen, ModeloEspecialista espec){
	//OBTENDO OS IDS DE ASSOCIACAO
	ArrayList idAssociacao = new ArrayList();
	comandosSQL = null;
	comandosSQL = "SELECT idAssociacao FROM associacoes WHERE Doenca_idDoenca = ? AND Sintoma_idSintoma = ?";
	abrirConexaoBD();
	try{
		for(int i = 0; i< tela.tabelaAssociacao.getRowCount();i++){
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, doen.getCoddoenca());
		ca_conexao.setInt(2, (int) tela.sintomas.get(i));
		System.out.println("SINTOMA= "+ tela.sintomas.get(i));
		ResultSet result = ca_conexao.executeQuery();
		while(result.next()){
			idAssociacao.add(result.getInt("idAssociacao"));
			System.out.println("IDASSOCIACAO= "+result.getInt("idAssociacao"));
		}
	}
}catch(Exception e){
	System.out.println("N�o foi poss�vel consultar os IDs das associa��es.");
}
	//INSERINDO NA TABELA RELACIONAMENTO ASSOCIA��O X ESPECIALISTA
	comandosSQL = null;

	comandosSQL = "INSERT INTO especialista_associacoes (Especialista_idEspecialista,Associacoes_idAssociacao,NumeroEspecialista) values (?,?,?)";
	try{
		for(int i = 0; i<= idAssociacao.size()-1; i++){
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, espec.getCodespecialista());
		ca_conexao.setInt(2, (int) idAssociacao.get(i));
		ca_conexao.setInt(3, NumEvidencia);
		System.out.println("NumEvidencia"+NumEvidencia);
		System.out.println("IDASSO"+idAssociacao.get(i));
		System.out.println("Especi"+espec.getCodespecialista());
		ca_conexao.execute();
		ca_conexao.close();
		}
	}catch(Exception e){
		System.out.println("N�o foi poss�vel inserir os valores na tabela de relacionamento Associacoes e Especialistas.");
	}
fechaConexaoBD();
}
//M�todo realiza a verifica��o do especialista associado, para n�o inserir evid�ncias novamente.
public boolean verificaEspecialistaAssociado(TelaPrincipal tela, ModeloEspecialista espec, ModeloDoenca doen){
	boolean especialistaAssociado = true;
	comandosSQL = null;
	comandosSQL = "SELECT E.Especialista_idEspecialista AS 'IDEspecialista', E.Associacoes_idAssociacao AS 'IDAssociacoes' "
			+ "FROM especialista_associacoes E INNER JOIN associacoes A ON E.Associacoes_idAssociacao = A.idAssociacao "
			+ "WHERE A.Doenca_idDoenca = ? AND E.Especialista_idEspecialista = ?";
	abrirConexaoBD();
	try{
			PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
			ca_conexao.setInt(1, doen.getCoddoenca());
			ca_conexao.setInt(2, espec.getCodespecialista());
			ResultSet result = ca_conexao.executeQuery();
			while(result.next()){
				especialistaAssociado = false;
				}
	}catch(Exception e){
	System.out.println("Erro ao verificar a rela��o da evid�ncia com o especialista.");	
	}
	fechaConexaoBD();
	return especialistaAssociado;
}
//M�todo carrega os pacientes na tabela de an�lise m�dica.
public void carregarPacientes(TelaPrincipal tela){
	comandosSQL = null;
	comandosSQL = "SELECT * FROM paciente";
	abrirConexaoBD();
	try{
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ResultSet result = ca_conexao.executeQuery();
		while(result.next()){ // ADICIONANDO PACIENTES NA TABELA DE AN�LISE
			tela.modeloAnalise = (DefaultTableModel) tela.tabelaPacienteAnalise.getModel();
			tela.modeloAnalise.addRow(new String[] {result.getInt("IdPaciente")+"�"+result.getString("NomePaciente")}); 
		}
	}catch(Exception e){
		System.out.println("N�o foi poss�vel preencher os pacientes na tabela de an�lise.");
	}
	fechaConexaoBD();
}
//M�todo realiza a captura de dados para inser��o na tela de an�lise m�dica.
public void carregarAnalisePaciente(TelaPrincipal tela, ModeloPaciente paci){
	comandosSQL = null;
	comandosSQL = "SELECT * FROM paciente WHERE idPaciente = ?";
	String comandosSQL2 = null;
	comandosSQL2 = "SELECT S.NomeSintoma AS 'Sintomas' FROM sintoma S INNER JOIN paciente_sintoma"
			+ " PA ON PA.Sintoma_idSintoma = S.idSintoma WHERE PA.Paciente_idPaciente = ?";
	abrirConexaoBD();
	try{
		// CAPTURANDO INFORMA��ES B�SICAS DO PACIENTE
		PreparedStatement ca_conexao = conexao.prepareStatement(comandosSQL);
		ca_conexao.setInt(1, paci.getCodPaciente());
		ResultSet result = ca_conexao.executeQuery();
		while(result.next()){
			paci.setNomePaciente(result.getString("NomePaciente"));
			paci.setIdadePaciente(result.getInt("IdadePaciente"));
			paci.setComentario(result.getString("comentario"));
		}
		//CAPTURANDO TODOS OS SINTOMAS
		ca_conexao = conexao.prepareStatement(comandosSQL2);
		ca_conexao.setInt(1, paci.getCodPaciente());
		result = ca_conexao.executeQuery();
		tela.sintomasPaciente.clear();
		while(result.next()){
			tela.sintomasPaciente.add(result.getString("Sintomas"));
		}
	}catch(Exception e){
		System.out.println("N�o foi poss�vel recuperar as informa��es do paciente.");
	}
}
}