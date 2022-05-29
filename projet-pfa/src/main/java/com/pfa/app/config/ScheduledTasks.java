package com.pfa.app.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import com.pfa.app.models.Demande;
import com.pfa.app.services.DemandeService;



@Component
public class ScheduledTasks {
	@Autowired
	DemandeService demandeService;
	
	//cette methode s'execute automatiquement chaque 2h permet de passer une demande vers l'historique si sa date est passé
	//a fin de nettoyer la BD
	/*@Scheduled(fixedRate = 720000)
	public void changeDemandeStatusToHistory() throws ParseException {
		System.out.println("-----------------------");
		Date dateNow = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String dateNow_2 = simpleDateFormat.format(dateNow);
		Date dateNow_3 = simpleDateFormat.parse(dateNow_2);
		List <Demande> demandes = demandeService.findAll();
		for(Demande demande: demandes)
		{
			if(simpleDateFormat.parse(demande.getDateCreation()).compareTo(dateNow_3) < 0)
			{
				//System.out.println("demande: " + demande.getId() + ", oui");
				demandeService.changeDemandeStatus(demande.getId(), "HISTORY");
			}
		}
		System.out.println("-----------------------");
	}*/
	
}
