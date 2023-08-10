package lab.space.my_house_24.model.article;

import lombok.Builder;

@Builder
public record ArticleRequest (
        Integer pageIndex
){
}
