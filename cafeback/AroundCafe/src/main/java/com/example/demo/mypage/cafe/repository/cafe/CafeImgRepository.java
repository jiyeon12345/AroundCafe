package com.example.demo.mypage.cafe.repository.cafe;

import com.example.demo.mypage.cafe.entity.CafeImgTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CafeImgRepository extends JpaRepository<CafeImgTable,Long> {

    @Query("select count(ci.CafeImgNo) from CafeImgTable ci join ci.cafe c where c.cafeNo = :cafe_no")
    Optional<Integer> findByCafe_no(@Param("cafe_no") Long cafe_no);


    @Modifying(clearAutomatically = true)
    @Query("delete from CafeImgTable ci where ci.cafe in (select c from Cafe c where c.cafeNo = :cafe_no)")
    public void deleteByCafeNo(@Param("cafe_no") Long cafe_no);


    @Query("select ci from CafeImgTable ci join fetch ci.cafe c where c.cafeNo = :cafe_no")
    public List<CafeImgTable> CafeImgList(@Param("cafe_no") Long cafe_no);

}
