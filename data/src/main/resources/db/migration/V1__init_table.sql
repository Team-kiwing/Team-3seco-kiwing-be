CREATE TABLE IF NOT EXISTS member
(
    id                BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nickname          VARCHAR(50) NULL,
    email             VARCHAR(50) NOT NULL,
    provider          VARCHAR(10) NOT NULL,
    profile_image     TEXT        NULL,
    bundle_order      TEXT        NOT NULL,
    role              VARCHAR(50) NULL,
    last_logged_in_at DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE INDEX member_unique_idx_nickname_and_deleted_at (nickname, deleted_at),
    UNIQUE INDEX member_unique_idx_email_and_deleted_at (email, deleted_at)
);

CREATE TABLE IF NOT EXISTS sns
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(20)     NOT NULL,
    url        TEXT            NOT NULL,
    member_id  BIGINT UNSIGNED NULL,
    created_at DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bundle
(
    id             BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name           VARCHAR(100)    NOT NULL,
    share_type     VARCHAR(10)     NOT NULL,
    question_order TEXT            NOT NULL,
    scrape_count   BIGINT UNSIGNED NOT NULL DEFAULT 0,
    view_count     BIGINT UNSIGNED NOT NULL DEFAULT 0,
    popularity     DOUBLE UNSIGNED NOT NULL DEFAULT 0,
    origin_id      BIGINT UNSIGNED NULL,
    member_id      BIGINT UNSIGNED NOT NULL,
    created_at     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX bundle_idx_name (name),
    FULLTEXT INDEX bundle_fulltext_idx_name (name) WITH PARSER ngram
);

CREATE TABLE IF NOT EXISTS question
(
    id                BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    content           VARCHAR(300)    NOT NULL,
    answer            VARCHAR(1500)   NULL,
    answer_share_type VARCHAR(10)     NOT NULL,
    share_count       BIGINT UNSIGNED NOT NULL DEFAULT 0,
    expose_count      BIGINT UNSIGNED NOT NULL DEFAULT 0,
    popularity        DOUBLE UNSIGNED NOT NULL DEFAULT 0,
    is_searchable     BOOLEAN         NOT NULL,
    origin_id         BIGINT UNSIGNED NULL,
    bundle_id         BIGINT UNSIGNED NOT NULL,
    member_id         BIGINT UNSIGNED NOT NULL,
    created_at        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at        DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX question_idx_content (content),
    FULLTEXT INDEX question_fulltext_idx_content (content) WITH PARSER ngram
);


CREATE TABLE IF NOT EXISTS tag
(
    id   BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    UNIQUE INDEX tag_unique_idx_name (name),
    FULLTEXT INDEX tag_fulltext_idx_name (name)
);

CREATE TABLE IF NOT EXISTS member_tag
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT UNSIGNED NOT NULL,
    tag_id     BIGINT UNSIGNED NOT NULL,
    created_at DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS bundle_tag
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    bundle_id  BIGINT UNSIGNED NOT NULL,
    tag_id     BIGINT UNSIGNED NOT NULL,
    created_at DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS question_tag
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    question_id BIGINT UNSIGNED NOT NULL,
    tag_id      BIGINT UNSIGNED NOT NULL,
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS question_report
(
    id          BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    reason      VARCHAR(100)    NOT NULL,
    question_id BIGINT UNSIGNED NOT NULL,
    created_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE IF NOT EXISTS claim
(
    id         BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    content    TEXT     NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
