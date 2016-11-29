/*
*
* Zakary Olyarnik
* zwo24@drexel.edu
* CS338:GUI, MyGradebook Project
* 
*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

// MyGradebook's driver class
public class MyGradebook extends JPanel
{
	// fonts, dimensions, and icons used throughout the project
	public static Font TITLE_FONT = new Font("Century Schoolbook L", Font.BOLD, 50);
	public static Font BUTTON_FONT = new Font("Century Schoolbook L", Font.BOLD, 30);
	public static Font DIALOG_FONT = new Font("Century Schoolbook L", Font.BOLD, 20);
	public static Font PREDICTED_FONT = new Font("Century Schoolbook L", Font.ITALIC, 20);
	public static Dimension TOP_BOTTOM_BORDER = new Dimension(0, 60);
	public static Dimension LEFT_RIGHT_BORDER = new Dimension(100, 0);
	public static Dimension BUTTON_SIZE = new Dimension(400, 50);
	public static Dimension BACK_BUTTON_SIZE = new Dimension(115, 40);
	public static ImageIcon EDIT_ICON;
	public static ImageIcon DELETE_ICON;
	public static ImageIcon BACK_ICON;
	public static ImageIcon ADD_ICON;
	public static ImageIcon EXPAND_ICON;
	public static ImageIcon COLLAPSE_ICON;

	private JFrame frame;
	private ClassListPanel classListPanel;
	private ClassViewPanel classViewPanel;
	private Calculator calculator;
	
	MyGradebook(JFrame f)
	{
		// set frame for displaying JDialogs later
		frame = f;
		
		// create classes to run prototype with
			// TODO: replace with deserialization
		ArrayList<Class> classes = init();

		// set up CardLayout
		setLayout(new CardLayout());
		classListPanel = new ClassListPanel(classes);
		add(classListPanel, "CLASS_LIST_PANEL");
		classViewPanel = new ClassViewPanel();
		add(classViewPanel, "CLASS_VIEW_PANEL");
		calculator = new Calculator();
		add(calculator, "CALCULATOR");
		CardLayout cl = (CardLayout)this.getLayout();
		cl.show(this, "CLASS_LIST_PANEL");
	}


	// these methods get the frame and panels for CardLayout switching
	public JFrame getFrame()
	{ return frame; }

	public ClassListPanel getClassListPanel()
	{ return classListPanel; }

	public ClassViewPanel getClassViewPanel()
	{ return classViewPanel; }

	public Calculator getCalculator()
	{ return calculator; }

	// creates sample data to run prototype with.  In an ideal implementation,
		// this would be replaced with a method that deserializes saved data
		// from a text file
	public ArrayList<Class> init()
	{
		ArrayList<Class> classes = new ArrayList<Class>();

		// all weights/grades known except grade for final
		Class c1 = new Class("CS-360");
		Category cat1_1 = new Category("Homework", 60);
		Grade g1_1 = new Grade("HW1", 11, 10);
		cat1_1.addGrade(g1_1);
		Grade g1_2 = new Grade("HW2", 39, 40);
		cat1_1.addGrade(g1_2);
		Grade g1_3 = new Grade("HW3", 56, 60);
		cat1_1.addGrade(g1_3);
		Grade g1_4 = new Grade("HW4", 20, 25);
		cat1_1.addGrade(g1_4);
		Grade g1_5 = new Grade("HW5", 27, 30);
		cat1_1.addGrade(g1_5);
		Grade g1_6 = new Grade("HW6", 40, 45);
		cat1_1.addGrade(g1_6);
		Grade g1_7 = new Grade("HW7", 38, 40);
		cat1_1.addGrade(g1_7);
		Grade g1_8 = new Grade("HW8", 36, 40);
		cat1_1.addGrade(g1_8);
		Grade g1_9 = new Grade("HW9", 45, 50);
		cat1_1.addGrade(g1_9);
		Grade g1_10 = new Grade("HW10", 60, 70);
		cat1_1.addGrade(g1_10);
		c1.addCategory(cat1_1);
		Category cat1_2 = new Category("Midterm", 20);
		Grade g1_11 = new Grade("Midterm", 87, 100);
		cat1_2.addGrade(g1_11);
		c1.addCategory(cat1_2);
		Category cat1_3 = new Category("Final", 20);
		c1.addCategory(cat1_3);
		c1.addWeight(100);
		classes.add(c1);
		
		// all grades known, no weighting in Class
		Class c2 = new Class("ANIM-240");
		Category cat2_1 = new Category("Assignments");
		Grade g2_1 = new Grade("Assignment 1", 95, 100);
		cat2_1.addGrade(g2_1);
		Grade g2_2 = new Grade("Assignment 2", 98, 100);
		cat2_1.addGrade(g2_2);
		Grade g2_3 = new Grade("Assignment 3", 94, 100);
		cat2_1.addGrade(g2_3);
		Grade g2_4 = new Grade("Assignment 4", 90, 100);
		cat2_1.addGrade(g2_4);
		c2.addCategory(cat2_1);
		Category cat2_2 = new Category("Final Project");
		Grade g2_5 = new Grade("Final Project", 90, 100);
		cat2_2.addGrade(g2_5);
		c2.addCategory(cat2_2);
		Category cat2_3 = new Category("Participation");
		Grade g2_6 = new Grade("Participation", 100, 100);
		cat2_3.addGrade(g2_6);
		c2.addCategory(cat2_3);
		classes.add(c2);

		// some grade scores missing
		Class c3 = new Class("CS-283");
		Category cat3_1 = new Category("Homework", 35);
		Grade g3_1 = new Grade("HW1", 40,  50);
		cat3_1.addGrade(g3_1);
		Grade g3_2 = new Grade("HW2", 15,  20);
		cat3_1.addGrade(g3_2);
		Grade g3_3 = new Grade("HW3", 13,  15);
		cat3_1.addGrade(g3_3);
		Grade g3_4 = new Grade("HW4", 40,  50);
		cat3_1.addGrade(g3_4);
		Grade g3_5 = new Grade("HW5");
		cat3_1.addGrade(g3_5);
		Grade g3_6 = new Grade("HW6");
		cat3_1.addGrade(g3_6);
		Grade g3_7 = new Grade("HW7");
		cat3_1.addGrade(g3_7);
		c3.addCategory(cat3_1);
		Category cat3_2 = new Category("Labs", 15);
		Grade g3_8 = new Grade("Lab 1", 46,  50);
		cat3_2.addGrade(g3_8);
		Grade g3_9 = new Grade("Lab 2", 42,  50);
		cat3_2.addGrade(g3_9);
		Grade g3_10 = new Grade("Lab 3", 49,  50);
		cat3_2.addGrade(g3_10);
		Grade g3_11 = new Grade("Lab 4");
		cat3_2.addGrade(g3_11);
		Grade g3_12 = new Grade("Lab 5");
		cat3_2.addGrade(g3_12);
		c3.addCategory(cat3_2);
		Category cat3_3 = new Category("Midterm", 20);
		Grade g3_13 = new Grade("Midterm", 85,  100);
		cat3_3.addGrade(g3_13);
		c3.addCategory(cat3_3);
		Category cat3_4 = new Category("Project", 10);
		Grade g3_14 = new Grade("Progress Report", 92,  100);
		cat3_4.addGrade(g3_14);
		Grade g3_15 = new Grade("Final Report");
		cat3_4.addGrade(g3_15);
		c3.addCategory(cat3_4);
		Category cat3_5 = new Category("Final", 20);
		Grade g3_16 = new Grade("Final");
		cat3_5.addGrade(g3_16);
		c3.addCategory(cat3_5);
		c3.addWeight(100);
		classes.add(c3);

		// some Categories without Grades yet
		Class c4 = new Class("CS-350");
		Category cat4_1 = new Category("Written Assignments", 25);
		Grade g4_1 = new Grade("Assignment 1", 55, 60);
		cat4_1.addGrade(g4_1);
		Grade g4_2 = new Grade("Assignment 2", 65, 60);
		cat4_1.addGrade(g4_2);
		c4.addCategory(cat4_1);
		Category cat4_2 = new Category("Programs", 5);
		c4.addCategory(cat4_2);	
		Category cat4_3 = new Category("Labs", 20);
		Grade g4_3 = new Grade("Lab 1", 5, 5);
		cat4_3.addGrade(g4_3);
		Grade g4_4 = new Grade("Lab 2", 5, 5);
		cat4_3.addGrade(g4_4);
		c4.addCategory(cat4_3);					
		Category cat4_4 = new Category("Projects", 10);
		c4.addCategory(cat4_4);
		Category cat4_5 = new Category("Midterm", 15);
		c4.addCategory(cat4_5);
		Category cat4_6 = new Category("Final", 25);
		c4.addCategory(cat4_6);
		c4.addWeight(100);
		classes.add(c4);
		
		return classes;
	}

	// main
	public static void main(String[] args)
	{
		// set default UI settings
		UIManager.put("OptionPane.messageFont", DIALOG_FONT);
		UIManager.put("OptionPane.buttonFont", DIALOG_FONT);
		UIManager.put("TextField.font", DIALOG_FONT);
		
		// set up all icons
		EDIT_ICON = new ImageIcon("images/edit.png");
		DELETE_ICON = new ImageIcon("images/delete.png");
		ADD_ICON = new ImageIcon("images/add.png");
		BACK_ICON = new ImageIcon("images/back.png");
		EXPAND_ICON = new ImageIcon("images/expand.png");
		COLLAPSE_ICON = new ImageIcon("images/collapse.png");

		// create frame
		JFrame frame = new JFrame("MyGradebook");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(1050, 925));
		frame.getContentPane().add(new MyGradebook(frame));
		frame.pack();
		//frame.setSize(800, 800);
		frame.setVisible(true);
	}
}
