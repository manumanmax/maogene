package pipeline;

import enums.ExecutionStatus;

public class PipelineExecutionException extends Exception {


	/** serialVersion */
	private static final long serialVersionUID = 1234L;
	
	public ExecutionStatus _status;
	public String _message;
	
	public PipelineExecutionException(ExecutionStatus status, String message) {
		super();
		_status = status;
		_message = message;
	}
	
	
}
