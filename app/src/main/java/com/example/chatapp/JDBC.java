package com.example.chatapp;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.example.chatapp.models.ChatModel;
import com.example.chatapp.models.Message;
import com.example.chatapp.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.postgresql.PGConnection;
import org.postgresql.PGNotification;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
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
        void logUser(boolean authComplete, String userUid);
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

    public interface CallBackReadChats {
        void readChats(ObservableList<ChatModel> chatList);
    }

    CallBackReadChats callBackReadChats;

    public void registerCallBackReadChats(CallBackReadChats callBackReadChats) {
        this.callBackReadChats = callBackReadChats;
    }

    public interface CallBackReadMessages {
        void readMsg(List<Message> messages);
    }

    CallBackReadMessages callBackReadMessages;

    public void registerCallBackReadMessages(CallBackReadMessages callBackReadMessages) {
        this.callBackReadMessages = callBackReadMessages;
    }

    public interface CallBackListenMsg {
        void beginListen(Message msg);
    }

    CallBackListenMsg callBackListenMsg;

    public void registerCallBackListenMsg(CallBackListenMsg callBackListenMsg) {
        this.callBackListenMsg = callBackListenMsg;
    }

    public interface CallBackUsers {
        void getUsers(List<User> users);
    }

    CallBackUsers callBackUsers;

    public void registerCallBackUsers(CallBackUsers callBackUsers) {
        this.callBackUsers = callBackUsers;
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
                callBack.logUser(true, findedUser);
            } else {
                callBack.logUser(false, findedUser);
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

    public void getUsers(String exludedUser) {
        Runnable taskRead = () -> {
            String getQuery = "select * from users where user_uid not in ('" + exludedUser + "')";
            List<User> usersList = new ArrayList<>();
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();
                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    usersList.add(new User(
                            rs.getString("user_uid"),
                            rs.getString("login"),
                            rs.getString("number"),
                            rs.getString("email"),
                            "null"));

                }
                callBackUsers.getUsers(usersList);
            } catch (SQLException e) {
                printSQLException(e);
            }

        };

        Thread thread = new Thread(taskRead);
        thread.start();
    }

    public void readMessages(int chatId, String usUid) {
        Runnable taskRead = () -> {
            String getQuery = "select * from messages where (sender = '" + usUid +
                    "' OR receiver = '" + usUid + "') AND chat_id = " + chatId;
            List<Message> messageList = new ArrayList<>();
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();
                // Step 4: Process the ResultSet object.
                while (rs.next()) {
                    messageList.add(new Message(
                            rs.getInt("message_id"),
                            rs.getInt("chat_id"),
                            rs.getString("sender"),
                            rs.getString("receiver"),
                            rs.getString("content"),
                            (Long) rs.getObject("date_create"),
                            rs.getBoolean("is_seen")));

                }
                callBackReadMessages.readMsg(messageList);
            } catch (SQLException e) {
                printSQLException(e);
            }

        };

        Thread thread = new Thread(taskRead);
        thread.start();
    }

    public void insertMessage(Message msg) {
        Runnable taskRead = () -> {
            String getQuery = "insert into messages(chat_id, sender, receiver, content, date_create)" +
                    "values(?,?,?,?,?)";
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
                // Step 3: Execute the query or update query
                preparedStatement.setInt(1, msg.getChatId());
                preparedStatement.setString(2, msg.getSender());
                preparedStatement.setString(3, msg.getReceiver());
                preparedStatement.setString(4, msg.getMessageText());
                preparedStatement.setObject(5, msg.getDateCreate());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                printSQLException(e);
            }

        };

        Thread thread = new Thread(taskRead);
        thread.start();
    }

    public void readChats(String usUId) {
        Runnable taskRead = () -> {
            String getQuery = "SELECT * FROM chats WHERE '"+ usUId + "' = ANY(participiants)";
            ObservableList<ChatModel> chatsList = new ObservableArrayList<>();
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
                //preparedStatement.setString(1, usUId);
                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        Array partc = rs.getArray("participiants");
                        String[] participip = (String[])partc.getArray();

                        chatsList.add(new ChatModel(rs.getInt("chat_id"),
                                rs.getString("name"),
                                rs.getString("creator_uid"),
                                participip,
                                rs.getString("last_message"),
                                (Long)rs.getObject("last_message_time")));
                    }
                    callBackReadChats.readChats(chatsList);
                } else {
                    chatsList = null;
                    callBackReadChats.readChats(chatsList);
                }


            } catch (SQLException e) {
                printSQLException(e);
            }
        };
        Thread thread = new Thread(taskRead);
        thread.start();

    }

    public void findChats(String usUId, String sUsUid) {
        Runnable taskRead = () -> {
            String getQuery = "SELECT * FROM chats WHERE '"+ usUId +
                    "' = ANY(participiants) AND '"+ sUsUid + "' = ANY(participiants)";
            ObservableList<ChatModel> chatsList = new ObservableArrayList<>();
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(getQuery)) {
                //preparedStatement.setString(1, usUId);
                // Step 3: Execute the query or update query
                ResultSet rs = preparedStatement.executeQuery();
                if (rs != null) {
                    while (rs.next()) {
                        Array partc = rs.getArray("participiants");
                        String[] participip = (String[])partc.getArray();

                        chatsList.add(new ChatModel(rs.getInt("chat_id"),
                                rs.getString("name"),
                                rs.getString("creator_uid"),
                                participip,
                                rs.getString("last_message"),
                                (Long)rs.getObject("last_message_time")));
                    }
                    callBackReadChats.readChats(chatsList);
                } else {
                    chatsList = null;
                    callBackReadChats.readChats(chatsList);
                }


            } catch (SQLException e) {
                printSQLException(e);
            }
        };
        Thread thread = new Thread(taskRead);
        thread.start();

    }

    public void createChat(String userCreator, String userEmployee) {
        Runnable taskCreate = () -> {
            String createQuery = "insert into chats(name, creator_uid, participiants)" +
                    "values (?, ?, ?)";
            ObservableList<ChatModel> chatsList = new ObservableArrayList<>();
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(createQuery)) {
                preparedStatement.setString(1, "Default");
                preparedStatement.setString(2, userCreator);
                String[] partc = {userCreator, userEmployee};
                preparedStatement.setObject(3, partc);
                preparedStatement.executeUpdate();

                // Step 4: Process the ResultSet object.


            } catch (SQLException e) {
                printSQLException(e);
            }
            findChats(userCreator, userEmployee);
        };
        Thread thread = new Thread(taskCreate);
        thread.start();
    }

    public void updateChat(int chatId, String message, long time) {
        Runnable taskRead = () -> {
            String setQuery = "update chats set last_message='"+message+"', last_message_time="+time +
                    " where chat_id=" + chatId;
            ObservableList<ChatModel> chatsList = new ObservableArrayList<>();
            // Step 1: Establishing a Connection
            try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
                 // Step 2:Create a statement using connection object
                 PreparedStatement preparedStatement = connection.prepareStatement(setQuery)) {
                //preparedStatement.setString(1, usUId);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();

                // Step 4: Process the ResultSet object.


            } catch (SQLException e) {
                printSQLException(e);
            }
        };
        Thread thread = new Thread(taskRead);
        thread.start();
    }

    public void listenMessages(int chatId) {

        Runnable listenTask = () -> {
            Connection conn = null;
            PGConnection pgconn = null;
            try {
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                pgconn = conn.unwrap(PGConnection.class);
                Statement stmt = conn.createStatement();
                stmt.execute("LISTEN message_added_chat_" + chatId);
                stmt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try
            {
                while (true)
                {
                    PGNotification notifications[] = pgconn.getNotifications();

                    // If this thread is the only one that uses the connection, a timeout can be used to
                    // receive notifications immediately:
                    // org.postgresql.PGNotification notifications[] = pgconn.getNotifications(10000);
                    ObjectMapper mapper = new ObjectMapper();
                    if (notifications != null)
                    {
                        for (int i=0; i < notifications.length; i++) {
                            Log.d("LISTENPOSTGRES","Got notification: " + notifications[i].getParameter());
                            Message msg = mapper.readValue(notifications[i].getParameter(),
                                    Message.class);
                            callBackListenMsg.beginListen(msg);
                        }

                    }

                    // wait a while before checking again for new
                    // notifications

                    Thread.sleep(500);
                }
            }
            catch (SQLException sqle)
            {
                sqle.printStackTrace();
            }
            catch (InterruptedException ie)
            {
                ie.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(listenTask);
        thread.start();

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