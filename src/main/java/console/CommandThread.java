package main.java.console;

import main.java.controler.ComputationControler;

public class CommandThread implements Runnable {

	private CommandLine commandLine;
	private ComputationControler computationControler;
	private WaitRunner waitRunner;

	public CommandThread(CommandLine commandLine,
			ComputationControler computationControler,WaitRunner waitRunner) {
		this.commandLine = commandLine;
		this.computationControler = computationControler;
		this.waitRunner = waitRunner;
	}

	@Override
	public void run() {
		this.waitRunner.run(commandLine,computationControler);

	}

}
