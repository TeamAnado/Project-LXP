DROP TABLE IF EXISTS `lectures`;

CREATE TABLE `lectures`
(
    `id`            BIGINT       NOT NULL AUTO_INCREMENT,
    `course_id`     BIGINT       NOT NULL,
    `title`         VARCHAR(255) NOT NULL,
    `description`   TEXT         NOT NULL,
    `path`          VARCHAR(255) NOT NULL,
    `date_created`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `date_modified` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);
