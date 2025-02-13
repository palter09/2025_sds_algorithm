import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    // 전역 변수 선언
    static int W, H, G, E, map[][], dist[][], INF = Integer.MAX_VALUE;
    // 간선 정보를 저장할 리스트
    // 각 간선은 {출발 y, 출발 x, 도착 y, 도착 x, 이동 시간} 형태의 int 배열로 저장됨
    static ArrayList<int[]> e;
    // 상하좌우 이동을 위한 방향 배열 (y축, x축)
    static int[] dy = new int[] {1, -1, 0, 0};
    static int[] dx = new int[] {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 테스트 케이스들을 처리하는 무한 루프
        while (true) {
            // 첫 줄에서 격자의 너비(W)와 높이(H)를 입력받음
            st = new StringTokenizer(br.readLine());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            // W와 H가 0인 경우 종료 조건
            if (W == 0 && H == 0) {
                return;
            }

            // G: 묘비(gravestones)의 개수를 입력받음
            G = Integer.parseInt(br.readLine());
            // map 배열 초기화
            // 0: 빈 칸, -1: 묘비, 1: 유령 구멍(ghost hole)의 시작 지점
            map = new int[H][W];
            // 각 칸까지의 최소 이동 시간을 저장할 dist 배열 초기화 (모두 INF로 설정)
            dist = new int[H][W];
            // 간선 정보를 저장할 리스트 초기화
            e = new ArrayList<int[]>();

            int x1, y1, x2, y2, t;

            // 묘비의 위치를 입력받아 map에 표시
            for (int i = 0; i < G; i++) {
                st = new StringTokenizer(br.readLine());
                x1 = Integer.parseInt(st.nextToken());
                y1 = Integer.parseInt(st.nextToken());
                // 해당 좌표에 묘비가 있음을 -1로 표시
                map[y1][x1] = -1;
            }

            // E: 유령 구멍(ghost hole)의 개수를 입력받음
            E = Integer.parseInt(br.readLine());
            // 유령 구멍의 정보를 입력받음
            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                x1 = Integer.parseInt(st.nextToken());
                y1 = Integer.parseInt(st.nextToken());
                x2 = Integer.parseInt(st.nextToken());
                y2 = Integer.parseInt(st.nextToken());
                t = Integer.parseInt(st.nextToken());
                // 유령 구멍의 시작 지점을 1로 표시 (일반 이동 불가)
                map[y1][x1] = 1;
                // 유령 구멍은 지정된 위치로 순간이동하며, 이동 시간 t가 소요됨
                e.add(new int[] {y1, x1, y2, x2, t});
            }

            int ni, nj;
            // 빈 칸에서 인접 칸으로의 일반 이동(상하좌우)에 대한 간선 정보를 추가
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    // dist 배열의 초기값을 INF로 설정
                    dist[i][j] = INF;
                    // 도착 지점 (H-1, W-1)에서는 이동하지 않으므로 건너뜀
                    if (i == (H - 1) && j == (W - 1)) {
                        continue;
                    }
                    // 현재 칸이 빈 칸이 아니라면(묘비나 유령 구멍이면) 일반 이동 불가능하므로 건너뜀
                    if (map[i][j] != 0) {
                        continue;
                    }
                    // 4방향(상하좌우) 이동에 대해 처리
                    for (int d = 0; d < 4; d++) {
                        ni = i + dy[d];
                        nj = j + dx[d];
                        // 이동 후 좌표가 격자 내에 있고, 도착 칸에 묘비가 없는 경우에만 이동 가능
                        if (ni >= 0 && nj >= 0 && ni < H && nj < W && map[ni][nj] != -1) {
                            // 일반 이동은 1초 소요되므로 간선 정보를 추가 (이동 시간 = 1)
                            e.add(new int[] {i, j, ni, nj, 1});
                        }
                    }
                }
            }

            // 시작 지점 (0,0)은 이동 시간 0으로 초기화
            dist[0][0] = 0;
            // Bellman-Ford 알고리즘을 사용하여 최단 경로 탐색
            // (H*W - G)번 반복 (최대 노드 수만큼 relax)
            for (int i = 0; i < (H * W) - G; i++) {
                // 모든 간선에 대해 relax 수행
                for (int j = 0; j < e.size(); j++) {
                    y1 = e.get(j)[0];
                    x1 = e.get(j)[1];
                    y2 = e.get(j)[2];
                    x2 = e.get(j)[3];
                    t = e.get(j)[4];
                    // 출발 지점의 값이 아직 INF이면 이동 불가능한 상태이므로 건너뜀
                    if (dist[y1][x1] == INF) {
                        continue;
                    }
                    // 현재 간선을 통해 도착 지점까지의 이동 시간을 갱신
                    dist[y2][x2] = Math.min(dist[y2][x2], dist[y1][x1] + t);
                }
            }

            // 음의 사이클 존재 여부를 확인하기 위한 flag
            boolean flag = false;
            // 모든 간선을 다시 검사하여 relax가 가능한지 확인
            for (int j = 0; j < e.size(); j++) {
                y1 = e.get(j)[0];
                x1 = e.get(j)[1];
                y2 = e.get(j)[2];
                x2 = e.get(j)[3];
                t = e.get(j)[4];
                // 출발 지점이 방문되지 않은 경우 건너뜀
                if (dist[y1][x1] == INF) {
                    continue;
                }
                // 여전히 relax가 가능하면 음의 사이클이 존재하는 것임
                if (dist[y2][x2] > dist[y1][x1] + t) {
                    flag = true;
                }
            }

            // 음의 사이클(시간 역행)이 발견되면 "Never" 출력
            if (flag) {
                System.out.println("Never");
                continue;
            }
            // 도착 지점에 도달할 수 없는 경우 "Impossible" 출력
            if (dist[H - 1][W - 1] == INF) {
                System.out.println("Impossible");
                continue;
            }
            // 도착 지점까지의 최소 이동 시간을 출력
            System.out.println(dist[H - 1][W - 1]);
        }
    }
}
