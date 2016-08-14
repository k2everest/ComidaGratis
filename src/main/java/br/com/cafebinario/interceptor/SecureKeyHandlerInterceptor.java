package br.com.cafebinario.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.cafebinario.register.datamemory.CafebinarioMemory;
import br.com.cafebinario.register.vo.user.UserAuthenticationVO;

@Component
public class SecureKeyHandlerInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private CafebinarioMemory cafebinarioMemory;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String secureKey = request.getParameter("secureKey");
		final String nick = request.getParameter("nick");
		
		if(Boolean.logicalAnd(secureKey == null, nick == null)){
			return false;
		}

		final UserAuthenticationVO userAuthenticationVO = cafebinarioMemory.get(secureKey);
		final boolean isSecure = nick.equals(userAuthenticationVO.getNick());
		return isSecure;
	}
}