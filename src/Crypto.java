import java.util.ArrayList;
import java.util.List;

public class Crypto {
    public static void main(String[] args) {
        Crypto crypto = new Crypto();
        String text = "Аа";
        System.out.println(text);
        List<String> result = crypto.encoder(text);
        String key = result.get(0);
        String encodeText = result.get(1);

        System.out.println(key + " key");
        System.out.println(encodeText);

        String decodeText = crypto.decoder(encodeText, key);
        System.out.println(decodeText);
    }


    public List<String> encoder (String text) {
        List<String> result = new ArrayList<>();

        //получаем рандомный ключ
        String key = String.valueOf((int) (Math.random() * 99999));
        result.add(key);

        //находим сумму байтов всех символов ключа
        char[] set = key.toCharArray();
        int sum = 0;
        for (int i = 0; i < set.length ; i++) {
            sum += set[i];
        }

        //видоизменяем ключ
        if (sum%2 == 0) {
            sum /= 2;
            sum += 9;
        }
        else {
            sum *= 3;
            sum -= 5;
        }

        //находим еще раз сумму всех байтов символов ключа
        char[] newset = String.valueOf(sum).toCharArray();
        int newsum = 0;
        for (int i = 0; i < newset.length ; i++) {
            newsum += newset[i];
        }

        //генерируем шифрованный текст
        StringBuilder sb = new StringBuilder();
        char[] textSet = text.toCharArray();
        for (int i = 0; i < text.length() ; i++) {
            sb.append(((long) (textSet[i])) * newsum + 8763);
            sb.append("%");
            sb.append(String.valueOf((int) (Math.random() * 99999)));
            sb.append("%");
            newsum += 127;
        }
        sb.reverse();

        result.add(sb.toString());

        return result;
    }

    public String decoder (String encodeText, String key) {
        String result = "";

        //находим сумму байтов всех символов ключа
        char[] set = key.toCharArray();
        int sum = 0;
        for (int i = 0; i < set.length ; i++) {
            sum += set[i];
        }

        //видоизменяем ключ
        if (sum%2 == 0) {
            sum /= 2;
            sum += 9;
        }
        else {
            sum *= 3;
            sum -= 5;
        }

        //находим еще раз сумму всех байтов символов ключа
        char[] newset = String.valueOf(sum).toCharArray();
        int newsum = 0;
        for (int i = 0; i < newset.length ; i++) {
            newsum += newset[i];
        }

        //дешифруем текст
        StringBuilder sb = new StringBuilder(encodeText);
        sb.reverse();

        String [] tmp = sb.toString().split("%");
        sb = new StringBuilder();

        for (int i = 0; i < tmp.length ; i+=2) {
            char a = (char) ((Integer.parseInt(tmp[i]) - 8763) / newsum);
            sb.append(Character.toString(a));
            newsum+=127;
        }
        result = sb.toString();

        return result;
    }
}
