package com.mysaly.bookkeeping.Services;

import com.mysaly.bookkeeping.Enum.Payment;
import com.mysaly.bookkeeping.Model.Balance;
import com.mysaly.bookkeeping.Model.BalanceChange;
import com.mysaly.bookkeeping.Model.Record;
import com.mysaly.bookkeeping.Repositories.BalanceRepository;
import com.mysaly.bookkeeping.Repositories.RecordRepository;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@Service
public class BalanceService {
    @Autowired
    private BalanceRepository balanceRepository;
    @Autowired
    private RecordRepository recordRepository;
    Logger log = Logger.getLogger(BalanceService.class.getName());

    public void createBalance(String name) {
        if (!balanceRepository.existsById(name)) {
            Balance newBalance = new Balance();
            newBalance.setName(name);
            newBalance.setValue(new BigDecimal("0"));

            log.info("Creating new balance");
            balanceRepository.save(newBalance);
        } else {
            log.info("Balance already exists");
        }
    }

    public ArrayList<Balance> getBalances() {
        log.info("Getting all balances");

        Iterable<Balance> iterableBalances = balanceRepository.findAll();
        ArrayList<Balance> balances = new ArrayList<>();
        iterableBalances.forEach(balances::add);
        return balances;
    }

    public void creditDebit(String creditor, String debited, BigDecimal value) {
        if (balanceRepository.existsById(creditor) && balanceRepository.existsById(debited)) {
            log.info("Adjusting values for the credit holder and debit holder");

            adjustBalance(debited, value, Payment.ADD);
            adjustBalance(creditor, value, Payment.SUBSTRACT);

        } else {
            log.info("One of the holders does not exist");
        }
    }

    public void adjustBalance(String name, BigDecimal value, Payment payment) {
        if (balanceRepository.existsById(name)) {
            log.info("Adjusting balance");

            Balance balance = balanceRepository.findById(name).get();
            if (payment.name() == Payment.ADD.name()) {
                balance.setValue(balance.getValue().add(value));
            } else {
                balance.setValue(balance.getValue().subtract(value));
            }
            balanceRepository.save(balance);
        } else {
            log.info("No such balance exists");
        }
    }

    public ArrayList<Balance> getBalancesCurrency(String currency) throws URISyntaxException, IOException, InterruptedException, JSONException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = new URIBuilder("https://api.exchangeratesapi.io/latest?")
                .addParameter("base", "SEK")
                .addParameter("symbols", currency)
                .build();

        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject object = new JSONObject(response.body());
        object = (JSONObject) object.get("rates");

        BigDecimal rate = new BigDecimal(object.get(currency).toString());

        Iterable<Balance> iterableBalances = balanceRepository.findAll();
        ArrayList<Balance> balances = new ArrayList<>();
        iterableBalances.forEach(x -> x.setValue(x.getValue().multiply(rate)));
        iterableBalances.forEach(balances::add);

        return balances;
    }

    public List<BalanceChange> getBalanceByDate(Date startDate, Date endDate) {
        List<BalanceChange> balanceChangeList = new ArrayList<>();
        List<String> names = recordRepository.getDistinctNames();

        for (String name: names) {
            if (recordRepository.existsByDateAndName(startDate, name) && recordRepository.existsByDateAndName(endDate, name)) {
                Record recordStart = recordRepository.findByDateAndName(startDate, name).stream().max(Comparator.comparing(Record::getId)).get();
                Record recordEnd = recordRepository.findByDateAndName(endDate, name).stream().max(Comparator.comparing(Record::getId)).get();

                BalanceChange balanceChange = new BalanceChange();
                balanceChange.setStartDate(startDate);
                balanceChange.setEndDate(endDate);
                balanceChange.setName(name);
                balanceChange.setDiff(recordEnd.getValue().subtract(recordStart.getValue()));

                balanceChangeList.add(balanceChange);
            }
        }
        return balanceChangeList;
    }
}
