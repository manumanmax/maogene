package pipeline;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.Start;
import module.Module;

public class Pipeline<O> implements Runnable {
	public module.Module<O> _input;
	public CompletableFuture<O> _output = new CompletableFuture<O>();
	final static Logger _logger = LoggerFactory.getLogger(Start.class);

	public Pipeline(Module<O> input) {
		super();
		_input = input;
	}

	@Override
	public void run() {
		try {
			_output.complete(_input.process());
		} catch (PipelineExecutionException e) {
			_logger.debug(e._message, e);
		}
	}

	public O getOutput() throws InterruptedException, ExecutionException {
		return _output.get();
	}
}
