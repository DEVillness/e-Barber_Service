package com.ssafyebs.customerback.domain.member.service;


import com.ssafyebs.customerback.domain.member.dto.*;
import com.ssafyebs.customerback.domain.member.entity.Member;
import org.springframework.http.HttpHeaders;

import java.util.Optional;



public interface MemberService {
	public Optional<Member> findByMemberUid(String memberUid);
	
	public void quitUser(String memberUid);
	
	public Member save(Member member);

	HttpHeaders createTokenHeader(String accessToken , String refreshToken);
	MemberResponseDto loginOAuthGoogle(GoogleLoginRequestDto googleLoginRequestDto);

	MemberResponseDto getMemberInfo(String memberUid);
	void updateRefreshToken(String memberUid, String refreshToken);
	String getMemberUid(String idToken);
	MemberResponseDto signUpOauthGoogle(MemberGoogleRequestDto memberGoogleRequestDto, String refreshToken);

	MemberResponseDto updateMemberInfo(String memberUid, MemberUpdateInfoRequestDto memberUpdateRequestDto);
}
