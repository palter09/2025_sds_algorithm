import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int N;
	static ArrayList<ArrayList<Integer>> adj;
	static int[] ind, time, ret;
	static Queue<Integer> q;

	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		adj = new ArrayList<ArrayList<Integer>>(N+1);
		for (int i=0; i<=N; i++) {
			adj.add(new ArrayList<Integer>());
		}
		ind = new int[N+1];
		time = new int[N+1];
		ret = new int[N+1];
		q = new LinkedList<Integer>();
		
		int t, temp;
		for (int i=1; i<=N; i++) {
			st = new StringTokenizer(br.readLine());
			time[i] = Integer.parseInt(st.nextToken());
			while (true) {
				temp = Integer.parseInt(st.nextToken());
				if (temp == -1) {
					break;
				}
				adj.get(temp).add(i);
				ind[i]++;
			}
		}
		for (int i=1; i<=N; i++) {
			if (ind[i] == 0) {
				q.add(i);
				ret[i] = time[i];
			}
		}
		while (!q.isEmpty()) {
			int cur = q.poll();
			for (int i=0; i<adj.get(cur).size(); i++) {
				int next = adj.get(cur).get(i);
				ind[next]--;
				ret[next] = Math.max(ret[cur]+time[next], ret[next]);
				if (ind[next] == 0) {
					q.add(next);
				}
			}
		}
		for (int i=1; i<=N; i++) {
			sb.append(ret[i] + "\n");
		}
		System.out.println(sb.toString());

	}
}
