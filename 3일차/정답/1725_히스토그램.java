import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    static int N, arr[];
    static Stack<Integer> stack;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        arr = new int[N+2];
        for (int i=1; i<=N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        stack = new Stack<Integer>();
        int pointer = 0;
        int ans = 0, cur;
        while (pointer <= N+1) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[pointer]) {
                cur = arr[stack.pop()];
                ans = Math.max(ans, cur*(pointer-stack.peek()-1));
            }
            stack.push(pointer++);
        }
        System.out.println(ans);
    }
}
