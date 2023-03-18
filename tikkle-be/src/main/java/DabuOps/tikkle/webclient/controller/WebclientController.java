package DabuOps.tikkle.webclient.controller;

import DabuOps.tikkle.webclient.dto.AccountBalanceDto;
import DabuOps.tikkle.webclient.dto.AccountListDto;
import DabuOps.tikkle.webclient.dto.AccountTransactionListDto;
import DabuOps.tikkle.webclient.dto.TokenResponseDto;
import DabuOps.tikkle.webclient.service.WebclientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@Slf4j
public class WebclientController {

    WebclientService webclientService;

    @PostMapping(path = "/oauth/2.0/token", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public TokenResponseDto requestToken(
        @RequestParam("code") String code,
        @RequestParam("client_id") String client_id,
        @RequestParam("client_secret") String client_secret,
        @RequestParam("redirect_uri") String redirect_uri,
        @RequestParam("grant_type") String grant_type){}

    @GetMapping(path = "/v2.0/account/list")
    public AccountListDto requestAccountList(
        @RequestHeader("Authorization") String access_token,
        @RequestParam("user_seq_no") String user_seq_no,
        @RequestParam("include_cancel_yn") String include_cancel_yn,
        @RequestParam("sort_order") String sort_order){}

    @GetMapping(path = "/v2.0/account/balance/fin_num")
    public AccountBalanceDto requestAccountBalance(
        @RequestHeader("Authorization") String access_token,
        @RequestParam("bank_tran_id") String bank_tran_id,
        @RequestParam("fintech_use_num") String fintech_use_num,
        @RequestParam("tran_dtime") String tran_dtime){}

    @GetMapping(path = "/v2.0/account/transaction_list/fin_num")
    public AccountTransactionListDto requestAccountTransactionList(
        @RequestParam("bank_tran_id") String bank_tran_id,
        @RequestParam("fintech_use_num") String fintech_use_num,
        @RequestParam("inquiry_type") String inquiry_type,
        @RequestParam("inquiry_base") String inquiry_base,
        @RequestParam("from_date") String from_date,
        @RequestParam("to_date") String to_date,
        @RequestParam("sort_order") String sort_order,
        @RequestParam("tran_dtime") String tran_dtime){}

}
