/*
* Zakary Olyarnik
* zwo24@drexel.edu
* CS338:GUI, Assignment 1
*
*/


// class which stores all of the user-entered data as one PropertyObject
public class PropertyObject
{

	// private data strings
	private String address, city, bedrooms, bathrooms, squareFeet, year, price;


	// constructors
	PropertyObject(){}

	PropertyObject(String a, String c, String bd, String bt, String s, String
			y, String p)
	{
		address = a;
		city = c;
		bedrooms = bd;
		bathrooms = bt;
		squareFeet = s;
		year = y;
		price = p;
	}


	// toString() returns a comma-separated string of the private data, which
	// is what is displayed in the GUI list 
	public String toString()
	{ return address + ", " + city + ", " + bedrooms + ", " + bathrooms + 
		", " + squareFeet + ", " + year + ", " + price; }
			

	// compares two PropertyObjects
	public boolean equals(PropertyObject p)
	{
		return ((address.equals(p.getAddress())) &&
				  (city.equals(p.getCity())) &&
				  (bedrooms.equals(p.getBedrooms())) &&
				  (bathrooms.equals(p.getBathrooms())) &&
				  (squareFeet.equals(p.getSquareFeet())) &&
				  (year.equals(p.getYear())) &&
				  (price.equals(p.getPrice())));
	}


	// setters
	public void setAddress(String s)
	{ address = s; }
	
	public void setCity(String s)
	{ city = s; }

	public void setBedrooms(String s)
	{ bedrooms = s; }

	public void setBathrooms(String s)
	{ bathrooms = s; }

	public void setSquareFeet(String s)
	{ squareFeet = s; }

	public void setYear(String s)
	{ year = s; }

	public void setPrice(String s)
	{ price = s; }


	// getters
	public String getAddress()
	{ return address; }

	public String getCity()
   { return city; }

	public String getBedrooms()
   { return bedrooms; }

	public String getBathrooms()
   { return bathrooms; }

	public String getSquareFeet()
   { return squareFeet; }

	public String getYear()
   { return year; }

	public String getPrice()
   { return price; }

}
