DROP TABLE IF EXISTS `questions`;

CREATE TABLE `questions`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `lecture_id` BIGINT NOT NULL,
    `user_id`    BIGINT NOT NULL,
    `root_id`    BIGINT NOT NULL,
    `parent_id`  BIGINT NULL,
    `title`      VARCHAR(255) NOT NULL,
    `body`       TEXT NOT NULL,
    `date_created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `date_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);
