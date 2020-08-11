package net.gecc.crudthymeleaf.controller;

import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import net.gecc.crudthymeleaf.entities.cliente;
import net.gecc.crudthymeleaf.repository.ClienteRepo;
import net.gecc.crudthymeleaf.service.PictureService;

@Controller
@RequestMapping("/cliente")
public class clienteControlador {
	
	@Autowired
	private ClienteRepo repo;
	
	@Autowired
	PictureService picService;
	
	//@RequestMapping("")
	//public String index() {
		//return "index";
	//}
	
	@GetMapping("/registry")//
	public String showSignUpForm(cliente cliente) {
		return "registry";
	}
	
	@GetMapping("/listcli")
	public String showGames(Model model) {
		model.addAttribute("client", repo.findAll());
		return "list_cliente";
	}
	
	@RequestMapping("/logincli")
	public String showLogin() {
		return "login_cliente";
	}
	
	//@GetMapping("/sc")
	//public String showGames() {
		//return "soundcloud.html";
	//}
	
	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping("/private")
	public String showPrivate(Model model) {
		model.addAttribute("client", repo.findAll());
		return "list_cliente";
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/addcli")
	public String addClient(cliente cliente, BindingResult result, Model model, @RequestParam("file") MultipartFile file) {
		if (result.hasErrors()) {
			return "registry";
		}
		UUID idPic = UUID.randomUUID();
		picService.uploadPicture(file, idPic);
		cliente.setFoto(idPic);
		repo.save(cliente);
		
		return "redirect:listcli";
		}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		cliente cliente = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
		model.addAttribute("client",cliente);
		return "update_cliente";
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping("/update/{id}")
	public String updateClient(@PathVariable("id") Long id, cliente cliente, BindingResult result, Model model, @RequestParam("file")  MultipartFile file) {
		if (result.hasErrors()) {
			cliente.setId(id);
			return "update_cliente";
		}
		
		if(!file.isEmpty()) {
			picService.deletePicture(cliente.getFoto());
			UUID idPic = UUID.randomUUID();
			picService.uploadPicture(file, idPic);
			cliente.setFoto(idPic);
		}
		repo.save(cliente);
		return "redirect:/juegos/listcli";
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/delete/{id}")
	public String deleteClient(@PathVariable("id") Long id, Model model) {
		cliente cliente = repo.findById(id).orElseThrow(()  -> new IllegalArgumentException("Invalid client Id:" + id));		
		picService.deletePicture(cliente.getFoto());
		repo.delete(cliente);
		model.addAttribute("client", repo.findAll());
		return "list_cliente";
	}
	
	
	
	

}
