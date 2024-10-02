package diego.basili.AtlheticusCIV.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.entities.Valutazione;
import diego.basili.AtlheticusCIV.enums.Ruolo;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.AtletaDTO;
import diego.basili.AtlheticusCIV.repositories.AtletiRepository;
import diego.basili.AtlheticusCIV.repositories.ValutazioniRepository;
import diego.basili.AtlheticusCIV.tools.MailgunSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Service
public class AtletiService {
    @Autowired
    private AtletiRepository atletiRepository;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private MailgunSender mailgunSender;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ValutazioniRepository valutazioniRepository;

    public Atleta saveAtleta(AtletaDTO body) {
        if (body == null) {
            throw new BadRequestException("Body richiesto");
        } else if (this.atletiRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
        } else {
            Ruolo ruolo = Ruolo.VISITATORE;
            Valutazione valutazione = new Valutazione(0.0, 0.0,0.0,0.0,0.0);
            valutazioniRepository.save(valutazione);
            Atleta atleta = new Atleta(body.nome(), body.cognome(), body.numeroDiCellulare(), body.email(), bcrypt.encode(body.password()), "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome(), ruolo, valutazione);
            atletiRepository.save(atleta);
            mailgunSender.sendRegistrationEmail(atleta);
            return atleta;
        }
    }

    public Atleta findById(UUID atletaId) {
        return this.atletiRepository.findById(atletaId).orElseThrow(() -> new NotFoundException(atletaId));
    }

    public Atleta findByEmail(String email) {
        return atletiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'atleta con l'email " + email + " non è stato trovato!"));
    }

    public Page<Atleta> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.atletiRepository.findAll(pageable);
    }

    public void findByIdAndDelete(UUID atletaId) {
        Atleta found = findById(atletaId);
        this.atletiRepository.delete(found);
    }

    public Atleta findByIdAndUpdate(UUID atletaId, AtletaDTO updateBody) {
        Atleta found = findById(atletaId);
        if (this.atletiRepository.existsByEmail(updateBody.email()) && !found.getEmail().equals(updateBody.email())) {
            throw new BadRequestException("L'email " + updateBody.email() + " è già in uso!");
        } else {
            found.setNome(updateBody.nome());
            found.setCognome(updateBody.cognome());
            found.setEmail(updateBody.email());
            found.setNumeroTelefono(updateBody.numeroDiCellulare());
            found.setPassword(updateBody.password());
            return atletiRepository.save(found);
        }
    }

    public Atleta uploadImage(UUID atletaId, MultipartFile file) throws IOException {
        Atleta atleta = findById(atletaId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        atleta.setAvatar(url);
        return atletiRepository.save(atleta);
    }

    public Atleta addStatistica (Atleta atleta) {
        if (atleta == null) {
            throw new BadRequestException("Atleta richiesto");
        }
        return atletiRepository.save(atleta);
    }
}
