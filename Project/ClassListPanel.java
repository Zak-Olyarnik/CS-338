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


// MyGradebook's first and main screen, where a user can view the list of their Classes, delete
	// them, or add more
public class ClassListPanel extends JPanel
{
	private ArrayList<Class> classes = new ArrayList<Class>();
	private JPanel classButtonsPanel;
	private JPanel deleteButtonsPanel;
	private JPanel scrollpaneHolderPanel;

	ClassListPanel(ArrayList<Class> cs)
	{
		classes = cs;

		// create panels to hold Class buttons and corresponding delete buttons
		classButtonsPanel = new JPanel();
		classButtonsPanel.setLayout(new BoxLayout(classButtonsPanel, BoxLayout.Y_AXIS));
		deleteButtonsPanel = new JPanel();
		deleteButtonsPanel.setLayout(new BoxLayout(deleteButtonsPanel, BoxLayout.Y_AXIS));

		// create intermediate panel to hold button panels
		JPanel classesPanel = new JPanel();
		classesPanel.setLayout(new BoxLayout(classesPanel, BoxLayout.X_AXIS));
		classesPanel.add(classButtonsPanel);
		classesPanel.add(deleteButtonsPanel);

		// create scrollpane to hold button panels
		JComponent scrollpane = new JScrollPane(classesPanel,
			JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollpane.setPreferredSize(new Dimension(519, 475));
		scrollpane.setBorder(BorderFactory.createEmptyBorder());

		// create intermediate panel to fix strange scrollpane resizing
		scrollpaneHolderPanel = new JPanel();
		scrollpaneHolderPanel.add(scrollpane);

		// create panel to hold all content on this screen
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		JLabel title = new JLabel("Classes");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		title.setFont(MyGradebook.TITLE_FONT);
		contentPanel.add(title);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		contentPanel.add(scrollpaneHolderPanel);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 135)));
		contentPanel.add(Box.createVerticalGlue());
		JButton addClassButton = createAddClassButton();
		contentPanel.add(addClassButton);

		// add borders for finished ClassListPanel object
		setLayout(new BorderLayout());
		add(contentPanel, BorderLayout.CENTER);
		add(Box.createRigidArea(MyGradebook.TOP_BOTTOM_BORDER), BorderLayout.NORTH);
		add(Box.createRigidArea(MyGradebook.LEFT_RIGHT_BORDER), BorderLayout.EAST);
		add(Box.createRigidArea(MyGradebook.LEFT_RIGHT_BORDER), BorderLayout.WEST);
		add(Box.createRigidArea(MyGradebook.TOP_BOTTOM_BORDER), BorderLayout.SOUTH);

		// populates this panel with any pre-defined Classes (read from file, etc.)
		if (!classes.isEmpty())
		{ refresh(); }
	}

	// creates a new button for each added Class
	public JButton createClassButton(Class c)
	{
		Dimension buttonSize = new Dimension(440, 50);

		JButton b = new JButton(c.getName());
		b.setAlignmentX(Component.CENTER_ALIGNMENT);
		b.setFont(MyGradebook.BUTTON_FONT);
		b.setToolTipText("View Class details");
		b.setMinimumSize(buttonSize);
		b.setMaximumSize(buttonSize);
		b.setPreferredSize(buttonSize);
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
			Container parent = ClassListPanel.this.getParent();
			ClassViewPanel cvp = ((MyGradebook)parent).getClassViewPanel();
			cvp.setClass(c);
			CardLayout cl = (CardLayout)parent.getLayout();
			cl.show(parent, "CLASS_VIEW_PANEL");
			}
		});		
		return b;
	}

	// creates a delete button which will correspond to a unique Class in the list
	public JButton createDeleteButton(Class c)
	{
		Dimension deleteButtonSize = new Dimension(60, 50);
		ImageIcon deleteIcon = new ImageIcon(MyGradebook.DELETE_ICON.getImage()
			.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
	
		JButton b = new JButton(deleteIcon);
		b.setAlignmentX(Component.CENTER_ALIGNMENT);
		b.setFont(MyGradebook.BUTTON_FONT);
		b.setToolTipText("Delete Class");
		b.setMinimumSize(deleteButtonSize);
		b.setMaximumSize(deleteButtonSize);
		b.setPreferredSize(deleteButtonSize);
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String className = c.getName();
				Object[] options = {"Yes", "Cancel"};
				int n = JOptionPane.showOptionDialog(scrollpaneHolderPanel,
					"Are you sure you want to delete Class " + className + "?",
					"Delete Class", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);
				if (n == JOptionPane.YES_OPTION)
				{
					classes.remove(c);
					refresh();
				}
			}
		});
		return b;
	}

	// creates the add Class button which invokes a dialog to create a new Class
	public JButton createAddClassButton()
	{
		ImageIcon addClassIcon = new ImageIcon(MyGradebook.ADD_ICON.getImage()
			.getScaledInstance(30, 30, Image.SCALE_SMOOTH));

		JButton b = new JButton("Add Class", addClassIcon);
		b.setIconTextGap(20);
		b.setAlignmentX(Component.CENTER_ALIGNMENT);
		b.setFont(MyGradebook.BUTTON_FONT);
		b.setToolTipText("Create a new Class");
		b.setMinimumSize(MyGradebook.BUTTON_SIZE);
		b.setMaximumSize(MyGradebook.BUTTON_SIZE);
		b.setPreferredSize(MyGradebook.BUTTON_SIZE);
		b.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Container parent = ClassListPanel.this.getParent();
				JFrame frame = ((MyGradebook)parent).getFrame();
				NewClassDialog ncd = new NewClassDialog(frame);
				ncd.setLocationRelativeTo(frame);
				ncd.setVisible(true);
			}
		});
		return b;
	}

	// gets the list of all Classes
	public ArrayList<Class> getClasses()
	{ return classes; }

	// checks for already-existing Class name
	public boolean duplicateClassName(String newClassName)
	{
		boolean duplicateFlag = false;
		for (Class c : classes)
		{
			if (c.getName().equals(newClassName))	// Class name already exists
			{
				duplicateFlag = true;
				break;
			}
		}
		return duplicateFlag;
	}

	// refreshes the screen
	public void refresh()
	{
		classButtonsPanel.removeAll();
		deleteButtonsPanel.removeAll();

		for(Class c : classes)
		{
			JButton b = createClassButton(c);
			classButtonsPanel.add(b);
			classButtonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

			JButton x = createDeleteButton(c);
			deleteButtonsPanel.add(x);
			deleteButtonsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
		}

		revalidate();
		repaint();
	}

	// custom dialog with error checking, invoked on Class add.  Adapted from:
	// http://stackoverflow.com/questions/13055107/joptionpane-check-user-input-and-prevent-from-closing-until-conditions-are-met
	class NewClassDialog extends JDialog implements ActionListener, PropertyChangeListener
	{
		private JTextField textField;
		private JOptionPane optionPane;
		private String enterButton = "Enter";
		private String cancelButton = "Cancel";

		// builds the custom dialog
		public NewClassDialog(Frame frame)
		{
			super(frame, true);
			setTitle("Add Class");
			String message = "Enter new Class name:";
			textField = new JTextField(20);
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
							"Add Class Error", JOptionPane.ERROR_MESSAGE);
						newClassName = null;
					}
					else
					{
						if (duplicateClassName(newClassName))	// Class name already exists
						{
							JOptionPane.showMessageDialog(this, "Class names must be unique!",
								"Add Class Error", JOptionPane.ERROR_MESSAGE);
							newClassName = null;
						}
						else	// good input
						{
							// create Class
							Class newClass = new Class(newClassName);
							classes.add(newClass);
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
}
