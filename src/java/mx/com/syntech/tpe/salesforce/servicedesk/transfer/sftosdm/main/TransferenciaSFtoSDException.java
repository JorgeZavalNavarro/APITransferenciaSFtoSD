package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.main;

import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.core.CoreException;

/**
 * 
 * @author Jorge Zavala Navarro
 */
public class TransferenciaSFtoSDException extends CoreException{
    
    public TransferenciaSFtoSDException(String idCode, Throwable th){
        super(idCode, th);
    }


}
