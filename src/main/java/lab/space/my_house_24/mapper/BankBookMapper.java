package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.entity.Rate;
import lab.space.my_house_24.model.bankBook.BankBookResponseForTable;
import lab.space.my_house_24.model.rate.RateResponseForTable;


public class BankBookMapper {
    public  static BankBookResponseForTable entityToDtoForTable(BankBook bankBook){
        if (bankBook!=null) {
            return BankBookResponseForTable.builder().number(bankBook.getNumber()).id(bankBook.getId()).build();
        }
        else return BankBookResponseForTable.builder().build();
    }
}
