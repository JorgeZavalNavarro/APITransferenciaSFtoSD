package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.props;

/**
 * 
 * @author Jorge Zavala Navarro
 */
public class AppPropsVO {
    
    // Propiedades de la clase
    private String principalAmbiente = null;
    private String pathConfigLogs = null;
    private String urlServicedeskWs = null;
    private String bddConexionClassDriver = null;
    private String bddConexionUrlFabricante = null;
    private String bddConexionServidor = null;
    private String bddConexionPuerto = null;
    private String bddConexionBasedatos = null;
    private String bddConexionUsuario = null;
    private String bddConexionPassword = null;
    private String queryTimeoutSecs = null;
    private String wssdTimeoutConect = null;
    private String wssdTimeoutRead=null;
    private String wssdCredencialUsuario = null;
    private String wssdCredencialPassword=null;

    // Métodos getters y setters
    public String getPrincipalAmbiente() {
        return principalAmbiente;
    }

    public void setPrincipalAmbiente(String principalAmbiente) {
        this.principalAmbiente = principalAmbiente;
    }

    public String getPathConfigLogs() {
        return pathConfigLogs;
    }

    public void setPathConfigLogs(String pathConfigLogs) {
        this.pathConfigLogs = pathConfigLogs;
    }

    public String getUrlServicedeskWs() {
        return urlServicedeskWs;
    }

    public void setUrlServicedeskWs(String urlServicedeskWs) {
        this.urlServicedeskWs = urlServicedeskWs;
    }

    public String getBddConexionClassDriver() {
        return bddConexionClassDriver;
    }

    public void setBddConexionClassDriver(String bddConexionClassDriver) {
        this.bddConexionClassDriver = bddConexionClassDriver;
    }

    public String getBddConexionUrlFabricante() {
        return bddConexionUrlFabricante;
    }

    public void setBddConexionUrlFabricante(String bddConexionUrlFabricante) {
        this.bddConexionUrlFabricante = bddConexionUrlFabricante;
    }

    public String getBddConexionServidor() {
        return bddConexionServidor;
    }

    public void setBddConexionServidor(String bddConexionServidor) {
        this.bddConexionServidor = bddConexionServidor;
    }

    public String getBddConexionPuerto() {
        return bddConexionPuerto;
    }

    public void setBddConexionPuerto(String bddConexionPuerto) {
        this.bddConexionPuerto = bddConexionPuerto;
    }

    public String getBddConexionBasedatos() {
        return bddConexionBasedatos;
    }

    public void setBddConexionBasedatos(String bddConexionBasedatos) {
        this.bddConexionBasedatos = bddConexionBasedatos;
    }

    public String getBddConexionUsuario() {
        return bddConexionUsuario;
    }

    public void setBddConexionUsuario(String bddConexionUsuario) {
        this.bddConexionUsuario = bddConexionUsuario;
    }

    public String getBddConexionPassword() {
        return bddConexionPassword;
    }

    public void setBddConexionPassword(String bddConexionPassword) {
        this.bddConexionPassword = bddConexionPassword;
    }

    public String getQueryTimeoutSecs() {
        return queryTimeoutSecs;
    }

    public void setQueryTimeoutSecs(String queryTimeoutSecs) {
        this.queryTimeoutSecs = queryTimeoutSecs;
    }

    public String getWssdTimeoutConect() {
        return wssdTimeoutConect;
    }

    public void setWssdTimeoutConect(String wssdTimeoutConect) {
        this.wssdTimeoutConect = wssdTimeoutConect;
    }

    public String getWssdTimeoutRead() {
        return wssdTimeoutRead;
    }

    public void setWssdTimeoutRead(String wssdTimeoutRead) {
        this.wssdTimeoutRead = wssdTimeoutRead;
    }

    public String getWssdCredencialUsuario() {
        return wssdCredencialUsuario;
    }

    public void setWssdCredencialUsuario(String wssdCredencialUsuario) {
        this.wssdCredencialUsuario = wssdCredencialUsuario;
    }

    public String getWssdCredencialPassword() {
        return wssdCredencialPassword;
    }

    public void setWssdCredencialPassword(String wssdCredencialPassword) {
        this.wssdCredencialPassword = wssdCredencialPassword;
    }

    @Override
    public String toString() {
        return "AppPropsVO{" + "principalAmbiente=" + principalAmbiente + ", pathConfigLogs=" + pathConfigLogs + ", urlServicedeskWs=" + urlServicedeskWs + ", bddConexionClassDriver=" + bddConexionClassDriver + ", bddConexionUrlFabricante=" + bddConexionUrlFabricante + ", bddConexionServidor=" + bddConexionServidor + ", bddConexionPuerto=" + bddConexionPuerto + ", bddConexionBasedatos=" + bddConexionBasedatos + ", bddConexionUsuario=" + bddConexionUsuario + ", bddConexionPassword=" + bddConexionPassword + ", queryTimeoutSecs=" + queryTimeoutSecs + ", wssdTimeoutConect=" + wssdTimeoutConect + ", wssdTimeoutRead=" + wssdTimeoutRead + ", wssdCredencialUsuario=" + wssdCredencialUsuario + ", wssdCredencialPassword=" + wssdCredencialPassword + '}';
    }
    
    
    

   
}
