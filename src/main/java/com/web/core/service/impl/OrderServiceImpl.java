package com.web.core.service.impl;

import com.web.core.mapper.OrderMapper;
import com.web.core.mapper.RoomMapper;
import com.web.core.mapper.UserMapper;
import com.web.core.pojo.Order;
import com.web.core.pojo.OrderInfo;
import com.web.core.pojo.Room;
import com.web.core.pojo.User;
import com.web.core.service.OrderSerive;
import com.web.core.service.UserService;
import com.web.utils.TimeUtils;
import org.aspectj.weaver.ast.Or;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: http://www.tanwei.com
 * @Date: 2019/7/24
 * @Description: com.web.core.service.impl
 * @versio: 1.0
 */
@Service
public class OrderServiceImpl implements OrderSerive {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private RoomMapper roomMapper;
    @Override
    public String  getUserOrder(Integer userid) {

        List<OrderInfo> orders = orderMapper.getUserOrder(userid);
        int count = orderMapper.qurryUserOrderCount(userid);
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("count",count);
        map.put("data",orders);
        ObjectMapper mapper=new ObjectMapper();
        //更改时间解析方式
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleDateFormat myDateFormat=new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        mapper.getSerializationConfig().setDateFormat(myDateFormat);
        try {
            String json=mapper.writeValueAsString(map);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }


    @Override
    public String  getAllOrder() {
        List<OrderInfo> orders = orderMapper.getAllOrder();
        int count=orderMapper.qurryAllOrderCount();
        Map<String,Object> map=new HashMap<>();
        map.put("code",0);
        map.put("msg","");
        map.put("count",count);
        map.put("data",orders);

        ObjectMapper mapper=new ObjectMapper();
        //更改时间解析方式
        mapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleDateFormat myDateFormat=new SimpleDateFormat("yyyy-MM-dd-HH:mm");
        mapper.getSerializationConfig().setDateFormat(myDateFormat);
        try {
            String json=mapper.writeValueAsString(map);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }



    @Override
    public boolean MakeOrder(int userId, int roomTypeId, String time) {
        List<Room> rooms = roomMapper.querytRoomByType(roomTypeId);
        for (Room r:rooms){
            if (orderMapper.queryCountByRoomIdAndTime(r.getId(),time)==0){
                try {
                    //下订单
                    Order order=new Order();
                    order.setUserId(userId);
                    order.setRoomId(r.getId());
                    order.setTime(TimeUtils.convertStringToDate(time, "yyyy-MM-dd"));
                    orderMapper.insertOrder(order);
                    return true;
                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }

            }
        }


        return false;
    }

    @Override
    public void delOrderByOrderId(int orderId) {
        orderMapper.delOrderByOrderId(orderId);
    }

    @Override
    public Order queryOrderByOrderId(int orderId) {
        Order order = orderMapper.queryOrderByOrderId(orderId);
        if(order!=null){
            return order;
        }
        return null;
    }
}
