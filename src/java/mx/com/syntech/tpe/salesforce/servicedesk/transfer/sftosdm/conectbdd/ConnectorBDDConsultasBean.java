package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.conectbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.keys.CodeKeys;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.props.AppPropsBean;
import org.apache.log4j.Category;

/**
 *
 * @author Jorge Zavala Navarro
 */
public class ConnectorBDDConsultasBean {
    
    private static final Category log = Category.getInstance(ConnectorBDDConsultasBean.class);

    public static Connection getConectionServiceDesk() throws ConnectorBDDConsultasException {
        Connection retorno = null;
        log.debug("Intentando conectar a la base de datos !!");
        try {

            // Validamos si la sesiòn no es la misma o la sesiòn es nula creamos 
            // un nuevo conector a la base de datos
            Class.forName(AppPropsBean.getPropsVO().getBddConexionClassDriver());

            String url 
                    = "jdbc:" + AppPropsBean.getPropsVO().getBddConexionUrlFabricante()
                    + "://" + AppPropsBean.getPropsVO().getBddConexionServidor()
                    + ":" + AppPropsBean.getPropsVO().getBddConexionPuerto()
                    + ";databaseName=" + AppPropsBean.getPropsVO().getBddConexionBasedatos();
            System.out.println("URL:" + url);
            DriverManager.setLoginTimeout(Integer.valueOf(AppPropsBean.getPropsVO().getQueryTimeoutSecs()).intValue());
            retorno = DriverManager.getConnection(url, AppPropsBean.getPropsVO().getBddConexionUsuario(), AppPropsBean.getPropsVO().getBddConexionPassword());
            retorno.setAutoCommit(Boolean.FALSE);

        } catch (Exception ex) {
            String retCode = null;
            if(ex.getMessage().contains("connect timed out")){
                retCode = CodeKeys.CODE_340_DATABASE_SQLTIMEOUT;
            }else{
                retCode = CodeKeys.CODE_220_DATABASE_UNREACHABLE;
            }
            throw new ConnectorBDDConsultasException(retCode, ex);
        } catch (Throwable th) {
            throw new ConnectorBDDConsultasException(CodeKeys.CODE_970_DATABASE_ERROR_NC, th);
        }
        return retorno;

    }
    
    public static void main(String...params){
        try {
            getConectionServiceDesk();
        } catch (ConnectorBDDConsultasException ex) {
            ex.printStackTrace();
        }
    }
}
