package com.lxp.question.model;

import com.lxp.base.BaseDateModifiedEntity;

import java.time.LocalDateTime;

/**
 * Question entity for the Q&A threading system.
 */
public class Question extends BaseDateModifiedEntity {
    
    private Long id;
    private Long lectureId;
    private Long userId;
    private Long rootId;
    private Long parentId; // null for root questions
    private String title;
    private String body;
    private LocalDateTime dateCreated;
    
    // Default constructor
    public Question() {
        this.dateCreated = LocalDateTime.now();
        recordDateModified();
    }
    
    // Constructor for root question

    /**
     * Constructor for root question
     * rootId will be set after insertion when we know the question ID
     * @param lectureId
     * @param userId
     * @param title
     * @param body
     */
    public Question(Long lectureId, Long userId, String title, String body) {
        this();
        this.lectureId = lectureId;
        this.userId = userId;
        this.title = title;
        this.body = body;
        this.parentId = null;
    }

    /**
     * Constructor for reply
     * @param lectureId
     * @param userId
     * @param rootId
     * @param parentId
     * @param title
     * @param body
     */
    public Question(Long lectureId, Long userId, Long rootId, Long parentId, String title, String body) {
        this();
        this.lectureId = lectureId;
        this.userId = userId;
        this.rootId = rootId;
        this.parentId = parentId;
        this.title = title;
        this.body = body;
    }

    /**
     * Full constructor for loading from database
     * @param id
     * @param lectureId
     * @param userId
     * @param rootId
     * @param parentId
     * @param title
     * @param body
     * @param dateCreated
     * @param dateModified
     */
    public Question(Long id, Long lectureId, Long userId, Long rootId, Long parentId, 
                   String title, String body, LocalDateTime dateCreated, LocalDateTime dateModified) {
        this.id = id;
        this.lectureId = lectureId;
        this.userId = userId;
        this.rootId = rootId;
        this.parentId = parentId;
        this.title = title;
        this.body = body;
        this.dateCreated = dateCreated;
        // Set the dateModified through the base class (if needed)
    }
    
    // Utility methods
    public boolean isRootQuestion() {
        return parentId == null;
    }
    
    public boolean isReply() {
        return parentId != null;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getLectureId() {
        return lectureId;
    }
    
    public void setLectureId(Long lectureId) {
        this.lectureId = lectureId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getRootId() {
        return rootId;
    }
    
    public void setRootId(Long rootId) {
        this.rootId = rootId;
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
        recordDateModified();
    }
    
    public String getBody() {
        return body;
    }
    
    public void setBody(String body) {
        this.body = body;
        recordDateModified();
    }
    
    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
    
    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", lectureId=" + lectureId +
                ", userId=" + userId +
                ", rootId=" + rootId +
                ", parentId=" + parentId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + getDateModified() +
                ", isRoot=" + isRootQuestion() +
                '}';
    }
}