package ferramentas.loggin.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BusinessEventLogger {

    private static final Logger log = LoggerFactory.getLogger("BUSINESS");

    public void logEvent(String event, String entity, Long entityId, String status) {
        log.info("Business event event={} entity={} entityId={} status={}",
                event, entity, entityId, status);

    }
}


