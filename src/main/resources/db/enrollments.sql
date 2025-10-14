DROP TABLE IF EXISTS `enrollments`;

CREATE TABLE `enrollments`
(
    `id`           BIGINT NOT NULL AUTO_INCREMENT,
    `user_id`      BIGINT NOT NULL,
    `course_id`    BIGINT NOT NULL,
    `state`        BOOLEAN NOT NULL,
    `date_created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `date_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);
