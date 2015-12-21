import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.math.BigInteger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;


/**ExtendedEuclideanVisual.java
 * -Dependencies:
 *  	GCDTab.java
 *  	ModInverseTab.java
 *  	CRTTab.java
 *  	HelpTab.java
 * @author Gage Heeringa
 * 
 * Visualization GUI to
 * (1) find the greatest common divisor of two numbers using the Extended Euclidean Algorithm,
 * (2) find the modular inverse of a number, and
 * (3) use the Chinese Remainder Theorem to solve a congruence system.
 * 
 * The code in this class is divided into the following sections in this order:
 * 
 * global variables
 * main
 * constructor
 * alignTable() method (used in all tabs)
 * declaration of Stack, Pair classes
 * 
 * 
 * (Code for each tab is in its respective class.)
 */
public class ExtendedEuclideanVisual extends JFrame{

	/** Global Variables */

	//tabs
	JTabbedPane tabbedPane;
	JComponent gcdPanel;
	JComponent modInversePanel;
	JComponent crtPanel;
	JComponent helpPanel;
	
	//JFrame for "Show GCD" option in Mod Inverse & CRT tabs
	static JFrame extraFrame;

	final static int _WIDTH = 600;
	final static int _HEIGHT = 674;
	
	
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

		setLocation(370, 50);
		setPreferredSize(new Dimension(_WIDTH, _HEIGHT)); //width by height.

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

		//CRT Tab
		crtPanel = new CRTTab();
		tabbedPane.addTab("Chinese Remainder Theorem", crtPanel);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);;
		
		//help tab
		helpPanel = new HelpTab();
		tabbedPane.addTab("Help", helpPanel);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_4);;
		
		//collect panels in the jframe
		add(tabbedPane);
		pack(); //fit gui components nicely
		setVisible(true);
		

		//extra frame (Show GCD computation) setup
		extraFrame = new JFrame();
		extraFrame.setVisible(false);
		ExtendedEuclideanVisual.extraFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 

	}//end constructor


	/**Maintain font color and position in table.
	 * tab 1 - gcd,  2 - mod inverse,  3 - crt
	 */
	static void alignTable(JTable t, int tab){ 
		DefaultTableCellRenderer renderer0 = new DefaultTableCellRenderer();
		renderer0.setForeground(Color.BLUE);
		if( tab > 1)
			renderer0.setHorizontalAlignment(SwingConstants.CENTER);
		else
			renderer0.setHorizontalAlignment(SwingConstants.RIGHT);
		t.getColumnModel().getColumn(0).setCellRenderer(renderer0);

		DefaultTableCellRenderer renderer1 = new DefaultTableCellRenderer();
		renderer1.setForeground(Color.BLACK);
		if(tab > 1)
			renderer1.setHorizontalAlignment(SwingConstants.CENTER);
		else
			renderer1.setHorizontalAlignment(SwingConstants.RIGHT);
		t.getColumnModel().getColumn(1).setCellRenderer(renderer1);

		DefaultTableCellRenderer renderer2 = new DefaultTableCellRenderer();
		renderer2.setForeground(Color.GREEN);
		if(tab > 1)
			renderer2.setHorizontalAlignment(SwingConstants.CENTER);
		else
			renderer2.setHorizontalAlignment(SwingConstants.LEFT);
		t.getColumnModel().getColumn(2).setCellRenderer(renderer2);

		//custom cell rendering for last column in GCD & second-to-last in mod inverse
		if(tab == 3){
			t.getColumnModel().getColumn(3).setCellRenderer(new CellRendererC(false));
		}
		else if(tab == 2){ 
			t.getColumnModel().getColumn(3).setCellRenderer(new CellRendererC());
		}
		else{
			t.getColumnModel().getColumn(3).setCellRenderer(new CellRendererA());
		}
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