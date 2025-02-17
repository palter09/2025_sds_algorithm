#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int n, q, m;
vector<int> mileage;

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> q;
	mileage.resize(n);

	for (int i = 0; i < n; i++) {
		cin >> mileage[i];
	}

	sort(mileage.begin(), mileage.end());

	for (int i = 0; i < q; i++) {
		cin >> m;
		int idx = lower_bound(mileage.begin(), mileage.end(), m) - mileage.begin();
		if (idx != n && m == mileage[idx]) {
			cout << idx * (n - idx - 1) << "\n"; // m보다 작은 수의 개수 * m보다 큰 수의 개수 = 모든 경우의 수
		}
		else {
			cout << 0 << "\n";
		}
	}

	return 0;
}
