package com.cuihui.zuul.zuuldemo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ParamFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(ParamFilter.class);

    private static final int FILTER_ORDER = 1;

    /*
    * 过滤器类型pre
    * */
    @Override
    public String filterType() {
        // 在请求路由之前调用,可以用在登录认证
        return FilterConstants.PRE_TYPE;
    }

    /*
    * 过滤器顺序
    * */
    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    /*
    * 始终对请求过滤
    * */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /*
    * 实现过滤
    * */
    @Override
    public Object run() {
        logger.info(".....过滤器发挥作用.....");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String password = request.getParameter("password");
        logger.info("执行password .....password=" + password);

        //如果用户名和密码都正确，则继续执行下一个filter
        if("since2012".equals(password) ){
            ctx.setSendZuulResponse(true);//会进行路由，也就是会调用api服务提供者
            ctx.setResponseStatusCode(200);
            ctx.set("isOK",true);//可以把一些值放到ctx中，便于后面的filter获取使用
        }else{
            ctx.setSendZuulResponse(false);//不需要进行路由，也就是不会调用api服务提供者
            ctx.setResponseStatusCode(401);
            ctx.set("isOK",false);//可以把一些值放到ctx中，便于后面的filter获取使用
            //返回内容给客户端
            ctx.setResponseBody("{\"result\":\"password not correct!\"}");// 返回错误内容
        }
        return null;
    }
}
