package diego.basili.AtlheticusCIV.controllers;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.entities.PrenotazionePartita;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.payloads.NewEntityRespDTO;
import diego.basili.AtlheticusCIV.payloads.PrenotazionePartitaDTO;
import diego.basili.AtlheticusCIV.repositories.PrenotazioniPartiteRepository;
import diego.basili.AtlheticusCIV.services.PrenotazioniPartiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prenotazioni-partite")
public class PrenotazioniPartiteController {
    @Autowired
    private PrenotazioniPartiteService prenotazioniPartiteService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Page<PrenotazionePartita> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "20") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.prenotazioniPartiteService.findAll(page, size, sortBy);
    }

    @GetMapping("/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public PrenotazionePartita findById(@PathVariable UUID prenotazioneId) {
        return this.prenotazioniPartiteService.findById(prenotazioneId);
    }

    @PostMapping("/{partitaId}")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public NewEntityRespDTO save(@AuthenticationPrincipal Atleta atleta, @PathVariable UUID partitaId) {
            return new NewEntityRespDTO(this.prenotazioniPartiteService.savePrenotazione(atleta, partitaId).getId());
        }

    @DeleteMapping("/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public void findByIdAndDelete(@PathVariable UUID prenotazioneId) {
        prenotazioniPartiteService.delete(prenotazioneId);
    }

    @PutMapping("/{prenotazioneId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public PrenotazionePartita findByIdAndUpdate(@PathVariable UUID prenotazioneId, @RequestBody @Validated PrenotazionePartitaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return this.prenotazioniPartiteService.findByIdAndUpdate(prenotazioneId, body);
        }
    }
}