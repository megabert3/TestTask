package ru.halimov;

public class Test {

    StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] arg) {

        String str = "4[3[x]y]k12[abc]";

        Test test = new Test();
        System.out.println(test.parseString(str));
    }

    public String parseString(String str) {
        stringBuilder = new StringBuilder("");

        char[] strArr = str.toCharArray();
        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0 ; i < strArr.length; i++) {

            //Если найдена первая скобка, то получаю количетво повторений
            if (strArr[i] == '[') {
                startIndex = i;

                int amountRepeat = 0;
                int offset = 1;

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

                        //Смещаю итератор на значение не распарсеной строки
                        i = endIndex;
                        break;

                        //Значит есть вложенные
                    } else if (strArr[j] == '[') {

                        //Количество скобок
                        int count = 2;
                        int localIndex = j + 1;

                        //Ищу крайниюю скобку соответствующую первой
                        while (count != 0) {

                            if (strArr[localIndex] == ']') {
                                count--;

                            } else if (strArr[localIndex] == '[') {
                                count++;
                            }

                            localIndex++;
                        }

                        endIndex = localIndex;
                        String string1 = str.substring(startIndex + 1, endIndex - 1);
                        repeatString(parseString(string1), amountRepeat - 1);
                        i = endIndex - 1;
                        break;
                    }
                }

            } else if (strArr[i] > '9') {
                stringBuilder.append(strArr[i]);
            }
        }
        return stringBuilder.toString();
    }

    private void repeatString(String str, int amount) {

        for (int i = 0; i < amount; i++) {
            stringBuilder.append(str);
        }
    }
}
