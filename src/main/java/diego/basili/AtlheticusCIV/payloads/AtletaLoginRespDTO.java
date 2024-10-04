package diego.basili.AtlheticusCIV.payloads;

import diego.basili.AtlheticusCIV.entities.Atleta;

public record AtletaLoginRespDTO(String accessToken,
                                 Atleta atleta) {
}
