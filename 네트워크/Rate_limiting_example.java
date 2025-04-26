import java.util.LinkedList;
import java.util.Queue;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleRateLimiter {
    public static void main(String[] args) throws InterruptedException {
        Queue<Long> requestTimes = new LinkedList<>();

        final int MAX_REQUESTS = 5;
        final long TIME_WINDOW_MS = 1000;

        int requestCount = 20;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (requestCount-- > 0) {
            long now = System.currentTimeMillis();

            while (!requestTimes.isEmpty() && now - requestTimes.peek() > TIME_WINDOW_MS) {
                requestTimes.poll();
            }

            String formattedNow = formatter.format(new Date(now));

            if (requestTimes.size() < MAX_REQUESTS) {
                requestTimes.add(now);
                System.out.println("요청 허용됨 : " + formattedNow);
            } else {
                System.out.println("요청 거부됨 : " + formattedNow);
            }

            Thread.sleep(200);
        }
    }
}
