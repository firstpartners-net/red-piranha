package net.firstpartners.core.file;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Simple Immutable Wrapper around an Original Apache POI Object Means we can
 * treat Word, Excel Docs in a similar way
 *
 * @author PBrowne
 * @version $Id: $Id
 */
public class OfficeDocument {

	Object original = null;
	private boolean isSpreadsheet =true;

	/**
	 * <p>isSpreadsheet.</p>
	 *
	 * @return a boolean
	 */
	public boolean isSpreadsheet() {
		return isSpreadsheet;
	}

	/**
	 * <p>Constructor for OfficeDocument.</p>
	 *
	 * @param wb a {@link org.apache.poi.ss.usermodel.Workbook} object
	 */
	public OfficeDocument(Workbook wb) {

		original = wb;
	}

	/**
	 * <p>Constructor for OfficeDocument.</p>
	 *
	 * @param doc a {@link org.apache.poi.xwpf.usermodel.XWPFDocument} object
	 */
	public OfficeDocument(XWPFDocument doc) {
		isSpreadsheet = false;
		original = doc;
	}
	
	/**
	 * <p>getOriginalAsPoiWordDoc.</p>
	 *
	 * @return a {@link org.apache.poi.xwpf.usermodel.XWPFDocument} object
	 */
	public XWPFDocument getOriginalAsPoiWordDoc() {
		return (XWPFDocument)original;
	}
	
	/**
	 * <p>getOriginalAsPoiWorkbook.</p>
	 *
	 * @return a {@link org.apache.poi.ss.usermodel.Workbook} object
	 */
	public Workbook getOriginalAsPoiWorkbook() {
		return (Workbook)original;
	}

}
