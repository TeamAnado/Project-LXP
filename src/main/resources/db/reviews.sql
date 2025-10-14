DROP TABLE IF EXISTS `reviews`;

CREATE TABLE `reviews`
(
    `id`            BIGINT NOT NULL AUTO_INCREMENT,
    `enrollment_id` BIGINT NOT NULL,
    `comment`       TEXT NOT NULL,
    `rating`        INT CHECK ( rating BETWEEN 1 AND 5) NOT NULL,
    `date_created` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `date_modified` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
);
