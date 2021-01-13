package fr.projet.servicepaiementintegration.service;

import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.projet.servicepaiementintegration.model.Paiement;


@Component
public class RabbitMQConsumer {
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	RestTemplate restTemplate;

	@RabbitListener(queues = "${servicepaiement.rabbitmq.queue}")
	// Consommation du message dans la queue lors d'un paiement pour le valider
	public void validationPaiement(String pay) {
		// Effectuer l'envoi de l'id pour la validation via REST au front à ce niveau
		try {
			Paiement p = mapper.readValue(pay, Paiement.class);
			JSONObject obj = new JSONObject();
			obj.put("id", p.getId());
		    
			HttpHeaders headers = new HttpHeaders(); headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> request = new HttpEntity<>(obj.toString() , headers);
			restTemplate.postForObject("http://localhost:8000/ValidationPayment/", request, String.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		System.out.println("Paiement effectué et validé : " + pay.toString());
	}
}