package com.gwn.exs.ba.data.shared;

public class ResponseDTO<M> {

	public static final int RESULT_OK = 1;
	
	private int resultCode;
	
	private M result;
	
	public ResponseDTO() {}
	
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
}
