package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.main;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.xml.rpc.holders.StringHolder;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.conectbdd.ConnectorBDDConsultasBean;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.conectbdd.ConnectorBDDConsultasException;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.core.CoreBean;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.keys.CodeKeys;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.props.AppPropsBean;
import org.apache.log4j.Category;

/**
 * Transfernecia deticket de un grupo a otro segun los parámetros de entrada
 * Para este caso vamos a utilizar el método transfer del servicio web Este
 * método se encuentra dentro de la categoria de Web Service Business Methos
 * Descripción Realiza una actividad de transferencia en una emisión, solicitud
 * u orden de cambio. Este método corresponde al comando "Actividades -
 * Transferencia" en la interfaz de CA SDM. Este método genera un registro de
 * actividad y, opcionalmente, establece un nuevo asignado, grupo u
 * organización. El cesionario, grupo u organización no se actualiza a menos que
 * uno o más de los correspondientes setAssignee / setGroup / setOrganization
 * parámetros se establece en verdadero. Si el parámetro complementario es
 * falso, la transferencia no intentará actualizar el campo, incluso si se pasa
 * un valor para ese campo. Por ejemplo, sisetAssignee se pasa como falso, la
 * transferencia no actualizará al cesionario incluso si newAssigneeespecifica
 * un valor. Si elsetXXXX parámetro es verdadero, entonces el campo se
 * actualiza. Pase la cadena vacía para establecer un campo en vacío / nulo.
 * Retorno Uno o más identificadores para los objetos de registro de actividad
 * creados. Los retornos están bajo un elemento padre llamado <Logs>.
 *
 * @author Jorge Zavala Navarro
 */
public class TransferenciaSFtoSDBean extends CoreBean {

    // Constantes de la clase
    private static final Category log = Category.getInstance(TransferenciaSFtoSDBean.class);

    // Propiedades de la clase
    private URL url = null;
    private static final String uNoLock = "  WITH (NOLOCK) ";

    public TransferenciaSFtoSDBean() throws MalformedURLException {
        // Inicializamos algunos parámetros
        url = new URL(AppPropsBean.getPropsVO().getUrlServicedeskWs());

    }

