package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.ApiInterface;
import generator.service.ApiInterfaceService;
import generator.mapper.ApiInterfaceMapper;
import org.springframework.stereotype.Service;

/**
* @author MUZUKI
* @description 针对表【api_interface】的数据库操作Service实现
* @createDate 2023-10-20 16:59:34
*/
@Service
public class ApiInterfaceServiceImpl extends ServiceImpl<ApiInterfaceMapper, ApiInterface>
    implements ApiInterfaceService{

}




