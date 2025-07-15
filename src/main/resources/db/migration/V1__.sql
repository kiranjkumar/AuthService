CREATE TABLE `role`
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NULL,
    updated_at datetime              NULL,
    deleted    BIT(1)                NULL,
    value      VARCHAR(255)          NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE session
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    created_at      datetime              NULL,
    updated_at      datetime              NULL,
    deleted         BIT(1)                NULL,
    token           VARCHAR(255)          NULL,
    expiration_date datetime              NULL,
    user_id         BIGINT                NULL,
    status          VARCHAR(255)          NULL,
    CONSTRAINT pk_session PRIMARY KEY (id)
);

CREATE TABLE user
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    created_at datetime              NULL,
    updated_at datetime              NULL,
    deleted    BIT(1)                NULL,
    email      VARCHAR(255)          NULL,
    password   VARCHAR(255)          NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT NOT NULL
);

ALTER TABLE session
    ADD CONSTRAINT FK_SESSION_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES `role` (id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id);