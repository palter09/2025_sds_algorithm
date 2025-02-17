#include <iostream>
#include <vector>
using namespace std;

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int n;
	cin >> n;

	vector<int> arr(n);
	for (int i = 0; i < n; i++) {
		cin >> arr[i];
	}

	// more[i][k] -> i와 k사이에 A_i보다 큰게 몇게 있는지
	vector<vector<int>> more(n, vector<int>(n));
	int total = 0;
	for (int i = 0; i < n; i++) {
		for (int k = i + 1; k < n; k++) {
			if (arr[i] < arr[k]) { // k가 아니라 j 개수를 세고 +1 해서 저장
				more[i][k] = more[i][k - 1] + 1;
			}

			else { // k 개수를 세서 total 계산
				more[i][k] = more[i][k - 1];
				total += more[i][k];
			}
		}
	}

	cout << total;

	return 0;
}
