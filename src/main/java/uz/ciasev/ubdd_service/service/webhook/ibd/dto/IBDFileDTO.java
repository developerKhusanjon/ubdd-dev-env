package uz.ciasev.ubdd_service.service.webhook.ibd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IBDFileDTO<T,U> {
    private T data;
    private U uri;
}
