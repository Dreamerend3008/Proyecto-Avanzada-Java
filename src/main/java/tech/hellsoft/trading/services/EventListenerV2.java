package tech.hellsoft.trading.services;

import tech.hellsoft.trading.dto.server.ErrorMessage;
import tech.hellsoft.trading.dto.server.FillMessage;
import tech.hellsoft.trading.dto.server.LoginOKMessage;
import tech.hellsoft.trading.dto.server.OfferMessage;
import tech.hellsoft.trading.dto.server.TickerMessage;

public interface EventListenerV2 {
    public void onLoginOk(LoginOKMessage msg);
    public void onFill(FillMessage fill);
    public void onTicker(TickerMessage ticker);
    public void onOffer(OfferMessage offer);
    public void onError(ErrorMessage error);
    public void onConnectionLost(Exception e);
}
