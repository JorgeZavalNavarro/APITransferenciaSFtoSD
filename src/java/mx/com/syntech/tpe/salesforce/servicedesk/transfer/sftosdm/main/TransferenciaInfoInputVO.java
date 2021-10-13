package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.main;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.core.CoreVO;

/**
 *
 * @author Jorge Zavala Navarro
 */
@XmlRootElement
public class TransferenciaInfoInputVO extends CoreVO {

    // Propiedades de la clase
    private String usuario = null;
    private String password = null;
    private String responsable = null;
    private String numRefTicket = null;
    private String numTicketSF = null;
    private String descripcion = null;
    private String nuevoGrupo = null;           // Nuevo grupo al cual se va a asignar el destinatario correspondiente

    // Métodos getters y setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getNumRefTicket() {
        return numRefTicket;
    }

    public void setNumRefTicket(String numRefTicket) {
        this.numRefTicket = numRefTicket;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNuevoGrupo() {
        return nuevoGrupo;
    }

    public void setNuevoGrupo(String nuevoGrupo) {
        this.nuevoGrupo = nuevoGrupo;
    }

    public String getNumTicketSF() {
        return numTicketSF;
    }

    public void setNumTicketSF(String numTicketSF) {
        this.numTicketSF = numTicketSF;
    }
    
    

    public String json() {
        String retorno = null;
        StringBuilder sbuilder = new StringBuilder();
        sbuilder.append("{");
        sbuilder.append("\"descripcion\"").append(" : \"").append(this.descripcion).append("\",");
        sbuilder.append("\"nuevoGrupo\"").append(" : \"").append(this.nuevoGrupo).append("\",");
        sbuilder.append("\"numRefTicket\"").append(" : \"").append(this.numRefTicket).append("\",");
        sbuilder.append("\"numTicketSF\"").append(" : \"").append(this.numTicketSF).append("\",");
        sbuilder.append("\"password\"").append(" : \"").append(this.password).append("\",");
        sbuilder.append("\"responsable\"").append(" : \"").append(this.responsable).append("\",");
        sbuilder.append("\"usuario\"").append(" : \"").append(this.usuario).append("\"");
        sbuilder.append("}");
        retorno = sbuilder.toString();
        return retorno;
    }

    public static String json(List<TransferenciaInfoInputVO> list) {
        String retorno = null;
        StringBuilder sbuilder = new StringBuilder();
        if (list != null && list.size() > 0) {

            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sbuilder.append("[").append(list.get(i).json());
                } else {
                    sbuilder.append(",").append(list.get(i).json());
                }
            }

            sbuilder.append("]");
        } else {
            sbuilder.append("sin informacion");
        }

        retorno = sbuilder.toString();
        return retorno;
    }

}
