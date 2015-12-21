import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;

/**HelpTab.java
 * @author Gage Heeringa
 */
public class HelpTab extends JPanel{

	final ImageIcon gcdEx;
	final ImageIcon modInvEx;
	final ImageIcon crtEx;

	/**Constructor
	 */
	HelpTab(){
		gcdEx = (new ImageIcon(getClass().getResource("gcdEx.png")));
		modInvEx = (new ImageIcon(getClass().getResource("modInverseEx.png")));
		crtEx = (new ImageIcon(getClass().getResource("crtEx.png")));

		setLayout(new GridLayout(1,1));

		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.addTab("GCD Example", helpPanel( gcdEx ));
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1); 

		tabbedPane.addTab("Mod Inverse Example", helpPanel( modInvEx ));
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2); 

		tabbedPane.addTab("CRT Example", helpPanel( crtEx ));
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3); 

		add(tabbedPane);

	}//end constructor


	/**Return panel containing example computation within a scrollbar.
	 * Input is the image to display.
	 */
	JPanel helpPanel(ImageIcon im){
		JPanel ret = new JPanel();
		ret.setLayout(new GridLayout(1,1));

		//scroller contains image
		JScrollPane scroll = new JScrollPane( new JLabel(im) );
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		ret.add(scroll);

		return ret;
	}
}
