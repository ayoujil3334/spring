package com.pfa.app.controllers;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.pfa.app.models.Demande;
import com.pfa.app.models.Document;
import com.pfa.app.models.Dossier;
import com.pfa.app.models.Specialite;
import com.pfa.app.models.TypeDocument;
import com.pfa.app.models.TypeDossier;
import com.pfa.app.models.User;
import com.pfa.app.services.DemandeService;
import com.pfa.app.services.DocumentService;
import com.pfa.app.services.DossierService;
import com.pfa.app.services.SpecialiteService;
import com.pfa.app.services.TypeDocumentService;
import com.pfa.app.services.TypeDossierService;
import com.pfa.app.services.UserService;

@Controller
@RequestMapping("/admin/profile")
public class AdminController {

	private final String _PESA = "PESA";

	private final String _PES = "PES";

	private final String _PH = "PH";

	@Autowired
    public JavaMailSender emailSender;
	
	@Autowired
	DocumentService documentService;

	@Autowired
	UserService userService;

	@Autowired
	SpecialiteService specialiteService;

	@Autowired
	DossierService dossierService;

	@Autowired
	DemandeService demandeService;

	@Autowired
	TypeDocumentService typeDocumentService;

	@Autowired
	TypeDossierService typeDossierService;

	// -----------------gestion des professeurs-------------

	@GetMapping("/professeurs/pesa/page/{pageNumber}")
	public String getOnePageProfesseurPesa(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<User> page = userService.getUserByRoleByType(currentPage, "PROFESSEUR", "PESA");
		int totalPages = page.getTotalPages();

		long totalItems = page.getTotalElements();
		List<User> users = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("listUsers", users);
		model.addAttribute("type", "pesa");

		return "admin_views/list_users";
	}

	@GetMapping("/professeurs/pesa")
	public String getProfesseurs(Model model) {
		return getOnePageProfesseurPesa(model, 1);
	}

	@GetMapping("/professeurs/pes/page/{pageNumber}")
	public String getOnePageProfessersPes(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<User> page = userService.getUserByRoleByType(currentPage, "PROFESSEUR", "PES");
		int totalPages = page.getTotalPages();

		long totalItems = page.getTotalElements();
		List<User> users = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("listUsers", users);
		model.addAttribute("type", "pes");

		return "admin_views/list_users";
	}

	@GetMapping("/professeurs/pes")
	public String getProfesseursPes(Model model) {
		return getOnePageProfessersPes(model, 1);
	}

	@GetMapping("/professeurs/ph/page/{pageNumber}")
	public String getOnePageProfessersPh(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<User> page = userService.getUserByRoleByType(currentPage, "PROFESSEUR", "PH");
		int totalPages = page.getTotalPages();

		long totalItems = page.getTotalElements();
		List<User> users = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("listUsers", users);
		model.addAttribute("type", "ph");

		return "admin_views/list_users";
	}

	@GetMapping("/professeurs/ph")
	public String getProfesseursPh(Model model) {
		return getOnePageProfessersPh(model, 1);
	}

	@GetMapping("/users/show/{id}")
	public String showProfesseur(Model model, @PathVariable("id") Long id) {
		model.addAttribute("user", userService.getUserByRoleByType(id, "PROFESSEUR"));
		model.addAttribute("typesDossiers", typeDossierService.findAll());

		return "admin_views/show_user";
	}

	// -------------statistiques------------

	@GetMapping("/dashboard")
	public String doashboard(Model model) {
		int allDemandes = demandeService.getNumberOfAllDemandes();
		model.addAttribute("pending", (demandeService.getNumberOfAllDemandesByStatus("PENDING") * 100) / allDemandes);
		model.addAttribute("accepted", (demandeService.getNumberOfAllDemandesByStatus("ACCEPTED") * 100) / allDemandes);
		model.addAttribute("refused", (demandeService.getNumberOfAllDemandesByStatus("REFUSED") * 100) / allDemandes);
		model.addAttribute("history", (demandeService.getNumberOfAllDemandesByStatus("HISTORY") * 100) / allDemandes);
		return "admin_views/doashboard";
	}

	// -------------gestion des specialites------------

	@GetMapping("/specialites/pesa/page/{pageNumber}")
	public String getOnePageSpecialite(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<Specialite> page = specialiteService.findAll(currentPage);
		int totalPages = page.getTotalPages();

		long totalItems = page.getTotalElements();
		List<Specialite> specialites = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("specialites", specialites);

		return "admin_views/list_specialites";
	}

