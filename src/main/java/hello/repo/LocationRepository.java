package hello.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import hello.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	Location findByLocationID(long locationID);
	List<Location> findByLocationCity(String city);
	@Query("SELECT l FROM Location l WHERE l.locationCity = ?3 AND l.locationHouseNumber = ?2 AND l.locationStreet=?1 AND l.locationZIP=?4")
	Location findLocation(String street, int housenumber, String city, char[] zip );
	
}