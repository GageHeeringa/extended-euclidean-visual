import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**CRTTab.java
 * @author Gage Heeringa
 * 
 * Unlike in the other tabs, the CRT Tab JPanel contains several sub-JPanels - one for each row of content.
 * 
 * The code in this class is divided into the following sections in this order:
 * 
 * global variables
 * constructor
 * panel1() - enter 1st congruence
 * panel2() - enter 2nd congruence
 * panel3() - "Solve System" & "Import a System" buttons
 * panel4() - whitespace strip for answer
 * panel5() - explain computation
 * panel6() - table
 * panel7() - final explanation of solution
 * inner declaration of ActionListener class for tab to be interactive
 * 
 */
public class CRTTab extends JPanel{

	/** Global Variables in sub-panel 1           */
	//entry labels & corresponding text field
	// α ≡ i (mod m)
	JLabel alphaLabel1; // α ≡
	JFormattedTextField iEnter; // ENTER i
	JLabel leftPar1; // (mod
	JFormattedTextField mEnter; // ENTER m
	JLabel rightPar1; // )

	/** Global Variables in sub-panel 2            */
	// α ≡ j (mod n)
	JLabel alphaLabel2; // α ≡
	JFormattedTextField jEnter; // ENTER j
	JLabel leftPar2; // (mod
	JFormattedTextField nEnter; // ENTER n
	JLabel rightPar2; // )

	/** Global Variables in sub-panel 3            */
	//Solve button
	JButton solveButton; 
	JButton importButton;
	//Show GCD checkbox & listener
	JCheckBox gcdCheckBox;
	GCD_CheckBox checkBoxListener;

	/** Global Variables in sub-panel 4           */
	JTextPane crtAnswer; // α ≡ _ (mod m*n)

	/** (NO Global Variables in sub-panel 5)       */

	/** Global Variables in sub-panel 6           */
	//table (showing computation) maintenance
	JTable t;
	JScrollPane scroller;

	/** Global Variables in sub-panel 7           */
	JTable answerTable;

	/**Construct CRT Tab. (Box layout)
	 */
	CRTTab() {
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS); // top to bottom
		setLayout(boxLayout);

		JPanel pan1, pan2, pan3, pan4, pan5, pan6, pan7; //sub-panels
		pan1 = panel1();
		pan1.setPreferredSize(new Dimension(ExtendedEuclideanVisual._WIDTH, 46)); // main window is 600 width, 674 height
		pan2 = panel2();
		pan2.setPreferredSize(new Dimension(ExtendedEuclideanVisual._WIDTH, 46));
		pan3 = panel3();
		pan3.setPreferredSize(new Dimension(ExtendedEuclideanVisual._WIDTH, 46));
		pan4 = panel4();
		pan4.setPreferredSize(new Dimension(ExtendedEuclideanVisual._WIDTH, 36));
		pan5 = panel5();
		pan5.setPreferredSize(new Dimension(ExtendedEuclideanVisual._WIDTH, 120));
		pan6 = panel6();
		pan6.setPreferredSize(new Dimension(ExtendedEuclideanVisual._WIDTH, 168));
		pan7 = panel7();
		pan7.setPreferredSize(new Dimension(ExtendedEuclideanVisual._WIDTH, 167));

