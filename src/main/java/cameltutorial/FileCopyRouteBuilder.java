package cameltutorial;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

/**
 * Copies files from a given folder to another folder
 */
public class FileCopyRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:{{inputFolder}}?noop=true")
                .process(new FileInfoLogger())
                // Content based routing
                // Depending on the file suffix the file is copied to a different folder
                .choice()
                .when(header("CamelFileName").endsWith(".xml"))
                .log(LoggingLevel.INFO, "Received file ${header.CamelFileName} is of type xml")
                .to("file:{{outputFolderXml}}")
                .when(header("CamelFileName").endsWith(".csv"))
                .to("file:{{outputFolderCsv}}")
                .otherwise()
                .to("file:{{outputFolderMisc}}")
                .end();
    }
}
