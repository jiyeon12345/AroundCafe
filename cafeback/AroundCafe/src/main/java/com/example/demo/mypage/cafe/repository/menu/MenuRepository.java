package com.example.demo.mypage.cafe.repository.menu;

import com.example.demo.mypage.cafe.entity.Cafe;
import com.example.demo.mypage.cafe.entity.CafeMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<CafeMenu, Long> {
    @Query("select cm from CafeMenu cm join fetch cm.cafe c where c.cafeNo = :cafeNo order by cm.menu_no desc")
    public List<CafeMenu> findByCafeNo(@Param("cafeNo") Long cafeNo);

    @Query("select count(cm.menu_no) from CafeMenu cm where cm.signature = true " +
            "and cm.cafe = :cafe")
    Optional<Integer> countSignature(@Param("cafe") Cafe cafe);

    @Query("select cm from CafeMenu cm where cm.signature = true")
    public List<CafeMenu> findBySignatureTrue();

    @Query("select cm from CafeMenu cm where cm.sold_out = true")
    public List<CafeMenu> findBySoldTrue();

    @Transactional
    @Modifying
    @Query("delete from CafeMenu cm where cm.cafe in (select c from Cafe c where c.cafeNo = :cafe_no)")
    public void deleteByCafeNo(@Param("cafe_no") Long cafe_no);

    @Query("select cm from CafeMenu cm where cm.menu_name like %:menuName% and cm.cafe in (select c from Cafe c where c.cafeNo = :cafeNo) order by cm.menu_no desc")
    List<CafeMenu> searchList(@Param("cafeNo") Long cafeNo, @Param("menuName") String menuName);

}
