import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int N, P, state, INF = 3000000;
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
        
        String s;
        s = br.readLine();
        String[] str = s.split("");
        
        state = (1<<N);
        
        for (int i=0; i<N; i++) {
        	if (str[i] == "Y") {
        		state |= (1 << i);
        	}
        	else {
        		state &= ~(1 << i);
        	}
        }
        
        st = new StringTokenizer(br.readLine());
        P = Integer.parseInt(st.nextToken());
        
        if (P <= Integer.bitCount(state)) {
        	System.out.println(0);
        }
        
        else {
        	d = new int [N][1<<N];
            System.out.println(tsp(0));
        }
    }
    
    static int tsp(int cur) {
    	// 종료 조건
    	if(P <= Integer.bitCount(state)) {
    		return arr[cur][0];
    	}
    	
    	if (d[cur][state] != 0) {
    		return d[cur][state];
    	}
    	
    	int ret = INF;
    	
    	// 발전소 방문
    	for (int i=0; i<N; i++) {
    		// 현재 visited 기준으로 가보지 않은 발전소 탐색
    		if((state & (1<<i)) == 0) {
    			state = state | (1<<i);
    			int t = tsp(i) + arr[cur][i];
    			ret = Math.min(ret,  t);
    		}
    	}
    	
    	d[cur][state] = ret;
    	return ret;
    }
}




