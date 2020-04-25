package net.firstpartners.core.spreadsheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Workbook;

import net.firstpartners.core.log.RpLogger;
import net.firstpartners.data.Range;
import net.firstpartners.data.RangeHolder;

/**
 * Convenience Class for Output of Spreadhsheets.
 * @author paul
 *
 */
public class SpreadSheetOutputter {

	// Logger
	private static final Logger log = RpLogger.getLogger(SpreadSheetOutputter.class.getName());

	/**
	 * Delete the output file if it already exists
	 *
	 * @param outputFile
	 */
	public static void deleteOutputFileIfExists(String outputFileName) {

		File outputFile = new File(outputFileName);
		if (outputFile.exists()) {
			outputFile.delete();
		}

	}

	/**
	 * Outputs Red-Piranha's own internal format to a Logging Console
	 * @param ranges
	 */
	public static void outputToConsole(RangeHolder ranges ){
		for (Range r : ranges) {
			log.info(r.toString());
		}
	}

	/**
	 * Outputs an Apache POI Workbook to a Logging Console
	 * @param wb
	 * @throws IOException
	 */
	public static void outputToConsole(Workbook wb) throws IOException{

		RangeHolder ranges = RangeConvertor.convertNamesFromPoiWorkbookIntoRedRange(wb);
		outputToConsole(ranges);

	}

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

}
