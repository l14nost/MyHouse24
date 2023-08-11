package lab.space.my_house_24.service.impl;

import lab.space.my_house_24.repository.SeoRepository;
import lab.space.my_house_24.service.SeoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeoServiceImpl implements SeoService {
    private final SeoRepository seoRepository;
}
