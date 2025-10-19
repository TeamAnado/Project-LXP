package com.lxp.enrollment.model;

import com.lxp.global.base.BaseDateCreatedEntity;

import java.sql.Date;
import java.time.LocalDateTime;
//TODO : 패키지 분리, 클래스명 수정
// TODO: 커스텀 Exception 활용

public class Enrollment extends BaseDateCreatedEntity {
    private Long userId;
    private Long courseId;
    private State state;
    private Date dateCompleted;

    public Enrollment() {

    }

    public Enrollment(Long userId, Long courseId, State state) {
        this.userId = userId;
        this.courseId = courseId;
        this.state = state;
    }

    public Enrollment(Long id, Long userId, Long courseId, State state, Date dateCompleted, LocalDateTime dateCreated) {
        super(id, dateCreated);
        this.userId = userId;
        this.courseId = courseId;
        this.state = state;
        this.dateCompleted = dateCompleted;
    }
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
