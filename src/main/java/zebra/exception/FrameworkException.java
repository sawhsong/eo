package zebra.exception;

import zebra.data.ParamEntity;

public class FrameworkException extends BaseException {
	public FrameworkException(Exception ex) {
		super(ex);
	}

	public FrameworkException(ParamEntity paramEntity, Exception ex) {
		super(paramEntity, ex);
	}

	public FrameworkException(String code, String message) {
		super(code, message);
	}
}