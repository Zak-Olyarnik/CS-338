/*
* Zakary Olyarnik
* zwo24@drexel.edu
* CS338:GUI, Assignment 1
*
*/

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class RealEstateList extends JPanel
{
	// UI components
	public static JTextField addressField, cityField, bedroomsField, bathroomsField,
		squareFeetField, yearField, priceField;
	public static JLabel addressLabel, cityLabel, bedroomsLabel, bathroomsLabel,
		squareFeetLabel, yearLabel, priceLabel;
	public static JButton addButton, editButton, removeButton, moveRightButton,
		moveLeftButton;

	// list and table UI and underlying models
	public static JList<PropertyObject> list;
	public static DefaultListModel<PropertyObject> listModel;
	public static JTable table;
	public static CustomTableModel tableModel;
	public static ListSelectionModel lsm;

	// I package all of my functional UI into one RealEstateList, which I
	// then add to the frame in main() down below (this works because
	// RealEstateList extends JPanel).  This pattern is based on this
	// documentation:
	// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextInputDemoProject/src/components/TextInputDemo.java
	//	I have abstracted methods to create the individual components such as
	//	buttons and the text-entry areas. It is necessary to pass in the frame
	//	so that I can create error message dialogs.
	RealEstateList(JFrame frame)
	{
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		// create topHalf, data entry components
		JPanel topHalf = new JPanel();
		topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));
		topHalf.add(Box.createRigidArea(new Dimension(30,30)));
		topHalf.add(Box.createHorizontalGlue());
		topHalf.add(createEntryFields());
		topHalf.add(Box.createRigidArea(new Dimension(30,30)));
		topHalf.add(createButtons(frame));
		topHalf.add(Box.createHorizontalGlue());
		topHalf.add(Box.createRigidArea(new Dimension(30,30)));

		// create bottomHalf, data display components
		JPanel bottomHalf = new JPanel();
		bottomHalf.setLayout(new BoxLayout(bottomHalf, BoxLayout.LINE_AXIS));
		bottomHalf.add(Box.createRigidArea(new Dimension(30,30)));
		bottomHalf.add(createList());
		bottomHalf.add(Box.createRigidArea(new Dimension(30,30)));
		bottomHalf.add(createSwapButtons());
		bottomHalf.add(Box.createRigidArea(new Dimension(30,30)));
		bottomHalf.add(createTable());
		bottomHalf.add(Box.createRigidArea(new Dimension(30,30)));

		// add everything to the RealEstateList JPanel
		add(Box.createRigidArea(new Dimension(30,30)));
		add(Box.createVerticalGlue());
		add(topHalf);
		add(Box.createRigidArea(new Dimension(30,30)));
		add(Box.createVerticalGlue());
		add(bottomHalf);
      add(Box.createRigidArea(new Dimension(30,30)));
		add(Box.createVerticalGlue());
	}

	// creates list using the DefaultListModel pattern, including a SelectionListener
	public JComponent createList()
	{
		listModel = new DefaultListModel<PropertyObject>();
      list = new JList<PropertyObject>(listModel);
      list.setVisibleRowCount(4);
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		list.addListSelectionListener(new ListSelectionListener()
			{
				public void valueChanged(ListSelectionEvent e)
				{
					if (e.getValueIsAdjusting() == false)
					{
						if(list.getSelectedIndex() == -1)	// nothing is selected, i.e.
						{													// user clicked away
							editButton.setEnabled(false);
							removeButton.setEnabled(false);
						}
						else	// populate textFields based on selection and enable buttons
						{
							table.clearSelection();
							PropertyObject p = listModel.getElementAt(list.getSelectedIndex());
							addressField.setText(p.getAddress());
							cityField.setText(p.getCity());
							bedroomsField.setText(p.getBedrooms());
							bathroomsField.setText(p.getBathrooms());
							squareFeetField.setText(p.getSquareFeet());
							yearField.setText(p.getYear());
							priceField.setText(p.getPrice());
							
							editButton.setEnabled(true);
							removeButton.setEnabled(true);
							moveRightButton.setEnabled(true);
						}
					}
				}
			});
      JComponent scrollpane = new JScrollPane(list);
		scrollpane.setPreferredSize(new Dimension(600,100));
		return scrollpane;
	}

	// creates table. I subclassed the DefaultTableModel to keep most of the
	// same implementation but to store an underlying list of PropertyObjects
	// as well. This allows easier addition, removal, editing, and indexing and
	// also makes checking for duplicates much easier. It is still necessary to
	// make use of a ListSelectionModel, the pattern for which I found here:
	// http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/SimpleTableSelectionDemoProject/src/components/SimpleTableSelectionDemo.java
	public JComponent createTable()
	{
		tableModel = new CustomTableModel();
		String[] columns = {"Address", "City", "Bedrooms", "Bathrooms", "Square Footage",
			"Year Built", "Price per Square Foot"};
		tableModel.setColumnIdentifiers(columns);
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		ListSelectionModel listSelectionModel = table.getSelectionModel();
		listSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		listSelectionModel.addListSelectionListener(new ListSelectionListener()
			{
				public void valueChanged(ListSelectionEvent e)
            {
            	if (e.getValueIsAdjusting() == false)
               {
						lsm = (ListSelectionModel)e.getSource();
                  if(lsm.isSelectionEmpty())
                  {
                     editButton.setEnabled(false);
                     removeButton.setEnabled(false);
                  }
                  else	// read data from table into new PropertyObject,then
                  {			// populate textFields from that
							list.clearSelection();
                     PropertyObject p = tableModel.getElementAt(lsm.getMinSelectionIndex());

                     addressField.setText(p.getAddress());
                     cityField.setText(p.getCity());
                     bedroomsField.setText(p.getBedrooms());
                     bathroomsField.setText(p.getBathrooms());
                     squareFeetField.setText(p.getSquareFeet());
                     yearField.setText(p.getYear());
                     priceField.setText(p.getPrice());

							moveLeftButton.setEnabled(true);
                     editButton.setEnabled(true);
                     removeButton.setEnabled(true);
                  }
               }
            }
         });
		table.setSelectionModel(listSelectionModel);
		JComponent scrollpane = new JScrollPane(table);
		scrollpane.setPreferredSize(new Dimension(600,100));
      return scrollpane;
	}

	// creates a CustomTableModel. I wanted most of the same implementation
	// of the DefaultTableModel, but I wanted the underlying data to be
	// stored as a list of PropertyObjects, just like the list.  I also
	// wrote custom methods for adding, getting, and removing elements so
	// that I can continue to deal with just PropertyElement data outside of
	// this class.  Loosely based on:
	// http://stackoverflow.com/questions/12559287/how-to-set-a-custom-object-in-a-jtable-row
	class CustomTableModel extends DefaultTableModel
	{
		private ArrayList<PropertyObject> properties;
		
		public CustomTableModel()
		{
			super();
			properties = new ArrayList<PropertyObject>();
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex)
		{
			Object value = "";
			PropertyObject p = properties.get(rowIndex);
			switch (columnIndex)
			{
				case 0:
					value = p.getAddress();
					break;
				case 1:
					value = p.getCity();
					break;
				case 2:
					value = p.getBedrooms();
					break;
				case 3:
					value = p.getBathrooms();
					break;
				case 4:
					value = p.getSquareFeet();
					break;
				case 5:
					value = p.getYear();
					break;
				case 6:
					value = p.getPrice();
					break;
			}
			return value;
		}
		
		public void addElement(PropertyObject p)
		{
			properties.add(p);
			String[] newRow = {p.getAddress(), p.getCity(), p.getBedrooms(),
				p.getBathrooms(), p.getSquareFeet(), p.getYear(), p.getPrice()};
		   tableModel.addRow(newRow);
		}

		public void removeElementAt(int index)
		{
			properties.remove(index);
			tableModel.removeRow(index);
		}

		public void setElementAt(PropertyObject p, int index)
		{
			properties.set(index, p);
			tableModel.setValueAt(p.getAddress(), index, 0);
			tableModel.setValueAt(p.getCity(), index, 1);
			tableModel.setValueAt(p.getBedrooms(), index, 2);
			tableModel.setValueAt(p.getBathrooms(), index, 3);
			tableModel.setValueAt(p.getSquareFeet(), index, 4);
			tableModel.setValueAt(p.getYear(), index, 5);
			tableModel.setValueAt(p.getPrice(), index, 6);
		}

		// because I now maintain a list of PropertyObjects separate from the
		// table itself, I can just directly return the PropertyObject
    	public PropertyObject getElementAt(int row)
	 	{ return properties.get(row); }
	
	}

	// Method to create the data entry part of the program. To easily align
	// the labels and textboxes, I adapted a GroupLayout as documented here:
	// http://docs.oracle.com/javase/8/docs/api/javax/swing/GroupLayout.html
	public JComponent createEntryFields()
	{
		JComponent panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		// create labels
		addressLabel = new JLabel("Street Address:");
		cityLabel = new JLabel("City:");
		bedroomsLabel = new JLabel("Number of Bedrooms:");
		bathroomsLabel = new JLabel("Number of Bathrooms:");
		squareFeetLabel = new JLabel("Square Footage:");
		yearLabel = new JLabel("Year Built:");
		priceLabel = new JLabel("Price per Square Foot:");

		// create text fields
		addressField = new JTextField(20);
		cityField = new JTextField(20);
		bedroomsField = new JTextField(20);
		bathroomsField = new JTextField(20);
		squareFeetField = new JTextField(20);
		yearField = new JTextField(20);
		priceField = new JTextField(20);

		// Turn on automatically adding gaps between components
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		// Create a sequential group for the horizontal axis
		GroupLayout.SequentialGroup rows = layout.createSequentialGroup();

		// The sequential group in turn contains two parallel groups.
		// One parallel group contains the labels, the other the text fields.
		// Putting the labels in a parallel group along the horizontal axis
		// positions them at the same x location.
		rows.addGroup(layout.createParallelGroup().
				addComponent(addressLabel).
				addComponent(cityLabel).
				addComponent(bedroomsLabel).
				addComponent(bathroomsLabel).
				addComponent(squareFeetLabel).
				addComponent(yearLabel).
				addComponent(priceLabel));
		rows.addGroup(layout.createParallelGroup().
				addComponent(addressField).
				addComponent(cityField).
				addComponent(bedroomsField).
				addComponent(bathroomsField).
				addComponent(squareFeetField).
				addComponent(yearField).
				addComponent(priceField));
		layout.setHorizontalGroup(rows);

		// Create a sequential group for the vertical axis.
		GroupLayout.SequentialGroup columns = layout.createSequentialGroup();

		// The sequential group contains two parallel groups that align
		// the contents along the baseline. The first parallel group contains
		// the first label and text field, the second parallel group contains
		// the second label and text field, and so on. By using a sequential group,
		// the labels and text fields are positioned vertically after one another.
		columns.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(addressLabel).addComponent(addressField));
		columns.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(cityLabel).addComponent(cityField));
		columns.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(bedroomsLabel).addComponent(bedroomsField));
		columns.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(bathroomsLabel).addComponent(bathroomsField));
		columns.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(squareFeetLabel).addComponent(squareFeetField));
		columns.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(yearLabel).addComponent(yearField));
		columns.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
				addComponent(priceLabel).addComponent(priceField));
		layout.setVerticalGroup(columns);

		return panel;
	}

	// Method to create the data entry buttons. To easily make all buttons
	// the same size and evenly spaced, I adapted a GridLayout as found here:
	// https://docs.oracle.com/javase/tutorial/uiswing/layout/grid.html 
	public JComponent createButtons(JFrame frame)
  	{
		JComponent panel = new JPanel();
		GridLayout layout = new GridLayout(0,1);
		panel.setLayout(layout);
		layout.setVgap(20);
		
		// addButton: creates a new PropertyObject from the user-entered data,
		// adds it to the listModel, and clears the textFields
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// throw error message box on empty field
					if((addressField.getText().equals("")) ||
						(cityField.getText().equals("")) ||
						(bedroomsField.getText().equals("")) ||
						(bathroomsField.getText().equals("")) ||
						(squareFeetField.getText().equals("")) ||
						(yearField.getText().equals("")) ||
						(priceField.getText().equals("")))
						{ JOptionPane.showMessageDialog(frame, "All fields are required.",
							"Add Error", JOptionPane.PLAIN_MESSAGE); }
					else	// check for duplicates in list, then table
					{
						boolean matchFlag = false;
						PropertyObject p = new PropertyObject(addressField.getText(),
							cityField.getText(), bedroomsField.getText(),
							bathroomsField.getText(), squareFeetField.getText(),
							yearField.getText(), priceField.getText());
						for(int i=0; i<listModel.size(); i++)
						{
							if(p.equals(listModel.elementAt(i)))
							{
								matchFlag = true;
								break;
							}
						}
						if(!matchFlag)	// only check table if no match has been found yet
						{
							for(int i=0; i<tableModel.getRowCount(); i++)
							{
								if(p.equals(tableModel.getElementAt(i)))
								{
									matchFlag = true;
									break;
								}
							}
						}
						
						if(matchFlag)
						{ JOptionPane.showMessageDialog(frame, "Property already exists in database.",
							"Add Error", JOptionPane.PLAIN_MESSAGE); }
						else	// successfully add to list
						{
							listModel.addElement(p);
							list.clearSelection();
							table.clearSelection();
							moveRightButton.setEnabled(false);
							moveLeftButton.setEnabled(false);
							clearTextFields();
						}
					}
				}
			});
		
		// editButton (enabled when a selection is made in either the list or
		// table): makes changes to the selected PropertyObject in the
		// underlying data model corresponding to the GUI component where the
		// selection was made. "Editing" a PropertyObject without actually
		// making changes is not disallowed 
		editButton =  new JButton("Edit");
		editButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					PropertyObject p = new PropertyObject(addressField.getText(),
						cityField.getText(), bedroomsField.getText(), bathroomsField.getText(),
						squareFeetField.getText(), yearField.getText(), priceField.getText());
					if(list.getSelectedIndex() != -1)	// selection from list
					{ listModel.setElementAt(p, list.getSelectedIndex()); }
					else	// selection from table
					{ tableModel.setElementAt(p, lsm.getMinSelectionIndex()); }
					
					clearTextFields();
					list.clearSelection();
					table.clearSelection();
					moveRightButton.setEnabled(false);
					moveLeftButton.setEnabled(false);
					editButton.setEnabled(false);
					removeButton.setEnabled(false);
				}
			});
		editButton.setEnabled(false);
		
		// removeButton (enabled when a selection is made in either the list
		// or table): removes the selection from the underlying data model
		// corresponding to the GUI component where the selection was made
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(list.getSelectedIndex() != -1)	// selection from list
				   { listModel.remove(list.getSelectedIndex()); }
					else	// selection from table
					{ tableModel.removeElementAt(lsm.getMinSelectionIndex()); }

					clearTextFields();
					moveRightButton.setEnabled(false);
					moveLeftButton.setEnabled(false);
					editButton.setEnabled(false);
					removeButton.setEnabled(false);
				}
			});
		removeButton.setEnabled(false);
		
		panel.add(addButton);
		panel.add(editButton);
		panel.add(removeButton);
		panel.setPreferredSize(new Dimension(200,200));
		panel.setMaximumSize(new Dimension(200,200));

		return panel;
	}

	// creates the additional buttons used to move data between the list and table
	public JComponent createSwapButtons()
	{
		JComponent panel = new JPanel();
		GridLayout layout = new GridLayout(0,1);
		panel.setLayout(layout);
		layout.setVgap(20);

		// moves from list to table
		moveRightButton = new JButton("=>");
		moveRightButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					PropertyObject p = listModel.getElementAt(list.getSelectedIndex());
               listModel.remove(list.getSelectedIndex());
					tableModel.addElement(p);

					list.clearSelection();
					table.clearSelection();
					clearTextFields();
					moveRightButton.setEnabled(false);
				}
			});
		moveRightButton.setEnabled(false);
		
		// moves from table to list
		moveLeftButton = new JButton("<=");
		moveLeftButton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					PropertyObject p = tableModel.getElementAt(lsm.getMinSelectionIndex());
					tableModel.removeElementAt(lsm.getMinSelectionIndex());
					listModel.addElement(p);

					list.clearSelection();
					table.clearSelection();
					clearTextFields();
					moveLeftButton.setEnabled(false);
				}
			});
		moveLeftButton.setEnabled(false);

		panel.add(moveRightButton);
		panel.add(moveLeftButton);
		panel.setMaximumSize(new Dimension(100,100));

		return panel;
	}

	// helper function to clear all textFields at once
	public void clearTextFields()
	{
		addressField.setText("");
      cityField.setText("");
      bedroomsField.setText("");
      bathroomsField.setText("");
      squareFeetField.setText("");
      yearField.setText("");
      priceField.setText("");
	}

	// main just creates the overall frame and adds the RealEstateList data
	// to it as a JPanel
	public static void main (String[] args)
	{	
		JFrame frame = new JFrame ("Real Estate List");
		frame.addWindowListener(new WindowAdapter()
			{ public void windowClosing (WindowEvent e) { System.exit(0); } });
		frame.getContentPane().add(new RealEstateList(frame));
		frame.pack();
		frame.setVisible(true);
	}
}
