import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Utilities
{
	public static String[] AmericanStates()
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

	public static String[] DegreePrograms()
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

	public static String[] StudentClasses()
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

	public static String[] Genders()
	{
		String[] genders = {"Male", "Female"};
		
		return genders;
	}

 	public static Integer[] Levels()
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

	public static Integer[] Credits()
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

	public static String[] Semesters()
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
	
	public static double letterToGPA(String grade)
	{
		if (grade.equals("A"))
		{
			return 4.0;
		}
		else if (grade.equals("A-"))
		{
			return 3.7;
		}
		else if (grade.equals("B+"))
		{
			return 3.3;
		}
		else if (grade.equals("B"))
		{
			return 3.0;
		}
		else if (grade.equals("B-"))
		{
			return 2.7;
		}
		else if (grade.equals("C+"))
		{
			return 2.3;
		}
		else if (grade.equals("C"))
		{
			return 2.0;
		}
		else if (grade.equals("C-"))
		{
			return 1.7;
		}
		else if (grade.equals("D+"))
		{
			return 1.3;
		}
		else if (grade.equals("D"))
		{
			return 1.0;
		}
		else if (grade.equals("F"))
		{
			return 0.0;
		}
		
		return 0;
	}
}
