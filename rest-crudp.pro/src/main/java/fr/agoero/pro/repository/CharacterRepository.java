package fr.agoero.pro.repository;

import fr.agoero.pro.domain.Character;
import fr.agoero.pro.repository.common.CommonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CommonRepository<Character, Integer>,
        JpaRepository<Character, Integer>, JpaSpecificationExecutor<Character> {

    @Query("DELETE FROM Character c WHERE c.id=:id")
    @Modifying
    void deleteById(@Param("id") int id);
}
