package com.jourgeois.backend.repository;

import com.jourgeois.backend.api.dto.cocktail.CocktailVO;
import com.jourgeois.backend.domain.cocktail.Cocktail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

    List<Cocktail> findAllByOrderById(Pageable pageable);
    @Query("SELECT c.id as id, c.name as name, c.nameKR as nameKR, c.alcohol as alcohol, c.cupId as cupId, " +
            "c.tag as tag, c.baseLiquor as baseLiquor, c.category as category, c.recipe as recipe, c.img as img FROM Cocktail c WHERE c.id = :id")
    Optional<CocktailVO> findCocktailById(@Param("id") Long id);

    @Query("SELECT cp.nameKR FROM Cocktail c JOIN Cup cp ON c.cupId.id = cp.id WHERE c.id = :id")
    Optional<String> findCocktailCupById(@Param("id") Long id);
    @Query("SELECT m.nameKR, m.img FROM Cocktail c JOIN CocktailToMaterial cm ON c.id = cm.cocktail.id JOIN Material m ON cm.material.id = m.id WHERE c.id = :id")
    Optional<ArrayList<String>> findAllMaterialsByCocktailId(@Param("id") Long id);

    @Query(nativeQuery = true, value="select * from cocktail where c_name_kr LIKE CONCAT('%',:name,'%') or c_name LIKE CONCAT('%',:name,'%') union " +
            "select * from cocktail where c_id in (select distinct c_id from cocktail_to_material where m_id in " +
            "(select m_id from material where m_name_kr LIKE CONCAT('%',:name,'%') or m_name LIKE CONCAT('%',:name,'%'))) order by c_name_kr limit 10 offset :page")
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
    Integer findByFilter(@Param("type") String type, @Param("abvlow") int abvlow, @Param("abvhigh") int abvhigh,
                         @Param("materials") List<Integer> materials, @Param("sum") int sum);

    Integer countByTypeAndAlcoholBetween(String type, double low, double high);

    @Query(nativeQuery = true, value="select * from cocktail where c_id in \n" +
            "(select c.c_id from \n" +
            "(select c_id from cocktail_to_material where m_id in :materials) c \n" +
            "group by c_id having count(c.c_id)=:sum)\n" +
            "and c_type=:type\n" +
            "and c_alcohol between :abvlow and :abvhigh limit 10 offset :page")
    List<Cocktail> findByFilterInfo(@Param("type") String type, @Param("abvlow") int abvlow, @Param("abvhigh") int abvhigh,
                                    @Param("materials") List<Integer> materials, @Param("sum") int sum, @Param("page") int page);

    @Query(value="SELECT * FROM cocktail WHERE c_type=:type AND c_alcohol BETWEEN :low AND :high limit :limit offset :offset", nativeQuery = true)
    List<Cocktail> findByTypeAndAlcoholBetween(String type, double low, double high, int offset, int limit);
//    @Query("UPDATE Cocktail c SET c.name = :name, c.nameKR = :nameKR, c.alcohol = :alcohol, c.cupId = :cupId," +
//            " c.tag = :tag, c.baseLiquor = :baseLiquor, c.category = :category, c.recipe = :recipe WHERE c.id = :id")
//    Optional<Cocktail> updateCocktail(@Param("Cocktail") Cocktail cocktail);

    @Query(value = "select * from cocktail where c_base_liquor = (select c_base_liquor\n" +
            "from cocktail \n" +
            "where c_id in (select c_id from cocktail_bookmark where m_id = :uid) \n" +
            "group by c_base_liquor\n" +
            "order by count(c_base_liquor) desc limit 1)", nativeQuery = true)
    List<Cocktail> findByrecommenderLiquor(Long uid, Pageable pageable);
}
