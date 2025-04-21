import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		
		String entity = (String) ((JComboBox<String>) components.getComponent("Entity")).getSelectedItem();
		
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
			
			// Add Components
			
			this.form.add(new JLabel("Department Code:"));
			this.form.add(code);
			
			this.form.add(new JLabel("Department Name:"));
			this.form.add(name);
			
			this.form.add(new JLabel("College:"));
			this.form.add(college);
			
			this.form.add(new JLabel("Office Number:"));
			this.form.add(oNum);
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
			pCity.setName("Permananent City");
			
			// Permanent State
			JComboBox<String> pState = new JComboBox<>(AmericanStates());
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
			JComboBox<String> cState = new JComboBox<>(AmericanStates());
			cState.setName("Current State");

			// Current ZIP
			AppTextField cZip = new AppTextField();
			cZip.setName("Current ZIP");
			
			// Gender
			JComboBox<String> gender = new JComboBox<>(Genders());
			gender.setName("Gender");
			
			// Birthdate
			Calendar calendar = Calendar.getInstance();
	        Date initialDate = calendar.getTime();
	        SpinnerDateModel dateModel = new SpinnerDateModel(initialDate, null, null, Calendar.DAY_OF_MONTH);
	        JSpinner bDate = new JSpinner(dateModel);
	        bDate.setName("Birthdate");
	        
	        // Class
			JComboBox<String> studentClass = new JComboBox<>(StudentClasses());
			studentClass.setName("Class");
			
			// Degree Program
			JComboBox<String> program = new JComboBox<>(DegreePrograms());
			program.setName("Degree Program");
			
			// Major Department
			JComboBox<String> major = new JComboBox<>(Departments());
			major.setName("Major Department");
			
			// Minor Department
			JComboBox<String> minor = new JComboBox<>(Departments());
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
			JComboBox<String> state = new JComboBox<>(AmericanStates());
			state.setName("State");
			
			// ZIP
			AppTextField zip = new AppTextField();
			zip.setName("ZIP");
			
			// Gender
			JComboBox<String> gender = new JComboBox<>(Genders());
			gender.setName("Gender");
			
			// Birthdate
			Calendar calendar = Calendar.getInstance();
	        Date initialDate = calendar.getTime();
	        SpinnerDateModel dateModel = new SpinnerDateModel(initialDate, null, null, Calendar.DAY_OF_MONTH);
	        JSpinner bDate = new JSpinner(dateModel);
	        bDate.setName("Birthdate");
			
			// Department
			JComboBox<String> dept = new JComboBox<>(Departments());
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
			JComboBox<String> dept = new JComboBox<>(Departments());
			dept.setName("Department");
			
			// Level
			JComboBox<Integer> level = new JComboBox<>(Levels());
			level.setName("Course Level");
			
			// Credit Hours
			JComboBox<Integer> credits = new JComboBox<>(Credits());
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
			JComboBox<String> course = new JComboBox<>(Courses());
			course.setName("Course");
			
			// Instructor
			JComboBox<String> instructor = new JComboBox<>(Instructors());
			instructor.setName("Instructor");
			
			// Semester
			JComboBox<String> semester = new JComboBox<>(Semesters());
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
				
				String deptQuery = "INSERT INTO DEPARTMENT VALUES (?, ?, ?, ?, ?, ?);";

			    PreparedStatement deptStmt = conn.prepareStatement(deptQuery);
			    
			    deptStmt.setString(1, ((AppTextField) this.components.getComponent("Department Code")).getText());
			    deptStmt.setString(2, ((AppTextField) this.components.getComponent("Department Name")).getText());
			    deptStmt.setString(3, ((AppTextField) this.components.getComponent("College")).getText());
			    deptStmt.setString(4, ((AppTextField) this.components.getComponent("Office Number")).getText());
			    deptStmt.setString(5, ((AppTextField) this.components.getComponent("Office Phone")).getText());
			    
				int rows = deptStmt.executeUpdate();
			    
			    if (rows > 0)
			    {
			    	System.out.println("Department successfully created.");
			    }
			    else
			    {
			    	System.out.println("Failed to construct department.");
			    }
				
				DBC.disconnect();
			}
			else if (entity.equals("Student"))
			{	
				// Person to Database
				
				DBC.connect();
				
				Connection conn = DBC.get();
				
				String personQuery = "INSERT INTO PERSON VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			    PreparedStatement personStmt = conn.prepareStatement(personQuery);
			    
			    personStmt.setString(1, ((AppTextField) this.components.getComponent("N-Number")).getText());
			    personStmt.setString(2, ((AppTextField) this.components.getComponent("SSN")).getText());
			    personStmt.setString(3, ((AppTextField) this.components.getComponent("First Name")).getText());
			    personStmt.setString(4, ((AppTextField) this.components.getComponent("Middle Initial")).getText());
			    personStmt.setString(5, ((AppTextField) this.components.getComponent("Last Name")).getText());
			    personStmt.setString(6, ((AppTextField) this.components.getComponent("Phone Number")).getText());
			    personStmt.setString(7, ((AppTextField) this.components.getComponent("Permanent Street")).getText());
			    personStmt.setString(8, ((AppTextField) this.components.getComponent("Permanent City")).getText());
			    personStmt.setString(9, (String) (((JComboBox) this.components.getComponent("Permanent State")).getSelectedItem()));
			    personStmt.setString(10, ((AppTextField) this.components.getComponent("Permanent ZIP")).getText());
			    personStmt.setString(12, (String) (((JComboBox) this.components.getComponent("Gender")).getSelectedItem()));
			    
			    Object bDate = ((JSpinner) this.components.getComponent("Birthdate")).getValue();
			    
			    if (bDate instanceof Date) // Need to do error checking beforehand
			    {
			    	personStmt.setDate(11, new java.sql.Date(((Date) bDate).getTime()));
			    }
			    
			    int personRows = personStmt.executeUpdate();
			    
			    if (personRows > 0)
			    {
			    	System.out.println("Person successfully created.");
			    }
			    else
			    {
			    	System.out.println("Failed to construct person.");
			    }
			    
				DBC.disconnect();
				
				// Student to Database
			
				DBC.connect();
				
				conn = DBC.get();
				
				String studentQuery = "INSERT INTO STUDENT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

			    PreparedStatement studentStmt = conn.prepareStatement(studentQuery);
			    
			    studentStmt.setString(1, ((AppTextField) this.components.getComponent("N-Number")).getText());
			    studentStmt.setString(2, ((AppTextField) this.components.getComponent("Current Street")).getText());
			    studentStmt.setString(3, ((AppTextField) this.components.getComponent("Current City")).getText());
			    studentStmt.setString(4, (String) (((JComboBox) this.components.getComponent("Current State")).getSelectedItem()));
				studentStmt.setString(5, ((AppTextField) this.components.getComponent("Current ZIP")).getText());
				studentStmt.setString(6, (String) (((JComboBox) this.components.getComponent("Class")).getSelectedItem()));
				studentStmt.setString(7, (String) (((JComboBox) this.components.getComponent("Degree Program")).getSelectedItem()));
				studentStmt.setString(8, (String) (((JComboBox) this.components.getComponent("Major Department")).getSelectedItem()));
				studentStmt.setString(9, (String) (((JComboBox) this.components.getComponent("Minor Department")).getSelectedItem()));
			    
				int studentRows = personStmt.executeUpdate();
			    
			    if (studentRows > 0)
			    {
			    	System.out.println("Student successfully created.");
			    }
			    else
			    {
			    	System.out.println("Failed to construct student.");
			    }
				
				DBC.disconnect();
			}
			else if (entity.equals("Instructor"))
			{
				// Person to Database
				
				DBC.connect();
				
				Connection conn = DBC.get();
				
				String personQuery = "INSERT INTO PERSON VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

			    PreparedStatement personStmt = conn.prepareStatement(personQuery);
			    
			    personStmt.setString(1, ((AppTextField) this.components.getComponent("N-Number")).getText());
			    personStmt.setString(2, ((AppTextField) this.components.getComponent("SSN")).getText());
			    personStmt.setString(3, ((AppTextField) this.components.getComponent("First Name")).getText());
			    personStmt.setString(4, ((AppTextField) this.components.getComponent("Middle Initial")).getText());
			    personStmt.setString(5, ((AppTextField) this.components.getComponent("Last Name")).getText());
			    personStmt.setString(6, ((AppTextField) this.components.getComponent("Phone Number")).getText());
			    personStmt.setString(7, ((AppTextField) this.components.getComponent("Street")).getText());
			    personStmt.setString(8, ((AppTextField) this.components.getComponent("City")).getText());
			    personStmt.setString(9, (String) (((JComboBox) this.components.getComponent("State")).getSelectedItem()));
			    personStmt.setString(10, ((AppTextField) this.components.getComponent("ZIP")).getText());
			    personStmt.setString(12, (String) (((JComboBox) this.components.getComponent("Gender")).getSelectedItem()));
			    
			    Object bDate = ((JSpinner) this.components.getComponent("Birthdate")).getValue();
			    
			    if (bDate instanceof Date) // Need to do error checking beforehand
			    {
			    	personStmt.setDate(11, new java.sql.Date(((Date) bDate).getTime()));
			    }
			    
			    int personRows = personStmt.executeUpdate();
			    
			    if (personRows > 0)
			    {
			    	System.out.println("Person successfully created.");
			    }
			    else
			    {
			    	System.out.println("Failed to construct person.");
			    }
			    
				DBC.disconnect();
				
				// Instructor to Database
			
				DBC.connect();
				
				conn = DBC.get();
				
				String instructorQuery = "INSERT INTO INSTRUCTOR VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

			    PreparedStatement instructorStmt = conn.prepareStatement(instructorQuery);
			    
			    instructorStmt.setString(1, ((AppTextField) this.components.getComponent("N-Number")).getText());
			    instructorStmt.setString(2, (String) (((JComboBox) this.components.getComponent("Department")).getSelectedItem()));
			    instructorStmt.setBigDecimal(3, new BigDecimal((String) (((AppTextField) this.components.getComponent("Office Number")).getText())));
			    
				int instructorRows = personStmt.executeUpdate();
			    
			    if (instructorRows > 0)
			    {
			    	System.out.println("Instructor successfully created.");
			    }
			    else
			    {
			    	System.out.println("Failed to construct instructor.");
			    }
				
				DBC.disconnect();
			}
			else if (entity.equals("Course"))
			{
				// Course to Database
				
				DBC.connect();
				
				Connection conn = DBC.get();
				
				String courseQuery = "INSERT INTO COURSE VALUES (?, ?, ?, ?, ?, ?);";

			    PreparedStatement courseStmt = conn.prepareStatement(courseQuery);
			    
			    courseStmt.setString(1, ((AppTextField) this.components.getComponent("Course Code")).getText());
			    courseStmt.setString(2, ((AppTextField) this.components.getComponent("Course Name")).getText());
			    courseStmt.setString(3, ((AppTextField) this.components.getComponent("Course Description")).getText());
			    courseStmt.setString(4, (String) (((JComboBox) this.components.getComponent("Department")).getSelectedItem()));
			    courseStmt.setString(5, (String) (((JComboBox) this.components.getComponent("Course Level")).getSelectedItem()));
			    courseStmt.setString(6, (String) (((JComboBox) this.components.getComponent("Credit Hours")).getSelectedItem()));
			    
				int rows = courseStmt.executeUpdate();
			    
			    if (rows > 0)
			    {
			    	System.out.println("Course successfully created.");
			    }
			    else
			    {
			    	System.out.println("Failed to construct course.");
			    }
				
				DBC.disconnect();
			}
			else if (entity.equals("Section"))
			{
				// Section to Database
				
				DBC.connect();
				
				Connection conn = DBC.get();
				
				String sectionQuery = "INSERT INTO SECTION (Course, Instructor, Semester, Year) VALUES (?, ?, ?, ?);";

			    PreparedStatement sectionStmt = conn.prepareStatement(sectionQuery);
			    
			    sectionStmt.setString(1, (String) (((JComboBox) this.components.getComponent("Course")).getSelectedItem()));
			    sectionStmt.setString(2, (String) (((JComboBox) this.components.getComponent("Instructor")).getSelectedItem()));
			    sectionStmt.setString(3, (String) (((JComboBox) this.components.getComponent("Semester")).getSelectedItem()));
			    
			    Object year = ((JSpinner) this.components.getComponent("Year")).getValue();
			    
			    if (year instanceof Integer) // Need to do error checking beforehand
			    {
			    	sectionStmt.setInt(4, (int) year);
			    }
			    
				int rows = sectionStmt.executeUpdate();
			    
			    if (rows > 0)
			    {
			    	System.out.println("Section successfully created.");
			    }
			    else
			    {
			    	System.out.println("Failed to construct section.");
			    }
				
				DBC.disconnect();
			}
			
			this.repaint();
			this.form.repaint();
		}
		else
		{
			System.out.println("Invalid eentries! Please enter correct information.");
		}
	}
	
	// Auxillary Listing
	
	private String[] AmericanStates()
	{
		String[] americanStates =
		{
			"Alabama",
			"Alaska",
			"Arizona",
			"Arkansas",
			"California",
			"Colorado",
			"Connecticut",
			"Delaware",
			"Florida",
			"Georgia",
			"Hawaii",
			"Idaho",
			"Illinois",
			"Indiana",
			"Iowa",
			"Kansas",
			"Kentucky",
			"Louisiana",
			"Maine",
			"Maryland",
			"Massachusetts",
			"Michigan",
			"Minnesota",
			"Mississippi",
			"Missouri",
			"Montana",
			"Nebraska",
			"Nevada",
			"New Hampshire",
			"New Jersey",
			"New Mexico",
			"New York",
			"North Carolina",
			"North Dakota",
			"Ohio",
			"Oklahoma",
			"Oregon",
			"Pennsylvania",
			"Rhode Island",
			"South Carolina",
			"South Dakota",
			"Tennessee",
			"Texas",
			"Utah",
			"Vermont",
			"Virginia",
			"Washington",
			"West Virginia",
			"Wisconsin",
			"Wyoming"
		};
		
		return americanStates;
	}

	private String[] DegreePrograms()
	{
		String[] degreePrograms =
		{
			"B.A.",
			"B.S.",
			"B.F.A",
			"M.A.",
			"M.S.",
			"M.B.A.",
			"Ph.D.",
			"Ed.D.",
			"J.D."
		};
		
		return degreePrograms;
	}

	private String[] StudentClasses()
	{
		String[] studentClasses =
		{
				"Freshman",
				"Sophomore",
				"Junior",
				"Senior",
				"Graduate"
		};
		
		return studentClasses;
	}

	private String[] Genders()
	{
		String[] genders = {"Male", "Female"};
		
		return genders;
	}

	private String[] Departments() throws SQLException
	{
		DBC.connect();
		Connection conn = DBC.get();
		String[] departments = {};
		DBC.disconnect();
		
		return departments;
	}

	private String[] Courses() throws SQLException
	{
		DBC.connect();
		Connection conn = DBC.get();
		String[] courses = {};
		DBC.disconnect();
		
		return courses;
	}

	private String[] Instructors() throws SQLException
	{
		DBC.connect();
		Connection conn = DBC.get();
		String[] instructors = {};
		DBC.disconnect();
		
		return instructors;
	}
	
 	private Integer[] Levels()
	{
		Integer[] levels =
		{
			1000,
			2000,
			3000,
			4000,
			5000,
			6000,
			7000
		};
		
		return levels;
	}

	private Integer[] Credits()
	{
		Integer[] credits =
		{
			1,
			2,
			3,
			4,
			5	
		};
		
		return credits;
	}

	private String[] Semesters()
	{
		String[] semesters =
		{
			"Fall",
			"Spring",
			"Summer A",
			"Summer B"
		};
		
		return semesters;
	}
}
