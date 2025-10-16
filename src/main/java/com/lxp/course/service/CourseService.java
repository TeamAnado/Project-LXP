package com.lxp.course.service;

import com.lxp.course.dao.CourseDAO;
import com.lxp.course.model.Course;
import com.lxp.course.service.dto.CourseDetailDto;
import com.lxp.course.service.dto.CourseListDto;
import com.lxp.course.service.dto.CreateCourseDto;
import com.lxp.exception.LXPException;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


public class CourseService {
    private final CourseDAO courseDAO;

    public CourseService() throws SQLException {
        courseDAO = new CourseDAO();
    }

    public Long createCourse (CreateCourseDto dto) throws SQLException {
        Course course = new Course(
                dto.title(),
                dto.description(),
                dto.instructorId(),
                dto.category()
        );
        return courseDAO.Save(course);
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

    public List<CourseListDto> findCourseByTitle(String title) throws SQLException {
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




}

