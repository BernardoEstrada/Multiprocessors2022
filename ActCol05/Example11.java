// =================================================================
//
// File: Example11.java
// Author(s): Martin Noboa - A01704052
// 						Bernardo Estrada - A01704320
// Description: This file implements the code that transforms a
//				grayscale image. The time this implementation takes will
//				be used as the basis to calculate the improvement obtained
// 				with parallel technologies.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Example11 extends Thread {
	private int src[], dest[], start, end;

	public Example11(int src[], int dest[], int start, int end) {
		this.src = src;
		this.dest = dest;
		this.start = start;
		this.end = end;
	}

	void doTask() {
		for (int i = start; i < end; i++) {
			int pixel = src[i];
			int r = (pixel >> 16) & 0xFF;
			int g = (pixel >> 8) & 0xFF;
			int b = (pixel & 0xFF);
			int gray = (int) (r + g + b)/3;
			dest[i] = (0xFF << 24) | ((gray << 16) | (gray << 8) | gray);
		}
	}

	public void run() {
		doTask();
	}

	public static void main(String args[]) throws Exception {
		long startTime, stopTime;
		double ms;

		if (args.length != 1) {
			System.out.println("usage: java Example11 image_file");
			System.exit(-1);
		}

		final String fileName = args[0];
		File srcFile = new File(fileName);
        final BufferedImage source = ImageIO.read(srcFile);

		int w = source.getWidth();
		int h = source.getHeight();
		int src[] = source.getRGB(0, 0, w, h, null, 0, w);
		int dest[] = new int[src.length];

		Example11 threads[] = new Example11[Utils.MAXTHREADS];

		System.out.printf("Starting...\n");
		ms = 0;
		int blockSize = (w * h) / Utils.MAXTHREADS;

		for (int i = 0; i < Utils.N; i++) {
			startTime = System.currentTimeMillis();

			for (int j = 0; j < Utils.MAXTHREADS; j++) {
				threads[j] = new Example11(src, dest, j*blockSize, (j+1)*blockSize);
				threads[j].start();
			}
			try {
				for (int j = 0; j < Utils.MAXTHREADS; j++) {
					threads[j].join();
				}
			} catch (InterruptedException ie) {
				System.out.printf("error: %s\n", ie.getMessage());
			}

			stopTime = System.currentTimeMillis();

			ms += (stopTime - startTime);
		}
		System.out.printf("avg time = %.5f\n", (ms / Utils.N));
		final BufferedImage destination = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		destination.setRGB(0, 0, w, h, dest, 0, w);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              ImageFrame.showImage("Original - " + fileName, source);
            }
        });

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
              ImageFrame.showImage("Gray - " + fileName, destination);
            }
        });
	}
}
