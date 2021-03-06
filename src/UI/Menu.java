package UI;

import Logic.Analyzer;
import Logic.DataBase;
import Logic.Group;
import Logic.Student;
import java.util.Scanner;

//Меню
public class Menu {
    public static void run(){
        String choice;
        DataBase dataBase = new DataBase();
        Group group = new Group();
        do {
            System.out.println("""     
                ***Программа учета учебной группы***
                1. Создать/Открыть группу
                2. Добавить студента
                3. Удалить студента
                4. Напечатать список группы
                5. Получить данные из базы
                6. Установить фильтры поиска по группе
                7. Провести аттестацию
                0. Выход""");
            System.out.print("Введите номер команды: ");

            Scanner in = new Scanner(System.in);
            choice = in.nextLine().trim();
            if (Analyzer.analyzeNum(choice, 0, 7)){
                if (Byte.parseByte(choice) == 1) {
                    System.out.print("Введите название группы: ");
                    String groupName = in.nextLine();
                    System.out.println(dataBase.connect(groupName));
                }
                else if (Byte.parseByte(choice) == 2 && dataBase.getConnection() != null){
                    System.out.println("""                  
                    Заполните информацию о студенте по следующему шаблону:
                    ID (число), имя, фамилия, дата рождения(дд.мм.гггг), наличие зачета(да/нет, зачет/незачет), оценка за дифф зачет (число 2-5), оценка за экзамен (число 2-5)
                    Каждое поле отделяйте друг от друга пробелом
                    """);
                    System.out.print("Ввод: ");
                    String[] studentData = in.nextLine().trim().split("[\\s]+");
                    if (studentData.length == 7){
                        if (Analyzer.analyzeDate(studentData[3]) && Analyzer.analyzeNum(studentData[0], 1, Integer.MAX_VALUE) &&
                                Analyzer.analyzeNum(studentData[5], 2, 5) && Analyzer.analyzeNum(studentData[6], 2, 5))
                        {
                            studentData[4] = (studentData[4].equalsIgnoreCase("да") || studentData[4].equalsIgnoreCase("зачет") ||
                                    studentData[4].equalsIgnoreCase("зачёт"))? "true" : "false";
                            studentData[3] = Analyzer.normalizeDate(studentData[3]);
                            Student student = new Student(Integer.parseInt(studentData[0]), studentData[1], studentData[2], studentData[3],
                                    Boolean.parseBoolean(studentData[4]), Byte.parseByte(studentData[5]), Byte.parseByte(studentData[6]));
                            System.out.println(dataBase.addData(student));
                        }
                        else {
                            System.out.println("Введите информацию согласно шаблону!");
                        }
                    }
                    else{
                        System.out.println("Недостаточно информации для добавления!");
                    }
                }
                else if (Byte.parseByte(choice) == 3 && dataBase.getConnection() != null){
                    System.out.print("Введите ID для удаления (число): ");
                    String idData = in.nextLine();
                    if (Analyzer.analyzeNum(idData, 1, Integer.MAX_VALUE)){
                        System.out.println(dataBase.removeData(Integer.parseInt(idData)));
                    }
                    else{
                        System.out.println("Введите число!");
                    }
                }
                else if (Byte.parseByte(choice) == 4 && dataBase.getConnection() != null){
                    System.out.print(DataBase.getTableHead());
                    group.print();
                }
                else if (Byte.parseByte(choice) == 5 && dataBase.getConnection() != null){
                    System.out.println(dataBase.executeAllData());
                    group.fillGroup(dataBase.getData());
                }
                else if (Byte.parseByte(choice) == 6 && dataBase.getConnection() != null){
                    System.out.println("""
                    Выберите фильтр для установки:
                    1. Поиск по ID
                    2. Группировка по результату зачета
                    3. Группировка по результату дифф. зачета
                    4. Группировка по результату экзамена
                    5. Группировка по наличию аттестации
                    6. Поиск по имени
                    7. Поиск по фамилии
                    8. Группировка по году рождения
                    0. Сбросить фильтры
                    """);
                    System.out.print("Ввод: ");
                    String filterChoice = in.nextLine();
                    if (Analyzer.analyzeNum(filterChoice, 0, 8)){
                        System.out.println("""
                        Введите корректное значение для поиска или группировки
                        При вводе допустимого, но некорректного значения фильтр не сработает
                        При вводе недопустимого значения фильтр не установится
                        Для фильтров 2 и 5 допустимые и корректные значения 1 и 0
                        Для фильтров 1, 3, 4 допустимые и корректные значения 2-5
                        Для сброса фильтров введите любое значение""");
                        System.out.print("Ввод: ");
                        String key = in.nextLine();
                        System.out.println(group.setSearchFilter(Byte.parseByte(filterChoice), key));
                    }
                    else{
                        System.out.println("Несуществующий фильтр!");
                    }
                }
                else if (Byte.parseByte(choice) == 7 && dataBase.getConnection() != null){
                    System.out.println(group.examineStudents());
                }
                else if (Byte.parseByte(choice) == 0){
                    System.out.println(dataBase.close());
                }
                else{
                    System.out.println("Введите существующий номер команды или создайте группу!");
                }
            }
            else{
                System.out.println("Введите числовое значение!");
            }
        } while (!choice.equals("0"));
    }
}
