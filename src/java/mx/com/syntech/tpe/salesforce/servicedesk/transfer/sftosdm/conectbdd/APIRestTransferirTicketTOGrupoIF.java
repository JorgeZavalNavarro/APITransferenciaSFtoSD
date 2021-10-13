package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.conectbdd;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.core.CoreBean;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.core.CoreException;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.keys.CodeKeys;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.main.TransferenciaInfoInputVO;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.main.TransferenciaInfoOutputVO;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.main.TransferenciaSFtoSDBean;
import org.apache.log4j.Category;

/**
 * REST Web Service
 *
 * @author dell
 */
@Path("ticket/transferir/grupo")
public class APIRestTransferirTicketTOGrupoIF {

    // Constantes de la clase
    Category log = Category.getInstance(APIRestTransferirTicketTOGrupoIF.class);

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of APIRestTransferirTicketTOGrupoIF
     */
    public APIRestTransferirTicketTOGrupoIF() {
    }

    /**
     * Retrieves representation of an instance of
     * mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.conectbdd.APIRestTransferirTicketTOGrupoIF
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_XHTML_XML)
    public String getJson() {
        String retorno = null;
        //TODO return proper representation object
        System.out.println("Solicitud de servicio incorrecto");
        retorno = "<error>" + "\n"
                + "   <titulo>Solicitud de servicio incorrecto</titulo>" + "\n"
                + "   <descripcion>Esta API se debe de consumir solamente por su método POST</descripcion>" + "\n"
                + "</error>";
        return retorno;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TransferenciaInfoOutputVO getPost(TransferenciaInfoInputVO inputVO) {
        
        TransferenciaInfoOutputVO retorno = new TransferenciaInfoOutputVO();
        retorno.setFechaSolicitud(CoreBean.fechaActual());
        log.warn("Tranferir ticket a otro grupo");

        try {
            log.info(" ::: Transferir el ticket con la siguiente información:");
            log.info(" ::: Descripción motivo de la transferencia..: " + inputVO.getDescripcion());
            log.info(" ::: Nombre del grupo destino................: " + inputVO.getNuevoGrupo());
            log.info(" ::: Numero de Ticket........................: " + inputVO.getNumRefTicket());
            // log.info(" ::: Password para el servicio...............: " + inputVO.getPassword());
            log.info(" ::: Nombre del responsable..................: " + inputVO.getResponsable());
            log.info(" ::: Usuario para el servicio................: " + inputVO.getUsuario());

            // Información adicional
            log.info("Default Charset=" + Charset.defaultCharset());
            log.info("file.encoding=" + System.getProperty("file.encoding"));
            log.info("Default Charset=" + Charset.defaultCharset());
            log.info("Default Charset in Use=" + getDefaultCharSet());

            log.info(" ::: Transfiriendo ticket a otro grupo");
            TransferenciaSFtoSDBean bean = new TransferenciaSFtoSDBean();
            bean.transferirTicket(inputVO);
            retorno.setCodigoRespuesta(CodeKeys.CODE_000_OK);
            retorno.setDescripcionRespuesta("La transferencia del ticket: "
                    + inputVO.getNumRefTicket() + " al grupo "
                    + inputVO.getNuevoGrupo() + " se realizó satisfactoriamente");
            retorno.setFechaRespuesta(CoreBean.fechaActual());
            retorno.setMensajeServicio("Se transfirió correctamente el ticket:"
                    + inputVO.getNumRefTicket() + " al grupo: " + inputVO.getNuevoGrupo());
            retorno.setRespuestaBoolean("true");
            log.info("   ::: " + retorno.getMensajeServicio());

        } catch (CoreException ex) {
            retorno.setCodigoRespuesta(ex.getIdError());
            retorno.setDescripcionRespuesta(ex.getMensaje());
            retorno.setFechaRespuesta(CoreBean.fechaActual());
            retorno.setMensajeServicio("No se logró transferir el ticket");
            retorno.setRespuestaBoolean("false");
            log.error(retorno.getDescripcionRespuesta(), ex);

        } catch (Exception ex) {
            retorno.setCodigoRespuesta(CodeKeys.CODE_970_DATABASE_ERROR_NC);
            retorno.setDescripcionRespuesta(ex.getLocalizedMessage());
            retorno.setFechaRespuesta(CoreBean.fechaActual());
            retorno.setMensajeServicio("No se logró transferir el ticket");
            retorno.setRespuestaBoolean("false");
            log.error(retorno.getDescripcionRespuesta(), ex);

        } catch (Throwable ex) {
            retorno.setCodigoRespuesta(CodeKeys.CODE_970_DATABASE_ERROR_NC);
            retorno.setDescripcionRespuesta(ex.getLocalizedMessage());
            retorno.setFechaRespuesta(CoreBean.fechaActual());
            retorno.setMensajeServicio("No se logró transferir el ticket");
            retorno.setRespuestaBoolean("false");
            log.error(retorno.getDescripcionRespuesta(), ex);
        }
        return retorno;
    }

    /**
     * PUT method for updating or creating an instance of
     * APIRestTransferirTicketTOGrupoIF
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    private static String getDefaultCharSet() {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
        String enc = writer.getEncoding();
        return enc;
    }
}
