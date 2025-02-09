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
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class PartiteService {
    @Autowired
    private PartiteRepository partiteRepository;

    public Page<Partita> findAll(int page, int size, String sortBy) {
        if (page > 100) page = 100;
        Sort sort = sortBy.equals("data")
                ? Sort.by(Sort.Direction.DESC, "data")
                : Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return this.partiteRepository.findAll(pageable);
    }

    public Partita savePartita(PartitaDTO body) {
        if (body == null) {
            throw new BadRequestException("Devi inserire il body del Partita!");
        }
        TipoPartita tipoPartita;
        try {
            tipoPartita = TipoPartita.valueOf(body.tipoPartita().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Tipo di partita non valido: " + body.tipoPartita() + " i tipi disponibili sono: CALCIO, CALCETTO o CALCIOTTO!");
        }
        if (partiteRepository.existsByDataAndTipoPartita(body.data(), tipoPartita)) {
            throw new BadRequestException("Una partita con questa data e tipo esiste già!");
        }
        Partita partita = new Partita(body.data(), body.luogo(), tipoPartita);
        return partiteRepository.save(partita);
    }

    public Partita findById(UUID partitaId) {
        return partiteRepository.findById(partitaId).orElseThrow(() -> new NotFoundException(partitaId));
    }

    public void delete(UUID partitaId) {
        Partita partita = findById(partitaId);
        partiteRepository.delete(partita);
    }

    public Partita findByIdAndUpdate(UUID partitaId, PartitaDTO updateBody) {
        Partita found = findById(partitaId);
        TipoPartita tipoPartita;
            try {
                tipoPartita = TipoPartita.valueOf(updateBody.tipoPartita().toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Tipo partita non valido: " + updateBody.tipoPartita());
            }
            found.setData(updateBody.data());
            found.setLuogo(updateBody.luogo());
            found.setTipoPartita(tipoPartita);
            return partiteRepository.save(found);

    }

    public Partita addStatistica(Partita updateBody) {
        if (updateBody == null) {
            throw new BadRequestException("partita richiesta");
        }
        return partiteRepository.save(updateBody);
    }
}
