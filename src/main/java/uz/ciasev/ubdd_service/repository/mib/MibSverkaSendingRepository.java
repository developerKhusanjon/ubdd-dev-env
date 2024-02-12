package uz.ciasev.ubdd_service.repository.mib;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.ciasev.ubdd_service.entity.mib.MibSverkaSending;

import java.util.List;

public interface MibSverkaSendingRepository extends JpaRepository<MibSverkaSending, Long> {

    @Query(value = "SELECT s " +
            "FROM MibSverkaSending s " +
            "WHERE s.pass = FALSE ")
    Slice<MibSverkaSending> findUnpassed(Pageable pageable);

    @Modifying
    @Query(value = "UPDATE MibSverkaSending s SET s.pass = FALSE WHERE s.hasError = TRUE AND s.isExclude IS NOT TRUE")
    void repassApiError();


    @Query(nativeQuery = true, value = "select i.invoice_serial " +
            "from core_v0.mib_kv m " +
            "join core_v0.invoice i on m.is_find = false and i.invoice_serial = m.doc_number")
    List<String> getNewGaiInvoiceSerialForSverka();

    @Modifying
    @Query(nativeQuery = true, value = "update core_v0.mib_kv set " +
            " add_with_new_invoice = true, " +
            " is_find = true " +
            "where doc_number in :invoiceSerials")
    void makeMibKVFinded(List<String> invoiceSerials);

    @Modifying
    @Query(nativeQuery = true, value = "update core_v0.mib_kv m set " +
            " penalty_status = pp.adm_status_id " +
            "from core_v0.invoice i " +
            " join core_v0.penalty_punishment p on p.id = i.penalty_punishment_id " +
            " join core_v0.punishment pp on pp.id =p.punishment_id " +
            "where i.invoice_serial = m.doc_number and m.penalty_status is null ")
    void setPenaltyStatus();

    @Modifying
    @Query(nativeQuery = true, value = "insert into core_v0.mib_sverka_sending (created_time, decision_id, card_id, control_serial, control_number, resolution_date, source_type) " +
            "select now(), p.decision_id, c.id as card_id, substring(invoice_serial, 1, 2), substring(invoice_serial, 3), p.created_time, 'new invoice cron' " +
            "from core_v0.invoice i " +
            " join core_v0.penalty_punishment pp on pp.id = i.penalty_punishment_id " +
            " join core_v0.punishment p on p.id = pp.punishment_id " +
            " left join core_v0.mib_execution_card c on p.decision_id = c.decision_id " +
            "where p.adm_status_id != 12 and i.invoice_serial in :invoiceSerials")
    void addDecisionInSverkaOrderByInvoiceSerial(List<String> invoiceSerials);

    @Modifying
    @Query(nativeQuery = true, value = "insert into core_v0.mib_execution_card( " +
            " created_time, " +
            " edited_time, " +
            " decision_id, " +
            " mib_owner_type_id, " +
            " out_number, " +
            " out_date, " +
            " region_id, " +
            " district_id, " +
            " notification_sent_date, " +
            " notification_receive_date, " +
            " notification_number, " +
            " notification_text, " +
            " notification_type_id)  " +
            "select  " +
            "now(), now(), s.decision_id, 1, " +
            "'SVERCA-MIB/'||s.id,  '2022-06-22', coalesce(a.region_id, null, 1), coalesce(a.district_id, null, 1), " +
            "'2022-06-22', '2022-06-22', v.mobile, 'Не требуеться', 3 " +
            "from core_v0.mib_sverka_sending s " +
            "join core_v0.decision d on d.id = s.decision_id " +
            "join core_v0.violator v on v.id = d.violator_id " +
            "join core_v0.address a on a.id = v.actual_address_id " +
            "where s.card_id is null")
    void createMibExecutionCardWhereNotExist();

    @Modifying
    @Query(nativeQuery = true, value = "update core_v0.mib_sverka_sending s set card_id = c.id " +
            "from core_v0.mib_execution_card c " +
            "where s.card_id is null and s.decision_id = c.decision_id ")
    void setCardIdWhereEmpty();

    @Modifying
    @Query(nativeQuery = true, value = "update core_v0.mib_sverka_sending s set enveloped_id = a.envelope_id " +
            "from (" +
            "   select c.doc_number, max(c.envelope_id) as envelope_id " +
            "   from core_v0.sverka_mib_enveloped_id_not_found20220620_csv c " +
            "   group by c.doc_number " +
            ") as a " +
            "where a.doc_number=(s.control_serial + s.control_number) and s.enveloped_id is null")
    void setEnvelopeIdFromMibFileWhereEmpty();

    @Modifying
    @Query(nativeQuery = true, value = "update core_v0.mib_sverka_sending s set pass = false where pass is null")
    void includeNewRowInOrder();

    @Modifying
    @Query(nativeQuery = true, value = "update core_v0.mib_sverka_sending s set pass = false " +
            "from core_v0.mib_card_movement m " +
            " join core_v0.d_mib_case_status cs on cs.id = m.mib_case_status_id " +
            "where m.is_active = true " +
            " and m.card_id = s.card_id " +
            " and pass = true " +
            " and cs.type_id in (1, 4)")
    void repassSverkaForInMibStatus();

    Page<MibSverkaSending> findByDecisionId(Long decisionId, Pageable pageable);
}
