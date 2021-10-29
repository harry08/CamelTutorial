package de.huebner;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

/**
 * Uses a Processor in the route to invoke HelloBean.
 */
public class InvokeWithProcessorRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:hello")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        // Extract the name parameter from the Camel message which we want to use
                        // when invoking the bean
                        String name = exchange.getIn().getBody(String.class);

                        HelloBean helloBean = new HelloBean();
                        String result = helloBean.hello(name);

                        exchange.getOut().setBody(result);
                    }
                });
    }
}
