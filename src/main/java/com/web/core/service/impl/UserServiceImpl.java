package com.web.core.service.impl;

import com.web.core.mapper.UserMapper;
import com.web.core.pojo.User;
import com.web.core.service.UserService;
import com.web.utils.EncryptUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


/**
 * @author TCW
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;


    @Override
    public boolean register(User user) {
        //判断username是否已经被注册
        if (userMapper.selectCountByUsername(user.getUsername())>=1){
            return false;
        }

        //对密码进行加密
        String[] result = EncryptUtils.encryptPassword(user.getPassword());
        user.setPassword(result[0]);
        user.setSalt(result[1]);

        userMapper.insertUser(user);
        return true;
    }

    @Override
    public boolean login(User user, HttpSession session) {

        String salt = userMapper.selectSaltByUsername(user.getUsername());
        //计算加密后的密码
        String ciphertext=EncryptUtils.getInputPasswordCiph(user.getPassword(),salt);
        //将加密后的密码存入user对象
        user.setPassword(ciphertext);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            System.out.println("登陆成功");
            session.setAttribute("User",subject.getPrincipal());
            return  true;
        }catch (UnknownAccountException e){
            //该用户不存在
            return false;
        }catch (IncorrectCredentialsException e){
            //密码错误
            return  false;
        }

    }

    /**
     * 实现修改用户密码，将s传来的密码利用盐值加密，存入数据库中
     * @param user
     * @return
     */
    @Override
    public void changePwd(User user,String newpassword) {

        String[] result = EncryptUtils.encryptPassword(newpassword);
        String username = user.getUsername();
        String password = result[0];
        String salt = result[1];
        userMapper.changePwd(username,password,salt);
    }

    @Override
    public String changeUserName(int id, String username) {
        User user = userMapper.selectUserByUsername(username);
        if(user==null){
            userMapper.changeUserNmae(id,username);
            return "1";
        }else{
            return "0";
        }
    }
}
