package com.web.core.mapper;

import com.web.core.pojo.Room;
import com.web.core.pojo.RoomType;
import com.web.core.pojo.RoomTypeInfo;
import com.web.core.pojo.TipInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoomTypeMapper {


    /**
     * 查询所有的房间类型信息
     * @return
     */
    List<RoomTypeInfo> queryRoomTypeList(int page,int limit);


    /**
     * 获取房间类型的总数
     * @return
     */
    int queryRoomTypeCount();

    /**
     * 更改room_type信息
     * @param homeTypeInfo
     */
    void updateRoomTypeInfo(@Param("homeTypeInfo") RoomTypeInfo homeTypeInfo);


    /**
     * 更新指定id的isrecommend的值
     * @param id
     * @param value
     */
    void updateRecommend(int id,String value);


    /**
     * 插入
     * @param roomType
     */
    void insertRoomType(RoomType roomType);


    /**
     * 查询指定的酒店的房型
     * @param hotelId
     * @return
     */
    List<RoomType> queryByHotelId(int hotelId);


    /**
     * 获取所有处于推荐状态的房子
     * @return
     */
    List<TipInfo> queryByRecommendStatus();

    /**
     * 分页查询所有的房间信息
     * @param begin
     * @param limit
     * @return
     */
    List<TipInfo> queryAllHote(int begin,int limit);


    /**
     * 查询房型数量
     * @return
     */
    int queryCount();


}
