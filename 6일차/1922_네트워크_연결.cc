#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

struct Edge {
    int s, e, cost;
};

vector<int> parent;

int find(int a) {
    if (parent[a] == a)
        return a;
	parent[a] = find(parent[a]);
    return parent[a];
}

void unionNodes(int a, int b) {
    int rootA = find(a);
    int rootB = find(b);
    if (rootA != rootB) {
        parent[rootB] = rootA;
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N, M;
    cin >> N >> M;

    // 초기화: 부모 배열
    parent.resize(N + 1);
    for (int i = 1; i <= N; i++) {
        parent[i] = i;
    }

    // 간선 정보 입력받기
    vector<Edge> edges(M);
    for (int i = 0; i < M; i++) {
        cin >> edges[i].s >> edges[i].e >> edges[i].cost;
    }

    // 간선을 가중치(cost) 기준 오름차순 정렬
    sort(edges.begin(), edges.end(), [](const Edge& a, const Edge& b) {
        return a.cost < b.cost;
    });

    int cnt = 0, totalCost = 0;
    for (int i = 0; i < M; i++) {
        int s = edges[i].s;
        int e = edges[i].e;
        int cost = edges[i].cost;
        if (find(s) != find(e)) {
            unionNodes(s, e);
            cnt++;
            totalCost += cost;
        }
        if (cnt == N - 1)
            break;
    }

    cout << totalCost << "\n";
    return 0;
}
