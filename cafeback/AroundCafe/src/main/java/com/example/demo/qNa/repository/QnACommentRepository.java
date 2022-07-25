package com.example.demo.qNa.repository;

import com.example.demo.qNa.dto.QnACommentDto;
import com.example.demo.qNa.entity.QnA;
import com.example.demo.qNa.entity.QnAComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface QnACommentRepository extends JpaRepository<QnAComment, Long> {

//    @Query(value = "select * from qna_comment where qna_no = :qnaNo order by qna_comment_no asc",nativeQuery = true)
//    public List<QnAComment> findByQnA(@Param("qnaNo")Integer qnaNo);

    @Query("select qc from QnAComment qc join fetch qc.qnA q where q.qna_no = :qnaNo order by qc.qna_comment_no asc")
    public List<QnAComment> findByQnA(@Param("qnaNo")Integer qnaNo);

    @Query("select qm from QnAComment qm where qm.qna_comment_no in " +
            "(select max(q.qna_comment_no) from QnAComment q where q.qnA = :qna)")
    Optional <QnAComment> findByRecentComment(@Param("qna") QnA qna);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from QnAComment qc where qc.qnA in(select q from QnA q where q.qna_no =:qnaNo)")
    public void deleteAllByQnA(@Param("qnaNo") Long qnaNo);
}
