package Logic;

import org.sqlite.JDBC;
import java.sql.*;
import java.util.ArrayList;

//БД
public class DataBase {
    private static final String CONNECTION_ADDRESS_MASK = "jdbc:sqlite:./";
    private static final String TABLE_HEAD = "|    ID    |       Имя       |       Фамилия       |       Дата рождения       |" +
                                            "       Наличие зачета       |       Оценка за дифф. зачет       |      Оценка за экзамен       |\n";
    private Connection connection;
    private ArrayList<Student> data;

    public DataBase(){
        data = new ArrayList<>();
        connection = null;
    }

    public ArrayList<Student> getData(){
        return data;
    }

    public static String getTableHead() {
        return TABLE_HEAD;
    }

    //Подключение к БД
    public String connect(String name){
        String result;
        try{
            DriverManager.registerDriver(new JDBC());
            connection = DriverManager.getConnection(CONNECTION_ADDRESS_MASK + name + ".db");
            create();
            result = "База данных успешно открыта!";
        }
        catch (SQLException exception){
            result = exception.getMessage();
        }

        return result;
    }

    //Создание таблицы в БД
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

    //Извлечение всех записей из БД
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

    //Добавление в БД
    public String addData(Student student){
        String result;
        try (Statement statement = connection.createStatement()){
            statement.execute(String.format("INSERT INTO students ('id', 'name', 'surname', 'birthday', 'test_result', 'diff_result', 'exam_result')" +
                    " VALUES (%d, '%s', '%s', '%s', %d, %d, %d);", student.getId(), student.getName(), student.getSurname(), student.getBirthday(),
                    student.getTestResult()? 1 : 0, student.getDiffTestResult(), student.getExamResult()));
            result = String.format("Информация о студенте успешно добавлена под номером %d!", student.getId());
        }
        catch (SQLException exception){
            result = exception.getMessage();
        }

        return result;
    }

    //Удаление из БД
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

    //Закрытие БД
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
