package diego.basili.AtlheticusCIV.runners;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.payloads.AtletaDTO;
import diego.basili.AtlheticusCIV.services.AtletiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AtletiRunner implements CommandLineRunner {
    @Autowired
    private AtletiService atletiService;

    @Override
    public void run(String... args) throws Exception {
        /*AtletaDTO diego = new AtletaDTO("Diego", "Basili", "d.basili17@gmail.com", "32912912006", "1234");
        AtletaDTO emanuele = new AtletaDTO("Emanuele", "Ietto", "emanuele.ietto@gmail.com", "32912912006", "1234");
        AtletaDTO patrick = new AtletaDTO("Patrick", "Rossi", "patrick.rossi@gmail.com", "32912912006", "1234");
        atletiService.saveAtleta(diego);
        atletiService.saveAtleta(emanuele);
        atletiService.saveAtleta(patrick);*/
    }
}
