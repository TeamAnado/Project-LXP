package com.lxp.course.service;

import com.lxp.course.dao.CourseDAO;
import com.lxp.course.model.Course;
import com.lxp.course.model.enums.Category;
import com.lxp.course.service.dto.CourseDetailDto;
import com.lxp.course.service.dto.CourseListDto;
import com.lxp.course.service.dto.CreateCourseDto;
import com.lxp.course.service.dto.UpdateCourseDto;
import com.lxp.global.exception.LXPException;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {

    private final CourseDAO courseDAO;

    public CourseService() {
        courseDAO = new CourseDAO();
    }

    public Long createCourse(CreateCourseDto dto) throws SQLException {
        Course course = new Course(
                dto.title(),
                dto.description(),
                dto.instructorId(),
                dto.category()
        );
        return courseDAO.save(course);
    }

    public List<CourseListDto> findAllCourses() throws SQLException {
        List<Course> courses = courseDAO.findAll();
        return courses.stream()
                .map(course -> new CourseListDto(
                        course.getId(),
                        course.getTitle(),
                        course.getCategory(),
                        course.getInstructorId()
                ))
                .collect(Collectors.toList());
    }

    public CourseDetailDto findCourseById(Long id) throws SQLException {
        Course course = courseDAO.findById(id);
        if (course == null) {
            throw new LXPException("해당 ID의 강의를 찾을 수 없습니다: " + id);
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

    public List<CourseListDto> findCoursesByTitle(String title) throws SQLException {
        List<Course> courses = courseDAO.findByTitleContaining(title);

        return courses.stream()
                .map(course -> new CourseListDto(
                        course.getId(),
                        course.getTitle(),
                        course.getCategory(),
                        course.getInstructorId()
                ))
                .collect(Collectors.toList());
    }

    public List<CourseListDto> findCoursesByCategory(Category category) throws SQLException {
        List<Course> courses = courseDAO.findByCategory(category);

        return courses.stream()
                .map(course -> new CourseListDto(
                        course.getId(),
                        course.getTitle(),
                        course.getCategory(),
                        course.getInstructorId()
                ))
                .collect(Collectors.toList());
    }

    public List<CourseListDto> findCoursesByInstructorId(Long instructorId) throws SQLException {
        List<Course> courses = courseDAO.findByInstructorId(instructorId);

        return courses.stream()
                .map(course -> new CourseListDto(
                        course.getId(),
                        course.getTitle(),
                        course.getCategory(),
                        course.getInstructorId()
                ))
                .collect(Collectors.toList());
    }

    public boolean updateCourse(UpdateCourseDto dto) throws SQLException {
        // Check if course exists
        Course existingCourse = courseDAO.findById(dto.id());
        if (existingCourse == null) {
            throw new LXPException("해당 ID의 강의를 찾을 수 없습니다: " + dto.id());
        }

        // Create new Course object with updated values and current dateModified
        Course updatedCourse = new Course(
                existingCourse.getId(),
                dto.title(),
                dto.description(),
                existingCourse.getInstructorId(),
                dto.category(),
                existingCourse.getDateCreated(),
                touch()
        );

        return courseDAO.update(updatedCourse);
    }

    public boolean deleteCourse(Long id) throws SQLException {
        // Check if course exists
        if (!courseDAO.existsById(id)) {
            throw new LXPException("해당 ID의 강의를 찾을 수 없습니다: " + id);
        }

        return courseDAO.delete(id);
    }

    public LocalDateTime touch() {
        return LocalDateTime.now();
    }

}

