package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.props;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.keys.ApplicationKeys;
import org.apache.log4j.Category;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Jorge Zavala Navarro
 */
public class AppPropsBean {

    // Propiedades de la clase
    private static AppPropsVO propsVO = null;
    private static Properties props = new Properties();

    static final Category log = Category.getInstance(AppPropsBean.class);

    static {

        System.out.println("Cargando propiedades de la clase...");

        // Cargar propiedades desde el archivo de configuración
        try {

            // Primero cargamos las propiedades del sistema
            cargarProps();

            // Inicializamos los logs
            iniciarLogs();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void iniciarLogs() throws FileNotFoundException, IOException {
        System.out.println("Iniciar los archivos de los logs...");
        System.out.println("Config Logs: " + AppPropsBean.getPropsVO().getPathConfigLogs());
        Properties logProperties = new Properties();
        logProperties.load(new FileInputStream(AppPropsBean.getPropsVO().getPathConfigLogs()));
        PropertyConfigurator.configure(logProperties);
        log.info("Logs inicializados satisfactoriamente !!");
    }

    private static void cargarProps() throws FileNotFoundException, IOException {
        InputStream is = AppPropsBean.class.getResourceAsStream(ApplicationKeys.KEY_ARCHIVO_CONFIGURACION);
        if(is==null){
            is = new FileInputStream(ApplicationKeys.KEY_ARCHIVO_CONFIGURACION);
        }
        props.load(is);

        // Inicializar la clase con las propiedades
        propsVO = new AppPropsVO();

        // Propiedades principales de la aplicación
        propsVO.setPrincipalAmbiente(props.getProperty(AppPropsKeys.PRINCIPAL_AMBIENTE));
        System.out.println("Trabajando en ambiente: " + propsVO.getPrincipalAmbiente());
        propsVO.setUrlServicedeskWs(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.URL_SERVICEDESK_WS));
        propsVO.setBddConexionClassDriver(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.BDD_CONEXION_CLASS_DRIVER));
        propsVO.setBddConexionUrlFabricante(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.BDD_CONEXION_URL_FABRICANTE));
        propsVO.setBddConexionServidor(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.BDD_CONEXION_SERVIDOR));
        propsVO.setBddConexionPuerto(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.BDD_CONEXION_PUERTO));
        propsVO.setBddConexionBasedatos(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.BDD_CONEXION_BASEDATOS));
        propsVO.setBddConexionUsuario(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.BDD_CONEXION_USUARIO));
        propsVO.setBddConexionPassword(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.BDD_CONEXION_PASSWORD));
        propsVO.setQueryTimeoutSecs(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.QUERY_TIMEOUT_SECS));
        propsVO.setWssdTimeoutConect(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.WSSD_TIMEOUT_CONECT));
        propsVO.setWssdTimeoutRead(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.WSSD_TIMEOUT_READ));
        propsVO.setPathConfigLogs(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.PATH_CONFIG_LOGS));
        propsVO.setWssdCredencialUsuario(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.WSSD_CREDENCIAL_USUARIO));
        propsVO.setWssdCredencialPassword(props.getProperty(propsVO.getPrincipalAmbiente() + "." + AppPropsKeys.WSSD_CREDENCIAL_PASSWORD));
    }

    public static AppPropsVO getPropsVO() {
        return propsVO;
    }

    public static void setPropsVO(AppPropsVO propsVO) {
        AppPropsBean.propsVO = propsVO;
    }

}
