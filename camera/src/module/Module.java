package module;

import pipeline.PipelineExecutionException;

public abstract class Module<O> {
	protected Module<?> _successor;

	public Module(Module<?> successor) {
		_successor = successor;
	}

	public abstract <T> O process(T previousOutput) throws PipelineExecutionException;
}
