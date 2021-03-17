package ru.halimov;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestTaskStringParser {
    //Результирующие данные
    StringBuilder result;

    public static void main(String[] arg) {

        //Test
        //String str = "4[3[x]y]k12[abc]k";

        TestTaskStringParser testTaskStringParser = new TestTaskStringParser();

        for (String line : arg) {
            System.out.println(testTaskStringParser.parseString(line));
        }
    }

    public String parseString(String str) {

        if (!validString(str)) {
            System.out.println("Неверный формат строки");
            return "";
        }

        result = new StringBuilder("");

        char[] strArr = str.toCharArray();
        int startIndex;
        int endIndex;

        for (int i = 0 ; i < strArr.length; i++) {

            //Если найдена первая скобка, то получаю количетво повторений
            if (strArr[i] == '[') {
                startIndex = i;

                int amountRepeat;
                int offset = 1;

                //Получаю количество числовых знаков
                while (startIndex - offset >= 0) {

                    if (strArr[startIndex - offset] >= '0' && strArr[startIndex - offset] <= '9') {
                        offset++;
                    } else {
                        break;
                    }
                }

                amountRepeat = Integer.parseInt(str.substring(startIndex - offset + 1, startIndex));

                //После ищу закрывающую скобку
                for (int j = startIndex + 1; j < strArr.length; j++) {

                    //Если следующая закрывающая
                    if (strArr[j] == ']') {
                        endIndex = j;

                        repeatString(str.substring(startIndex + 1, endIndex), amountRepeat);

                        //Смещаю итератор на значение не распарсенной строки
                        i = endIndex;
                        break;

                        //Если следующая опять открывающая, значит есть вложенные значения
                    } else if (strArr[j] == '[') {

                        //Количество скобок
                        int count = 2;
                        int localIndex = j + 1;

                        //ищу крайниюю скобку соответствующую первой
                        while (count != 0) {

                            if (strArr[localIndex] == ']') {
                                count--;

                            } else if (strArr[localIndex] == '[') {
                                count++;
                            }

                            localIndex++;
                        }

                        //Далее с помощью рекурсии получаю значение самых глубоких вложений
                        endIndex = localIndex;

                        String innerString = str.substring(startIndex + 1, endIndex - 1);
                        repeatString(parseString(innerString), amountRepeat - 1);
                        //Смещаю индекс прочитанных данных
                        i = endIndex - 1;
                        break;
                    }
                }

                //Если символ, то просто добавляю к остальным
            } else if (strArr[i] < '0' || strArr[i] > '9') {
                result.append(strArr[i]);
            }
        }
        return result.toString();
    }

    private void repeatString(String str, int amount) {
        for (int i = 0; i < amount; i++) {
            result.append(str);
        }
    }

    /**
     * Проверяет входную строку для парсинга на валидность
     * @param str
     * @return
     */
    private boolean validString(String str) {
        if (str == null || str.isEmpty()) return false;

        //Количество открывающихся скобок должно быть равно количество закрывающихся
        int amount = 0;
        char[] strChar = str.toCharArray();

        Matcher matcher = Pattern.compile("[^A-Za-z0-9\\[\\]]").matcher(str);

        if (matcher.find()) return false;

        if (strChar[0] == '[') return false;

        for (int i = 0; i < strChar.length; i++) {

            if (strChar[i] == '[') {

                //Если перед открывающей скобкой не цифра
                if (!Character.isDigit(strChar[i - 1])) return false;

                amount++;

            } else if (strChar[i] == ']') {
                amount--;
            }
        }

        return amount == 0;
    }
}
