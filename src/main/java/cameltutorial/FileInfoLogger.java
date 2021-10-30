package cameltutorial;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileInfoLogger implements Processor {
    private static Logger LOGGER = LoggerFactory.getLogger(FileInfoLogger.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        LOGGER.info("Processing file: " + exchange.getIn().getHeader("CamelFileName"));
    }
}
