package br.com.cafebinario.register.vo.result.builder;

import br.com.cafebinario.register.vo.result.ResultVO;

public class ResultVOBuilder {
	
	public final static ResultVO SUCCESS(){
		return new ResultVO(0, "SUCCESSS");
	}

	public static final ResultVO ERROR_SEND_SECURE_KEY(){
		return new ResultVO(1, "ERROR SEND SECURE KEY");
	}

	public static final ResultVO ERROR_USER_HAS_EXIST(){
		return new ResultVO(2, "USER HAS EXIST");
	}

	public static final ResultVO ERROR_USER_NOT_EXIST(){
		return new ResultVO(3, "USER NOT EXIST");
	}
	
	public static ResultVO ERROR_AUTHENTICANTION() {
		return new ResultVO(4, "UNAUTHORIZED");
	}

	public static ResultVO BLANCK() {
		return new ResultVO(0, "");
	}

}
