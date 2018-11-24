package cn.zzuisa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FetchVideoCover {
	private String ffmpegEXE;

	public FetchVideoCover(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}

	public String getFile(String path) {
		File file = new File(path);
		return null;
	}

	public void getCover(String videoInputPath, String coverOutputPath) throws Exception {
		// ffmpeg -i demo.mp4 output.avi
		List<String> command = new ArrayList<>();

		command.add(ffmpegEXE);
		command.add("-ss");
		command.add("00:00:00");
		command.add("-y");
		command.add("-i");
		command.add(videoInputPath);
		command.add("-vframes");
		command.add("1");
		command.add(coverOutputPath);
		for (String c : command) {
			System.out.print(c + " ");
		}
		ProcessBuilder builder = new ProcessBuilder(command);
		Process process = builder.start();
		close(process);

	}

	public void close(Process process) throws Exception {
		InputStream errorStream = process.getErrorStream();
		InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
		BufferedReader br = new BufferedReader(inputStreamReader);
		String line = "";
		while ((line = br.readLine()) != null) {
		}
		if (br != null) {
			br.close();

		}
		if (inputStreamReader != null) {
			inputStreamReader.close();

		}
		if (errorStream != null) {
			errorStream.close();

		}
	}

	public static void main(String[] args) {
		FetchVideoCover ffmPegTest = new FetchVideoCover("E:\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			ffmPegTest.getCover("E:\\ffmpeg\\bin\\demo.mp4", "E:\\ffmpeg\\bin\\new.jpg");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
