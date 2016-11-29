/*
*
* Zakary Olyarnik
* zwo24@drexel.edu
* CS338:GUI, MyGradebook Project
*
*/


import java.util.ArrayList;

// MyGradebook's Grade object
public class Grade
{
	private String name;
	private int earnedPoints;
	private int possiblePoints;
	private boolean predicted = false;
	private boolean hasScore = false;

	Grade(){}

	Grade(String n)
	{ name = n; }
	
	Grade(String n, int ep, int pp)
	{
		name = n;
		earnedPoints = ep;
		possiblePoints = pp;
		hasScore = true;
	}

	// getters and setters, including getters that return Strings to set GUI labels
	public String getName()
	{ return name; }

	public void setName(String n)
	{ name = n; }

	public int getEarnedPoints()
	{ return earnedPoints; }

	public String getEarnedPointsString()
	{
		if (hasScore)
		{ return Integer.toString(earnedPoints); }
		else
		{ return ""; }
	}

	public void setEarnedPoints(int p)
	{ earnedPoints = p; }

	public int getPossiblePoints()
	{ return possiblePoints; }

	public String getPossiblePointsString()
	{
		if (hasScore)
		{ return Integer.toString(possiblePoints); }
		else
		{ return ""; }
	}

	public void setPossiblePoints(int p)
	{ possiblePoints = p; }

	public String getPointsString()
	{
		if (hasScore)
		{ return Integer.toString(earnedPoints) + "/" + Integer.toString(possiblePoints); }
		else
		{ return "--/--"; }
	}

	public int getPercentage()
	{ return calculatePercentage(); }

	public String getPercentageString()
	{
		if (hasScore)
		{ return Integer.toString(calculatePercentage()) + "%"; }
		else
		{ return "--"; }
	}

	// booleans to make error checking and facilitation easier	
	public boolean getPredicted()
	{ return predicted; }
	
	public void setPredicted(boolean b)
	{ predicted = b; }
	
	public boolean hasScore()
	{ return hasScore; }
	
	public void setHasScore(boolean b)
	{ hasScore = b; }

	// calculate the percentage earned on this Grade
	public int calculatePercentage()
	{
		if (hasScore)
		{
			int percentage = (int)Math.round((earnedPoints * 100.0) / possiblePoints);
			return percentage;
		}
		else
		{ return 0; }
	}
}
