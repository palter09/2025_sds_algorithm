import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	
	static int arr[], N, tree[], lazy[];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		arr = new int[] {3, 2, 4, 5, 1, 6, 2, 7};
		N = arr.length;
		tree = new int[N*4];
		lazy = new int[N*4];
		init(1, 0, N-1);
		lazy_update(1, 0, N-1, 0, 7, 2);
		System.out.println(lazy_query(1, 0, N-1, 0, 1));
		lazy_update(1, 0, N-1, 0, 5, 3);
		System.out.println(lazy_query(1, 0, N-1, 0, 1));
//		System.out.println(query(1, 0, N-1, 0, 6));
//		update(1, 0, N-1, 0, 5);
//		System.out.println(query(1, 0, N-1, 0, 1));
//		update(1, 0, N-1, 2, 100);
//		System.out.println(query(1, 0, N-1, 0, 2));
	}
	// 구간합을 대표하는 트리의 초기화
	// 트리의 노드번호, 원본배열(리프노드) 구간의 시작점, 끝점
	static int init(int node, int start, int end) {
		if (start == end) {
			tree[node] = arr[start];
			return tree[node];
		}
		int mid = (start + end)/2;
		tree[node] = init(node*2, start, mid) 
				+ init(node*2+1, mid+1, end);
		return tree[node];
	}
	// lazy 값을 처리하고 자식 노드로 전파
	static void propagate(int node, int start, int end) {
		if (lazy[node] != 0) {
			tree[node] += lazy[node] * (end-start+1);
			if (start != end) {
				lazy[node*2] += lazy[node];
				lazy[node*2+1] += lazy[node];
			}
			lazy[node] = 0;
		}
	}
	// 트리의 i~j까지의 구간합을 가져온다, left=>찾고자하는 구간의 시작점/right=>찾고자하는 구간의끝점
//	static int query(int node, int start, int end, int left, int right) {
//		// 찾고자하는 구간이 현재 노드가 대표하는 구간의 범위에 따라 동작이 다르다.
//		// 구간이 아예 일치 않는경우
//		if (right < start || end < left) {
//			return 0;
//		}
//		// 구간이 완전히 일치하는 경우
//		if (left <= start && end <= right) {
//			return tree[node];
//		}
//		int mid = (start + end)/2;
//		return query(node*2, start, mid, left, right) 
//				+ query(node*2+1, mid+1, end, left, right);
//	}
	static int lazy_query(int node, int start, int end, int left, int right) {
		propagate(node, start, end);
		// 찾고자하는 구간이 현재 노드가 대표하는 구간의 범위에 따라 동작이 다르다.
		// 구간이 아예 일치 않는경우
		if (right < start || end < left) {
			return 0;
		}
		// 구간이 완전히 일치하는 경우
		if (left <= start && end <= right) {
			return tree[node];
		}
		int mid = (start + end)/2;
		return lazy_query(node*2, start, mid, left, right) 
				+ lazy_query(node*2+1, mid+1, end, left, right);
	}
	// left ~ right 구간에 diff 변화 시켜라.
	static void lazy_update(int node, int start, int end, int left, int right, int diff) {
		propagate(node, start, end);
		// 구간이 일치하지 않는 경우
		if (right < start || end < left) {
			return;
		}
		// 구간이 완전히 포함되는경우
		if (left <= start && end <= right) {
			tree[node] += diff * (end-start+1);
			if (start != end) {
				lazy[node*2] += diff;
				lazy[node*2+1] += diff;
			}
			return;
		}
		int mid = (start+end)/2;
		lazy_update(node*2, start, mid, left, right, diff);
		lazy_update(node*2+1, mid+1, end, left, right, diff);
		tree[node] = tree[node*2] + tree[node*2+1];
	}
	// 원본배열(리프노드) i 의 값을 diff 만큼 변화 시켜라, idx => 원본배열번호, diff=>변화량
//	static void update(int node, int start, int end, int idx, int diff) {
//		// 관련없는 경우
//		if (idx < start || end < idx) {
//			return;
//		}
//		tree[node] += diff;
//		if (start != end) {
//			int mid = (start + end)/2;
//			update(node*2, start, mid, idx, diff);
//			update(node*2+1, mid+1, end, idx, diff);
//		}
//	}
}
