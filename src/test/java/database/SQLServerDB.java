package database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import base.Log;
import net.sourceforge.jtds.jdbc.Driver;

public class SQLServerDB {
	
	public Connection con;
	public Properties dbConfig;
	public SQLServerDB() throws IOException, SQLException{
		FileInputStream db=new FileInputStream(System.getProperty("user.dir")+"//src//test//java//configuration//database.properties");
		dbConfig=new Properties();
		dbConfig.load(db);
		String connectionUrl=dbConfig.getProperty("SQLServerConnection");
		DriverManager.registerDriver(new Driver()); //net.sourceforge.jtds.jdbc.Driver() 
		con = DriverManager.getConnection(connectionUrl);
		Log.info("DB connection was established.");
	}
	
	public ArrayList<String> getJobTitles() throws SQLException {
		String query = "SELECT * FROM mtblJobTitle WHERE mIsActive=1";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		ArrayList<String> al = new ArrayList<String>();
		while (rs.next()) {
			al.add(rs.getString("mJobTitleName"));
		}
		return al;
	}

}
