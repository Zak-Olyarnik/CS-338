/*
*
* Zakary Olyarnik
* zwo24@drexel.edu
* CS338:GUI, MyGradebook Project
*
*/


import java.util.ArrayList;

// MyGradebook's Category object, containing a list of Grades
public class Category
{
	private String name;
	private int weight;
	private boolean hasWeight = false;
	private boolean expanded = false;
	private int earnedPoints;
	private int possiblePoints;
	private int average;
	private boolean hasAverage = false;
	private ArrayList<Grade> grades = new ArrayList<Grade>();

	Category(){}

	Category(String n)
	{ name = n; }
	
	Category(String n, int w)
	{
		name = n;
		weight = w;
		hasWeight = true;
	}

	// getters and setters, including getters that return Strings to set GUI labels
	public String getName()
	{ return name; }

	public void setName(String n)
	{ name = n; }

	public int getWeight()
	{ return weight; }

	public String getWeightString()
	{
		if (hasWeight)
		{ return Integer.toString(weight); }
		else
		{ return "--"; }
	}

	public void setWeight(int w)
	{
		hasWeight = true;
		weight = w;
	}

	public int getNumGrades()
	{ return grades.size(); }

	public String getNumGradesString()
	{ return Integer.toString(grades.size()); }
	
	public int getAverage()
	{ return average; }

	public String getAverageString()
	{
		calculateAverage();
		if (hasAverage)
		{ return Integer.toString(average); }
		else
		{ return "--"; }
	}

	public int getEarnedPoints()
	{ return earnedPoints; }

	public int getPossiblePoints()
	{ return possiblePoints; }
	
	public ArrayList<Grade> getGrades()
	{ return grades; }

	// booleans to make error checking and facilitation easier
	public boolean hasAverage()
	{ return hasAverage; }

	public boolean hasWeight()
	{ return hasWeight; }

	public void setHasWeight(boolean b)
	{ hasWeight = b; }
	
	public boolean isExpanded()
	{ return expanded; }

	public void setExpanded(boolean b)
	{ expanded = b; }

	// add and delete Grades
	public void addGrade(Grade g)
	{ grades.add(g); }

	public void deleteGrade(Grade g)
	{ grades.remove(g); }

	// calculate the average of all Grades in Category
	public void calculateAverage()
	{
		earnedPoints = 0;
		possiblePoints = 0;
		int goodGradesCounter = 0;
		for (Grade g : grades)
		{
			if (g.hasScore())
			{
				earnedPoints = earnedPoints + g.getEarnedPoints();
				possiblePoints = possiblePoints + g.getPossiblePoints();
				goodGradesCounter = goodGradesCounter + 1;
			}
		}
		if (goodGradesCounter > 0)
		{
			hasAverage = true;
			average = (int)Math.round((earnedPoints * 100.0) / possiblePoints);
		}
		else
		{ hasAverage = false; }
	}

	// toString
	public String toString()
	{ return name; }
}
