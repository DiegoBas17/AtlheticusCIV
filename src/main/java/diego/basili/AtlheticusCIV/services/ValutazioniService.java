package diego.basili.AtlheticusCIV.services;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.entities.Tracker;
import diego.basili.AtlheticusCIV.entities.Valutazione;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.ValutazioneDTO;
import diego.basili.AtlheticusCIV.repositories.ValutazioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    private AtletiService atletiService;

    public Valutazione findById(UUID valutazioneId) {
        return this.valutazioniRepository.findById(valutazioneId).orElseThrow(() -> new NotFoundException(valutazioneId));
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
        return new Valutazione(body.difesa(), body.velocita(), body.resistenza(), body.tiro(), body.tecnica(), atleta);
    }

    public Valutazione findByIdAndUpdate(ValutazioneDTO body, UUID valutazioneId) {
        Valutazione valutazione = findById(valutazioneId);
        valutazione.setDifesa(body.difesa());
        valutazione.setVelocità(body.velocita());
        valutazione.setResistenza(body.resistenza());
        valutazione.setTecnica(body.tecnica());
        valutazione.setTiro(body.tiro());
        return valutazioniRepository.save(valutazione);
    }
}
