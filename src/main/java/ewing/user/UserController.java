package ewing.user;

import ewing.application.ResultMessage;
import ewing.application.paging.Page;
import ewing.entity.User;
import ewing.user.vo.FindUserParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器。
 **/
@RestController
@RequestMapping("/user")
@Api(tags = "user", description = "用户模块的方法")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/findUser")
    @ApiOperation("分页查找用户")
    @PreAuthorize("hasAnyAuthority('USER_MANAGE')")
    public ResultMessage<Page<User>> findUsers(@RequestBody FindUserParam findUserParam) {
        return new ResultMessage<>(userService.findUsers(findUserParam));
    }

    @PostMapping("/addUser")
    @ApiOperation("添加用户并返回ID")
    @PreAuthorize("hasAnyAuthority('USER_ADD')")
    public ResultMessage<Long> addUser(User user) {
        return new ResultMessage<>(userService.addUser(user));
    }

    @GetMapping("/getUser")
    @ApiOperation("根据ID获取用户")
    @PreAuthorize("denyAll()")
    public ResultMessage<User> getUser(Long userId) {
        return new ResultMessage<>(userService.getUser(userId));
    }

    @PostMapping("/updateUser")
    @ApiOperation("更新用户")
    @PreAuthorize("hasAnyAuthority('USER_UPDATE')")
    public ResultMessage updateUser(User user) {
        userService.updateUser(user);
        return new ResultMessage();
    }

    @GetMapping("/deleteUser")
    @ApiOperation("根据ID删除用户")
    @PreAuthorize("hasAnyAuthority('USER_DELETE')")
    public ResultMessage deleteUser(Long userId) {
        userService.deleteUser(userId);
        return new ResultMessage();
    }

}
