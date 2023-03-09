package fr.miage.amc_comptes.controllers;

import fr.miage.amc_comptes.export.ErrorExport;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Gestionnaire d'exceptions pour envoyer les bonnes erreurs HTTP
 */
@ControllerAdvice
public class GestionnaireExceptions {

    /**
     * Erreur 404 en cas de Compte Inconnu
     * @param request requête HTTP
     * @param exception exception
     * @return l'erreur 404
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorExport> gereAutreException(HttpServletRequest request, RuntimeException exception) {
        return new ResponseEntity<>(new ErrorExport(exception.getMessage(), exception.getClass().getName()), HttpStatus.NOT_FOUND);
    }


    /**
     * Erreur 404 en cas de Client Inconnu
     * Note : c'est cette erreur qui est générée lors de la transformation automatique
     * de l'id en Client
     * @param request requête HTTP
     * @param exception exception
     * @return l'erreur 404
     */
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorExport> gereAutreException(HttpServletRequest request, MissingPathVariableException exception) {
        return new ResponseEntity<>(new ErrorExport(exception.getMessage(), exception.getClass().getName()), HttpStatus.NOT_FOUND);
    }

    /**
     * Erreur 500 en cas d'autre erreur
     * @param request requête HTTP
     * @param exception exception
     * @return l'erreur 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorExport> gereAutreException(HttpServletRequest request, Exception exception) {
        return new ResponseEntity<>(new ErrorExport(exception.getMessage(), exception.getClass().getName()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
