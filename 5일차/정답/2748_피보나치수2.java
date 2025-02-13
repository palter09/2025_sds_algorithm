import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int V;
    static long d[];

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        V = Integer.parseInt(st.nextToken());
        d = new long[V+1];
        d[0] = 0;
        d[1] = 1;
        for (int i=2; i<=V; i++) {
            d[i] = d[i-1] + d[i-2];
        }
        System.out.println(d[V]);
    }
}
