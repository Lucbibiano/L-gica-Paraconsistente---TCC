package Controle;

import java.sql.*;

public class ConnectionFactory {
String driver = "com.mysql.jdbc.Driver";
String endereco = "jdbc:mysql://localhost:3306/diagnostico"; // endereço do BD
String usuario = "root"; // usuário do banco de dados
String senha = "2095"; // senha do banco de dados
Connection con;

public Connection getConnection(){
	try{
		Class.forName(driver);
		con = DriverManager.getConnection(endereco, usuario, senha); // entrando com os valores do banco
		System.out.println("Conexão estabelecida.");
	}
	catch(ClassNotFoundException e){ // Exceção caso o driver do banco de dados não seja encontrado
		System.out.println("O driver do banco de dados não foi encontrado.");
	}
	catch(SQLException e){ // Exceção caso ocorra problemas de conexão com o banco de dados.
		System.out.println("Problemas na conexão com os dados.");
	}
	catch(Exception e){ // Exceção para outros problemas menos especificos.
		e.printStackTrace();
		System.out.println("O sistema encontrou algum problema de conexão.");
	}
	return con;
}

}

