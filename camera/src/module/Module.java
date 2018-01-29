package module;

import pipeline.PipelineExecutionException;

public abstract class Module<O> {
	protected Module<?> _predecessor;

	public Module(Module<?> predecessor) {
		_predecessor = predecessor;
	}

	public abstract O process() throws PipelineExecutionException;
}
