package org.school.exam.productlisting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductListingRepo extends JpaRepository<ProductListing, Long> {
    @Query("SELECT p FROM ProductListing p WHERE p.active = true AND p.purchaseDeadline < :now")
    List<ProductListing> findActiveWithPurchaseDeadlineAfter(@Param("now") LocalDateTime now);

    @Query("SELECT p FROM ProductListing p WHERE p.active = true")
    List<ProductListing> findActiveForCustomer();

    @Query("SELECT p FROM ProductListing p WHERE p.active = true AND p.chefId = :id ORDER BY p.readyBy ASC")
    List<ProductListing> findChefActiveListings(@Param("chefId") Long id);

    @Query("SELECT p FROM ProductListing p WHERE p.active = false AND p.chefId = :id ORDER BY p.readyBy DESC")
    List<ProductListing> findChefHistoricListings(@Param("chefId") Long id);
}
