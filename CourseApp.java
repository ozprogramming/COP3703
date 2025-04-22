import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JPanel list;
	
	// Construct Application
	
	public CourseApp() throws SQLException
	{
		// Initialize Panel
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.GREEN));
		
		JComboBox<String> dept = new JComboBox<>(DBC.Departments());
		
		dept.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				try 
				{
					update();
				} 
				catch (SQLException error)
				{
					error.printStackTrace();
				}
			}
		});
		
		dept.setName("Department");
		
		// Initialize Listings Panel
		
		this.list = new JPanel();
		
		this.list.setLayout(new BoxLayout(this.list, BoxLayout.Y_AXIS));
		this.list.setBorder(new LineBorder(Color.GREEN));
		
		// Add Components to Panel
		
		this.add(dept);
		this.add(this.list);
		
		// Component Map
		
		this.components = new ComponentMap(this);
		
		// Populate Courses
		
		this.update();
	}
	
	// Update Applicationn
	
	public void update() throws SQLException
	{
		this.list.removeAll();
		
		String name = (String) this.components.getValue("Department");
		
		// Get Department Code by Department Name
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String dCodeQuery = "SELECT Dcode FROM DEPARTMENT WHERE Dname=?";
	    
	    PreparedStatement dCodeStmt = conn.prepareStatement(dCodeQuery);
	    
	    dCodeStmt.setString(1, name);
	    
	    ResultSet dCodeSet = dCodeStmt.executeQuery();  
	    
	    dCodeSet.next();
	    
	    String code = dCodeSet.getString("Dcode");
	    
	    dCodeStmt.close();
	    
	    dCodeSet.close();
	    
	    // Get Offered Courses
		
		String query = "SELECT Cnumber, Cname, Cdesc, Level_, Hours_ FROM COURSE WHERE Dept=?";
		
		PreparedStatement stmt = conn.prepareStatement(query);
		
		stmt.setString(1, code);
		
		ResultSet set = stmt.executeQuery();
		
		int count = DBC.queryCount("SELECT Cnumber, Cname, Cdesc, Level_, Hours_ FROM COURSE WHERE Dept='" + code + "'");
		
		Object[][] courses = new Object[count][5];
		
		for (int i = 0; i < count; i++)
		{
			set.next();
			
			courses[i][0] = set.getString("Cnumber");
			courses[i][1] = set.getString("Cname");
			courses[i][2] = set.getString("Cdesc");
			courses[i][3] = set.getBigDecimal("Level_");
			courses[i][4] = set.getInt("Hours_");
		}
		
		stmt.close();
		
		set.close();
		
		DBC.disconnect();
		
		// Table
		
		String[] header = {"Code", "Name", "Description", "Level", "Credit Hours"};
		
		JTable table = new JTable(new DefaultTableModel(courses, header));
		
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Finalize
		
		this.list.add(scrollPane);
		
		this.list.updateUI();
		this.updateUI();
	}
}
