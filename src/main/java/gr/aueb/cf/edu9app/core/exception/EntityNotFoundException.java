package gr.aueb.cf.edu9app.core.exception;

public class EntityNotFoundException extends AppGenericException {

    private static final String DEFAULT_CODE = "NotFound";

    public EntityNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}
