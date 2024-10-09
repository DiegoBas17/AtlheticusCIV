package diego.basili.AtlheticusCIV.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import diego.basili.AtlheticusCIV.enums.Ruolo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "atleti")
public class Atleta implements UserDetails {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue
    private UUID id;
    private String nome;
    private String cognome;
    private String email;
    @Column(name = "numero_telefono")
    private String numeroTelefono;
    @JsonIgnore
    private String password;
    private String avatar;
    private Double mediaGol;
    private Double mediaAssist;
    private Long partiteGiocate;
    private Double mediaVoti;
    private Long totaleGol;
    private Long totaleAssist;
    @Enumerated(EnumType.STRING)
    private Ruolo ruolo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "valutazione_id")
    private Valutazione valutazione;

    @JsonIgnore
    @OneToMany(mappedBy = "atleta", cascade = CascadeType.ALL)
    private List<PrenotazionePartita> prenotazioniPartite;

    @JsonIgnore
    @OneToMany(mappedBy = "atleta", cascade = CascadeType.ALL)
    private List<Voto> voti;

    @JsonIgnore
    @OneToMany(mappedBy = "atleta", cascade = CascadeType.ALL)
    private List<Statistica> statistiche;


    public Atleta(String nome, String cognome, String numeroTelefono, String email, String password, String avatar, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeroTelefono = numeroTelefono;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.ruolo = ruolo;
        this.mediaGol = 0.0;
        this.mediaAssist = 0.0;
        this.partiteGiocate = 0L;
        this.mediaVoti = 0.0;
        this.totaleGol = 0L;
        this.totaleAssist = 0L;
        this.prenotazioniPartite = new ArrayList<>();
        this.voti = new ArrayList<>();
        this.statistiche = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(ruolo.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
