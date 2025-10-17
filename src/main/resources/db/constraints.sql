ALTER TABLE `courses`
    ADD CONSTRAINT `fk_courses_to_users` FOREIGN KEY (`instructor_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `lectures`
    ADD CONSTRAINT `fk_lectures_to_courses` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

ALTER TABLE `enrollments`
    ADD CONSTRAINT `fk_enrollments_to_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `fk_enrollments_to_courses` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

SET FOREIGN_KEY_CHECKS = 1;
