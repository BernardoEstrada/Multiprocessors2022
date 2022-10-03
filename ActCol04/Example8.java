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

import java.util.Arrays;

public class Example8 extends Thread {
	private static final int SIZE = 100_000;
	private int array[];

	public Example8(int array[]) {
		this.array = array;
	}

	private void fillPosition(int pos[]){
		int i, j;
		for(i = 0; i < SIZE; i++){
			for(j = 0; j < SIZE; j++){
				if(array[i] < array[j] || (array[i] == array[j] && i < j)){
					pos[j] += 1;
				}
			}
		}
	}

	private void movePosition(int pos[]){
		int i;
		int temp[] = new int[SIZE];

		for(i = 0; i < SIZE; i++){
			temp[pos[i]] = array[i];
		}
		for(i = 0; i < SIZE; i++){
			array[i] = temp[i];
		}
	}

	public void doTask() {
		int pos[] = new int[SIZE];
		fillPosition(pos);
		movePosition(pos);
	}

	public int[] getSortedArray() {
		return array;
	}

	public static void main(String args[]) {
		int array[] = new int[SIZE];
		long startTime, stopTime;
		double ms;
		
		Utils.randomArray(array);
		Example8 obj = new Example8(array);
		Utils.displayArray("before", array);

		System.out.printf("Starting...\n");
		ms = 0;
		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			obj.doTask();

			stopTime = System.currentTimeMillis();

			ms += (stopTime - startTime);
		}
		Utils.displayArray("after", obj.getSortedArray());
		System.out.printf("avg time = %.5f\n", (ms / Utils.N));
	}
}
