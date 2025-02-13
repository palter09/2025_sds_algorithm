import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    // 노드의 개수 N, 간선의 개수 M, 각 노드별 비용을 저장할 배열 c
    static int N, M, c[];
    // 각 노드별 인접 리스트를 저장 (각 간선은 {연결된 노드, 간선 가중치} 형태의 int[]로 저장)
    static ArrayList<ArrayList<int[]>> e;
    // 우선순위 큐 (배열: {현재까지 총 비용, 현재 노드, 현재까지의 최소 비용})
    static PriorityQueue<long[]> pq;
    // d[i][j]: i번 노드에 도달했을 때, j(현재까지의 최소 비용)를 가진 상태에서의 최소 총 비용
    static long d[][];

    public static void main(String[] args) throws IOException {
        // 입력을 위한 BufferedReader 및 첫 줄 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        // 노드의 개수와 간선의 개수를 입력받음
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 인접 리스트 초기화: 1번부터 N번 노드까지 사용 (0번 인덱스는 사용하지 않음)
        e = new ArrayList<ArrayList<int[]>>();
        st = new StringTokenizer(br.readLine());
        c = new int[N+1];
        // 0번 인덱스는 dummy로 비워둠
        e.add(new ArrayList<int[]>());
        for (int i = 1; i <= N; i++) {
            e.add(new ArrayList<int[]>());
            // 각 노드별 비용 입력 (예: 연료 가격, 기타 비용 등 문제에 따라 달라질 수 있음)
            c[i] = Integer.parseInt(st.nextToken());
        }

        // 간선 정보를 입력받고 인접 리스트에 추가 (무방향 그래프로 처리)
        int a, b, w;
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken());
            b = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());
            // a번 노드와 b번 노드를 연결하는 간선 정보 추가 (양쪽에 추가하여 무방향 처리)
            e.get(a).add(new int[] {b, w});
            e.get(b).add(new int[] {a, w});
        }

        // d 배열 초기화
        // d[i][j]: i번 노드에 도달 시, 현재까지의 최소 비용이 j일 때의 총 비용
        // 문제에서 최소 비용은 최대 2500까지 고려함
        d = new long[N+1][2501];
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j <= 2500; j++) {
                d[i][j] = Long.MAX_VALUE; // 무한대로 초기화
            }
        }

        // 우선순위 큐 초기화 (현재까지의 총 비용을 기준으로 오름차순 정렬)
        pq = new PriorityQueue<long[]>((o1, o2) -> {
            return (int)(o1[0] - o2[0]);
        });
        // 시작점 추가: 노드 1, 총 비용 0, 현재까지의 최소 비용은 시작 노드의 비용 c[1]
        pq.add(new long[] {0, 1, c[1]});

        // 답을 저장할 변수
        long ans = 0, total, temp[];
        // 변수 선언: 현재 노드, 간선 가중치(cost), 다음 노드(to), 현재까지의 최소 비용(minCost)
        int node, cost, to, minCost;

        // 다익스트라 알고리즘 수행: 우선순위 큐가 빌 때까지 반복
        while (!pq.isEmpty()) {
            // 우선순위 큐에서 가장 비용이 낮은 상태를 꺼냄
            temp = pq.poll();
            total = temp[0];      // 현재까지 누적 비용
            node = (int)temp[1];  // 현재 노드
            minCost = (int)temp[2];   // 지금까지의 최소 비용 (예를 들어, 연료 가격의 최솟값 등)

            // 도착 노드(N)에 도달했다면, 답을 저장하고 종료
            if (node == N) {
                ans = total;
                break;
            }

            // 현재 노드와 인접한 모든 노드에 대해 탐색
            for (int i = 0; i < e.get(node).size(); i++) {
                // 인접 노드와 간선 가중치 가져오기
                to = e.get(node).get(i)[0];
                cost = e.get(node).get(i)[1];
                // 이동 후 총 비용 = 현재 총 비용 + (간선 가중치 * 현재까지의 최소 비용)
                // 만약 해당 상태로 to 노드에 도달하는 총 비용이 기존보다 작다면 갱신
                if (d[to][minCost] > total + (cost * minCost)) {
                    d[to][minCost] = total + (cost * minCost);
                    // 다음 상태에서의 최소 비용은 현재 상태의 minCost와 도착 노드의 비용 c[to] 중 더 작은 값
                    pq.add(new long[] {d[to][minCost], to, Math.min(minCost, c[to])});
                }
            }
        }
        // 최종적으로 구한 최소 비용 출력
        System.out.println(ans);
    }
}
