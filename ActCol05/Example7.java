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
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;


public class Example7 extends RecursiveTask<Long> {
	private static final int SIZE = 1_000_000;
	private static final int MIN = 10_000;
	private boolean array[];
	private int start, end;

	public Example7(boolean array[], int start, int end) {
		this.array = array;
		this.start = start;
		this.end = end;
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

	public Long calculate(int start, int end) {
		long result = 0;
		for (int i = start; i < end; i++){
			if (array[i]){
				result += i;
			}
		}
		return result;
	}

	public Long computeDirectly() {
		prime(start, end);
		return calculate(start, end);
		 
	}

	@Override
	protected Long compute() {
		if ( (end - start) <= MIN ) {
			return computeDirectly();
		} else {
			int mid = start + ( (end - start) / 2 );
			Example7 lowerMid = new Example7(array, start, mid);
			lowerMid.fork();
			Example7 upperMid = new Example7(array, mid, end);
			return upperMid.compute() + lowerMid.join();
		}
	}

	

	public static void main(String args[]) {
		long startTime, stopTime, result = 0;
		boolean array[] = new boolean[SIZE + 1];
		double ms;
		ForkJoinPool pool;


		System.out.printf("Starting with %d threads...\n", Utils.MAXTHREADS);
		ms = 0;
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			pool = new ForkJoinPool(Utils.MAXTHREADS);
			result = pool.invoke(new Example7(array, 0, array.length));

			stopTime = System.currentTimeMillis();
			ms += (stopTime - startTime);
		}
		System.out.printf("result = %d\n", result);
		System.out.printf("avg time = %.5f\n", (ms / Utils.N));
	}
}