package ferramentas.loggin.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ErrorLogger {

    private static final Logger log = LoggerFactory.getLogger("ERROR");

    public void logError(String code, String message, Exception ex) {
        log.error("Application error code={} message={}", code, message, ex);
    }

}