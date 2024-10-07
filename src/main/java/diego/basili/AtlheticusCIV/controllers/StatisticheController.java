package diego.basili.AtlheticusCIV.controllers;

import diego.basili.AtlheticusCIV.entities.Partita;
import diego.basili.AtlheticusCIV.entities.Statistica;
import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.payloads.NewEntityRespDTO;
import diego.basili.AtlheticusCIV.payloads.PartitaDTO;
import diego.basili.AtlheticusCIV.services.StatisticheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistiche")
public class StatisticheController {
    @Autowired
    private StatisticheService statisticheService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Page<Statistica> findAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        return this.statisticheService.findAll(page, size, sortBy);
    }

    @PostMapping("{partitaId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public List<Statistica> save(@PathVariable UUID partitaId){
            return this.statisticheService.saveStatistiche(partitaId);
    }

    @GetMapping("/{statisticaId}")
    @PreAuthorize("hasAnyAuthority('VISITATORE', 'ATLETA','ADMIN', 'SUPERADMIN')")
    public Statistica findById(@PathVariable UUID statisticaId){
        return this.statisticheService.findById(statisticaId);
    }

    @DeleteMapping("/{statisticaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID statisticaId){
        this.statisticheService.delete(statisticaId);
    }
}
