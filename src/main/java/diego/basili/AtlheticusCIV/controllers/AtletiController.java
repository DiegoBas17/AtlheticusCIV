package diego.basili.AtlheticusCIV.controllers;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.payloads.AtletaDTO;
import diego.basili.AtlheticusCIV.services.AtletiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/atleti")
public class AtletiController {
    @Autowired
    private AtletiService atletiService;

    /*ME*/
    @GetMapping("/me")
    public Atleta getProfile(@AuthenticationPrincipal Atleta currentAuthenticatedUser) {
        return currentAuthenticatedUser;
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@AuthenticationPrincipal Atleta currentAuthenticatedUser) {
        this.atletiService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }

    @PutMapping("/me")
    public Atleta updateProfile(@AuthenticationPrincipal Atleta currentAuthenticatedUser, @RequestBody AtletaDTO body) {
        return this.atletiService.findByIdAndUpdate(currentAuthenticatedUser.getId(), body);
    }

    @PutMapping("/me/avatar")
    public Atleta uploadMeAvatar(@AuthenticationPrincipal Atleta currentAuthenticatedUser, @RequestParam("avatar") MultipartFile image) throws IOException {
        return this.atletiService.uploadImage(currentAuthenticatedUser.getId(), image);
    }

}
