package uz.ciasev.ubdd_service.service.document.generated;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ciasev.ubdd_service.dto.internal.ProtocolGroupByPersonDTO;
import uz.ciasev.ubdd_service.dto.internal.RequirementPrintDTO;
import uz.ciasev.ubdd_service.entity.document.RequirementGeneration;
import uz.ciasev.ubdd_service.entity.signature.SignatureEvent;
import uz.ciasev.ubdd_service.entity.user.User;
import uz.ciasev.ubdd_service.repository.document.RequirementGenerateRegisterRepository;
import uz.ciasev.ubdd_service.service.aop.signature.DigitalSignatureCheck;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequirementGenerateRegisterServiceImpl implements RequirementGenerateRegisterService {

    private final RequirementGenerateRegisterRepository repository;

    @DigitalSignatureCheck(event = SignatureEvent.REQUIREMENT_GENERATION)
    public RequirementGeneration createRegistration(User user, RequirementPrintDTO requestDTO) {

        RequirementGeneration registration = new RequirementGeneration(user,
                //mapPerson(requestDTO.getPerson()),
                requestDTO.getPerson(),
                requestDTO.getPerson().getProtocols(),
                requestDTO.getSearchParams());

        return repository.saveAndFlush(registration);
    }

    private Map<String, String> mapPerson(ProtocolGroupByPersonDTO person) {

        Field[] fields = ProtocolGroupByPersonDTO.class.getDeclaredFields();
        Method[] methods = ProtocolGroupByPersonDTO.class.getDeclaredMethods();

        return Arrays.stream(fields)
                .filter(f -> f.getType() == String.class || f.getType() == LocalDate.class)
                .collect(Collectors.toMap(
                        Field::getName,
                        f -> Arrays.stream(methods)
                                .filter(m -> m.getName().toLowerCase().equals("get" + f.getName().toLowerCase()))
                                .map(m -> {
                                    try {
                                        return m.invoke(person);
                                    } catch (Exception e) {
                                        return "";
                                    }
                                }).findFirst().orElse("").toString()
                ));
    }
}
