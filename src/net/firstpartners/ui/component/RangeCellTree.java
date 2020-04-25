package net.firstpartners.ui.component;

import java.awt.BorderLayout;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.Cell;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeHolder;

/**
 * Component we use on our panels to display Ranges and Cells Based on example
 * from @link
 * https://www.codejava.net/java-se/swing/jtree-basic-tutorial-and-examples
 */
public class RangeCellTree extends JPanel {

	private static final long serialVersionUID = 2126146342089903866L;
	private JTree tree;
	private DefaultMutableTreeNode treeRoot; // need to keep a handle to it later
	// private JLabel selectedLabel;

	public RangeCellTree() {

		// Logger
		final Logger log = RpLogger.getLogger(RangeCellTree.class.getName());

		treeRoot = new DefaultMutableTreeNode("Name of Ranges in Excel. Select Node for more details");

		// create the tree by passing in the treeRoot node
		tree = new JTree(treeRoot);
		tree.setShowsRootHandles(true);
		tree.setRootVisible(true);

		setLayout(new BorderLayout(2, 2));
		add(new JScrollPane(tree), BorderLayout.CENTER);

		// details of selected node
		JTextArea textArea = new JTextArea(2, 30);
		textArea.setText(" ");
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setOpaque(false);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setBackground(UIManager.getColor("Label.background"));
		textArea.setFont(UIManager.getFont("Label.font"));
		textArea.setBorder(UIManager.getBorder("Label.border"));

		// selectedLabel = new JLabel(" ");
		SwingGuiUtils.updateFontSize(textArea);
		add(new JScrollPane(textArea), BorderLayout.EAST);

		// Add the default tree listener
		tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {

				log.info(e.toString());
				Object thisNodeObject = ""; // default value
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

				if (selectedNode != null) {
					thisNodeObject = selectedNode.getUserObject();
				}

				if (thisNodeObject instanceof Cell) {
					Cell thisNodeCell = (Cell) thisNodeObject;
					textArea.setText(thisNodeCell.toLongString());

				} else {
					textArea.setText(thisNodeObject.toString());
				}

			}
		});

		tree.setSelectionRow(0);

	}

	/**
	 * Set the DataModel into our component, it will update the display
	 * automatically
	 * 
	 * @param redRange
	 */
	public void setDataModel(RangeHolder redRange) {

		for (Range range : redRange) {

			DefaultMutableTreeNode branch = new DefaultMutableTreeNode(range.getRangeName());
			treeRoot.add(branch);

			for (Cell cell : range.values()) {
				branch.add(new DefaultMutableTreeNode(cell));
			}

		}

		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		model.reload();

	}

}