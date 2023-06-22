package hotel.com.backend.Exception;

public class EntityExistingException extends RuntimeException {
    
    public EntityExistingException(String message) {
        super(message);
    }
}
