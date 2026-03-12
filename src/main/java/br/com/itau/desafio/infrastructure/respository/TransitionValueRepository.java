package br.com.itau.desafio.infrastructure.respository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import br.com.itau.desafio.infrastructure.dto.TransitionValueRequest;
import br.com.itau.desafio.infrastructure.entity.TransitionValueEntity;

@Repository
public class TransitionValueRepository {

    private Map<Long, TransitionValueEntity> transitionValueMap = new HashMap<>();

    private Long idSequence = 1L;

    private TransitionValueEntity transitionValueEntity = new TransitionValueEntity();

    public void save(TransitionValueRequest transitionValueRequest){

        transitionValueEntity.setId(idSequence);
        transitionValueEntity.setValor(transitionValueRequest.getValor());
        transitionValueEntity.setDataHora(transitionValueRequest.getDataHora());

        transitionValueMap.put(idSequence, transitionValueEntity);

        idSequence++;

    }

    public TransitionValueEntity findById(Long id){

        return transitionValueMap.get(id);

    }

}
