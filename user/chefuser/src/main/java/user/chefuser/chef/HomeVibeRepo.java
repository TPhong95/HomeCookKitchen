package user.chefuser.chef;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HomeVibeRepo extends JpaRepository<HomeVibe, Long> {
    Optional<HomeVibe> findByAddressId(Long addressId);
}