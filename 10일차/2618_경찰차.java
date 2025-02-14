import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int N, W;
    // 사건 좌표를 1번부터 W번까지 저장 (인덱스 1~W)
    static int[][] events;
    // dp[i][j] : 경찰차1이 마지막으로 처리한 사건 번호가 i, 경찰차2가 마지막으로 처리한 사건 번호가 j일 때 남은 사건들을 처리하는 최소 이동 거리
    // i, j가 0이면 해당 경찰차의 시작 위치를 의미 (경찰차1: (1,1), 경찰차2: (N,N))
    static int[][] dp;
    static final int INF = 1000000000;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken());
        
        events = new int[W+1][2];  // 1번부터 W번 사건
        for (int i = 1; i <= W; i++) {
            st = new StringTokenizer(br.readLine());
            events[i][0] = Integer.parseInt(st.nextToken());
            events[i][1] = Integer.parseInt(st.nextToken());
        }
        
        // dp 테이블 초기화 (-1: 미계산)
        dp = new int[W+1][W+1];
        for (int i = 0; i <= W; i++) {
            Arrays.fill(dp[i], -1);
        }
        
        // 두 경찰차의 상태를 (0, 0) (즉, 아직 아무 사건도 처리하지 않은 상태)에서 최소 이동거리 계산
        int answer = solve(0, 0);
        System.out.println(answer);
        
        // reconstruct: 각 사건을 어느 경찰차가 처리하는지 복원
        StringBuilder sb = new StringBuilder();
        int pos1 = 0, pos2 = 0;
        for (int step = 1; step <= W; step++) {
            int next = Math.max(pos1, pos2) + 1;
            // 경찰차1의 현재 위치에서 next 사건까지의 거리
            int dist1 = (pos1 == 0 ? Math.abs(1 - events[next][0]) + Math.abs(1 - events[next][1])
                                     : Math.abs(events[pos1][0] - events[next][0]) + Math.abs(events[pos1][1] - events[next][1]));
            // 경찰차2의 현재 위치에서 next 사건까지의 거리
            int dist2 = (pos2 == 0 ? Math.abs(N - events[next][0]) + Math.abs(N - events[next][1])
                                     : Math.abs(events[pos2][0] - events[next][0]) + Math.abs(events[pos2][1] - events[next][1]));
            
            // dp[pos1][pos2] = min( dp[next][pos2] + dist1, dp[pos1][next] + dist2 )
            if (dp[pos1][pos2] == dp[next][pos2] + dist1) {
                sb.append("1").append("\n");
                pos1 = next;
            } else {
                sb.append("2").append("\n");
                pos2 = next;
            }
        }
        System.out.print(sb);
    }
    
    // 재귀 DP: pos1, pos2는 각 경찰차가 마지막으로 처리한 사건 번호 (0이면 시작 위치)
    static int solve(int pos1, int pos2) {
        int next = Math.max(pos1, pos2) + 1;
        // 모든 사건을 처리했다면
        if (next > W) {
            return 0;
        }
        
        if (dp[pos1][pos2] != -1) {
            return dp[pos1][pos2];
        }
        
        // 경찰차1이 다음 사건을 처리할 경우 이동 거리
        int dist1 = (pos1 == 0 ? Math.abs(1 - events[next][0]) + Math.abs(1 - events[next][1])
                               : Math.abs(events[pos1][0] - events[next][0]) + Math.abs(events[pos1][1] - events[next][1]));
        int option1 = solve(next, pos2) + dist1;
        
        // 경찰차2가 다음 사건을 처리할 경우 이동 거리
        int dist2 = (pos2 == 0 ? Math.abs(N - events[next][0]) + Math.abs(N - events[next][1])
                               : Math.abs(events[pos2][0] - events[next][0]) + Math.abs(events[pos2][1] - events[next][1]));
        int option2 = solve(pos1, next) + dist2;
        
        dp[pos1][pos2] = Math.min(option1, option2);
        return dp[pos1][pos2];
    }
}