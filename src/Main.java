import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        int numThread = 1000;
        Thread[] threads = new Thread[numThread];

        for (int i = 0; i < numThread; i++) {
            threads[i] = new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                int count = countRightTurns(route);
                synchronized (sizeToFreq) {
                    sizeToFreq.put(count, sizeToFreq.getOrDefault(count, 0) + 1);
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        printResults();
    }

    private static void printResults() {
        int maxFreq = 0;
        int count = 0;
        System.out.println("Самое частое количество повторений:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (maxFreq < entry.getKey()) {
                maxFreq = entry.getKey();
                count = entry.getValue();
            }
        }
        sizeToFreq.remove(count);
        System.out.println(count + " (встретилось  " + maxFreq + " раз)");

        System.out.println("Другие повторения");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            System.out.println(entry.getValue() + " (встретилось  " + entry.getKey() + " раз)");
        }
    }

    private static int countRightTurns(String route) {
        int count = 0;
        for (char c : route.toCharArray()) {
            if (c == 'R') {
                count++;
            }
        }
        return count;
    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}