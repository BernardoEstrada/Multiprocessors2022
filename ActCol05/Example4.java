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
//			This implementation uses Fork Join.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
// ======Outputs====================================================
// Single Thread
// sum = 941896832
// avg time = 60.1 ms
//
// Fork Join
// sum = 941896832
// avg time =  43.6ms
//
// Speedup = 1.38x
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;


public class Example4 extends RecursiveTask<Integer> {
	private static final int SIZE = 100_000_000;
	private static final int MIN = 10_000;
	private int array[];
	private int start, end;

	public Example4(int array[], int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
	}

	public Integer computeDirectly() {
		int result = 0;
		for (int i = start; i < end; i++){
			if (array[i] % 2 == 0){
				result += array[i];
			}
		}
		return result;
	}

	@Override
	protected Integer compute() {
		if ((end - start) <= MIN) {
			return computeDirectly();
		} else {
			int mid = start + ((end - start) / 2);
			Example4 lowerMid = new Example4(array, start, mid);
			lowerMid.fork();
			Example4 upperMid = new Example4(array, mid, end);
			return (upperMid.compute() + lowerMid.join());
		}

	}

	public static void main(String args[]) {
		int array[] = new int[SIZE];
		long startTime, stopTime;
		double acum = 0;
		int res = 0;
		ForkJoinPool pool;

		Utils.fillArray(array);
		Utils.displayArray("array", array);

		acum = 0;
		System.out.printf("Starting with %d threads\n", Utils.MAXTHREADS);

		for (int i = 0; i < Utils.N; i++) {
			res = 0;
			startTime = System.currentTimeMillis();

			pool = new ForkJoinPool(Utils.MAXTHREADS);
			res = pool.invoke(new Example4(array, 0, array.length));
			stopTime = System.currentTimeMillis();

			acum += (stopTime - startTime);
		}
		System.out.printf("sum = %d\n", res);
		System.out.printf("avg time = %.5f ms\n", (acum / Utils.N));
	}
}

