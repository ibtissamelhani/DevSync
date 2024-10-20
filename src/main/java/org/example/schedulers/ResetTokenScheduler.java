package org.example.schedulers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.entities.Token;
import org.example.model.entities.User;
import org.example.model.enums.TokenType;
import org.example.repository.implementation.TokenRepositoryImpl;
import org.example.repository.implementation.UserRepositoryImpl;
import org.example.repository.interfaces.UserRepository;
import org.example.service.TokenService;
import org.example.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.TimerTask;
import java.util.logging.Logger;

public class ResetTokenScheduler extends TimerTask {

    private UserService userService ;
    private TokenService tokenService ;

    public ResetTokenScheduler() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("DevSyncPU");
        this.tokenService = new TokenService(new TokenRepositoryImpl(entityManagerFactory));
        UserRepository userRepository = new UserRepositoryImpl(entityManagerFactory);
        this.userService = new UserService(userRepository, tokenService);
    }

    private static final Logger logger = Logger.getLogger(ResetTokenScheduler.class.getName());

    @Override
    public void run() {
        List<User> users = userService.getAllUsers();

        for (User user : users) {
            Optional<Token> modificationTokenOptional = tokenService.findModificationTokenByUserId(user.getId());
            if (modificationTokenOptional.isPresent()) {
                Token modificationToken = modificationTokenOptional.get();
                modificationToken.setTokenCount(2);
                tokenService.updateToken(modificationToken);
            } else {
                Token newModificationToken = new Token(TokenType.MODIFICATION, LocalDate.now().plusMonths(1), user, 2);
                tokenService.save(newModificationToken);
            }

            Optional<Token> suppressionTokenOptional = tokenService.findSuppressionTokenByUserId(user.getId());
            if (suppressionTokenOptional.isPresent()) {
                Token suppressionToken = suppressionTokenOptional.get();
                suppressionToken.setTokenCount(1);
                tokenService.updateToken(suppressionToken);
            } else {
                Token newSuppressionToken = new Token(TokenType.SUPPRESSION, LocalDate.now().plusMonths(1), user, 1);
                tokenService.save(newSuppressionToken);
            }

            System.out.println("Tokens reset for user: " + user.getId());
        }

    }
}
