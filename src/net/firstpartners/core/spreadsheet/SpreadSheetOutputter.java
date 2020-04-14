package net.firstpartners.core.spreadsheet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Convenience Class for Output of Spreadhsheets
 * @author paul
 *
 */
public class SpreadSheetOutputter {

	private static Log log = LogFactory.getLog(SpreadSheetOutputter.class);

	/**
	 * Outputs an Apache POI Workbook to a file
	 * @param wb - Apache POI Workbook (excel)
	 * @param fileName
	 * @throws IOException
	 */
	public static void outputToFile(Workbook wb, String fileName) throws IOException{

		// Write out modified Excel sheet
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(
					fileName);
			outputToStream(wb,fileOutputStream);
			fileOutputStream.close();
		} catch (java.security.AccessControlException ace){
			//Unable to output file (e.g. as in Google App Engine) - drop back and log via console instea
			log.error("Unable to output to file - logging to console instead");
			outputToConsole(wb);
		}

	}

	/**
	 * Outputs an Apache POI Workbook to a Stream (e.g Servlet response)
	 * @param wb
	 * @param stream
	 * @throws IOException
	 */
	public static void outputToStream(Workbook wb,OutputStream stream) throws IOException{

		wb.write(stream);
	}

	/**
	 * Outputs an Apache POI Workbook to a Logging Console
	 * @param wb
	 * @throws IOException
	 */
	public static void outputToConsole(Workbook wb) throws IOException{

		RangeHolder ranges = RangeConvertor.convertPoiWorkbookIntoRedRange(wb);
		outputToConsole(ranges);

	}

	/**
	 * Outputs Red-Piranha's own internal format to a Logging Console
	 * @param ranges
	 */
	public static void outputToConsole(RangeHolder ranges ){
		for (Range r : ranges) {
			log.info(r);
		}
	}

}
