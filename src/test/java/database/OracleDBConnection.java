package database;

import java.sql.Connection;
import java.sql.SQLException;
import base.Log;
import oracle.jdbc.pool.OracleDataSource;


public class OracleDBConnection {
	public  final static String DB_URL="jdbc:oracle:thin:@192.168.10.18:1521:xtdocqa";
	public  final static String DB_USERNAME="xtdocs";
	public  final static String DB_PASSWORD="xtdqaxtdocs16";
	public static Connection con;
	public OracleDataSource ds =null;
	public OracleDBConnection() throws SQLException{
		ds = new OracleDataSource();
		Log.info("Database connection is going to establish");
		ds.setURL(DB_URL);
		con = ds.getConnection(DB_USERNAME, DB_PASSWORD);
	}

}
