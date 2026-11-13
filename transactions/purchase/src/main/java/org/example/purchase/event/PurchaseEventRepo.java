package org.example.purchase.event;

import org.example.purchase.dtos.PurchasedListingQuantityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseEventRepo extends JpaRepository<PurchaseEvent,Long> {
    @Query("""
            select new org.example.purchase.dtos.PurchasedListingQuantityDto(
                e.listingInfoDto.recipeListingId,
                sum(e.quantity)
            )
            from PurchaseEvent e
            where e.purchase.customerId = :customerId
            group by e.listingInfoDto.recipeListingId
            order by e.listingInfoDto.recipeListingId
            """)
    List<PurchasedListingQuantityDto> sumQuantitiesByListingForCustomer(@Param("customerId") Long customerId);
}
