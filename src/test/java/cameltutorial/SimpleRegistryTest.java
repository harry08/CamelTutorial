package cameltutorial;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.Registry;
import org.apache.camel.support.DefaultRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Using {@link org.apache.camel.support.SimpleRegistry} as the Camel {@link org.apache.camel.spi.Registry}
 * to register beans and let Camel lookup them to be used in routes.
 */
public class SimpleRegistryTest {
    private CamelContext camelContext;
    private ProducerTemplate template;

    /**
     * Starts Camel with the route "diret:hello"
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Create the registry to be the DefaultRegistry which is by default using the SimpleRegistry
        Registry registry = new DefaultRegistry();
        // Register the HelloBean class under the name helloBean
        registry.bind("helloBean", new HelloBean());

        // Tell Camel to use the registry
        camelContext = new DefaultCamelContext(registry);

        // Create a producer template to use for testing
        template = camelContext.createProducerTemplate();

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:hello")
                        .bean("helloBean", "hello");
            }
        });

        // Start Camel
        camelContext.start();
    }

    /**
     * Stops Camel after test
     */
    @AfterEach
    public void tearDown() throws Exception {
        template.stop();
        camelContext.stop();
    }

    /**
     * Tests the route
     * by sending in World and expect the reply to be Hello World
     */
    @Test
    public void testHello() throws Exception {
        Object reply = template.requestBody("direct:hello", "World");
        Assertions.assertEquals("Hello World", reply);
    }
}

