package com.bemedica.springboot.app.controllers;


import org.thymeleaf.context.Context;

import java.awt.Color;
import java.io.File;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bemedica.springboot.app.component.EmailHtmlSender;
import com.bemedica.springboot.app.component.EmailStatus;
import com.bemedica.springboot.app.controllers.TableTemplate;
import com.lowagie.text.Font;

@Controller
public class CorreoController {
	@Autowired
    private EmailHtmlSender emailHtmlSender;
	
	private static Font catFont = new Font(Font.TIMES_ROMAN, 18, Font.BOLD);
    
    private static Font redFont = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.RED);
    
    private static Font subFont = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
    
    private static Font smallBold = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
	
	@RequestMapping(value = "/enviar_cotizacion", method=RequestMethod.POST)
    public String sendmail(@RequestParam(value = "data") String jsonStr, @RequestParam(value = "correo") String correo, @RequestParam(value = "monto") int monto
) throws JSONException {
		//Se transforma a JSON el objeto recibido
		JSONArray jsonArray = new JSONArray(jsonStr); 
	
        String content = "Lista de Estudios\n";
        String nombre = "";
        String precio = "";
        String indicaciones = "";
        content += " \n";
        
        
        for (int i = 0; i < jsonArray.length(); i++)
		{
			try {
				
				JSONObject obj = jsonArray.getJSONObject(i);
				nombre += "Nombre: " + obj.getString("nom") + " ";
				precio += "Cantidad: " + obj.getInt("can") + " Precio: " + obj.getInt("precio") + " ";
				int sub = (obj.getInt("precio")) * (obj.getInt("can"));
				content += "Subtotal: " + sub;
				content += "Tipo: " + obj.getString("tipo");
				indicaciones += "Indicaciones: " + obj.getString("info") + " ";
			
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		precio += "Monto Total: " + monto;

		Context context = new Context();
	    context.setVariable("nombre", nombre);
	    context.setVariable("precio", precio);
	    context.setVariable("indicaciones", indicaciones);
	    
	  //Se bautiza el archivo jsjs
	    String DEST = "pdf_media/CotizacionDeEstudios.pdf";
	    
	    PDFMain(jsonArray, monto, DEST);
	    	    
	    FileSystemResource archivo = new FileSystemResource(new File(DEST));
	    context.setVariable("pdfxd", archivo);
	        
        EmailStatus emailStatus = emailHtmlSender.send(correo, "Cotización de estudios", "email/enviar", context, DEST);
        return "index";

    }
	public String PDFMain(JSONArray jsonArray, int monto, String DEST) 
	{	
		try 
		{
			TableTemplate tt = new TableTemplate();
			tt.createPdf(jsonArray, monto, DEST);
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
        }
		
		
		return "index";
	}
}
