package diego.basili.AtlheticusCIV.services;

import diego.basili.AtlheticusCIV.entities.Partita;
import diego.basili.AtlheticusCIV.enums.TipoPartita;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.PartitaDTO;
import diego.basili.AtlheticusCIV.repositories.PartiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.UUID;

public class PartiteService {
    @Autowired
    private PartiteRepository partiteRepository;

    public Page<Partita> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.partiteRepository.findAll(pageable);
    }

    public Partita savePartita(PartitaDTO body) {
        TipoPartita tipoPartita;
        if (body == null) {
            throw new BadRequestException("Devi inserire il body del Partita!");
        } else if (partiteRepository.existsByData(body.data())) {
            throw new BadRequestException("Questo Partita è gia presente!");
        } else {
            try {
                tipoPartita = TipoPartita.valueOf(body.tipoPartita().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Ruolo utente non valido: " + body.tipoPartita() + " i ruoli disponibili sono: NORMALE o ORGANIZZATORE!");
            }
        }
        Partita partita = new Partita(body.data(), body.luogo(), tipoPartita);
        return partiteRepository.save(partita);
    }

    public Partita findById(UUID ruoloId) {
        return partiteRepository.findById(ruoloId).orElseThrow(() -> new NotFoundException(ruoloId));
    }

    public void delete(UUID ruoloId) {
        Partita ruolo = findById(ruoloId);
        partiteRepository.delete(ruolo);
    }

    public Partita findByIdAndUpdate(UUID userId, PartitaDTO updateBody) {
        Partita found = findById(userId);
        TipoPartita tipoPartita;
        if (this.partiteRepository.existsByData(updateBody.data()) && !found.getData().equals(updateBody.data())) {
            throw new BadRequestException("L'email " + updateBody.data() + " è già in uso!");
        } else {
            try {
                tipoPartita = TipoPartita.valueOf(updateBody.tipoPartita().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Ruolo utente non valido: " + updateBody.tipoPartita() + " i ruoli disponibili sono: NORMALE o ORGANIZZATORE!");
            }
            found.setData(updateBody.data());
            found.setLuogo(updateBody.luogo());
            found.setTipoPartita(tipoPartita);
            return partiteRepository.save(found);
        }

    }

}
