package diego.basili.AtlheticusCIV.controllers;

import diego.basili.AtlheticusCIV.entities.Partita;
import diego.basili.AtlheticusCIV.entities.PrenotazionePartita;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.payloads.NewEntityRespDTO;
import diego.basili.AtlheticusCIV.payloads.PartitaDTO;
import diego.basili.AtlheticusCIV.repositories.PartiteRepository;
import diego.basili.AtlheticusCIV.services.PartiteService;
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
@RequestMapping("/partite")
public class PartiteController {
    @Autowired
    private PartiteService partiteService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Page<Partita> findAll(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sortBy) {
        return this.partiteService.findAll(page, size, sortBy);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public NewEntityRespDTO save(@RequestBody @Validated PartitaDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return new NewEntityRespDTO(this.partiteService.savePartita(body).getId());
        }
    }

    @GetMapping("/{partitaId}")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Partita findById(@PathVariable UUID partitaId){
        return this.partiteService.findById(partitaId);
    }

    @PutMapping("/{partitaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Partita findByIdAndUpdate(@PathVariable UUID partitaId, @RequestBody @Validated PartitaDTO body, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return this.partiteService.findByIdAndUpdate(partitaId, body);
        }
    }

    @DeleteMapping("/{partitaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public void findByIdAndDelete(@PathVariable UUID partitaId){
        this.partiteService.delete(partitaId);
    }
}
