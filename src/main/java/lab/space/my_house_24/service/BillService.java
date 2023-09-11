package lab.space.my_house_24.service;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.Bill;
import lab.space.my_house_24.model.bill.*;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface BillService {
    Bill getBillById(Long id) throws EntityNotFoundException;

    BillResponse getBillResponseById(Long id) throws EntityNotFoundException;

    Page<BillResponse> getAllBillResponse(BillRequest request);

    List<EnumResponse> getDraft();

    void updateBillByRequest(BillUpdateRequest request) throws EntityNotFoundException;

    void saveBillByRequest(BillSaveRequest request) throws EntityNotFoundException;

    Bill saveBill(Bill bill);

    void deleteBillByRequest(List<BillDeleteRequest> bills) throws EntityNotFoundException, IllegalArgumentException;

    void deleteBillById(Long id) throws EntityNotFoundException, IllegalArgumentException;

    List<EnumResponse> getAllBillStatus();

    BillResponse getNewBillResponse();

    BigDecimal sumOfAllBills();

    List<BigDecimal> sumOffAllBillsByMonths();

    List<BigDecimal> sumOffAllPaidBillsByMonths();
}
