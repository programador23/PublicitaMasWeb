package cl.christian.demo.controler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.jar.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.christian.demo.interfaceService.ICampaniaPublicitariaService;
import cl.christian.demo.interfaceService.ICartelPublicitarioService;
import cl.christian.demo.modelo.CampaniaPublicitaria;
import cl.christian.demo.modelo.CartelPublicitario;

/**
 * @author Christian
 *
 */
/**
 * @author Christian
 *
 */
/**
 * @author Christian
 *
 */
@Controller
@RequestMapping
public class Controlador {
	
	@Autowired
	private ICampaniaPublicitariaService service;
	
	@GetMapping("/listar")
	public String listarcampania(Model model) {
		List<CampaniaPublicitaria>campaniapublicitaria=service.listarcampania();
		model.addAttribute("campaniapublicitaria", campaniapublicitaria);
		return "index";
	}
	@GetMapping("/listarPublicidad")
	public String listarcampaniaPublidad(Model model) {
		List<CampaniaPublicitaria>campaniapublicitaria=service.listarcampania();
		model.addAttribute("campaniapublicitaria", campaniapublicitaria);
		return "listapublicidadgeneral";
	}

	
	
	
	@GetMapping("/new")
	//llama
	public String agregarcampania(Model model) {
		model.addAttribute("campaniapublicitaria", new CampaniaPublicitaria());
		return "usuario/FormularioAgregarCampaña";
	}
	
	
	@PostMapping("/savecampania")
	public String savecampania(@Validated CampaniaPublicitaria c, Model model, @RequestParam("file") MultipartFile image, RedirectAttributes attributes) {
		
		
		if(!image.isEmpty()) {
			Path directorioImagenes = Paths.get("src//main//resources//static/images");
			String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
			
			try {
				byte[] bytesImg =image.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//"+ image.getOriginalFilename());
				Files.write(rutaCompleta, bytesImg);
				
				c.setImage(image.getOriginalFilename());
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		
	
		service.savecampania(c);
		return "redirect:/listar";	
	}
	
	@GetMapping("modificarcampania/{id}")
	public String editar(@PathVariable("id") int id,Model model) {
		
		CampaniaPublicitaria campaniapublicitaria=service.listarId(id);
		
		model.addAttribute("campaniapublicitaria",campaniapublicitaria);
		
		return"usuario/FormularioAgregarCampaña";
	}
	
	
	/*Detalle de campaña por id*/
	@GetMapping("detalle/{id}")
	public String detalleCampania(@PathVariable("id") int id,Model model,RedirectAttributes attribute) {
		
		 CampaniaPublicitaria campaniapublicitaria= null;
		//CampaniaPublicitaria campaniapublicitaria = service.BuscarPorId(id);
		
		  if(id > 0) {
			  campaniapublicitaria = service.listarId(id);
		  
		 if(campaniapublicitaria==null) {
			 attribute.addFlashAttribute("error","El ID del la Campania no existe");
			 return "DetalleCampania";
		 }
		  }else {
			  attribute.addFlashAttribute("error","error con el id de la campania");
			  return "DetalleCampania";
		  }
		model.addAttribute("titulo","Detalle de  "+ campaniapublicitaria.getNombre());
		  
		model.addAttribute("campaniapublicitaria", campaniapublicitaria);
		return"DetalleCampania";
	}
	
	

	@GetMapping("/listausuario")
	public String listaCampaniasUsuarios(Model model) {

	
		model.addAttribute("campaniapublicitaria", new CampaniaPublicitaria());
		return "ListaDeCampaniaUsuario";
		
	}
	
	/*
	 * Este metodo busca por el id del usuario todas las campañas que tenga
	 * vinculadas o que allá creado
	 */
	@GetMapping("/idusuario/")
	public String BuscarPorIdusuario(@RequestParam String idusuario,Model model,@ModelAttribute("campaniapublicitaria") CampaniaPublicitaria campaniaPublicitaria ) {

		model.addAttribute("PublicidadPorUsuario", service.BuscarPordusuario(idusuario));
		return "usuario/ListaDeCampaniaUsuario";
	}
	
		
	/**
	 * Este metodo elimina una campaña por su id 
	 */
		@GetMapping("/eliminarcampania/{id}")
	public String delete(Model model, @PathVariable int id) {
		service.delete(id);
		return "redirect:/listar";
	}
	
		
	/*carteles publicitarios*/
	@Autowired
	private ICartelPublicitarioService servicecartel;
	
	@GetMapping("/listacarteles")
	public String listarcarteles(Model model) {
		List<CartelPublicitario> cartelpublicitarios = servicecartel.listarcarteles();
		model.addAttribute("cartelpublicitarios", cartelpublicitarios);
		return "carteles/listacarteles";
	} 
	
	@GetMapping("/nuevocartel")
	public String nuevocartel(Model model) {
		model.addAttribute("cartelpublicitario", new CartelPublicitario());
		return "FormularioAgregarCartel";
	}
	
	@PostMapping("/savecartel")
	public String savecartel(@Validated CartelPublicitario c, Model model, @RequestParam("file") MultipartFile image, @RequestParam("document") MultipartFile documentos,RedirectAttributes atrAttributes) {
		
		if(!image.isEmpty() && ! documentos.isEmpty()) {
			Path directorioImagenes = Paths.get("src//main//resources/static/images");
			String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
			Path directorioDocumentos =Paths.get("src//main//resources/static/documentos");
			String rutaAbsolutaDoc = directorioDocumentos.toFile().getAbsolutePath();
			
			try {
				byte[] bytesImg = image.getBytes();
				Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + image.getOriginalFilename());
				byte[] bytesDoc = documentos.getBytes();
				Path rutaCompletaDoc = Paths.get(rutaAbsolutaDoc + "//" + documentos.getOriginalFilename());
			
				Files.write(rutaCompleta, bytesImg);
				Files.write(rutaCompletaDoc, bytesDoc);
				
				c.setImage(image.getOriginalFilename());
				c.setDocumentacion(documentos.getOriginalFilename());
				
					
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
		servicecartel.savecartel(c);
		return "redirect:/listarcarteles";
	}
	
	@GetMapping("modificarcartel/{id}")
	public String editarcartel(@PathVariable int id,Model model) {
		CartelPublicitario cartelpublicitario=servicecartel.listarIdcartel(id);
		model.addAttribute("cartelpublicitario",cartelpublicitario);
		
		return"FormularioAgregarCartel";
	}
	/*lista por idusuario de carteles ingresados*/
	@PostMapping("/listacarteles_usuario/{idusuario}")
	public String listarcarteles_usuarios(@PathVariable int idusuario,Model model) {
		
		 
		
		model.addAttribute("cartelpublicitarios", servicecartel.listarIdcartelUsuario(idusuario));
		return "listacarteles_usuario";
	}
	
	
	@GetMapping("/eliminarcartel/{id}")
	public String deletecartel(Model del,@PathVariable int id) {
		servicecartel.deletecartel(id);
		return"redirect:/listarcartel";
	}
	@GetMapping("/agregarCartel")
	//llama
	public String agregarcartel(Model model) {
		model.addAttribute("cartelpublicitario", new CartelPublicitario());
		return "carteles/FormularioAgregarCartel";
	}
	
	@GetMapping("/index")
	//llama de inicio 
	public String incio(Model model) {
		//model.addAttribute("cartelpublicitario", new CartelPublicitario());
		return "inicio";
	}
}
