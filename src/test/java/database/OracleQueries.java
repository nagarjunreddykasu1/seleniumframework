package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleQueries {
public OracleDBConnection db=null;
	
	public OracleQueries() throws SQLException{
		db=new OracleDBConnection();
	}
	
	public ResultSet dbLookupValues() throws SQLException{

		String	stmt=" SELECT GL.LOOKUP_DISPLAY_NAME FROM GENERIC_LOOKUP GL "
				+ " JOIN CLIENT_INSTANCE_MASTER CIM ON CIM.CLIENT_INSTANCE_ID=GL.CLIENT_INSTANCE_ID "
				+ " JOIN CLIENT_MASTER CM ON CM.CLIENT_ID = CIM.CLIENT_ID WHERE GL.LOOKUP_NAME = 'Abaco_PaymentMethod' "
				+ " AND CIM.INSTANCE_NAME = 'Abaco' AND CM.CLIENT_NAME='Abaco' AND GL.ACTIVE_FLAG='Y' ";
		
		PreparedStatement ps=OracleDBConnection.con.prepareStatement(stmt);
		ResultSet rs=ps.executeQuery();	
		return rs;	
	}

}
