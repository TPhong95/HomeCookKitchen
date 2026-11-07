package user.chefuser.chef;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HomeVibe {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "home_vibe_seq")
    @SequenceGenerator(name = "home_vibe_seq", sequenceName = "home_vibe_seq", allocationSize = 1)
    private Long id;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    private Genre music;

    private Boolean pets = false;
    private Boolean outdoor = false;
    private Boolean familyFriendly = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false, unique = true)
    private Address address;
}