package diego.basili.AtlheticusCIV.services;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.entities.Statistica;
import diego.basili.AtlheticusCIV.entities.Tracker;
import diego.basili.AtlheticusCIV.enums.TipoPartita;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.TrackerDTO;
import diego.basili.AtlheticusCIV.repositories.TrackersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TrakersService {
    @Autowired
    private TrackersRepository trackersRepository;
    @Autowired
    private StatisticheService statisticheService;

    public Tracker findById(UUID trackId) {
        return this.trackersRepository.findById(trackId).orElseThrow(() -> new NotFoundException(trackId));
    }

    public Page<Tracker> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.trackersRepository.findAll(pageable);
    }

    public void findByIdAndDelete(UUID trackId) {
        Tracker found = findById(trackId);
        this.trackersRepository.delete(found);
    }

    public Tracker saveTracker(TrackerDTO body, UUID statisticaId) {
        Statistica statistica = statisticheService.findById(statisticaId);
        TipoPartita tipoPartita;
        try {
            tipoPartita = TipoPartita.valueOf(body.tipoPartita().toUpperCase());
        }
        catch (IllegalArgumentException e){
            throw new BadRequestException("tipo partita non valido!");
        }
        Double attività;
        Double distanza;
        Double sprintMedio;
        Double sprintMassimo;
        Double tiroMassimo;
        Double tiroMedio;
        Double valutazione5Stelle;
        try {
            attività = Double.parseDouble(body.attività());
            distanza = Double.parseDouble(body.distanza());
            sprintMedio = Double.parseDouble(body.sprintMedio());
            sprintMassimo = Double.parseDouble(body.sprintMassimo());
            tiroMassimo = Double.parseDouble(body.tiroMassimo());
            tiroMedio = Double.parseDouble(body.tiroMedio());
            valutazione5Stelle = Double.parseDouble(body.valutazione5Stelle());
        }
        catch (NumberFormatException e) {
            throw new BadRequestException("Errore nei valori di attività, distanza, sprint medio, sprinta massimo, tiro massimo, tiro medio o la valutazione a 5 stelle, rincontrollare!!");
        }
        Long passaggi;
        Long numeroSprint;
        Long tiri;
        try {
            passaggi = (long) body.passaggi();
            numeroSprint = (long) body.numeroSprint();
            tiri = (long) body.tiri();
        }
        catch (NullPointerException e) {
            throw new BadRequestException("errore in uno dei seguenti dati: passaggi, tiri o numero sprint!!");
        }
        Tracker tracker = new Tracker(tipoPartita, attività, distanza, passaggi, body.corsa(), numeroSprint,
                sprintMedio, sprintMassimo, body.possesso(),tiri, tiroMassimo, tiroMedio, valutazione5Stelle,
                body.analisiAllenatore(), statistica);
        trackersRepository.save(tracker);
        statistica.setTracker(tracker);
        statisticheService.addTracker(statistica);
        return tracker;
    }

    public Tracker findByIdAndUpdate(UUID trackerId, TrackerDTO body) {
        Tracker tracker = findById(trackerId);
        TipoPartita tipoPartita;
        try {
            tipoPartita = TipoPartita.valueOf(body.tipoPartita());
        }
        catch (IllegalArgumentException e){
            throw new BadRequestException("tipo partita non valido!");
        }
        Double attività;
        Double distanza;
        Double sprintMedio;
        Double sprintMassimo;
        Double tiroMassimo;
        Double tiroMedio;
        Double valutazione5Stelle;
        try {
            attività = Double.parseDouble(body.attività());
            distanza = Double.parseDouble(body.distanza());
            sprintMedio = Double.parseDouble(body.sprintMedio());
            sprintMassimo = Double.parseDouble(body.sprintMassimo());
            tiroMassimo = Double.parseDouble(body.tiroMassimo());
            tiroMedio = Double.parseDouble(body.tiroMedio());
            valutazione5Stelle = Double.parseDouble(body.valutazione5Stelle());
        }
        catch (NumberFormatException e) {
            throw new BadRequestException("Errore nei valori di attività, distanza, sprint medio, sprinta massimo, tiro massimo, tiro medio o la valutazione a 5 stelle, rincontrollare!!");
        }
        Long passaggi;
        Long numeroSprint;
        Long tiri;
        try {
            passaggi = (long) body.passaggi();
            numeroSprint = (long) body.numeroSprint();
            tiri = (long) body.tiri();
        }
        catch (NullPointerException e) {
            throw new BadRequestException("errore in uno dei seguenti dati: passaggi, tiri o numero sprint!!");
        }
        tracker.setTipoPartita(tipoPartita);
        tracker.setAttività(attività);
        tracker.setDistanza(distanza);
        tracker.setPassaggi(passaggi);
        tracker.setCorsa(body.corsa());
        tracker.setNumeroSprint(numeroSprint);
        tracker.setSprintMedio(sprintMedio);
        tracker.setSprintMassimo(sprintMassimo);
        tracker.setPossesso(body.possesso());
        tracker.setTiri(tiri);
        tracker.setTiroMassimo(tiroMassimo);
        tracker.setTiroMedio(tiroMedio);
        tracker.setValutazione5Stelle(valutazione5Stelle);
        tracker.setAnalisiAllenatore(body.analisiAllenatore());
        /*forse dovrei aggiungere la possibilita di cambiare la statistica nella modifica del tracker*/
        return trackersRepository.save(tracker);
    }
}
