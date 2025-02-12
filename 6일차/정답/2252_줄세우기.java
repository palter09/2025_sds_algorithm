import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	static int N, M;
	static int[] ind;
	static ArrayList<ArrayList<Integer>> adj;
	static Queue<Integer> q;
	
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		adj = new ArrayList<ArrayList<Integer>>(N+1);
		ind = new int[N+1];
		for (int i=0; i<=N; i++) {
			adj.add(new ArrayList<Integer>());
		}
		int a, b, temp1, temp2;
		for (int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			adj.get(a).add(b);
			ind[b]++;
		}
		q = new LinkedList<Integer>();
		for (int i=1; i<=N; i++) {
			if (ind[i] == 0) {
				q.add(i);
			}
		}
		while (!q.isEmpty()) {
			temp1 = q.poll();
			sb.append(temp1 + " ");
			for (int i=0; i<adj.get(temp1).size(); i++) {
				temp2 = adj.get(temp1).get(i);
				ind[temp2]--;
				if (ind[temp2] == 0) {
					q.add(temp2);
				}
			}
		}
		System.out.println(sb.toString());

	}
}
