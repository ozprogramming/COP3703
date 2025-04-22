import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

public class InsertApp extends JPanel
{
	private JPanel form;
	private ComponentMap components;
	
	// Construct Application
	
	public InsertApp() throws SQLException
	{
		// Initialize Panel
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new LineBorder(Color.GREEN));
		
		// Initialize Form
		
		this.form = new JPanel();
		this.form.setLayout(new BoxLayout(this.form, BoxLayout.Y_AXIS));
		this.form.setBorder(new LineBorder(Color.GREEN));
		
		// Entity
		
		String[] entities = {"Department", "Student", "Instructor", "Course", "Section"};
		JComboBox<String> entity = new JComboBox<>(entities);
		entity.setName("Entity");
		entity.addActionListener(new ActionListener()
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
		String name = (String) entity.getSelectedItem();
		
		// Submit Button
		
		JButton submit = new JButton("Insert " + name);
		submit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				try
				{
					submit();
				}
				catch (SQLException error)
				{
					error.printStackTrace();
				}
			}
		});
		submit.setName("Submit");
		
		// Initialize Component Map
		
		this.components = new ComponentMap();
		this.components.append(entity);
		this.components.append(submit);
		
		// Set Form
		
		this.add(entity);
		this.add(this.form);
		this.add(submit);
		
		this.update();
	}
	
	// Update Application
	
	private void update() throws SQLException
	{
		JButton submit = (JButton) components.getComponent("Submit");
		
		String entity = (String) this.components.getValue("Entity");
		
		this.form.removeAll();
		
		if (entity.equals("Department"))
		{
			// Department Code
			AppTextField code = new AppTextField();
			code.setName("Department Code");
			
			
			// Department Name
			AppTextField name = new AppTextField();
			name.setName("Department Name");
			
			// Office Number
			AppTextField oNum = new AppTextField();
			oNum.setName("Office Number");
			
			// College
			AppTextField college = new AppTextField();
			college.setName("College");
			
			// Office Phone
			AppTextField oPhone = new AppTextField();
			oPhone.setName("Office Phone");
			
			// Add Components
			
			this.form.add(new JLabel("Department Code:"));
			this.form.add(code);
			
			this.form.add(new JLabel("Department Name:"));
			this.form.add(name);
			
			this.form.add(new JLabel("College:"));
			this.form.add(college);
			
			this.form.add(new JLabel("Office Number:"));
			this.form.add(oNum);
			
			this.form.add(new JLabel("Office Phone:"));
			this.form.add(oPhone);
		}
		else if (entity.equals("Student"))
		{
			// N-Number
			AppTextField nNum = new AppTextField();
			nNum.setName("N-Number");
			
			// SSN
			AppTextField ssn = new AppTextField();
			ssn.setName("SSN");
			
			// First Name
			AppTextField fName = new AppTextField();
			fName.setName("First Name");
			
			// Middle Initial
			AppTextField mInit = new AppTextField();
			mInit.setName("Middle Initial");
						
			// Last Name
			AppTextField lName = new AppTextField();
			lName.setName("Last Name");

			// Phone Number
			AppTextField pNum = new AppTextField();
			pNum.setName("Phone Number");
			
			// Permanent Street
			AppTextField pStreet = new AppTextField();
			pStreet.setName("Permanent Street");
			
			// Permanent City
			AppTextField pCity = new AppTextField();
			pCity.setName("Permanent City");
			
			// Permanent State
			JComboBox<String> pState = new JComboBox<>(Utilities.AmericanStates());
			pState.setName("Permanent State");
			
			// Permanent ZIP
			AppTextField pZip = new AppTextField();
			pZip.setName("Permanent ZIP");
			
			// Current Street
			AppTextField cStreet = new AppTextField();
			cStreet.setName("Current Street");
			
			// Current City
			AppTextField cCity = new AppTextField();
			cCity.setName("Current City");
			
			// Current State
			JComboBox<String> cState = new JComboBox<>(Utilities.AmericanStates());
			cState.setName("Current State");

			// Current ZIP
			AppTextField cZip = new AppTextField();
			cZip.setName("Current ZIP");
			
			// Gender
			JComboBox<String> gender = new JComboBox<>(Utilities.Genders());
			gender.setName("Gender");
			
			// Birthdate
			Calendar calendar = Calendar.getInstance();
	        Date initialDate = calendar.getTime();
	        String dateFormat = "MM/dd/yyyy";
	        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
	        SpinnerDateModel dateModel = new SpinnerDateModel(initialDate, null, null, Calendar.DAY_OF_MONTH);
	        JSpinner bDate = new JSpinner(dateModel);
	        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(bDate, dateFormat);
	        bDate.setEditor(dateEditor);
	        bDate.setName("Birthdate");
	        
	        // Class
			JComboBox<String> studentClass = new JComboBox<>(Utilities.StudentClasses());
			studentClass.setName("Class");
			
			// Degree Program
			JComboBox<String> program = new JComboBox<>(Utilities.DegreePrograms());
			program.setName("Degree Program");
			
			// Major Department
			JComboBox<String> major = new JComboBox<>(DBC.Departments());
			major.setName("Major Department");
			
			// Minor Department
			JComboBox<String> minor = new JComboBox<>(DBC.Departments());
			minor.setName("Minor Department");
			
			// Add Components
			
			this.form.add(new JLabel("N-Number:"));
			this.form.add(nNum);
			
			this.form.add(new JLabel("SSN:"));
			this.form.add(ssn);
			
			this.form.add(new JLabel("First Name:"));
			this.form.add(fName);
			
			this.form.add(new JLabel("Middle Initial:"));
			this.form.add(mInit);
			
			this.form.add(new JLabel("Last Name:"));
			this.form.add(lName);
			
			this.form.add(new JLabel("Phone Number:"));
			this.form.add(pNum);
			
			this.form.add(new JLabel("Permanent Street:"));
			this.form.add(pStreet);
			
			this.form.add(new JLabel("Permanent City:"));
			this.form.add(pCity);
			
			this.form.add(new JLabel("Permanent State:"));
			this.form.add(pState);
			
			this.form.add(new JLabel("Permanent ZIP:"));
			this.form.add(pZip);
			
			this.form.add(new JLabel("Current Street:"));
			this.form.add(cStreet);
			
			this.form.add(new JLabel("Current City:"));
			this.form.add(cCity);
			
			this.form.add(new JLabel("Current State:"));
			this.form.add(cState);
			
			this.form.add(new JLabel("Current ZIP:"));
			this.form.add(cZip);
			
			this.form.add(new JLabel("Gender:"));
			this.form.add(gender);
			
			this.form.add(new JLabel("Birthdate:"));
			this.form.add(bDate);
			
			this.form.add(new JLabel("Class:"));
			this.form.add(studentClass);
			
			this.form.add(new JLabel("Degree Program:"));
			this.form.add(program);
			
			this.form.add(new JLabel("Major Department:"));
			this.form.add(major);
			
			this.form.add(new JLabel("Minor Department:"));
			this.form.add(minor);
		}
		else if (entity.equals("Instructor"))
		{
			// N-Number
			AppTextField nNum = new AppTextField();
			nNum.setName("N-Number");
			
			// SSN
			AppTextField ssn = new AppTextField();
			ssn.setName("SSN");
			
			// First Name
			AppTextField fName = new AppTextField();
			fName.setName("First Name");
			
			// Middle Initial
			AppTextField mInit = new AppTextField();
			mInit.setName("Middle Initial");
						
			// Last Name
			AppTextField lName = new AppTextField();
			lName.setName("Last Name");

			// Phone Number
			AppTextField pNum = new AppTextField();
			pNum.setName("Phone Number");
			
			// Street
			AppTextField street = new AppTextField();
			street.setName("Street");
			
			// City
			AppTextField city = new AppTextField();
			city.setName("City");
			
			// State
			JComboBox<String> state = new JComboBox<>(Utilities.AmericanStates());
			state.setName("State");
			
			// ZIP
			AppTextField zip = new AppTextField();
			zip.setName("ZIP");
			
			// Gender
			JComboBox<String> gender = new JComboBox<>(Utilities.Genders());
			gender.setName("Gender");
			
			// Birthdate
			Calendar calendar = Calendar.getInstance();
	        Date initialDate = calendar.getTime();
	        String dateFormat = "MM/dd/yyyy";
	        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
	        SpinnerDateModel dateModel = new SpinnerDateModel(initialDate, null, null, Calendar.DAY_OF_MONTH);
	        JSpinner bDate = new JSpinner(dateModel);
	        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(bDate, dateFormat);
	        bDate.setEditor(dateEditor);
	        bDate.setName("Birthdate");
			
			// Department
			JComboBox<String> dept = new JComboBox<>(DBC.Departments());
			dept.setName("Department");
			
			// Office Number
			AppTextField oNum = new AppTextField();
			oNum.setName("Office Number");
			
			// Add Components
			
			this.form.add(new JLabel("N-Number:"));
			this.form.add(nNum);
			
			this.form.add(new JLabel("SSN:"));
			this.form.add(ssn);
			
			this.form.add(new JLabel("First Name:"));
			this.form.add(fName);
			
			this.form.add(new JLabel("Middle Initial:"));
			this.form.add(mInit);
			
			this.form.add(new JLabel("Last Name:"));
			this.form.add(lName);
			
			this.form.add(new JLabel("Phone Number:"));
			this.form.add(pNum);
			
			this.form.add(new JLabel("Street:"));
			this.form.add(street);
			
			this.form.add(new JLabel("City:"));
			this.form.add(city);
			
			this.form.add(new JLabel("State:"));
			this.form.add(state);
			
			this.form.add(new JLabel("ZIP:"));
			this.form.add(zip);
			
			this.form.add(new JLabel("Gender:"));
			this.form.add(gender);
			
			this.form.add(new JLabel("Birthdate:"));
			this.form.add(bDate);
			
			this.form.add(new JLabel("Department:"));
			this.form.add(dept);
			
			this.form.add(new JLabel("Office Number:"));
			this.form.add(oNum);
		}
		else if (entity.equals("Course"))
		{
			// Course Code
			AppTextField code = new AppTextField();
			code.setName("Course Code");
			
			// Course Name
			AppTextField name = new AppTextField();
			name.setName("Course Name");
			
			// Course Description
			AppTextField desc = new AppTextField();
			desc.setName("Course Description");
			
			// Department
			JComboBox<String> dept = new JComboBox<>(DBC.Departments());
			dept.setName("Department");
			
			// Level
			JComboBox<Integer> level = new JComboBox<>(Utilities.Levels());
			level.setName("Course Level");
			
			// Credit Hours
			JComboBox<Integer> credits = new JComboBox<>(Utilities.Credits());
			credits.setName("Credit Hours");
			
			// Add Components
			
			this.form.add(new JLabel("Course Code:"));
			this.form.add(code);
			
			this.form.add(new JLabel("Course Name:"));
			this.form.add(name);
			
			this.form.add(new JLabel("Course Description:"));
			this.form.add(desc);
			
			this.form.add(new JLabel("Department:"));
			this.form.add(dept);
			
			this.form.add(new JLabel("Course Level:"));
			this.form.add(level);
			
			this.form.add(new JLabel("Credit Hours:"));
			this.form.add(credits);
		}
		else if (entity.equals("Section"))
		{
			// Course
			JComboBox<String> course = new JComboBox<>(DBC.Courses());
			course.setName("Course");
			
			// Instructor
			JComboBox<String> instructor = new JComboBox<>(DBC.Instructors());
			instructor.setName("Instructor");
			
			// Semester
			JComboBox<String> semester = new JComboBox<>(Utilities.Semesters());
			semester.setName("Semester");
			
			// Year
			int current = Calendar.getInstance().get(Calendar.YEAR);
			SpinnerNumberModel yearModel = new SpinnerNumberModel(current, current - 25, current + 25, 1);
	        JSpinner year = new JSpinner(yearModel);
	        year.setName("Year");
	        
	        // Add Components
	        
	        this.form.add(new JLabel("Course:"));
	        this.form.add(course);
	        
	        this.form.add(new JLabel("Instructor:"));
	        this.form.add(instructor);
	        
	        this.form.add(new JLabel("Semester:"));
	        this.form.add(semester);
	        
	        this.form.add(new JLabel("Year:"));
	        this.form.add(year);
		}
		
		submit.setText("Insert " + entity);
		
		this.form.updateUI();
		this.updateUI();
		
		this.components.clear();
		this.components.put(this);
		this.components.put(this.form);
	}
	
	// Evaluate Application Input
	
	private boolean evaluate()
	{
		return this.components.evaluateTextComponents();
	}
	
	// Submit to Database
	
	private void submit() throws SQLException
	{
		if (this.evaluate())
		{
			String entity = (String) this.components.getValue("Entity");
			
			if (entity.equals("Department"))
			{
				// Department to Database
				
				DBC.connect();
				
				Connection conn = DBC.get();
				
				String deptQuery = "INSERT INTO DEPARTMENT VALUES (?, ?, ?, ?, ?)";

			    PreparedStatement deptStmt = conn.prepareStatement(deptQuery);
			    
			    deptStmt.setString(1, (String) this.components.getValue("Department Code"));
			    deptStmt.setString(2, (String) this.components.getValue("Department Name"));
			    deptStmt.setString(3, (String) this.components.getValue("College"));
			    deptStmt.setBigDecimal(4, new BigDecimal((String) this.components.getValue("Office Number")));
			    deptStmt.setString(5, (String) this.components.getValue("Office Phone"));
			    
				deptStmt.executeQuery();
				
				deptStmt.close();
			    
				DBC.disconnect();
			}
			else if (entity.equals("Student"))
			{	
				// Person to Database
				
				DBC.connect();
				
				Connection conn = DBC.get();
				
				String personQuery = "INSERT INTO PERSON VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			    PreparedStatement personStmt = conn.prepareStatement(personQuery);
			    
			    personStmt.setString(1, (String) this.components.getValue("N-Number"));
			    personStmt.setString(2, (String) this.components.getValue("SSN"));
			    personStmt.setString(3, (String) this.components.getValue("First Name"));
			    personStmt.setString(4, (String) this.components.getValue("Middle Initial"));
			    personStmt.setString(5, (String) this.components.getValue("Last Name"));
			    personStmt.setString(6, (String) this.components.getValue("Phone Number"));
			    personStmt.setString(7, (String) this.components.getValue("Permanent Street"));
			    personStmt.setString(8, (String) this.components.getValue("Permanent City"));
			    personStmt.setString(9, (String) this.components.getValue("Permanent State"));
			    personStmt.setString(10, (String) this.components.getValue("Permanent ZIP"));
			    personStmt.setString(12, (String) this.components.getValue("Gender"));
			    
			    Object bDate = ((JSpinner) this.components.getComponent("Birthdate")).getValue();
			    
			    if (bDate instanceof Date) // Need to do error checking beforehand
			    {
			    	personStmt.setDate(11, new java.sql.Date(((Date) bDate).getTime()));
			    }
			    
			    personStmt.executeQuery();
			    
			    personStmt.close();
			    
				DBC.disconnect();
				
				// Student to Database
			
				DBC.connect();
				
				conn = DBC.get();
				
				String studentQuery = "INSERT INTO STUDENT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			    PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
			    
			    String majorQuery = "SELECT Dcode FROM DEPARTMENT WHERE Dname=?";
			    String minorQuery = "SELECT Dcode FROM DEPARTMENT WHERE Dname=?";
			    
			    PreparedStatement majorStmt = conn.prepareStatement(majorQuery);
			    PreparedStatement minorStmt = conn.prepareStatement(minorQuery);
			    
			    majorStmt.setString(1, (String) this.components.getValue("Major Department"));
			    minorStmt.setString(1, (String) this.components.getValue("Minor Department"));
			    
			    ResultSet majorSet = majorStmt.executeQuery();
			    ResultSet minorSet = minorStmt.executeQuery();
			    
			    majorSet.next();
			    minorSet.next();
			    
			    String majorCode = majorSet.getString("Dcode");
			    String minorCode = minorSet.getString("Dcode");
			    
			    majorStmt.close();
			    minorStmt.close();
			    
			    majorSet.close();
			    minorSet.close();
			    
			    studentStmt.setString(1, (String) this.components.getValue("N-Number"));
			    studentStmt.setString(2, (String) this.components.getValue("Current Street"));
			    studentStmt.setString(3, (String) this.components.getValue("Current City"));
			    studentStmt.setString(4, (String) this.components.getValue("Current State"));
				studentStmt.setString(5, (String) this.components.getValue("Current ZIP"));
				studentStmt.setString(6, (String) this.components.getValue("Class"));
				studentStmt.setString(7, (String) this.components.getValue("Degree Program"));
				studentStmt.setString(8, majorCode);
				studentStmt.setString(9, minorCode);
			    
				studentStmt.executeQuery();
			    
			    studentStmt.close();
				
				DBC.disconnect();
			}
			else if (entity.equals("Instructor"))
			{
				// Person to Database
				
				DBC.connect();
				
				Connection conn = DBC.get();
				
				String personQuery = "INSERT INTO PERSON VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			    PreparedStatement personStmt = conn.prepareStatement(personQuery);
			    
			    personStmt.setString(1, (String) this.components.getValue("N-Number"));
			    personStmt.setString(2, (String) this.components.getValue("SSN"));
			    personStmt.setString(3, (String) this.components.getValue("First Name"));
			    personStmt.setString(4, (String) this.components.getValue("Middle Initial"));
			    personStmt.setString(5, (String) this.components.getValue("Last Name"));
			    personStmt.setString(6, (String) this.components.getValue("Phone Number"));
			    personStmt.setString(7, (String) this.components.getValue("Street"));
			    personStmt.setString(8, (String) this.components.getValue("City"));
			    personStmt.setString(9, (String) this.components.getValue("State"));
			    personStmt.setString(10, (String) this.components.getValue("ZIP"));
			    personStmt.setString(12, (String) this.components.getValue("Gender"));
			    
			    Object bDate = ((JSpinner) this.components.getComponent("Birthdate")).getValue();
			    
			    if (bDate instanceof Date) // Need to do error checking beforehand
			    {
			    	personStmt.setDate(11, new java.sql.Date(((Date) bDate).getTime()));
			    }
			    
			    personStmt.executeQuery();
			    
			    personStmt.close();
			    
				DBC.disconnect();
				
				// Instructor to Database
			
				DBC.connect();
				
				conn = DBC.get();
				
				String instructorQuery = "INSERT INTO INSTRUCTOR VALUES (?, ?, ?)";

			    PreparedStatement instructorStmt = conn.prepareStatement(instructorQuery);
			    
			    String deptQuery = "SELECT Dcode FROM DEPARTMENT WHERE Dname=?";
			    
			    PreparedStatement deptStmt = conn.prepareStatement(deptQuery);
			    
			    deptStmt.setString(1, (String) this.components.getValue("Department"));
			    
			    ResultSet deptSet = deptStmt.executeQuery();
			    
			    deptSet.next();
			    
			    String dept = deptSet.getString("Dcode");
			    
			    deptStmt.close();
			    
			    instructorStmt.setString(1, (String) this.components.getValue("N-Number"));
			    instructorStmt.setString(2, dept);
			    instructorStmt.setBigDecimal(3, new BigDecimal((String) this.components.getValue("Office Number")));
			    
			    instructorStmt.executeQuery();
			    
			    instructorStmt.close();
				
				DBC.disconnect();
			}
			else if (entity.equals("Course"))
			{
				// Course to Database
				
				DBC.connect();
				
				Connection conn = DBC.get();
				
				String courseQuery = "INSERT INTO COURSE VALUES (?, ?, ?, ?, ?, ?)";

			    PreparedStatement courseStmt = conn.prepareStatement(courseQuery);
			    
			    String deptQuery = "SELECT Dcode FROM DEPARTMENT WHERE Dname=?";
			    
			    PreparedStatement deptStmt = conn.prepareStatement(deptQuery);
			    
			    deptStmt.setString(1, (String) this.components.getValue("Department"));
			    
			    ResultSet deptSet = deptStmt.executeQuery();
			    
			    deptSet.next();
			    
			    String dept = deptSet.getString("Dcode");
			    
			    deptStmt.close();
			    
			    courseStmt.setString(1, (String) this.components.getValue("Course Code"));
			    courseStmt.setString(2, (String) this.components.getValue("Course Name"));
			    courseStmt.setString(3, (String) this.components.getValue("Course Description"));
			    courseStmt.setString(4, dept);
			    courseStmt.setBigDecimal(5, new BigDecimal((Integer) this.components.getValue("Course Level")));
			    courseStmt.setInt(6, (int) this.components.getValue("Credit Hours"));
			    
				courseStmt.executeQuery();
			    
			    courseStmt.close();
				
				DBC.disconnect();
			}
			else if (entity.equals("Section"))
			{
				// Section to Database
				
				DBC.connect();
				
				Connection conn = DBC.get();
				
				String sectionQuery = "INSERT INTO SECTION (Course, Instructor, Semester, Year_, Snumber) VALUES (?, ?, ?, ?, ?)";
				
			    PreparedStatement sectionStmt = conn.prepareStatement(sectionQuery);
			    
			    // Get Course Number by Course Name
			    
			    String courseQuery = "SELECT Cnumber FROM COURSE WHERE Cname=?";
			    
			    PreparedStatement courseStmt = conn.prepareStatement(courseQuery);
			    
			    courseStmt.setString(1, (String) this.components.getValue("Course"));
			    
			    ResultSet courseSet = courseStmt.executeQuery();
			    
			    courseSet.next();
			    
			    String course = courseSet.getString("Cnumber");
			    
			    courseStmt.close();
			    
			    courseSet.close();
			    
			    // Get Instructor N-Number
			    
			    String instructorListing = (String) this.components.getValue("Instructor");
			    
			    int length = instructorListing.length();
			    
			    String instructor = instructorListing.substring(length - 10, length - 1);
			    
			    // Derive Necessary Section Number
			    
			    String sNumberQuery = "SELECT Snumber FROM SECTION WHERE Course=? AND Semester=? AND Year_=?";
			    
			    PreparedStatement sNumberStmt = conn.prepareStatement(sNumberQuery);
			    
			    sNumberStmt.setString(1, course);
			    sNumberStmt.setString(2, (String) this.components.getValue("Semester"));
			    
			    Object year = ((JSpinner) this.components.getComponent("Year")).getValue();
			    
			    if (year instanceof Integer) // Need to do error checking beforehand
			    {
			    	sNumberStmt.setBigDecimal(3, new BigDecimal((Integer) year));
			    }
			    
			    ResultSet sNumberSet = sNumberStmt.executeQuery();
			    
			    int maxSNumber = 1;
			    
			    while (sNumberSet.next())
			    {
			    	int currentSNumber = sNumberSet.getInt("Snumber");
			    	
			    	if (currentSNumber >= maxSNumber)
			    	{
			    		maxSNumber = currentSNumber + 1;
			    	}
			    }
			    
			    sNumberStmt.close();
			    
			    sNumberSet.close();
			    
			    sectionStmt.setString(1, course);
			    sectionStmt.setString(2, instructor);
			    sectionStmt.setString(3, (String) this.components.getValue("Semester"));
			    
			    if (year instanceof Integer) // Need to do error checking beforehand
			    {
			    	sectionStmt.setBigDecimal(4, new BigDecimal((Integer) year));
			    }
			    
			    sectionStmt.setInt(5, maxSNumber);
			    
				sectionStmt.executeQuery();
			    
			    sectionStmt.close();
				
				DBC.disconnect();
			}
			
			this.repaint();
			this.form.repaint();
		}
		else
		{
			System.out.println("Invalid entries! Please enter correct information.");
		}
	}
}
