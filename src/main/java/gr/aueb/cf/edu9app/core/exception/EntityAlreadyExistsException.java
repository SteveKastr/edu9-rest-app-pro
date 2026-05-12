package gr.aueb.cf.edu9app.core.exception;

public class EntityAlreadyExistsException extends AppGenericException{
    private static final String DEFAULT_CODE = "AlreadyExists";

    public EntityAlreadyExistsException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
