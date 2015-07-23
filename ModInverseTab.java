import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**ModInverseTab.java
 * @author Gage Heeringa
 * 
 * The code in this class is divided into the following sections in this order:
 * 
 * global variables
 * constructor
 * gcd()
 * modInverse()
 * inner declaration of ActionListener class for tab to be interactive
 * gcdPopup()
 * 
 * declaration of CellRendererB class (last column in Mod Inverse table)
 * declaration of GCD_CheckBox ActionListener 
 * declaration of CellRendererC class (2nd-to-last column in GCD table)
 */
public class ModInverseTab extends JPanel {

	/** Global Variables */

	//image file for Compute b⁻¹ (mod a) button
	final ImageIcon computeModInverseImage = (new ImageIcon(getClass().getResource("computeModInverseButton.png")));

	//entry labels & corresponding text field
	JLabel aLabel;
	JFormattedTextField aEnter;
	JLabel bLabel;
	JFormattedTextField bEnter;

	//Compute Mod Inverse button & answer label
	JButton computeModInverseButton; 
	JTextPane modInverseAnswer;

	//Show GCD checkbox & listener
	JCheckBox showGcdCheckBox;
	GCD_CheckBox checkBoxListener;

	//table (showing GCD computation) maintenance
	JTable t;
	JScrollPane scroller;

	//similar congruence label
	static JLabel congr;


	/**Construct Modular Inverse Tab
	 */
	ModInverseTab() {
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

		bEnter = new JFormattedTextField();
		bEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		bLabel.setLabelFor(bEnter);

		//constraints: b entry
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 35 , 0, 55);
		add(bEnter, c);

		// jpanel with ___ layout contains computeModInverseButton and Show GCD checkbox

		//mod inverse button & checkbox & its listener
		computeModInverseButton = new JButton(computeModInverseImage); //Compute b⁻¹ (mod a)
		showGcdCheckBox = new JCheckBox("Show GCD");
		checkBoxListener = new GCD_CheckBox(false);
		showGcdCheckBox.addActionListener(checkBoxListener);

		//panel
		JPanel pan = new JPanel();
		BoxLayout boxLayout = new BoxLayout(pan, BoxLayout.X_AXIS); 
		pan.setLayout(boxLayout);

		pan.add(Box.createRigidArea(new Dimension(80, 0))); //filler to help center computeModInverse button
		pan.add(computeModInverseButton);
		pan.add(Box.createRigidArea(new Dimension(10, 0))); //space between button and checkbox
		pan.add(showGcdCheckBox);

		//constraints: panel
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.NONE;
		c.insets = new Insets(10, 0, 10 , 0);
		add(pan, c);

		//mod inverse answer
		modInverseAnswer = new JTextPane(){
			public boolean isEditable(){ //don't let user edit label           
				return false;      
			};
		};
		modInverseAnswer.setAlignmentX(CENTER_ALIGNMENT);
		modInverseAnswer.setText("");
		modInverseAnswer.setFont(new Font("Arial", Font.BOLD, 22));

