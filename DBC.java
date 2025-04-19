import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBC
{
	private static Connection conn = null;
	private final static String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";
	private final static String uid = "n01577055";
    private final static String pword = "7055";
	
	public static void connect() throws SQLException
	{
		if (conn == null)
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		    
		    conn = DriverManager.getConnection(url, uid, pword);
		}
	}
	
	public static Connection get()
	{
		if (conn != null)
		{
			return conn;
		}
		
		return null;
	}
	
	public static void disconnect() throws SQLException
	{
		if (conn != null)
		{
			conn.close();
			conn = null;
		}
	}
}
