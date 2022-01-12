package tenpo.diegosaez.core.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageUtil {
	private final MessageSource messageSource;

	public String getMessage(String code, String... args) {

		return messageSource.getMessage(code, args, Locale.getDefault());
	}

}
