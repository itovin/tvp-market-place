CREATE TABLE refresh_tokens(
    id BINARY(16) PRIMARY KEY,
    user_id BIGINT NOT NULL,
    revoked TINYINT(1) DEFAULT 0 NOT NULL,
    expires_at DATETIME NOT NULL,

    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);