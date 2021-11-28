package Logic;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Predicate;

//Группа
public class Group {
    private ArrayList<Student> students;
    private ArrayList<Predicate<Student>> filters;

    public Group(){
        students = new ArrayList<>();
        filters = new ArrayList<>();
    }

    public void fillGroup(ArrayList<Student> students) {
        this.students.addAll(students);
    }

    public ArrayList<Student> getStudents()
    {
        return students;
    }

    //Установка фильтров поиска по списку группы
    public String setSearchFilter(byte type, String key){
        String result;
        try{
            switch (type) {
                case 1 -> filters.add((student -> Integer.parseInt(key) == student.getId()));
                case 2 -> filters.add((student -> Byte.parseByte(key) == (byte) ((student.getTestResult()) ? 1 : 0)));
                case 3 -> filters.add((student -> Byte.parseByte(key) == student.getDiffTestResult()));
                case 4 -> filters.add((student -> Byte.parseByte(key) == student.getExamResult()));
                case 5 -> filters.add((student -> Byte.parseByte(key) == (byte) ((student.hasAttestation()) ? 1 : 0)));
                case 6 -> filters.add((student -> key.equalsIgnoreCase(student.getName())));
                case 7 -> filters.add((student -> key.equalsIgnoreCase(student.getSurname())));
                case 8 -> filters.add((student -> key.equals(student.getBirthday().substring(6))));
                case 0 -> {
                    filters.clear();
                    filters.add(Objects::nonNull);
                }
            }
            result = "Фильтр успешно применен!";
        }
        catch (NumberFormatException exception){
            result = "Неверное значение для фильтра!";
        }

        return result;
    }

    //Вывод списка группы с учетом выбранных фильтров
    public void print(){
        Predicate<Student> totalFilter = filters.stream().reduce(student -> true, Predicate::and);
        students.stream().filter(totalFilter).forEach(System.out::println);
    }

    //Проведение аттестационных работ у группы
    public void examineStudents(){
        Test test = new Test((byte)10);
        DiffTest diffTest = new DiffTest((byte)10);
        Exam exam = new Exam((byte)20);
        for (Student student : students){
            test.calculateResult(student, test.startWork());
            diffTest.calculateResult(student, diffTest.startWork());
            exam.calculateResult(student, exam.startWork());
        }
    }
}
