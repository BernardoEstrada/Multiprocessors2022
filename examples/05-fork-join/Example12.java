// =================================================================
//
// File: Example12.java
// Author: Pedro Perez
// Description: This file implements the merge sort algorithm using
//				Java's Fork-Join.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class Example12 extends RecursiveAction {
	private static final int SIZE = 100_000_000;
	private static final int MIN = 1_000;
	private int A[], B[], start, end;

	public Example12(int A[], int B[], int start, int end) {
		this.A = A;
		this.B = B;
		this.start = start;
		this.end = end;
	}

	private void swap(int a[], int i, int j) {
		int aux = a[i];
		a[i] = a[j];
		a[j] = aux;
	}

	private void copyArray(int low, int high) {
		int length = high - low;
		System.arraycopy(B, low, A, low, length);
	}

	private void merge(int low, int mid, int high) {
		int i, j, k;

		i = low;
		j = mid + 1;
		k = low;
		while(i <= mid && j <= high){
			if(A[i] < A[j]){
				B[k] = A[i];
				i++;
			}else{
				B[k] = A[j];
				j++;
			}
			k++;
		}
		for(; i <= mid; i++){
			B[k++] = A[i];
		}
		for(; j <= high; j++){
			B[k++] = A[j];
		}
	}

	protected void computeDirectly() {
		int size = end - start + 1;
		for(int i = start + 1; i < size; i++){
			for(int j = i; j > start && A[j] < A[j - 1]; j--){
				swap(A, j, j - 1);
			}
		}
	}

	@Override
	protected void compute() {
		if ( (end - start) <= MIN ) {
			computeDirectly();
		} else {
			int mid = start + ((end - start) / 2);
			invokeAll(new Example12(A, B, start, mid),
					  new Example12(A, B, mid, end));
			merge(start, mid, end);
			copyArray(start, end);
		}
	}

	public int[] getSortedArray() {
		return A;
	}

	public static void main(String args[]) {
		int array[], aux[];
		long startTime, stopTime;
		double ms;
		ForkJoinPool pool;
		Example12 obj = null;

		array = new int[SIZE];
		aux = new int[SIZE];
		Utils.randomArray(array);
		Utils.displayArray("before", array);

		System.out.printf("Starting with %d threads...\n", Utils.MAXTHREADS);
		ms = 0;
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			pool = new ForkJoinPool(Utils.MAXTHREADS);
			obj = new Example12(Arrays.copyOf(array, array.length),
								aux, 0, SIZE - 1);
			pool.invoke(obj);

			stopTime = System.currentTimeMillis();
			ms += (stopTime - startTime);
		}
		Utils.displayArray("after", obj.getSortedArray());
		System.out.printf("avg time = %.5f\n", (ms / Utils.N));
	}
}
