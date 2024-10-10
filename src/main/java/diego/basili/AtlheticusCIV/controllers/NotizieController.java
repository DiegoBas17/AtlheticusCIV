package diego.basili.AtlheticusCIV.controllers;

import diego.basili.AtlheticusCIV.entities.Notizia;
import diego.basili.AtlheticusCIV.payloads.NotiziaDTO;
import diego.basili.AtlheticusCIV.services.NotizieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/notizie")
public class NotizieController {
    @Autowired
    private NotizieService notizieService;

    @GetMapping("/{notiziaId}")
    public Notizia findById(@PathVariable UUID notiziaId) {
        return notizieService.findById(notiziaId);
    }

    @GetMapping
    public Page<Notizia> findAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "titolo") String sortBy) {
        return notizieService.findAll(page, size, sortBy);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Notizia save(@ModelAttribute @Validated NotiziaDTO body) {
        return notizieService.saveNotizia(body);
    }

    @PutMapping("/{notiziaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public Notizia findByIdAndUpdate(@PathVariable UUID notiziaId, @ModelAttribute @Validated NotiziaDTO body) {
        return notizieService.updateNotizia(notiziaId, body);
    }

    @DeleteMapping("/{notiziaId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID notiziaId) {
        notizieService.deleteNotizia(notiziaId);
    }
}
