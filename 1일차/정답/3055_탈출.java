import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static char[][] map;
    static int[][] water;
    static int[][] animal;
    static Queue<int[]> waterQueue;
    static Queue<int[]> animalQueue;
    static int R, C;

    static int[] dy = {1, -1, 0, 0};
    static int[] dx = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];
        water= new int[R][C];
        animal = new int[R][C];
        waterQueue = new LinkedList<int[]>();
        animalQueue = new LinkedList<int[]>();
        for (int r = 0; r < R; r++) {
            String s = br.readLine();
            for (int c = 0; c < C; c++) {
                map[r][c] = s.charAt(c);
                water[r][c] = -1;
                animal[r][c] = -1;
                if (map[r][c] == '*') {
                    waterQueue.add(new int[] {r, c});
                    water[r][c] = 0;
                }
                if (map[r][c] == 'S') {
                    animalQueue.add(new int[] {r, c});
                    animal[r][c] = 0;
                }
            }
        }
        int ret = run();
        System.out.println(ret == -1 ? "KAKTUS" : ret);

    }
    static int run() {
        while(!waterQueue.isEmpty()) {
            int[] item = waterQueue.poll();
            for (int d = 0; d < 4; d++) {
                int ny = item[0] + dy[d];
                int nx = item[1] + dx[d];
                int nt = water[item[0]][item[1]] + 1;

                if (ny >= 0 && nx >= 0 && ny < R && nx < C && map[ny][nx] == '.' && water[ny][nx] == -1) {
                    water[ny][nx] = nt;
                    waterQueue.add(new int[] {ny, nx});
                }
            }
        }

        while(!animalQueue.isEmpty()) {
            int[] item = animalQueue.poll();
            for (int d = 0; d < 4; d++) {
                int ny = item[0] + dy[d];
                int nx = item[1] + dx[d];
                int nt = animal[item[0]][item[1]] + 1;

                if (ny >= 0 && nx >= 0 && ny < R && nx < C) {
                    if (map[ny][nx] == 'D') {
                        return nt;
                    }

                    if (map[ny][nx] == '.'  && animal[ny][nx] == -1 
                            && (water[ny][nx] > nt || water[ny][nx] == -1)) {
                        animal[ny][nx] = nt;
                        animalQueue.add(new int[] {ny, nx});
                    }
                } 
            }
        }
        return -1;
    }
}
