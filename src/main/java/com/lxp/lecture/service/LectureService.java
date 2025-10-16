package com.lxp.lecture.service;

import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.dto.LectureCreateRequest;
import com.lxp.lecture.dto.LectureCreateResponse;
import com.lxp.lecture.model.Lecture;

public class LectureService {

    private final LectureDao lectureDao;

    public LectureService(LectureDao lectureDao) {
        this.lectureDao = lectureDao;
    }

    public LectureCreateResponse save(LectureCreateRequest lectureRequest) {
        Lecture lecture = Lecture.of(
                lectureRequest.title(),
                lectureRequest.description()
        );
        lecture.recordTime();
        return new LectureCreateResponse(lectureDao.save(lecture));
    }

}
