// 필요한 라이브러리 임포트
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    // 정점 수(V), 간선 수(E), 시작점(S), 도착점(D)
    // dist: 시작점으로부터 각 정점까지의 최단 거리
    // used: 각 간선의 사용 여부(최단 경로에 포함되면 1로 표시)
    // INF: 무한대를 의미하는 큰 값
    static int V, E, S, D, dist[], used[], INF = 987654321;
    
    // e: 정방향 그래프 (각 정점에서 나가는 간선 정보 저장)
    // re: 역방향 그래프 (각 정점에 도착하는 간선 정보 저장)
    // 각 간선 정보는 int 배열 {다음 정점, 가중치, 간선의 고유 번호}로 구성
    static ArrayList<ArrayList<int[]>> e, re;
    
    // 다익스트라 알고리즘에 사용할 우선순위 큐
    static PriorityQueue<int[]> pq;
    
    // BFS에 사용할 큐
    static Queue<Integer> q;

    public static void main(String[] args) throws IOException {
        // 입력 처리를 위한 BufferedReader 객체 생성
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        // 출력 결과를 누적해서 저장할 StringBuilder 객체
        StringBuilder sb = new StringBuilder();

        // 여러 테스트 케이스 처리 (종료 조건: V와 E가 0인 경우)
        while (true) {
            st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken()); // 정점의 개수 입력
            E = Integer.parseInt(st.nextToken()); // 간선의 개수 입력
            // 종료 조건: 입력으로 0 0이 들어오면 결과 출력 후 프로그램 종료
            if (V == 0 && E == 0) {
                System.out.println(sb.toString());
                return;
            }
            
            // 정방향 그래프와 역방향 그래프를 정점 수에 맞게 초기화
            e = new ArrayList<ArrayList<int[]>>(V);
            re = new ArrayList<ArrayList<int[]>>(V);
            // 최단 거리 배열 초기화
            dist = new int[V];
            // 간선 사용 여부 배열 초기화 (모든 간선은 처음에 사용 가능한 상태)
            used = new int[E];
            
            // 각 정점마다 빈 리스트를 생성하여 그래프 구성
            for (int i = 0; i < V; i++) {
                e.add(new ArrayList<int[]>());
                re.add(new ArrayList<int[]>());
            }
            
            // 시작점 S와 도착점 D 입력
            st = new StringTokenizer(br.readLine());
            S = Integer.parseInt(st.nextToken());
            D = Integer.parseInt(st.nextToken());
            
            int a, b, c;
            // 간선 정보 입력: a -> b 로 가는 간선의 가중치 c
            // 각 간선에는 고유 번호(i)를 부여하여 나중에 식별
            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                a = Integer.parseInt(st.nextToken());
                b = Integer.parseInt(st.nextToken());
                c = Integer.parseInt(st.nextToken());
                // 정방향 그래프에 간선 정보 추가: {도착 정점, 가중치, 간선 번호}
                e.get(a).add(new int[] {b, c, i});
                // 역방향 그래프에 간선 정보 추가: {출발 정점, 가중치, 간선 번호}
                re.get(b).add(new int[] {a, c, i});
            }
            
            // 첫 번째 다익스트라 알고리즘 수행: 시작점 S에서 각 정점까지의 최단 경로 계산
            dijkstra(S);
            // BFS를 통해 최단 경로에 포함되는 간선들을 찾아 제거(mark)
            bfs(D);
            // 제거된 간선을 제외하고 다시 다익스트라 알고리즘 수행하여 "거의 최단 경로" 계산
            dijkstra(S);
            
            // 도착점 D까지의 경로가 존재하지 않으면 -1 출력, 있으면 최단 거리 출력
            if (dist[D] == INF) {
                sb.append("-1\n");
            } else {
                sb.append(dist[D] + "\n");
            }
        }
    }
    
    /**
     * 역방향 그래프를 이용하여 BFS 수행
     * - 도착점 D부터 시작하여 최단 경로에 포함되는 간선을 찾아 used 배열에 1로 표시
     * - dist 배열을 이용해 역추적: 만약 dist[현재 정점] == dist[이전 정점] + 간선 가중치이면,
     *   해당 간선은 최단 경로에 포함된 것으로 간주
     */
    static void bfs(int D) {
        q = new LinkedList<Integer>();
        // 도착점 D에서 역추적 시작
        q.add(D);
        int el, rnext, rw, ri;
        while (!q.isEmpty()) {
            el = q.poll();
            // 현재 정점 el에 도착하는 모든 간선을 역방향 그래프에서 탐색
            for (int i = 0; i < re.get(el).size(); i++) {
                // rnext: 현재 간선의 출발 정점
                rnext = re.get(el).get(i)[0];
                // rw: 현재 간선의 가중치
                rw = re.get(el).get(i)[1];
                // ri: 현재 간선의 고유 번호
                ri = re.get(el).get(i)[2];
                // 이미 제거한 간선이면 건너뛰기
                if (used[ri] == 1) {
                    continue;
                }
                // 최단 경로 여부 판단: 만약 현재 정점까지의 거리와 이전 정점까지의 거리 + 간선 가중치가 같다면
                if (dist[el] == dist[rnext] + rw) {
                    used[ri] = 1; // 해당 간선은 최단 경로에 포함되므로 제거(mark)
                    q.add(rnext); // 이전 정점으로 이동하여 추가 간선 탐색
                }
            }
        }
    }
    
    /**
     * 다익스트라 알고리즘 수행
     * - 시작점 S로부터 각 정점까지의 최단 경로 계산
     * - used 배열에 표시된 간선(최단 경로에 포함된 간선)은 경로 계산에서 제외
     */
    static void dijkstra(int S) {
        // 모든 정점의 최단 거리를 무한대(INF)로 초기화
        for (int i = 0; i < V; i++) {
            dist[i] = INF;
        }
        // 우선순위 큐 초기화 (가중치 오름차순 정렬)
        pq = new PriorityQueue<int[]>((o1, o2) -> {
            return o1[1] - o2[1];
        });
        // 시작점 S의 최단 거리는 0
        dist[S] = 0;
        pq.add(new int[] {S, 0});
        int el, w, next, cost, use, cur[];
        
        // 우선순위 큐가 빌 때까지 반복
        while (!pq.isEmpty()) {
            cur = pq.poll();
            el = cur[0]; // 현재 정점
            w = cur[1];  // 현재까지 누적 가중치
            // 현재 정점에서 나가는 모든 간선 탐색
            for (int i = 0; i < e.get(el).size(); i++) {
                // 다음 정점 정보 및 간선 정보 가져오기
                next = e.get(el).get(i)[0];      // 다음 정점
                cost = e.get(el).get(i)[1];        // 간선 가중치
                use = used[e.get(el).get(i)[2]];   // 간선 사용 여부 (제거된 간선이면 1)
                // 만약 해당 간선이 제거되지 않았고, 새로운 경로의 거리가 기존보다 짧으면 갱신
                if (use == 0 && dist[next] > w + cost) {
                    dist[next] = w + cost;
                    pq.add(new int[] {next, w + cost});
                }
            }
        }
    }
}
