package uz.ciasev.ubdd_service.service.temporary;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.entity.temporary.UbddDecison202311;
import uz.ciasev.ubdd_service.repository.temporary.UbddDecisionList202311Repository;

import java.util.List;

@Builder
@Service
@RequiredArgsConstructor
public class UbddDecison202311Service {

    private final UbddDecisionList202311Repository repository;

    public List<UbddDecison202311> getAll(){
        return repository.findAll();
    }

    public void update(UbddDecison202311 entity){
        repository.save(entity);
    }

}
