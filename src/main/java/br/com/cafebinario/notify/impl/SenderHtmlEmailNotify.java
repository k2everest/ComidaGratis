package br.com.cafebinario.notify.impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import br.com.cafebinario.exception.NotifyException;
import br.com.cafebinario.exception.SendEmailException;
import br.com.cafebinario.notify.data.EventNotifyData;
import br.com.cafebinario.notify.interfaces.Sender;

@Component
public class SenderHtmlEmailNotify implements Sender {

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void execute(final EventNotifyData eventNotifyData) throws NotifyException {

		Assert.notNull(eventNotifyData);
		Assert.noNullElements(eventNotifyData.getTo());

		try {
			final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			final List<Exception> errors = new ArrayList<>();

			mimeMessageHelper.setFrom("alexandre.msl@gmail.com");
			mimeMessageHelper.setTo(eventNotifyData.getTo());
			mimeMessageHelper.setSubject(eventNotifyData.getSubject());
			mimeMessageHelper.setText(eventNotifyData.getContent(), true);

			if (eventNotifyData.getAttachs() != null && !eventNotifyData.getAttachs().isEmpty()) {
				eventNotifyData.getAttachs().forEach(attach -> {
					try {
						mimeMessageHelper.addAttachment(attach.getIdentify(),
								new ByteArrayResource(attach.getAtachment()), attach.getContentType());
					} catch (Exception e) {
						errors.add(e);
					}
				});
			}

			if (errors.isEmpty()) {
				javaMailSender.send(mimeMessage);
			} else {
				throw new SendEmailException("erro durante criacao de e-mail", errors);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new NotifyException("erro durante criacao de e-mail");
		}
	}
}
