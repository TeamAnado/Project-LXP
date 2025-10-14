DROP TABLE IF EXISTS `attachments`;

CREATE TABLE `attachments`
(
    `id`         BIGINT NOT NULL AUTO_INCREMENT,
    `lecture_id` BIGINT NOT NULL,
    `file_type`  ENUM('VIDEO', 'PDF', 'IMAGE') NOT NULL,
    `path`       VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
);
