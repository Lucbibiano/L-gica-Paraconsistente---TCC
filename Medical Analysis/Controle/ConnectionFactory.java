package Controle;

import java.sql.*;

public class ConnectionFactory {
String driver = "com.mysql.jdbc.Driver";
String endereco = "jdbc:mysql://localhost:3306/diagnostico"; // endere�o do BD
String usuario = "root"; // usu�rio do banco de dados
String senha = "2095"; // senha do banco de dados
Connection con;

public Connection getConnection(){
	try{
		Class.forName(driver);
		con = DriverManager.getConnection(endereco, usuario, senha); // entrando com os valores do banco
		System.out.println("Conex�o estabelecida.");
	}
	catch(ClassNotFoundException e){ // Exce��o caso o driver do banco de dados n�o seja encontrado
		System.out.println("O driver do banco de dados n�o foi encontrado.");
	}
	catch(SQLException e){ // Exce��o caso ocorra problemas de conex�o com o banco de dados.
		System.out.println("Problemas na conex�o com os dados.");
	}
	catch(Exception e){ // Exce��o para outros problemas menos especificos.
		e.printStackTrace();
		System.out.println("O sistema encontrou algum problema de conex�o.");
	}
	return con;
}

}