	@GetMapping("/specialites")
	public String getSpecialites(Model model) {
		return getOnePageSpecialite(model, 1);
	}

	@GetMapping("/specialites/delete/{id}")
	public String deleteSpecialite(Model model, @PathVariable("id") Long id) {
		specialiteService.deleteById(id);
		return "redirect:/admin/profile/specialites";
	}

	@GetMapping("/specialites/edit/{id}")
	public String editSpecialiteForm(Model model, @PathVariable("id") Long id) {
		Specialite specialite = specialiteService.findById(id);
		model.addAttribute("specialite", specialite);
		return "admin_views/edit_specialite";
	}

	@GetMapping("/specialites/add")
	public String addSpecialiteForm(Model model) {
		Specialite specialite = new Specialite();
		model.addAttribute("specialite", specialite);
		return "admin_views/add_specialite";
	}

	@PostMapping("/specialites/create")
	public String createSpecialite(Specialite specialite) {
		specialiteService.create(specialite);
		return "redirect:/admin/profile/specialites/add";
	}

	@PostMapping("/specialites/update")
	public String updateSpecialite(Specialite specialite) {
		specialiteService.update(specialite);
		return "redirect:/admin/profile/specialites/edit/" + specialite.getId();
	}

	// -------------gestion des types dossiers---------------

	@GetMapping("/types-dossiers/page/{pageNumber}")
	public String getOnePageTypeDossier(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<TypeDossier> page = typeDossierService.findAll(currentPage);
		int totalPages = page.getTotalPages();

		long totalItems = page.getTotalElements();
		List<TypeDossier> typesDossiers = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("typesDossiers", typesDossiers);

		return "admin_views/list_types_dossiers";
	}

	@GetMapping("/types-dossiers")
	public String getTypesDossiers(Model model) {
		return getOnePageTypeDossier(model, 1);
	}

	@GetMapping("/types-dossiers/delete/{id}")
	public String deleteTypesDossiers(Model model, @PathVariable("id") Long id) {
		typeDossierService.deleteById(id);
		return "redirect:/admin/profile/types-dossiers";
	}

	@GetMapping("/types-dossiers/edit/{id}")
	public String editTypeDossierForm(Model model, @PathVariable("id") Long id) {
		TypeDossier typeDossier = typeDossierService.findById(id);
		model.addAttribute("typeDossier", typeDossier);
		return "admin_views/edit_type_dossier";
	}

	@GetMapping("/types-dossiers/add")
	public String addTypeDossierForm(Model model) {
		TypeDossier typeDossier = new TypeDossier();
		model.addAttribute("typeDossier", typeDossier);
		return "admin_views/add_type_dossier";
	}

	@PostMapping("/types-dossiers/create")
	public String createTypeDossier(TypeDossier typeDossier) {
		typeDossierService.create(typeDossier);
		return "redirect:/admin/profile/types-dossiers/add";
	}

	@PostMapping("/types-dossiers/update")
	public String updateTypeDossier(TypeDossier typeDossier) {
		typeDossierService.update(typeDossier);
		return "redirect:/admin/profile/types-dossiers/edit/" + typeDossier.getId();
	}

	// -------------manipulation des types document---------------

	@GetMapping("/types-documents/page/{pageNumber}")
	public String getOnePageTypeDocument(Model model, @PathVariable("pageNumber") int currentPage) {
		Page<TypeDocument> page = typeDocumentService.findAllTypesDocuments(currentPage);
		int totalPages = page.getTotalPages();

		long totalItems = page.getTotalElements();
		List<TypeDocument> typesDocuments = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("typesDocuments", typesDocuments);

		return "admin_views/list_types_documents";
	}
	
