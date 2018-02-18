package module;

import pipeline.PipelineExecutionException;

public abstract class Module<O> {
	protected Module<?> _predecessor;
	protected O _output;
	protected boolean _processed = false;

	public Module(Module<?> predecessor) {
		_predecessor = predecessor;
	}

	public abstract O process() throws PipelineExecutionException;

	public final Module<?> getPredecessor() {
		return _predecessor;
	}

	public final O getOutput() {
		return _output;
	}

	public final boolean processed() {
		return _processed;
	}

	public final Module<?> initModule() {
		return _predecessor == null ? this : _predecessor.initModule();
	}

	protected final O endProcess(O output) {
		_output = output;
		_processed = true;
		return output;
	}

	public final void resetProcess() {
		_output = null;
		_processed = false;
		_predecessor.resetProcess();
	}
}
