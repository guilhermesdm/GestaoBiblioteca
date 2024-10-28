package com.gestaobiblioteca.handler.error;

import java.util.List;

/**
 * Lista com os erros para resposta
 *
 * @author Guilherme
 */
public record ListErrorBeanResponse(List<ErrorBean> errors, Integer status) {
}
