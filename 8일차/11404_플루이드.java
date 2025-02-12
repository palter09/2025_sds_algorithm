import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 빠른 입출력을 위한 BufferedReader, BufferedWriter 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        // 도시의 개수와 버스의 개수 입력
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());
        
        // INF 값은 문제에서 주어진 비용의 최대값보다 크게 잡아줍니다.
        final int INF = 1000000000;
        
        // 비용을 저장할 2차원 배열 (인덱스를 1부터 사용하기 위해 n+1 크기)
        int[][] cost = new int[n + 1][n + 1];
        
        // 초기값 세팅: 자기 자신으로 가는 비용은 0, 나머지는 INF로 초기화
        for (int i = 1; i <= n; i++) {
            Arrays.fill(cost[i], INF);
            cost[i][i] = 0;
        }
        
        // 각 버스 노선의 정보를 입력받아 최소 비용 갱신
        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            
            // 여러 버스 노선이 있을 수 있으므로 최소값을 저장합니다.
            cost[start][end] = Math.min(cost[start][end], c);
        }
        
        // 플로이드-워셜 알고리즘을 이용한 모든 쌍 최단경로 계산
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (cost[i][j] > cost[i][k] + cost[k][j]) {
                        cost[i][j] = cost[i][k] + cost[k][j];
                    }
                }
            }
        }
        
        // 결과 출력: 도달할 수 없는 경우 0을 출력
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                bw.write((cost[i][j] == INF ? 0 : cost[i][j]) + " ");
            }
            bw.newLine();
        }
        
        bw.flush();
        bw.close();
        br.close();
    }
}