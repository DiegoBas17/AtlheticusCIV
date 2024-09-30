package diego.basili.AtlheticusCIV.controllers;

import diego.basili.AtlheticusCIV.exceptions.BadRequestException;
import diego.basili.AtlheticusCIV.payloads.AtletaDTO;
import diego.basili.AtlheticusCIV.payloads.AtletaLoginDTO;
import diego.basili.AtlheticusCIV.payloads.AtletaLoginRespDTO;
import diego.basili.AtlheticusCIV.payloads.AtletaRespDTO;
import diego.basili.AtlheticusCIV.services.AtletiService;
import diego.basili.AtlheticusCIV.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private AtletiService atletiService;

    @PostMapping("/login")
    public AtletaLoginRespDTO login(@RequestBody AtletaLoginDTO payload) {
        return new AtletaLoginRespDTO(this.authService.checkCredentialsAndGenerateToken(payload));
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AtletaRespDTO save(@RequestBody @Validated AtletaDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            String messages = validationResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". "));
            throw new BadRequestException("Ci sono stati errori nel payload. " + messages);
        } else {
            return new AtletaRespDTO(this.atletiService.saveAtleta(body).getId());
        }

    }
}
