package net.firstpartners.ui.component;

import java.awt.Font;

import javax.swing.JComponent;

public class SwingGuiUtils {

	/**
	 * Helper method to update font size of a Swing Component
	 * 
	 * @param swingComponent
	 */
	public static void updateFontSize(JComponent swingComponent) {
		Font currentFont = swingComponent.getFont();
		Font updatedFont = currentFont.deriveFont(currentFont.getSize() * 1.4F);
		swingComponent.setFont(updatedFont);

	}

}
