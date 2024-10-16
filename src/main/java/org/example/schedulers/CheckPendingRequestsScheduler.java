package org.example.schedulers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.entities.Request;
import org.example.model.entities.Token;
import org.example.model.entities.User;
import org.example.model.enums.ActionType;
import org.example.model.enums.RequestStatus;
import org.example.model.enums.TokenType;
import org.example.repository.implementation.*;
import org.example.service.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.TimerTask;
import java.util.logging.Logger;

public class CheckPendingRequestsScheduler extends TimerTask {
    private final RequestService requestService;
    private final TokenService tokenService;

    public CheckPendingRequestsScheduler() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
        this.tokenService = new TokenService(new TokenRepositoryImpl(entityManagerFactory));
        UserService userService = new UserService(new UserRepositoryImpl(entityManagerFactory),tokenService);
        TagService tagService = new TagService(new TagRepositoryImpl(entityManagerFactory));
        TaskService taskService = new TaskService(new TaskRepositoryImpl(entityManagerFactory),tagService,userService);
        this.requestService = new RequestService(new RequestRepositoryImpl(entityManagerFactory),tokenService,taskService);

    }

    private static final Logger logger = Logger.getLogger(CheckPendingRequestsScheduler.class.getName());

    @Override
    public void run() {
        logger.info("Checking pending task change requests...");

        List<Request> pendingRequests = requestService.findRequestsByStatus(RequestStatus.PENDING);
        LocalDateTime now = LocalDateTime.now();

        for (Request request : pendingRequests) {

            LocalDateTime requestCreationDateTime = request.getRequestDate().atStartOfDay();
            long hoursElapsed = ChronoUnit.HOURS.between(requestCreationDateTime, now);

            if (hoursElapsed >= 12) {
                User user = request.getUser();

                if (request.getType() == ActionType.SWAP) {
                    Optional<Token> modificationTokenOptional = tokenService.findModificationTokenByUserId(user.getId());
                    if (modificationTokenOptional.isPresent()) {
                        Token modificationToken = modificationTokenOptional.get();
                        modificationToken.setTokenCount(modificationToken.getTokenCount() * 2);
                        tokenService.updateToken(modificationToken);
                    } else {
                        Token newModificationToken = new Token(TokenType.MODIFICATION, LocalDate.now().plusDays(1), user, 4);
                        tokenService.save(newModificationToken);
                    }
                    logger.info("Doubled modification tokens for user: " + user.getFirstName() + " due to SWAP action.");

                } else if (request.getType() == ActionType.DELETE) {
                    Optional<Token> suppressionTokenOptional = tokenService.findSuppressionTokenByUserId(user.getId());
                    if (suppressionTokenOptional.isPresent()) {
                        Token suppressionToken = suppressionTokenOptional.get();
                        suppressionToken.setTokenCount(suppressionToken.getTokenCount() * 2);
                        tokenService.updateToken(suppressionToken);
                    } else {
                        Token newSuppressionToken = new Token(TokenType.SUPPRESSION, LocalDate.now().plusMonths(1), user, 2);
                        tokenService.save(newSuppressionToken);
                    }
                    logger.info("Doubled suppression tokens for user: " + user.getFirstName() + " due to DELETE action.");
                }
            }
        }

        logger.info("Finished checking pending requests.");
    }
}
