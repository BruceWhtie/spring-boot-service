package ewing.security;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
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

}
