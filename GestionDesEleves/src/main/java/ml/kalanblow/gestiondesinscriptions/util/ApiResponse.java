package ml.kalanblow.gestiondesinscriptions.util;

import java.util.Date;

/**
 * Cette classe représente une réponse générique pour les API.
 *
 * @param <T> Le type de données inclus dans la réponse.
 */
public class ApiResponse<T> {

    private T data;
    private Long timestamp = new Date().getTime();
    private String message;

    /**
     * Constructeur par défaut de la classe ApiResponse.
     */
    public ApiResponse() {
    }

    /**
     * Constructeur de la classe ApiResponse avec un message.
     *
     * @param message Le message à inclure dans la réponse.
     */
    public ApiResponse(String message) {
        this.message = message;
    }

    /**
     * Constructeur de la classe ApiResponse avec des données et un message.
     *
     * @param data    Les données à inclure dans la réponse.
     * @param message Le message à inclure dans la réponse.
     */
    public ApiResponse(T data, String message) {
        this.data = data;
        this.message = message;
    }

    /**
     * Obtient les données de la réponse.
     *
     * @return Les données incluses dans la réponse.
     */
    public T getData() {
        return data;
    }

    /**
     * Définit les données de la réponse.
     *
     * @param data Les données à inclure dans la réponse.
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Obtient le timestamp de la réponse.
     *
     * @return Le timestamp de la réponse.
     */
    public Long getTimestamp() {
        return timestamp;
    }

    /**
     * Définit le timestamp de la réponse.
     *
     * @param timestamp Le timestamp à inclure dans la réponse.
     */
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Obtient le message de la réponse.
     *
     * @return Le message inclus dans la réponse.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Définit le message de la réponse.
     *
     * @param message Le message à inclure dans la réponse.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

