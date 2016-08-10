package br.com.cafebinario.register.vo.result;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.cafebinario.register.vo.NewUserVO;
import br.com.cafebinario.register.vo.result.builder.ResultVOBuilder;

public class UserListResultVO implements Serializable{

	private static final long serialVersionUID = -7488630822625453456L;

	public static UserListResultVO createUserListResultVOJUnitTest() {
		final ResultVO resultVO = ResultVOBuilder.SUCCESS();
		final List<NewUserVO> userListVO = new ArrayList<>();
		
		userListVO.add(NewUserVO.createUserVOJUnitValidTest());

		return new UserListResultVO(resultVO, userListVO);
	}
	
	private final ResultVO result;
	private final List<NewUserVO> userList;

	public UserListResultVO(){
		super();
		this.result = null;
		this.userList = null;
	}
	
	public UserListResultVO(final ResultVO result, final List<NewUserVO> userList) {
		super();
		this.result = result;
		this.userList = userList;
	}

	public ResultVO getResult() {
		return result;
	}
	
	public List<NewUserVO> getUserList() {
		return userList;
	}
}
