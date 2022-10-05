// =================================================================
//
// File: Example8.java
// Authors: Martin Noboa - A01704052
// 		   Bernardo Estrada - A01704320
// Description: This file implements the enumeration sort algorithm.
// 				The time this implementation takes will be used as the
//				basis to calculate the improvement obtained with
//				parallel technologies.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

public class Example8 extends RecursiveAction {
	private static final int SIZE = 100_000;
	private static final int MIN = 1_000;
	private int array[],B[],start,end;

	public Example8(int array[],int B[],int start,int end) {
		this.array = array;
		this.B=B;
		this.start=start;
		this.end=end;
	}

	protected void computeDirectly() {
		int i,j;
		for(i=start;i<end;i++){
			for(j=0;j<array.length;j++){
				if(array[i]>array[j]||array[i]==array[j]&&j<i){
					B[i]+=1;
				}
			}
		}
	}

	@Override
	protected void compute() {
		if ( (end - start) <= MIN ) {
			computeDirectly();
		} else {
			int mid = start + ( (end - start) / 2 );
			invokeAll(new Example8(array,B,start, mid),
					  new Example8(array,B,mid, end));
		}
	}

	public static void main(String args[]) {
		long startTime, stopTime;
		int array[],C[],B[];
		double ms;
		ForkJoinPool pool;

		array = new int[SIZE];
		C = new int[array.length];
		B = new int[array.length];
		Utils.randomArray(array);
		Utils.displayArray("before", array);
		System.out.printf("Starting with %d threads...\n", Utils.MAXTHREADS);
		ms = 0;
		for (int i = 0; i < Utils.N; i++) {
			
			for(int j=0;j<array.length;j++){
				B[j]=0;
			}
			startTime = System.currentTimeMillis();

			pool = new ForkJoinPool(Utils.MAXTHREADS);
			pool.invoke(new Example8(Arrays.copyOf(array, array.length),B, 0, array.length));

			stopTime = System.currentTimeMillis();
			ms += (stopTime - startTime);
		}
		for(int i=0;i<array.length;i++){
			C[B[i]]=array[i];
		}
		for(int i=0;i<array.length;i++){
			array[i]=C[i];
		}
		Utils.displayArray("after", array);
		System.out.printf("avg time = %.5f ms\n", (ms / Utils.N));
	}
}
