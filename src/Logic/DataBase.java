package Logic;

import org.sqlite.JDBC;
import java.sql.*;
import java.util.ArrayList;

//БД
public class DataBase {
    private static final String CONNECTION_ADDRESS_MASK = "jdbc:sqlite:./";
    private Connection connection;
    private ArrayList<Student> data;

    public DataBase(){
        data = new ArrayList<>();
        connection = null;
    }

    public String connect(String name){
        String result;
        try{
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection(CONNECTION_ADDRESS_MASK + name);
            result = "База данных успешно открыта!";
        }
        catch (SQLException exception){
            result = exception.getMessage();
        }

        return result;
    }

    public String executeAllData(){
        data.clear();
        String result;
        try (Statement statement = connection.createStatement()) {
            ResultSet executionResult = statement.executeQuery("SELECT * FROM students;");
            while (executionResult.next()){
                data.add(new Student(executionResult.getInt("id"), executionResult.getString("student_name"), executionResult.getString("student_surname"),
                        executionResult.getString("student_birthday"), executionResult.getBoolean("test_result"), executionResult.getByte("diff_result"),
                        executionResult.getByte("exam_result")));
            }
            result = (data.isEmpty())? "База данных пуста!" : "Чтение успешно завершено!";
        }
        catch (SQLException exception){
            result = exception.getMessage();
        }

        return result;
    }

    public ArrayList<Student> getData(){
        return data;
    }

    public String close(){
        String result;
        try{
            data.clear();
            connection.close();
            result = "База данных успешно закрыта!";
        }
        catch (SQLException exception){
            result = exception.getMessage();
        }

        return result;
    }
}
