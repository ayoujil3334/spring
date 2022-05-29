package com.pfa.app.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pfa.app.models.Demande;
import com.pfa.app.models.Document;
import com.pfa.app.models.Dossier;
import com.pfa.app.models.TypeDossier;
import com.pfa.app.models.User;
import com.pfa.app.services.DemandeService;
import com.pfa.app.services.DocumentService;
import com.pfa.app.services.DossierService;
import com.pfa.app.services.SpecialiteService;
import com.pfa.app.services.TypeDocumentService;
import com.pfa.app.services.TypeDossierService;
import com.pfa.app.services.UserService;

import ch.qos.logback.core.util.FileUtil;

@Controller
@RequestMapping("/professeur/profile")
public class ProfesseurController {
	@Autowired
	DocumentService documentService;

	@Autowired
	TypeDocumentService typeDocumentService;

	@Autowired
	DemandeService demandeService;

	@Autowired
	DossierService dossierService;

	@Autowired
	UserService userService;

	@Autowired
	TypeDossierService typeDossierService;
	
	@Autowired
	SpecialiteService specialiteService;

	Authentication auth;

	private static final DecimalFormat df = new DecimalFormat("0.00");

	private Path foundFile;
	
	//-----------------debut modifier profile------------------------------
	
	@GetMapping("/edit")
	public String modifierProfile(Model model)
	{
		auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUserName(auth.getName());
		
		model.addAttribute("user", user);
		model.addAttribute("userImage", user.getImage());
		model.addAttribute("typesDossiers", typeDossierService.findAll());
		model.addAttribute("specialites", specialiteService.findAll());
		return "professeur_views/edit_profile";
	}
	
	@PostMapping("/update")
	public String updateProfile(RedirectAttributes redirAttrs, User user, @RequestParam("img") MultipartFile img) throws IOException
	{
		if(img.isEmpty() == false)
		{
			if(uploadImage(img) == false)
			{
				redirAttrs.addFlashAttribute("dangerMessage", "erreur !!");
				return "redirect:/professeur/profile/edit";
			}
			deleteImage(userService.findById(user.getId()).getImage());
			//System.out.println("image: " + userService.findById(user.getId()).getImage());
		}
		
		user.setImage(img.getOriginalFilename());
		userService.update(user);
		
		redirAttrs.addFlashAttribute("successMessage", "votre profile a été bien modifié :)");
		return "redirect:/professeur/profile/edit";
	}
	
	public boolean uploadImage(MultipartFile image) {
		return userService.upload(image);
	}
	
	public boolean deleteImage(String imageName) throws IOException {
		return userService.deleteIfExists(imageName);
	}
	
	
	//-----------------fin modifier profile--------------------------------
	
	//-----------------debut gestion des demandes--------------------------
	
	@PreAuthorize("hasAuthority('PROFESSEUR')")
	@GetMapping("/demandes/{status}/page/{pageNumber}")
	public String findOneDemandePage(Model model, @PathVariable("pageNumber") int currentPage, @PathVariable("status") String status) {
		auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUserName(auth.getName());
		
		Page<Demande> page = demandeService.getAllDemandesByProfesseurAndStatus(user.getId(), status.toUpperCase(), currentPage);
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<Demande> demandes = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("demandes", demandes);
		model.addAttribute("status", status.toUpperCase());
		model.addAttribute("typesDossiers", typeDossierService.findAll());
		model.addAttribute("userImage", user.getImage());
		
		return "professeur_views/list_demandes";
	}
	
	@PreAuthorize("hasAuthority('PROFESSEUR')")
	@GetMapping("/demandes/{status}")
	public String getDemandes(Model model, @PathVariable("status") String status) {
		return findOneDemandePage(model, 1, status);
	}
	
	@PreAuthorize("hasAuthority('PROFESSEUR')")
	@GetMapping("/demandes/add")
	public String add(Model model)
	{
		auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUserName(auth.getName());
		model.addAttribute("demande", new Demande());
		model.addAttribute("typesDossiers", typeDossierService.findAll());
		model.addAttribute("userImage", user.getImage());
		return "professeur_views/add_demande";
	}
	
	@PreAuthorize("hasAuthority('PROFESSEUR')")
	@PostMapping("/demandes/create")
	public String add(RedirectAttributes redirAttrs, Model model, Demande demande)
	{
		auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUserName(auth.getName());
		
		//verifier si les champs sont remplis
		if(demande.getMotivation().equals(""))
		{
			redirAttrs.addFlashAttribute("warningMessage", "tous les champs sont obligatoires!");
			return "redirect:/professeur/profile/demandes/add";
		}
		
		//tester si le professeur deja il a une demande en cours de traitement par l'administration
		if(demandeService.getDemandeByProfesseur(user.getId(), "PENDING").size() > 0)
		{
			redirAttrs.addFlashAttribute("warningMessage", "pardon, vous avez déjà une demande en cours de traitement par l'administration!");
			return "redirect:/professeur/profile/demandes/add";
		}
		
		demande.setProfesseur(user); //professeur connecté maintenant
		demande.setStatus("PENDING"); //status par defaut
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		demande.setDateCreation(simpleDateFormat.format(new Date()));
		
		demandeService.create(demande);
		
		redirAttrs.addFlashAttribute("successMessage", "merci, votre demande a été bien envoyé!");
		
		return "redirect:/professeur/profile/demandes/add";
	}
	
	//-----------------fin gestion des demandes--------------------------

	//-----------------debut gestion des fichiers--------------------------
	
