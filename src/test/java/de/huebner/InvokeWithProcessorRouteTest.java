package de.huebner;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Assert;
import org.junit.Test;

public class InvokeWithProcessorRouteTest extends CamelTestSupport {

    @Test
    public void testHello() {
        String reply = template.requestBody("direct:hello", "Camel in action", String.class);
        Assert.assertEquals("Hello Camel in action", reply);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new InvokeWithProcessorRouteBuilder();
    }
}