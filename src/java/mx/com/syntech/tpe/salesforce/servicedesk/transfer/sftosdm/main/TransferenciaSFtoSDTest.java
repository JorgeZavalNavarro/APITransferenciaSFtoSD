/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.syntech.tpe.salesforce.servicedesk.transfer.sftosdm.main;

/**
 *
 * @author Jorge Zavala Navarro
 */
public class TransferenciaSFtoSDTest {

    public static void main(String... params) {

        try {
            TransferenciaInfoInputVO entrada = new TransferenciaInfoInputVO();
            entrada.setDescripcion("Descripción con la causa de la transferencia.");
            entrada.setNuevoGrupo("cnt:067F9D34304CEC4C889696A660D06453");
            entrada.setNumRefTicket("cr:1470814");
            entrada.setPassword("DeskService01");
            entrada.setResponsable("cnt:E5EDC774C826EC40AF16CE44689F7A73");
            entrada.setUsuario("servicedesk");

            TransferenciaSFtoSDBean bean = new TransferenciaSFtoSDBean();
            bean.transferirTicket(entrada);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
