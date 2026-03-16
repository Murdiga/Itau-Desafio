package br.com.itau.desafio.infrastructure.respository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.com.itau.desafio.infrastructure.dto.TransitionValueRequest;
import br.com.itau.desafio.infrastructure.entity.TransitionValueEntity;

@Repository
public class TransitionValueRepository {

    private Map<Long, TransitionValueEntity> transitionValueMap = new HashMap<>();

    private Long idSequence = 1L;

    public void save(TransitionValueRequest transitionValueRequest){

        TransitionValueEntity transitionValueEntity = new TransitionValueEntity();

        transitionValueEntity.setId(idSequence);
        transitionValueEntity.setValor(transitionValueRequest.getValor());
        transitionValueEntity.setDataHora(transitionValueRequest.getDataHora());

        transitionValueMap.put(idSequence, transitionValueEntity);

        idSequence++;

        System.out.println(transitionValueMap.values());

    }

    public void delete(){

        this.transitionValueMap.clear();

        System.out.println(transitionValueMap.values());

    }

    public TransitionValueEntity findById(Long id){

        return transitionValueMap.get(id);

    }

}
