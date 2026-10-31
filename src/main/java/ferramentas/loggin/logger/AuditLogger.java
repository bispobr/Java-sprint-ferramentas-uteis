package ferramentas.loggin.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AuditLogger {

    private static final Logger log = LoggerFactory.getLogger("AUDIT");

    public void logLogin(Long userId, String email,String roles) {
        log.info("User login userId={} email={} roles={}", userId, email, roles);
    }
}