/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao_db;
import java.sql.*;

/**
 *
 * @author ninguem
 */
public class ModuloConexao {
    //Método responsavel por estabelcer a conexão com o banco
    public static Connection conector(){
        java.sql.Connection conexao = null;
        //A linha abaixo chama o driver
        String driver = "com.mysql.cj.jdbc.Driver";
        
        //Armazenando informações referentes ao banco
        String url = "jdbc:mysql://localhost:3306/db_barbearia";
        String user = "root";
        String password = "";
        
        //Estabelecendo a conexão com o banco de dados
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
}
