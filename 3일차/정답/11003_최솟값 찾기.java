import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {

    static int N, L;
    static int[] arr;
    static Deque<Integer> dq;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        dq = new LinkedList<Integer>();

        for (int i = 0; i < N; i++) {

            while (!dq.isEmpty() && i-L >= dq.peekFirst()) {
                dq.pollFirst();
            }
            while (!dq.isEmpty() && arr[i] <= arr[dq.peekLast()]) {
                dq.pollLast();
            }

            dq.addLast(i);
            sb.append(arr[dq.peekFirst()] + " ");
        }
        System.out.println(sb.toString());
    }
}
