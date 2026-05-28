package com.buildcraft.inquiry.client;

import com.buildcraft.inquiry.entity.CustomerInquiry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification")
public interface NotificationClient {
    @PostMapping("/api/v1/notifications/send-enquiry-email")
    void triggerMails(@RequestBody CustomerInquiry inquiry);
}
