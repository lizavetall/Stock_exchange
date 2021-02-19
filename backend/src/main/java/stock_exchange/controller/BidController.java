package stock_exchange.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stock_exchange.dto.BidDTO;
import stock_exchange.dto.CreateBidDTO;
import stock_exchange.model.Bid;
import stock_exchange.model.Deal;
import stock_exchange.response.MessageResponse;
import stock_exchange.service.BidService;

import java.util.Map;

@RestController
@RequestMapping("/bid")
public class BidController {
    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/find")
    public ResponseEntity<BidDTO> find(@RequestParam int id) {
        return new ResponseEntity(bidService.find(id), HttpStatus.OK);
    }

    @GetMapping("/find/all")
    public ResponseEntity<Page<BidDTO>> findAll(@RequestParam String issuer,
                                                     @RequestParam int page,
                                                     @RequestParam int size,
                                                     @RequestParam String[] sort) {
        return new ResponseEntity(bidService.findAll(issuer, page, size, sort), HttpStatus.OK);
    }

    @GetMapping("find/brokers-bids")
    public ResponseEntity findBrokersBids(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam String[] sort,
                                          @RequestParam int brokerId) {
        return new ResponseEntity(bidService.findBrokersBids(page, size, sort, brokerId), HttpStatus.OK);
    }

    @GetMapping("find/clients-bids")
    public ResponseEntity findClientBids(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam String[] sort,
                                         @RequestParam int clientId) {
        return new ResponseEntity(bidService.findClientsBids(page, size, sort, clientId), HttpStatus.OK);
    }

    @GetMapping("/find/bids-for-deal")
    public ResponseEntity findBids(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam String[] sort,
                                   @RequestParam int bidId) {
        return new ResponseEntity(bidService.findBids(page, size, sort, bidId), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity createBid(@RequestParam int id,
                                    @RequestBody CreateBidDTO createBid) {
        bidService.create(id, createBid);
        return new ResponseEntity(new MessageResponse(""),HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<MessageResponse> update(@RequestBody BidDTO bid) {

        return new ResponseEntity(bidService.update(bid), HttpStatus.OK);
    }

    @PostMapping("/create-deal/{seller-bid-id}/{buyer-bid-id}/{price}")
    public ResponseEntity<MessageResponse> create(@PathVariable(name = "seller-bid-id") int sellerBidId,
                                                  @PathVariable(name = "buyer-bid-id") int buyerBidId,
                                                  @PathVariable double price) {
        return new ResponseEntity(bidService.createDeal(sellerBidId, buyerBidId, price), HttpStatus.OK);
    }
}
