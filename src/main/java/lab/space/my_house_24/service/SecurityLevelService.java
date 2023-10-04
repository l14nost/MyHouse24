package lab.space.my_house_24.service;

import lab.space.my_house_24.entity.SecurityLevel;
import lab.space.my_house_24.enums.Page;

import java.util.List;

public interface SecurityLevelService {

    SecurityLevel getSecurityLevelByPage(Page page);

    List<SecurityLevel> getAllSecurityLevel();

    void saveSecurityLevel(SecurityLevel securityLevel);

}
