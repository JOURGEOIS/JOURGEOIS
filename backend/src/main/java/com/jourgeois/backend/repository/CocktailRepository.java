package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.SearchFilterDto;
import com.jourgeois.backend.domain.Cocktail;
import com.jourgeois.backend.domain.Cup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CocktailRepository extends JpaRepository<Cocktail, Cup> {

    Optional<Cocktail> findById(Long id);
    Optional<Cocktail> deleteById(Long id);

    @Query("SELECT c.id, c.name, c.nameKR, c.alcohol, c.cupId.id, c.tag, c.baseLiquor, c.category, c.recipe FROM Cocktail c WHERE c.id = :id")
    Optional<String> findCocktailById(@Param("id") Long id);

    @Query("SELECT cp.nameKR FROM Cocktail c JOIN Cup cp ON c.cupId.id = cp.id WHERE c.id = :id")
    Optional<String> findCocktailCupById(@Param("id") Long id);
    @Query("SELECT m.nameKR, m.img FROM Cocktail c JOIN CocktailToMaterial cm ON c.id = cm.cocktail.id JOIN Material m ON cm.material.id = m.id WHERE c.id = :id")
    Optional<ArrayList<String>> findAllMaterialsByCocktailId(@Param("id") Long id);

    @Query(nativeQuery = true, value="select * from cocktail where c_name_kr LIKE CONCAT('%',:name,'%') union " +
            "select * from cocktail where c_id in (select distinct c_id from cocktail_to_material where m_id in " +
            "(select m_id from material where m_name_kr LIKE CONCAT('%',:name,'%'))) order by c_name_kr limit 10 offset :page")
    List<Cocktail> findCocktailBySearch(@Param("name") String name, @Param("page") int page);

    @Query(nativeQuery = true, value = "select * from cocktail where c_id in (select distinct c_id from cocktail_to_material where m_id=:id)" +
            " order by c_name_kr limit 10 offset :page")
    List<Cocktail> findByMaterialContaining(@Param("id") Long id, @Param("page") int page);

    @Query(nativeQuery = true, value="select count(*) from cocktail where c_id in \n" +
            "(select c.c_id from \n" +
            "(select c_id from cocktail_to_material where m_id in :materials) c \n" +
            "group by c_id having count(c.c_id)=:sum)\n" +
            "and c_type=:type\n" +
            "and c_alcohol between :abvlow and :abvhigh")
    Integer findByFilter(@Param("type") String type, @Param("abvlow") int abvlow, @Param("abvhigh") int abvhigh, @Param("materials") List<Integer> materials, @Param("sum") int sum);

    @Query(nativeQuery = true, value="select * from cocktail where c_id in \n" +
            "(select c.c_id from \n" +
            "(select c_id from cocktail_to_material where m_id in :materials) c \n" +
            "group by c_id having count(c.c_id)=:sum)\n" +
            "and c_type=:type\n" +
            "and c_alcohol between :abvlow and :abvhigh")
    List<Cocktail> findByFilterInfo(@Param("type") String type, @Param("abvlow") int abvlow, @Param("abvhigh") int abvhigh, @Param("materials") List<Integer> materials, @Param("sum") int sum);

//    @Query("UPDATE Cocktail c SET c.name = :name, c.nameKR = :nameKR, c.alcohol = :alcohol, c.cupId = :cupId," +
//            " c.tag = :tag, c.baseLiquor = :baseLiquor, c.category = :category, c.recipe = :recipe WHERE c.id = :id")
//    Optional<Cocktail> updateCocktail(@Param("Cocktail") Cocktail cocktail);
}
