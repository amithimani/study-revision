package knowledgecafe.repos;

import knowledgecafe.model.AmenityType;
import knowledgecafe.model.Capacity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapacityRepository extends JpaRepository<Capacity, Long> {

    Capacity findByAmenityType(AmenityType amenityType);
}
