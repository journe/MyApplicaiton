package me.journey.android.lib;

import java.util.Scanner;

public class WetSharkGame {
	private static int n;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();
		int x = 1, y = 1;
		int[] t = new int[n];
		int[] X = new int[n];
		int[] Y = new int[n];
		for (int i = 0; i < n; i++) {
			t[i] = sc.nextInt();
			X[i] = sc.nextInt();
			Y[i] = sc.nextInt();
		}

		for (int i = 0; i < n; i++) {
			if (t[i] == 1) {
				x += X[i] - Y[i];
				if (x <= 0) {
					System.out.println("NO");
					return;
				}
			} else {
				y += X[i] - Y[i];
				if (y <= 0) {
					System.out.println("NO");
					return;
				}
			}
		}
		System.out.println("YES");
		System.out.println("(" + x + ", " + y + ")");
	}

}
