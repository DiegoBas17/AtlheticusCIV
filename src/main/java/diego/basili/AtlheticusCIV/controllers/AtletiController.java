package diego.basili.AtlheticusCIV.controllers;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.enums.Ruolo;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.UnauthorizedException;
import diego.basili.AtlheticusCIV.payloads.AtletaDTO;
import diego.basili.AtlheticusCIV.services.AtletiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/atleti")
public class AtletiController {
    @Autowired
    private AtletiService atletiService;

    /*ME*/
    @GetMapping("/me")
    public Atleta getProfile(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta) {
        return currentAuthenticatedAtleta;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta) {
        this.atletiService.findByIdAndDelete(currentAuthenticatedAtleta.getId());
    }

    @PutMapping("/me")
    public Atleta updateProfile(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @RequestBody AtletaDTO body) {
        return this.atletiService.findByIdAndUpdate(currentAuthenticatedAtleta.getId(), body);
    }

    @PutMapping("/me/avatar")
    public Atleta uploadMeAvatar(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.atletiService.uploadImage(currentAuthenticatedAtleta.getId(), image);
    }

    @GetMapping
    public Page<Atleta> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return this.atletiService.findAll(page, size, sortBy);
    }

    @GetMapping("/{atletaId}")
    public Atleta findById(@PathVariable UUID atletaId) {
        return this.atletiService.findById(atletaId);
    }

    @PutMapping("/{atletaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Atleta findByIdAndUpdate(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @PathVariable UUID atletaId, @RequestBody @Validated AtletaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        }
        Atleta targetAtleta = findById(atletaId);
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.ADMIN && targetAtleta.getRuolo() == Ruolo.ADMIN && !currentAuthenticatedAtleta.getId().equals(targetAtleta.getId())) {
            throw new UnauthorizedException("Gli amministratori non possono modificare altri amministratori.");
        }
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.SUPERADMIN && targetAtleta.getRuolo() == Ruolo.SUPERADMIN && !currentAuthenticatedAtleta.getId().equals(targetAtleta.getId())) {
            throw new UnauthorizedException("Gli superamministratori non possono modificare altri superamministratori.");
        }
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.ADMIN && targetAtleta.getRuolo() == Ruolo.SUPERADMIN) {
            throw new UnauthorizedException("Gli amministratori non possono modificare altri i superamministratori.");
        }
        return this.atletiService.findByIdAndUpdate(atletaId, body);
    }

    @DeleteMapping("/{atletaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public void findByIdAndDelete(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @PathVariable UUID atletaId) {
        Atleta targetAtleta = findById(atletaId);
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.ADMIN && targetAtleta.getRuolo() == Ruolo.ADMIN && !currentAuthenticatedAtleta.getId().equals(targetAtleta.getId())) {
            throw new UnauthorizedException("Gli amministratori non possono modificare altri amministratori.");
        }
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.SUPERADMIN && targetAtleta.getRuolo() == Ruolo.SUPERADMIN && !currentAuthenticatedAtleta.getId().equals(targetAtleta.getId())) {
            throw new UnauthorizedException("Gli superamministratori non possono modificare altri superamministratori.");
        }
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.ADMIN && targetAtleta.getRuolo() == Ruolo.SUPERADMIN) {
            throw new UnauthorizedException("Gli amministratori non possono modificare altri i superamministratori.");
        }
        this.atletiService.findByIdAndDelete(atletaId);
    }

    @PutMapping("/{atletaId}/avatar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Atleta uploadAvatar(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @PathVariable UUID atletaId, @RequestParam("avatar") MultipartFile image) throws IOException {
        Atleta targetAtleta = findById(atletaId);
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.ADMIN && targetAtleta.getRuolo() == Ruolo.ADMIN && !currentAuthenticatedAtleta.getId().equals(targetAtleta.getId())) {
            throw new UnauthorizedException("Gli amministratori non possono modificare altri amministratori.");
        }
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.SUPERADMIN && targetAtleta.getRuolo() == Ruolo.SUPERADMIN && !currentAuthenticatedAtleta.getId().equals(targetAtleta.getId())) {
            throw new UnauthorizedException("Gli superamministratori non possono modificare altri superamministratori.");
        }
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.ADMIN && targetAtleta.getRuolo() == Ruolo.SUPERADMIN) {
            throw new UnauthorizedException("Gli amministratori non possono modificare altri i superamministratori.");
        }
        return this.atletiService.uploadImage(atletaId, image);
    }
}
