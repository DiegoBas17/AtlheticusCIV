package diego.basili.AtlheticusCIV.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import diego.basili.AtlheticusCIV.entities.Notizia;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.NotiziaDTO;
import diego.basili.AtlheticusCIV.repositories.NotizieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class NotizieService {
    @Autowired
    private NotizieRepository notizieRepository;
    @Autowired
    private Cloudinary cloudinary;

    public Notizia findById(UUID notiziaId) {
        return notizieRepository.findById(notiziaId)
                .orElseThrow(() -> new NotFoundException("Notizia non trovata con id: " + notiziaId));
    }

    public Page<Notizia> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return notizieRepository.findAll(pageable);
    }

    public Notizia saveNotizia(NotiziaDTO body) {
        Notizia notizia = new Notizia(body.titolo(), body.testo(), LocalDateTime.now(), body.autore());
        if (body.immagine() != null && !body.immagine().isEmpty()) {
            try {
                String url = (String) cloudinary.uploader().upload(body.immagine().getBytes(), ObjectUtils.emptyMap()).get("url");
                notizia.setImmagine(url);
            } catch (IOException e) {
                throw new BadRequestException("Errore nel caricamento del file, verifica il formato o le dimensioni!");
            }
        }
        return notizieRepository.save(notizia);
    }

    public Notizia updateNotizia(UUID notiziaId, NotiziaDTO body) {
        Notizia notizia = findById(notiziaId);
        if (body.immagine() != null && !body.immagine().isEmpty()) {
            try {
                String url = (String) cloudinary.uploader().upload(body.immagine().getBytes(), ObjectUtils.emptyMap()).get("url");
                notizia.setImmagine(url);
            } catch (IOException e) {
                throw new BadRequestException("Errore nel caricamento del file, verifica il formato o le dimensioni!");
            }
        }
        notizia.setTitolo(body.titolo());
        notizia.setTesto(body.testo());
        notizia.setAutore(body.autore());
        return notizieRepository.save(notizia);
    }

    public void deleteNotizia(UUID notiziaId) {
        Notizia found = findById(notiziaId);
        notizieRepository.delete(found);
    }
}
