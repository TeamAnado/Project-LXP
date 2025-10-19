package com.lxp.course.presentation.controller;

import com.lxp.course.model.enums.Category;
import com.lxp.course.presentation.controller.request.CourseCreateRequest;
import com.lxp.course.presentation.controller.request.CourseSearchRequest;
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

    public List<CourseResponse> searchCourses(CourseSearchRequest request) {
        List<CourseListDto> courses;
        
        if (request.title() != null && !request.title().trim().isEmpty()) {
            courses = courseService.findCoursesByTitle(request.title());
        } else if (request.category() != null) {
            courses = courseService.findCoursesByCategory(request.category());
        } else if (request.instructorId() != null) {
            courses = courseService.findCoursesByInstructorId(request.instructorId());
        } else {
            courses = courseService.findAllCourses();
        }
        
        return mapToResponseList(courses);
    }

    // Deprecated - use searchCourses instead
    @Deprecated
    public List<CourseResponse> searchCoursesByTitle(String title) {
        return searchCourses(new CourseSearchRequest(title, null, null));
    }

    // Deprecated - use searchCourses instead  
    @Deprecated
    public List<CourseResponse> searchCoursesByCategory(Category category) {
        return searchCourses(new CourseSearchRequest(null, category, null));
    }

    // Deprecated - use searchCourses instead
    @Deprecated
    public List<CourseResponse> searchCoursesByInstructor(Long instructorId) {
        return searchCourses(new CourseSearchRequest(null, null, instructorId));
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
