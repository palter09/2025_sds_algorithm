#include <iostream>
using namespace std;

long* tree;
long* lazy;
long MAX = 1000000;

void propagate(long node, long start, long end) {
	if (lazy[node] != 0) {
		tree[node] += lazy[node] * (end-start+1);
		if (start != end) {
			lazy[node*2] += lazy[node];
			lazy[node*2+1] += lazy[node];
		}
		lazy[node] = 0;
	}
}

long lazy_query(long node, long start, long end, long left, long right) {
	propagate(node, start, end);
	
	if (right < start || left > end) {
		return 0;
	}
	
	if (left <= start && right >= end) {
		return tree[node];
	}
	
	long mid = (start + end) / 2;
	return lazy_query(node*2, start, mid, left, right) + lazy_query(node*2+1, mid+1, end, left, right);
}

void lazy_update(long node, long start, long end, long left, long right, long diff) {
	propagate(node, start, end);
	
	if (right < start || left > end) {
		return;
	}
	
	if (left <= start && right >= end) {
		tree[node] += diff * (end-start+1);
		if(start != end) {
			lazy[node*2] += diff;
			lazy[node*2+1] += diff;
		}
		return;
	}
	long mid = (start+end) / 2;
	lazy_update(node*2, start, mid, left, right, diff);
	lazy_update(node*2+1, mid+1, end, left, right, diff);
	tree[node] = tree[node*2] + tree[node*2+1];
}

void update(long node, long start, long end, long idx, long diff) {
	if (start > idx || end < idx) {
		return;
	}
	
	tree[node] += diff;
	
	if (start != end) {
		long mid = (start + end) / 2;
		update(node*2, start, mid, idx, diff);
		update(node*2+1, mid+1, end, idx, diff);
	}
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	
	int N, M, K;
	cin >> N >> M >> K;
	
	tree = new long[MAX*4];
	lazy = new long[MAX*4];
	fill(tree, tree+MAX*4, 0);
	
	for (int i=0; i<N; i++) {
		long num;
		cin >> num;
		update(1, 0, MAX-1, i, num);
	}
	
	for (int i=0; i<M+K; i++) {
		int a;
		cin >> a;
		
		if (a == 1) {
			long b, c, d;
			cin >> b >> c >> d;
			
			lazy_update(1, 0, MAX-1, b-1, c-1, d);
		}
		
		if (a == 2) {
			long b, c;
			cin >> b >> c;
			
			cout << lazy_query(1, 0, MAX-1, b-1, c-1) << endl;
		}
	}
	
	delete[] tree;
	delete[] lazy;
	return 0;
}
