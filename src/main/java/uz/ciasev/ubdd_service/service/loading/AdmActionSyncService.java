package uz.ciasev.ubdd_service.service.loading;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ciasev.ubdd_service.entity.action.Action;
import uz.ciasev.ubdd_service.entity.action.ActionAlias;
import uz.ciasev.ubdd_service.repository.action.ActionRepository;
import uz.ciasev.ubdd_service.utils.types.MultiLanguage;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AdmActionSyncService {

    private final ActionRepository actionRepository;

    public void sync() {
        HashSet<ActionAlias> declaredAliases = new HashSet<>(List.of(ActionAlias.values()));
        declaredAliases.remove(ActionAlias.UNKNOWN);

        //  удалить из базы лишние
//        actionRepository.deleteAllByAliasNotIn(declaredAliases.stream().map(String::valueOf).collect(Collectors.toSet()));

        //  добавить в базы не доастающие
        Set<ActionAlias> existValues = actionRepository.findAll().stream().map(Action::getAlias).collect(Collectors.toSet());
        declaredAliases.stream()
                .filter(alias -> !existValues.contains(alias))
                .map(alias -> {
                    String text = String.valueOf(alias).toLowerCase(Locale.ROOT).replace("_", " ");

                    return Action.builder()
                            .alias(alias)
                            .name(new MultiLanguage(text, text, text))
                            .build();
                })
                .forEach(actionRepository::save);
    }

//    private void syncStatus() {
//        Set<AdmStatusAlias> declaredAliases = new HashSet<>(List.of(AdmStatusAlias.values()));
//
//        //  удалить из базы лишние
//        admStatusRepository.deleteAllByAliasNotIn(declaredAliases.stream().map(String::valueOf).collect(Collectors.toSet()));
//
//        //  добавить в базы не доастающие
//        Set<AdmStatusAlias> existValues = admStatusRepository.findAll().stream().map(AdmStatus::getAlias).collect(Collectors.toSet());
//        declaredAliases
//                .stream()
//                .filter(alias -> !existValues.contains(alias))
//                .map(alias -> {
//                    String text = String.valueOf(alias).toLowerCase(Locale.ROOT).replace("_", " ");
//
//                    return AdmStatus.builder()
//                            .alias(alias)
//                            .name(new MultiLanguage(text, text, text))
//                            .build();
//                })
//                .forEach(admStatusRepository::create);
//    }
}
