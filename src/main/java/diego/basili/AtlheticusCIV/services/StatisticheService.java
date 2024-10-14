package diego.basili.AtlheticusCIV.services;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.entities.Partita;
import diego.basili.AtlheticusCIV.entities.PrenotazionePartita;
import diego.basili.AtlheticusCIV.entities.Statistica;
import diego.basili.AtlheticusCIV.enums.ColoreSquadra;
import diego.basili.AtlheticusCIV.enums.TipoPartita;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.StatisticaDTO;
import diego.basili.AtlheticusCIV.repositories.StatisticheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StatisticheService {
    @Autowired
    private StatisticheRepository statisticheRepository;
    @Autowired
    private PartiteService partiteService;
    @Autowired
    private AtletiService atletiService;

    public Page<Statistica> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.statisticheRepository.findAll(pageable);
    }

    public List<Statistica> saveStatistiche(UUID partitaId) {
        Partita partita = partiteService.findById(partitaId);
        List<PrenotazionePartita> prenotazioni = partita.getPrenotazioniPartite();
        if (prenotazioni.isEmpty()) {
            throw new BadRequestException("Nessun atleta trovato per la partita.");
        }
        List<Statistica> statistiche = new ArrayList<>();
        prenotazioni.forEach(prenotazione -> {
            Atleta atleta = prenotazione.getAtleta();
            Statistica statistica = new Statistica(TipoPartita.CALCETTO, ColoreSquadra.ROSSO, 0L, 0L, partita, atleta);
            statisticheRepository.save(statistica);
            statistiche.add(statistica);
            atleta.getStatistiche().add(statistica);
            atletiService.addStatistica(atleta, statistica);
            partita.getStatistiche().add(statistica);
            partiteService.addStatistica(partita);
        });
        return statistiche;
    }

    public Statistica findById(UUID statisticaId) {
        return statisticheRepository.findById(statisticaId).orElseThrow(() -> new NotFoundException(statisticaId));
    }

    public void delete(UUID statistica_Id) {
        Statistica statistica = findById(statistica_Id);
        statisticheRepository.delete(statistica);
    }

    public Statistica findByUpdate(UUID statisticaId, StatisticaDTO body) {
        Statistica statistica = findById(statisticaId);
        ColoreSquadra coloreSquadra;
        try {
            coloreSquadra = ColoreSquadra.valueOf(body.coloreSquadra().toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new BadRequestException("Colore squadra non valido!");
        }
        TipoPartita tipoPartita;
        try {
            tipoPartita = TipoPartita.valueOf(body.tipoPartita().toUpperCase());
        }
        catch (IllegalArgumentException e) {
            throw new BadRequestException("Tipo partita non valido!");
        }
        statistica.setAssist(body.assist());
        statistica.setGol(body.gol());
        statistica.setColoreSquadra(coloreSquadra);
        statistica.setTipoPartita(tipoPartita);
        return statisticheRepository.save(statistica);
    }

    public List<Statistica> findByAtletaId(UUID atletaId) {
        return this.statisticheRepository.findByAtletaId(atletaId);
    }

    public void addTracker (Statistica statistica) {
        statisticheRepository.save(statistica);
    }
}
