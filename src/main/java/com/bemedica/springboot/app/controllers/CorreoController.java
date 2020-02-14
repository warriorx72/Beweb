package com.bemedica.springboot.app.controllers;


import org.thymeleaf.context.Context;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bemedica.springboot.app.component.EmailHtmlSender;
import com.bemedica.springboot.app.component.EmailStatus;

@Controller
public class CorreoController {
	@Autowired
    private EmailHtmlSender emailHtmlSender;
	
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
         
        EmailStatus emailStatus = emailHtmlSender.send(correo, "Cotización de estudios", "email/enviar", context);
        
        return "index";

    }
}
