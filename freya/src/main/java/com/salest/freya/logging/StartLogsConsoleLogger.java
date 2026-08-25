package com.salest.freya.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartLogsConsoleLogger {

	private static final String ANSI_GREEN = "\u001B[32m";
	private static final String ANSI_RESET = "\u001B[0m";
    private static final Logger log = LoggerFactory.getLogger(StartLogsConsoleLogger.class);

    @Value("${server.port:8080}")
    private String puerto;

    @EventListener(ApplicationReadyEvent.class)
    public void onAppReady() {
        log.info("<=========================================================>");
        log.info(ANSI_GREEN + "SWAGGER DOCS:" + ANSI_RESET);
        log.info("http://localhost:{}/swagger-ui/index.html", puerto);
        log.info(ANSI_GREEN + "GITHUB REPO (INFO):" + ANSI_RESET);
        log.info("https://github.com/phalanxeyes/freya-java");
        log.info("<=========================================================>");
    }
}
