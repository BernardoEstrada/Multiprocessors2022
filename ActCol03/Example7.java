// =================================================================
//
// File: Example7.java
// Authors: Martin Noboa - A01704052
// 		   Bernardo Estrada - A01704320
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM. The time this
//				implementation takes will be used as the basis to
//				calculate the improvement obtained with parallel
//				technologies.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

import java.lang.Math;

public class Example7 {
	private static final int SIZE = 1_000_000;
	private boolean array[];
	private double result;

	public Example7(boolean array[]) {
		this.array = array;
		this.result = 0;
	}

	public double getResult() {
		return result;
	}

	public void prime() {
		boolean notPrime;
		for (int n = 2; n < array.length; n++){
			notPrime = false;
			for (int i = 2; i <= Math.sqrt(n); i++){
				if(notPrime){
					break;
				}
				if(n%i == 0){
					notPrime = true;
				}
			}
			if(!notPrime){
				array[n] = true;
			}
		}
	}

	public void calculate() {
		result = 0;
		for (int i = 2; i < array.length; i++){
			if (array[i]){
				result += i;
			}
		}
	}

	public static void main(String args[]) {
		boolean array[] = new boolean[SIZE + 1];
		long startTime, stopTime;
		double acum = 0;

		// System.out.println("At first, neither is a prime. We will display to TOP_VALUE:");
		// for (int i = 0; i < Utils.TOP_VALUE; i++) {
		// 	array[i] = false;
		// 	System.out.print("" + i + ", ");
		// }
		// System.out.println("");

		Example7 e = new Example7(array);
		acum = 0;
		System.out.printf("Starting...\n");
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			e.prime();
			e.calculate();
			

			stopTime = System.currentTimeMillis();

			acum += (stopTime - startTime);
		}
		System.out.println("Expanding the numbers that are prime to TOP_VALUE:");
		for (int i = 2; i < Utils.TOP_VALUE; i++) {
		// for (int i = 2; i < 10; i++) {
			if (array[i]) {
				System.out.print("" + i + ", ");
			}
		}
		System.out.println("");
		System.out.printf("sum =  " + e.getResult() + "\n");
		System.out.println("");
		System.out.printf("avg time = %.5f ms\n", (acum / Utils.N));
	}
}