	@PreAuthorize("hasAuthority('PROFESSEUR')")
	@GetMapping("/dossiers/{typeDossier}/page/{pageNumber}")
	public String findOneDocumentsPage(Model model, @PathVariable("pageNumber") int currentPage, @PathVariable("typeDossier") String typeDossier) {
		auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUserName(auth.getName());
		Dossier dossier = dossierService.findDossierByType(typeDossier,
				userService.getUserByUserName(auth.getName()).getId());
		
		Page<Document> page = documentService.findAllDocumentByDossierId(currentPage, dossier.getId());
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<Document> documents = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("documents", documents);

		//System.out.println("type de ssier id: " + dossier.getTypeDossier().getId());
		model.addAttribute("documents", documents);
		model.addAttribute("userImage", user.getImage());
		model.addAttribute("typesDossiers", typeDossierService.findAll());
		model.addAttribute("dossier", typeDossier);
		model.addAttribute("idDossier", dossier.getId());
		model.addAttribute("document", new Document());
		model.addAttribute("typesDocuments", typeDocumentService.findByTypeDossier(dossier.getTypeDossier().getId()));
		model.addAttribute("typesDossiers", typeDossierService.findAll());
		
		return "professeur_views/folders";
	}
	
	@PreAuthorize("hasAuthority('PROFESSEUR')")
	@GetMapping("/dossiers/{typeDossier}")
	public String getDocuments(Model model, @PathVariable("typeDossier") String typeDossier) {
		return findOneDocumentsPage(model, 1, typeDossier);
	}

	@PreAuthorize("hasAuthority('PROFESSEUR')")
	@PostMapping("/documents/create")
	public String saveDocument(RedirectAttributes redirAttrs, Model model, Document document,
			@RequestParam("fichier") MultipartFile fichier) {
		if(document.getTypeDocument() == null || fichier.isEmpty() == true)
		{
			redirAttrs.addFlashAttribute("warningMessage", "svp, tous les champs sont obligatoires !!");
			return "redirect:/professeur/profile/dossiers/" + document.getDossier().getTypeDossier().getType();
		}
		
		String size = null;
		auth = SecurityContextHolder.getContext().getAuthentication();
		Dossier dossier = dossierService.findById(document.getDossier().getId());
		User user = userService.getUserByUserName(auth.getName());
		String userFolder = user.getNom() + "_" + user.getPrenom() + "_" + user.getCin();

		String extension = FilenameUtils.getExtension(fichier.getOriginalFilename());
		String name = FilenameUtils.removeExtension(fichier.getOriginalFilename());

		document.setExtension(FilenameUtils.getExtension(fichier.getOriginalFilename()));

		// pour enregistrer la colonne taille selon la taille du fichier (KB, MB, GB)
		/*
		 * if((fichier.getSize() / 1024 / 1024) < 0.00) { size =
		 * df.format(fichier.getSize() / 1024) + " KB"; } else if((fichier.getSize() /
		 * 1024 / 1024) > 0.00) { size = df.format(fichier.getSize() / 1024 / 1024) +
		 * " MB"; } else if((fichier.getSize() / 1024 / 1024) > 1000) { size =
		 * df.format(fichier.getSize() / 1024 / 1024 / 1024) + " MB"; }
		 */
		document.setSize(df.format(fichier.getSize() / 1024 / 1024) + " MB"); // convert from bytes to megabytes
		document.setNom(name);

		documentService.create(document);

		if (uploadFile(fichier, userFolder, name, extension)) {
			return "redirect:/professeur/profile/dossiers/" + dossier.getTypeDossier().getType();
		}

		return "redirect:/professeur/profile/dossiers/" + dossier.getTypeDossier().getType();
	}

	public boolean uploadFile(MultipartFile fichier, String userFolder, String name, String extension) {
		documentService.upload(fichier, userFolder, name, extension);
		return false;
	}

	@PreAuthorize("hasAuthority('PROFESSEUR')")
	@GetMapping("/documents/delete/{id}")
	public String deleteDocument(RedirectAttributes redirAttrs, Model model, @PathVariable("id") Long id) {
		auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUserName(auth.getName());
		File fileToDelete = FileUtils
				.getFile("global_folder/" + user.getNom() + "_" + user.getPrenom() + "_" + user.getCin() + "/"
						+ documentService.findById(id).getNom() + "." + documentService.findById(id).getExtension());

		Dossier dossier = dossierService.findById(documentService.findById(id).getDossier().getId());

		if(user.getId() != dossier.getUser().getId())
		{
			redirAttrs.addFlashAttribute("dangerMessage", "action non authorisé!");
			return "redirect:/logout";
		}
			
		// suppression physique
		boolean success = FileUtils.deleteQuietly(fileToDelete);

		// suppression logique
		documentService.deleteById(id);
		
		redirAttrs.addFlashAttribute("successMessage", "le document a été bien supprimé :)");

		return "redirect:/professeur/profile/dossiers/" + dossier.getTypeDossier().getType();
	}

	@PreAuthorize("hasAuthority('PROFESSEUR')")
	@GetMapping(value = "/documents/show/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id, HttpServletRequest request)
			throws IOException {
		auth = SecurityContextHolder.getContext().getAuthentication();
		byte[] pdf = null;
		HttpHeaders headers = new HttpHeaders();
		Document document = documentService.findById(id);
		Dossier dossier = dossierService.findById(document.getDossier().getId());
		User user = userService.getUserByUserName(auth.getName());
		String fileName = document.getNom() + "." + document.getExtension();
		String userFolder = user.getNom() + "_" + user.getPrenom() + "_" + user.getCin();
		System.out.println("global_folder/" + userFolder + "/" + fileName);

		// Load file as Resource
		Resource resource = documentService.loadFileAsResource(userFolder, fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			// logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
	//-----------------fin gestion des fichiers--------------------------
}
