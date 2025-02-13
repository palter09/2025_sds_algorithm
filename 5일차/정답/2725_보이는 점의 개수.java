import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {

    static int T, N, euler[];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        euler = new int[1111];
        for (int i=1; i<=1000; i++) {
            euler[i] = i;
        }
        for (int i=2; i<=1000; i++) {
            if (euler[i] == i) {
                for (int j=i; j<=1000; j+=i) {
                    euler[j] = euler[j]-euler[j]/i;
                }
            }
        }
        T = Integer.parseInt(br.readLine());
        for (int t=0; t<T; t++) {
            N = Integer.parseInt(br.readLine());
            int ans = 3;
            for (int i=2; i<=N; i++) {
                ans += euler[i]*2;
            }
            sb.append(ans+"\n");
        }
        System.out.println(sb.toString());
    }
}
