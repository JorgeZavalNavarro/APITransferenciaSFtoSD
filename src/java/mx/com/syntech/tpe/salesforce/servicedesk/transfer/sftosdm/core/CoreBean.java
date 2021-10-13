package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.core;

import java.text.SimpleDateFormat;

/**
 * 
 * @author Jorge Zavala Navarro
 */
public class CoreBean {
    
    
    private static final String FORMATO_FECHA_RESPONSE_TPE = "yyyy-MM-dd HH:mm:ss";
    
    
    protected String fechaHoraActual(){
        String retorno = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        retorno = format.format(new java.util.Date());
        return retorno;
    }
    
    public static String fechaActual(){
        String retorno = null;
        SimpleDateFormat format = new SimpleDateFormat(FORMATO_FECHA_RESPONSE_TPE);
        java.util.Date fechaActual = new java.util.Date();
        retorno = format.format(fechaActual);
        return retorno;
    }
    

}
