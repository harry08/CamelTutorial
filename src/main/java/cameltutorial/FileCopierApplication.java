package cameltutorial;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.PropertiesComponent;

/**
 * Copies files from a given folder to another folder.
 */
public class FileCopierApplication {

    public static void main (String[] args) {
        CamelContext ctx = new DefaultCamelContext();
        PropertiesComponent prop = ctx.getPropertiesComponent();
        prop.setLocation("classpath:filecopy.properties");

        try {
            ctx.addRoutes(new FileCopyRouteBuilder());

            // Start the route and let it do its work
            ctx.start();
            Thread.sleep(5000);
            
            ctx.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
