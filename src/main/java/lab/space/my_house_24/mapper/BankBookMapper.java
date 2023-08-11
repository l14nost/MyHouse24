package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.model.bankBook.BankBookResponseForTable;
import lab.space.my_house_24.model.rate.RateResponseForTable;


public class BankBookMapper {
    public  static BankBookResponseForTable entityToDtoForTable(BankBook bankBook){
        return BankBookResponseForTable.builder().number(String.format("%09d",bankBook.getId())).id(bankBook.getId()).build();
    }
}
