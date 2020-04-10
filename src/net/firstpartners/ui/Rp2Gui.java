package net.firstpartners.ui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.firstpartners.core.drools.PreCompileRuleBuilder;
import org.drools.compiler.compiler.DroolsParserException;

/**
 * Previously released as 'RulePlayer', Desktop GUI for Red Piranha
 * @author PBrowne
 *
 */
public class Rp2Gui extends WindowAdapter implements WindowListener, ActionListener, Runnable {
	private JFrame frame = new JFrame("Red-Piranha RulePlayer - Build your rules");
	private JTextArea textArea;
	private boolean quit = false;

	void compileRules() {
		String ruleFile = "http://red-piranha.googlecode.com/svn/trunk/war/sampleresources/SpreadSheetServlet/log-then-modify-rules.drl";
		if (ruleFile == null) {
			this.textArea.append("Please set 'rule' as a param pointing at the rule file you wish to compile ");
		} else {
			this.textArea.append("Compiling Rules...\n");
			this.textArea.append("Using file:" + ruleFile + "\n");
			PreCompileRuleBuilder rulebuilder = new PreCompileRuleBuilder();

			try {
				rulebuilder.cacheRule(ruleFile, (String) null);
			} catch (DroolsParserException var4) {
				this.textArea.append("DroolsParserException:" + var4 + "\n");
				var4.printStackTrace();
			} catch (IOException var5) {
				this.textArea.append("IOException:" + var5.getMessage() + "\n");
				var5.printStackTrace();
			} catch (ClassNotFoundException var6) {
				this.textArea.append("ClassNotFoundException:" + var6.getMessage() + "\n");
				var6.printStackTrace();
			}

			this.textArea.append("Compiling complete\n");
		}

	}

	public Rp2Gui() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension(screenSize.width / 2, screenSize.height / 2);
		int x = frameSize.width / 2;
		int y = frameSize.height / 2;
		this.frame.setBounds(x, y, frameSize.width, frameSize.height);
		this.textArea = new JTextArea();
		this.textArea.setEditable(false);
		this.textArea.setLineWrap(true);
		JButton button = new JButton("Run Again");
		this.frame.getContentPane().setLayout(new BorderLayout());
		this.frame.getContentPane().add(new JScrollPane(this.textArea), "Center");
		this.frame.getContentPane().add(button, "South");
		this.frame.setVisible(true);
		this.frame.addWindowListener(this);
		button.addActionListener(this);
		this.quit = false;
	}

	public synchronized void windowClosed(WindowEvent evt) {
		this.quit = true;
		this.notifyAll();
		System.exit(0);
	}

	public synchronized void windowClosing(WindowEvent evt) {
		this.frame.setVisible(false);
		this.frame.dispose();
	}

	public synchronized void actionPerformed(ActionEvent evt) {
		this.textArea.setText("");
		Runnable readRun = new Thread(this);
		readRun.run();
	}

	public synchronized void run() {
		while (true) {
			try {
				if (this.quit = false) {
					Thread.sleep(1000L);
					continue;
				}
			} catch (InterruptedException var2) {
				this.textArea.append(var2.toString());
			} catch (Throwable var3) {
				this.textArea.append(var3.toString());
			}

			return;
		}
	}

	public static void main(String[] arg) {
		Rp2Gui player = new Rp2Gui();
		Runnable readRun = new Thread(player);
		readRun.run();
		player.compileRules();
		System.out.println("RulePlayer Complete");
	}
}
