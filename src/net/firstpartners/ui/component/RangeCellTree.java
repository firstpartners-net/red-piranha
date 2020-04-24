package net.firstpartners.ui.component;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.firstpartners.core.spreadsheet.Cell;
import net.firstpartners.core.spreadsheet.Range;
import net.firstpartners.core.spreadsheet.RangeHolder;

/**
 * Component we use on our panels to display Ranges and Cells Based on example
 * from @author
 * https://www.codejava.net/java-se/swing/jtree-basic-tutorial-and-examples
 */
public class RangeCellTree extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2126146342089903866L;
	private JTree tree;
	private DefaultMutableTreeNode treeRoot; // need to keep a handle to it later
	private JLabel selectedLabel;

	public RangeCellTree() {
		
		treeRoot = new DefaultMutableTreeNode("Name of Ranges in Excel. Select Node for moore details");

		// create the tree by passing in the treeRoot node
		tree = new JTree(treeRoot);
		tree.setShowsRootHandles(true);
		tree.setRootVisible(true);

		setLayout(new BorderLayout());
		add(new JScrollPane(tree), BorderLayout.CENTER);

		selectedLabel = new JLabel();
		add(selectedLabel, BorderLayout.SOUTH);
		tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				selectedLabel.setText(selectedNode.getUserObject().toString());
			}
		});

	}

	/**
	 * Set the Data
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