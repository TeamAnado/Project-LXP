ALTER TABLE `courses`
    ADD CONSTRAINT `fk_courses_to_users` FOREIGN KEY (`instructor_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

ALTER TABLE `sections`
    ADD CONSTRAINT `fk_sections_to_courses` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

ALTER TABLE `lectures`
    ADD CONSTRAINT `fk_lectures_to_sections` FOREIGN KEY (`section_id`) REFERENCES `sections` (`id`) ON DELETE CASCADE;

ALTER TABLE `attachments`
    ADD CONSTRAINT `fk_attachments_to_lectures` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`id`) ON DELETE CASCADE;

ALTER TABLE `enrollments`
    ADD CONSTRAINT `fk_enrollments_to_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `fk_enrollments_to_courses` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE;

ALTER TABLE `reviews`
    ADD CONSTRAINT `fk_reviews_to_enrollments` FOREIGN KEY (`enrollment_id`) REFERENCES `enrollments` (`id`) ON DELETE CASCADE;

ALTER TABLE `questions`
    ADD CONSTRAINT `fk_questions_to_lectures` FOREIGN KEY (`lecture_id`) REFERENCES `lectures` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `fk_questions_to_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

SET FOREIGN_KEY_CHECKS = 1;