    public void transferirTicket(TransferenciaInfoInputVO inputVO) throws TransferenciaSFtoSDException {
        Connection conn = null;
        String uuidResponsable = null;
        String uuidGrupoDestino = null;
        String persidTicket = null;

        // Validamos el parámetro de entrada
        if (inputVO != null) {
            // Inicializamos el elemento de retorno
            int sid = 0;

            try {

                // Obtenemos la conección a la base de datos
                conn = ConnectorBDDConsultasBean.getConectionServiceDesk();

                /**
                 * VALIDAR EL RESPONSABLE *
                 */
                log.info("   ::: Validando el responsable del movimiento " + inputVO.getResponsable() + "...");
                if (inputVO.getResponsable() == null || inputVO.getResponsable().isEmpty()) {
                    throw new TransferenciaSFtoSDException(CodeKeys.CODE_010_SIN_INFORMACION, new Exception("No se está especificando el responsable de la transferencia"));
                } else {
                    // Buscamos este responsable como el 
                    String sqlBuscarResponsable
                            = "select top 1 contact_uuid, userid, alias, last_name, first_name  \n"
                            + "from ca_contact  " + this.uNoLock + " \n"
                            + "where userid = ?  \n"
                            + "  and inactive = '0'  \n"
                            + "order by last_update_date desc   ";
                    PreparedStatement psBuscarResponsable = conn.prepareCall(sqlBuscarResponsable);
                    psBuscarResponsable.setString(1, inputVO.getResponsable());
                    log.debug("   ::: Ejecutamos el siguiente query");
                    log.debug(sqlBuscarResponsable);
                    ResultSet rsBuscarResponsable = psBuscarResponsable.executeQuery();
                    if (rsBuscarResponsable.next()) {
                        uuidResponsable = rsBuscarResponsable.getString("contact_uuid");
                        log.info("   ::: Responsable id=" + inputVO.getResponsable() + " --> " + uuidResponsable);
                        uuidResponsable = "cnt:" + uuidResponsable; // .substring(2);
                        log.info("   ::: Responsable =" + uuidResponsable);
                    } else {
                        throw new TransferenciaSFtoSDException(CodeKeys.CODE_960_SERVICE_DESK_ERROR_NC, new Exception("No se encuentra el responsable con el id de usuario: " + inputVO.getResponsable() + " o no se encuentra activo."));
                    }
                }

                /**
                 * VALIDAR LA INFORMACIÓN DEL NUEVO GRUPO *
                 */
                log.info("   ::: Validando el GRUPO DESTINO " + inputVO.getNuevoGrupo() + "...");
                if (inputVO.getNuevoGrupo() == null || inputVO.getNuevoGrupo().isEmpty()) {
                    throw new TransferenciaSFtoSDException(CodeKeys.CODE_960_SERVICE_DESK_ERROR_NC, new Exception("No se está especificando el grupo a donde se va a transferir el ticket"));
                } else {
                    // Buscamos este responsable como el 
                    String sqlBuscarGrupoDestino
                            = "select top 1 contact_uuid \n"
                            + "from ca_contact  " + this.uNoLock + " \n"
                            + "where last_name = ?  \n"
                            + "  and inactive = '0'  \n"
                            + "order by last_update_date desc   ";
                    PreparedStatement psBuscarGrupo = conn.prepareCall(sqlBuscarGrupoDestino);
                    psBuscarGrupo.setString(1, inputVO.getNuevoGrupo());
                    log.info("   ::: Ejecutamos el siguiente query");
                    log.info(sqlBuscarGrupoDestino);
                    ResultSet rsBuscarGrupo = psBuscarGrupo.executeQuery();
                    if (rsBuscarGrupo.next()) {
                        uuidGrupoDestino = rsBuscarGrupo.getString("contact_uuid");
                        log.info("   ::: Grupo destino id=" + inputVO.getNuevoGrupo() + " --> " + uuidGrupoDestino);
                        uuidGrupoDestino = "cnt:" + uuidGrupoDestino; // .substring(2);
                        log.info("   ::: Grupo destino =" + uuidGrupoDestino);

                    } else {
                        throw new TransferenciaSFtoSDException(CodeKeys.CODE_960_SERVICE_DESK_ERROR_NC, new Exception("No se encuentra el grupo destino con el nombre: " + inputVO.getNuevoGrupo() + " o no se encuentra activo."));
                    }
                }

                log.info("   ::: Validar la información del ticket proveniente de salesfor: " + inputVO.getNumTicketSF());
                if (inputVO.getNumTicketSF()== null || inputVO.getNumTicketSF().isEmpty()) {
                    throw new TransferenciaSFtoSDException(CodeKeys.CODE_120_SIN_CRETERIA, new Exception("No se está recibiendo el número de ticket de Salesforce"));
                }

                log.info("   ::: Validar la información del ticket (folio/num_ref) " + inputVO.getNumRefTicket());
                if (inputVO.getNumRefTicket() == null || inputVO.getNumRefTicket().isEmpty()) {
                    throw new TransferenciaSFtoSDException(CodeKeys.CODE_960_SERVICE_DESK_ERROR_NC, new Exception("No se está especificando el número o folio del ticket"));
                } else {
                    // Obtenemos el persid (cr:nnnnnnnn) del ticket
                    String sqlBuscarPersidTicket
                            = "select persid, zfolio_dbw_sf \n"
                        // Se cambia el nombre del campo del tickeyt externo 22/06/2021
                        // por el campo de zfolio_dbw_sf el cual se va a registrar el numero
                        // de ticket correspondiente a salesforce
                            
                            + "  from call_req " + this.uNoLock + " \n"
                            + " where ref_num = ? \n"
                            + "   and active_flag = '1' \n"
                            + " order by open_date desc";
                    log.info("   ::: Buscar el ticket " + inputVO.getNumRefTicket());
                    log.info("   ::: Ejecutar el query :");
                    log.info(sqlBuscarPersidTicket);
                    PreparedStatement psBuscarPersidTicket = conn.prepareCall(sqlBuscarPersidTicket);
                    psBuscarPersidTicket.setString(1, inputVO.getNumRefTicket());
                    ResultSet rsBuscarPersidTicket = psBuscarPersidTicket.executeQuery();
                    if (rsBuscarPersidTicket.next()) {
                        // Cargamos el persid del ticket
                        persidTicket = rsBuscarPersidTicket.getString("persid");
                        String externalSystemTicket = rsBuscarPersidTicket.getString("zfolio_dbw_sf");

                        // Validamos que el zfolio_dbw_sf correspponda al que
                        // se está recibiendo en el inputVO
                        if (!inputVO.getNumTicketSF().trim().equals(externalSystemTicket.trim())) {
                            throw new TransferenciaSFtoSDException(CodeKeys.CODE_130_VALORES_INCORRECTOS,
                                    new Exception("El ticket de salesfoce que se proporcionó: "
                                            + inputVO.getNumTicketSF() + " no corresponde al que tiene relacionado "
                                            + "el ticket de SD en la base de datos, el cual es: "
                                            + externalSystemTicket));
                        }
                    } else {
                        // No se encontró el ticket
                        throw new TransferenciaSFtoSDException(CodeKeys.CODE_960_SERVICE_DESK_ERROR_NC,
                                new Exception("No se encuentra el ticket con el folio: "
                                        + inputVO.getNumRefTicket() + " o no se encuentra activo."));
                    }
                }

                /**
                 * Validamos que se tenga especificado el usuario *
                 */
                if (inputVO.getUsuario() == null || inputVO.getUsuario().isEmpty()) {
                    String error = "   ::: No se está recibiendo la cuenta de usuario";
                    log.error(error);
                    throw new TransferenciaSFtoSDException(CodeKeys.CODE_110_SIN_CREDENCIALES, new Exception(error));
                } else if (inputVO.getPassword() == null || inputVO.getPassword().isEmpty()) {
                    String error = "   ::: No se está recibiendo el password";
                    log.error(error);
                    throw new TransferenciaSFtoSDException(CodeKeys.CODE_110_SIN_CREDENCIALES, new Exception(error));
                }
                log.info(" ::: Logeando al usuario " + inputVO.getUsuario() + "...");
                sid = login(inputVO.getUsuario(), inputVO.getPassword());
                log.info(" ::: SID: " + sid);

                log.info(" ::: Generarel ahndler del ticket " + inputVO.getNumRefTicket());
                StringHolder objectHanle = new StringHolder(persidTicket);
                log.info(" ::: Handle del ticket obtenido: " + objectHanle.value);

                log.info(" ::: Obtener el handle del nuevo grupo a donde se va a asignar el ticket");
                StringHolder handleNuevoGrupo = new StringHolder(uuidGrupoDestino);
                log.info(" ::: Handle del nuevo grupo:" + handleNuevoGrupo.value);

                log.info(" ::: Ejecutar la transfernecia correspondiente...");
                String retornoTransfer = transfer(
                        sid, // ID de la sesión del usuario
                        uuidResponsable, // Handler del usuario responsable
                        objectHanle.value, // Handle del objeto que se desea cambiar de grupo o transferir
                        inputVO.getDescripcion(), // Descipción para el cambio correspondiente
                        Boolean.FALSE, // Indica que cambiamos el asignatario del ticket
                        "", // Handle del nuevo responsable al cual se le va a signar el ticket
                        Boolean.TRUE, // Indica cambio de grupo del ticket correspondiente
                        uuidGrupoDestino, // Handle del nuevo grupo a donde se va a asignar el ticket
                        Boolean.FALSE,
                        "");

                log.info(" ::: El servicio resolvió lo siguiente: " + retornoTransfer);

                // Agregamos el movimiento en el activity log
                String handleCreator = this.getHandleForUserid(sid, inputVO.getUsuario());
                log.info(" ::: Agregar el movimiento en el log de actividades...");
                String logDescripcion = "El usuario de Dashboard Web transfirió el "
                        + "ticket " + inputVO.getNumRefTicket() + " hacia el grupo "
                        + inputVO.getNuevoGrupo() + ". " + inputVO.getDescripcion();
                String actLogRetorno = this.createActivityLog(sid, handleCreator, objectHanle.value, logDescripcion, "LOG", 0, false);
                log.info(" ::: Activity log regresó lo siguiente: " + actLogRetorno);
            } catch (TransferenciaSFtoSDException ex) {
                throw new TransferenciaSFtoSDException(ex.getIdError(), ex);
            } catch (SQLException ex) {
                throw new TransferenciaSFtoSDException(CodeKeys.CODE_350_DATABASE_QUERYERROR, ex);
            } catch (ConnectorBDDConsultasException ex) {
                String error = "Se produjo un error al intentar cambiar al grupo " + inputVO.getNuevoGrupo() + " el ticket " + inputVO.getNumRefTicket() + " --> " + ex.getMessage();
                throw new TransferenciaSFtoSDException(CodeKeys.CODE_210_SERVICE_DESK_UNREACHABLE, ex);
            } catch (Exception ex) {
                String error = "Se produjo un error al intentar cambiar al grupo " + inputVO.getNuevoGrupo() + " el ticket " + inputVO.getNumRefTicket() + " --> " + ex.getMessage();
                throw new TransferenciaSFtoSDException(CodeKeys.CODE_960_SERVICE_DESK_ERROR_NC, ex);
            } catch (Throwable ex) {
                String error = "Se produjo un error al intentar cambiar al grupo " + inputVO.getNuevoGrupo() + " el ticket " + inputVO.getNumRefTicket() + " --> " + ex.getMessage();
                throw new TransferenciaSFtoSDException(CodeKeys.CODE_980_ERROR, ex);

            } finally {

                // Cerrar sesión ontenida desde Service Desk
                if (sid > 0) {
                    log.info(" ::: Cerrar la sesión: " + sid);
                    logout(sid);
                    log.info(" ::: La sesión se cerró satisfactoriamente !!");
                }

                // Cerrar la connección a la base de datos
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (Exception ex) {
                        log.error(ex);
                        throw new TransferenciaSFtoSDException(CodeKeys.CODE_970_DATABASE_ERROR_NC, ex);
                    } catch (Throwable ex) {
                        log.error(ex);
                        throw new TransferenciaSFtoSDException(CodeKeys.CODE_970_DATABASE_ERROR_NC, ex);
                    }
                }
            }

        } else {
            // No se está recibiendo la información correctamente
            throw new TransferenciaSFtoSDException(CodeKeys.CODE_010_SIN_INFORMACION, new Exception("No se está recibiendo la información para procesar"));
        }
    }

    private int login(java.lang.String username, java.lang.String password) {
        mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebService service = new mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebService(url);
        mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebServiceSoap port = service.getUSDWebServiceSoap();
        return port.login(username, password);
    }

    private void logout(int sid) {
        mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebService service = new mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebService(url);
        mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebServiceSoap port = service.getUSDWebServiceSoap();
        port.logout(sid);
    }

    private String transfer(int sid, java.lang.String creator, java.lang.String objectHandle, java.lang.String description, boolean setAssignee, java.lang.String newAssigneeHandle, boolean setGroup, java.lang.String newGroupHandle, boolean setOrganization, java.lang.String newOrganizationHandle) {
        mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebService service = new mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebService(url);
        mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebServiceSoap port = service.getUSDWebServiceSoap();
        return port.transfer(sid, creator, objectHandle, description, setAssignee, newAssigneeHandle, setGroup, newGroupHandle, setOrganization, newOrganizationHandle);
    }

    private String getHandleForUserid(int sid, java.lang.String userID) {
        mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebService service = new mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebService(url);
        mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebServiceSoap port = service.getUSDWebServiceSoap();
        return port.getHandleForUserid(sid, userID);
    }

    private String createActivityLog(int sid, java.lang.String creator, java.lang.String objectHandle, java.lang.String description, java.lang.String logType, int timeSpent, boolean internal) {
        mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebService service = new mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebService(url);
        mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client.USDWebServiceSoap port = service.getUSDWebServiceSoap();
        return port.createActivityLog(sid, creator, objectHandle, description, logType, timeSpent, internal);
    }

}
