package org.example.purchase.purchase;

import org.example.purchase.dtos.PurchasedListingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {
    @Query("""
        select new org.example.purchase.dtos.PurchasedListingDto(
            p.customerId, sum(e.quantity))
        from Purchase p join p.purchaseEvent e
        where e.listingInfoDto.recipeListingId = :productListingId
        group by p.customerId
        """)
    List<PurchasedListingDto> findQuantitiesByProductListingId(
            @Param("productListingId") Long productListingId);
}
