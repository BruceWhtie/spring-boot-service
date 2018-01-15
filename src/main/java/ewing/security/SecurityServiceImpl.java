package ewing.security;

import ewing.application.AppAsserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 安全服务实现。
 **/
@Service
@Transactional(rollbackFor = Throwable.class)
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private SecurityDao securityDao;

    @Override
    public SecurityUser getSecurityUser(String username) {
        AppAsserts.hasText(username, "用户名不能为空！");
        return securityDao.getSecurityUser(username);
    }

    @Override
    public List<AuthorityNode> getUserAuthorities(Long userId) {
        AppAsserts.notNull(userId, "用户ID不能为空！");
        return securityDao.getUserAuthorities(userId);
    }

    @Override
    public List<PermissionNode> getUserPermissions(Long userId) {
        AppAsserts.notNull(userId, "用户ID不能为空！");
        return securityDao.getUserPermissions(userId);
    }

}
