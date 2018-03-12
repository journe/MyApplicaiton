package me.journey.android.lib;

import java.math.BigInteger;
import java.util.Scanner;

public class WetSharkDigitEnemies {
	private static int n;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();

		if (n <= 7) {
			long start = (long) Math.pow(10.0, (n - 1));
			long end = (long) Math.pow(10.0, n);
			long maxValue = start - (81 * n);
			long minValue = end - 1;
			long maxNumber = 0;
			long minNumber = 0;

			for (long i = start; i < end; i++) {
				long power = getPower(i);
				if (maxValue <= power) {
					maxValue = power;
					maxNumber = i;
				}
				if (minValue >= power) {
					minValue = power;
					minNumber = i;
				}
			}
			System.out.println(maxNumber);
			System.out.println(minNumber);
			System.out.println(maxValue);
			System.out.println(minValue);
		} else {
			System.out.println(getMaxNumber(n));
			System.out.println(getMinNumber(n));
			System.out.println(getMaxValue(n));
			System.out.println(getMinValue(n));
		}
	}

	private static String getMaxNumber(int n) {
		String s = "";
		for (int i = 0; i < n - 2; i++) {
			s += "9";
		}
		return s + "51";
	}

	private static String getMinNumber(int n) {
		String s = "1";
		for (int i = 0; i < n - 2; i++) {
			s += "0";
		}
		return s + "9";
	}

	private static String getMinValue(int n) {
		String s = "";
		for (int i = 0; i < n - 3; i++) {
			s += "9";
		}
		return s + "27";
	}

	private static String getMaxValue(int n) {
		BigInteger bigInteger = BigInteger.valueOf(10);
		bigInteger = bigInteger.pow(8).subtract(BigInteger.valueOf(81 * n)).add(BigInteger.valueOf(87));
		String s = "";
		for (int i = 0; i < n - 8; i++) {
			s += "9";
		}
		s += bigInteger.toString();
		return s;
	}

	private static long getPower(long n) {
		return n - squareDigitSum(n);
	}

	private static long squareDigitSum(long n) {
		long a;
		long sum = 0;
		long temp = n;
		while (temp > 0) {
			a = temp % 10;
			sum += a * a;
			temp = temp / 10;
		}
		return sum;
	}

}
