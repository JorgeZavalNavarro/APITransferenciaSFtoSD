
package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.wssd.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="createAttachmentReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "createAttachmentReturn"
})
@XmlRootElement(name = "createAttachmentResponse")
public class CreateAttachmentResponse {

    @XmlElement(required = true)
    protected String createAttachmentReturn;

    /**
     * Obtiene el valor de la propiedad createAttachmentReturn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreateAttachmentReturn() {
        return createAttachmentReturn;
    }

    /**
     * Define el valor de la propiedad createAttachmentReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreateAttachmentReturn(String value) {
        this.createAttachmentReturn = value;
    }

}
