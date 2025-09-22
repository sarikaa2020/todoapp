package com.todo.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.todo.model.Todo;
import com.todo.util.DatabaseConnection;

public class TodoAppDAO {

    private static final String SELECT_ALL_TODOS = "SELECT * FROM todos ORDER BY created_at DESC";
    private static final String INSERT_TODO = "INSERT INTO todos (title,description,completed,created_at,updated_at) VALUES(?,?,?,?,?)";
    private static final String SELECT_TODO_BY_ID = "SELECT * FROM todos WHERE id=?";
    private static final String UPDATE_TODO = "UPDATE todos SET title=?,description=?,completed=?,updated_at=? WHERE id=?";
    private static final String DELETE_TODO = "DELETE FROM todos WHERE id=?";
    private static final String FILTER_TODOS = "SELECT * FROM todos WHERE completed=? ";

    public int createTodo(Todo todo) throws SQLException {
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT_TODO, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, todo.getTitle());
            stmt.setString(2, todo.getDescription());
            stmt.setBoolean(3, todo.isCompleted());
            stmt.setTimestamp(4, Timestamp.valueOf(todo.getCreated_at()));
            stmt.setTimestamp(5, Timestamp.valueOf(todo.getUpdated_at()));

            int rowAffected = stmt.executeUpdate();
            if (rowAffected == 0) {
                throw new SQLException("Creating todo failed, no row inserted");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating todo failed, no ID found");
                }
            }
        }
    }
    public boolean updateTodo(Todo todo) throws SQLException {
        
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE_TODO)) {

            stmt.setString(1, todo.getTitle());
            stmt.setString(2, todo.getDescription());
            stmt.setBoolean(3, todo.isCompleted());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5, todo.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean deleteTodo(int id )throws SQLException{
        try(Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(DELETE_TODO))
            {
                stmt.setInt(1,id);
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }

    }
    public Todo getTodoByID(int id) throws SQLException {
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_TODO_BY_ID)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return getTodoRow(rs);
                } else {
                    return null;
                }
            }
        }
    }

    private Todo getTodoRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String description = rs.getString("description");
        boolean completed = rs.getBoolean("completed");
        LocalDateTime created_at = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updated_at = rs.getTimestamp("updated_at").toLocalDateTime();

        return new Todo(id, title, description, completed, created_at, updated_at);
    }

    public List<Todo> getAllTodos() throws SQLException {
        List<Todo> todos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_TODOS);
             ResultSet res = stmt.executeQuery()) {

            while (res.next()) {
                todos.add(getTodoRow(res));
            }
        }
        return todos;
    }

    public List<Todo> filterTodos(boolean filter) throws SQLException {
        List<Todo> todos = new ArrayList<>();
        try(Connection conn = DatabaseConnection.getDBConnection();
            PreparedStatement stmt  = conn.prepareStatement(FILTER_TODOS))
            {
                stmt.setBoolean(1, filter);
                try(ResultSet res = stmt.executeQuery())
                {
                    while(res.next())
                    {
                        todos.add(getTodoRow(res));
                    }
                }

            }

        return todos;
    }

    
}