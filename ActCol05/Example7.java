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
//			This implementation uses the Thread class.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

// ======Outputs====================================================
// Single Thread
// sum = 3.7550402023E10
// avg time = 133.3 ms
//
// Multi Thread
// sum = 3.7550402023E10
// avg time = 371.3 ms ??
//
// Speedup = 0.36x ??
import java.lang.Math;


public class Example7 extends Thread {
	private static final int SIZE = 1_000_000;
	private boolean array[];
	private double result;
	private int start, end;

	public Example7(boolean array[], int start, int end) {
		this.array = array;
		this.result = 0;
		this.start = start;
		this.end = end;
	}

	public double getResult() {
		return result;
	}

	public void prime(int start, int end) {
		boolean notPrime;
		for (int n = start; n < end; n++){
			notPrime = false;
			if (n == 0 || n == 1) {
				continue;
			}
			else {
				for (int i = 2; i <= Math.sqrt(n); i++){
					if(n%i == 0){
						notPrime = true;
					}
				}
			}
			if(!notPrime){
				array[n] = true;
			}
		}
	}

	public void calculate(int start, int end) {
		result = 0;
		for (int i = start; i < end; i++){
			if (array[i]){
				result += i;
			}
		}
	}

	public void run() {
		prime(start, end);
		calculate(start, end);
	}

	public static void main(String args[]) {
		boolean array[] = new boolean[SIZE + 1];
		long startTime, stopTime;
		double acum = 0;
		double res = 0;

		Example7 t[] = new Example7[Utils.MAXTHREADS];
		int sum = 0;
		System.out.printf("Starting with %d threads...\n", Utils.MAXTHREADS);

		int blockSize = SIZE / Utils.MAXTHREADS;

		for (int i = 0; i < Utils.N; i++) {
			res = 0;
			startTime = System.currentTimeMillis();

			for (int j = 0; j < t.length; j++){
				t[j] = new Example7(array, j*blockSize, (j+1)*blockSize);
				t[j].start();
			}
			try {
				for (int j = 0; j < t.length; j++){
					t[j].join();
					res += t[j].getResult();
				}
			} catch (InterruptedException ie) {
				System.out.printf("An error ocurred while waiting for the threads to finish.\n");
			}

			stopTime = System.currentTimeMillis();

			acum += (stopTime - startTime);
		}
		System.out.printf("sum =  " + res + "\n");
		System.out.printf("avg time = %.5f ms\n", (acum / Utils.N));
	}
}