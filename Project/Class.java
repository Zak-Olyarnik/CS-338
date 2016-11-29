/*
*
* Zakary Olyarnik
* zwo24@drexel.edu
* CS338:GUI, MyGradebook Project
*
*/


import java.util.ArrayList;

// MyGradebook's Class object, containing a list of Categories
public class Class
{
	private String name;
	private int weights = 0;
	private ArrayList<Category> categories = new ArrayList<Category>();

	Class(){}

	Class(String n)
	{ name = n; }

	// getters and setters
	public String getName()
	{ return name; }

	public void setName(String n)
	{ name = n; }

	public int getWeights()
	{ return weights; }


	public ArrayList<Category> getCategories()
	{ return categories; }

	// add and subtract Category weights
	public void addWeight(int w)
	{ weights = weights + w; }
	
	public void subWeight(int w)
	{ weights = weights - w; }
	
	// add and delete Categories
	public void addCategory(Category c)
	{ categories.add(c); }

	public void deleteCategory(Category c)
	{ categories.remove(c); }

	// toString
	public String toString()
	{ return name; }
}
