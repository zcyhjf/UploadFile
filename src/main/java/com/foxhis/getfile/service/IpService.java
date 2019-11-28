package com.foxhis.getfile.service;

import com.foxhis.getfile.entity.Ip;

public interface IpService {

	Ip getIp(String tenantid, String hotelid);
}
