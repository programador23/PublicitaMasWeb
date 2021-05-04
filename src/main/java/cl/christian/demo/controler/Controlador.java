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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cl.christian.demo.interfaceService.ICampaniaPublicitariaService;
import cl.christian.demo.modelo.CampaniaPublicitaria;

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
	
	@GetMapping("/new")
	//llama
	public String agregarcampania(Model model) {
		model.addAttribute("campaniapublicitaria", new CampaniaPublicitaria());
		return "FormularioAgregarCampaña";
	}
	
	
	@PostMapping("/savecampania")
	public String savecampania(@Validated CampaniaPublicitaria c, Model model, @RequestParam("file") MultipartFile image, RedirectAttributes attributes) {
		
		
		if(!image.isEmpty()) {
			Path directorioImagenes = Paths.get("src//main//resources/images");
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
	public String editar(@PathVariable int id,Model model) {
		Optional<CampaniaPublicitaria>campaniapublicitaria=service.listarId(id);
		model.addAttribute("campaniapublicitaria",campaniapublicitaria);
		
		return"FormularioAgregarCampaña";
	}
	
	
	
	@GetMapping("detallecampania/{id}")
	public String detallecampania(@PathVariable int id,Long idcampaniapublicitaria, Model model,RedirectAttributes attribute) {
		Optional<CampaniaPublicitaria>campaniapublicitaria=service.listarId(id);
		
	//model.addAttribute("titulo", "Detalle de la Campaña "+ campaniapublicitaria);
		model.addAttribute("campaniapublicitaria",campaniapublicitaria);
		
		//return"DetalleCampania";
		return"DetalleCampania";
	}
	
	
}
