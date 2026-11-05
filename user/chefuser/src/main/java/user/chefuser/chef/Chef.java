package user.chefuser.chef;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chef {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "chef_seq", sequenceName = "chef_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    private Boolean active = true;

    @OneToMany(mappedBy = "chef", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Address> addresses = new ArrayList<>();

    public void addAddress(Address address){
        addresses.add(address);
        address.setChef(this);
    }

}
