package lab.space.my_house_24.service;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.CashBox;
import lab.space.my_house_24.model.cash_box.CashBoxRequest;
import lab.space.my_house_24.model.cash_box.CashBoxResponse;
import lab.space.my_house_24.model.cash_box.CashBoxSaveRequest;
import lab.space.my_house_24.model.cash_box.CashBoxUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CashBoxService {
    CashBox getCashBoxById(Long id) throws EntityNotFoundException;

    CashBoxResponse getCashBoxResponseById(Long id) throws EntityNotFoundException;

    Page<CashBoxResponse> getAllCashBoxResponse(CashBoxRequest request);

    List<EnumResponse> getDraft();

    void updateCashBoxByRequest(CashBoxUpdateRequest request) throws EntityNotFoundException;

    void saveCashBoxByRequest(CashBoxSaveRequest request) throws EntityNotFoundException;

    CashBox saveCashBox(CashBox cashBox);

    void deleteCashBoxById(Long id) throws EntityNotFoundException, IllegalArgumentException;

    CashBoxResponse getNewCashBoxResponse(Boolean type);
}
