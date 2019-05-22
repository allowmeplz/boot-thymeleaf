package iducsa.boot.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iducsa.boot.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public List<User> findByName(String name);
}
