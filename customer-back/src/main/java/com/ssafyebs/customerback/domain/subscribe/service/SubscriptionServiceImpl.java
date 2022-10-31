package com.ssafyebs.customerback.domain.subscribe.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ssafyebs.customerback.domain.subscribe.entity.Subscription;
import com.ssafyebs.customerback.domain.subscribe.repository.SubscriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService{

	private final SubscriptionRepository subscriptionRepository;
	
	@Override
	public List<Subscription> findByMember_MemberUid(String uid) {
		return subscriptionRepository.findByMember_MemberUid(uid);
	}

	@Override
	public Subscription makeSubscription(Subscription subscription) {
		return subscriptionRepository.save(subscription);
	}

}
