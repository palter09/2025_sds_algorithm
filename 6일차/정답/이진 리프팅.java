import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static int d[][];
	public static void main(String[] args) throws IOException {
		BufferedReader  br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
//		int N = 984541515;
//		System.out.println(Integer.toBinaryString(N));
//		System.out.println((1 << 0) + " " +Integer.toBinaryString((1 << 0)));
//		System.out.println((1 << 1) + " " +Integer.toBinaryString((1 << 1)));
//		System.out.println((1 << 2) + " " +Integer.toBinaryString((1 << 2)));
//		System.out.println((1 << 3) + " " +Integer.toBinaryString((1 << 3)));
//		for (int k=30; k>=0; k--) {
//			if (N >= (1<<k)) {
//				System.out.println(k + " " + (1<<k));
//				N -= (1<<k);
//			}
//		}
		//d[k][n] = n의 2^k번째 부모
		d = new int[10][15];
		for (int i=1; i<=10; i++) {
			d[0][i] = i+1;
		}
		for (int k=1; k<=9; k++) {
			for (int i=1; i<=10; i++) {
				d[k][i] = d[k-1][d[k-1][i]];
			}
		}
		// num을 N번 이동시킨다.
		int N = 5;
		int num = 1;
		for (int k=9; k>=0; k--) {
			if (N >= (1 << k)) {
				N -= (1 << k);
				num = d[k][num];
			}
		}
		System.out.println(num);	
	}
}