		add(pan1); add(pan2); add(pan3); add(pan4); add(pan5); add(pan6); add(pan7);
	}//end constructor


	/**Return the first sub-panel in the CRT tab.  
	 * Here the first congruence is entered (ROW 1)
	 */
	JPanel panel1(){
		JPanel panel = new JPanel();

		// α ≡ i (mod m) in ROW 1

		// α ≡ label
		alphaLabel1 = new JLabel("α ≡", JLabel.CENTER);
		alphaLabel1.setFont(new Font("Arial", Font.PLAIN, 22));
		panel.add(alphaLabel1);

		//enter i
		iEnter = new JFormattedTextField();
		iEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		alphaLabel1.setLabelFor(iEnter);
		iEnter.setPreferredSize(new Dimension( ExtendedEuclideanVisual._WIDTH/3 , 31));
		panel.add(iEnter);

		//(mod label
		leftPar1 = new JLabel(" (mod");
		leftPar1.setFont(new Font("Arial", Font.PLAIN, 22));
		panel.add(leftPar1);

		//enter m
		mEnter = new JFormattedTextField();
		mEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		leftPar1.setLabelFor(mEnter);
		mEnter.setPreferredSize(new Dimension( ExtendedEuclideanVisual._WIDTH/4 , 31));
		panel.add(mEnter);

		//) label
		rightPar1 = new JLabel(")");
		rightPar1.setFont(new Font("Arial", Font.PLAIN, 22));
		panel.add(rightPar1);

		return panel;
	}// end panel1()


	/**Second sub-panel 
	 * Second congruence entered (ROW 2)
	 */
	JPanel panel2(){
		JPanel panel = new JPanel();

		//α ≡ label
		alphaLabel2 = new JLabel("α ≡", JLabel.CENTER);
		alphaLabel2.setFont(new Font("Arial", Font.PLAIN, 22));
		panel.add(alphaLabel2);

		//enter j
		jEnter = new JFormattedTextField();
		jEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		alphaLabel2.setLabelFor(jEnter);
		jEnter.setPreferredSize(new Dimension( ExtendedEuclideanVisual._WIDTH/3 , 31));
		panel.add(jEnter);

		//(mod label
		leftPar2 = new JLabel(" (mod");
		leftPar2.setFont(new Font("Arial", Font.PLAIN, 22));
		panel.add(leftPar2);

		//enter m
		nEnter = new JFormattedTextField();
		nEnter.setFont(new Font("Arial", Font.PLAIN, 22));
		leftPar2.setLabelFor(nEnter);
		nEnter.setPreferredSize(new Dimension( ExtendedEuclideanVisual._WIDTH/4 , 31));
		panel.add(nEnter);

		//) label
		rightPar2 = new JLabel(")");
		rightPar2.setFont(new Font("Arial", Font.PLAIN, 22));
		panel.add(rightPar2);

		return panel;
	}// end panel2()


	/**Third sub-panel
	 * "Solve system" button and Show GCD check box
	 */
	JPanel panel3(){
		JPanel pan = new JPanel();
		BoxLayout boxLayout = new BoxLayout(pan, BoxLayout.X_AXIS); 
		pan.setLayout(boxLayout);

		//solve button
		solveButton = new JButton("Solve System");
		solveButton.setFont(new Font("Arial", Font.PLAIN, 20));

		//checkbox
		gcdCheckBox  = new JCheckBox("Show GCD");

		//add to panel
		pan.add(Box.createRigidArea(new Dimension(80, 0))); //filler to help center button
		pan.add(solveButton);
		pan.add(Box.createRigidArea(new Dimension(10, 0))); //space between button and checkbox
		pan.add(gcdCheckBox);

		//listeners for interactive components
		CRT_Solve_Listener solveListener = new CRT_Solve_Listener();
		solveButton.addActionListener(solveListener);

		checkBoxListener = new GCD_CheckBox(false);
		gcdCheckBox.addActionListener(checkBoxListener);

		return pan;
	}// end panel3()


	/**CRT Answer (appears as a long whitespace strip)
	 */
	JPanel panel4(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		crtAnswer = new JTextPane(){
			public boolean isEditable(){ //don't let user edit label            
				return false;      
			};
		};
		crtAnswer.setAlignmentX(CENTER_ALIGNMENT);
		crtAnswer.setText("");
		crtAnswer.setFont(new Font("Arial", Font.PLAIN, 22));

		//center text
		StyledDocument doc = crtAnswer.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);

		//constraints: crt answer
		c.weightx = c.weighty = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 0, 0, 0);
		panel.add(crtAnswer, c);

		return panel;
	}//end panel4()


	/**Label explaining system
	 */
	JPanel panel5(){
		JPanel panel = new JPanel();

		//&#09; = an indentation tab  -----  &nbsp; = whitespace
		String text = "<html><div align=\"left\">If GCD(m,n) = 1," +
				" then consider the system as<br></div>" +
				"<b>&#09;&#09;α ≡ i (mod m)<br>&#09;&#09;α ≡ j (mod n)</b>" +
				"<br><div align=\"left\">Then rewrite it as<br></div>" +
				"&#09;&nbsp;&nbsp;&nbsp;&nbsp;<b>α ≡ jmy + inx (mod mn)</b></html>";

		JLabel l = new JLabel(text); 
		l.setFont(new Font("Arial", Font.PLAIN, 18));
		panel.add(l);

		return panel;
	}//end 5()


	/**Computation table
	 */
	JPanel panel6(){
		JPanel panel = new JPanel();
		panel.setLayout( new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//table with gcd computations
		DefaultTableModel defaultModel = new DefaultTableModel(new String[]{"A", "B", "X", "Y", "1 = AX + BY"}, 0);
		t = new JTable(defaultModel){
			public boolean isCellEditable(int row, int column){ //don't let user edit table               
				return false;      
			};

			/**For drawing arrows between cells to clarify workflow.
			 */
			@Override
			protected void paintComponent(Graphics g){

				super.paintComponent(g);

				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(2));

				//draw arrows
				for (int i = 0; i < getRowCount() - 1; i++)
				{
					// -- draw arrow between A,B columns --
					// stem
					Rectangle cell = getCellRect(i, 0, false); // cell.x , y = top left corner coords. of rectangle
					int outReach = 5; //length to go from paint starting point
					Point center = new Point(cell.x + cell.width, cell.y + cell.height); //bottom right corner of cell
					Point sw = new Point(center.x - outReach, center.y + outReach); //go southwest. remember x++ is right, y++ is down
					Point ne = new Point(center.x + outReach, center.y - outReach); //go northeast
					g2.setColor( Color.BLACK);
					g2.drawLine(sw.x, sw.y, ne.x, ne.y);

					//now the arrow tip starting from (sw.x, sw.y) - upper part here
					outReach = 6;
					center = new Point(sw.x, sw.y);
					Point north = new Point(center.x, center.y - outReach); 
					g2.drawLine(center.x, center.y, north.x, north.y);

					//lower part of arrow tip
					Point e = new Point(center.x + outReach, center.y); //east
					g2.drawLine(center.x, center.y, e.x, e.y);


					// -- X,Y columns --
					cell = getCellRect(i, 2, false);
					outReach = 5; 
					center = new Point(cell.x + cell.width, cell.y + cell.height); //bottom right corner of cell
					Point nw = new Point(center.x - outReach, center.y - outReach); 
					Point se = new Point(center.x + outReach, center.y + outReach); 
					g2.drawLine(nw.x, nw.y, se.x, se.y);

					//horizontal tip (above other part)
					outReach = 6;
					center = new Point(nw.x, nw.y);
					e = new Point(center.x + outReach, center.y ); 
					g2.drawLine(center.x, center.y, e.x, e.y);

					//vertical tip (lower)
					Point south = new Point(center.x, center.y + outReach);
					g2.drawLine(center.x, center.y, south.x, south.y);

				}
			}// end paintComponent()
		};

		/* table specifications */
		t.setFont(new Font("Arial", Font.PLAIN, 20));
		t.setRowHeight(24); //24 on mod inverse tab
		t.getTableHeader().setReorderingAllowed(false);

		//make 1 = AX + BY column larger
		t.getColumnModel().getColumn(4).setPreferredWidth(150); 
		t.getColumnModel().getColumn(4).setCellRenderer(new CellRendererB());

		ExtendedEuclideanVisual.alignTable(t, 3);

		//scroller containing table
		scroller = new JScrollPane(t);

		//constraints: scrollbar with gcd computations
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0 , 0);
		c.fill = GridBagConstraints.BOTH;
		panel.add(scroller, c);

		return panel;
	}//end 6()


	/**Final explanation of answer		 
	 */
	JPanel panel7(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		//table with explanation of answer
		DefaultTableModel model = new DefaultTableModel(7,1);
		answerTable = new JTable(model){
			public boolean isCellEditable(int row, int column){                
				return false;      
			};
		};

		//table specifications
		answerTable.setTableHeader(null); 
		answerTable.setShowGrid(false); //this and line below so that lines don't show in table
		answerTable.setIntercellSpacing(new Dimension(0, 0));
		answerTable.setRowHeight(24);
		answerTable.setFont(new Font("Arial", Font.PLAIN, 20));
		answerTable.setBackground( solveButton.getBackground());

		//constraints: scroller
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0 , 0);
		c.fill = GridBagConstraints.BOTH;
		panel.add(answerTable, c);

		//text for table
		String text = "<html>1 = a(x) + b(y)<br>" +
				"&nbsp;&nbsp; = 345346(234234234234) + 23(3242342342342323), so<br>" +
				"<b>α</b> ≡ jmy + inx<br></html>" +
				"<html>&nbsp;&nbsp; ≡ 327(3)(3) + 8(3)(11)<br>" +
				"&nbsp;&nbsp; ≡ 328 (mod 77)<br>" +
				"&nbsp;&nbsp;<b> ≡ 37 (mod 77)</b></html>";

		return panel;
	}//end 7()


	/**React if Solve System button is pressed.
	 */
	private class CRT_Solve_Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			//attempt to read input
			BigInteger iEntry, mEntry, jEntry, nEntry = new BigInteger("0");
			try{
				iEntry = new BigInteger(iEnter.getText());
				mEntry = new BigInteger(mEnter.getText());
				jEntry = new BigInteger(jEnter.getText());
				nEntry = new BigInteger(nEnter.getText());
			} catch(Exception exc){
				JOptionPane.showMessageDialog(null, "Enter valid integers.", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//make sure the moduli are > 0
			if(mEntry.compareTo(BigInteger.ZERO) < 1 || nEntry.compareTo(BigInteger.ZERO) < 1){
				JOptionPane.showMessageDialog(null, "The moduli must be positive.", "Error", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//clear old labels 
			crtAnswer.setText("");
			DefaultTableModel dm = (DefaultTableModel) answerTable.getModel();
			for(int i = dm.getRowCount() - 1; i >= 0; i--)
				dm.setValueAt("", i, 0);

			//ENSURE GCD(a,b) = 1
			BigInteger gcd;

			//clear old computations in gui table
			dm = (DefaultTableModel) t.getModel();
			for(int i = dm.getRowCount() - 1; i >= 0; i--)
				dm.removeRow(i);

			//compute gcd
			Stack s = new Stack();
			gcd = ModInverseTab.gcd(nEntry, mEntry, s, t);

			// m,n must be coprime (...for this program)
			if(!gcd.equals(BigInteger.ONE)){
				crtAnswer.setText("Irreducible Answer");

				//pop up - solution does not exist
				JOptionPane.showMessageDialog(null, "GCD(a, b) ≠ 1",
						"Solution Does Not Exist", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			//mod inverse exists, so compute and fill table
			ModInverseTab.modInverse(BigInteger.ZERO, s, s.size, t);

			//PARSE 1 = ax + by FROM LAST (TOP NORTHEAST) CELL IN TABLE  
			String[] sol = ((String) t.getModel().getValueAt(0, 4)).split(" "); //1 = 5(3) + 7(-2) becomes [1, =, 5(3), +, 7(-2)]
			BigInteger a = new BigInteger(sol[2].substring( 0, sol[2].indexOf("("))); //extract a
			BigInteger b = new BigInteger(sol[4].substring( 0, sol[4].indexOf("("))); //extract b

			BigInteger x = new BigInteger(sol[2].substring( sol[2].indexOf("(")+1, sol[2].indexOf(")"))); //extract x
			BigInteger y = new BigInteger(sol[4].substring( sol[4].indexOf("(")+1, sol[4].indexOf(")"))); //extract y

			//SOLVE  α ≡ JMY + INX
			BigInteger answer = jEntry.multiply(mEntry).multiply(y); // JMY + INX
			BigInteger sum1 = answer;
			BigInteger sum2 = iEntry.multiply(nEntry).multiply(x);
			answer = answer.add( sum2 );

			//now reduce it mod m*n (and make sure it's positive)
			BigInteger newModulus = mEntry.multiply( nEntry ); //JMY + INX reduced mod (m*n)
			BigInteger finalAnswer = answer.mod( newModulus ); // answerA %= m*n

			//rename entry variables for convenience...
			BigInteger i,m,j,n; // & we have: x, y, answer, finalAnswer, newModulus, sum1, sum2
			i = iEntry; m = mEntry; j = jEntry; n = nEntry;


			//set answer label
			crtAnswer.setText("α ≡ " + finalAnswer + " (mod " + newModulus.toString() + ")");

			//set explanation in last panel
			DefaultTableModel model = (DefaultTableModel) answerTable.getModel();
			answerTable.setValueAt(String.format(" 1 = ax + by"), 0, 0); 
			answerTable.setValueAt(String.format("    = %s(%s) + %s(%s), so", a,x,b,y), 1, 0);
			answerTable.setValueAt("<html><b>α</b> ≡ jmy + inx</html>", 2, 0);
			answerTable.setValueAt(String.format("   ≡ %s(%s)(%s) + %s(%s)(%s)", j,m,y,i,n,x), 3, 0); //&nbsp; = space
			answerTable.setValueAt(String.format("   ≡ %s + %s", sum1, sum2), 4, 0);
			//show negative congruence first computed
			if( answer.compareTo(BigInteger.ZERO) < 0){
				answerTable.setValueAt(String.format("   ≡ %s", answer), 5, 0);
				answerTable.setValueAt(String.format("<html>&nbsp;&nbsp; <b>≡ %s (mod %s)</b></html>", finalAnswer, newModulus), 6, 0);
			}
			else{
				answerTable.setValueAt(String.format("<html>&nbsp;&nbsp; <b>≡ %s (mod %s)</b></html>", finalAnswer, newModulus), 5, 0);
			}


			// -- gcd popup? --
			if(checkBoxListener.showGcd)
				ModInverseTab.gcdPopup(nEntry, mEntry, null, 3);

			//to rearrange A,B columns if n < m
			s = new Stack();
			//switch parameters if n (the second modulus) is greater than m (the first modulus)
			if(nEntry.compareTo(mEntry) < 0){
				dm = (DefaultTableModel) t.getModel();
				for(int k = dm.getRowCount() - 1; k >= 0; k--){
					dm.removeRow(k);
				}
				gcd = ModInverseTab.gcd(mEntry, nEntry, s, t);
				ModInverseTab.modInverse(BigInteger.ZERO, s, s.size, t);
			}

		}//end actionPerformed()

	}//end CRTTabListener class
}
