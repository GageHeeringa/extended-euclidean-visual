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
 * -Dependencies:
 *  	GCDTab.java
 *  	ModInverseTab.java
 * @author Gage Heeringa
 * 
 * Visualization GUI for 
 * (1) finding the greatest common divisor of two numbers using the Extended Euclidean Algorithm and
 * (2) finding the modular inverse of a number.
 *
 * The code in this class is divided into the following sections in this order:
 * 
 * global variables
 * main
 * constructor
 * alignTable() method (used in all tabs)
 * declaration of Stack, Pair classes
 * 
 * (Code for each tab is in its respective class.)
 * 
 * -----
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

/**TODO
 * 
 * link to blog explaining each manually
 * 
 */
public class ExtendedEuclideanVisual extends JFrame{

	/** Global Variables */

	//tabs
	JTabbedPane tabbedPane;
	JComponent gcdPanel;
	JComponent modInversePanel;


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

		setLocation(200, 50);
		setPreferredSize(new Dimension(600, 674)); //width by height. (was 474,674) .. then (was 574,674)

		//JTabbedPane
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Dialog", Font.PLAIN, 12));

		//GCD Tab
		gcdPanel = new GCDTab();
		tabbedPane.addTab("Greatest Common Divisor", gcdPanel);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1); 

		//Mod Inverse Tab
		modInversePanel = new ModInverseTab();
		tabbedPane.addTab("Modular Inverse", modInversePanel);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		add(tabbedPane);
		pack(); //fit gui components nicely
		setVisible(true);

	}//end constructor


	/**Maintain font color and position in table.
	 */
	static void alignTable(JTable t, boolean modInverseTable){ 
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

}//end ExtendedEuclideanVisualization class


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