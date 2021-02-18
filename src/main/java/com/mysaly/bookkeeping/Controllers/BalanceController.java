package com.mysaly.bookkeeping.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysaly.bookkeeping.Model.BalanceChange;
import com.mysaly.bookkeeping.Services.BalanceService;
import com.mysaly.bookkeeping.Model.Balance;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BalanceController {
    @Autowired
    private BalanceService balanceService;

    @PostMapping("/balances/{name}")
    public void createNewBalance(@PathVariable String name) {
        balanceService.createBalance(name);
    }

    @PostMapping("/transactions/{credit}/{debit}/{value}")
    public void transactions(@PathVariable String credit, @PathVariable String debit, @PathVariable BigDecimal value) {
        balanceService.creditDebit(credit, debit, value);
    }

    @GetMapping("/balances")
    public ArrayList<Balance> getBalances() {
        return balanceService.getBalances();
    }

    @GetMapping("/balances/{currency}")
    public ArrayList<Balance> getBalancesCurrency(@PathVariable String currency) throws InterruptedException, IOException, URISyntaxException, JSONException {
        return balanceService.getBalancesCurrency(currency);
    }

    @GetMapping("/balances/{startDate}/{endDate}")
    public List<BalanceChange> getBalanceByDate(@PathVariable Date startDate, @PathVariable Date endDate) {
       return balanceService.getBalanceByDate(startDate, endDate);
    }
}
