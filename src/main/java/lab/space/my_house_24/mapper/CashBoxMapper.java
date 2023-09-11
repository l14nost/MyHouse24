package lab.space.my_house_24.mapper;

import lab.space.my_house_24.entity.Article;
import lab.space.my_house_24.entity.BankBook;
import lab.space.my_house_24.entity.CashBox;
import lab.space.my_house_24.entity.Staff;
import lab.space.my_house_24.enums.ArticleType;
import lab.space.my_house_24.model.cash_box.CashBoxResponse;
import lab.space.my_house_24.model.cash_box.CashBoxSaveRequest;
import lab.space.my_house_24.model.cash_box.CashBoxUpdateRequest;
import lab.space.my_house_24.model.enums_response.EnumResponse;

import java.time.LocalDate;
import java.time.ZoneId;

import static java.util.Objects.nonNull;

public interface CashBoxMapper {

    static CashBoxResponse toCashBoxResponse(String number, LocalDate date) {
        return CashBoxResponse.builder()
                .number(number)
                .createAt(date)
                .build();
    }

    static CashBoxResponse toCashBoxResponse(CashBox cashBox, EnumResponse draft) {
        return CashBoxResponse.builder()
                .id(cashBox.getId())
                .number(cashBox.getNumber())
                .createAt(cashBox.getCreateAt().atZone(ZoneId.systemDefault()).toLocalDate())
                .draft(draft)
                .price(cashBox.getPrice())
                .comment(cashBox.getComment())
                .type(cashBox.getType())
                .bankBook(nonNull(cashBox.getBankBook()) ? BankBookMapper.toBankBookResponse(cashBox.getBankBook()) : null)
                .staff(StaffMapper.toStaffResponse(cashBox.getStaff()))
                .article(ArticleMapper.toArticleResponse(cashBox.getArticles()))
                .build();
    }


    static CashBox toCashBox(CashBoxSaveRequest request, BankBook bankBook, Staff staff, Article article) {
        return CashBox.builder()
                .number(request.number())
                .createAt(request.createAt().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
                .draft(request.draft())
                .type(
                        article.getType().equals(ArticleType.INCOME)
                )
                .price(request.price())
                .comment(request.comment())
                .bankBook(bankBook)
                .staff(staff)
                .articles(article)
                .isActive(request.draft())
                .build();
    }

    static CashBox toCashBox(CashBox cashBox, CashBoxUpdateRequest request, BankBook bankBook, Staff staff, Article article) {
        cashBox
                .setNumber(request.number())
                .setCreateAt(request.createAt().atStartOfDay(ZoneId.systemDefault()).toLocalDateTime())
                .setDraft(request.draft())
                .setPrice(request.price())
                .setComment(request.comment())
                .setBankBook(bankBook)
                .setStaff(staff)
                .setArticles(article);
        if (!cashBox.getIsActive() && request.draft()){
            cashBox.setIsActive(true);
        }
        return cashBox;
    }
}
