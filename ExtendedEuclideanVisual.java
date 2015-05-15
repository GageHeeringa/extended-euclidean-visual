import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.math.BigInteger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**ExtendedEuclideanVisual.java
 * @author Gage Heeringa
 * 
 * Visualization GUI for (1) finding the greatest common divisor of two numbers using the Extended Euclidean Algorithm and
 * (2) finding the modular inverse of a number.
 * 
 * ex. Euclidean for GCD~~~~~
 * gcd(27,15)
 *
 * 27 = 15(1) + 12        
 * 15 = 12(1) + 3 
 * 12 =  3(4) + 0 -> gcd = 3
 * 
 * ex. Extended Euclidean for modular inverse~~~~~
 * inverse of 5(mod 27)
 * 
 * gcd(5,27) = 1 iff an inverse exists
 * 
 * top to bottom     | bottom to top
 *
 * A     B              X      Y   	(1 = AX + BY)
 * 27 =  5(5) + 2      -2     11     1 = 27(-2) + 5(11) -> 5^-1 (mod 27) = 11
 *  5 =  2(2) + 1       1     -2     1 = 5(1) + 2(-2)
 *  2 =  1(2) + 0       0      1	 1 = 2(0) + 1(1)	
 *    -> gcd = 1
 */
public class ExtendedEuclideanVisual extends JFrame{

	//image file for Compute b⁻¹ (mod a) button
	final ImageIcon computeModInverseImage = (new ImageIcon(getClass().getResource("computeModInverseButton.png")));
	
	
	/** Global Variables */

	//tabs
	JTabbedPane tabbedPane;
	JComponent gcdPanel;
	JComponent modInversePanel;


	/** modInversePanel */
	//entry labels & corresponding text field
	JLabel _aLabel;
	JFormattedTextField _aEnter;
	JLabel _bLabel;
	JFormattedTextField _bEnter;

	//Compute Mod Inverse button & answer label
	JButton _computeModInverseButton; 
	JTextPane _modInverseAnswer;

	//table (showing GCD computation) maintenance
	JTable _t;
	JScrollPane _scroller;

	//similar congruence label
	JLabel _congr;


	/** gcdPanel */
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


	/** End Global Variables */


	/**Main
	 */
	public static void main(String[] args){
		new ExtendedEuclideanVisual();
	}//end main


	/**Constructor
	 */
	ExtendedEuclideanVisual(){
		//Main Window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Extended Euclidean Visual");
		setIconImage(new ImageIcon(getClass().getResource("Euclid.ico")).getImage());

		setLocation(200, 50);
		setPreferredSize(new Dimension(574, 674)); //width by height. (was 474,674)

		//JTabbedPane
		tabbedPane = new JTabbedPane();

		//GCD Tab
		gcdPanel = createGcdPanel();
		tabbedPane.addTab("Greatest Common Divisor", gcdPanel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		//Mod Inverse Tab
		modInversePanel = createModInversePanel();
		tabbedPane.addTab("Modular Inverse", modInversePanel);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		add(tabbedPane);
		pack(); //fit gui components nicely
		setVisible(true);

	}//end constructor


	/**Contents of GCD Tab
	 */
	JComponent createGcdPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//a & b labels in top row
		aLabel = new JLabel("Enter a:", JLabel.CENTER);
		aLabel.setFont(new Font("Arial", Font.PLAIN, 22));

		//constraints: a label
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.BASELINE;
		c.weightx = 1.0;
		c.weighty = 0.01;
		panel.add(aLabel, c);

		//b label
		bLabel = new JLabel("Enter b:", JLabel.CENTER);
		bLabel.setFont(new Font("Arial", Font.PLAIN, 22));

		//constraints: b label
		c.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(bLabel, c);

		//a & b entry text fields in next row
		aEnter = new JFormattedTextField();
		aEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		aLabel.setLabelFor(aEnter);

		//constraints: a entry
		c.gridwidth = GridBagConstraints.WEST;
		c.weighty = 0;
		c.insets = new Insets(0, 55 , 0, 35);
		panel.add(aEnter, c);

		//b entry text field
		bEnter = new JFormattedTextField();
		bEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		bLabel.setLabelFor(bEnter);

		//constraints: b entry
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 35 , 0, 55);
		panel.add(bEnter, c);

