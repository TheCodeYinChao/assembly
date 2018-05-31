package cn.bainuo.exception;

public class DBException extends BaseRuntimeException{
	private static final long serialVersionUID = 1L;

	public DBException(Throwable cause){
		super(cause);
	}
	
	
	public DBException(String errorCode) {
		super(errorCode);
	}

	public DBException(String errorCode, String message) {
			super(errorCode, message);
		}

	public DBException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}

	
}
