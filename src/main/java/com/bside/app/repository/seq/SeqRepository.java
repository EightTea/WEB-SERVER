package com.bside.app.repository.seq;

import com.bside.app.domain.Seq;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class SeqRepository {

    @PersistenceContext
    private EntityManager em;

    public Long findSeq(String seqName){
        Seq seq = em.createQuery("select s from Seq s where s.seqName =:seqName", Seq.class)
                .setParameter("seqName",seqName)
                .getSingleResult();

        seq.setSeqValue( seq.getSeqValue() + 1 );
        em.persist(seq);

        return seq.getSeqValue();
    }
}
