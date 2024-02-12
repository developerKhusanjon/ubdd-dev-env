package uz.ciasev.ubdd_service.mvd_core.expapi.user;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.ciasev.ubdd_service.mvd_core.expapi.user.dto.UserLoginTimeResponse;
import uz.ciasev.ubdd_service.utils.validator.MaxPageSize;

@RestController
@RequestMapping(path = "${mvd-ciasev.url-v0}/user", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserLoginTimeController {

    private final UserLoginTimeService service;

    @GetMapping("/by-username")
    private Page<UserLoginTimeResponse> findAllByUsername(@RequestParam(name = "username", required = false) String username,
                                                          @MaxPageSize(100) Pageable pageable
    ) {
        return service.findAllByUsername(username,pageable);
    }
}
