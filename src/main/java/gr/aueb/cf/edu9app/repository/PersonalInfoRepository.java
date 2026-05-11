package gr.aueb.cf.edu9app.repository;

import gr.aueb.cf.edu9app.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PersonalInfoRepository extends JpaRepository<PersonalInfo,Long>,
        JpaSpecificationExecutor<PersonalInfo> {

    Optional<PersonalInfo> findByAmka(String amka);
}
