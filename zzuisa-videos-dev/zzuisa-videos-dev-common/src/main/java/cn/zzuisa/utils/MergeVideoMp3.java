package cn.zzuisa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MergeVideoMp3 {
	private String ffmpegEXE;

	public MergeVideoMp3(String ffmpegEXE) {
		super();
		this.ffmpegEXE = ffmpegEXE;
	}

	public String getFile(String path) {
		File file = new File(path);
		return null;
	}

	public void convertor(String mp3InputPath, String videoInputPath, double seconds, String videoOutputPath,
			boolean flag) throws Exception {
		// ffmpeg -i demo.mp4 output.avi
		List<String> command = new ArrayList<>();
		// if (flag) {
		// List<String> preCommand = new ArrayList<>();
		// preCommand.add(ffmpegEXE);
		// preCommand.add("-i");
		// preCommand.add(videoInputPath);
		// preCommand.add("-an");
		// preCommand.add("-y");
		// preCommand.add(videoOutputPath);
		// ProcessBuilder builder = new ProcessBuilder(preCommand);
		// Process preProcess = builder.start();
		// close(preProcess);
		// for (String c : preCommand) {
		// System.out.print(c + " ");
		// }
		// System.out.println();
		// }

		command.add(ffmpegEXE);
		command.add("-i");
		command.add(mp3InputPath);
		command.add("-i");
		if (flag)
			command.add(videoOutputPath);
		else
			command.add(videoInputPath);
		command.add("-t");
		command.add(String.valueOf(seconds));
		command.add("-y");
		// if (flag)
		// command.add(videoInputPath);
		// else
		command.add(videoOutputPath);

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
		MergeVideoMp3 ffmPegTest = new MergeVideoMp3("E:\\ffmpeg\\bin\\ffmpeg.exe");
		try {
			ffmPegTest.convertor("E:\\ffmpeg\\bin\\demo.mp4", "E:\\ffmpeg\\bin\\hl.mp3", 6, "E:\\ffmpeg\\bin\\qqq.mp4",
					true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
