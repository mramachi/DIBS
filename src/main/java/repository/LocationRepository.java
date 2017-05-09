package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hello.model.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
	List<Location> findByLocationID(long locationID);
}