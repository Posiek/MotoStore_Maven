package report;

import org.apache.log4j.Logger;



public class SampleReport {

	static Logger logger = Logger.getLogger(SampleReport.class);
	
	public void generateReport()
	{
		logger.debug("Sample debug message");
		logger.info("Sample info message");
		logger.warn("Sample warn message");
		logger.error("Sample error message");
		logger.fatal("Sample fatal message");
	}
}
