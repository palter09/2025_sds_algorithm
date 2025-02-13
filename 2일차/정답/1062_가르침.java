import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    static int N, K;
    static String[] words;
    static int[] visited;
    static int ans;
    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken()) - 5;
        words = new String[N];
        visited = new int[26];
        visited['a' - 'a'] = 1;
        visited['n' - 'a'] = 1;
        visited['t' - 'a'] = 1;
        visited['i' - 'a'] = 1;
        visited['c' - 'a'] = 1;

        for(int i = 0; i < N; i++) {
            words[i] = br.readLine();
        }
        if (K < 0) {
            System.out.println(0);
            return;
        }
        ans = 0;
        bt(0, 0);
        System.out.println(ans);
    }
    static void bt(int cur, int count) {
        if (K == count) {
            int num = 0;
            for(int w = 0; w < N; w++) {
                String word = words[w];
                boolean flag = true;
                for (int j = 4; j < word.length()-4; j++) {
                    if (visited[word.charAt(j) - 'a'] == 0) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    num++;
                }
            }
            ans = Math.max(ans, num);
            return;
        }
        for (int i = cur; i < 26; i++) {
            if (visited[i] == 0) {
                visited[i] = 1;
                bt(i + 1, count + 1);
                visited[i] = 0;
            }
        }
    }
}
