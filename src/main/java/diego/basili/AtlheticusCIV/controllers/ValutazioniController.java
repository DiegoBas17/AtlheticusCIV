package diego.basili.AtlheticusCIV.controllers;

import diego.basili.AtlheticusCIV.entities.Partita;
import diego.basili.AtlheticusCIV.entities.Valutazione;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.payloads.NewEntityRespDTO;
import diego.basili.AtlheticusCIV.payloads.PartitaDTO;
import diego.basili.AtlheticusCIV.payloads.ValutazioneDTO;
import diego.basili.AtlheticusCIV.repositories.ValutazioniRepository;
import diego.basili.AtlheticusCIV.services.ValutazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/valutazioni")
public class ValutazioniController {
    @Autowired
    private ValutazioniService valutazioniService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Page<Valutazione> findAll(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy) {
        return this.valutazioniService.findAll(page, size, sortBy);
    }

    @GetMapping("/{valutazioneId}")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Valutazione findById(@PathVariable UUID valutazioneId){
        return this.valutazioniService.findById(valutazioneId);
    }

    @DeleteMapping("/{valutazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID valutazioneId){
        this.valutazioniService.findByIdAndDelete(valutazioneId);
    }

    @PostMapping("{atletaId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public NewEntityRespDTO save(@PathVariable UUID atletaId, @RequestBody @Validated ValutazioneDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return new NewEntityRespDTO(this.valutazioniService.saveValutazione(body, atletaId).getId());
        }
    }

    @PutMapping("/{valutazioneId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Valutazione findByIdAndUpdate(@PathVariable UUID valutazioneId, @RequestBody @Validated ValutazioneDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return this.valutazioniService.findByIdAndUpdate(body, valutazioneId);
        }
    }
}
