package ewing.security;

import ewing.application.RequestUtils;
import ewing.application.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 安全控制器。
 **/
@RestController
@RequestMapping("/security")
@Api(tags = "security", description = "安全控制器")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @GetMapping("/getCurrentUser")
    @ApiOperation("获取当前登陆的用户信息")
    public ResultMessage<SecurityUser> getCurrentUser() {
        return new ResultMessage<>(RequestUtils.getCurrentUser());
    }

}
