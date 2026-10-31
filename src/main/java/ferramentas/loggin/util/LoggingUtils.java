package ferramentas.loggin.util;

import org.slf4j.MDC;
import java.util.UUID;

public class LoggingUtils {

    public static String generateCorrelationId() {

        String correlationId = UUID.randomUUID().toString();
        MDC.put("correlationId", correlationId);
        return correlationId;
    }

    public static void clear() {

        MDC.clear();
    }
}

