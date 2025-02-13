import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        int[] left = new int[N];
        int[] right = new int[N];
        left[0] = arr[0];
        right[N-1] = arr[N-1];
        for(int i = 1; i < N; i++) {
            left[i] = gcd(arr[i], left[i-1]);
            right[N-i-1] = gcd(right[N-i], arr[N-i-1]);
        }
        int ans = -1;
        int k = 0;
        for(int i = 0; i < N; i++) {
            int g = 0;
            if (i == 0) {
                g = right[1];
            } else if (i == N-1) {
                g = left[N-2];
            } else {
                g = gcd(left[i-1], right[i+1]);
            }
            if (ans < g && arr[i]%g != 0) {
                k = i;
                ans = g;
            }
        }
        System.out.println(ans == -1 ? "-1" : ans + " " + arr[k]);

    }
    static int gcd(int a, int b) {
        while (b != 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
}
