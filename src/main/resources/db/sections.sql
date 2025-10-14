DROP TABLE IF EXISTS `sections`;

CREATE TABLE `sections`
(
    `id`        BIGINT NOT NULL AUTO_INCREMENT,
    `course_id` BIGINT NOT NULL,
    `title`     VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);
