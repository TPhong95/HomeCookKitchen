package user.chefuser.chef;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChefRepo extends JpaRepository<Chef, Long> {
    List<Chef> findByActiveTrue();
}
