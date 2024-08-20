package br.com.ifpe.bazzar.util.exception.GlobalException;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import br.com.ifpe.bazzar.util.exception.UserException;

@RestControllerAdvice
public class GlobalException {

    private final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    // Trata exceções relacionadas ao usuário (duplicidade de e-mail, CPF, etc.)
    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handleUserException(UserException userException) {
        logger.error("UserException captada: {}", userException.getMessage());
        return new ResponseEntity<>(userException.getMessage(), HttpStatus.BAD_REQUEST);
    }

   
}
