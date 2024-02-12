package uz.ciasev.ubdd_service.mvd_core.api.court.service.mapper;

public interface CourtRequestMapper<I, O> {

    O map(I requestDTO);
}
