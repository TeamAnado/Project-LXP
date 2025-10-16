package com.lxp.lecture.service;

import com.lxp.exception.LXPException;
import com.lxp.lecture.dao.LectureDao;
import com.lxp.lecture.dto.LectureCreateRequest;
import com.lxp.lecture.dto.LectureCreateResponse;
import com.lxp.lecture.model.Lecture;
import com.lxp.section.dao.SectionDao;
import com.lxp.section.domain.Section;

public class LectureService {
    private final LectureDao lectureDao;
    private final SectionDao sectionDao;

    public LectureService(LectureDao lectureDao, SectionDao sectionDao) {
        this.lectureDao = lectureDao;
        this.sectionDao = sectionDao;
    }

    public LectureCreateResponse save(LectureCreateRequest lectureRequest) {
        Section section = findBySectionId(lectureRequest);

        Lecture lecture = Lecture.of(
                section.getId(),
                lectureRequest.title(),
                lectureRequest.description()
        );
        lecture.recordTime();

        return new LectureCreateResponse(lectureDao.save(lecture));
    }

    private Section findBySectionId(LectureCreateRequest lectureRequest) {
        return sectionDao.findById(lectureRequest.sectionId())
                .orElseThrow(() -> new LXPException("존재하지 않는 섹션 ID 입니다."));
    }
}
