package diego.basili.AtlheticusCIV.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.entities.Statistica;
import diego.basili.AtlheticusCIV.entities.Valutazione;
import diego.basili.AtlheticusCIV.enums.Ruolo;
import diego.basili.AtlheticusCIV.enums.RuoloInCampo;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.NotFoundException;
import diego.basili.AtlheticusCIV.payloads.AtletaDTO;
import diego.basili.AtlheticusCIV.payloads.RuoliInCampoDTO;
import diego.basili.AtlheticusCIV.payloads.ValutazioneDTO;
import diego.basili.AtlheticusCIV.repositories.AtletiRepository;
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
import java.util.Objects;
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
    private ValutazioniService valutazioniService;


    public Atleta saveAtleta(AtletaDTO body) {
        if (body == null) {
            throw new BadRequestException("Body richiesto");
        } else if (this.atletiRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
        } else {
            Ruolo ruolo;
            if (Objects.equals(body.email(), "d.basili17@gmail.com")) {
                ruolo = Ruolo.SUPERADMIN;
            } else {
                ruolo = Ruolo.VISITATORE;
            }
            Atleta atleta = new Atleta(body.nome(), body.cognome(), body.numeroDiCellulare(), body.email(), bcrypt.encode(body.password()), "https://ui-avatars.com/api/?name=" + body.nome() + "+" + body.cognome(), ruolo);
            atletiRepository.save(atleta);
            ValutazioneDTO valutazioneAdminDTO = new ValutazioneDTO(0, 0, 0, 0, 0);
            Valutazione valutazioneAdmin = valutazioniService.saveValutazione(valutazioneAdminDTO, atleta.getId());
            ValutazioneDTO valutazioneCivDTO = new ValutazioneDTO(0, 0, 0, 0, 0);
            Valutazione valutazioneCiv = valutazioniService.saveValutazione(valutazioneCivDTO, atleta.getId());
            atleta.setValutazioneAdmin(valutazioneAdmin);
            atleta.setValutazioneCIV(valutazioneCiv);
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

    public Atleta uploadImage(UUID atletaId, MultipartFile file) {
        try {
            Atleta atleta = findById(atletaId);
            String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
            atleta.setAvatar(url);
            return atletiRepository.save(atleta);
        } catch (IOException e ) {
            throw new BadRequestException("Errore nel caricamento del file, verifica il formato o le dimensioni!");
        }

    }

    public void addStatistica (Atleta atleta, Statistica statistica) {
        if (atleta == null || statistica == null) {
            throw new BadRequestException("Atleta e Statistica richiesti");
        }
        atleta.setTotaleGol(atleta.getTotaleGol() + statistica.getGol());
        atleta.setTotaleAssist(atleta.getTotaleAssist() + statistica.getAssist());
        atleta.setPartiteGiocate(atleta.getPartiteGiocate() + 1);
        atleta.aggiornaMediaGol();
        atleta.aggiornaMediaAssist();
        atleta.getStatistiche().add(statistica);
        atletiRepository.save(atleta);
    }

    public Atleta updateRuoliInCampo(UUID atletaId, RuoliInCampoDTO body) {
        Atleta atleta = findById(atletaId);
        RuoloInCampo ruoloInCampoPrimario;
        RuoloInCampo ruoloInCampoSecondario;
        RuoloInCampo ruoloInCampoAlternativo;
        try {
            ruoloInCampoPrimario = RuoloInCampo.valueOf(body.ruoloInCampoPrimario());
            ruoloInCampoSecondario = RuoloInCampo.valueOf(body.ruoloInCampoSecondario());
            ruoloInCampoAlternativo = RuoloInCampo.valueOf(body.ruoloInCampoAlternativo());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Errori nei Ruoli inseriti!");
        }
        atleta.setRuoloInCampoPrimario(ruoloInCampoPrimario);
        atleta.setRuoloInCampoSecondario(ruoloInCampoSecondario);
        atleta.setRuoloInCampoAlternativo(ruoloInCampoAlternativo);
        return atletiRepository.save(atleta);
    }
}
