import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, INF = 3000000;
    static int arr[][], d[][];

    public static void main(String[] args) throws IOException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuffer sb = new StringBuffer();
        
        N = Integer.parseInt(st.nextToken());
        arr = new int[N][N];
        for (int i=0; i<N; i++) {
        	st = new StringTokenizer(br.readLine());
        	for (int j=0; j<N; j++) {
        		arr[i][j] = Integer.parseInt(st.nextToken());
        	}
        }
        
        
        d = new int [N][1<<N];
        System.out.println(tsp(0, 1));
    }
    
    static int tsp(int cur, int visited) {
    	// 종료 조건
    	if(visited == (1<<N)-1) {
    		if (arr[cur][0] == 0) {
    			return INF;
    		}
    		return arr[cur][0];
    	}
    	
    	if (d[cur][visited] != 0) {
    		return d[cur][visited];
    	}
    	
    	int ret = INF;
    	
    	// 도시 방문
    	for (int i=0; i<N; i++) {
    		// 현재 visited 기준으로 가보지 않은 도시 탐색
    		if((visited & (1<<i)) == 0 && arr[cur][i] != 0) {
    			int t = tsp(i, visited | (1<<i)) + arr[cur][i];
    			ret = Math.min(ret,  t);
    		}
    	}
    	
    	d[cur][visited] = ret;
    	return ret;
    }
}

