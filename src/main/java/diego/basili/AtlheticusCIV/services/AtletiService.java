package diego.basili.AtlheticusCIV.services;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.enums.Ruolo;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.AtletaDTO;
import diego.basili.AtlheticusCIV.repositories.AtletiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class AtletiService {
    @Autowired
    private AtletiRepository atletiRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public Atleta saveUser(AtletaDTO body) {
        if (body == null) {
            throw new BadRequestException("Body richiesto");
        } else if (this.atletiRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
        } else {
            Ruolo ruolo = Ruolo.ATLETA;
            Atleta user = new Atleta(body.nome(), body.cognome(), body.numeroDiCellulare(), body.email(), bcrypt.encode(body.password()), "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome(), ruolo);
            return this.atletiRepository.save(user);
        }
    }

    public Atleta findById(UUID userId) {
        return this.atletiRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public Atleta findByEmail(String email) {
        return atletiRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con l'email " + email + " non è stato trovato!"));
    }

    public Page<Atleta> findAll(int page, int size, String sortBy) {
        if (page > 20) page = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.atletiRepository.findAll(pageable);
    }

    public void findByIdAndDelete(UUID userId) {
        Atleta found = findById(userId);
        this.atletiRepository.delete(found);
    }

    public Atleta findByIdAndUpdate(UUID userId, AtletaDTO updateBody) {
        Atleta found = findById(userId);
        if (this.atletiRepository.existsByEmail(updateBody.email()) && !found.getEmail().equals(updateBody.email())) {
            throw new BadRequestException("L'email " + updateBody.email() + " è già in uso!");
        } else {
            found.setNome(updateBody.nome());
            found.setCognome(updateBody.cognome());
            found.setEmail(updateBody.email());
            found.setPassword(updateBody.password());
            return atletiRepository.save(found);
        }
    }
}
