package diego.basili.AtlheticusCIV.controllers;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.enums.Ruolo;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.exceptions.UnauthorizedException;
import diego.basili.AtlheticusCIV.payloads.AtletaAuthorizationDTO;
import diego.basili.AtlheticusCIV.payloads.AtletaDTO;
import diego.basili.AtlheticusCIV.payloads.AtletaValoriDTO;
import diego.basili.AtlheticusCIV.payloads.RuoliInCampoDTO;
import diego.basili.AtlheticusCIV.services.AtletiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/atleti")
public class AtletiController {
    @Autowired
    private AtletiService atletiService;

    /*ME*/
    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Atleta getProfile(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta) {
        return currentAuthenticatedAtleta;
    }

    @DeleteMapping("/me")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta) {
        this.atletiService.findByIdAndDelete(currentAuthenticatedAtleta.getId());
    }

    @PutMapping("/me")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Atleta updateProfile(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @RequestBody @Validated AtletaDTO body) {
        return this.atletiService.findByIdAndUpdate(currentAuthenticatedAtleta.getId(), body);
    }

    @PutMapping(value = "/me/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Atleta uploadMeAvatar(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.atletiService.uploadImage(currentAuthenticatedAtleta.getId(), image);
    }

    @PutMapping("/me/ruoli")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Atleta updateRuoli(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @RequestBody @Validated RuoliInCampoDTO ruoliDTO) {
        return  atletiService.updateRuoliInCampo(currentAuthenticatedAtleta.getId(), ruoliDTO);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Page<Atleta> findAll(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "100") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.atletiService.findAll(page, size, sortBy);
    }

    @GetMapping("/{atletaId}")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
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
        checkAuthorization(currentAuthenticatedAtleta, targetAtleta);
        return this.atletiService.findByIdAndUpdate(atletaId, body);
    }

    @DeleteMapping("/{atletaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public void findByIdAndDelete(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @PathVariable UUID atletaId) {
        Atleta targetAtleta = findById(atletaId);
        checkAuthorization(currentAuthenticatedAtleta, targetAtleta);
        this.atletiService.findByIdAndDelete(atletaId);
    }

    @PutMapping("/{atletaId}/avatar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Atleta uploadAvatar(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @PathVariable UUID atletaId, @RequestParam("avatar") MultipartFile image) throws IOException {
        Atleta targetAtleta = findById(atletaId);
        checkAuthorization(currentAuthenticatedAtleta, targetAtleta);
        return this.atletiService.uploadImage(atletaId, image);
    }

    @PutMapping("/{atletaId}/ruoli")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Atleta updateRuoli(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @PathVariable UUID atletaId, @RequestBody @Validated RuoliInCampoDTO ruoliDTO) {
        Atleta targetAtleta = findById(atletaId);
        checkAuthorization(currentAuthenticatedAtleta, targetAtleta);
        return  atletiService.updateRuoliInCampo(atletaId, ruoliDTO);
    }

    @PatchMapping("/{atletaId}/authorization")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Atleta updateAuthorization(@AuthenticationPrincipal Atleta currentAuthenticatedAtleta, @PathVariable UUID atletaId, @RequestBody @Validated AtletaAuthorizationDTO body) {
        Atleta targetAtleta = findById(atletaId);
        checkAuthorization(currentAuthenticatedAtleta, targetAtleta);
        return atletiService.updateAuthorization(atletaId, body);
    }

    @PutMapping("/{atletaId}/storico")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Atleta updateStorico(@PathVariable UUID atletaId, @RequestBody @Validated AtletaValoriDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        }
        return this.atletiService.updateStorico(atletaId, body);
    }

    private void checkAuthorization(Atleta currentAuthenticatedAtleta, Atleta targetAtleta) {
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.ADMIN && targetAtleta.getRuolo() == Ruolo.ADMIN
                && !currentAuthenticatedAtleta.getId().equals(targetAtleta.getId())) {
            throw new UnauthorizedException("Gli amministratori non possono modificare altri amministratori.");
        }
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.SUPERADMIN && targetAtleta.getRuolo() == Ruolo.SUPERADMIN
                && !currentAuthenticatedAtleta.getId().equals(targetAtleta.getId())) {
            throw new UnauthorizedException("Gli superamministratori non possono modificare altri superamministratori.");
        }
        if (currentAuthenticatedAtleta.getRuolo() == Ruolo.ADMIN && targetAtleta.getRuolo() == Ruolo.SUPERADMIN) {
            throw new UnauthorizedException("Gli amministratori non possono modificare i superamministratori.");
        }
    }
}
