package cameltutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloBean {

    private static Logger LOGGER = LoggerFactory.getLogger(HelloBean.class);

    public String hello(String name) {
        LOGGER.info("HelloBean called with name " + name);
        return "Hello " + name;
    }
}
