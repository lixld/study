package com.lixl.study.请求合并;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 只有高并发的时候，才需要请求合并
 * 因为他的缺点确定也很明显，就是在请求发起之前有延迟了
 * 典型的：时间换空间--》节省了大量服务器--》10ms延迟
 * 空间换时间 ==》缓存--》内存空间，服务器资源--200ms
 *
 */
public class pipleLineRequest {
    class RequestClass {
        String moviceCode;
        CompletableFuture<Map<String,Object>> future;
    }
    //把上面的请求放入这个队列，通过定时线程池去队列中取值，并执行业务逻辑
    LinkedBlockingDeque<RequestClass> queue = new LinkedBlockingDeque<>();

    //准备定时任务
    @PostConstruct
    public void  init(){
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(()->{
            //从队列中拿数据，组装批量查询
            int size = queue.size();
            if (size == 0) {
                return;
            }
            List<RequestClass> requests = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                RequestClass poll = queue.poll();
                requests.add(poll);
            }
            System.out.format("从队列里取出，合并了%s个请求",size);

            List<String> commodityCodes = new ArrayList<>();

            //准备请求参数
            for (RequestClass request : requests) {
                commodityCodes.add(request.moviceCode);
            }

            List<Map<String, Object>> result = null;//xxxService.批量查询接口(commodityCodes);

            //分发结果
            //[
            // {code:500,phone:huawie},
            // {code:600,phone:vivo},
            // ]
            //把结果集进行处理 --map.get(code)==>response
            Map<String,Map<String, Object>> responseMap = new HashMap<>();
            for (Map<String, Object> resultMap : result) {
                String code = (String) resultMap.get("code");
                responseMap.put(code,resultMap);
            }

            //将结果分发给请求（也就是和结果和请求做匹配）
            //匹配到的结果如何返回给请求线程。这里就涉及都了线程通讯
            for (RequestClass request : requests) {
                Map<String, Object> res = responseMap.get(request.moviceCode);
                request.future.complete(res);
            }


        },0,10, TimeUnit.MILLISECONDS);

    }



    //这里就是封装后的service方法
    public Map<String,Object> queryCommodity(String movieCode) throws ExecutionException, InterruptedException {
        //每一个请求都有自己的一个Future用来接收后台服务接口返回的数据
        CompletableFuture<Map<String,Object>> future = new CompletableFuture<>();

        RequestClass request = new RequestClass();
        request.moviceCode = movieCode;
        request.future = future;
        queue.add(request);
        try {
            //200毫秒后超时
            return future.get(200,TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            e.printStackTrace();
            //TODO 出现异常，执行下面的方法，这就是所谓降级。。。
            return  null;
        }
    }



}
