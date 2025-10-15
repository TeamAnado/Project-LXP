package com.lxp.question.dao;

import com.lxp.question.model.Question;
import com.lxp.support.QueryUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Question entity
 * Handles all database operations for the Q&A threading system
 */
public class QuestionDAO {
    
    private final Connection connection;
    
    public QuestionDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Insert a new root question and return its ID
     */
    public Long insertRootQuestion(Question question) throws SQLException {
        String sql = QueryUtil.getQuery("INSERT_ROOT_QUESTION");
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, question.getLectureId());
            stmt.setLong(2, question.getUserId());
            stmt.setString(3, question.getTitle());
            stmt.setString(4, question.getBody());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating root question failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long questionId = generatedKeys.getLong(1);
                    // Update root_id to be the same as id for root questions
                    updateRootId(questionId, questionId);
                    return questionId;
                } else {
                    throw new SQLException("Creating root question failed, no ID obtained.");
                }
            }
        }
    }
    
    /**
     * Insert a reply question and return its ID
     */
    public Long insertReply(Question question) throws SQLException {
        String sql = QueryUtil.getQuery("INSERT_REPLY_QUESTION");
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, question.getLectureId());
            stmt.setLong(2, question.getUserId());
            stmt.setLong(3, question.getRootId());
            stmt.setLong(4, question.getParentId());
            stmt.setString(5, question.getTitle());
            stmt.setString(6, question.getBody());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating reply failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating reply failed, no ID obtained.");
                }
            }
        }
    }
    
    /**
     * Update root_id for a question
     */
    private void updateRootId(Long questionId, Long rootId) throws SQLException {
        String sql = QueryUtil.getQuery("UPDATE_ROOT_ID");
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, rootId);
            stmt.setLong(2, questionId);
            stmt.executeUpdate();
        }
    }
    
    /**
     * Find all questions by user_id (for user profile page)
     */
    public List<Question> findQuestionsByUser(Long userId) throws SQLException {
        String sql = QueryUtil.getQuery("FIND_QUESTIONS_BY_USER");
        List<Question> questions = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(mapResultSetToQuestion(rs));
                }
            }
        }
        
        return questions;
    }
    
    /**
     * Find root questions by lecture_id (for lecture page)
     */
    public List<Question> findRootQuestionsByLecture(Long lectureId) throws SQLException {
        String sql = QueryUtil.getQuery("FIND_ROOT_QUESTIONS_BY_LECTURE");
        List<Question> questions = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, lectureId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(mapResultSetToQuestion(rs));
                }
            }
        }
        
        return questions;
    }
    
    /**
     * Find complete thread by root_id
     */
    public List<Question> findThreadByRootId(Long rootId) throws SQLException {
        String sql = QueryUtil.getQuery("FIND_THREAD_BY_ROOT_ID");
        List<Question> questions = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, rootId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(mapResultSetToQuestion(rs));
                }
            }
        }
        
        return questions;
    }
    
    /**
     * Find question by ID
     */
    public Question findQuestionById(Long id) throws SQLException {
        String sql = QueryUtil.getQuery("FIND_QUESTION_BY_ID");
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToQuestion(rs);
                }
                return null;
            }
        }
    }

    // Methods from here should be implemented on USER part

    /**
     * Check if user is enrolled in the course that contains the lecture
     */
    public boolean isUserEnrolled(Long userId, Long lectureId) throws SQLException {
        String sql = QueryUtil.getQuery("CHECK_USER_ENROLLMENT");

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, lectureId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("enrolled") > 0;
                }
                return false;
            }
        }
    }
    
    /**
     * Check if user is instructor of the course that contains the lecture
     */
    public boolean isUserInstructor(Long userId, Long lectureId) throws SQLException {
        String sql = QueryUtil.getQuery("CHECK_USER_IS_INSTRUCTOR");
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            stmt.setLong(2, lectureId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("is_instructor") > 0;
                }
                return false;
            }
        }
    }
    
    /**
     * Get the user_id of the root question creator
     */
    public Long getRootQuestionCreator(Long rootId) throws SQLException {
        String sql = QueryUtil.getQuery("GET_ROOT_QUESTION_CREATOR");
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, rootId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("user_id");
                }
                return null;
            }
        }
    }
    
    /**
     * Check if question exists
     */
    public boolean questionExists(Long questionId) throws SQLException {
        String sql = QueryUtil.getQuery("CHECK_QUESTION_EXISTS");
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, questionId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
    
    /**
     * Count replies in a thread
     */
    public int countThreadReplies(Long rootId) throws SQLException {
        String sql = QueryUtil.getQuery("COUNT_THREAD_REPLIES");
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, rootId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("reply_count");
                }
                return 0;
            }
        }
    }
    
    /**
     * Find direct replies to a question
     */
    public List<Question> findDirectReplies(Long parentId) throws SQLException {
        String sql = QueryUtil.getQuery("FIND_DIRECT_REPLIES");
        List<Question> questions = new ArrayList<>();
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, parentId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    questions.add(mapResultSetToQuestion(rs));
                }
            }
        }
        
        return questions;
    }
    
    /**
     * Helper method to map ResultSet to Question object
     */
    private Question mapResultSetToQuestion(ResultSet rs) throws SQLException {
        Long parentId = rs.getObject("parent_id", Long.class); // Handles NULL values
        
        return new Question(
            rs.getLong("id"),
            rs.getLong("lecture_id"),
            rs.getLong("user_id"),
            rs.getLong("root_id"),
            parentId,
            rs.getString("title"),
            rs.getString("body"),
            rs.getTimestamp("date_created").toLocalDateTime(),
            rs.getTimestamp("date_modified").toLocalDateTime()
        );
    }
}