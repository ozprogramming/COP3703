import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public static int queryCount(String query) throws SQLException
	{
		if (conn != null)
		{
			int count = 0;
			
			int start = query.indexOf("FROM");
			
			if (start != -1)
			{
				String sub = query.substring(start);
				
				String countQuery = "SELECT COUNT(*) " + sub;
				
				PreparedStatement countStmt = conn.prepareStatement(countQuery);
				
				ResultSet countSet = countStmt.executeQuery();
				
				if (countSet.next())
				{
					count = countSet.getInt(1);
				}
				
				countStmt.close();
				countSet.close();
			}
	        
	        return count;
		}
		
		return 0;
	}

	public static String[] Departments() throws SQLException
	{
		DBC.connect();
		
		String query = "SELECT Dname FROM DEPARTMENT";
		
		PreparedStatement stmt = conn.prepareStatement(query);
		
		ResultSet set = stmt.executeQuery();
		
		int count = DBC.queryCount(query);
		
		String[] departments = new String[count];
		
		for (int i = 0; i < count; i++)
		{
			set.next();
			
			departments[i] = set.getString("Dname");
		}
		
		stmt.close();
		set.close();
		
		DBC.disconnect();
		
		return departments;
	}

	public static String[] Courses() throws SQLException
	{
		DBC.connect();
		
		conn = DBC.get();
		
		String query = "SELECT Cname FROM COURSE";
		
		PreparedStatement stmt = conn.prepareStatement(query);
		
		ResultSet set = stmt.executeQuery();
		
		int count = DBC.queryCount(query);
		
		String[] courses = new String[count];
		
		for (int i = 0; i < count; i++)
		{
			set.next();
			
			courses[i] = set.getString("Cname");
		}
		
		stmt.close();
		set.close();
		
		DBC.disconnect();
		
		return courses;
	}

	public static String[] Instructors() throws SQLException
	{
		DBC.connect();
		
		conn = DBC.get();
		
		String query = "SELECT PERSON.Fname, PERSON.Lname, INSTRUCTOR.Nnumber FROM PERSON, INSTRUCTOR WHERE PERSON.Nnumber=INSTRUCTOR.Nnumber";
		
		PreparedStatement stmt = conn.prepareStatement(query);
		
		ResultSet set = stmt.executeQuery();
		
		int count = DBC.queryCount(query);
		
		String[] instructors = new String[count];
		
		for (int i = 0; i < count; i++)
		{
			set.next();
			
			instructors[i] = set.getString("Fname") + " " + set.getString("Lname") + " (" + set.getString("Nnumber") + ")";
		}
		
		stmt.close();
		set.close();
		
		DBC.disconnect();
		
		return instructors;
	}	
}
