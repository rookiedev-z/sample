package net.gittab.fsmsample.repository;

import net.gittab.fsmsample.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author rookiedev
 */
public interface StatusRepository extends JpaRepository<Status, Long> {
}
