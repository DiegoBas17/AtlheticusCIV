package diego.basili.AtlheticusCIV.tools;


import diego.basili.AtlheticusCIV.entities.Atleta;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailgunSender {
    private String fromEmail;
    private String apiKey;
    private String domainName;

    public MailgunSender(@Value("${mailgun.key}") String apiKey,
                         @Value("${mailgun.domain}") String domainName,
                         @Value("${mailgun.email}") String fromEmail) {
        this.apiKey = apiKey;
        this.domainName = domainName;
        this.fromEmail = fromEmail;
    }

    public void sendRegistrationEmail(Atleta atleta, String subject, String body) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from", fromEmail)
                .queryString("to", fromEmail)
                .queryString("subject", subject)
                .queryString("text", "Ciao " + atleta.getCognome() + body)
                .asJson();
        System.out.println(response.getBody());
    }
}