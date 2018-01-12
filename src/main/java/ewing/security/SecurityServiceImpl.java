package ewing.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 安全服务实现。
 **/
@Service
@Transactional(rollbackFor = Throwable.class)
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SecurityDao securityDao;

}
