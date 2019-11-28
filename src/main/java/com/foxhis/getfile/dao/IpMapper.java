package com.foxhis.getfile.dao;

import com.foxhis.getfile.entity.Ip;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface IpMapper {

	Ip getIp(Map paramMap);
}
