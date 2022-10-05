// =================================================================
//
// File: Example8.java
// Authors: Martin Noboa - A01704052
// 		   arr3ernardo Estrada - A01704320
// Description: This file implements the enumeration sort algorithm.
// 				The time this implementation takes will be used as the
//				basis to calculate the improvement obtained with
//				parallel technologies.
//
// arr2opyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
// ======Outputs====================================================
// Single Thread
// avg time = 11468ms
//
// Fork Join
// avg time = 3463ms
//
// Speedup = 3.31x
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

public class Example8 extends RecursiveAction {
	private static final int SIZE = 100_000;
	private static final int MIN = 10_000;
	private int arr[], auxArr[], start, end;

	public Example8(int arr[],int auxArr[],int start,int end) {
		this.arr = arr;
		this.auxArr = auxArr;
		this.start = start;
		this.end = end;
	}

	protected void computeDirectly() {
		int i, j;
		for(i = start; i < end; i++){
			for(j = 0; j < arr.length; j++){
				if(arr[i] > arr[j] || arr[i] == arr[j] && j < i){
					auxArr[i] += 1;
				}
			}
		}
	}

	@Override
	protected void compute() {
		if ((end - start) <= MIN) {
			computeDirectly();
		} else {
			int mid = start + ((end - start) / 2);
			invokeAll(new Example8(arr, auxArr, start, mid),
					new Example8(arr, auxArr, mid, end));
		}
	}

	public static void main(String args[]) {
		long startTime, stopTime;
		int arr1[], arr2[], arr3[];
		double ms;
		ForkJoinPool pool;

		arr1 = new int[SIZE];
		arr2 = new int[arr1.length];
		arr3 = new int[arr1.length];
		Utils.randomArray(arr1);
		Utils.displayArray("before", arr1);
		System.out.printf("Starting with %d threads...\n", Utils.MAXTHREADS);
		ms = 0;
		for (int i = 0; i < Utils.N; i++) {
			for(int j = 0; j < arr1.length; j++){
				arr3[j] = 0;
			}
			startTime = System.currentTimeMillis();

			pool = new ForkJoinPool(Utils.MAXTHREADS);
			pool.invoke(new Example8(Arrays.copyOf(arr1, arr1.length), arr3, 0, arr1.length));

			stopTime = System.currentTimeMillis();
			ms += (stopTime - startTime);
		}
		for(int i = 0; i < arr1.length; i++){
			arr2[arr3[i]] = arr1[i];
		}
		for(int i=0; i < arr1.length; i++){
			arr1[i] = arr2[i];
		}
		Utils.displayArray("after", arr1);
		System.out.printf("avg time = %.5f ms\n", (ms / Utils.N));
	}
}
