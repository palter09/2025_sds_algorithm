import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int prime[], MAX=1000000;
    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        prime = new int[MAX+1];
        for (int i=2; i<=MAX; i++) {
            if (prime[i] == 0) {
                for (int j=i*2; j<=MAX; j+=i) {
                    prime[j] = 1;
                }
            }
        }
        while (true) {
            int n = Integer.parseInt(br.readLine());
            if (n==0) {
                System.out.println(sb.toString());
                return;
            }
            int ans = -1;
            for (int i=3; i<n; i++) {
                if (prime[i]==0 && prime[n-i]==0) {
                    ans = i;
                    break;
                }
            }
            if (ans == -1) {
                sb.append("Goldbach's conjecture is wrong.\n");
            } else {
                sb.append(n+" = "+ans+" + "+(n-ans)+"\n");
            }
        }
    }
}
