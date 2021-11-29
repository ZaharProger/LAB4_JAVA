package Logic;

public class Analyzer {
    //Проверка даты
    public static boolean analyzeDate(String data){
        String[] date = data.trim().split("[.]+");
        boolean isCorrect;
        try{
            if (date.length == 3){//Проверка на соответствие формату дд.мм.гггг (при разбиении строки получится 3 элемента)
                //Определение корректности дня в месяце
                byte daysAmount = switch (Byte.parseByte(date[1])) {
                    case 2 -> (byte)29;
                    case 4, 6, 9, 11 -> (byte)30;
                    case 1, 3, 5, 7, 8, 10, 12 -> (byte)31;
                    default -> (byte)-1;
                };
                if (daysAmount == -1){
                    isCorrect = false;
                }
                else isCorrect = Byte.parseByte(date[0]) >= 1 && Byte.parseByte(date[0]) <= daysAmount;
                //Определение корректности года
                Short.parseShort(date[2]);
            }
            else{
                isCorrect = false;
            }
        }
        catch (NumberFormatException exception){
            isCorrect = false;
        }

        return isCorrect;
    }

    //Проверка числовых полей
    public static boolean analyzeNum(String data, int min, int max){
        boolean isCorrect;
        try{
            isCorrect =  Integer.parseInt(data) >= min && Integer.parseInt(data) <= max;
        }
        catch (NumberFormatException exception){
            isCorrect = false;
        }

        return isCorrect;
    }

    //Приведение даты к виду дд.мм.гггг
    public static String normalizeDate(String data){
        String[] date = data.trim().split("[.]+");
        //Установка нулевых разрядов по маске
        if (date[0].length() == 1)
            date[0] = "0" + date[0].charAt(0);
        if (date[1].length() == 1)
            date[1] = "0" + date[1].charAt(0);
        if (date[2].length() == 1)
            date[2] = "000" + date[2].charAt(0);

        return String.join(".", date);
    }
}