		//center text for mod inverse answer using attributes stuff
		StyledDocument doc = modInverseAnswer.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		//constraints: mod inverse answer
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0, 5, 0);
		add(modInverseAnswer, c);

		//similar congruence label ( for if b needs to be reduced (mod a) )
		congr = new JLabel();
		congr.setFont(new Font("Arial", Font.PLAIN, 22));
		congr.setText(""); 

		//constraints: label
		c.insets = new Insets(0,0, 0, 0);
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		add(congr, c);

		//table with gcd computations
		DefaultTableModel defaultModel = new DefaultTableModel(new String[]{"A", "B", "X", "Y", "1 = AX + BY"}, 0);
		t = new JTable(defaultModel){
			public boolean isCellEditable(int row, int column){ //don't let user edit table               
				return false;      
			};
		};

		/* table specifications */
		t.setFont(new Font("Arial", Font.PLAIN, 20));
		t.setRowHeight(24);

		//make 1 = AX + BY column larger
		t.getColumnModel().getColumn(4).setPreferredWidth(150); //normal in gcd tab is 75 each
		t.getColumnModel().getColumn(4).setCellRenderer(new CellRendererB());

		ExtendedEuclideanVisual.alignTable(t, 2);

		//scroller containing table
		scroller = new JScrollPane(t);

		//constraints: scrollbar with gcd computations
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0 , 0);
		c.fill = GridBagConstraints.BOTH;
		add(scroller, c);

		//listeners for all interactive components
		ModInverseTabListener computeModInverseListener = new ModInverseTabListener();
		computeModInverseButton.addActionListener(computeModInverseListener);

	}//end constructor


	/**Find GCD(a,b) and perform pre-computations to find b^-1 (mod a).  Store steps in table.
	 * NOTE: Because of the way it fills the table, for the Chinese Remainder Thm tab, must check outside the method 
	 * so that the greater parameter is a!
	 */
	static BigInteger gcd(BigInteger a, BigInteger b, Stack s, JTable t){
		//this print statement won't work if b == 0
		//System.out.printf(a.toString() + " = " + b.toString() + "*(" + (a.divide(b)).toString() + ")" + 
		//		" + " + (a.remainder(b).toString()) + "\n");
		DefaultTableModel model = (DefaultTableModel) t.getModel();
		model.addRow(new String[]{a.toString(),		b.toString(),	"", 	"",	 ""});

		//0 mod anything is congruent to 0
		if(b.equals(BigInteger.ZERO))
			return b;

		s.push(a, b);

		if(a.remainder(b).equals(BigInteger.ZERO))
			return b;
		return gcd(b, a.remainder(b), s, t);
	}//end gcd()



	/**Find b^-1 (mod a) given Extended Euclidean Algorithm's computations.
	 * This method is only called when a modular inverse exists.
	 * 
	 * NOTE: Given a pre-computed stack s, call the first round of this method using BigInteger x = 0
	 * example: BigInteger modularInverse = modInverse(BigInteger.ZERO, s, s.size, t);
	 * 
	 * model.getRowCount() - (original stack size of s) + (current stack size of s) =  # of rows filled with data
	 * ^ don't need -1 in above line since we pop before this computation
	 */
	static BigInteger modInverse(BigInteger x, Stack s, int origSize, JTable t){
		Pair p = s.pop();
		BigInteger y = ( BigInteger.ONE.subtract((p.a).multiply(x)) ).divide(p.b); //1 = ax + by -> y = (1 - ax)/b

		//System.out.printf("%d\t%d\t%d\t%d\n",p.a,p.b,x,y);

		/* here is where table will be filled with data in right 3 columns from bottom up using stack */
		DefaultTableModel model = (DefaultTableModel) t.getModel();
		model.setValueAt(x.toString(), model.getRowCount() - origSize + s.size, 2); //X
		model.setValueAt(y.toString(), model.getRowCount() - origSize + s.size, 3); //Y
		model.setValueAt( (p.a.multiply(x)).add(p.b.multiply(y)).toString() + " = " + p.a + "(" + x + ") + " + p.b + "(" + y + ")"
				, model.getRowCount() - origSize + s.size, 4); //1 = AX + BY

		//last line of table
		if(s.size == 0){ 
			return y;
		}

		return modInverse(y, s, origSize, t);
	}//end modInverse()


	/**React if Compute Mod Inverse button is pressed.  Ensure entry for a and b are valid.  
	 * Answer appears in answer label.  Table displays steps.
	 */
	private class ModInverseTabListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {

			//attempt to read input
			BigInteger aEntry = new BigInteger("0"), bEntry = new BigInteger("0");
			try{
				aEntry = new BigInteger(aEnter.getText());
				bEntry = new BigInteger(bEnter.getText());
			} catch(Exception exc){
				JOptionPane.showMessageDialog(null, "Please enter valid integers for a and b.", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//make sure a > 0
			if(aEntry.compareTo(BigInteger.ZERO) < 1){
				JOptionPane.showMessageDialog(null, "The modulus (a) must be positive.", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			congr.setText(""); //clear similar congruence label

			//ENSURE GCD(a,b) = 1
			BigInteger gcd;

			//if a (the modulus) is less than b, then reduce b (mod a) to smallest integer between 0 and a
			//OR if b is negative, add the modulus to it so that it is the smallest integer between 0 and a
			BigInteger originalB = bEntry;
			if(aEntry.compareTo(bEntry) == -1 || bEntry.compareTo(BigInteger.ZERO) == -1){
				bEntry = bEntry.mod(aEntry); // b %= a
				//BigInteger mod() differs from remainder in that it always returns a non-negative BigInteger

				//update label to make congruence clear to user
				congr.setText("Note: " + bEnter.getText() + "≡" +bEntry.toString() + " (mod " + aEnter.getText() + ")");
			}

			//clear old computations in gui table
			DefaultTableModel dm = (DefaultTableModel) t.getModel();
			for(int i = dm.getRowCount() - 1; i >= 0; i--)
				dm.removeRow(i);

			//compute gcd
			Stack s = new Stack();
			gcd = gcd(aEntry, bEntry, s, t);

			//mod inverse DNE (does not exist)
			if(!gcd.equals(BigInteger.ONE)){
				//clear old modInverseAnswer, state that sol DNE
				String text = bEnter.getText() + "^-1 (mod " + aEnter.getText() + ") D.N.E."; 
				modInverseAnswer.setText( text );
				//pop up
				JOptionPane.showMessageDialog(null, "GCD(a,b) ≠ 1, meaning they are not coprime numbers and a modular inverse does not exist.",
						"Solution Does Not Exist", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//mod inverse exists, so compute and fill table
			BigInteger modInverse = modInverse(BigInteger.ZERO, s, s.size, t);

			//DISPLAY SOLUTION

			//if answer was negative, show negative answer computed in table as well as least positive answer between 0 and a
			String text = bEnter.getText() + "^-1 ≡ "; // b^-1 ≡ 

			if(modInverse.compareTo(BigInteger.ZERO) == -1){
				text += modInverse.toString() + " ≡ "; // negativeAnswer ≡

				while(modInverse.compareTo(BigInteger.ZERO) == -1) //while(answer < 0)
					modInverse = modInverse.add( aEntry ); //take a positive congruence for answer
			}

			text += modInverse.toString() + " (mod " + aEnter.getText() + ")";  // leastPosAnswer (mod a)
			text += "\n∴ " + bEnter.getText() + "*" + modInverse.toString() + " ≡ 1 (mod " + aEnter.getText() + ")"; 
			modInverseAnswer.setText( text );


			// -- gcd popup? --
			if(checkBoxListener.showGcd)
				gcdPopup(aEntry, bEntry, originalB, 2);

		}//end actionPerformed()

	}//end ModInverseTabListener class


	/**Show pop-up with gcd info.  Used in Mod Inverse, CRT tabs.
	 * 
	 * @param a Entry one.
	 * @param b Entry two.
	 * @param bOriginal Used only in ModInverseTab.  It is the unsimplified & original entry b (mod a)
	 * @param tab 1=GCD (unused), 2=ModInverse, 3=CRT
	 */
	static void gcdPopup(BigInteger a, BigInteger b, BigInteger bOriginal, int tab){

		// title for pop-up: GCD(k, j)  where k <= j
		String text;
		
		// make clear that the simplification of b(mod a) is being computed for ModInverseTab
		if( tab == 2){
			text = String.format("GCD(%s, %s ≡ %s) (mod %s)", 
					a.toString(), bOriginal.toString(), b.toString(), a.toString()); 
		}
		else{
			if(a.compareTo(b) == -1)
				text = "GCD(" + b.toString() + ", " + a.toString() + ") ";
			else
				text = "GCD(" + a.toString() + ", " + b.toString() + ") ";
		}

		
		//make sure not providing duplicate info, can happen when
		//(1) the user just hit the button with same input again, or (2) same last gcd computed in other tab
		if( ExtendedEuclideanVisual.extraFrame.getTitle().equals(text))
			return;


		// --- window setup ---
		ExtendedEuclideanVisual.extraFrame.getContentPane().removeAll(); //reset frame

		//title
		ExtendedEuclideanVisual.extraFrame.setTitle(text);


		//position slightly lower if there is a congruence simplification shown (ModInverseTab only)
		if( tab == 2){
			if(congr.getText().length() > 0){ 
				ExtendedEuclideanVisual.extraFrame.setLocation(60, 286); // right, down
			}
			else{
				ExtendedEuclideanVisual.extraFrame.setLocation(60, 286 - congr.getHeight());
			}
		}
		else{
			ExtendedEuclideanVisual.extraFrame.setLocation(60, 286 - congr.getHeight());
		}

		//size
		ExtendedEuclideanVisual.extraFrame.setPreferredSize(new Dimension(320, 350)); 
		//  width by height. (was 474,674) .. then (was 574,674)


		// -- add gcd table --
		//table with gcd computations
		DefaultTableModel model = new DefaultTableModel(new String[]{"A", "B", "c", "d"}, 0);
		JTable table = new JTable(model){
			public boolean isCellEditable(int row, int column){                
				return false;      
			};
		};

		//table specifications
		ExtendedEuclideanVisual.alignTable(table, 1);
		table.setFont(new Font("Arial", Font.PLAIN, 18));
		table.setRowHeight(24);
		table.getColumnModel().getColumn(3).setCellRenderer(new CellRendererA()); 

		//scroller containing table
		JScrollPane scrollPane = new JScrollPane(table);
		ExtendedEuclideanVisual.extraFrame.add(scrollPane);

		//compute
		if(a.compareTo(b) == -1){
			GCDTab.gcd(b, a, new Stack(), table);
		}
		else{
			GCDTab.gcd(a, b, new Stack(), table);
		}


		//pack and show
		ExtendedEuclideanVisual.extraFrame.pack();
		ExtendedEuclideanVisual.extraFrame.setVisible(true);

	}//end gcdPopup()
}//end ModInverseTab class


/**Custom cell rendering for last column (1 = AX + BY) in Modular Inverse Panel & CRTTab.
 */
class CellRendererB extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		c.setFont(new Font("Arial", Font.PLAIN, 17));
		c.setForeground(Color.BLUE);
		return c;
	}
}//end CellRenderer class


/**Set "showGcd" to true or false.
 */
class GCD_CheckBox implements ActionListener{
	boolean showGcd;

	GCD_CheckBox(boolean showGcd){
		this.showGcd = showGcd;
	}

	public void actionPerformed(ActionEvent e) {
		showGcd = showGcd ? false : true;
	}

}//end GCD_CheckBox class

/**Custom cell rendering for second-to-last column (Y) in Modular Inverse Panel & CRTTab.
 */
class CellRendererC extends DefaultTableCellRenderer {

	boolean modInverseTabSelected;

	//constructors
	CellRendererC(){
		this.modInverseTabSelected = true;
	}
	CellRendererC(boolean modInverseTabSelected){
		this.modInverseTabSelected = modInverseTabSelected ? true : false;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		c.setForeground(Color.RED);
		setHorizontalAlignment(SwingConstants.CENTER);

		c.setBackground(Color.WHITE);
		if(row == 0 && column == table.getColumnCount() - 2 && modInverseTabSelected){
			c.setBackground(Color.YELLOW);
		}

		return c;
	}
}//end CellRenderer class