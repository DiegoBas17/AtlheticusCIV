package diego.basili.AtlheticusCIV.services;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.entities.Statistica;
import diego.basili.AtlheticusCIV.entities.Valutazione;
import diego.basili.AtlheticusCIV.entities.Voto;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.VotoDTO;
import diego.basili.AtlheticusCIV.repositories.VotiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class VotiService {
    @Autowired
    private VotiRepository votiRepository;
    @Autowired
    private StatisticheService statisticheService;

    public Voto findById(UUID trackId) {
        return this.votiRepository.findById(trackId).orElseThrow(() -> new NotFoundException(trackId));
    }

    public Page<Voto> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.votiRepository.findAll(pageable);
    }

    public void findByIdAndDelete(UUID trackId) {
        Voto found = findById(trackId);
        this.votiRepository.delete(found);
    }

    public Voto saveVoto(Atleta atleta, VotoDTO body, UUID statisticaId) {
        Statistica statistica = statisticheService.findById(statisticaId);
        Long voto;
        try {
            voto = (long) body.voto();
        }
        catch (NumberFormatException e) {
            throw new BadRequestException("voto non valido!");
        }
        Voto voto1 = new Voto(voto, statistica, atleta);
        return votiRepository.save(voto1);
    }

    public Voto findByIdAndUpdate(UUID votoId, VotoDTO body) {
        Voto voto = findById(votoId);
        Long voto1;
        try {
            voto1 = (long) body.voto();
        }
        catch (NumberFormatException e) {
            throw new BadRequestException("voto non valido!");
        }
        voto.setVoto(voto1);
        return votiRepository.save(voto);
    }
}
