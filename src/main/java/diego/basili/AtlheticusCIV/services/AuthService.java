package diego.basili.AtlheticusCIV.services;

import diego.basili.AtlheticusCIV.entities.Atleta;
import diego.basili.AtlheticusCIV.exceptions.UnauthorizedException;
import diego.basili.AtlheticusCIV.payloads.AtletaLoginDTO;
import diego.basili.AtlheticusCIV.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AtletiService atletiService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder bcrypt;

    public String checkCredentialsAndGenerateToken(AtletaLoginDTO body) {
        Atleta found = this.atletiService.findByEmail(body.email());
        if (bcrypt.matches(body.password(), found.getPassword())) {
            return jwtTools.createToken(found);
        } else {
            throw new UnauthorizedException("Credenziali errate!");
        }
    }
}
