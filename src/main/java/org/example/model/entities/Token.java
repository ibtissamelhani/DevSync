package org.example.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.enums.TokenType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean used;

    @Column(name = "token_count")
    private int tokenCount;

    public Token(TokenType type, LocalDate expirationDate, User user,int tokenCount) {
        this.type = type;
        this.expirationDate = expirationDate;
        this.user = user;
        this.used = false;
        this.tokenCount = tokenCount;
    }

    public void useToken() {
        if (!used && LocalDate.now().isBefore(expirationDate)) {
            this.used = true;
        } else {
            throw new IllegalStateException("Token is already used");
        }
    }

}
