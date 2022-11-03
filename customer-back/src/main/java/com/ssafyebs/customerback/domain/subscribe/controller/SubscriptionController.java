package com.ssafyebs.customerback.domain.subscribe.controller;

import java.util.Calendar;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafyebs.customerback.domain.member.service.MemberService;
import com.ssafyebs.customerback.domain.subscribe.entity.FederatedSubscription;
import com.ssafyebs.customerback.domain.subscribe.entity.Subscription;
import com.ssafyebs.customerback.domain.subscribe.service.FederatedSubscriptionService;
import com.ssafyebs.customerback.domain.subscribe.service.SubscriptionService;
import com.ssafyebs.customerback.global.exception.DuplicateSubscriptionException;
import com.ssafyebs.customerback.global.exception.NoExistSubscriptionException;
import com.ssafyebs.customerback.global.response.CommonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/subscribe")
@RequiredArgsConstructor
public class SubscriptionController {
	private final SubscriptionService subscriptionService;
	private final MemberService memberService;
	private final FederatedSubscriptionService federatedSubscriptionService;
	
	@GetMapping()
	public ResponseEntity<?> getSubscriptionList(HttpServletRequest request){
		String memberUid = (String) request.getAttribute("memberuid");
		System.out.println(memberUid);
//		String memberUid = "3262732023";
		return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.createSuccess("구독정보 조회 완료.",subscriptionService.findByMember_MemberUid(memberUid)));
	}
	
	
	@GetMapping("/{business_seq}")
	public ResponseEntity<?> checkSubscription(HttpServletRequest request, @PathVariable("business_seq")Long seq){
		String memberUid = (String)request.getAttribute("memberuid");
//		String memberUid = "3262732023";
		return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.createSuccess("구독여부 조회 완료.", subscriptionService.findByMember_MemberUidAndFederatedSubscription_BusinessSeq(memberUid, seq)));
	}
	
	@PostMapping("/{pricing_seq}")
	public ResponseEntity<?> makeSubscription(HttpServletRequest request, @PathVariable("pricing_seq")Long seq){
		
		String memberUid = (String)request.getAttribute("memberuid");
//		String memberUid = "3262732023";
		Optional<FederatedSubscription> f = federatedSubscriptionService.findByPricingSeq(seq);
		if(f.isPresent()) {
			FederatedSubscription fs = f.get();
			if(subscriptionService.findByMember_MemberUidAndFederatedSubscription_BusinessSeq(memberUid, fs.getBusinessSeq())) {
				throw new DuplicateSubscriptionException("이미 구독중입니다.");
			}
			Subscription subscription = new Subscription();
			subscription.setMember(memberService.findByMemberUid(memberUid).get());
			subscription.setSubscriptionLeft(fs.getPricingNumber());
			//현재날짜 기준으로 해서 계산해줘야한다. 아래는 그냥 테스트 코드.
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, fs.getPricingMonth().intValue());
			subscription.setSubscriptionExpiration(cal);
			subscription.setFederatedSubscription(fs);
			
			subscriptionService.makeSubscription(subscription);
			
			return ResponseEntity.status(HttpStatus.OK).body(CommonResponse.createSuccess("구독완료.",null));
		}
		else
			throw new NoExistSubscriptionException("상품이 존재하지 않습니다.");
	}
}
