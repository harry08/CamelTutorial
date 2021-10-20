package de.huebner;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Copies files from a given folder to another folder
 */
public class FileCopierApplication {

    public static void main (String[] args) {
        CamelContext ctx = new DefaultCamelContext();
        PropertiesComponent prop = ctx.getComponent("properties", PropertiesComponent.class);
        prop.setLocation("classpath:filecopy.properties");

        try {
            ctx.addRoutes(new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("file:{{inputFolder}}?noop=true")
                            .process(new FileInfoLogger())
                            // Content based routing
                            // Depending on the file suffix the file is copied to a different folder
                            .choice()
                            .when(header("CamelFileName").endsWith(".xml"))
                            .log(LoggingLevel.INFO, "Received file: ${header.CamelFileName} is of type xml")
                            .to("file:{{outputFolderXml}}")
                            .when(header("CamelFileName").endsWith(".csv"))
                            .to("file:{{outputFolderCsv}}")
                            .otherwise()
                            .to("file:{{outputFolderMisc}}")
                            .end();
                }
            });

            // Start the route and let it do its work
            ctx.start();
            Thread.sleep(5000);
            
            ctx.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
