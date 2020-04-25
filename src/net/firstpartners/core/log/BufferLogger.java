package net.firstpartners.core.log;

/**
 * An Adaptor that allows us and hold Loggin in Memory Useful for Testing
 * 
 * @author paulbrowne
 *
 */
public class BufferLogger implements ILogger {

	ILogger nestedLogger = null;

	// We also allow this to be configured to log to memory
	final StringBuffer buffer = new StringBuffer();

	public BufferLogger() {

	}

	public BufferLogger(ILogger nestedLogger) {
		this.nestedLogger = nestedLogger;
	}

	public void debug(String output) {
		buffer.append("DEBUG:" + output + "\n");

		if (nestedLogger != null) {
			nestedLogger.debug(output);
		}
	}

	public void info(String output) {
		buffer.append("INFO:" + output + "\n");

		if (nestedLogger != null) {
			nestedLogger.info(output);
		}
	}

	public void exception(String output, Throwable t) {
		buffer.append("EXCEPTION:" + output + "\n");
		buffer.append(t.fillInStackTrace());

		if (nestedLogger != null) {
			nestedLogger.exception(output, t);
		}
	}

	public String getContents() {
		return buffer.toString();
	}

	public ILogger getNestedLogger() {
		return nestedLogger;
	}

	public void setNestedLogger(ILogger nestedLogger) {
		this.nestedLogger = nestedLogger;
	}

}
