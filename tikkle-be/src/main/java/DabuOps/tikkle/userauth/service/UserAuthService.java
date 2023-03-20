package DabuOps.tikkle.userauth.service;

import DabuOps.tikkle.account.entity.Account;
import DabuOps.tikkle.account.repository.AccountRepository;
import DabuOps.tikkle.member.entity.Member;
import DabuOps.tikkle.member.repository.MemberRepository;
import DabuOps.tikkle.userauth.dto.AccountInfoDto;
import DabuOps.tikkle.userauth.dto.TokenResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/*
 * client 사용자 인증 요청 클릭시 핸드폰 인증을 하며 authorization_code 발급 받아 headers 에 담아서 server 로 넘겨주기 (프론트)
 * server 로 넘겨준 authorization_code 를 requestToken() 메서드로 넘긴다.(controller)
 */
@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;
    private final RestTemplate restTemplate;
    @Value("{openbanking.api.url}")
    private String openBankingApiUrl;
    @Value("{K_CLIENT_ID}")
    private String clientId;

    @Value("{K_CLIENT_SECRET}")
    private String clientSecret;

    @Value("{K_REDIRECT_URI}")
    private String redirectUri;

    public TokenResponseDto requestToken(String authorizationCode, Long memberId) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", authorizationCode);
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("redirect_uri", redirectUri);
        formData.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        ResponseEntity<TokenResponseDto> response = restTemplate.postForEntity(openBankingApiUrl + "/oauth/2.0/token", request, TokenResponseDto.class);
        TokenResponseDto tokenResponse = response.getBody();

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setAccessToken(tokenResponse.getAccessToken());
            memberRepository.save(member);
        }
        return tokenResponse;
    }

    public AccountInfoDto requestUserInfo(String accessToken, String userSeqNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setPragma(userSeqNo);
        headers.setBearerAuth(accessToken);

            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<AccountInfoDto> response = restTemplate.exchange(openBankingApiUrl + "/v2.0/user/me?user_seq_no=" + userSeqNo, HttpMethod.GET, request, AccountInfoDto.class);
            AccountInfoDto accountInfoDto = response.getBody();
            Account account = new Account(fintechUseNum);
            accountRepository.save(account);

            return accountInfoDto;
        }
    }

//    public Mono<AccountTransactionListDto> requestTransactionHistory(AccountTransactionRequestDto accountTransactionRequestDto
//    , String accessToken){
//        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
//        formData.add("bank_tran_id", accountTransactionRequestDto.getBank_tran_id());
//        formData.add("fintech_use_num", accountTransactionRequestDto.getFintech_use_num());
//        formData.add("inquiry_type", accountTransactionRequestDto.getInquiry_type());
//        formData.add("inquiry_base", accountTransactionRequestDto.getInquiry_base());
//        formData.add("from_date", accountTransactionRequestDto.getFrom_date());
//        formData.add("to_date", accountTransactionRequestDto.getTo_date());
//        formData.add("tran_dtime", accountTransactionRequestDto.getTran_dtime());
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(accessToken);
//
//        return null;
//    }

}