package br.com.cafebinario.config;

import org.jasypt.encryption.pbe.PBEStringCleanablePasswordEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import br.com.cafebinario.notify.impl.SenderHtmlEmailNotify;
import br.com.cafebinario.notify.interfaces.Sender;

@Configuration
public class CafebinarioConfig {

	@Bean
	PBEStringCleanablePasswordEncryptor standardPBEStringEncryptor(){
		return new StandardPBEStringEncryptor();
	}
	
	@Bean
	Sender sender(){
		return new SenderHtmlEmailNotify();
	}
	
	@Bean
	JavaMailSender javaMailSender(){
		return new JavaMailSenderImpl();
	}

/**TODO
Verificação abaixo deve ser feita em demais contextos.
nessa API todas as URLs são liberadas.
	
	@Bean
	FilterRegistrationBean filterRegistrationBean() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setDispatcherTypes(DispatcherType.REQUEST);
	    registration.setFilter(filter());

	    return registration;
	}

	
	private Filter filter() {
		return new HttpFilter(){
			private static final long serialVersionUID = -7909922787201179635L;

			@Override
			protected void doFilter(HttpServletRequest request, HttpServletResponse response,
		            FilterChain chain) throws IOException, ServletException {
				
				String[] pathVariables = request.getPathInfo().split("/");
				
				boolean validate = false;
				int i = 0;
				while(i < pathVariables.length){
					if("token".equals(pathVariables[i++])){
						String token = pathVariables[i];
						if(CafebinarioMemory.getInstance().get(token) != null){
							validate = true;
							break;
						}
					}
				}

				if(!validate){
					response.setStatus(HttpStatus.FORBIDDEN.value());
				}
		        
				chain.doFilter(request, response);
		    }
			
			@Override
			public void destroy() {
			}
		};
	}
*/
}
