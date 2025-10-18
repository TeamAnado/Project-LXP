package com.lxp.enrollment.model;

import com.lxp.base.BaseDateCreatedEntity;

import java.sql.Date;
//TODO : 패키지 분리, 클래스명 수정
// TODO: 커스텀 Exception 활용

public class Enrollment extends BaseDateCreatedEntity {
    private Long userId;
    private Long courseId;
    private State state;
    private Date dateCompleted;

    // getUserId - camel case

    public Long getUserId() {
        return userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public State getState() {
        return state;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }
}
