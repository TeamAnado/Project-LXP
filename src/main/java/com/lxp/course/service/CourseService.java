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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {

    private final CourseDao courseDao;

    public CourseService() {
        courseDao = new CourseDao();
    }

    public Long createCourse(CreateCourseDto dto) {
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
        List<Course> courses = courseDao.findByTitleContaining(title);

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

