package com.example.chatapp;

import android.app.Application;
import android.util.Log;

import com.example.chatapp.models.ChatModel;
import com.example.chatapp.models.Message;
import com.example.chatapp.models.User;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class JDBC {

    final String DB_URL = "jdbc:postgresql://192.168.50.49:5432/testDb";
    final String USER = "baseAdmin";            //TODO зашифровать и засунуть в SharedPreferences
    final String PASS = "postgresPassword";

    private String findedUser;

    public JDBC() {

    }

    public interface CallBackLogin {
        void logUser(boolean authComplete);
    }

    CallBackLogin callBack;

    public void registerCallback(CallBackLogin callBack) {
        this.callBack = callBack;
    }

    public interface CallBackRegister {
        void regUser(User user);
    }

    CallBackRegister callReg;

    public void registerRegCallback(CallBackRegister callReg) {
        this.callReg = callReg;
    }

    public interface CallBackCheck {
        void checkUs(String email, boolean exist);
    }

    CallBackCheck callBackCheck;

    public void registerCallBackCheck(CallBackCheck callBackCheck) {
        this.callBackCheck = callBackCheck;
    }

    public void logUser(String email, String pass) {
        Runnable taskLog = () -> {
            String getQuery = "select * from users where email = '" + email +
                    "' AND password = '" + pass + "'";
            //Class.forName("org.postgresql.Driver");
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();
                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    findedUser = rs.getString("user_uid");
                }
                Log.d("POSTGRES", "Data retrieved!");

            } catch (SQLException e) {
                printSQLException(e);
                Log.d("POSTGRES", "Failed retrieve!");
            }

            if (findedUser != null) {
                Log.d("POSTGRES", "User found: " + findedUser);
                callBack.logUser(true);
            } else {
                callBack.logUser(false);
            }
        };
        Thread thread = new Thread(taskLog);
        thread.start();
    }

    public void checkUser(String email) {
        Runnable chekTask = () -> {
            String sql_query_find = "select email from users where email =?";
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

                 PreparedStatement preparedStatement = connection.prepareStatement(sql_query_find)) {
                preparedStatement.setString(1, email);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs != null) {
                    callBackCheck.checkUs(email, false);
                } else {

                    callBackCheck.checkUs(email, true);
                }

            } catch (SQLException e) {

                // print SQL exception information
                printSQLException(e);
            }
        };
        Thread checkTh = new Thread(chekTask);
        checkTh.start();
    }

    public void registerUser(User user) {
        Runnable taskReg = () -> {

            String sql_query = "INSERT INTO users (user_uid, email, login, password) " +
                    "VALUES (?,?,?,?);";

            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 //Class.forName("com.postgres.Driver");
                 // Step 2:Create a statement using connection object

                 PreparedStatement preparedStatement = connection.prepareStatement(sql_query)) {
                preparedStatement.setString(1, user.Uid);
                preparedStatement.setString(2, user.Email);
                preparedStatement.setString(3, user.Name);
                preparedStatement.setString(4, user.Password);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();

                callReg.regUser(user);

            } catch (SQLException e) {

                // print SQL exception information
                printSQLException(e);
            }

        };
        Thread thread = new Thread(taskReg);
        thread.start();

    }

    public void readMessages(int chatId) {
        Runnable taskRead = () -> {
            String getQuery = "select * from messages where (sender =? OR receiver =?) AND chat_id =?";
            List<Message> messageList = new ArrayList<>();
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
                preparedStatement.setString(1, findedUser);
                preparedStatement.setString(2, findedUser);
                preparedStatement.setInt(3, chatId);
                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();
                int i = 1;
                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    /*messageList.add(new Message(
                            rs.getInt("message_id"),
                            rs.getInt("chat_id"),
                            rs.getString("sender"),
                            rs.getString("receiver"),
                            rs.getString("content"),
                            rs.getInt("date_create"),
                            rs.getBoolean("is_seen")));*/
                    Object msgObj = rs.getObject(i);
                    Message msg = (Message)msgObj;
                    messageList.add(msg);
                    i++;
                }
                i = 1;
            } catch (SQLException e) {
                printSQLException(e);
            }

        };

        Thread thread = new Thread(taskRead);
        thread.start();
    }

    public void readChats(User user) {
        Runnable taskRead = () -> {
            String getQuery = "SELECT * FROM chats WHERE '?' = ANY(participiants)";
            List<ChatModel> chatsList = new ArrayList<>();
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
                preparedStatement.setString(1, user.Uid);
                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();

                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    Array partc = rs.getArray("participiants");
                    String[] participip = (String[])partc.getArray();

                    chatsList.add(new ChatModel(rs.getInt("chat_id"),
                            rs.getString("name"),
                            rs.getString("creator_uid"),
                            participip));
                }

            } catch (SQLException e) {
                printSQLException(e);
            }
        };
        Thread thread = new Thread(taskRead);
        thread.start();

    }

    public void listenMessages() {

    }

    public void listenChats() {

    }


    public void unlistenMessages() {

    }

    public void unlistenChats() {

    }

    public void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}