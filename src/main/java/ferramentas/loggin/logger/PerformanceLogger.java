package ferramentas.loggin.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PerformanceLogger {
    private static final Logger log = LoggerFactory.getLogger("PERFORMANCE");

    public void logExecution(String operation, long duration) {

        log.info("Performance operation={} durationMs={}", operation, duration);
    }
}


