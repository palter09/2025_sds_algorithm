import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int A, B, C, D;

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        C = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());

        int top = A*D+B*C;
        int bot = B*D;
        int g = gcd(top, bot);
        top /= g;
        bot /= g;
        System.out.println(top + " " + bot);
    }
    static int gcd(int a, int b) {
        while (b!=0) {
            int r = a%b;
            a = b;
            b = r;
        }
        return a;
    }
    static int lcm(int a, int b) {
        return a * b / gcd(a,b);
    }
}
