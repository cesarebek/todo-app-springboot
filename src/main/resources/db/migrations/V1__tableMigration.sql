CREATE TABLE `user`
(
    `id`       int          NOT NULL PRIMARY KEY,
    `email`    varchar(255) NOT NULL UNIQUE,
    `password` varchar(255) NOT NULL,
    `username` varchar(255) NOT NULL UNIQUE,

) ENGINE=INNODB;

CREATE TABLE `todo`
(
    `id`           int          NOT NULL,
    `description`  varchar(255) DEFAULT NULL,
    `is_completed` bit(1)       DEFAULT "Description is empty...",
    `title`        varchar(255) NOT NULL,
    `user_id`      int          NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id)
        REFERENCES `user`
) ENGINE=INNODB;

CREATE TABLE `role`
(
    `id`          int          NOT NULL,
    `name`        varchar(255) NOT NULL,
    `description` varchar(255) DEFAULT "No Description...",
    PRIMARY KEY (id)
) ENGINE=INNODB;

CREATE TABLE `user_roles`
(
    `user_id` INT UNSIGNED NOT NULL,
    `role_id` SMALLINT UNSIGNED NOT NULL,
    PRIMARY KEY (`Student`, `Course`),
    CONSTRAINT `user_fk`
        FOREIGN KEY `user_id` REFERENCES `user` (`id`)
            ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `Constr_CourseMembership_Course_fk`
        FOREIGN KEY `role_id` REFERENCES `role` (`id`)
            ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=INNODB;