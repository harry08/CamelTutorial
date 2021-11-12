package cameltutorial;

import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.apache.camel.test.junit5.TestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UnitTest for FileMoveRouteBuilder.
 * Intercepts the from part of the route to send the file to a MockEndpoint
 */
public class FileMoveRouteMockTest extends CamelTestSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMoveRouteTest.class);

    @BeforeEach
    public void mockEndpoints() throws Exception {
        LOGGER.info("delete inbox and outbox directories");
        TestSupport.deleteDirectory("target/inbox");
        
        AdviceWithRouteBuilder mockFileCopy = new AdviceWithRouteBuilder() {

            @Override
            public void configure() throws Exception {
                interceptSendToEndpoint("file://target/outbox")
                        .skipSendToOriginalEndpoint()
                        .to("mock:catchFileMessage");
            }
        };

        RouteDefinition routeDefinition = context.getRouteDefinitions().get(0);
        context.adviceWith(routeDefinition, mockFileCopy);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new FileMoveRouteBuilder();
    }

    @Test
    public void testMoveFile() throws Exception {
        MockEndpoint mockFile = getMockEndpoint("mock:catchFileMessage");

        context.start();
        mockFile.expectedMessageCount(1);
        mockFile.expectedHeaderReceived(Exchange.FILE_NAME, "hello.txt");
        // Create file hello.txt in inbox folder
        template.sendBodyAndHeader("file://target/inbox", "Hello World", Exchange.FILE_NAME, "hello.txt");

        // Verify mock
        mockFile.assertIsSatisfied();
        context.stop();
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }
}
