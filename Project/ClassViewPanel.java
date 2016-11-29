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
import java.beans.*;
import java.text.DecimalFormat;

// MyGradebook's second screen, where a user can view all the details of
	// their selected Class
public class ClassViewPanel extends JPanel
{
	private Class c;
	private ArrayList<Category> categories;
	private JLabel classNameLabel;
	private JLabel gradeValueLabel;
	private JLabel gradeLetterLabel;
	private JPanel scrollpaneHolderPanel;
	private JPanel scrollpaneHeading;
	private JButton calculateButton;
	
	private Dimension categoryLabelSize = new Dimension(275, 50);
	private Dimension weightLabelSize = new Dimension(130, 50);
	private Dimension numberLabelSize = new Dimension(50, 50);
	private Dimension averageLabelSize = new Dimension(160, 50);

	ClassViewPanel()
	{
		Dimension scrollpaneHolderPanelSize = new Dimension(840, 335);

		// create title components
		classNameLabel = new JLabel();
		classNameLabel.setFont(MyGradebook.TITLE_FONT);
		JButton editClassNameButton = createEditClassNameButton();
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		titlePanel.add(classNameLabel);
		titlePanel.add(Box.createRigidArea(new Dimension(25, 0)));
		titlePanel.add(editClassNameButton);

		JLabel gradeLabel = new JLabel("Grade:");
		gradeLabel.setFont(MyGradebook.TITLE_FONT);
		gradeValueLabel = new JLabel("--");
		gradeValueLabel.setFont(MyGradebook.TITLE_FONT);
		gradeLetterLabel = new JLabel("--");
		gradeLetterLabel.setFont(MyGradebook.TITLE_FONT);
		JPanel gradeDisplayPanel = new JPanel();
		gradeDisplayPanel.setLayout(new BoxLayout(gradeDisplayPanel, BoxLayout.X_AXIS));
		gradeDisplayPanel.add(gradeLabel);
		gradeDisplayPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		gradeDisplayPanel.add(gradeValueLabel);
		gradeDisplayPanel.add(Box.createRigidArea(new Dimension(40, 0)));
		gradeDisplayPanel.add(gradeLetterLabel);

		// create intermediate panels for custom component alignment
		scrollpaneHeading = createScrollpaneHeading();
		scrollpaneHolderPanel = new JPanel();
		scrollpaneHolderPanel.setLayout(new BoxLayout(scrollpaneHolderPanel, BoxLayout.X_AXIS));
		scrollpaneHolderPanel.setMinimumSize(scrollpaneHolderPanelSize);
		scrollpaneHolderPanel.setMaximumSize(scrollpaneHolderPanelSize);
		scrollpaneHolderPanel.setPreferredSize(scrollpaneHolderPanelSize);
		JButton addCategoryButton = createAddCategoryButton();
		calculateButton = createCalculateButton();
		JButton backButton = createBackButton();
		JPanel intermediatePanel = new JPanel();
		intermediatePanel.setLayout(new BoxLayout(intermediatePanel, BoxLayout.Y_AXIS));
		intermediatePanel.add(addCategoryButton);
		intermediatePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		intermediatePanel.add(calculateButton);
		intermediatePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		intermediatePanel.add(backButton);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(intermediatePanel);

		// create panel to hold all content on this screen
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(titlePanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		contentPanel.add(gradeDisplayPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		contentPanel.add(scrollpaneHeading);
		contentPanel.add(scrollpaneHolderPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 96)));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(buttonsPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		
		// add borders for finished ClassViewPanel object
		setLayout(new BorderLayout());
		add(contentPanel, BorderLayout.CENTER);
		add(Box.createRigidArea(MyGradebook.TOP_BOTTOM_BORDER), BorderLayout.NORTH);
		add(Box.createRigidArea(MyGradebook.LEFT_RIGHT_BORDER), BorderLayout.EAST);
		add(Box.createRigidArea(MyGradebook.LEFT_RIGHT_BORDER), BorderLayout.WEST);
		add(Box.createRigidArea(MyGradebook.TOP_BOTTOM_BORDER), BorderLayout.SOUTH);
	}
	
	// creates the editClassName button which invokes a dialog to rename the Class
	public JButton createEditClassNameButton()
	{
		Dimension editClassNameButtonSize = new Dimension(30, 30);
		ImageIcon editIcon = new ImageIcon(MyGradebook.EDIT_ICON.getImage()
			.getScaledInstance(20, 20, Image.SCALE_SMOOTH));

		JButton b = new JButton(editIcon);
		b.setAlignmentX(Component.CENTER_ALIGNMENT);
		b.setToolTipText("Edit Class name");
		b.setMinimumSize(editClassNameButtonSize);
		b.setMaximumSize(editClassNameButtonSize);
		b.setPreferredSize(editClassNameButtonSize);
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Container parent = ClassViewPanel.this.getParent();
				JFrame frame = ((MyGradebook)parent).getFrame();
				RenameClassDialog rcd = new RenameClassDialog(frame);
				rcd.setLocationRelativeTo(frame);
				rcd.setVisible(true);
			}
		});
		return b;
	}
  
	// creates the addCategory button which invokes a dialog to add a Category to the Class
	public JButton createAddCategoryButton()
	{
		ImageIcon addCategoryIcon = new ImageIcon(MyGradebook.ADD_ICON.getImage()
			.getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JButton b = new JButton("Add Category", addCategoryIcon);
		b.setIconTextGap(20);
		b.setAlignmentX(Component.LEFT_ALIGNMENT);
		b.setFont(MyGradebook.BUTTON_FONT);
		b.setToolTipText("Add a new Category");
		b.setMinimumSize(MyGradebook.BUTTON_SIZE);
		b.setMaximumSize(MyGradebook.BUTTON_SIZE);
		b.setPreferredSize(MyGradebook.BUTTON_SIZE);
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Container parent = ClassViewPanel.this.getParent();
				JFrame frame = ((MyGradebook)parent).getFrame();
				NewCategoryDialog ncd = new NewCategoryDialog(frame);
				ncd.setLocationRelativeTo(frame);
				ncd.setVisible(true);
			}
		});
		return b;
	}

	// creates the calculate button which leads to the Calculator panel
	public JButton createCalculateButton()
	{	
		JButton b = new JButton("Calculator");
		b.setAlignmentX(Component.LEFT_ALIGNMENT);
		b.setFont(MyGradebook.BUTTON_FONT);
		b.setMinimumSize(MyGradebook.BUTTON_SIZE);
		b.setMaximumSize(MyGradebook.BUTTON_SIZE);
		b.setPreferredSize(MyGradebook.BUTTON_SIZE);
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Container parent = ClassViewPanel.this.getParent();
				Calculator calc = ((MyGradebook)parent).getCalculator();
				calc.setClass(c);
				CardLayout cl = (CardLayout)parent.getLayout();
				cl.show(parent, "CALCULATOR");
			}
		});
		return b;
	}

	// creates the back button which leads back to the ClassListPanel
	public JButton createBackButton()
	{
		ImageIcon backIcon = new ImageIcon(MyGradebook.BACK_ICON.getImage()
			.getScaledInstance(20, 20, Image.SCALE_SMOOTH));

		JButton b = new JButton("Back", backIcon);
		b.setIconTextGap(7);
		b.setAlignmentX(Component.LEFT_ALIGNMENT);
		b.setFont(MyGradebook.DIALOG_FONT);
		b.setToolTipText("Back to Class List");
		b.setMinimumSize(MyGradebook.BACK_BUTTON_SIZE);
		b.setMaximumSize(MyGradebook.BACK_BUTTON_SIZE);
		b.setPreferredSize(MyGradebook.BACK_BUTTON_SIZE);
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Container parent = ClassViewPanel.this.getParent();
				ClassListPanel clp = ((MyGradebook)parent).getClassListPanel();
				clp.refresh();
				CardLayout cl = (CardLayout)parent.getLayout();
				cl.show(parent, "CLASS_LIST_PANEL");
			}
		});
		return b;
	}

	// creates the heading row for the Categories scrollpane
	public JPanel createScrollpaneHeading()
	{
		Dimension headingSize = new Dimension(840, 58);

		JLabel categoryLabel = new JLabel(" Category");
		categoryLabel.setFont(MyGradebook.BUTTON_FONT);
		categoryLabel.setMinimumSize(categoryLabelSize);
		categoryLabel.setMaximumSize(categoryLabelSize);
		categoryLabel.setPreferredSize(categoryLabelSize);

		JLabel weightLabel = new JLabel("Weight", SwingConstants.CENTER);
		weightLabel.setFont(MyGradebook.BUTTON_FONT);
		weightLabel.setMinimumSize(weightLabelSize);
		weightLabel.setMaximumSize(weightLabelSize);
		weightLabel.setPreferredSize(weightLabelSize);

		JLabel numberLabel = new JLabel("#", SwingConstants.CENTER);
		numberLabel.setFont(MyGradebook.BUTTON_FONT);
		numberLabel.setMinimumSize(numberLabelSize);
		numberLabel.setMaximumSize(numberLabelSize);
		numberLabel.setPreferredSize(numberLabelSize);

		JLabel averageLabel = new JLabel("Average", SwingConstants.CENTER);
		averageLabel.setFont(MyGradebook.BUTTON_FONT);
		averageLabel.setMinimumSize(averageLabelSize);
		averageLabel.setMaximumSize(averageLabelSize);
		averageLabel.setPreferredSize(averageLabelSize);

		JPanel heading = new JPanel();
		heading.setLayout(new BoxLayout(heading, BoxLayout.X_AXIS));
		heading.add(Box.createRigidArea(new Dimension(50, 0)));
		heading.add(categoryLabel);
		heading.add(weightLabel);
		heading.add(numberLabel);
		heading.add(averageLabel);
		heading.add(Box.createRigidArea(new Dimension(167, 0)));
		heading.setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		heading.setMinimumSize(headingSize);
		heading.setMaximumSize(headingSize);
		heading.setPreferredSize(headingSize);

		return heading;
	}
	
	// creates and populates the main Categories scrollpane
	public void createCategories()
	{
		Dimension buttonSize = new Dimension(50, 48);
		ImageIcon expandIcon = new ImageIcon(MyGradebook.EXPAND_ICON.getImage()
			.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		ImageIcon collapseIcon = new ImageIcon(MyGradebook.COLLAPSE_ICON.getImage()
			.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		ImageIcon addIcon = new ImageIcon(MyGradebook.ADD_ICON.getImage()
			.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		ImageIcon editIcon = new ImageIcon(MyGradebook.EDIT_ICON.getImage()
			.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		ImageIcon deleteIcon = new ImageIcon(MyGradebook.DELETE_ICON.getImage()
			.getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JPanel categoriesPanel = new JPanel();
		categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));

		for (Category cat : categories)
		{
			JButton toggle = new JButton();
			if (cat.getGrades().isEmpty())
			{
				cat.setExpanded(false);
				toggle.setEnabled(false);
			}
			if (cat.isExpanded())	// initialize with correct icon
			{ toggle.setIcon(collapseIcon); }
			else
			{ toggle.setIcon(expandIcon); }
			toggle.setToolTipText("View Grades in this Category");
			toggle.setMinimumSize(buttonSize);
			toggle.setMaximumSize(buttonSize);
			toggle.setPreferredSize(buttonSize);
			toggle.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if (cat.isExpanded())	// switch to collapsed
					{
						cat.setExpanded(false);
						toggle.setIcon(expandIcon);
						refresh();
					}
					else	// switch to expanded
					{
						cat.setExpanded(true);
						toggle.setIcon(collapseIcon);
						refresh();
					}
				}
			});
			
			JLabel categoryLabel = new JLabel(" " + cat.getName());
			categoryLabel.setFont(MyGradebook.BUTTON_FONT);
			categoryLabel.setMinimumSize(categoryLabelSize);
			categoryLabel.setMaximumSize(categoryLabelSize);
			categoryLabel.setPreferredSize(categoryLabelSize);
			
			JLabel weightLabel = new JLabel(cat.getWeightString(), SwingConstants.CENTER);
			weightLabel.setFont(MyGradebook.BUTTON_FONT);
			weightLabel.setMinimumSize(weightLabelSize);
			weightLabel.setMaximumSize(weightLabelSize);
			weightLabel.setPreferredSize(weightLabelSize);
			
			JLabel numberLabel = new JLabel(cat.getNumGradesString(), SwingConstants.CENTER);
			numberLabel.setFont(MyGradebook.BUTTON_FONT);
			numberLabel.setMinimumSize(numberLabelSize);
			numberLabel.setMaximumSize(numberLabelSize);
			numberLabel.setPreferredSize(numberLabelSize);
			
			JLabel averageLabel = new JLabel(cat.getAverageString(), SwingConstants.CENTER);
			averageLabel.setFont(MyGradebook.BUTTON_FONT);
			averageLabel.setMinimumSize(averageLabelSize);
			averageLabel.setMaximumSize(averageLabelSize);
			averageLabel.setPreferredSize(averageLabelSize);
			
			JButton add = new JButton(addIcon);
			add.setToolTipText("Add a new Grade to this Category");
			add.setMinimumSize(buttonSize);
			add.setMaximumSize(buttonSize);
			add.setPreferredSize(buttonSize);
			add.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Container parent = ClassViewPanel.this.getParent();
					JFrame frame = ((MyGradebook)parent).getFrame();
					NewGradeDialog ngd = new NewGradeDialog(frame, cat);
					ngd.setLocationRelativeTo(frame);
					ngd.setVisible(true);
				}
			});
			
			JButton edit = new JButton(editIcon);
			edit.setToolTipText("Edit the name and weight of this Category");
			edit.setMinimumSize(buttonSize);
			edit.setMaximumSize(buttonSize);
			edit.setPreferredSize(buttonSize);
			edit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Container parent = ClassViewPanel.this.getParent();
					JFrame frame = ((MyGradebook)parent).getFrame();
					EditCategoryDialog ecd = new EditCategoryDialog(frame, cat);
					ecd.setLocationRelativeTo(frame);
					ecd.setVisible(true);
				}
			});
			
			JButton delete = new JButton(deleteIcon);
			delete.setToolTipText("Delete this Category");
			delete.setMinimumSize(buttonSize);
			delete.setMaximumSize(buttonSize);
			delete.setPreferredSize(buttonSize);
			delete.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String categoryName = cat.getName();
					Object[] options = {"Yes", "Cancel"};
					int n = JOptionPane.showOptionDialog(scrollpaneHolderPanel,
						"Are you sure you want to delete Category " + categoryName + "?",
						"Delete Category", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
						null, options, options[1]);
					if (n == JOptionPane.YES_OPTION)
					{
					categories.remove(cat);
					refresh();
					}
				}
			});

			JPanel catRow = new JPanel();
			catRow.setLayout(new BoxLayout(catRow, BoxLayout.X_AXIS));
			catRow.add(toggle);
			catRow.add(categoryLabel);
			catRow.add(weightLabel);
			catRow.add(numberLabel);
			catRow.add(averageLabel);
			catRow.add(add);
			catRow.add(edit);
			catRow.add(delete);
			catRow.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
			categoriesPanel.add(catRow);

			// add Grades if Category is expanded
			if(!cat.getGrades().isEmpty() && cat.isExpanded())
			{
				for(Grade g : cat.getGrades())
				{
					JPanel gradeRow = createGrade(cat, g);
					categoriesPanel.add(gradeRow);
				}
		  	}
		}

		// put everything in scrollpane
		JScrollPane scrollpane = new JScrollPane(categoriesPanel,
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.setPreferredSize(new Dimension(836, 330));
		scrollpane.setBorder(BorderFactory.createMatteBorder(0,4,4,4, Color.BLACK));
		scrollpaneHolderPanel.add(scrollpane);
	}

	// creates row in scrollpane for each invdividual grade
	public JPanel createGrade(Category cat, Grade grade)
	{
		Dimension gradeLabelSize = new Dimension(350, 35);
		Dimension pointsLabelSize = new Dimension(110, 35);
		Dimension percentageLabelSize = new Dimension(100, 35);
		Dimension buttonSize = new Dimension(32, 31);
		Dimension gradeRowSize = new Dimension(815, 35);
		ImageIcon editIcon = new ImageIcon(MyGradebook.EDIT_ICON.getImage()
			.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		ImageIcon deleteIcon = new ImageIcon(MyGradebook.DELETE_ICON.getImage()
			.getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		Font gradeFont;
		if(grade.getPredicted())	// mark predicted grades with italics
		{ gradeFont = MyGradebook.PREDICTED_FONT; }
		else
		{ gradeFont = MyGradebook.DIALOG_FONT; }

		JLabel gradeLabel = new JLabel(" " + grade.getName(), SwingConstants.LEFT);
		gradeLabel.setFont(gradeFont);
		gradeLabel.setMinimumSize(gradeLabelSize);
		gradeLabel.setMaximumSize(gradeLabelSize);
		gradeLabel.setPreferredSize(gradeLabelSize);
		
		JLabel pointsLabel = new JLabel(grade.getPointsString(), SwingConstants.CENTER);
		pointsLabel.setFont(gradeFont);
		pointsLabel.setMinimumSize(pointsLabelSize);
		pointsLabel.setMaximumSize(pointsLabelSize);
		pointsLabel.setPreferredSize(pointsLabelSize);
		
		JLabel percentageLabel = new JLabel(grade.getPercentageString(), SwingConstants.CENTER);
		percentageLabel.setFont(gradeFont);
		percentageLabel.setMinimumSize(percentageLabelSize);
		percentageLabel.setMaximumSize(percentageLabelSize);
		percentageLabel.setPreferredSize(percentageLabelSize);

		JButton edit = new JButton(editIcon);
		edit.setToolTipText("Edit this Grade");
		edit.setMinimumSize(buttonSize);
		edit.setMaximumSize(buttonSize);
		edit.setPreferredSize(buttonSize);
		edit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
					Container parent = ClassViewPanel.this.getParent();
					JFrame frame = ((MyGradebook)parent).getFrame();
					EditGradeDialog egd = new EditGradeDialog(frame, cat, grade);
					egd.setLocationRelativeTo(frame);
					egd.setVisible(true);
			}
		});
		
		JButton delete = new JButton(deleteIcon);
		delete.setToolTipText("Delete this Grade");
		delete.setMinimumSize(buttonSize);
		delete.setMaximumSize(buttonSize);
		delete.setPreferredSize(buttonSize);
		delete.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String gradeName = grade.getName();
				Object[] options = {"Yes", "Cancel"};
				int n = JOptionPane.showOptionDialog(scrollpaneHolderPanel,
					"Are you sure you want to delete Grade " + gradeName + "?",
					"Delete Grade", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);
				if (n == JOptionPane.YES_OPTION)
				{
					cat.deleteGrade(grade);
					refresh();
				}
			}
		});

		JPanel gradeRow = new JPanel();
		gradeRow.setLayout(new BoxLayout(gradeRow, BoxLayout.X_AXIS));
		gradeRow.add(gradeLabel);
		gradeRow.add(pointsLabel);
		gradeRow.add(Box.createRigidArea(new Dimension(15, 0)));
		gradeRow.add(percentageLabel);
		gradeRow.add(Box.createRigidArea(new Dimension(25, 0)));
		gradeRow.add(edit);
		gradeRow.add(delete);
		gradeRow.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
		gradeRow.setMinimumSize(gradeRowSize);
		gradeRow.setMaximumSize(gradeRowSize);
		gradeRow.setPreferredSize(gradeRowSize);

		return gradeRow;
	}
	
	// checks for already-existing Category name
	public boolean duplicateCategoryName(String newCategoryName)
	{
		boolean duplicateFlag = false;
		for (Category cat : categories)
		{
			if (cat.getName().equals(newCategoryName))  // Category name already exists
			{
				duplicateFlag = true;
				break;
			}	
		}
		return duplicateFlag;
	}
	
	// checks for already-existing Grade name
	public boolean duplicateGradeName(String newGradeName, Category cat)
	{
		boolean duplicateFlag = false;
		for (Grade g : cat.getGrades())
		{
			if (g.getName().equals(newGradeName))  // Grade name already exists
			{
				duplicateFlag = true;
				break;
			}	
		}
		return duplicateFlag;
	}
	
	// sets the Class to be viewed on this screen, invoked from ClassListPanel before the
		// CardLayout flips
	public void setClass(Class cl)
	{
		c = cl;
		categories = c.getCategories();
		refresh();
	}

	// calculates the Class' average, taking into account all possible combinations of known 
		// and unknown Category averages and weights
	public void calculateClassAverage()
	{
		double avg = 0.0;				// running avg of known averages/weights Categories
		double unweighted = 0.0;	// average of all unweighted Categories together
		int weights = 0;				// running total of known weights
		int subweights = 0;			// running total of known weights/no averages
		int unknownWeights = 0;		// calculated defacto weight for unknown weight Categories
		int numUnweighted = 0;		// number of unweighted Categories
		int earnedPoints = 0;		// total Category earned points (for unweighted)
		int possiblePoints = 0;		// total Category possible points (for unweighted)

		for (Category cat : categories)
		{
			if (cat.hasAverage() && cat.hasWeight())
			{
				avg = avg + (cat.getAverage() * cat.getWeight()) / 100.0;
				weights = weights + cat.getWeight();
			}
			else if (cat.hasAverage() && !cat.hasWeight())
			{
				earnedPoints = earnedPoints + cat.getEarnedPoints();
				possiblePoints = possiblePoints + cat.getPossiblePoints();
				numUnweighted = numUnweighted + 1;
			}
			else if (!cat.hasAverage() && cat.hasWeight())
			{ subweights = subweights + cat.getWeight(); }
		}

		if (numUnweighted != 0)	// calculate the defacto weight of unweighted Categories
		{
			unweighted = (earnedPoints * 100.0) / possiblePoints;
			unknownWeights = 100 - weights - subweights;
			unweighted = (unweighted * unknownWeights) / 100.0;
		}
		avg = avg + unweighted;
		avg = (avg * 100.0) / (weights + unknownWeights);
		DecimalFormat df = new DecimalFormat("###.##");
		gradeValueLabel.setText(df.format(avg));
		calculateLetterGrade(avg);
	}

	// displays letter grade based on calculated Class average
	public void calculateLetterGrade(double avg)
	{
		if (avg > 97)
		{ gradeLetterLabel.setText("A+"); }
		else if (avg > 93)
		{ gradeLetterLabel.setText("A"); }
		else if (avg > 90)
		{ gradeLetterLabel.setText("A-"); }
		else if (avg > 87)
		{ gradeLetterLabel.setText("B+"); }
		else if (avg > 83)
		{ gradeLetterLabel.setText("B"); }
		else if (avg > 80)
		{ gradeLetterLabel.setText("B-"); }
		else if (avg > 77)
		{ gradeLetterLabel.setText("C+"); }
		else if (avg > 73)
		{ gradeLetterLabel.setText("C"); }
		else if (avg > 70)
		{ gradeLetterLabel.setText("C-"); }
		else if (avg > 67)
		{ gradeLetterLabel.setText("D+"); }
		else if (avg > 63)
		{ gradeLetterLabel.setText("D"); }
		else if (avg < 60)
		{ gradeLetterLabel.setText("D-"); }
		else
		{ gradeLetterLabel.setText("F"); }
	}
	
	// refreshes the screen
	public void refresh()
	{
		// reset everything
		classNameLabel.setText(c.getName());
		gradeValueLabel.setText("--");
		gradeLetterLabel.setText("--");
		calculateButton.setEnabled(false);
		calculateButton.setToolTipText("Class must be unweighted or have weights totalling 100" +
				" to run calculations");
		scrollpaneHolderPanel.removeAll();

		if (!categories.isEmpty())	// only add Categories back if they exist to be added
		{
			createCategories();
			boolean w = false;
			for (Category cat: categories)
			{
				if (cat.hasWeight())
				{
					w = true;
					break;
				}
			}
			if ((c.getWeights() == 100) || !w)
			{
				calculateButton.setEnabled(true);
				calculateButton.setToolTipText("Calculate scores needed to obtain an overall" +
						" Grade in the Class");
			}
			for (Category cat : categories)
			{
				if (cat.hasAverage())	// only do calculations once we have Grades
				{
					calculateClassAverage();
					break;
				}
			}
		}

		revalidate();
		repaint();
	}

	
	// custom dialog with error checking, invoked on Class rename.  See ClassListPanel for code source
	class RenameClassDialog extends JDialog implements ActionListener, PropertyChangeListener
	{
		private JTextField textField;
		private JOptionPane optionPane;
		private String enterButton = "Enter";
		private String cancelButton = "Cancel";

		// builds the custom dialog
		public RenameClassDialog(Frame frame)
		{
			super(frame, true);
			setTitle("Rename Class");
			String message = "Enter new Class name:";
			textField = new JTextField(c.getName(), 20);
			Object[] components = {message, textField};	// array of components
			Object[] options = {enterButton, cancelButton};	// array of buttons
			
			// creates the JOptionPane
			optionPane = new JOptionPane(components, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.YES_NO_OPTION, null, options, options[0]);
			setContentPane(optionPane);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			textField.addActionListener(this);
			optionPane.addPropertyChangeListener(this);
			pack();
		}
		
		// textField event handler
		@Override
		public void actionPerformed(ActionEvent e)
		{ optionPane.setValue(enterButton); }
		
		// optionPane event handler
		@Override
		public void propertyChange(PropertyChangeEvent e)
		{
			String prop = e.getPropertyName();
			if (isVisible() && (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop)
				|| JOptionPane.INPUT_VALUE_PROPERTY.equals(prop)))
			{
				Object value = optionPane.getValue();
				if (value == JOptionPane.UNINITIALIZED_VALUE)	// ignore reset
				{ return; }
				
				// resets the JOptionPane's value (if you don't do this, then if the user
				// presses the same button again, no property change event will be fired)
				optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
				
				// processes textField input
				if (enterButton.equals(value))	// user clicked Enter
				{
					String newClassName = textField.getText();
					if (newClassName.trim().isEmpty())	// textField left blank
					{
						JOptionPane.showMessageDialog(this, "Class name cannot be left blank!",
							"Rename Class Error", JOptionPane.ERROR_MESSAGE);
						newClassName = null;
					}
					else
					{
						Container parent = ClassViewPanel.this.getParent();
						ClassListPanel clp = ((MyGradebook)parent).getClassListPanel();
						if (clp.duplicateClassName(newClassName))	// Class name already exists
						{
							if (newClassName.equals(c.getName()))	// no rename necessary
							{ dispose(); }
							else
							{
								JOptionPane.showMessageDialog(this, "Class names must be unique!",
									"Rename Class Error", JOptionPane.ERROR_MESSAGE);
								newClassName = null;
							}
						}
						else	// good input
						{
							// rename Class
							c.setName(newClassName);
							refresh();
							dispose();
						}
					}
				}
				else	// user clicked Cancel or X
				{ dispose(); }
			}
		}
	}
	
	// custom dialog with error checking, invoked on Category add.  See ClassListPanel for code source
	class NewCategoryDialog extends JDialog implements ActionListener, PropertyChangeListener
	{
		private JTextField textField1;
		private JTextField textField2;
		private JOptionPane optionPane;
		private String enterButton = "Enter";
		private String cancelButton = "Cancel";

		// builds the custom dialog
		public NewCategoryDialog(Frame frame)
		{
			super(frame, true);
			setTitle("Add Category");
			String message1 = "Enter new Category name:";
			String message2 = "Enter new Category weight percentage (if known):";
			textField1 = new JTextField(20);
			textField2 = new JTextField(20);
			Object[] components = {message1, textField1, Box.createRigidArea(new Dimension(0, 10)),
				message2, textField2, Box.createRigidArea(new Dimension(0, 10))};	// array of components
			Object[] options = {enterButton, cancelButton};	// array of buttons
			
			// creates the JOptionPane
			optionPane = new JOptionPane(components, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.YES_NO_OPTION, null, options, options[0]);
			setContentPane(optionPane);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			optionPane.addPropertyChangeListener(this);
			pack();
		}
		
		// textField event handler
		@Override
		public void actionPerformed(ActionEvent e)
		{ optionPane.setValue(enterButton); }
		
		// optionPane event handler
		@Override
		public void propertyChange(PropertyChangeEvent e)
		{
			String prop = e.getPropertyName();
			if (isVisible() && (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop)
				|| JOptionPane.INPUT_VALUE_PROPERTY.equals(prop)))
			{
				Object value = optionPane.getValue();
				if (value == JOptionPane.UNINITIALIZED_VALUE)	// ignore reset
				{ return; }
				
				// resets the JOptionPane's value (if you don't do this, then if the user
				// presses the same button again, no property change event will be fired)
				optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
				
				// processes textField input	
				if (enterButton.equals(value))	// user clicked Enter
				{
					String newCategoryName = textField1.getText();
					if (newCategoryName.trim().isEmpty())	// textField left blank
					{
						JOptionPane.showMessageDialog(this, "Category name cannot be left blank!",
							"Add Category Error", JOptionPane.ERROR_MESSAGE);
						newCategoryName = null;
					}
					else
					{
						if (duplicateCategoryName(newCategoryName))	// Category name already exists
						{
							JOptionPane.showMessageDialog(this, "Category names must be unique!",
								"Add Category Error", JOptionPane.ERROR_MESSAGE);
							newCategoryName = null;
						}
						else	// good name
						{
							String newCategoryWeight = textField2.getText();
							if(!newCategoryWeight.trim().isEmpty())	// check weight is realistic
							{
								try
								{
									int newWeight = Integer.parseInt(newCategoryWeight);
									if (newWeight < 1)
									{
										JOptionPane.showMessageDialog(this, "Weight must be a" +
											" positive integer!", "Add Category Error",
											JOptionPane.ERROR_MESSAGE);
									}
									else if (newWeight > (100 - c.getWeights()))
									{
										JOptionPane.showMessageDialog(this, "Total weight cannot" +
											" exceed 100!", "Add Category Error",
											JOptionPane.ERROR_MESSAGE);
									}
									else	// good input
									{
										// create Category
										Category newCategory = new Category(newCategoryName, newWeight);
										c.addWeight(newWeight);
										categories.add(newCategory);
										refresh();
										dispose();
									}
								}
								catch(NumberFormatException ex)	//not a numeric String
								{
									JOptionPane.showMessageDialog(this, "Weight must be a" +
										" positive integer!", "Add Category Error",
										JOptionPane.ERROR_MESSAGE);
								}
							}
							else	// no weight specified, ok
							{
								// create Category
								Category newCategory = new Category(newCategoryName);
								categories.add(newCategory);
								refresh();
								dispose();
							}
						}
					}
				}
				else	// user clicked Cancel or X
				{ dispose(); }
			}
		}
	}

	// custom dialog with error checking, invoked on Category edit.  See ClassListPanel for code source
	class EditCategoryDialog extends JDialog implements ActionListener, PropertyChangeListener
	{
		private JTextField textField1;
		private JTextField textField2;
		private JOptionPane optionPane;
		private String enterButton = "Enter";
		private String cancelButton = "Cancel";
		private Category cat;

		// builds the custom dialog
		public EditCategoryDialog(Frame frame, Category category)
		{
			super(frame, true);
			cat = category;
			setTitle("Add Category");
			String message1 = "Enter new Category name:";
			String message2 = "Enter new Category weight percentage (if known):";
			textField1 = new JTextField(cat.getName(), 20);
			if (cat.hasWeight())	// initialize differently depending on if Category is weighted or not
			{textField2 = new JTextField(cat.getWeightString(), 20); }
			else
			{ textField2 = new JTextField(20); }
			Object[] components = {message1, textField1, Box.createRigidArea(new Dimension(0, 10)),
				message2, textField2, Box.createRigidArea(new Dimension(0, 10))};	// array of components
			Object[] options = {enterButton, cancelButton};	// array of buttons
			
			// creates the JOptionPane
			optionPane = new JOptionPane(components, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.YES_NO_OPTION, null, options, options[0]);
			setContentPane(optionPane);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			optionPane.addPropertyChangeListener(this);
			pack();
		}
		
		// textField event handler
		@Override
		public void actionPerformed(ActionEvent e)
		{ optionPane.setValue(enterButton); }
		
		// optionPane event handler
		@Override
		public void propertyChange(PropertyChangeEvent e)
		{
			String prop = e.getPropertyName();
			if (isVisible() && (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop)
				|| JOptionPane.INPUT_VALUE_PROPERTY.equals(prop)))
			{
				Object value = optionPane.getValue();
				if (value == JOptionPane.UNINITIALIZED_VALUE)	// ignore reset
				{ return; }
				
				// resets the JOptionPane's value (if you don't do this, then if the user
				// presses the same button again, no property change event will be fired)
				optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
				
				// processes textField input	
				if (enterButton.equals(value))	// user clicked Enter
				{
					String newCategoryName = textField1.getText();
					if (newCategoryName.trim().isEmpty())	// textField left blank
					{
						JOptionPane.showMessageDialog(this, "Category name cannot be left blank!",
							"Edit Category Error", JOptionPane.ERROR_MESSAGE);
						newCategoryName = null;
					}
					else
					{
						if (duplicateCategoryName(newCategoryName) && !newCategoryName.equals(cat.getName()))
						{	// Category name already exists, and is not the current name
							JOptionPane.showMessageDialog(this, "Category names must be unique!",
								"Edit Category Error", JOptionPane.ERROR_MESSAGE);
							newCategoryName = null;
						}
						else	// good name
						{
							String newCategoryWeight = textField2.getText();
							if(!newCategoryWeight.trim().isEmpty())	// check weight is realistic
							{
								try
								{
									int newWeight = Integer.parseInt(newCategoryWeight);
									int w = 0;
									if (cat.hasWeight())
									{ w = cat.getWeight(); }
									if (newWeight < 1)
									{
										JOptionPane.showMessageDialog(this, "Weight must be a" +
											" positive integer!", "Add Category Error",
											JOptionPane.ERROR_MESSAGE);
									}
									else if (newWeight > (100 - c.getWeights() + w))
									{
										JOptionPane.showMessageDialog(this, "Total weight cannot" +
											" exceed 100!", "Add Category Error",
											JOptionPane.ERROR_MESSAGE);
									}
									else	// good input
									{
										// edit Category
										cat.setName(newCategoryName);
										cat.setWeight(newWeight);
										c.subWeight(w);
										c.addWeight(newWeight);
										refresh();
										dispose();
									}
								}
								catch(NumberFormatException ex)	//not a numeric String
								{
									JOptionPane.showMessageDialog(this, "Weight must be a" +
										" positive integer!", "Add Category Error",
										JOptionPane.ERROR_MESSAGE);
								}
							}
							else	// no weight specified, ok
							{
								// edit Category
								cat.setName(newCategoryName);
								if (cat.hasWeight())
								{
									int w = cat.getWeight();
									c.subWeight(w);
								}
								cat.setHasWeight(false);
								refresh();
								dispose();
							}
						}
					}
				}
				else	// user clicked Cancel or X
				{ dispose(); }
			}
		}
	}
	
	// custom dialog with error checking, invoked on Grade add.  See ClassListPanel for code source
	class NewGradeDialog extends JDialog implements ActionListener, PropertyChangeListener
	{
		private JTextField textField1;
		private JTextField textField2;
		private JTextField textField3;
		private JCheckBox checkbox;
		private JOptionPane optionPane;
		private String enterButton = "Enter";
		private String cancelButton = "Cancel";
		private Category cat;

		// builds the custom dialog
		public NewGradeDialog(Frame frame, Category category)
		{
			super(frame, true);
			cat = category;
			setTitle("Add Grade");
			String message1 = "Enter new Grade name:";
			String message2 = "Enter earned points:";
			String message3 = "Enter possible points:";
			textField1 = new JTextField(20);
			textField2 = new JTextField(20);
			textField3 = new JTextField(20);
			checkbox = new JCheckBox("Predicted?");
			checkbox.setFont(MyGradebook.DIALOG_FONT);
			Object[] components = {message1, textField1, Box.createRigidArea(new Dimension(0, 10)),
				message2, textField2, Box.createRigidArea(new Dimension(0, 10)), message3, textField3,
				Box.createRigidArea(new Dimension(0, 10)), checkbox,
				Box.createRigidArea(new Dimension(0, 10))};	// array of components
			Object[] options = {enterButton, cancelButton};	// array of buttons
			
			// creates the JOptionPane
			optionPane = new JOptionPane(components, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.YES_NO_OPTION, null, options, options[0]);
			setContentPane(optionPane);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			optionPane.addPropertyChangeListener(this);
			pack();
		}
		
		// textField event handler
		@Override
		public void actionPerformed(ActionEvent e)
		{ optionPane.setValue(enterButton); }
		
		// optionPane event handler
		@Override
		public void propertyChange(PropertyChangeEvent e)
		{
			String prop = e.getPropertyName();
			if (isVisible() && (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop)
				|| JOptionPane.INPUT_VALUE_PROPERTY.equals(prop)))
			{
				Object value = optionPane.getValue();
				if (value == JOptionPane.UNINITIALIZED_VALUE)	// ignore reset
				{ return; }
				
				// resets the JOptionPane's value (if you don't do this, then if the user
				// presses the same button again, no property change event will be fired)
				optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
				
				// processes textField input	
				if (enterButton.equals(value))	// user clicked Enter
				{
					String newGradeName = textField1.getText();
					if (newGradeName.trim().isEmpty())	// textField left blank
					{
						JOptionPane.showMessageDialog(this, "Grade name cannot be left blank!",
							"Add Grade Error", JOptionPane.ERROR_MESSAGE);
						newGradeName = null;
					}
					else
					{
						if (duplicateGradeName(newGradeName, cat))	// Grade name already exists
						{
							JOptionPane.showMessageDialog(this, "Grade names must be unique!",
							"Add Grade Error", JOptionPane.ERROR_MESSAGE);
							newGradeName = null;
						}
						else	// good name
						{
							String newGradeEarnedPoints = textField2.getText();
							String newGradePossiblePoints = textField3.getText();
							boolean earnedPointsEmpty = newGradeEarnedPoints.trim().isEmpty();
							boolean possiblePointsEmpty = newGradePossiblePoints.trim().isEmpty();
							if(earnedPointsEmpty && !possiblePointsEmpty || !earnedPointsEmpty && possiblePointsEmpty)
							{	// incomplete point data entered
								JOptionPane.showMessageDialog(this, "Point values must both be entered or both left blank!",
									"Add Grade Error", JOptionPane.ERROR_MESSAGE);	
							}
							else if (earnedPointsEmpty && possiblePointsEmpty)	// both empty, ok
							{
								// create Grade
								Grade newGrade = new Grade(newGradeName);
								newGrade.setHasScore(false);
								if(checkbox.isSelected())
								{ newGrade.setPredicted(true); }
						
								cat.getGrades().add(newGrade);
								cat.setExpanded(true);
								refresh();
								dispose();
							}
							else	// point data entered, must check validity
							{
								try	// it baffles me that there is no good isInteger() library function yet
								{
									int earnedPoints = Integer.parseInt(newGradeEarnedPoints);
									int possiblePoints = Integer.parseInt(newGradePossiblePoints);
									
									if (earnedPoints < 0 || possiblePoints < 1)	// check in realistic range
									{
										JOptionPane.showMessageDialog(this, "Point values must be positive integers!",
											"Add Grade Error", JOptionPane.ERROR_MESSAGE);
									}
									else	// good input
									{
										// create Grade
										Grade newGrade = new Grade(newGradeName, earnedPoints, possiblePoints);
										if(checkbox.isSelected())
										{ newGrade.setPredicted(true); }
						
										cat.getGrades().add(newGrade);
										cat.setExpanded(true);
										refresh();
										dispose();
									}
								}
								catch(NumberFormatException ex)	// point data was not a numeric string
								{
									JOptionPane.showMessageDialog(this, "Point values must be positive integers!",
										"Add Grade Error", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
				}
				else	// user clicked Cancel or X
				{ dispose(); }
			}
		}
	}
	
	// custom dialog with error checking, invoked on Grade edit.  See ClassListPanel for code source
	class EditGradeDialog extends JDialog implements ActionListener, PropertyChangeListener
	{
		private JTextField textField1;
		private JTextField textField2;
		private JTextField textField3;
		private JCheckBox checkbox;
		private JOptionPane optionPane;
		private String enterButton = "Enter";
		private String cancelButton = "Cancel";
		private Category cat;
		private Grade g;

		// builds the custom dialog
		public EditGradeDialog(Frame frame, Category category, Grade grade)
		{
			super(frame, true);
			cat = category;
			g = grade;
			setTitle("Add Grade");
			String message1 = "Enter new Grade name:";
			String message2 = "Enter earned points:";
			String message3 = "Enter possible points:";
			textField1 = new JTextField(g.getName(), 20);
			textField2 = new JTextField(g.getEarnedPointsString(), 20);
			textField3 = new JTextField(g.getPossiblePointsString(), 20);
			checkbox = new JCheckBox("Predicted?");
			checkbox.setFont(MyGradebook.DIALOG_FONT);
			Object[] components = {message1, textField1, Box.createRigidArea(new Dimension(0, 10)),
				message2, textField2, Box.createRigidArea(new Dimension(0, 10)), message3, textField3,
				Box.createRigidArea(new Dimension(0, 10)), checkbox,
				Box.createRigidArea(new Dimension(0, 10))};	// array of components
			Object[] options = {enterButton, cancelButton};	// array of buttons
			
			// creates the JOptionPane
			optionPane = new JOptionPane(components, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.YES_NO_OPTION, null, options, options[0]);
			setContentPane(optionPane);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			optionPane.addPropertyChangeListener(this);
			pack();
		}
		
		// textField event handler
		@Override
		public void actionPerformed(ActionEvent e)
		{ optionPane.setValue(enterButton); }
		
		// optionPane event handler
		@Override
		public void propertyChange(PropertyChangeEvent e)
		{
			String prop = e.getPropertyName();
			if (isVisible() && (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop)
				|| JOptionPane.INPUT_VALUE_PROPERTY.equals(prop)))
			{
				Object value = optionPane.getValue();
				if (value == JOptionPane.UNINITIALIZED_VALUE)	// ignore reset
				{ return; }
				
				// resets the JOptionPane's value (if you don't do this, then if the user
				// presses the same button again, no property change event will be fired)
				optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
				
				// processes textField input	
				if (enterButton.equals(value))	// user clicked Enter
				{
					String newGradeName = textField1.getText();
					if (newGradeName.trim().isEmpty())	// textField left blank
					{
						JOptionPane.showMessageDialog(this, "Grade name cannot be left blank!",
							"Add Grade Error", JOptionPane.ERROR_MESSAGE);
						newGradeName = null;
					}
					else
					{
						if (duplicateGradeName(newGradeName, cat) && !newGradeName.equals(g.getName()))
						{	// Grade name already exists
							JOptionPane.showMessageDialog(this, "Grade names must be unique!",
							"Edit Grade Error", JOptionPane.ERROR_MESSAGE);
							newGradeName = null;
						}
						else	// good name
						{
							String newGradeEarnedPoints = textField2.getText();
							String newGradePossiblePoints = textField3.getText();
							boolean earnedPointsEmpty = newGradeEarnedPoints.trim().isEmpty();
							boolean possiblePointsEmpty = newGradePossiblePoints.trim().isEmpty();
							if(earnedPointsEmpty && !possiblePointsEmpty || !earnedPointsEmpty && possiblePointsEmpty)
							{	// incomplete point data entered
								JOptionPane.showMessageDialog(this, "Point values must both be entered or both left blank!",
									"Edit Grade Error", JOptionPane.ERROR_MESSAGE);	
							}
							else if (earnedPointsEmpty && possiblePointsEmpty)	// both empty, ok
							{
								// edit Grade
								g.setName(newGradeName);
								g.setHasScore(false);
								if(checkbox.isSelected())
								{ g.setPredicted(true); }
						
								refresh();
								dispose();
							}
							else	// point data entered, must check validity
							{
								try	// it baffles me that there is no good isInteger() library function yet
								{
									int earnedPoints = Integer.parseInt(newGradeEarnedPoints);
									int possiblePoints = Integer.parseInt(newGradePossiblePoints);
									
									if (earnedPoints < 0 || possiblePoints < 1)	// check in realistic range
									{
										JOptionPane.showMessageDialog(this, "Point values must be positive integers!",
											"Edit Grade Error", JOptionPane.ERROR_MESSAGE);
									}
									else	// good input
									{
										// edit Grade
										g.setName(newGradeName);
										g.setEarnedPoints(earnedPoints);
										g.setPossiblePoints(possiblePoints);
										g.setHasScore(true);
										if(checkbox.isSelected())
										{ g.setPredicted(true); }

										refresh();
										dispose();
									}
								}
								catch(NumberFormatException ex)	// point data was not a numeric string
								{
									JOptionPane.showMessageDialog(this, "Point values must be positive integers!",
										"Edit Grade Error", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
				}
				else	// user clicked Cancel or X
				{ dispose(); }
			}
		}
	}
}
