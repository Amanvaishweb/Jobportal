package jobportal.DTO;

import java.util.Map;

public class ExceptionErrorDTO {
    private int status;
    private String message;
    private Map<String, String> errors;
    public ExceptionErrorDTO() {}
    public ExceptionErrorDTO(int status, String message, Map<String, String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {this.status=status;}
    public String getMessage() {return message;}
    public void setMessage(String message) {this.message=message;}
    public Map<String, String> getErrors() {
        return errors;
    }

}
