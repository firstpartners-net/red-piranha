package net.firstpartners.core.log;

import org.junit.Test;

public class BufferLoggerTest {

	@Test
	public final void testLog() {
		//Make these these log without exception
		BufferStatusUpdate bufferLogger = new BufferStatusUpdate();
		bufferLogger.debug("testDebug");
		bufferLogger.info("testInfo");
		bufferLogger.exception("testException", new Exception("Sample"));
	}

	@Test
	public final void testNestedLog() {
		//Make these these log without exception
		ConsoleStatusUpdate consoleLogger = new ConsoleStatusUpdate();
		BufferStatusUpdate bufferLogger = new BufferStatusUpdate(consoleLogger);


		bufferLogger.debug("testDebug");
		bufferLogger.info("testInfo");
		bufferLogger.exception("testException", new Exception("Sample"));
	}

}

