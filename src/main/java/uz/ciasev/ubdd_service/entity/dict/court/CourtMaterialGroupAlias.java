package uz.ciasev.ubdd_service.entity.dict.court;

public enum CourtMaterialGroupAlias {
    // Вынесение нового решения
    APPEAL_ON_DECISION,
    // Внести исполнение лишения прав
    RETURN_LICENSE,
    // Не требует специальной обработки
    OTHER,
    // Игнорируемый тип материала. Приходит к нам изза ошибки концелярии суда. К нам не относиться, поэтому обрабатываться не должен.
    IGNORED
}
