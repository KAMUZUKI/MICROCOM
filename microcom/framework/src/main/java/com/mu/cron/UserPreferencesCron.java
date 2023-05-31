package com.mu.cron;

import com.mu.service.impl.RecommendToRedisServiceImpl;
import com.mu.service.impl.UserServiceImpl;
import com.mu.utils.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author MUZUKI
 * @Classname UserPreferencesCron
 * @Description TODO
 * @Date 2023/5/25 11:26
 */

@Slf4j
@Component
public class UserPreferencesCron {

    @Value("${recommend.user.size}")
    private Integer size = 10;
    /**
     * 每天10分钟执行
     */
    @Scheduled(cron="0 0/10 * * * ?")   //每10分钟执行一次
    public void run() throws InterruptedException {
        UserServiceImpl userService = (UserServiceImpl) ApplicationContextUtil.getBean("userServiceImpl");
        log.info("==================================");
        log.info("=========开始同步用户偏好数据=========");
        // 获取所有用户的uid
        List<Long> uidList = userService.getUidList();
        RecommendToRedisServiceImpl recommendToRedisService = (RecommendToRedisServiceImpl) ApplicationContextUtil.getBean("recommendToRedisServiceImpl");
        log.info(Thread.currentThread().getName() + "=========开始同步用户偏好数据=========");
        // 完成数据同步
        uidList.forEach(
                uid -> recommendToRedisService.freshRedisRecommend(Math.toIntExact(uid), size)
        );
        log.info(Thread.currentThread().getName() + "=========同步用户偏好数据完成=========");
        // 设置线程数量
//        int numThreads = 4;
//        List<List<Long>> groupList = new ArrayList<>();
//        int groupSize = uidList.size() / numThreads;
//        int remainder = uidList.size() % numThreads;
//        int index = 0;
//        for (int i = 0; i < numThreads; i++) {
//            int size = groupSize;
//            if (remainder > 0) {
//                size++;
//                remainder--;
//            }
//            List<Long> group = new ArrayList<>(uidList.subList(index, index + size));
//            groupList.add(group);
//            index += size;
//        }
//        // 创建线程池
//        ExecutorService executor = ExecutorBuilder.create()
//                .setCorePoolSize(numThreads)
//                .setMaxPoolSize(10)
//                .setWorkQueue(new LinkedBlockingQueue<>(numThreads))
//                .build();
//
//        // 提交任务
//        for (List<Long> group : groupList) {
//            executor.submit(new DataSyncTask(group));
//        }
//        // 等待所有任务执行完成
//        executor.shutdown();
//        if (executor.awaitTermination(10, TimeUnit.MINUTES)){
//            log.info("=========同步用户偏好数据完成=========");
//            log.info("==================================");
//        };
    }

    private class DataSyncTask implements Runnable {
        private final List<Long> dataList;

        public DataSyncTask(List<Long> dataList) {
            this.dataList = dataList;
        }

        @Override
        public void run() {
            RecommendToRedisServiceImpl recommendToRedisService = (RecommendToRedisServiceImpl) ApplicationContextUtil.getBean("recommendToRedisServiceImpl");
            log.info(Thread.currentThread().getName() + "=========开始同步用户偏好数据=========");
            // 完成数据同步
            dataList.forEach(
                    uid -> recommendToRedisService.freshRedisRecommend(Math.toIntExact(uid), size)
            );
            log.info(Thread.currentThread().getName() + "=========同步用户偏好数据完成=========");
        }
    }
}
