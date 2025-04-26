import java.util.LinkedList;
import java.util.Queue;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleRateLimiter {
    /**
     * 큐에는 최근 1초 동안 들어온 요청 시간만 저장.
     * 새로운 요청이 오면, 오래된 기록을 빼내고, 현재 큐 크기를 검사.
     * 200ms마다 요청을 시도해서 초당 5개 미만이면 요청을 허용하고, 아니면 거부.
     */
    public static void main(String[] args) throws InterruptedException {
        Queue<Long> requestTimes = new LinkedList<>();

        final int MAX_REQUESTS = 5;
        final long TIME_WINDOW_MS = 1000;

        int requestCount = 20;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (requestCount-- > 0) {
            long now = System.currentTimeMillis();

            // 만약 현재 시간(now) - 가장 오래된 시간이 1초(1000ms)보다 크면  타임윈도우에서 벗어난 요청이니까 큐에서 poll()로 꺼내서 제거.
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
