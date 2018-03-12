package me.journey.android.lib;

import java.util.Scanner;

//http://codeforces.com/submissions/journesky
public class AlmostPalindrome {
	static int inputLength = 0, swapSteps = 0;
	static char[] inputChars;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		inputChars = sc.nextLine().toCharArray();
		inputLength = inputChars.length;

		int checkStep = 0;
		if (isPalindrome(inputChars)) {
			for (int i = 0; i < (inputLength - 1) / 2; i++) {
				if (inputChars[inputLength - 1 - checkStep] != inputChars[i]) {
					for (int j = inputLength - 1 - i; j > i; j--)
						if (inputChars[i] == inputChars[j]) {
							checkStep++;
							swap(j, inputLength - 1 - i);
							break;
						}
				} else {
					checkStep++;
				}
			}
			if (swapSteps > 1) {
				System.out.println("NO");
			} else {
				System.out.println("YES");
			}
		} else {
			System.out.println("NO");
		}
	}

	private static void swap(int source, int target) {
		char temp = inputChars[source];
		inputChars[source] = inputChars[target];
		inputChars[target] = temp;
		swapSteps++;
	}

	private static boolean isPalindrome(char[] a) {
		int b[] = new int[26];
		for (int i = 0; i < inputLength; i++) {
			b[a[i] - 'a']++;
		}
		int singleCharCount = 0;
		for (int i = 0; i < 26; i++) {
			if (b[i] % 2 == 1) {
				singleCharCount++;
			}
		}
		if (singleCharCount > 1) {
			return false;
		} else {
			return true;
		}
	}

}
