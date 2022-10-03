// =================================================================
//
// File: Example4.java
// Authors: Martin Noboa - A01704052
// 		   Bernardo Estrada - A01704320
// Description: This file contains the code to count the number of
//				even numbers within an array. The time this implementation
//				takes will be used as the basis to calculate the
//				improvement obtained with parallel technologies.
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
// sum = 941896832
// avg time = 34.2 ms
//
// Multi Thread
// sum = 941896832
// avg time = 16.6 ms
//
// Speedup = 2.06x

public class Example4 extends Thread {
	private static final int SIZE = 100_000_000;
	private int array[];
	private int result;
	private int start, end;

	public Example4(int array[], int start, int end) {
		this.array = array;
		this.result = 0;
		this.start = start;
		this.end = end;
	}

	public int getResult() {
		return result;
	}

	public void calculate() {
		result = 0;
		for (int i = start; i < end; i++){
			if (array[i] % 2 == 0){
				result += array[i];
			}
		}
	}

	public void run() {
		calculate();
	}

	public static void main(String args[]) {
		int array[] = new int[SIZE];
		long startTime, stopTime;
		double acum = 0;
		int res = 0;

		Utils.fillArray(array);
		Utils.displayArray("array", array);

		Example4 threads[] = new Example4[Utils.MAXTHREADS];
		int blockSize = SIZE / Utils.MAXTHREADS;

		acum = 0;
		System.out.printf("Starting with %d threads\n", Utils.MAXTHREADS);
		for (int i = 0; i < Utils.N; i++) {
			res = 0;
			startTime = System.currentTimeMillis();

			for (int j = 0; j < threads.length; j++) {
				threads[j] = new Example4(array, j * blockSize, (j + 1) * blockSize);
				threads[j].start();
			}
			try {
				for (int j = 0; j < threads.length; j++) {
					threads[j].join();
					res += threads[j].getResult();
				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}

			stopTime = System.currentTimeMillis();

			acum += (stopTime - startTime);
		}
		System.out.printf("sum = %d\n", res);
		System.out.printf("avg time = %.5f ms\n", (acum / Utils.N));
	}
}

