package cameltutorial;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InvokeWithProcessorRouteTest extends CamelTestSupport {

    @Test
    public void testHello() {
        String reply = template.requestBody("direct:hello", "Camel in action", String.class);
        Assertions.assertEquals("Hello Camel in action", reply);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new InvokeWithProcessorRouteBuilder();
    }
}