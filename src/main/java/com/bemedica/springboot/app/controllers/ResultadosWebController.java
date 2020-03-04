package com.bemedica.springboot.app.controllers;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bemedica.springboot.app.Dao.IAntibiogramasDao;
import com.bemedica.springboot.app.Dao.IResultadosWebDao;
import com.bemedica.springboot.app.Dao.IResultadosAntiDao;
import com.bemedica.springboot.app.models.entity.Resultados;
import com.bemedica.springboot.app.models.entity.ResultadosAnti;
import com.bemedica.springboot.app.service.UserService;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ResultadosWebController {
	
	
	@Autowired
	@Qualifier("ResultadosDaoJPA")
	private IResultadosWebDao ResultadosDao;
	
	
	@Autowired
	private IAntibiogramasDao AntibiogramasDao;
	
	@Autowired
	private IResultadosAntiDao ResultadosAntiDao;
	
	@Autowired
	private ServletContext context;
	
	
	
	@Autowired
    UserService userService;
	
	

	String nombrepdf;

	

	@RequestMapping(value = "/descargar_resultados/{id}")
	public void listaar(@PathVariable(value = "id") Long id, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Long aux = 1000000 + id;
		Date ahora = new Date();
		SimpleDateFormat formateador = new SimpleDateFormat("yyyyMMdd-hhmmss");
		nombrepdf = "ORD" + aux + "-" + formateador.format(ahora);
		String fullPaths = request.getServletContext().getRealPath("/" + nombrepdf + ".pdf");
		this.pdf(request, id, fullPaths);
		filedownloadPDF(fullPaths, response, id);

	}

	public void pdf(HttpServletRequest request, Long id, String fullPath)
			throws FileNotFoundException, DocumentException {
		// String fullPath = request.getServletContext().getRealPath("/" + "Resultado" +
		// ".pdf");
		Font fuen_1 = new Font(Font.HELVETICA, 9.0f, Font.BOLD, Color.black);

		Font fuen_2 = new Font(Font.HELVETICA, 9.0f, Font.NORMAL, Color.BLACK);

		Font fuen_comentario = new Font(Font.HELVETICA, 7.0f, Font.NORMAL, Color.BLACK);
		Font fuen_validacion = new Font(Font.HELVETICA, 7.0f, Font.NORMAL, Color.BLACK);

		// Document documento = new Document();
		Document documento = new Document(PageSize.A4, 36, 36, 182, 120);

		FileOutputStream ficheroPdf = new FileOutputStream(fullPath);
		PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

		String fecha = null;
		String medico = null;
		String pacienten = null;
		String folio = null;
		String edad = null;
		String sexo = null;
		String x=null;
		
		String x2=null;
		
		
		
		List<Object[]> paciente = ResultadosDao.PacienteOrden(id);

		for (Object[] p : paciente) {

			fecha = p[5].toString();

			if (p[7].toString() == null) {
				medico = "A QUIEN CORRESPONDA";
			} else {
				medico = p[7].toString();
			}

			pacienten = p[1].toString();
			folio = p[6].toString();

			if (p[2].toString() == "0") {
				edad = p[3].toString() + " mes(es)";
			} else {
				edad = p[2].toString() + " año(s)";
			}

			if (p[2].toString() == "0" && p[3].toString() == "0") {
				if (p[10].toString() == "0") {
					edad = "Recien nacido";
				} else {
					edad = p[10].toString() + " día(s)";
				}
			}

			sexo = p[4].toString();

		}

		HeaderFooterPageEvent event = new HeaderFooterPageEvent(fecha, medico, pacienten, folio, edad, sexo);
		writer.setPageEvent(event);
		documento.open();

		PdfPTable tableR = new PdfPTable(4);

		tableR.setWidths(new float[] { 40.0f, 20.0f, 20.0f, 30.0f });

		//tableR.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableR.setWidthPercentage(100);
		tableR.setHeaderRows(1);

		Paragraph Estudio = new Paragraph("ESTUDIO",fuen_2);
		Paragraph Resultado = new Paragraph("RESULTADO",fuen_2);
		Paragraph Espacio = new Paragraph("",fuen_2);
		Paragraph VR = new Paragraph("VALORES DE REFERENCIA",fuen_2);

		PdfPCell ce = new PdfPCell(Estudio);
		PdfPCell cr = new PdfPCell(Resultado);
		PdfPCell esp = new PdfPCell(Espacio);
		PdfPCell cvr = new PdfPCell(VR);

		ce.setHorizontalAlignment(Element.ALIGN_LEFT);
		//ce.disableBorderSide(Rectangle.BOX);
		ce.setBorder(Rectangle.BOTTOM);
		ce.setExtraParagraphSpace(5f);

		cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
		//cr.disableBorderSide(Rectangle.BOX);
		cr.setBorder(Rectangle.BOTTOM);
		cr.setExtraParagraphSpace(5f);

		cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
		//cvr.disableBorderSide(Rectangle.BOX);
		cvr.setBorder(Rectangle.BOTTOM);
		cvr.setExtraParagraphSpace(5f);

		esp.setHorizontalAlignment(Element.ALIGN_CENTER);
		//esp.disableBorderSide(Rectangle.BOX);
		esp.setBorder(Rectangle.BOTTOM);
		esp.setExtraParagraphSpace(5f);
		tableR.addCell(ce);
		tableR.addCell(cr);
		tableR.addCell(esp);
		tableR.addCell(cvr);

		List<Object[]> LineasOrdenes = ResultadosDao.LineasOrden(id);
		for (Object[] lo : LineasOrdenes) {

			System.out.print(lo[0] + " ");
			System.out.print(lo[1] + " ");
			System.out.print(lo[2] + " ");
			System.out.print(lo[3] + " ");
			System.out.print(lo[4] + " ");
			System.out.print(lo[5] + " ");
			System.out.print(lo[6] + " ");
			System.out.print(lo[7] + " ");
			System.out.print(lo[8] + " ");
			System.out.println(" ");

			if (lo[8].toString().equals("estudio")) {
				List<Object[]> estudio = ResultadosDao.Resultados(Long.valueOf(lo[0].toString()));
				
				for (Object[] e : estudio) {
					String Valor = "";
					
					if (!e[7].equals(" ")) {
						Valor = Valor + e[7].toString() + "\n";
					}
					if (!e[8].equals(" ")) {
						Valor = Valor + e[8].toString() + "\n";
					}
					if (!e[9].equals(" ")) {
						Valor = Valor + e[9].toString() + "\n";
					}
					if (!e[10].equals(" ")) {
						Valor = Valor + e[10].toString() + "\n";
					}
					if (!e[11].equals(" ")) {
						Valor = Valor + e[11].toString() + "\n";
					}
					if (!e[12].equals(" ")) {
						Valor = Valor + e[12].toString() + "\n";
					}
					if (!e[13].equals(" ")) {
						Valor = Valor + e[13].toString() + "\n";
					}

					Estudio = new Paragraph(e[2].toString(), fuen_1);
					Resultado = new Paragraph(e[6].toString(), fuen_2);
					Espacio = new Paragraph("  ");
					VR = new Paragraph(Valor, fuen_2);
					Paragraph Comentario = null;

					if (e[0].toString().equals("") || e[0].toString().equals(null)) {
						Comentario = new Paragraph("");
					} else {
						Comentario = new Paragraph("OBSERVACIONES\n" + e[0].toString(), fuen_comentario);
					}

					ce = new PdfPCell(Estudio);
					cr = new PdfPCell(Resultado);
					esp = new PdfPCell(Espacio);
					cvr = new PdfPCell(VR);

					ce.setHorizontalAlignment(Element.ALIGN_LEFT);
					ce.disableBorderSide(Rectangle.BOX);
					ce.setExtraParagraphSpace(1.5f);

					cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cr.disableBorderSide(Rectangle.BOX);
					cr.setExtraParagraphSpace(1.5f);

					esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
					esp.disableBorderSide(Rectangle.BOX);
					esp.setExtraParagraphSpace(1.5f);

					cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
					cvr.disableBorderSide(Rectangle.BOX);
					cvr.setExtraParagraphSpace(1.5f);
					
					
					

					Paragraph Validacion = new Paragraph("Estudio(s) validado por:"
							
							
							+ResultadosDao.NombreVal(Long.parseLong(e[14].toString()))
							,fuen_validacion);

					PdfPCell cell = new PdfPCell(Comentario);

					PdfPCell cell2 = new PdfPCell(Validacion);

					cell.setBorder(PdfPCell.NO_BORDER);
					cell2.setBorder(PdfPCell.NO_BORDER);
					tableR.addCell(ce);
					tableR.addCell(cr);
					tableR.addCell(esp);
					tableR.addCell(cvr);
					tableR.addCell(cell);
					tableR.addCell(esp);
					tableR.addCell(esp);
					tableR.addCell(esp);
					tableR.addCell(cell2);
					tableR.addCell(esp);
					tableR.addCell(esp);
					tableR.addCell(esp);
				}
			}
			if (lo[8].toString().equals("perfil")) {
				Paragraph perfilTitulo;
				Paragraph perfilTituloR;
				Paragraph perfilTituloE;
				Paragraph perfilTituloVR;
				List<Object[]> perfil = ResultadosDao.Perfil((Long.valueOf(lo[7].toString())));
				String auxComentario = "";
				int contador=0;	
			
				for (Object[] aux : perfil) {
					
					
				
					if ( aux[3].toString().equals("estudio")){
						
					
					List<Object[]> estudio = ResultadosDao.Resultados(Long.valueOf(lo[0].toString()));
					
					for (Object[] e : estudio) {
						contador++;
					
						x=ResultadosDao.NombreVal(Long.parseLong(e[14].toString()));
						
						
						
						
						
						perfilTitulo = new Paragraph(lo[3].toString(), fuen_1);
						perfilTituloR = new Paragraph("");
						perfilTituloE = new Paragraph("  ");
						perfilTituloVR = new Paragraph("");
						
						PdfPCell cep = new PdfPCell(perfilTitulo);
						PdfPCell crp = new PdfPCell(perfilTituloR);
						PdfPCell espp = new PdfPCell(perfilTituloE);
						PdfPCell cvrp = new PdfPCell(perfilTituloVR);
						
						cep.setHorizontalAlignment(Element.ALIGN_LEFT);
						cep.disableBorderSide(Rectangle.BOX);
						cep.setExtraParagraphSpace(1.5f);

						crp.setHorizontalAlignment(Element.ALIGN_RIGHT);
						crp.disableBorderSide(Rectangle.BOX);
						crp.setExtraParagraphSpace(1.5f);

						espp.setHorizontalAlignment(Element.ALIGN_RIGHT);
						espp.disableBorderSide(Rectangle.BOX);
						espp.setExtraParagraphSpace(1.5f);

						cvrp.setHorizontalAlignment(Element.ALIGN_LEFT);
						cvrp.disableBorderSide(Rectangle.BOX);
						cvrp.setExtraParagraphSpace(1.5f);
						
						if (contador==1) {
						tableR.addCell(cep);
						tableR.addCell(crp);
						tableR.addCell(espp);
						tableR.addCell(cvrp);}
						
						
						String Valor = "";

						if (!e[7].equals(" ")) {
							Valor = Valor + e[7].toString() + "\n";
						}
						if (!e[8].equals(" ")) {
							Valor = Valor + e[8].toString() + "\n";
						}
						if (!e[9].equals(" ")) {
							Valor = Valor + e[9].toString() + "\n";
						}
						if (!e[10].equals(" ")) {
							Valor = Valor + e[10].toString() + "\n";
						}
						if (!e[11].equals(" ")) {
							Valor = Valor + e[11].toString() + "\n";
						}
						if (!e[12].equals(" ")) {
							Valor = Valor + e[12].toString() + "\n";
						}
						if (!e[13].equals(" ")) {
							Valor = Valor + e[13].toString() + "\n";
						}
						Estudio = new Paragraph("	" + e[2].toString(), fuen_2);
						Resultado = new Paragraph(e[6].toString(), fuen_2);
						Espacio = new Paragraph("  ");
						VR = new Paragraph(Valor, fuen_2);

						if (e[0].toString().equals("") || e[0].toString().equals(null)) {
						} 
						else {
							if (auxComentario == "") {
								auxComentario = e[0].toString();

							} else {
								auxComentario +=  ", " + e[0].toString();

							}
						}

						ce = new PdfPCell(Estudio);
						cr = new PdfPCell(Resultado);
						esp = new PdfPCell(Espacio);
						cvr = new PdfPCell(VR);

						ce.setHorizontalAlignment(Element.ALIGN_LEFT);
						ce.disableBorderSide(Rectangle.BOX);
						ce.setExtraParagraphSpace(1.5f);

						cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cr.disableBorderSide(Rectangle.BOX);
						cr.setExtraParagraphSpace(1.5f);

						esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
						esp.disableBorderSide(Rectangle.BOX);
						esp.setExtraParagraphSpace(1.5f);

						cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
						cvr.disableBorderSide(Rectangle.BOX);
						cvr.setExtraParagraphSpace(1.5f);

						tableR.addCell(ce);
						tableR.addCell(cr);
						tableR.addCell(esp);
						tableR.addCell(cvr);

					}
					
				 }
					if ( aux[3].toString().equals("cultivo")){ // eeee
						System.out.println("hello i am cultivo");
						List<Object[]> estudio = ResultadosAntiDao.ResultadoCultivo((Long.valueOf(lo[4].toString())), (Long.valueOf(aux[0].toString())) );
						
						for (Object[] e : estudio) {
							
							x2=ResultadosDao.NombreVal(Long.parseLong(e[2].toString()));
							
						
							
							// x2= " el valor de x2 es igual Leon";
							
						
							String Valor = "";

							Estudio = new Paragraph("	"+e[0].toString(), fuen_1);
							Resultado = new Paragraph(e[1].toString(), fuen_2);
							Espacio = new Paragraph("  ");
							VR = new Paragraph(Valor, fuen_2);
							

						

							ce = new PdfPCell(Estudio);
							cr = new PdfPCell(Resultado);
							esp = new PdfPCell(Espacio);
							cvr = new PdfPCell(VR);

							ce.setHorizontalAlignment(Element.ALIGN_LEFT);
							ce.disableBorderSide(Rectangle.BOX);
							ce.setExtraParagraphSpace(1.5f);

							cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cr.disableBorderSide(Rectangle.BOX);
							cr.setExtraParagraphSpace(1.5f);

							esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
							esp.disableBorderSide(Rectangle.BOX);
							esp.setExtraParagraphSpace(1.5f);

							cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
							cvr.disableBorderSide(Rectangle.BOX);
							cvr.setExtraParagraphSpace(1.5f);

							

							tableR.addCell(ce);
							tableR.addCell(cr);
							tableR.addCell(esp);
							tableR.addCell(cvr);
							
							
							
							if (!e[1].toString().equals("Negativo")) {
								
								String NombreAnti = ResultadosAntiDao.NombreAntibiograma ((Long.valueOf(lo[4].toString())), (Long.valueOf(aux[0].toString())) );
								
								List<Object[]> antibiograma = ResultadosAntiDao.ResultadoAntibio((Long.valueOf(lo[4].toString())), (Long.valueOf(aux[0].toString())) );
								
								Estudio = new Paragraph("    Antibiograma: "+NombreAnti, fuen_1);
								Resultado = new Paragraph("", fuen_2);
								Espacio = new Paragraph("  ");
								VR = new Paragraph(Valor, fuen_2);
								

							

								ce = new PdfPCell(Estudio);
								cr = new PdfPCell(Resultado);
								esp = new PdfPCell(Espacio);
								cvr = new PdfPCell(VR);

								ce.setHorizontalAlignment(Element.ALIGN_LEFT);
								ce.disableBorderSide(Rectangle.BOX);
								ce.setExtraParagraphSpace(1.5f);

								cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cr.disableBorderSide(Rectangle.BOX);
								cr.setExtraParagraphSpace(1.5f);

								esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
								esp.disableBorderSide(Rectangle.BOX);
								esp.setExtraParagraphSpace(1.5f);

								cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
								cvr.disableBorderSide(Rectangle.BOX);
								cvr.setExtraParagraphSpace(1.5f);
								
								tableR.addCell(ce);
								tableR.addCell(cr);
								tableR.addCell(esp);
								tableR.addCell(cvr);
								
								
								for (Object[] a : antibiograma) {
						
									Estudio = new Paragraph("      "+a[0].toString(), fuen_2	);
									Resultado = new Paragraph(a[1].toString(), fuen_2);
									Espacio = new Paragraph("  ");
									VR = new Paragraph("", fuen_2);
								

								

									ce = new PdfPCell(Estudio);
									cr = new PdfPCell(Resultado);
									esp = new PdfPCell(Espacio);
									cvr = new PdfPCell(VR);

									ce.setHorizontalAlignment(Element.ALIGN_LEFT);
									ce.disableBorderSide(Rectangle.BOX);
									ce.setExtraParagraphSpace(1.5f);

									cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cr.disableBorderSide(Rectangle.BOX);
									cr.setExtraParagraphSpace(1.5f);

									esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
									esp.disableBorderSide(Rectangle.BOX);
									esp.setExtraParagraphSpace(1.5f);

									cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
									cvr.disableBorderSide(Rectangle.BOX);
									cvr.setExtraParagraphSpace(1.5f);
									tableR.addCell(ce);
									tableR.addCell(cr);
									tableR.addCell(esp);
									tableR.addCell(cvr);
									
								}
							}
							
						
						}
							
						
						}
					
					
					
			
					
					}
				
				
				
				
				
				esp = new PdfPCell(Espacio);
				esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
				esp.disableBorderSide(Rectangle.BOX);
				esp.setExtraParagraphSpace(1.5f);
			
				
				
				
				Paragraph Validacion = new Paragraph("Estudio(s) validado por: "+x2
						,fuen_validacion);
				
			
				
				
				PdfPCell cell_validacion = new  PdfPCell(Validacion);
				
				Paragraph Comentario;
				if ( auxComentario.equals(null ) || auxComentario.equals("") ){
					Comentario = new Paragraph("");
				}
				else {
					Comentario = new Paragraph("   OBSERVACIONES:\n    " + auxComentario, fuen_comentario);
				}
				
				PdfPCell cell_comentario = new  PdfPCell(Comentario);
				
				cell_validacion.setBorder(PdfPCell.NO_BORDER);
				cell_validacion.disableBorderSide(Rectangle.BOX);
				cell_validacion.setExtraParagraphSpace(1.5f);
				cell_comentario.setBorder(PdfPCell.NO_BORDER);
				cell_comentario.disableBorderSide(Rectangle.BOX);
				cell_comentario.setExtraParagraphSpace(1.5f);
				
				tableR.addCell(cell_comentario);
				tableR.addCell(esp);
				tableR.addCell(esp);
				tableR.addCell(esp);
				tableR.addCell(cell_validacion);
				tableR.addCell(esp);
				tableR.addCell(esp);
				tableR.addCell(esp);
				}
			
			
				
				
				
				
			if (lo[8].toString().equals("paquete")) {
		
				int contadorTitulo=0;
				Paragraph paqueteTitulo;
				Paragraph paqueteTituloR;
				Paragraph paqueteTituloE;
				Paragraph paqueteTituloVR;
				

				List<Object[]> paquete = ResultadosDao.Paquete((Long.valueOf(lo[7].toString())));

				for (Object[] aux : paquete) {
					
					// verifica que exista un estudio individual
					
					if (aux[3].toString().equals("null")) {

						List<Object[]> estudio = ResultadosDao.Resultados(Long.valueOf(lo[0].toString()));
						for (Object[] e : estudio) {
							String Valor = "";
							
							x=ResultadosDao.NombreVal(Long.parseLong(e[14].toString()));
							
							contadorTitulo++;
							
							paqueteTitulo = new Paragraph(lo[3].toString(), fuen_1);
							paqueteTituloR = new Paragraph("");
							paqueteTituloE = new Paragraph("  ");
							paqueteTituloVR = new Paragraph("");
							
							PdfPCell cepa = new PdfPCell(paqueteTitulo);
							PdfPCell crpa = new PdfPCell(paqueteTituloR);
							PdfPCell esppa = new PdfPCell(paqueteTituloE);
							PdfPCell cvrpa = new PdfPCell(paqueteTituloVR);
							
							cepa.setHorizontalAlignment(Element.ALIGN_LEFT);
							cepa.disableBorderSide(Rectangle.BOX);
							cepa.setExtraParagraphSpace(1.5f);

							crpa.setHorizontalAlignment(Element.ALIGN_RIGHT);
							crpa.disableBorderSide(Rectangle.BOX);
							crpa.setExtraParagraphSpace(1.5f);

							esppa.setHorizontalAlignment(Element.ALIGN_RIGHT);
							esppa.disableBorderSide(Rectangle.BOX);
							esppa.setExtraParagraphSpace(1.5f);

							cvrpa.setHorizontalAlignment(Element.ALIGN_LEFT);
							cvrpa.disableBorderSide(Rectangle.BOX);
							cvrpa.setExtraParagraphSpace(1.5f);
							
							if (contadorTitulo==1) {
							tableR.addCell(cepa);
							tableR.addCell(crpa);
							tableR.addCell(esppa);
							tableR.addCell(cvrpa);}
							

							if (!e[7].equals(" ")) {Valor = Valor + e[7].toString() + "\n";}
							if (!e[8].equals(" ")) {Valor = Valor + e[8].toString() + "\n";}
							if (!e[9].equals(" ")) {Valor = Valor + e[9].toString() + "\n";}
							if (!e[10].equals(" ")) {Valor = Valor + e[10].toString() + "\n";}
							if (!e[11].equals(" ")) {Valor = Valor + e[11].toString() + "\n";}
							if (!e[12].equals(" ")) {Valor = Valor + e[12].toString() + "\n";}
							if (!e[13].equals(" ")) {Valor = Valor + e[13].toString() + "\n";}
							
							Estudio = new Paragraph("   " + e[2].toString(), fuen_2);
							Resultado = new Paragraph(e[6].toString(), fuen_2);
							Espacio = new Paragraph("  ");
							VR = new Paragraph(Valor, fuen_2);
							Paragraph Comentario = null;

							if (e[0].toString().equals("") || e[0].toString().equals(null)) {
								Comentario = new Paragraph("");
							} else {
								Comentario = new Paragraph("   OBSERVACIONES\n   " + e[0].toString(), fuen_comentario);
							}

							ce = new PdfPCell(Estudio);
							cr = new PdfPCell(Resultado);
							esp = new PdfPCell(Espacio);
							cvr = new PdfPCell(VR);

							ce.setHorizontalAlignment(Element.ALIGN_LEFT);
							ce.disableBorderSide(Rectangle.BOX);
							ce.setExtraParagraphSpace(1.5f);

							cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cr.disableBorderSide(Rectangle.BOX);
							cr.setExtraParagraphSpace(1.5f);

							esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
							esp.disableBorderSide(Rectangle.BOX);
							esp.setExtraParagraphSpace(1.5f);

							cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
							cvr.disableBorderSide(Rectangle.BOX);
							cvr.setExtraParagraphSpace(1.5f);

							Paragraph Validacion = new Paragraph("Estudio(s) validado por:"
									
									
							+ResultadosDao.NombreVal(Long.parseLong(e[14].toString()))
							,fuen_validacion);
							PdfPCell cell = new PdfPCell(Comentario);
							PdfPCell cell2 = new PdfPCell(Validacion);

							cell.setBorder(PdfPCell.NO_BORDER);
							cell2.setBorder(PdfPCell.NO_BORDER);
							tableR.addCell(ce);
							tableR.addCell(cr);
							tableR.addCell(esp);
							tableR.addCell(cvr);
							tableR.addCell(cell);
							tableR.addCell(esp);
							tableR.addCell(esp);
							tableR.addCell(esp);
							tableR.addCell(cell2);
							tableR.addCell(esp);
							tableR.addCell(esp);
							tableR.addCell(esp);
						}

					} // cierra el if de los estudios individuales 

					else { // verifica que exista perfiles en el paquete. 
						
							if (contadorTitulo == 0) {
							
							contadorTitulo++;
							paqueteTitulo = new Paragraph(lo[3].toString(), fuen_1);
							paqueteTituloR = new Paragraph("");
							paqueteTituloE = new Paragraph("  ");
							paqueteTituloVR = new Paragraph("");
							
							PdfPCell cepa = new PdfPCell(paqueteTitulo);
							PdfPCell crpa = new PdfPCell(paqueteTituloR);
							PdfPCell esppa = new PdfPCell(paqueteTituloE);
							PdfPCell cvrpa = new PdfPCell(paqueteTituloVR);
							
							cepa.setHorizontalAlignment(Element.ALIGN_LEFT);
							cepa.disableBorderSide(Rectangle.BOX);
							cepa.setExtraParagraphSpace(1.5f);

							crpa.setHorizontalAlignment(Element.ALIGN_RIGHT);
							crpa.disableBorderSide(Rectangle.BOX);
							crpa.setExtraParagraphSpace(1.5f);

							esppa.setHorizontalAlignment(Element.ALIGN_RIGHT);
							esppa.disableBorderSide(Rectangle.BOX);
							esppa.setExtraParagraphSpace(1.5f);

							cvrpa.setHorizontalAlignment(Element.ALIGN_LEFT);
							cvrpa.disableBorderSide(Rectangle.BOX);
							cvrpa.setExtraParagraphSpace(1.5f);
							
							if (contadorTitulo==1) {
							tableR.addCell(cepa);
							tableR.addCell(crpa);
							tableR.addCell(esppa);
							tableR.addCell(cvrpa);
							}
							
						}
						
						String auxComentario2 = "";
						Paragraph perfilTitulo;
						Paragraph perfilTituloR;
						Paragraph perfilTituloE;
						Paragraph perfilTituloVR;

						List<Object[]> perfil = ResultadosDao.Perfil((Long.valueOf(aux[3].toString())));
						
						int contadoraux =0;
						
					
						for (Object[] aux2 : perfil) {
						
							
							if ( aux2[3].toString().equals("estudio")){
							List<Object[]> estudio = ResultadosDao.Resultados(Long.valueOf(lo[0].toString()));
							for (Object[] e : estudio) {
								String Valor = "";
								
								
								x=ResultadosDao.NombreVal(Long.parseLong(e[14].toString()));
								
								contadoraux++;
								perfilTitulo = new Paragraph("    "+aux[4].toString(), fuen_1);
								perfilTituloR = new Paragraph("");
								perfilTituloE = new Paragraph("  ");
								perfilTituloVR = new Paragraph("");
								
								PdfPCell cep = new PdfPCell(perfilTitulo);
								PdfPCell crp = new PdfPCell(perfilTituloR);
								PdfPCell espp = new PdfPCell(perfilTituloE);
								PdfPCell cvrp = new PdfPCell(perfilTituloVR);
								
								cep.setHorizontalAlignment(Element.ALIGN_LEFT);
								cep.disableBorderSide(Rectangle.BOX);
								cep.setExtraParagraphSpace(1.5f);

								crp.setHorizontalAlignment(Element.ALIGN_RIGHT);
								crp.disableBorderSide(Rectangle.BOX);
								crp.setExtraParagraphSpace(1.5f);

								espp.setHorizontalAlignment(Element.ALIGN_RIGHT);
								espp.disableBorderSide(Rectangle.BOX);
								espp.setExtraParagraphSpace(1.5f);

								cvrp.setHorizontalAlignment(Element.ALIGN_LEFT);
								cvrp.disableBorderSide(Rectangle.BOX);
								cvrp.setExtraParagraphSpace(1.5f);
								
								if (contadoraux==1) {
								tableR.addCell(cep);
								tableR.addCell(crp);
								tableR.addCell(espp);
								tableR.addCell(cvrp);
								
								}

								if (!e[7].equals(" ")) {Valor = Valor + e[7].toString() + "\n";}
								if (!e[8].equals(" ")) {Valor = Valor + e[8].toString() + "\n";}
								if (!e[9].equals(" ")) {Valor = Valor + e[9].toString() + "\n";}
								if (!e[10].equals(" ")) {Valor = Valor + e[10].toString() + "\n";}
								if (!e[11].equals(" ")) {Valor = Valor + e[11].toString() + "\n";}
								if (!e[12].equals(" ")) {Valor = Valor + e[12].toString() + "\n";}
								if (!e[13].equals(" ")) {Valor = Valor + e[13].toString() + "\n";}

								Estudio = new Paragraph("     " + e[2].toString(), fuen_2);
								Resultado = new Paragraph(e[6].toString(), fuen_2);
								Espacio = new Paragraph("  ");
								VR = new Paragraph(Valor, fuen_2);

								if (e[0].toString().equals("") || e[0].toString().equals(null)) {
								} else {
									if (auxComentario2 == "") {
										auxComentario2 = auxComentario2 + e[0].toString();

									} else {
										auxComentario2 += auxComentario2 + ", " + e[0].toString();

									}
								}

								ce = new PdfPCell(Estudio);
								cr = new PdfPCell(Resultado);
								esp = new PdfPCell(Espacio);
								cvr = new PdfPCell(VR);

								ce.setHorizontalAlignment(Element.ALIGN_LEFT);
								ce.disableBorderSide(Rectangle.BOX);
								ce.setExtraParagraphSpace(1.5f);

								cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cr.disableBorderSide(Rectangle.BOX);
								cr.setExtraParagraphSpace(1.5f);

								esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
								esp.disableBorderSide(Rectangle.BOX);
								esp.setExtraParagraphSpace(1.5f);

								cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
								cvr.disableBorderSide(Rectangle.BOX);
								cvr.setExtraParagraphSpace(1.5f);

								tableR.addCell(ce);
								tableR.addCell(cr);
								tableR.addCell(esp);
								tableR.addCell(cvr);

							}
						}
							
							if ( aux2[3].toString().equals("cultivo")) {
								// eeee
								System.out.println("hello i am cultivo");
								List<Object[]> estudio = ResultadosAntiDao.ResultadoCultivo((Long.valueOf(lo[4].toString())), (Long.valueOf(aux2[0].toString())) );
								
								for (Object[] e : estudio) {
									if (contadorTitulo == 0) {
									
										contadorTitulo++;
										paqueteTitulo = new Paragraph(lo[3].toString(), fuen_1);
										paqueteTituloR = new Paragraph("");
										paqueteTituloE = new Paragraph("  ");
										paqueteTituloVR = new Paragraph("");
										
										PdfPCell cepa = new PdfPCell(paqueteTitulo);
										PdfPCell crpa = new PdfPCell(paqueteTituloR);
										PdfPCell esppa = new PdfPCell(paqueteTituloE);
										PdfPCell cvrpa = new PdfPCell(paqueteTituloVR);
										
										cepa.setHorizontalAlignment(Element.ALIGN_LEFT);
										cepa.disableBorderSide(Rectangle.BOX);
										cepa.setExtraParagraphSpace(1.5f);

										crpa.setHorizontalAlignment(Element.ALIGN_RIGHT);
										crpa.disableBorderSide(Rectangle.BOX);
										crpa.setExtraParagraphSpace(1.5f);

										esppa.setHorizontalAlignment(Element.ALIGN_RIGHT);
										esppa.disableBorderSide(Rectangle.BOX);
										esppa.setExtraParagraphSpace(1.5f);

										cvrpa.setHorizontalAlignment(Element.ALIGN_LEFT);
										cvrpa.disableBorderSide(Rectangle.BOX);
										cvrpa.setExtraParagraphSpace(1.5f);
										
										if (contadorTitulo==1) {
										tableR.addCell(cepa);
										tableR.addCell(crpa);
										tableR.addCell(esppa);
										tableR.addCell(cvrpa);
										}
										
									}
									
									String Valor = "";
									
									Estudio = new Paragraph("     "+e[0].toString(), fuen_1);
									Resultado = new Paragraph(e[1].toString(), fuen_2);
									Espacio = new Paragraph("  ");
									VR = new Paragraph(Valor, fuen_2);
									ce = new PdfPCell(Estudio);
									cr = new PdfPCell(Resultado);
									esp = new PdfPCell(Espacio);
									cvr = new PdfPCell(VR);

									ce.setHorizontalAlignment(Element.ALIGN_LEFT);
									ce.disableBorderSide(Rectangle.BOX);
									ce.setExtraParagraphSpace(1.5f);

									cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cr.disableBorderSide(Rectangle.BOX);
									cr.setExtraParagraphSpace(1.5f);

									esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
									esp.disableBorderSide(Rectangle.BOX);
									esp.setExtraParagraphSpace(1.5f);

									cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
									cvr.disableBorderSide(Rectangle.BOX);
									cvr.setExtraParagraphSpace(1.5f);

									

									tableR.addCell(ce);
									tableR.addCell(cr);
									tableR.addCell(esp);
									tableR.addCell(cvr);
									
									
									
									if (!e[1].toString().equals("Negativo")) {
										String NombreAnti = ResultadosAntiDao.NombreAntibiograma ((Long.valueOf(lo[4].toString())), (Long.valueOf(aux2[0].toString())) );
										List<Object[]> antibiograma = ResultadosAntiDao.ResultadoAntibio((Long.valueOf(lo[4].toString())), (Long.valueOf(aux2[0].toString())) );
										
										Estudio = new Paragraph("         Antibiograma:"+NombreAnti, fuen_1);
										Resultado = new Paragraph("", fuen_2);
										Espacio = new Paragraph("  ");
										VR = new Paragraph(Valor, fuen_2);
										ce = new PdfPCell(Estudio);
										cr = new PdfPCell(Resultado);
										esp = new PdfPCell(Espacio);
										cvr = new PdfPCell(VR);

										ce.setHorizontalAlignment(Element.ALIGN_LEFT);
										ce.disableBorderSide(Rectangle.BOX);
										ce.setExtraParagraphSpace(1.5f);

										cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
										cr.disableBorderSide(Rectangle.BOX);
										cr.setExtraParagraphSpace(1.5f);

										esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
										esp.disableBorderSide(Rectangle.BOX);
										esp.setExtraParagraphSpace(1.5f);

										cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
										cvr.disableBorderSide(Rectangle.BOX);
										cvr.setExtraParagraphSpace(1.5f);
										
										tableR.addCell(ce);
										tableR.addCell(cr);
										tableR.addCell(esp);
										tableR.addCell(cvr);
										
										
										for (Object[] a : antibiograma) {
								
											Estudio = new Paragraph("           "+a[0].toString(), fuen_2	);
											Resultado = new Paragraph(a[1].toString(), fuen_2);
											Espacio = new Paragraph("  ");
											VR = new Paragraph("", fuen_2);
											ce = new PdfPCell(Estudio);
											cr = new PdfPCell(Resultado);
											esp = new PdfPCell(Espacio);
											cvr = new PdfPCell(VR);

											ce.setHorizontalAlignment(Element.ALIGN_LEFT);
											ce.disableBorderSide(Rectangle.BOX);
											ce.setExtraParagraphSpace(1.5f);

											cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
											cr.disableBorderSide(Rectangle.BOX);
											cr.setExtraParagraphSpace(1.5f);

											esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
											esp.disableBorderSide(Rectangle.BOX);
											esp.setExtraParagraphSpace(1.5f);

											cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
											cvr.disableBorderSide(Rectangle.BOX);
											cvr.setExtraParagraphSpace(1.5f);

											
											

											tableR.addCell(ce);
											tableR.addCell(cr);
											tableR.addCell(esp);
											tableR.addCell(cvr);
											
										}
									}
									
									
								

									
									
								}
							}
							
							
						}
						
						
						
						Paragraph Comentario;
						
						if (contadoraux >= 1) {
							
						

                       Paragraph Validacion = new Paragraph("Estudio(s) validado por:"
                     +x ,fuen_validacion);
						
						
						if( auxComentario2 == null || auxComentario2 =="") {
							Comentario= new Paragraph("");
							}
						else{
							Comentario= new Paragraph("     OBSERVACIONES\n      " + auxComentario2,
									fuen_comentario);
							
							}
						
						
						esp = new PdfPCell(Espacio);
						esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
						esp.disableBorderSide(Rectangle.BOX);
						esp.setExtraParagraphSpace(1.5f);

						//Paragraph Validacion = new Paragraph("   Estudio(s) validado por: Q.F.B: El doctor Chapatn",fuen_validacion);
						PdfPCell cell_validacion = new  PdfPCell(Validacion);
						
						//Paragraph Comentario = new Paragraph("   OBSERVACIONES:\n    " + auxComentario, fuen_comentario);
						PdfPCell cell_comentario = new  PdfPCell(Comentario);
						
						cell_validacion.setBorder(PdfPCell.NO_BORDER);
						cell_validacion.disableBorderSide(Rectangle.BOX);
						cell_validacion.setExtraParagraphSpace(1.5f);
						cell_comentario.setBorder(PdfPCell.NO_BORDER);
						cell_comentario.disableBorderSide(Rectangle.BOX);
						cell_comentario.setExtraParagraphSpace(1.5f);
						
						tableR.addCell(cell_comentario);
						tableR.addCell(esp);
						tableR.addCell(esp);
						tableR.addCell(esp);
						tableR.addCell(cell_validacion);
						tableR.addCell(esp);
						tableR.addCell(esp);
						tableR.addCell(esp);
						
						
						}
					

					} // llave del else de if para verificar que no perfil
					
					// verifica que exista cultivos individuales 
					if (aux[3].toString().equals("null") &&  aux[4].toString().equals("cultivo")) {
						
						List<Object[]> estudio = ResultadosAntiDao.ResultadoCultivo((Long.valueOf(lo[4].toString())), (Long.valueOf(aux[0].toString())));
						for (Object[] e : estudio) {
							String Valor = "";

							Estudio = new Paragraph("    "+e[0].toString(), fuen_1);
							Resultado = new Paragraph(e[1].toString(), fuen_2);
							Espacio = new Paragraph("  ");
							VR = new Paragraph(Valor, fuen_2);
							Paragraph Comentario = null;

						

							ce = new PdfPCell(Estudio);
							cr = new PdfPCell(Resultado);
							esp = new PdfPCell(Espacio);
							cvr = new PdfPCell(VR);

							ce.setHorizontalAlignment(Element.ALIGN_LEFT);
							ce.disableBorderSide(Rectangle.BOX);
							ce.setExtraParagraphSpace(1.5f);

							cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cr.disableBorderSide(Rectangle.BOX);
							cr.setExtraParagraphSpace(1.5f);

							esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
							esp.disableBorderSide(Rectangle.BOX);
							esp.setExtraParagraphSpace(1.5f);

							cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
							cvr.disableBorderSide(Rectangle.BOX);
							cvr.setExtraParagraphSpace(1.5f);

							Paragraph Validacion = new Paragraph("Estudio(s) validado por:"
									
									
							+x,fuen_validacion);

							PdfPCell cell = new PdfPCell(Comentario);

							PdfPCell cell2 = new PdfPCell(Validacion);
							
							tableR.addCell(ce);
							tableR.addCell(cr);
							tableR.addCell(esp);
							tableR.addCell(cvr);
							
							
							
							if (!e[1].toString().equals("Negativo")) {
								String NombreAnti = ResultadosAntiDao.NombreAntibiograma((Long.valueOf(lo[4].toString())), (Long.valueOf(aux[0].toString())));
								List<Object[]> antibiograma = ResultadosAntiDao.ResultadoAntibio((Long.valueOf(lo[4].toString())), (Long.valueOf(aux[0].toString())));
								
								
								Estudio = new Paragraph("       Antibiograma: "+NombreAnti, fuen_1);
								Resultado = new Paragraph("", fuen_2);
								Espacio = new Paragraph("  ");
								VR = new Paragraph(Valor, fuen_2);
								

							

								ce = new PdfPCell(Estudio);
								cr = new PdfPCell(Resultado);
								esp = new PdfPCell(Espacio);
								cvr = new PdfPCell(VR);

								ce.setHorizontalAlignment(Element.ALIGN_LEFT);
								ce.disableBorderSide(Rectangle.BOX);
								ce.setExtraParagraphSpace(1.5f);

								cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
								cr.disableBorderSide(Rectangle.BOX);
								cr.setExtraParagraphSpace(1.5f);

								esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
								esp.disableBorderSide(Rectangle.BOX);
								esp.setExtraParagraphSpace(1.5f);

								cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
								cvr.disableBorderSide(Rectangle.BOX);
								cvr.setExtraParagraphSpace(1.5f);
								
								tableR.addCell(ce);
								tableR.addCell(cr);
								tableR.addCell(esp);
								tableR.addCell(cvr);
								
								
								for (Object[] a : antibiograma) {
						
									Estudio = new Paragraph("         "+a[0].toString(), fuen_2	);
									Resultado = new Paragraph(a[1].toString(), fuen_2);
									Espacio = new Paragraph("  ");
									VR = new Paragraph("", fuen_2);
								

								

									ce = new PdfPCell(Estudio);
									cr = new PdfPCell(Resultado);
									esp = new PdfPCell(Espacio);
									cvr = new PdfPCell(VR);

									ce.setHorizontalAlignment(Element.ALIGN_LEFT);
									ce.disableBorderSide(Rectangle.BOX);
									ce.setExtraParagraphSpace(1.5f);

									cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
									cr.disableBorderSide(Rectangle.BOX);
									cr.setExtraParagraphSpace(1.5f);

									esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
									esp.disableBorderSide(Rectangle.BOX);
									esp.setExtraParagraphSpace(1.5f);

									cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
									cvr.disableBorderSide(Rectangle.BOX);
									cvr.setExtraParagraphSpace(1.5f);

									
									

									tableR.addCell(ce);
									tableR.addCell(cr);
									tableR.addCell(esp);
									tableR.addCell(cvr);
									
								}
							}

							esp = new PdfPCell(Espacio);
							esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
							esp.disableBorderSide(Rectangle.BOX);
							esp.setExtraParagraphSpace(1.5f);

							//Paragraph Validacion = new Paragraph("   Estudio(s) validado por: Q.F.B: El doctor Chapatín",fuen_validacion);
							PdfPCell cell_validacion = new  PdfPCell(Validacion);
							
							//Paragraph Comentario = new Paragraph("   OBSERVACIONES:\n    " + auxComentario, fuen_comentario);
							PdfPCell cell_comentario = new  PdfPCell(Comentario);
							
							cell_validacion.setBorder(PdfPCell.NO_BORDER);
							cell_validacion.disableBorderSide(Rectangle.BOX);
							cell_validacion.setExtraParagraphSpace(1.5f);
							cell_comentario.setBorder(PdfPCell.NO_BORDER);
							cell_comentario.disableBorderSide(Rectangle.BOX);
							cell_comentario.setExtraParagraphSpace(1.5f);
							
							tableR.addCell(cell_comentario);
							tableR.addCell(esp);
							tableR.addCell(esp);
							tableR.addCell(esp);
							tableR.addCell(cell_validacion);
							tableR.addCell(esp);
							tableR.addCell(esp);
							tableR.addCell(esp);
							
						}
						
					}
					

				} // llave de la consulta de pauete

			} // llave de la condición de la paquete			
			// cultivo individual no esta ni en paquete ni perfil 
			if (lo[8].toString().equals("cultivo")) {//jhh
				List<Object[]> estudio = ResultadosAntiDao.ResultadoCultivo((Long.valueOf(lo[4].toString())), (Long.valueOf(lo[7].toString())));
				for (Object[] e : estudio) {
					String Valor = "";

					Estudio = new Paragraph(e[0].toString(), fuen_1);
					Resultado = new Paragraph(e[1].toString(), fuen_2);
					Espacio = new Paragraph("  ");
					VR = new Paragraph(Valor, fuen_2);
					Paragraph Comentario = null;
					ce = new PdfPCell(Estudio);
					cr = new PdfPCell(Resultado);
					esp = new PdfPCell(Espacio);
					cvr = new PdfPCell(VR);
					ce.setHorizontalAlignment(Element.ALIGN_LEFT);
					ce.disableBorderSide(Rectangle.BOX);
					ce.setExtraParagraphSpace(1.5f);

					cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cr.disableBorderSide(Rectangle.BOX);
					cr.setExtraParagraphSpace(1.5f);

					esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
					esp.disableBorderSide(Rectangle.BOX);
					esp.setExtraParagraphSpace(1.5f);

					cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
					cvr.disableBorderSide(Rectangle.BOX);
					cvr.setExtraParagraphSpace(1.5f);
					
					
					
					
					
					Paragraph Validacion = new Paragraph("Estudio(s) validado por:"
							+ResultadosDao.NombreVal(Long.parseLong(e[2].toString()))
							,fuen_validacion);
					
					
					PdfPCell cell = new PdfPCell(Comentario);
					PdfPCell cell2 = new PdfPCell(Validacion);
					tableR.addCell(ce);
					tableR.addCell(cr);
					tableR.addCell(esp);
					tableR.addCell(cvr);
					if (!e[1].toString().equals("Negativo")) {
						
						String NombreAnti = ResultadosAntiDao.NombreAntibiograma ((Long.valueOf(lo[4].toString())), (Long.valueOf(lo[7].toString())) );
						List<Object[]> antibiograma = ResultadosAntiDao.ResultadoAntibio((Long.valueOf(lo[4].toString())), (Long.valueOf(lo[7].toString())));
						
						
						Estudio = new Paragraph("Antibiograma: "+NombreAnti, fuen_1);
						Resultado = new Paragraph("", fuen_2);
						Espacio = new Paragraph("  ");
						VR = new Paragraph(Valor, fuen_2);

						ce = new PdfPCell(Estudio);
						cr = new PdfPCell(Resultado);
						esp = new PdfPCell(Espacio);
						cvr = new PdfPCell(VR);

						ce.setHorizontalAlignment(Element.ALIGN_LEFT);
						ce.disableBorderSide(Rectangle.BOX);
						ce.setExtraParagraphSpace(1.5f);

						cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
						cr.disableBorderSide(Rectangle.BOX);
						cr.setExtraParagraphSpace(1.5f);

						esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
						esp.disableBorderSide(Rectangle.BOX);
						esp.setExtraParagraphSpace(1.5f);

						cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
						cvr.disableBorderSide(Rectangle.BOX);
						cvr.setExtraParagraphSpace(1.5f);
						
						tableR.addCell(ce);
						tableR.addCell(cr);
						tableR.addCell(esp);
						tableR.addCell(cvr);
						
						
						for (Object[] a : antibiograma) {
				
							Estudio = new Paragraph("   "+a[0].toString(), fuen_2	);
							Resultado = new Paragraph(a[1].toString(), fuen_2);
							Espacio = new Paragraph("  ");
							VR = new Paragraph("", fuen_2);
						

						

							ce = new PdfPCell(Estudio);
							cr = new PdfPCell(Resultado);
							esp = new PdfPCell(Espacio);
							cvr = new PdfPCell(VR);

							ce.setHorizontalAlignment(Element.ALIGN_LEFT);
							ce.disableBorderSide(Rectangle.BOX);
							ce.setExtraParagraphSpace(1.5f);

							cr.setHorizontalAlignment(Element.ALIGN_RIGHT);
							cr.disableBorderSide(Rectangle.BOX);
							cr.setExtraParagraphSpace(1.5f);

							esp.setHorizontalAlignment(Element.ALIGN_RIGHT);
							esp.disableBorderSide(Rectangle.BOX);
							esp.setExtraParagraphSpace(1.5f);

							cvr.setHorizontalAlignment(Element.ALIGN_LEFT);
							cvr.disableBorderSide(Rectangle.BOX);
							cvr.setExtraParagraphSpace(1.5f);

							
							

							tableR.addCell(ce);
							tableR.addCell(cr);
							tableR.addCell(esp);
							tableR.addCell(cvr);
							
						}
					}

					
					cell.setBorder(PdfPCell.NO_BORDER);
					cell2.setBorder(PdfPCell.NO_BORDER);
					tableR.addCell(cell);
					tableR.addCell(esp);
					tableR.addCell(esp);
					tableR.addCell(esp);
					tableR.addCell(cell2);
					tableR.addCell(esp);
					tableR.addCell(esp);
					tableR.addCell(esp);
				}
			}//ghj

		}

		documento.add(tableR);

		documento.close();

	}

	private void filedownloadPDF(String fullPath, HttpServletResponse response, Long id) {
		File file = new File(fullPath);
		final int BUFFER_SIZE = 4096;
		if (file.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(file);
				String mimeType = context.getMimeType(fullPath);
				response.setContentType(mimeType);
				response.setHeader("content-disposition", "attachment; filename=" + nombrepdf + ".pdf");
				OutputStream outputStream = response.getOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int bytesRead = -1;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				file.delete();
				inputStream.close();
				outputStream.close();
			} catch (Exception e) {

			}
		}
	}

	@RequestMapping(value = "/estatus_imp", method = RequestMethod.POST)
	public String estatus(@RequestParam("id") Long id, @RequestParam("id_res") Long id_res, @RequestParam("lo") Long lo,
			@RequestParam("aux") boolean aux, Model m, RedirectAttributes redirectAttrs) {
		Resultados res;
		res = ResultadosDao.findOne(id_res);
		if (aux == true) {
			System.out.print("i´m estatus_imp");
			res.setImpresion(false);
			ResultadosDao.save(res);
		} else {
			System.out.print("i´m estatus_imp else");
			res.setImpresion(true);
			ResultadosDao.save(res);
		}
		m.addAttribute("resul", ResultadosDao.findAll(lo));
		m.addAttribute("paciente", ResultadosDao.PacienteOrden(id));
		m.addAttribute("lineas", ResultadosDao.LineasOrden(id));
		m.addAttribute("id", id);
		m.addAttribute("lo", lo);
		return "listar_ordenes";
	}
	@RequestMapping(value = "/select_all/{id}/{i}/{t}/{lo}", method = RequestMethod.GET)
	public String SelectAll(@PathVariable(value = "id") Long id, @PathVariable(value = "i") Long i,
			@PathVariable(value = "t") String tipo, @PathVariable(value = "lo") Long lo, Map<String, Object> model,
			Model m) {
		ResultadosDao.UpdateImp(lo,1);
		m.addAttribute("id", id);
		m.addAttribute("lo", lo);
		m.addAttribute("resul", ResultadosDao.findAll(lo));
		m.addAttribute("paciente", ResultadosDao.PacienteOrden(id));
		m.addAttribute("lineas", ResultadosDao.LineasOrden(id));
		return "listar_ordenes";
	}
	@RequestMapping(value = "/deselect_all/{id}/{i}/{t}/{lo}", method = RequestMethod.GET)
	public String DeselectAll(@PathVariable(value = "id") Long id, @PathVariable(value = "i") Long i,
			@PathVariable(value = "t") String tipo, @PathVariable(value = "lo") Long lo, Map<String, Object> model,
			Model m) {
		ResultadosDao.UpdateImp(lo,0);
		m.addAttribute("id", id);
		m.addAttribute("lo", lo);
		m.addAttribute("resul", ResultadosDao.findAll(lo));
		m.addAttribute("paciente", ResultadosDao.PacienteOrden(id));
		m.addAttribute("lineas", ResultadosDao.LineasOrden(id));
		return "listar_ordenes";
	}

	

}
