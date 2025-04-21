import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class CourseApp extends JPanel
{
	private ComponentMap components;
	private String[][] departments;
	
	// Construct Application
	
	public CourseApp() throws SQLException
	{
		// Initialize Panel
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.GREEN));
		
		// Retrieve Departments from Database
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String query = "SELECT Dcode, Dname FROM DEPARTMENT;";
				
		PreparedStatement stmt = conn.prepareStatement(query);
		
		ResultSet set = stmt.executeQuery();
		
		DBC.disconnect();
		
		// Department
		
		int count = resultSetSize(set);
		
		this.departments = new String[count][2];
		
		for (int i = 0; i < count; i++)
		{
			set.next();
			
			this.departments[i][0] = set.getString("Dcode");
			this.departments[i][1] = set.getString("Dname");
		}
		
		String[] options = new String[count];
		
		for (int i = 0; i < count; i++)
		{
			options[i] = this.departments[i][0] + ": " + this.departments[i][1];
		}
		
		JComboBox<String> dept = new JComboBox<>(options);
		dept.setName("Department");
		
		// Add Components to Panel
		
		this.add(dept);
		
		// Component Map
		
		this.components = new ComponentMap(this);
	}
	
	// Update Applicationn
	
	public void update() throws SQLException
	{
		int selected = ((JComboBox<String>) this.components.getComponent("Department")).getSelectedIndex();
		String code = this.departments[selected][0];
		
		// Get Offered Courses from Database
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String query = "SELECT Cnumber, Cname, Cdesc FROM COURSE WHERE Dept=\"?\";";
		
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, code);
		
		ResultSet set = stmt.executeQuery();
		
		DBC.disconnect();
		
		int count = resultSetSize(set);
		
		String[][] courses = new String[count][3];
		
		for (int i = 0; i < count; i++)
		{
			set.next();
			
			courses[i][0] = set.getString("Cnumber");
			courses[i][1] = set.getString("Cname");
			courses[i][2] = set.getString("Cdesc");
		}
		
		// Table
		
		String[] header = {"Code", "Name", "Description"};
		
		JTable table = new JTable(new DefaultTableModel(courses, header));
		
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Finalize
		
		this.add(scrollPane);
		
		this.updateUI();
	}
	
	private int resultSetSize(ResultSet rs) throws SQLException
	{
		int count = 0;
		
        if (rs != null)
        {
            rs.beforeFirst();

            while (rs.next())
            {
                count++;
            }
            
            rs.beforeFirst();
        }
        
        return count;
	}
}
