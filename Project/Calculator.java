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
import java.beans.*;
import java.text.DecimalFormat;

// MyGradeook's third screen, where a user can do grade prediction calculations
public class Calculator extends JPanel
{
	private JComboBox<Class> classCB;
	private JComboBox<Category> categoryCB;
	private JTextField desiredText;
	private JLabel resultLabel;

	Calculator()
	{
		Dimension resultLabelSize = new Dimension(500, 50);

		// create title components
		JLabel title = new JLabel("Calculator");
		title.setFont(MyGradebook.TITLE_FONT);
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
		titlePanel.add(title);

		// create intermediate panels for custom component alignment
		JPanel middlePanel = createMiddlePanel();

		resultLabel = new JLabel("", SwingConstants.CENTER);
		resultLabel.setFont(MyGradebook.BUTTON_FONT);
		resultLabel.setMinimumSize(resultLabelSize);
		resultLabel.setMaximumSize(resultLabelSize);
		resultLabel.setPreferredSize(resultLabelSize);
		JPanel resultPanel = new JPanel();
		resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.X_AXIS));
		resultPanel.add(resultLabel);

		JButton calculateButton = createCalculateButton();
		JButton backButton = createBackButton();
		JPanel intermediatePanel = new JPanel();
		intermediatePanel.setLayout(new BoxLayout(intermediatePanel, BoxLayout.Y_AXIS));
		intermediatePanel.add(calculateButton);
		intermediatePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		intermediatePanel.add(backButton);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(intermediatePanel);

		// create panel to hold all content on this screen
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(titlePanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 135)));
		contentPanel.add(middlePanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 230)));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(resultPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 25)));
		contentPanel.add(buttonsPanel);
		
		// add borders for finished ClassViewPanel object
		setLayout(new BorderLayout());
		add(contentPanel, BorderLayout.CENTER);
		add(Box.createRigidArea(MyGradebook.TOP_BOTTOM_BORDER), BorderLayout.NORTH);
		add(Box.createRigidArea(MyGradebook.LEFT_RIGHT_BORDER), BorderLayout.EAST);
		add(Box.createRigidArea(MyGradebook.LEFT_RIGHT_BORDER), BorderLayout.WEST);
		add(Box.createRigidArea(MyGradebook.TOP_BOTTOM_BORDER), BorderLayout.SOUTH);
	}

	// creates the button which runs the predictive grade calculations
	public JButton createCalculateButton()
	{
		JButton b = new JButton("Calculate");
		b.setAlignmentX(Component.LEFT_ALIGNMENT);
		b.setFont(MyGradebook.BUTTON_FONT);
		b.setToolTipText("Run calculation");
		b.setMinimumSize(MyGradebook.BUTTON_SIZE);
		b.setMaximumSize(MyGradebook.BUTTON_SIZE);
		b.setPreferredSize(MyGradebook.BUTTON_SIZE);
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Container parent = Calculator.this.getParent();
				JFrame frame = ((MyGradebook)parent).getFrame();

				String input = desiredText.getText().trim();
				if (input.isEmpty())	// textField left blank
				{
					JOptionPane.showMessageDialog(frame, "Desired grade cannot be left blank!",
						"Calculate Grade Error", JOptionPane.ERROR_MESSAGE);
					input = null;
				}
				else
				{
					try	// it baffles me that there is no good isInteger() library function yet
					{
						int desiredGrade = Integer.parseInt(input);
						if (desiredGrade < 1 || desiredGrade > 100)	// check in realistic range
						{
							JOptionPane.showMessageDialog(frame, "Desired grade must be an "
								+ "integer between 1 and 100!", "Calculate Grade Error",
								JOptionPane.ERROR_MESSAGE);
							input = null;
						}
						else	// good input
						{
							Class c = (Class) classCB.getSelectedItem();
							Category category = (Category) categoryCB.getSelectedItem();							
							double needed = 0.0;
							int numGrades = 0;

							if (category.hasWeight())	// case 1: Class weights total 100
							{
								double avgSum = 0.0;			// sum of known averages
								double known = 0.0;			// compilation of known values
								int subPercent = 0;			// other Grades to subtract off
								int emptyCat = 0;				// Categories without Grades yet

								for (Category cat : c.getCategories())
								{
									cat.calculateAverage();
									if (!cat.getName().equals(category.getName()) && cat.hasAverage())
									{ avgSum = avgSum + (cat.getAverage() * cat.getWeight()) / 100.0; }
									else if (!cat.getName().equals(category.getName()) && !cat.hasAverage())
									{ emptyCat = emptyCat + cat.getWeight(); }
								}
								if (category.hasAverage())		// other grades in Category of interest
								{
									subPercent = category.getAverage();
									for (Grade g : category.getGrades())
									{
										if (g.hasScore())
										{ numGrades = numGrades + 1; }
									}
								}
								known = (desiredGrade * (100.0 - emptyCat)) / 100.0;
								known = (known - avgSum) * (numGrades + 1) * 100.0  / category.getWeight();
								needed = known - (subPercent * numGrades);
							}
							else		// case 2: all Categories are unweighted
							{
								int avgSum = 0;

								for (Category cat : c.getCategories())
								{
									cat.calculateAverage();
									if (cat.hasAverage())
									{
										for (Grade g : cat.getGrades())
										{
											if (g.hasScore())
											{
												avgSum = avgSum + g.getPercentage();
												numGrades = numGrades + 1;
											}
										}
									}
								}
								needed = (desiredGrade * (numGrades + 1)) - avgSum;
							}
							if (needed < 0)
							{ needed = 0; }
							DecimalFormat df = new DecimalFormat("###.##");
							resultLabel.setText("You need to score a " + df.format(needed));
						}
					}
					catch(NumberFormatException ex)	// not a numeric string
					{
						JOptionPane.showMessageDialog(frame, "Desired grade must be an "
							+ "integer between 1 and 100!", "Calculate Grade Error",
							JOptionPane.ERROR_MESSAGE);
						input = null;
					}
				}
			}
		});
		return b;
	}

	// creates the middle panel labels, combo boxes, and text field for user interactions
	public JPanel createMiddlePanel()
	{
		Dimension comboBoxSize = new Dimension(275, 50);
		Dimension panelSize = new Dimension(700, 70);

		// create Class label and combo box
		JLabel classLabel = new JLabel("Class:", SwingConstants.RIGHT);
		classLabel.setFont(MyGradebook.BUTTON_FONT);
		classCB = new JComboBox<Class>();
		classCB.setFont(MyGradebook.BUTTON_FONT);
		classCB.setToolTipText("Select Class for calculation");
		classCB.setMinimumSize(comboBoxSize);
		classCB.setMaximumSize(comboBoxSize);
		classCB.setPreferredSize(comboBoxSize);
		classCB.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent ie)
			{
				if (ie.getStateChange() == ItemEvent.SELECTED)	// this stops event from firing
				{													// on Class add/remove, which
					// updates categoryCB based on Class selection	// caused NPEs
					categoryCB.removeAllItems();
					Class selectedClass = (Class)classCB.getSelectedItem();
					for (Category cat : selectedClass.getCategories())
					{ categoryCB.addItem(cat); }
				}
			}
		});
		
		// create Category label and combo box
		JLabel categoryLabel = new JLabel("Category:", SwingConstants.RIGHT);
		categoryLabel.setFont(MyGradebook.BUTTON_FONT);
		categoryCB = new JComboBox<Category>();
		categoryCB.setFont(MyGradebook.BUTTON_FONT);
		categoryCB.setToolTipText("Select Category for Grade of interest");
		categoryCB.setMinimumSize(comboBoxSize);
		categoryCB.setMaximumSize(comboBoxSize);
		categoryCB.setPreferredSize(comboBoxSize);
		
		// create desired grade label and text field
		JLabel desiredLabel = new JLabel("Desired Class Grade:", SwingConstants.RIGHT);
		desiredLabel.setFont(MyGradebook.BUTTON_FONT);
		desiredText = new JTextField();
		desiredText.setFont(MyGradebook.BUTTON_FONT);
		desiredText.setToolTipText("Enter the Grade you want to get in the Class");
		desiredText.setMinimumSize(comboBoxSize);
		desiredText.setMaximumSize(comboBoxSize);
		desiredText.setPreferredSize(comboBoxSize);

		// add everything to panel using GroupLayout for alignment
		JPanel middlePanel = new JPanel();
		GroupLayout layout = new GroupLayout(middlePanel);
		middlePanel.setLayout(layout);

		// creates a sequential group for the horizontal axis.  The sequential
			// group contains two parallel groups, one containing the labels
			// and the other the second column of objects.  Putting the labels
			// in a parallel group along the horizontal axis positions them at
			// the same x-location.
		GroupLayout.SequentialGroup rows = layout.createSequentialGroup();
		rows.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).
			addComponent(classLabel).
			addComponent(categoryLabel).
			addComponent(desiredLabel));
		rows.addGap(15);
		rows.addGroup(layout.createParallelGroup().
			addComponent(classCB).
			addComponent(categoryCB).
			addComponent(desiredText));
		layout.setHorizontalGroup(rows);

		// creates a sequential group for the vertical axis.  The sequential
			// group contains parallel groups that align the contents along the
			// baseline.  Each parallel group contains a label and the labeled
			// component.  By using a sequential group, the labels and
			// corresponding components are positioned vertically one after
			// another.
		GroupLayout.SequentialGroup columns = layout.createSequentialGroup();
		columns.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
			addComponent(classLabel).addComponent(classCB));
		columns.addGap(25);
		columns.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
			addComponent(categoryLabel).addComponent(categoryCB));
		columns.addGap(25);
		columns.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
			addComponent(desiredLabel).addComponent(desiredText));
		layout.setVerticalGroup(columns);

		return middlePanel;
	}

	// creates the back button which leads back to the ClassViewPanel
	public JButton createBackButton()
	{
		ImageIcon backIcon = new ImageIcon(MyGradebook.BACK_ICON.getImage()
			.getScaledInstance(20, 20, Image.SCALE_SMOOTH));

		JButton b = new JButton("Back", backIcon);
		b.setIconTextGap(7);
		b.setAlignmentX(Component.LEFT_ALIGNMENT);
		b.setFont(MyGradebook.DIALOG_FONT);
		b.setToolTipText("Back to Class View");
		b.setMinimumSize(MyGradebook.BACK_BUTTON_SIZE);
		b.setMaximumSize(MyGradebook.BACK_BUTTON_SIZE);
		b.setPreferredSize(MyGradebook.BACK_BUTTON_SIZE);
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Container parent = Calculator.this.getParent();
				ClassListPanel clp = ((MyGradebook)parent).getClassListPanel();
				clp.refresh();
				CardLayout cl = (CardLayout)parent.getLayout();
				cl.show(parent, "CLASS_VIEW_PANEL");
			}
		});
		return b;
	}

	// sets the Class initially selected on this screen, invoked from ClassViewPanel
		// before the CardLayout flips
	public void setClass(Class c)
	{
		// clear everything
		classCB.removeAllItems();
		desiredText.setText("");
		resultLabel.setText("");

		// add all Classes to classCB and set the selected one (this will
			// auto-update categoryCB also because it triggers the ItemListener)
		Container parent = Calculator.this.getParent();
		ClassListPanel clp = ((MyGradebook)parent).getClassListPanel();
		for (Class cl : clp.getClasses())
		{
			boolean w = false;
			for (Category cat: cl.getCategories())
			{
				if (cat.hasWeight())
				{
					w = true;
					break;
				}
			}
			if ((cl.getWeights() == 100) || !w)		// only allow calculation on classes with
			{ classCB.addItem(cl); }						// complete weights
		}
		classCB.setSelectedItem(c);
		refresh();
	}

	// refreshes the screen
	public void refresh()
	{
		revalidate();
		repaint();
	}
}
