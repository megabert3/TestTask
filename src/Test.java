
public class Test {

    public static void main(String[] arg) {

        String str = "[3[x]y]";

        Test test = new Test();
        test.parseString(str);

    }

    public void parseString(String str) {
        char[] strArr = str.toCharArray();
        int startIndex = 0;
        int endIndex = 0;

        for (int i = 0 ; i < strArr.length; i++) {

            if (strArr[i] == '[') {
                startIndex = i;

                for (int j = startIndex; j < strArr.length; j++) {

                    if (strArr[j] == ']') {
                        endIndex = j;

                        int offset = 1;

                        while (startIndex - offset >= 0) {

                            if (strArr[startIndex - offset] >= '0' && strArr[startIndex - offset] <= '9') {
                                offset++;
                            } else {
                                break;
                            }
                        }

                        int amount = Integer.parseInt(str.substring(startIndex - offset + 1, startIndex));

                        repeatString(str.substring(startIndex + 1, endIndex), amount);
                        i = endIndex;
                        break;

                    } else if (strArr[j] == '[') {
                        //Количество скобок
                        int count = 2;
                        endIndex = j + 1;

                        while (count != 0) {

                            if (strArr[endIndex] == ']') {
                                count--;
                            } else if (strArr[endIndex] == '[') {
                                count++;
                            }

                            endIndex++;
                        }

                        parseString(str.substring(i + 1, endIndex));
                    }
                }

            } else if (strArr[i] >= 'A' && strArr[i] <= 'z') {
                System.out.print(strArr[i]);
            }
        }
    }

    public void repeatString(String str, int amount) {
        for (int i = 0; i < amount; i++) {
            System.out.print(str);
        }
    }
}
