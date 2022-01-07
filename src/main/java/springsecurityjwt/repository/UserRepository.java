package springsecurityjwt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springsecurityjwt.entity.DigitalUser;

@Repository
public interface UserRepository extends JpaRepository<DigitalUser,Long> {

    DigitalUser findByIdentityNo(String identityNo);
}
