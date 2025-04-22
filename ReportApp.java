import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ReportApp extends JPanel
{
	private ComponentMap components;
	private JPanel report;
	
	// Construct Application
	
	public ReportApp() throws SQLException
	{
		// Initialize Panel
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.GREEN));
		
		// N-Number Input
		
		AppTextField nNumber = new AppTextField();
		nNumber.setName("N-Number");
		
		// Report Panel
		
		this.report = new JPanel();
		this.report.setLayout(new BoxLayout(this.report, BoxLayout.Y_AXIS));
		this.report.setBorder(new LineBorder(Color.GREEN));
		
		// Submit Button
		
		JButton submit = new JButton("Generate Grade Report");
		
		submit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					submit();
				} catch (SQLException error) {
					error.printStackTrace();
				}
			}
		});
		
		// Add Components to Panel
		
		this.add(new JLabel("Student N-Number:"));
		this.add(nNumber);
		this.add(submit);
		this.add(this.report);
		
		// Initialize Component Map
		
		this.components = new ComponentMap();
		this.components.put(this);
	}

	// Evaluate Application Input
	
	private boolean evaluate()
	{
		AppTextField nNumber = (AppTextField) this.components.getComponent("N-Number");
		
		return nNumber.evaluate();
	}
	
	// Request from Database
	
	private void submit() throws SQLException
	{
		if (this.evaluate())
		{
			this.update();
		}
	}
	
	// Update Application
	
	private void update() throws SQLException
	{
		// Clear
		
		this.report.removeAll();
		
		// Get Student Nnumber
		
		String nNumber = (String) this.components.getValue("N-Number");
		
		// Get Student Information
		
		DBC.connect();
		
		Connection conn = DBC.get();
		
		String personQuery = "SELECT Fname, Minit, Lname, Phone, Street, City, State_, Zip, Bdate, Gender FROM PERSON WHERE Nnumber=?";
		String studentQuery = "SELECT Curr_street, Curr_city, Curr_state, Curr_zip, Class_, Degree_pgrm, Major_dept, Minor_dept FROM STUDENT WHERE Nnumber=?";
		
		PreparedStatement personStmt = conn.prepareStatement(personQuery);
		PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
		
		personStmt.setString(1, nNumber);
		studentStmt.setString(1, nNumber);
		
		ResultSet personSet = personStmt.executeQuery();
		ResultSet studentSet = studentStmt.executeQuery();
		
		personSet.next();
		studentSet.next();
		
		String fName = personSet.getString("Fname");
		String mInit = personSet.getString("Minit");
		String lName = personSet.getString("Lname");
		String phone = personSet.getString("Phone");
		String pStreet = personSet.getString("Street");
		String pCity = personSet.getString("City");
		String pState = personSet.getString("State_");
		String pZip = personSet.getString("Zip");
		String cStreet = studentSet.getString("Curr_street");
		String cCity = studentSet.getString("Curr_city");
		String cState = studentSet.getString("Curr_state");
		String cZip = studentSet.getString("Curr_zip");
		Date bDate = personSet.getDate("Bdate");
		String gender = personSet.getString("Gender");
		String studentClass = studentSet.getString("Class_");
		String degreePgrm = studentSet.getString("Degree_pgrm");
		String major = studentSet.getString("Major_dept");
		String minor = studentSet.getString("Minor_dept");
		
		this.report.add(new JLabel("Name: " + fName + " " + mInit + " " + lName));
		this.report.add(new JLabel("Phone Number: " + phone));
		this.report.add(new JLabel("Permanent Address: " + pStreet + ", " + pCity + ", " + pState + ", " + pZip));
		this.report.add(new JLabel("Current Address: " + cStreet + ", " + cCity + ", " + cState + ", " + cZip));
		this.report.add(new JLabel("Birthdate: " + bDate));
		this.report.add(new JLabel("Gender: " + gender));
		this.report.add(new JLabel("Class: " + studentClass));
		this.report.add(new JLabel("Degree Program: " + degreePgrm));
		this.report.add(new JLabel("Major: " + major));
		this.report.add(new JLabel("Minor: " + minor));
		
		personStmt.close();
		studentStmt.close();
		
		personSet.close();
		studentSet.close();
		
		DBC.disconnect();

		// Get Enrolled Sections from Database
		
		DBC.connect();
		
		conn = DBC.get();
		
		String courseQuery = "SELECT Course, Grade FROM ENROLLED_IN WHERE Student=?";
		
		PreparedStatement courseStmt = conn.prepareStatement(courseQuery);
		
		courseStmt.setString(1, nNumber);
		
		ResultSet courseSet = courseStmt.executeQuery();
		
		int courseCount = DBC.queryCount("FROM ENROLLED_IN WHERE Student='" + nNumber + "'");
		
		String[] header = {"Course", "Letter Grade", "Course GPA"};
		
		Object[][] courses = new Object[courseCount][3];
		
		for (int i = 0; i < courseCount; i++)
		{
			courseSet.next();
			
			String grade = courseSet.getString("Grade");
			
			courses[i][0] = courseSet.getString("Course");
			courses[i][1] = grade;
			courses[i][2] = Utilities.letterToGPA(grade);
		}
		
		JTable table = new JTable(new DefaultTableModel(courses, header));
		
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		this.report.add(new JLabel("Grades:"));
		this.report.add(scrollPane);
		
		DBC.disconnect();
		
        TableModel model = table.getModel();
        
        int rowCount = model.getRowCount();
        
        double sum = 0;

        for (int row = 0; row < rowCount; row++) {
            sum += (double) model.getValueAt(row, 2);
        }
        
        double gpa = sum / rowCount;
        
        this.report.add(new JLabel("Cumulative GPA: " + gpa));
		
		// Refresh page
		
		this.report.updateUI();
		this.updateUI();
	}

}
