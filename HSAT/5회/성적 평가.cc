#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int N;
vector<int> ans;
vector<pair<int,int>> scores;
vector<pair<int,int>> total;
vector<int> ranking;

void computeRanking(vector<pair<int,int>> arr);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> N;
	ans.resize(N);
	scores.resize(N);
	total.resize(N);
	ranking.resize(N);

	for (int i = 0; i < 3; i++) {
		for (int j = 0; j < N; j++) {
			cin >> scores[j].first;
			scores[j].second = j;

			total[j].first += scores[j].first;
			total[j].second = j;
		}
		computeRanking(scores);
	}
	computeRanking(total);

	return 0;
}

void computeRanking(vector< pair<int, int>> arr) {
	// 점수가 높은 순으로 정렬
	sort(arr.begin(), arr.end(), [](const pair<int, int>& a, const pair<int, int>& b) {
		return a.first > b.first;
	});

	int cnt = 1;
	ranking[0] = cnt;
	for (int i = 1; i < N; i++) {
		if (arr[i].first != arr[i - 1].first) {
			cnt = i + 1;
		}
		ranking[i] = cnt;
	}

	for (int i = 0; i < N; i++) {
		ans[arr[i].second] = ranking[i];
	}

	for (int i = 0; i < N; i++) {
		cout << ans[i] << " ";
	}
	cout << "\n";
}
