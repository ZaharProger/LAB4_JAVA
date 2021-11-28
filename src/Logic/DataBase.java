package Logic;

import org.sqlite.JDBC;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;

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
            create();
            result = "База данных успешно открыта!";
        }
        catch (SQLException exception){
            result = exception.getMessage();
        }

        return result;
    }

    private void create() throws SQLException{
        try (Statement statement = connection.createStatement()){
            statement.execute("CREATE TABLE IF NOT EXISTS 'students' (" +
                                    "'id' INTEGER NOT NULL PRIMARY KEY, " +
                                    "'name' VARCHAR(50) NOT NULL, " +
                                    "'surname' VARCHAR(50) NOT NULL, " +
                                    "'birthday' VARCHAR(10) NOT NULL, " +
                                    "'test_result' TINYINT NOT NULL, " +
                                    "'diff_result' TINYINT NOT NULL, " +
                                    "'exam_result' TINYINT NOT NULL);");
        }
    }

    public String executeAllData(){
        data.clear();
        String result;
        try (Statement statement = connection.createStatement()) {
            ResultSet executionResult = statement.executeQuery("SELECT * FROM students;");
            while (executionResult.next()){
                data.add(new Student(executionResult.getInt("id"), executionResult.getString("name"), executionResult.getString("surname"),
                        executionResult.getString("birthday"), executionResult.getBoolean("test_result"), executionResult.getByte("diff_result"),
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

    public String addData(Student student){
        String result;
        try (Statement statement = connection.createStatement()){
            statement.execute(String.format("INSERT INTO students ('id', 'name', 'surname', 'birthday', 'test_result', 'diff_result', 'exam_result')" +
                    " VALUES (%d, '%s', '%s', '%s', %d, %d, %d);", student.getId(), student.getName(), student.getSurname(), student.getBirthday(),
                    student.getTestResult()? 1 : 0, student.getDiffTestResult(), student.getExamResult()));
            result = "Информация о студенте успешно добавлена!";
        }
        catch (SQLException exception){
            result = exception.getMessage();
        }

        return result;
    }

    public String removeData(int id){
        String result;
        try (Statement statement = connection.createStatement()){
            ResultSet executionResult = statement.executeQuery(String.format("SELECT id FROM students WHERE id = %d;", id));
            if (executionResult.next()){
                statement.execute(String.format("DELETE FROM students WHERE id = %d;", id));
                result = "Информация о студенте успешно удалена!";
            }
            else{
                result = "Информация о студенте не найдена!";
            }
        }
        catch(SQLException exception){
            result = exception.getMessage();
        }

        return result;
    }

    public void setSearchFilter(byte type, String key){
        switch (type) {
            case 1 -> data.stream().filter(student -> Integer.parseInt(key) == student.getId());
            case 2 -> data.stream().filter(student -> Byte.parseByte(key) == (byte) ((student.getTestResult()) ? 1 : 0));
            case 3 -> data.stream().filter(student -> Byte.parseByte(key) == student.getDiffTestResult());
            case 4 -> data.stream().filter(student -> Byte.parseByte(key) == student.getExamResult());
            case 5 -> data.stream().filter(student -> Byte.parseByte(key) == (byte) ((student.hasAttestation()) ? 1 : 0));
            case 6 -> data.stream().filter(student -> key.equalsIgnoreCase(student.getName()));
            case 7 -> data.stream().filter(student -> key.equalsIgnoreCase(student.getSurname()));
            case 8 -> data.stream().filter(student -> key.equals(student.getBirthday().substring(6, 4)));
        }
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