		//gcd button
		computeGcdButton = new JButton("Compute GCD");
		computeGcdButton.setFont(new Font("Arial", Font.PLAIN, 22));

		//constraints: gcd button
		c.insets = new Insets(15, 20, 15 , 0);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.NONE;
		panel.add(computeGcdButton, c);

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
		panel.add(gcdAnswer, c);

		//label: A = B*(c) + d
		JLabel temp = new JLabel("A = B*(c) + d");
		temp.setFont(new Font("Arial", Font.PLAIN, 22));

		//constraints: label
		c.insets = new Insets(0,0, 0, 0);
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(temp, c);

		//table with gcd computations
		DefaultTableModel model = new DefaultTableModel(new String[]{"A", "B", "c", "d"}, 0);
		t = new JTable(model){
			public boolean isCellEditable(int row, int column){                
				return false;      
			};
		};

		//table specifications
		alignTable(t, false);
		t.setFont(new Font("Arial", Font.PLAIN, 22));
		t.setRowHeight(24);


		//scroller containing table
		scroller = new JScrollPane(t);

		//constraints: scrollbar with gcd computations
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0 , 0);
		c.fill = GridBagConstraints.BOTH;
		panel.add(scroller, c);

		//listeners for all interactive components
		GcdTabListener computeGcdListener = new GcdTabListener();
		computeGcdButton.addActionListener(computeGcdListener);

		return panel;
	}//end createGcdPanel()


	/**Contents of Modular Inverse Tab
	 */
	JComponent createModInversePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//a & b labels in top row
		_aLabel = new JLabel("Enter a:", JLabel.CENTER);
		_aLabel.setFont(new Font("Arial", Font.PLAIN, 22));

		//constraints: a label
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.BASELINE;
		c.weightx = 1.0;
		c.weighty = 0.01;
		panel.add(_aLabel, c);

		_bLabel = new JLabel("Enter b:", JLabel.CENTER);
		_bLabel.setFont(new Font("Arial", Font.PLAIN, 22));

		//constraints: b label
		c.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(_bLabel, c);

		//a & b entry text fields in next row
		_aEnter = new JFormattedTextField();
		_aEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		_aLabel.setLabelFor(_aEnter);

		//constraints: a entry
		c.gridwidth = GridBagConstraints.WEST;
		c.weighty = 0;
		c.insets = new Insets(0, 55 , 0, 35);
		panel.add(_aEnter, c);

		_bEnter = new JFormattedTextField();
		_bEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		_bLabel.setLabelFor(_bEnter);

		//constraints: b entry
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(0, 35 , 0, 55);
		panel.add(_bEnter, c);

		//mod inverse button
		_computeModInverseButton = new JButton(computeModInverseImage); //Compute b⁻¹ (mod a)
		
		//constraints: mod inverse button
		c.insets = new Insets(15, 20, 15 , 0);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.NONE;
		panel.add(_computeModInverseButton, c);

		//mod inverse answer
		_modInverseAnswer = new JTextPane(){
			public boolean isEditable(){ //don't let user edit label           
				return false;      
			};
		};
		_modInverseAnswer.setAlignmentX(CENTER_ALIGNMENT);
		_modInverseAnswer.setText("");
		_modInverseAnswer.setFont(new Font("Arial", Font.BOLD, 22));

		//center text for mod inverse answer using attributes stuff
		StyledDocument doc = _modInverseAnswer.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		//constraints: mod inverse answer
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0,0, 5, 0);
		panel.add(_modInverseAnswer, c);

		//similar congruence label ( for if b needs to be reduced (mod a) )
		_congr = new JLabel();
		_congr.setFont(new Font("Arial", Font.PLAIN, 22));
		_congr.setText(""); 

		//constraints: label
		c.insets = new Insets(0,0, 0, 0);
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		panel.add(_congr, c);

		//table with gcd computations
		DefaultTableModel defaultModel = new DefaultTableModel(new String[]{"A", "B", "X", "Y", "1 = AX + BY"}, 0);
		_t = new JTable(defaultModel){
			public boolean isCellEditable(int row, int column){ //don't let user edit table               
				return false;      
			};
		};

		/* table specifications */
		_t.setFont(new Font("Arial", Font.PLAIN, 22));
		_t.setRowHeight(24);

		//make 1 = AX + BY column larger
		_t.getColumnModel().getColumn(4).setPreferredWidth(150); //normal in gcd tab is 75 each
		_t.getColumnModel().getColumn(4).setCellRenderer(new CustomCellRenderer());

		alignTable(_t, true);

		//scroller containing table
		_scroller = new JScrollPane(_t);

		//constraints: scrollbar with gcd computations
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0 , 0);
		c.fill = GridBagConstraints.BOTH;
		panel.add(_scroller, c);

		//listeners for all interactive components
		ModInverseTabListener computeModInverseListener = new ModInverseTabListener();
		_computeModInverseButton.addActionListener(computeModInverseListener);

		return panel;
	}


	//GCD Tab Methods~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**Find GCD(a,b) and perform pre-computations to find b^-1 (mod a).  Store steps in table.
	 */
	static BigInteger gcdTabExtendedEuclidean(BigInteger a, BigInteger b, Stack s, JTable t){
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
		return gcdTabExtendedEuclidean(b, a.remainder(b), s, t);
	}


	//Mod Inverse Tab Methods~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**Find GCD(a,b) and perform pre-computations to find b^-1 (mod a).  Store steps in table.
	 */
	static BigInteger modInverseTabExtendedEuclidean(BigInteger a, BigInteger b, Stack s, JTable t){
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
		return modInverseTabExtendedEuclidean(b, a.remainder(b), s, t);
	}


	/**Find b^-1 (mod a) given Extended Euclidean Algorithm's computations.
	 * This method is only called when a modular inverse exists.
	 * 
	 * NOTE: Given a pre-computed stack s, call the first round of this method using BigInteger x = 0
	 * example: BigInteger modularInverse = modInverseTabModularInverse(BigInteger.ZERO, s, s.size, _t);
	 * 
	 * model.getRowCount() - (original stack size of s) + (current stack size of s) =  # of rows filled with data
	 * ^ don't need -1 in above line since we pop before this computation
	 */
	static BigInteger modInverseTabModularInverse(BigInteger x, Stack s, int origSize, JTable t){
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
			while(y.compareTo(BigInteger.ZERO) == -1) //while(y < 0)
				y = y.add(p.a); //take a positive congruence for answer
			return y;
		}

		return modInverseTabModularInverse(y, s, origSize, t);
	}


	//Other Methods~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**Maintain font color and position in table.
	 */
	void alignTable(JTable t, boolean modInverseTable){ 
		DefaultTableCellRenderer renderer0 = new DefaultTableCellRenderer();
		renderer0.setForeground(Color.BLUE);
		if(modInverseTable)
			renderer0.setHorizontalAlignment(SwingConstants.CENTER);
		else
			renderer0.setHorizontalAlignment(SwingConstants.RIGHT);
		t.getColumnModel().getColumn(0).setCellRenderer(renderer0);

		DefaultTableCellRenderer renderer1 = new DefaultTableCellRenderer();
		renderer1.setForeground(Color.BLACK);
		if(modInverseTable)
			renderer1.setHorizontalAlignment(SwingConstants.CENTER);
		else
			renderer1.setHorizontalAlignment(SwingConstants.RIGHT);
		t.getColumnModel().getColumn(1).setCellRenderer(renderer1);

		DefaultTableCellRenderer renderer2 = new DefaultTableCellRenderer();
		renderer2.setForeground(Color.GREEN);
		if(modInverseTable)
			renderer2.setHorizontalAlignment(SwingConstants.CENTER);
		else
			renderer2.setHorizontalAlignment(SwingConstants.LEFT);
		t.getColumnModel().getColumn(2).setCellRenderer(renderer2);

		DefaultTableCellRenderer renderer3 = new DefaultTableCellRenderer();
		renderer3.setForeground(Color.RED);
		if(modInverseTable)
			renderer3.setHorizontalAlignment(SwingConstants.CENTER);
		else
			renderer3.setHorizontalAlignment(SwingConstants.LEFT);
		t.getColumnModel().getColumn(3).setCellRenderer(renderer3);
	}//end alignTable()


	//Listeners~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**React if Compute Mod Inverse button is pressed.  Ensure entry for a and b are valid.  
	 * Answer appears in answer label.  Table displays steps.
	 */
	private class ModInverseTabListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {

			//attempt to read input
			BigInteger _aEntry = new BigInteger("0"), _bEntry = new BigInteger("0");
			try{
				_aEntry = new BigInteger(_aEnter.getText());
				_bEntry = new BigInteger(_bEnter.getText());
			} catch(Exception exc){
				JOptionPane.showMessageDialog(null, "Please enter valid integers for a and b.", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//make sure a > 0
			if(_aEntry.compareTo(BigInteger.ZERO) < 1){
				JOptionPane.showMessageDialog(null, "The modulus (a) must be positive.", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			_congr.setText(""); //clear similar congruence label

			//ENSURE GCD(a,b) = 1
			BigInteger gcd;
			//if a (the modulus) is less than b, then reduce b (mod a) to smallest integer between 0 and a
			//OR if b is negative, add the modulus to it so that it is the smallest integer between 0 and a
			if(_aEntry.compareTo(_bEntry) == -1 || _bEntry.compareTo(BigInteger.ZERO) == -1){
				_bEntry = _bEntry.mod(_aEntry); // b %= a
				//BigInteger mod() differs from remainder in that it always returns a non-negative BigInteger

				//update label to make congruence clear to user
				_congr.setText("Note: " + _bEnter.getText() + "≡" +_bEntry.toString() + " (mod " + _aEnter.getText() + ")");
			}

			//clear old computations in gui table
			DefaultTableModel dm = (DefaultTableModel) _t.getModel();
			for(int i = dm.getRowCount() - 1; i >= 0; i--)
				dm.removeRow(i);

			//compute gcd
			Stack s = new Stack();
			gcd = modInverseTabExtendedEuclidean(_aEntry, _bEntry, s, _t);

			//mod inverse DNE (does not exist)
			if(!gcd.equals(BigInteger.ONE)){
				//clear old modInverseAnswer, state that sol DNE
				String text = _bEnter.getText() + "^-1 (mod " + _aEnter.getText() + ") D.N.E."; 
				_modInverseAnswer.setText( text );
				//pop up
				JOptionPane.showMessageDialog(null, "GCD(a,b) ≠ 1, meaning they are not coprime numbers and a modular inverse does not exist.",
						"Solution Does Not Exist", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//mod inverse exists, so compute and fill table
			BigInteger modInverse = modInverseTabModularInverse(BigInteger.ZERO, s, s.size, _t);

			String text = _bEnter.getText() + "^-1 ≡ " + modInverse.toString() + " (mod " + _aEnter.getText() + ")"; 
			text += "\n∴ " + _bEnter.getText() + "*" + modInverse.toString() + " ≡ 1 (mod " + _aEnter.getText() + ")"; 
			_modInverseAnswer.setText( text );
		}

	}//end ModInverseTabListener class (inner class of ExtendedEuclideanVisualization)


	/**React if Compute GCD button is pressed.  Ensure entry for a and b are valid.  
	 * Answer appears in answer label.  Table displays steps.
	 */
	private class GcdTabListener implements ActionListener {

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
				gcd = gcdTabExtendedEuclidean(bEntry, aEntry, new Stack(), t);
			else
				gcd = gcdTabExtendedEuclidean(aEntry, bEntry, new Stack(), t);
			String text = "GCD(" + aEntry.toString() + ", " + bEntry.toString() + ") = " + gcd.toString();

			gcdAnswer.setText( text );
		}

	}//end GcdTabListener class (inner class of ExtendedEuclideanVisualization)

}//end ExtendedEuclideanVisualization class


/**Custom cell rendering for last column (1 = AX + BY) in Modular Inverse Panel.
 */
class CustomCellRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column){

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		c.setFont(new Font("Arial", Font.PLAIN, 17));
		c.setForeground(Color.BLUE);
		return c;
	}
}//end CustomCellRenderer class


/** stack of (A,B) pairs */
class Stack{
	Pair head;
	int size;

	Stack(){
		head = null;
		size = 0;
	}//end constructor

	void push(BigInteger a, BigInteger b){
		size++;
		if(head == null){
			head = new Pair(a,b,null);
			return;
		}
		Pair p = new Pair(a,b,head);
		head = p;
	}
	Pair pop(){
		if(head == null)
			return null;
		Pair ret = head;
		head = head.next;
		size--;
		return ret;
	}

}//end Stack class


/** contains integers A,B */
class Pair{
	BigInteger a,b;
	Pair next;

	Pair(BigInteger a, BigInteger b, Pair next){
		this.a = a;
		this.b = b;
		this.next = next;
	}//end constructor

}//end Pair class