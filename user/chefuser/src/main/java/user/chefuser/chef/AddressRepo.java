package user.chefuser.chef;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
    Optional<Address> findByIdAndChefId(Long id, Long chefId);

    @Query("SELECT a FROM Address a WHERE a.active = TRUE AND a.chef.id = :chefId")
    List<Address> findActiveByChefId(@Param("chefId") Long chefId);
}
