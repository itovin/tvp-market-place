package com.tierraverdemp.backend.refreshtoken;

import com.tierraverdemp.backend.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "revoked")
    private boolean isRevoked;

    @Column(name = "expires_at")
    private Instant expiresAt;

    public RefreshToken(User user, boolean isRevoked, Instant expiresAt){
        this.user = user;
        this.isRevoked = false;
        this.expiresAt = expiresAt;
    }



}
