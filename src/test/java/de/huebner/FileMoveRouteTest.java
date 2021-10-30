package de.huebner;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * UnitTest for a simple route which moves files from one directory to another.
 */
public class FileMoveRouteTest extends CamelTestSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMoveRouteTest.class);

    public void setUp() throws Exception {
        LOGGER.info("delete inbox and outbox directories");
        deleteDirectory("target/inbox");
        deleteDirectory("target/outbox");
        super.setUp();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new FileMoveRouteBuilder();
    }

    @Test
    public void testMoveFile() throws Exception {
        // Create file hello.txt in inbox folder
        template.sendBodyAndHeader("file://target/inbox", "Hello World", Exchange.FILE_NAME, "hello.txt");

        // Let to Camel its work
        Thread.sleep(1000);

        // Verify that file is copied.
        File expectedFile = new File("target/outbox/hello.txt");
        assertTrue("File not moved", expectedFile.exists());
        String expectedContent = context.getTypeConverter().convertTo(String.class, expectedFile);
        assertEquals("Hello World", expectedContent);
    }
}
