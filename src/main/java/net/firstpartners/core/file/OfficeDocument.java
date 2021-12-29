package net.firstpartners.core.file;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Simple Immutable Wrapper around an Original Apache POI Object Means we can
 * treat Word, Excel Docs in a similar way
 * 
 * @author PBrowne
 *
 */
public class OfficeDocument {

	Object original = null;
	private boolean isSpreadsheet =true;

	public boolean isSpreadsheet() {
		return isSpreadsheet;
	}

	public OfficeDocument(Workbook wb) {

		original = wb;
	}

	public OfficeDocument(XWPFDocument doc) {
		isSpreadsheet = false;
		original = doc;
	}
	
	public XWPFDocument getOriginalAsPoiWordDoc() {
		return (XWPFDocument)original;
	}
	
	public Workbook getOriginalAsPoiWorkbook() {
		return (Workbook)original;
	}

}
