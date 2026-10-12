package com.videocomp;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class mp4comp {
     public File compressMp4(File inputFile) throws IOException {

        String compressedFileName = "compressed.mp4";
        File compressedFile = new File(compressedFileName);

        ProcessBuilder builder = new ProcessBuilder(
            "ffmpeg",  
            "-i", inputFile.getAbsolutePath(),
            "-vcodec", "libx264",
            "-crf", "28",
            "-preset", "fast",
            "-acodec", "aac",
            compressedFile.getAbsolutePath()
        );

        builder.redirectErrorStream(true);
        Process process = builder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while (reader.readLine() != null);
        }

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) throw new IOException("FFmpeg failed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return compressedFile.exists() ? compressedFile : null;
    }

    public File decompressMp4(File inputFile) throws IOException {

        String outputFileName = "decompressed.mp4";
        File outputFile = new File(outputFileName);

        ProcessBuilder builder = new ProcessBuilder(
            "ffmpeg",
            "-i", inputFile.getAbsolutePath(),
            "-vcodec", "libx264",
            "-crf", "18", // Higher quality
            "-preset", "slow",
            "-acodec", "aac",
            outputFile.getAbsolutePath()
        );

        builder.redirectErrorStream(true);
        Process process = builder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while (reader.readLine() != null);
        }

        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("FFmpeg decompression failed");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return outputFile.exists() ? outputFile : null;
    }

}

    
