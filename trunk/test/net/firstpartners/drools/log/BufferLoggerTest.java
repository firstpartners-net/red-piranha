package net.firstpartners.drools.log;

import org.junit.Test;

public class BufferLoggerTest {

	@Test
	public final void testLog() {
		//Make these these log without exception
		BufferLogger bufferLogger = new BufferLogger();
		bufferLogger.debug("testDebug");
		bufferLogger.info("testInfo");
		bufferLogger.exception("testException", new Exception("Sample"));
	}

	@Test
	public final void testNestedLog() {
		//Make these these log without exception
		ConsoleLogger consoleLogger = new ConsoleLogger();
		BufferLogger bufferLogger = new BufferLogger(consoleLogger);


		bufferLogger.debug("testDebug");
		bufferLogger.info("testInfo");
		bufferLogger.exception("testException", new Exception("Sample"));
	}

}

