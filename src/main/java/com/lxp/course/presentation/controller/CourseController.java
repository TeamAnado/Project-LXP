package com.lxp.course.presentation.controller;

import com.lxp.course.model.enums.Category;
import com.lxp.course.presentation.controller.request.CourseCreateRequest;
import com.lxp.course.presentation.controller.request.CourseUpdateRequest;
import com.lxp.course.presentation.controller.response.CourseCreateResponse;
import com.lxp.course.presentation.controller.response.CourseDetailResponse;
import com.lxp.course.presentation.controller.response.CourseResponse;
import com.lxp.course.service.CourseService;
import com.lxp.course.service.dto.CourseDetailDto;
import com.lxp.course.service.dto.CourseListDto;

import java.util.List;
import java.util.stream.Collectors;

public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    public CourseCreateResponse createCourse(CourseCreateRequest request) {
        Long courseId = courseService.createCourse(request.to());
        return CourseCreateResponse.from(courseId);
    }

    public List<CourseResponse> findAllCourses() {
        List<CourseListDto> courses = courseService.findAllCourses();
        return mapToResponseList(courses);
    }

    public CourseDetailResponse findCourseById(Long id) {
        CourseDetailDto courseDetail = courseService.findCourseById(id);
        return CourseDetailResponse.from(courseDetail);
    }

    public List<CourseResponse> searchCoursesByTitle(String title) {
        List<CourseListDto> courses = courseService.findCoursesByTitle(title);
        return mapToResponseList(courses);
    }

    public List<CourseResponse> searchCoursesByCategory(Category category) {
        List<CourseListDto> courses = courseService.findCoursesByCategory(category);
        return mapToResponseList(courses);
    }

    
    private List<CourseResponse> mapToResponseList(List<CourseListDto> courses) {
        return courses.stream()
                .map(CourseResponse::from)
                .collect(Collectors.toList());
    }

    public boolean updateCourse(CourseUpdateRequest request) {
        return courseService.updateCourse(request.to());
    }

    public boolean deleteCourse(Long id) {
        return courseService.deleteCourse(id);
    }

}
