package com.lxp.course.service;

import com.lxp.course.dao.CourseDao;
import com.lxp.course.exception.CourseNotFoundException;
import com.lxp.course.exception.InvalidCourseIdException;
import com.lxp.course.model.Course;
import com.lxp.course.model.enums.Category;
import com.lxp.course.service.dto.CourseDetailDto;
import com.lxp.course.service.dto.CourseListDto;
import com.lxp.course.service.dto.CreateCourseDto;
import com.lxp.course.service.dto.UpdateCourseDto;
import com.lxp.global.exception.LXPException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {

    private final CourseDao courseDao;

    public CourseService() {
        courseDao = new CourseDao();
    }
    
    // For testing - allows dependency injection
    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    public Long createCourse(CreateCourseDto dto) {
        // Input validation
        if (dto.title() == null || dto.title().trim().isEmpty()) {
            throw new IllegalArgumentException("강의 제목은 필수 입력값입니다.");
        }
        if (dto.category() == null) {
            throw new IllegalArgumentException("강의 카테고리는 필수 입력값입니다.");
        }
        if (dto.instructorId() == null || dto.instructorId() <= 0) {
            throw new IllegalArgumentException("유효한 강사 ID가 필요합니다.");
        }
        
        LocalDateTime now = LocalDateTime.now();
        Course course = new Course(
                null,
                dto.title(),
                dto.description(),
                dto.instructorId(),
                dto.category(),
                now,
                now
        );
        return courseDao.save(course);
    }

    public List<CourseListDto> findAllCourses() {
        List<Course> courses = courseDao.findAll();
        return courses.stream()
                .map(course -> new CourseListDto(
                        course.getId(),
                        course.getTitle(),
                        course.getCategory(),
                        course.getInstructorId()
                ))
                .collect(Collectors.toList());
    }

    public CourseDetailDto findCourseById(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidCourseIdException();
        }
        
        Course course = courseDao.findById(id);
        if (course == null) {
            throw new CourseNotFoundException("해당 ID의 강의를 찾을 수 없습니다: " + id);
        }
        return new CourseDetailDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getCategory(),
                course.getInstructorId(),
                course.getDateCreated()
        );
    }

    public List<CourseListDto> findCoursesByTitle(String title) {
        String keyword = title == null ? "" : title.trim();
        if (keyword.isEmpty()) {
            return List.of();
        }
        List<Course> courses = courseDao.findByTitleContaining(keyword);

        return courses.stream()
                .map(course -> new CourseListDto(
                        course.getId(),
                        course.getTitle(),
                        course.getCategory(),
                        course.getInstructorId()
                ))
                .collect(Collectors.toList());
    }

    public List<CourseListDto> findCoursesByCategory(Category category) {
        if (category == null) {
            return List.of();
        }
        List<Course> courses = courseDao.findByCategory(category);
        return courses.stream()
                .map(course -> new CourseListDto(
                        course.getId(),
                        course.getTitle(),
                        course.getCategory(),
                        course.getInstructorId()
                ))
                .collect(Collectors.toList());
    }

    public List<CourseListDto> findCoursesByInstructorId(Long instructorId) {
        if (instructorId == null || instructorId <= 0) {
            return List.of();
        }
        List<Course> courses = courseDao.findByInstructorId(instructorId);

        return courses.stream()
                .map(course -> new CourseListDto(
                        course.getId(),
                        course.getTitle(),
                        course.getCategory(),
                        course.getInstructorId()
                ))
                .collect(Collectors.toList());
    }

    public boolean updateCourse(UpdateCourseDto dto) {
        if (dto.id() == null || dto.id() <= 0) {
            throw new InvalidCourseIdException();
        }

        Course existingCourse = courseDao.findById(dto.id());
        if (existingCourse == null) {
            throw new CourseNotFoundException("해당 ID의 강의를 찾을 수 없습니다: " + dto.id());
        }
        if (dto.title() == null || dto.title().isBlank()) {
            throw new LXPException("제목은 필수입니다.");
        }
        if (dto.category() == null) {
            throw new LXPException("카테고리는 필수입니다.");
        }

        Course updatedCourse = new Course(
                existingCourse.getId(),
                dto.title(),
                dto.description(),
                existingCourse.getInstructorId(),
                dto.category(),
                existingCourse.getDateCreated(),
                touch()
        );

        return courseDao.update(updatedCourse);
    }

    public boolean deleteCourse(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidCourseIdException();
        }
        
        if (!courseDao.existsById(id)) {
            throw new CourseNotFoundException("해당 ID의 강의를 찾을 수 없습니다: " + id);
        }

        return courseDao.delete(id);
    }

    public LocalDateTime touch() {
        return LocalDateTime.now();
    }

}

