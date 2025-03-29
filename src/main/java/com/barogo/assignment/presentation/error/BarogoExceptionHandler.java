package com.barogo.assignment.presentation.error;

import com.barogo.assignment.application.ClientException;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class BarogoExceptionHandler {
	private final MessageSource ms;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ProblemDetail handle(BindException e) {
		Locale locale = LocaleContextHolder.getLocale();

		String errorMessage = createErrorMessage(e, locale);

		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errorMessage);
	}

	@ExceptionHandler(ClientException.class)
	public ProblemDetail handle(ClientException e) {

		return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
	}

	private String createErrorMessage(BindException e, Locale locale) {
		return e.getBindingResult().getFieldErrors().stream()
				.map(
						error -> {
							String[] codes = error.getCodes();
							if (codes == null) {
								return getUnknownErrorMessage(locale);
							}

							return resolveMessageFromCodes(codes, locale);
						})
				.collect(Collectors.joining("\n"));
	}

	private String resolveMessageFromCodes(String[] codes, Locale locale) {
		for (String code : codes) {
			try {
				return ms.getMessage(code, null, locale);
			} catch (NoSuchMessageException ignored) {

			}
		}
		return getUnknownErrorMessage(locale);
	}

	private String getUnknownErrorMessage(Locale locale) {
		return ms.getMessage("UnknownErrorMessage", null, locale);
	}
}
