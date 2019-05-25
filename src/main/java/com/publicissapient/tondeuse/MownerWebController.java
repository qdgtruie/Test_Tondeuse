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

    private static final String FILE_CONF = "file.txt";
    private static final StringBuilder buffer = new StringBuilder();

    @GetMapping
    public String run() throws ConfigurationException {
        buffer.setLength(0);

        log.info("[ConsoleApp] Starting mowner controller...");
        new MownerController()
                .load(Configuration.basedOn(FileConfigurationProvider.fromRessource(FILE_CONF)))
                .withAlerter(MownerWebController::notifyInvalidMove)
                .withResultPublisher(MownerWebController::printMownerFinalLocation)
                .run();

        return buffer.toString();
    }

    private static void notifyInvalidMove(InvalidMoveEventArg x) {
        buffer.append("[Mowner ");
        buffer.append(x.getMownerID().toString());
        buffer.append("] tried to reach invalid position at : ");
        buffer.append( x.getTargetPosition().toString());
        buffer.append("<p/>");
    }

    private static void printMownerFinalLocation(PositionProvider x) {
        buffer.append("[Mowner ");
        buffer.append( x.getId().toString());
        buffer.append( "] Job complete : position is ");
        buffer.append( x.getCurrentLocation().toString());
        buffer.append("<p/>");
    }

}
