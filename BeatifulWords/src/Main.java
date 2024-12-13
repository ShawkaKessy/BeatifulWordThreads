import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger countLength3 = new AtomicInteger(0);
    private static final AtomicInteger countLength4 = new AtomicInteger(0);
    private static final AtomicInteger countLength5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeThread = new Thread(() -> checkPalindrome(texts));
        Thread sameLettersThread = new Thread(() -> checkSameLetters(texts));
        Thread ascendingOrderThread = new Thread(() -> checkAscendingOrder(texts));

        palindromeThread.start();
        sameLettersThread.start();
        ascendingOrderThread.start();

        palindromeThread.join();
        sameLettersThread.join();
        ascendingOrderThread.join();

        System.out.printf("Красивых слов с длиной 3: %d шт\n", countLength3.get());
        System.out.printf("Красивых слов с длиной 4: %d шт\n", countLength4.get());
        System.out.printf("Красивых слов с длиной 5: %d шт\n", countLength5.get());
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    private static void checkPalindrome(String[] texts) {
        for (String text : texts) {
            if (isPalindrome(text)) {
                incrementCounter(text.length());
            }
        }
    }

    private static boolean isPalindrome(String text) {
        int length = text.length();
        for (int i = 0; i < length / 2; i++) {
            if (text.charAt(i) != text.charAt(length - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    private static void checkSameLetters(String[] texts) {
        for (String text : texts) {
            if (isSameLetters(text)) {
                incrementCounter(text.length());
            }
        }
    }

    private static boolean isSameLetters(String text) {
        char firstChar = text.charAt(0);
        for (char c : text.toCharArray()) {
            if (c != firstChar) {
                return false;
            }
        }
        return true;
    }

    private static void checkAscendingOrder(String[] texts) {
        for (String text : texts) {
            if (isAscendingOrder(text)) {
                incrementCounter(text.length());
            }
        }
    }

    private static boolean isAscendingOrder(String text) {
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) < text.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    private static void incrementCounter(int length) {
        switch (length) {
            case 3 -> countLength3.incrementAndGet();
            case 4 -> countLength4.incrementAndGet();
            case 5 -> countLength5.incrementAndGet();
        }
    }
}
