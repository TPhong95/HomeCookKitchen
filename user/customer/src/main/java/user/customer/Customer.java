package user.customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_gen")
    @SequenceGenerator(name = "customer_gen", sequenceName = "customer_seq", allocationSize = 1)
    @Column(name = "id")
    private Long customerId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}