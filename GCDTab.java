import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**GCDTab.java
 * @author Gage Heeringa
 * 
 * The code in this class is divided into the following sections in this order:
 * 
 * global variables
 * constructor
 * gcd()
 * inner declaration of ActionListener class for tab to be interactive
 * 
 * declaration of CellRendererA class (last column in GCD table)
 */
public class GCDTab extends JPanel{

	/** Global Variables */
	//entry labels & corresponding text field
	JLabel aLabel;
	JFormattedTextField aEnter;
	JLabel bLabel;
	JFormattedTextField bEnter;

	//Compute GCD button & answer label
	JButton computeGcdButton; 
	JTextPane gcdAnswer;

	//table (showing GCD computation) maintenance
	JTable t;
	JScrollPane scroller;


	/**Construct GCD Tab
	 */
	GCDTab() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//a & b labels in top row
		aLabel = new JLabel("Enter a:", JLabel.CENTER);
		aLabel.setFont(new Font("Arial", Font.PLAIN, 22));

		//constraints: a label
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.BASELINE;
		c.weightx = 1.0;
		c.weighty = 0.01;
		add(aLabel, c);

		//b label
		bLabel = new JLabel("Enter b:", JLabel.CENTER);
		bLabel.setFont(new Font("Arial", Font.PLAIN, 22));

		//constraints: b label
		c.gridwidth = GridBagConstraints.REMAINDER;
		add(bLabel, c);

		//a & b entry text fields in next row
		aEnter = new JFormattedTextField();
		aEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		aLabel.setLabelFor(aEnter);

		//constraints: a entry
		c.gridwidth = GridBagConstraints.WEST;
		c.weighty = 0;
		c.insets = new Insets(0, 55 , 0, 35);
		add(aEnter, c);

		//b entry text field
		bEnter = new JFormattedTextField();
		bEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		bLabel.setLabelFor(bEnter);

		//constraints: b entry
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 35 , 0, 55);
		add(bEnter, c);

		//gcd button
		computeGcdButton = new JButton("Compute GCD");
		computeGcdButton.setFont(new Font("Arial", Font.PLAIN, 22));

		//constraints: gcd button
		c.insets = new Insets(15, 20, 15 , 0);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.NONE;
		add(computeGcdButton, c);

		//gcd answer
		gcdAnswer = new JTextPane(){
			public boolean isEditable(){ //don't let user edit label            
				return false;      
			};
		};
		gcdAnswer.setAlignmentX(CENTER_ALIGNMENT);
		gcdAnswer.setText("");
		gcdAnswer.setFont(new Font("Arial", Font.BOLD, 22));

		//center text
		StyledDocument doc = gcdAnswer.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		//constraints: gcd answer
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0, 5, 0);
		add(gcdAnswer, c);

		//label: A = B*(c) + d
		JLabel temp = new JLabel("A = B*(c) + d");
		temp.setFont(new Font("Arial", Font.PLAIN, 22));

		//constraints: label
		c.insets = new Insets(0,0, 0, 0);
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		add(temp, c);

		//table with gcd computations
		DefaultTableModel model = new DefaultTableModel(new String[]{"A", "B", "c", "d"}, 0);
		t = new JTable(model){
			public boolean isCellEditable(int row, int column){                
				return false;      
			};
		};

		//table specifications
		ExtendedEuclideanVisual.alignTable(t, 1);
		t.setFont(new Font("Arial", Font.PLAIN, 22));
		t.setRowHeight(24);
		t.getTableHeader().setReorderingAllowed(false);


		//scroller containing table
		scroller = new JScrollPane(t);

		//constraints: scrollbar with gcd computations
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0 , 0);
		c.fill = GridBagConstraints.BOTH;
		add(scroller, c);

		//listeners for all interactive components
		GcdTabListener computeGcdListener = new GcdTabListener();
		computeGcdButton.addActionListener(computeGcdListener);

	}//end constructor


	/**Find GCD(a,b). Store steps in table.
	 */
	static BigInteger gcd(BigInteger a, BigInteger b, Stack s, JTable t){
		//System.out.printf(a.toString() + " = " + b.toString() + "*(" + (a.divide(b)).toString() + ")" + 
		//		" + " + (a.remainder(b).toString()) + "\n");
		DefaultTableModel model = (DefaultTableModel) t.getModel();
		model.addRow(new String[]{a.toString() + " =", 	
				b.toString() + "*",						
				"(" + (a.divide(b)).toString() + ")",   
				"+ " + (a.remainder(b).toString())}     
				);

		s.push(a, b);

		if(a.remainder(b).equals(BigInteger.ZERO))
			return b;
		return gcd(b, a.remainder(b), s, t);
	}//end gcd()
    
	
	/**React if Compute GCD button is pressed.  Ensure entry for a and b are valid.  
	 * Answer appears in answer label.  Table displays steps.
	 */
	class GcdTabListener implements ActionListener {

		public void actionPerformed(ActionEvent e){

			BigInteger aEntry = new BigInteger("0"), bEntry = new BigInteger("0");
			try{
				aEntry = new BigInteger(aEnter.getText());
				bEntry = new BigInteger(bEnter.getText());
			} catch(Exception exc){
				JOptionPane.showMessageDialog(null, "Please enter valid integers for a and b.", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//make sure a,b are not 0
			if(aEntry.equals(BigInteger.ZERO) || bEntry.equals(BigInteger.ZERO)){
				JOptionPane.showMessageDialog(null, "Please enter valid integers for a and b.", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//clear old computations in gui table
			DefaultTableModel dm = (DefaultTableModel) t.getModel();
			for (int i = dm.getRowCount() - 1; i >= 0; i--)
				dm.removeRow(i);

			BigInteger gcd;
			if(aEntry.compareTo(bEntry) == -1)
				gcd = gcd(bEntry, aEntry, new Stack(), t);
			else
				gcd = gcd(aEntry, bEntry, new Stack(), t);
			String text = "GCD(" + aEntry.toString() + ", " + bEntry.toString() + ") = " + gcd.toString();

			gcdAnswer.setText( text );

		}
	}//end GcdTabListener class 

}//end GCDTab class


/**Custom cell rendering for column 3 in GCD Tab (so that the answer can be highlighted).
 */
class CellRendererA extends DefaultTableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		c.setForeground(Color.RED);
		
		c.setBackground(Color.WHITE);
		if(row == table.getRowCount() - 2 && column == table.getColumnCount() - 1){
			c.setBackground(Color.YELLOW);
		}
			
		return c;
	}
}//end CellRenderer class
