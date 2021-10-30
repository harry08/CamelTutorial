package cameltutorial;

import org.apache.camel.builder.RouteBuilder;

/**
 * Moves files from an input folder to an output folder.
 */
public class FileMoveRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file://target/inbox")
                .to("file://target/outbox");
    }
}

