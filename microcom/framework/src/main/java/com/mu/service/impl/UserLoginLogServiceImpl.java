package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.UserLoginLog;
import generator.service.UserLoginLogService;
import generator.mapper.UserLoginLogMapper;
import org.springframework.stereotype.Service;

/**
* @author MUZUKI
* @description 针对表【user_login_log(用户登录日志)】的数据库操作Service实现
* @createDate 2023-11-09 10:39:26
*/
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog>
    implements UserLoginLogService{

}




