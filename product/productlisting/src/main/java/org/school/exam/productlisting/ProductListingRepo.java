package org.school.exam.productlisting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductListingRepo extends JpaRepository<ProductListing, Long> {

}
