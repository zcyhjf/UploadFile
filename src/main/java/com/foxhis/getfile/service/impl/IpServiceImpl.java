package com.foxhis.getfile.service.impl;

import com.foxhis.getfile.dao.IpMapper;
import com.foxhis.getfile.entity.Ip;
import com.foxhis.getfile.service.IpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("IpService")
public class IpServiceImpl implements IpService {

	@Autowired
	IpMapper ipMapper;

	@Override
	public Ip getIp(String tenantid, String hotelid) {
		Map paramMap = new HashMap();
		paramMap.put("hotelid",hotelid);
		paramMap.put("tenantid",tenantid);
		return ipMapper.getIp(paramMap);
	}
}
