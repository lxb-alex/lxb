package com.lxb.sys.security;

import com.lxb.common.utils.AESUtil;
import com.lxb.common.utils.ShiroUtils;
import com.lxb.common.utils.StringUtils;
import com.lxb.sys.entity.SysMenuEntity;
import com.lxb.sys.entity.SysRoleEntity;
import com.lxb.sys.entity.SysUserEntity;
import com.lxb.sys.service.SysMenuService;
import com.lxb.sys.service.SysRoleService;
import com.lxb.sys.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description
 * @author Liaoxb
 * @date 2017/12/21 0021 17:07:07
 */
@Service
public class UserRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 登录成功，判断用户具有的权限（如：查询具有的目录）
     * 此方法调用  hasRole,hasPermission的时候才会进行回调.
     *
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，
     * 调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等
     *
     * @param principalCollection principal
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("======================执行Shiro权限认证======================");
        //获取当前登录输入的用户名或用户实体类（认证时存入SimpleAuthenticationInfo中的对象），等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        SysUserEntity user = (SysUserEntity) super.getAvailablePrincipal(principalCollection);
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        if(user == null){
            return null;
        }
        //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        // 查询用户具有的目录
        List<SysMenuEntity> menuList = sysMenuService.selectMenuByUserId(user.getId());
/*        //用户的角色集合
        info.setRoles(rolesName);*/
        //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
        for (SysMenuEntity menu : menuList) {
            if (StringUtils.isNotEmpty(menu.getPermission())){
                String[] permissions = menu.getPermission().split(",");
                info.addStringPermissions(Arrays.asList(permissions));
            }
        }
        return info;
    }

    /**
     * 登录时认证
     *
     * @param authcToken token
     * @return SimpleAuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        AccountPasswordToken token = (AccountPasswordToken) authcToken;
        Session session = ShiroUtils.getSession();
        String code = (String)session.getAttribute(ShiroUtils.CACHE_CAPTCHA_CODE);
        if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
            log.error("验证码错误, 请重试.");
            throw new AuthenticationException("验证码错误, 请重试.");
        }
        SysUserEntity user = sysUserService.getSysUserEntity(token.getUsername());
        if (user != null){
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            byte[] salt = AESUtil.AESDecode(user.getPassword()).getBytes();
            /**
             * SimpleAuthenticationInfo 密码需要和authcToken中的密码保持一致（同为明文或同为密文）
             * principal 用户名/或则登录实体；（如果为实体：则登录时不需要再次去查询用户，直接读取保存session就行）
             * hashedCredentials hash密码；
             * credentialsSalt 盐值；
             * realmName 当前realm名称；
             */
            return new SimpleAuthenticationInfo(user, AESUtil.AESDecode(user.getPassword()), ByteSource.Util.bytes(salt), getName());
        }else {
            log.error("账号不存在");
            throw new UnknownAccountException("账号不存在");
        }
    }

}