	@GetMapping("/users/{idUser}/dossiers/{typeDossier}/archive")
	public void downloadAllInZip(HttpServletResponse response, @PathVariable("idUser") Long idUser, @PathVariable("typeDossier") String typeDossier)
	{
		User user = userService.findById(idUser);
		Dossier dossier = dossierService.findDossierByType(typeDossier, idUser);
		List <Document> documents = documentService.findAllDocumentByDossierId(dossier.getId());
		String userFolder = user.getNom() + "_" + user.getPrenom() + "_" + user.getCin();
		response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=download.zip");
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
            for(Document document : documents) {
                FileSystemResource fileSystemResource = new FileSystemResource("C:\\Users\\pc\\eclipse-workspace\\projet-pfa\\global_folder\\" + userFolder + "\\" + document.getNom() + "." + document.getExtension());
                System.out.println("chemin: " + "/global_folder/" + userFolder + "/" + document.getNom() + "." + document.getExtension());
                ZipEntry zipEntry = new ZipEntry(fileSystemResource.getFilename());
                zipEntry.setSize(fileSystemResource.contentLength());
                zipEntry.setTime(System.currentTimeMillis());

                zipOutputStream.putNextEntry(zipEntry);

                StreamUtils.copy(fileSystemResource.getInputStream(), zipOutputStream);
                zipOutputStream.closeEntry();
            }
            zipOutputStream.finish();
        } catch (IOException e) {
            //logger.error(e.getMessage(), e);
        }
	}

	@GetMapping("/types-documents")
	public String getTypesDocuments(Model model) {
		return getOnePageTypeDocument(model, 1);
	}

	@GetMapping("/types-documents/delete/{id}")
	public String deleteTypesDocuments(Model model, @PathVariable("id") Long id) {
		typeDossierService.deleteById(id);
		return "redirect:/admin/profile/types-documents";
	}

	@GetMapping("/types-documents/edit/{id}")
	public String editTypeDocumentrForm(Model model, @PathVariable("id") Long id) {
		TypeDocument typeDocument = typeDocumentService.findById(id);
		model.addAttribute("typeDocument", typeDocument);
		return "admin_views/edit_type_document";
	}

	@GetMapping("/types-documents/add")
	public String addTypeDocumentForm(Model model) {
		TypeDocument typeDocument = new TypeDocument();
		model.addAttribute("typeDocument", typeDocument);
		model.addAttribute("typesDossiers", typeDossierService.findAll());
		return "admin_views/add_type_document";
	}

	@PostMapping("/types-documents/create")
	public String createTypeDocument(RedirectAttributes redirAttrs, TypeDocument typeDocument) {
		if(typeDocument.getType().equals("") || typeDocument.getTypeDossier() == null)
		{
			redirAttrs.addFlashAttribute("warningMessage", "svp, tous les champs sont obligatoires !");
			return "redirect:/admin/profile/types-documents/add";
		}
		
		typeDocumentService.create(typeDocument);
		return "redirect:/admin/profile/types-documents/add";
	}

	@PostMapping("/types-documents/update")
	public String updateTypeDocument(TypeDocument typeDocument) {
		typeDocumentService.update(typeDocument);
		return "redirect:/admin/profile/types-documents/edit/" + typeDocument.getId();
	}

	// -------------gestion des demandes-------------------------

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/demandes/{status}/page/{pageNumber}")
	public String findOneDemandePage(Model model, @PathVariable("pageNumber") int currentPage,
			@PathVariable("status") String status) {
		Page<Demande> page = demandeService.getAllDemandesByStatus(status.toUpperCase(), currentPage);
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<Demande> demandes = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("demandes", demandes);
		model.addAttribute("status", status.toUpperCase());
		model.addAttribute("typesDossiers", typeDossierService.findAll());

		return "admin_views/list_demandes";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/demandes/{status}")
	public String getDemandes(Model model, @PathVariable("status") String status) {
		return findOneDemandePage(model, 1, status);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/demandes/{idDemande}/pass/to/{status}")
	public String getDemandes(RedirectAttributes redirAttrs, Model model, @PathVariable("idDemande") Long idDemande,
			@PathVariable("status") String status) {
		demandeService.changeDemandeStatus(idDemande, status.toUpperCase());

		if (status.toUpperCase().equals("ACCEPTED")) {
			switch (demandeService.findById(idDemande).getProfesseur().getType()) {
			case _PESA:
				userService.changeTypeUser(demandeService.findById(idDemande).getProfesseur().getId(), "PROFESSEUR",
						_PH);
				redirAttrs.addFlashAttribute("successMessage", "La demande a été accepté !");
				sendSimpleEmail(demandeService.findById(idDemande).getProfesseur().getEmail(), "Demande de gradation", "salut " + demandeService.findById(idDemande).getProfesseur().getNom() + " " + demandeService.findById(idDemande).getProfesseur().getPrenom() + ", votre de mande de gradation " + _PESA + " => " + _PH + " a été confirmé par l\'dministration");
				break;
			case _PH:
				userService.changeTypeUser(demandeService.findById(idDemande).getProfesseur().getId(), "PROFESSEUR",
						_PES);
				redirAttrs.addFlashAttribute("successMessage", "La demande a été accepté !");
				sendSimpleEmail(demandeService.findById(idDemande).getProfesseur().getEmail(), "Demande de gradation", "salut " + demandeService.findById(idDemande).getProfesseur().getNom() + " " + demandeService.findById(idDemande).getProfesseur().getPrenom() + ", votre de mande de gradation " + _PH + " => " + _PES + " a été confirmé par l\'dministration");
				break;
			case _PES:

				break;
			default:

			}
		}
		
		if (status.toUpperCase().equals("REFUSED")) {
			redirAttrs.addFlashAttribute("successMessage", "La demande a été refusé !");
			sendSimpleEmail(demandeService.findById(idDemande).getProfesseur().getEmail(), "Demande de gradation", "salut " + demandeService.findById(idDemande).getProfesseur().getNom() + " " + demandeService.findById(idDemande).getProfesseur().getPrenom() + ", votre de mande de gradation a été refusé par l\'dministration");
		}

		return "redirect:/admin/profile/demandes/" + status;
	}

	// ----------------------gestion des dossiers-----------

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/users/{id}/dossiers/{typeDossier}/page/{pageNumber}")
	public String findOneDocumentsPage(Model model, @PathVariable("pageNumber") int currentPage,
			@PathVariable("id") Long id, @PathVariable("typeDossier") String typeDossier) {
		Dossier dossier = dossierService.findDossierByType(typeDossier, id);

		Page<Document> page = documentService.findAllDocumentByDossierId(currentPage, dossier.getId());
		int totalPages = page.getTotalPages();
		long totalItems = page.getTotalElements();
		List<Document> documents = page.getContent();

		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("totalItems", totalItems);
		model.addAttribute("documents", documents);
		model.addAttribute("a", dossier.getUser().getId());
		model.addAttribute("b", typeDossier);

		model.addAttribute("documents", documents);
		model.addAttribute("typesDossiers", typeDossierService.findAll());
		model.addAttribute("dossier", typeDossier);

		return "admin_views/folders";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/users/{id}/dossiers/{typeDossier}")
	public String getDocuments(Model model, @PathVariable("id") Long id,
			@PathVariable("typeDossier") String typeDossier) {
		return findOneDocumentsPage(model, 1, id, typeDossier);
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping(value = "/users/{idUser}/documents/download/{idDocument}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> downloadFile(@PathVariable("idUser") Long idUser,
			@PathVariable("idDocument") Long idDocument, HttpServletRequest request) throws IOException {
		byte[] pdf = null;
		HttpHeaders headers = new HttpHeaders();
		User user = userService.findById(idUser);
		Document document = documentService.findById(idDocument);
		Dossier dossier = dossierService.findById(document.getDossier().getId());
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

	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/users/{idUser}/documents/delete/{idDocument}")
	public String deleteDocument(RedirectAttributes redirAttrs, Model model, @PathVariable("idUser") Long idUser,
			@PathVariable("idDocument") Long idDocument) {
		User user = userService.findById(idUser);
		File fileToDelete = FileUtils.getFile("global_folder/" + user.getNom() + "_" + user.getPrenom() + "_"
				+ user.getCin() + "/" + documentService.findById(idDocument).getNom() + "."
				+ documentService.findById(idDocument).getExtension());

		Dossier dossier = dossierService.findById(documentService.findById(idDocument).getDossier().getId());

		if (user.getId() != dossier.getUser().getId()) {
			redirAttrs.addFlashAttribute("dangerMessage", "action non authorisé!");
			return "redirect:/logout";
		}

		// suppression physique
		boolean success = FileUtils.deleteQuietly(fileToDelete);

		// suppression logique
		documentService.deleteById(idDocument);

		return "redirect:/admin/profile/users/" + user.getId() + "/dossiers/" + dossier.getTypeDossier().getType();
	}
	
    public String sendSimpleEmail(String email, String subject, String msg) {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(email);
        message.setSubject(subject);
        message.setText(msg);

        // Send Message!
        this.emailSender.send(message);

        return "Email Sent!";
    }
}
