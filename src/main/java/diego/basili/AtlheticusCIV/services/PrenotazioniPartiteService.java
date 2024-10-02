package diego.basili.AtlheticusCIV.services;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.entities.Partita;
import diego.basili.AtlheticusCIV.entities.PrenotazionePartita;
import diego.basili.AtlheticusCIV.enums.Ruolo;
import diego.basili.AtlheticusCIV.enums.StatoPrenotazione;
import diego.basili.AtlheticusCIV.enums.TipoPartita;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.PartitaDTO;
import diego.basili.AtlheticusCIV.payloads.PrenotazionePartitaDTO;
import diego.basili.AtlheticusCIV.repositories.PrenotazioniPartiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PrenotazioniPartiteService {
    @Autowired
    private PrenotazioniPartiteRepository prenotazioniPartiteRepository;
    @Autowired
    private PartiteService partiteService;

    public Page<PrenotazionePartita> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.prenotazioniPartiteRepository.findAll(pageable);
    }

    public PrenotazionePartita savePrenotazione(Atleta atleta, UUID partitaId) {
        Partita partita = partiteService.findById(partitaId);
        StatoPrenotazione statoPrenotazione;
        if (atleta.getRuolo() == Ruolo.VISITATORE) {
            statoPrenotazione = StatoPrenotazione.ATTESA;
        } else {
            statoPrenotazione = StatoPrenotazione.APPROVATA;
        }
        PrenotazionePartita prenotazione = new PrenotazionePartita(atleta, partita, statoPrenotazione);
        return prenotazioniPartiteRepository.save(prenotazione);
    }

    public PrenotazionePartita findById(UUID ruoloId) {
        return prenotazioniPartiteRepository.findById(ruoloId).orElseThrow(() -> new NotFoundException(ruoloId));
    }

    public void delete(UUID ruoloId) {
        PrenotazionePartita ruolo = findById(ruoloId);
        prenotazioniPartiteRepository.delete(ruolo);
    }

    public PrenotazionePartita findByIdAndUpdate(UUID prenotazioneId, PrenotazionePartitaDTO updateBody) {
        PrenotazionePartita found = findById(prenotazioneId);
        try {
            StatoPrenotazione statoPrenotazione = StatoPrenotazione.valueOf(updateBody.statoPrenotazione());
            found.setStatoPrenotazione(statoPrenotazione);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Valore di stato Prenotazione non valido: " + updateBody.statoPrenotazione());
        }
            return prenotazioniPartiteRepository.save(found);
    }
}

