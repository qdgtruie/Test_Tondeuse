package com.publicissapient.tondeuse;

import com.publicissapient.tondeuse.domain.PositionProvider;
import com.publicissapient.tondeuse.domain.configuration.Configuration;
import com.publicissapient.tondeuse.domain.configuration.errors.ConfigurationException;
import com.publicissapient.tondeuse.domain.configuration.errors.InvalidMoveEventArg;
import com.publicissapient.tondeuse.domain.configuration.providers.FileConfigurationProvider;
import com.publicissapient.tondeuse.service.MownerController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/")
public class MownerWebController {

    /**
     * Conf file reference
     */
    private static final String FILE_CONF = "file.txt";

    /**
     * Buffer for all notifications
     */
    private static final StringBuilder buffer = new StringBuilder();

    /**
     * Run the MownerController
     * @return a string representing the result.
     * @throws ConfigurationException when config is not right
     */
    @GetMapping
    public String run() throws ConfigurationException {
        buffer.setLength(0);

        log.info("[ConsoleApp] Starting mowner controller...");
        new MownerController()
                .load(Configuration.basedOn(FileConfigurationProvider.fromFileResource(FILE_CONF)))
                .withAlerter(MownerWebController::notifyInvalidMove)
                .withResultPublisher(MownerWebController::printMownerFinalLocation)
                .run();

        return buffer.toString();
    }

    /**
     * Handle invalid move notification (log, display)
     * @param x move attempted.
     */
    private static void notifyInvalidMove(InvalidMoveEventArg x) {
        if (log.isInfoEnabled())
            log.info("[Mowner {} ] tried to reach invalid position at {}",
                    x.getMownerID().toString(), x.getTargetPosition().toString());

        buffer.append("[Mowner ");
        buffer.append(x.getMownerID().toString());
        buffer.append("] tried to reach invalid position at : ");
        buffer.append(x.getTargetPosition().toString());
        buffer.append("<p/>");
    }

    /**
     * handle finakl move notification (log, display)
     * @param x final position
     */
    private static void printMownerFinalLocation(PositionProvider x) {
        if (log.isInfoEnabled())
            log.info("[Mowner {} ] Job complete : position is {}",
                    x.getId().toString(), x.getCurrentLocation().toString());

        buffer.append(x.getCurrentLocation().getPosition().getX());
        buffer.append(" ");
        buffer.append(x.getCurrentLocation().getPosition().getY());
        buffer.append(" ");
        buffer.append(x.getCurrentLocation().getOrientation().toString());
        buffer.append("<p/>");
    }

}
