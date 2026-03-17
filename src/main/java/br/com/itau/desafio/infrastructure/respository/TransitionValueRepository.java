package br.com.itau.desafio.infrastructure.respository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.itau.desafio.infrastructure.dto.transitionValue.TransitionValueRequest;
import br.com.itau.desafio.infrastructure.entity.TransitionValueEntity;

@Repository
public class TransitionValueRepository {

    private List<TransitionValueEntity> transitionValueList = new ArrayList<>();

    private Long idSequence = 1L;

    public void save(TransitionValueRequest transitionValueRequest){

        TransitionValueEntity transitionValueEntity = new TransitionValueEntity();

        transitionValueEntity.setId(idSequence);
        transitionValueEntity.setValor(transitionValueRequest.getValor());
        transitionValueEntity.setDataHora(transitionValueRequest.getDataHora());

        transitionValueList.add(transitionValueEntity);

        idSequence++;

    }

    public void delete(){

        this.transitionValueList.clear();

    }

    public List<TransitionValueEntity> findLastMinuteTransitions(){

        List<TransitionValueEntity> result = new ArrayList<>();

        for(int i = transitionValueList.size() - 1; i > 0; i--){

            var entity = transitionValueList.get(i);

            if (entity.getDataHora().isBefore(LocalDateTime.now().minusMinutes(1))) {
                break;
            }

            result.add(entity);

        }

        return result;

    }

}
