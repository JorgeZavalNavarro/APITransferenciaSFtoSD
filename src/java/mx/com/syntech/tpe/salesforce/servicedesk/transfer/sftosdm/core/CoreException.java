package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.core;

import org.apache.log4j.Category;

/**
 * 
 * @author Jorge Zavala Navarro
 */
public class CoreException extends Exception{
    
    // Constales de lña clase
    Category log = Category.getInstance(CoreException.class);
    
    private String idError = null;
    private String mensaje = null;
    private Throwable throwable = null;

    public CoreException(String message) {
        super(message);
    }

    public CoreException(Throwable cause) {
        super(cause);
    }
    
    

    public CoreException(String idError, Throwable cause) {
        super(cause);
        
        log.error("Recibiendo exception: ");
        
        this.idError = idError;
        this.throwable = cause;
        this.mensaje = cause.getLocalizedMessage()==null 
                || cause.getLocalizedMessage().isEmpty()
                ? cause.getMessage() 
                : cause.getLocalizedMessage();
        log.error("ID Error: " + this.idError);
        log.error("Mensaje : " + this.mensaje);
    }

    public String getIdError() {
        return idError;
    }

    public void setIdError(String idError) {
        this.idError = idError;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

}
