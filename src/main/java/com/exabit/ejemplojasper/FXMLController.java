package com.exabit.ejemplojasper;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;

public class FXMLController implements Initializable {
    

    @FXML
    private AnchorPane anchorPane;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Se inicializar una lista que contendra los datos
        List<Persona> lstPersonas=new ArrayList<>();
        //Agregamos dos objetos de prueba. Estos se pueden reemplazar o completar con los datos de una consulta Persistence
        Persona p1=new Persona();
        p1.setNombres("Juan");
        p1.setApellidos("Perez");
        
        Persona p2=new Persona();
        p2.setNombres("Ana");
        p2.setApellidos("Diaz");
        
        //Se agregan los dos objetos a la lista
        lstPersonas.add(p1);
        lstPersonas.add(p2);
        
        try {
            //Cargamos el archivo de reporte al programa
            JasperReport jasper=(JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/jasper/ReportePersonas.jasper"));
            //Inicializamos el DataSource o fuente de datos con el que se va a llenar el reporte
            JRBeanCollectionDataSource datos=new JRBeanCollectionDataSource(lstPersonas);
            //Inicializarmos un mapa de parametros
            Map<String, Object> parametros=new HashMap<>();
            //Agregamos un parametro de ejemplo. Un supuesto usuario que genero el reporte
            parametros.put("usuario", "Administrador");
            
            //Creamos el JasperPrint. Que seria el reporte ya llenado con los datos
            JasperPrint jasperPrint=JasperFillManager.fillReport(jasper, parametros, datos);
            
            //Mostramos el reporte en la ventana
            SwingNode swNode=new SwingNode();
            AnchorPane.setTopAnchor(swNode, 0.0);
            AnchorPane.setBottomAnchor(swNode, 0.0);
            AnchorPane.setLeftAnchor(swNode, 0.0);
            AnchorPane.setRightAnchor(swNode, 0.0);
            
            
            JRViewer jrv=new JRViewer(jasperPrint);
            swNode.setContent(jrv);
            
            anchorPane.getChildren().add(swNode);
        } catch (JRException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, "No se puede cargar el archivo jasper", ex);
        }
       
    }    
}
