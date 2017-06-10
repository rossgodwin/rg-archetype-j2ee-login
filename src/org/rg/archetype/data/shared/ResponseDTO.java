package org.rg.archetype.data.shared;

import java.io.Serializable;
import java.util.List;

public class ResponseDTO<M> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1894859949710117719L;

	public static final int RESULT_OK = 1;
	public static final int RESULT_FAIL = 2;
	
	private int resultCode;
	
	private M result;
	
	private List<String> errs;
	
	public ResponseDTO() {}
	
	public ResponseDTO(int resultCode) {
		this.resultCode = resultCode;
	}
	
	public ResponseDTO(M result) {
		this(RESULT_OK, result);
	}
	
	public ResponseDTO(int resultCode, M result) {
		this.resultCode = resultCode;
		this.result = result;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public M getResult() {
		return result;
	}

	public void setResult(M result) {
		this.result = result;
	}
	
	public List<String> getErrs() {
		return errs;
	}

	public void setErrs(List<String> errs) {
		this.errs = errs;
	}
}
