import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class RegistrationApp
{
	private JPanel panel;
	private ComponentMap components;
	
	public RegistrationApp() throws SQLException
	{
		// Initialize Panel
		
		this.panel = new JPanel();
		this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
		this.panel.setBorder(new LineBorder(Color.GREEN));
		
		// N-Number Input
		
		JTextField nNumber = new JTextField();
		nNumber.setName("N-Number");
		
		// Get Courses from Database
		
		DBC.connect();
		Connection conn = DBC.get();
		conn = null;
		DBC.disconnect();
		
		// Course Dropdown
		
		String[] courses = {};
		JComboBox<String> course = new JComboBox<>();
		course.setName("Course");
		course.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
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
		
		// Get Sections of Course from Database
		
		DBC.connect();
		conn = DBC.get();
		conn = null;
		DBC.disconnect();
		
		// Section Table
		
		String[][] sections = {{}};
		String[] header = {"Instructor", "Semester", "Year", "Section Number"};
		
		JTable table = new JTable(new DefaultTableModel(sections, header));
		table.setName("Sections");
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Submit Button
		
		JButton submitButton = new JButton("Enroll Student in Course");
		submitButton.setName("Enrollment Button");
		
		// Add Components
		
		this.panel.add(new JLabel("Student N-Number:"));
		this.panel.add(nNumber);
		
		this.panel.add(new JLabel("Course:"));
		this.panel.add(course);
		
		this.panel.add(new JLabel("Sections:"));
		this.panel.add(scrollPane);

		this.panel.add(submitButton);
		
		// Create Component Map
		
		this.components = new ComponentMap(this.panel);
	}
	
	private void update() throws SQLException
	{
		String course = (String) ((JComboBox<String>) this.components.getComponent("Course")).getSelectedItem();
		
		JTable table = (JTable) this.components.getComponent("Sections");
		
		// Get Sections of Course from Database
		
		DBC.connect();
		Connection conn = DBC.get();
		String[][] sections = {{}};
		conn = null;
		DBC.disconnect();
		
		// Repopulate Table
		
		String[] header = {"Instructor", "Semester", "Year", "Section Number"};
		
		table.setModel(new DefaultTableModel(sections, header));
		
		table.updateUI();
	}
}