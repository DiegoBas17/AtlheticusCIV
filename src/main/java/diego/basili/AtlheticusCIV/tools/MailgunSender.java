package diego.basili.AtlheticusCIV.tools;


import diego.basili.AtlheticusCIV.entities.Atleta;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {
    @Value("${mailgun.email}")
    private String fromEmail;
    private String apiKey;
    private String domainName;

    public MailgunSender(@Value("${mailgun.key}") String apiKey, @Value("${mailgun.domain}") String domainName) {
        this.apiKey = apiKey;
        this.domainName = domainName;
    }

    public void sendRegistrationEmail(Atleta recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", fromEmail) // TODO: Mettere il mittente in env.properties
                .queryString("to", recipient.getEmail()) // N.B. Ricordarsi di verificare tramite dashboard Mailgun l'indirizzo del ricevente
                .queryString("subject", "Registrazione completata")
                .queryString("text", "Ciao " + recipient.getCognome() + ", grazie per esserti registrato!")
                .asJson();
        System.out.println(response.getBody()); // <- Stampo il messaggio in risposta per rilevare eventuali errori
    }
}