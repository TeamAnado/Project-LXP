package com.lxp.course.exception;

import com.lxp.global.exception.LXPException;

public class CourseNotFoundException extends LXPException {
    
    public CourseNotFoundException() {
        super("해당 ID의 코스를 찾을 수 없습니다.");
    }

}
