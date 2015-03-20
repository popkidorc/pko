package logback.demo.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {
	static Logger logger = LoggerFactory.getLogger(LogbackTest.class);

	@Test
	public void LogbackPrint() {
		for (int i = 0; i < 100; i++) {
			logger.trace("trace message " + i);
			logger.debug("debug message " + i);
			logger.info("info message " + i);
			logger.warn("warn message " + i);
			logger.error("error message " + i);
		}
		System.out.println("Hello World! 2");
	}
}
