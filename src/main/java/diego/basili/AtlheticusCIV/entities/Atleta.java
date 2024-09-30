package diego.basili.AtlheticusCIV.entities;

import diego.basili.AtlheticusCIV.enums.Ruolo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
    private String numeroTelefono;
    private String password;
    private String avatar;
    private Ruolo ruolo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "valutazione_id")
    private Valutazione valutazione;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "statistica_id")
    private Statistica statistica;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "atleta_partita",
            joinColumns = @JoinColumn(name = "atleta_id"),
            inverseJoinColumns = @JoinColumn(name = "partita_id")
    )
    private List<Partita> partite;

    public Atleta(String nome, String cognome, String numeroTelefono, String email, String password, String avatar, Ruolo ruolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.numeroTelefono = numeroTelefono;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.ruolo = ruolo;
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
