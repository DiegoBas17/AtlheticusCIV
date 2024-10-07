package diego.basili.AtlheticusCIV.services;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.entities.Tracker;
import diego.basili.AtlheticusCIV.entities.Valutazione;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.ValutazioneDTO;
import diego.basili.AtlheticusCIV.repositories.ValutazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValutazioniService {
    @Autowired
    private ValutazioniRepository valutazioniRepository;
    @Autowired
    private AtletiService atletiService;

    public Valutazione findById(UUID trackId) {
        return this.valutazioniRepository.findById(trackId).orElseThrow(() -> new NotFoundException(trackId));
    }

    public Page<Valutazione> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.valutazioniRepository.findAll(pageable);
    }

    public void findByIdAndDelete(UUID trackId) {
        Valutazione found = findById(trackId);
        this.valutazioniRepository.delete(found);
    }

    public Valutazione saveValutazione(ValutazioneDTO body, UUID atletaId) {
        Atleta atleta = atletiService.findById(atletaId);
        Double difesa;
        Double velocità;
        Double resistenza;
        Double tiro;
        Double tecnica;
        try {
            difesa = Double.parseDouble(body.difesa());
            velocità = Double.parseDouble(body.velocità());
            resistenza = Double.parseDouble(body.resistenza());
            tiro = Double.parseDouble(body.tiro());
            tecnica = Double.parseDouble(body.tecnica());
        }
        catch (NumberFormatException e) {
            throw new BadRequestException("Ci sono errori nel body!");
        }
        Valutazione valutazione = new Valutazione(difesa, velocità, resistenza, tiro, tecnica, atleta);
        return valutazione;
    }

    public Valutazione findByIdAndUpdate(ValutazioneDTO body, UUID valutazioneId) {
        Valutazione valutazione = findById(valutazioneId);
        Double difesa;
        Double velocità;
        Double resistenza;
        Double tiro;
        Double tecnica;
        try {
            difesa = Double.parseDouble(body.difesa());
            velocità = Double.parseDouble(body.velocità());
            resistenza = Double.parseDouble(body.resistenza());
            tiro = Double.parseDouble(body.tiro());
            tecnica = Double.parseDouble(body.tecnica());
        }
        catch (NumberFormatException e) {
            throw new BadRequestException("Ci sono errori nel body!");
        }
        valutazione.setDifesa(difesa);
        valutazione.setVelocità(velocità);
        valutazione.setResistenza(resistenza);
        valutazione.setTecnica(tecnica);
        valutazione.setTiro(tiro);
        return valutazioniRepository.save(valutazione);
    }
}
