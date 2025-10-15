package com.lxp.question.service;

import com.lxp.question.dao.QuestionDAO;
import com.lxp.question.model.Question;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Service class that handles business logic for the Q&A threading system.
 * Implements access control rules:
 * - Only enrolled users can create root questions
 * - Only instructor and root question creator can add replies to threads
 * - Provides the two main functions: search by writer and get root questions by lecture
 */
public class QuestionService {
    
    private final QuestionDAO questionDAO;
    
    public QuestionService(Connection connection) {
        this.questionDAO = new QuestionDAO(connection);
    }
    
    /**
     * Create a new root question (only for enrolled users)
     * @param lectureId the lecture to ask question about
     * @param userId the user asking the question
     * @param title question title
     * @param body question body
     * @return question ID if successful
     * @throws IllegalAccessException if user is not enrolled
     */
    public Long createRootQuestion(Long lectureId, Long userId, String title, String body) 
            throws SQLException, IllegalAccessException {

        // TODO

        // Check if user is enrolled in the course
        if (!enrollmentDAO.isUserEnrolled(userId, lectureId)) {
            throw new IllegalAccessException("User must be enrolled in the course to ask questions");
        }
        
        // Validate input
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Question title cannot be empty");
        }
        if (body == null || body.trim().isEmpty()) {
            throw new IllegalArgumentException("Question body cannot be empty");
        }
        
        Question question = new Question(lectureId, userId, title.trim(), body.trim());
        return questionDAO.insertRootQuestion(question);
    }
    
    /**
     * Add a reply to an existing question (only instructor or root creator can reply)
     * @param parentQuestionId the question being replied to
     * @param userId the user replying
     * @param title reply title
     * @param body reply body
     * @return reply ID if successful
     * @throws IllegalAccessException if user cannot reply to this thread
     */
    public Long addReply(Long parentQuestionId, Long userId, String title, String body) 
            throws SQLException, IllegalAccessException {

        // TODO

        // Get parent question to validate access and get thread info
        Question parentQuestion = questionDAO.findQuestionById(parentQuestionId);
        if (parentQuestion == null) {
            throw new IllegalArgumentException("Parent question does not exist");
        }
        
        // Get root question ID and creator
        Long rootId = parentQuestion.isRootQuestion() ? parentQuestion.getId() : parentQuestion.getRootId();
        Long rootCreatorId = questionDAO.getRootQuestionCreator(rootId);
        
        // Check if user can reply (instructor or root creator)
        boolean isInstructor = courseDAO.isUserInstructor(userId, parentQuestion.getLectureId());
        boolean isRootCreator = userId.equals(rootCreatorId);
        
        if (!isInstructor && !isRootCreator) {
            throw new IllegalAccessException("Only instructor or the original question creator can reply to this thread");
        }
        
        // Validate input
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Reply title cannot be empty");
        }
        if (body == null || body.trim().isEmpty()) {
            throw new IllegalArgumentException("Reply body cannot be empty");
        }
        
        Question reply = new Question(
            parentQuestion.getLectureId(),
            userId,
            rootId,
            parentQuestionId,
            title.trim(),
            body.trim()
        );
        
        return questionDAO.insertReply(reply);
    }
    
    /**
     * Find questions by writer (user_id)
     * Used for user profile page to show all questions written by the logged-in user
     */
    public List<Question> getQuestionsByWriter(Long userId) throws SQLException {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        return questionDAO.findQuestionsByUser(userId);
    }
    
    /**
     * Find root questions by lecture (lecture_id)
     * Used for lecture page to show all questions asked about this lecture
     * Only returns root questions, not replies
     */
    public List<Question> getRootQuestionsByLecture(Long lectureId) throws SQLException {
        if (lectureId == null) {
            throw new IllegalArgumentException("Lecture ID cannot be null");
        }
        
        return questionDAO.findRootQuestionsByLecture(lectureId);
    }
    
    /**
     * Get complete thread by root question ID (useful for viewing full conversation)
     */
    public List<Question> getCompleteThread(Long rootQuestionId) throws SQLException {
        if (rootQuestionId == null) {
            throw new IllegalArgumentException("Root question ID cannot be null");
        }
        
        // Verify it's actually a root question
        Question rootQuestion = questionDAO.findQuestionById(rootQuestionId);
        if (rootQuestion == null) {
            throw new IllegalArgumentException("Question not found");
        }
        if (!rootQuestion.isRootQuestion()) {
            throw new IllegalArgumentException("Specified question is not a root question");
        }
        
        return questionDAO.findThreadByRootId(rootQuestionId);
    }
    
    /**
     * Get direct replies to a specific question
     * NOT SURE ABOUT THIS METHODS...
     */
    public List<Question> getDirectReplies(Long questionId) throws SQLException {
        if (questionId == null) {
            throw new IllegalArgumentException("Question ID cannot be null");
        }
        
        return questionDAO.findDirectReplies(questionId);
    }
    
    /**
     * Get question by ID
     */
    public Question getQuestionById(Long questionId) throws SQLException {
        if (questionId == null) {
            throw new IllegalArgumentException("Question ID cannot be null");
        }
        
        return questionDAO.findQuestionById(questionId);
    }
    
    /**
     * Check if user can create root question in lecture
     */
    public boolean canUserCreateRootQuestion(Long userId, Long lectureId) throws SQLException {

        // TODO

        return enrollmentDAO.isUserEnrolled(userId, lectureId);
    }
    
    /**
     * Check if user can reply to a question
     */
    public boolean canUserReplyToQuestion(Long userId, Long questionId) throws SQLException {

        // TODO

        Question question = questionDAO.findQuestionById(questionId);
        if (question == null) {
            return false;
        }
        
        // Check if user is instructor
        if (courseDAO.isUserInstructor(userId, question.getLectureId())) {
            return true;
        }
        
        // Check if user is root creator
        Long rootId = question.isRootQuestion() ? question.getId() : question.getRootId();
        Long rootCreatorId = questionDAO.getRootQuestionCreator(rootId);
        
        return userId.equals(rootCreatorId);
    }
    
    /**
     * Get thread statistics
     */
    public ThreadStats getThreadStatistics(Long rootQuestionId) throws SQLException {
        if (rootQuestionId == null) {
            throw new IllegalArgumentException("Root question ID cannot be null");
        }
        
        Question rootQuestion = questionDAO.findQuestionById(rootQuestionId);
        if (rootQuestion == null || !rootQuestion.isRootQuestion()) {
            throw new IllegalArgumentException("Invalid root question ID");
        }
        
        int replyCount = questionDAO.countThreadReplies(rootQuestionId);
        return new ThreadStats(rootQuestion, replyCount);
    }
    
    /**
     * Inner class to hold thread statistics
     */
    public static class ThreadStats {
        private final Question rootQuestion;
        private final int replyCount;
        
        public ThreadStats(Question rootQuestion, int replyCount) {
            this.rootQuestion = rootQuestion;
            this.replyCount = replyCount;
        }
        
        public Question getRootQuestion() {
            return rootQuestion;
        }
        
        public int getReplyCount() {
            return replyCount;
        }
        
        public boolean hasReplies() {
            return replyCount > 0;
        }
        
        @Override
        public String toString() {
            return "ThreadStats{" +
                    "rootQuestion='" + rootQuestion.getTitle() + '\'' +
                    ", replyCount=" + replyCount +
                    ", hasReplies=" + hasReplies() +
                    '}';
        }
    }
}