package cameltutorial;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.junit5.TestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * UnitTest for a simple route which moves files from one directory to another.
 */
public class FileMoveRouteTest extends CamelTestSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMoveRouteTest.class);

    @BeforeEach
    public void cleanDirectories() {
        LOGGER.info("delete inbox and outbox directories");
        TestSupport.deleteDirectory("target/inbox");
        TestSupport.deleteDirectory("target/outbox");
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
        Assertions.assertTrue(expectedFile.exists());
        String expectedContent = context.getTypeConverter().convertTo(String.class, expectedFile);
        Assertions.assertEquals("Hello World", expectedContent);
    }
}
