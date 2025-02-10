#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

struct Edge {
    int s, e, cost;
};

int find(vector<int>& parent, int a) {
    if (parent[a] == a)
        return a;
    return parent[a] = find(parent, parent[a]);
}

void unionNodes(vector<int>& parent, int a, int b) {
    int rootA = find(parent, a);
    int rootB = find(parent, b);
    if (rootA != rootB) {
        parent[rootB] = rootA;
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int V, E;
    cin >> V >> E;

    // 초기화: 부모 배열
    vector<int> parent(V + 1);
    for (int i = 1; i <= V; i++) {
        parent[i] = i;
    }

    // 간선 정보 입력받기
    vector<Edge> edges(E);
    for (int i = 0; i < E; i++) {
        cin >> edges[i].s >> edges[i].e >> edges[i].cost;
    }

    // 간선을 가중치(cost) 기준 오름차순 정렬
    sort(edges.begin(), edges.end(), [](const Edge& a, const Edge& b) {
        return a.cost < b.cost;
    });

    int cnt = 0, totalCost = 0;
    for (int i = 0; i < E; i++) {
        int s = edges[i].s;
        int e = edges[i].e;
        int cost = edges[i].cost;
        if (find(parent, s) != find(parent, e)) {
            unionNodes(parent, s, e);
            cnt++;
            totalCost += cost;
        }
        if (cnt == V - 1)
            break;
    }

    cout << totalCost << "\n";
    return 0;
}