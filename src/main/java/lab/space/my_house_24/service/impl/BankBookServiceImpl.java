package lab.space.my_house_24.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.mapper.BankBookMapper;
import lab.space.my_house_24.model.bankBook.BankBookResponseForTable;
import lab.space.my_house_24.repository.BankBookRepository;
import lab.space.my_house_24.service.BankBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class BankBookServiceImpl implements BankBookService {

    private final BankBookRepository bankBookRepository;

    @Override
    public BankBook findById(Long id) {
        return bankBookRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Bank book by id "+id+" is not found"));
    }

    @Override
    public List<BankBookResponseForTable> bankBookListForTable() {
        return bankBookRepository.findAll().stream().map(BankBookMapper::entityToDtoForTable).toList();
    }

    @Override
    public void setBankBookApartmentIdNull(Long id) {
        BankBook bankBook = findById(id);
        bankBook.setApartment(null);
        bankBookRepository.save(bankBook);
    }
}
