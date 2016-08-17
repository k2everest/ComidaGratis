package br.com.cafebinario.register.vo.result.builder;

import java.util.Arrays;
import java.util.List;

import br.com.cafebinario.register.vo.result.ResultVO;
import br.com.cafebinario.register.vo.result.hateos.NavigationVO;

public class ResultVOBuilder {
	
	public final static ResultVO SUCCESS(final List<NavigationVO> navigations){
		return new ResultVO(0, "SUCCESSS", navigations);
	}

	public static final ResultVO ERROR_SEND_SECURE_KEY(){
		return new ResultVO(1, "ERROR SEND SECURE KEY", null);
	}

	public static final ResultVO ERROR_USER_HAS_EXIST(){
		return new ResultVO(2, "USER HAS EXIST", null);
	}

	public static final ResultVO ERROR_USER_NOT_EXIST(){
		return new ResultVO(3, "USER NOT EXIST", null);
	}
	
	public static ResultVO ERROR_AUTHENTICANTION() {
		return new ResultVO(4, "UNAUTHORIZED", null);
	}

	public static ResultVO BLANCK() {
		return new ResultVO(0, "", Arrays.asList(new NavigationVO("", "", "")));
	}
}
