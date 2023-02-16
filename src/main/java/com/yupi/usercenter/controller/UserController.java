package com.yupi.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.usercenter.common.BaseResponse;
import com.yupi.usercenter.common.ErrorCode;
import com.yupi.usercenter.common.ResultUtils;
import com.yupi.usercenter.exception.BusinessException;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.model.domain.request.UserLoginRequest;
import com.yupi.usercenter.model.domain.request.UserRegisterRequest;
import com.yupi.usercenter.model.domain.vo.UserQueryVO;
import com.yupi.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.yupi.usercenter.contant.UserConstant.ADMIN_ROLE;
import static com.yupi.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author yupi
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        log.info("user register request:userRegisterRequest={}",userRegisterRequest);
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        log.info("register return num={}",result);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        log.info("user login request:userLoginRequest={}",userLoginRequest);
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        log.info("user login info={}",user);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<String> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        log.info("get Session user={}",userObj);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        log.info("current safetyUser info={}",safetyUser);
        return ResultUtils.success(safetyUser);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        log.info("user search param={}",username);
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        log.info("user search return list={}",list);
        return ResultUtils.success(list);
    }

    @PostMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteUser(@PathVariable long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            log.error(" 非法id={}",id);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    @PostMapping("/deleteBatch")
    public BaseResponse<Boolean> deleteBatchUser(@RequestBody List<Long> idList, HttpServletRequest request) {
        if (!isAdmin(request)) {
            log.warn("no auth session={}",request.getSession());
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (CollectionUtils.isEmpty(idList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeBatchByIds(idList);
        return ResultUtils.success(b);
    }

    @PostMapping("/pageUserList/{current}/{limit}")
    public BaseResponse<IPage<User>> findPageUserList(@PathVariable long current,
                                                      @PathVariable long limit,
                                                      @RequestBody(required = false) UserQueryVO userQueryVO) {

        log.info("user search pageUserList current={} limit={} userQueryVO={}",current,limit,userQueryVO);
        //创建page对象
        Page<User> pageParam = new Page<>(current, limit);
        //进行分页查询
        if (userQueryVO == null) { //查询全部
            IPage<User> page = userService.page(pageParam, null);
            return ResultUtils.success(page);
        } else {
            Long id = userQueryVO.getId();
            String username = userQueryVO.getUsername();
            String phone = userQueryVO.getPhone();
            String createTime = userQueryVO.getCreateTime();
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            if (id != null && id > 0) {
                queryWrapper.eq("id", id);
            }
            if (StringUtils.isNotBlank(username)) {
                queryWrapper.eq("username", username);
            }
            if (StringUtils.isNotBlank(phone)) {
                queryWrapper.eq("phone", phone);
            }
            if (StringUtils.isNotBlank(createTime)) {
                //大于等于
                queryWrapper.ge("createTime", createTime);
            }
            IPage<User> userPage = userService.page(pageParam, queryWrapper);
            return ResultUtils.success(userPage);
        }
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

}
