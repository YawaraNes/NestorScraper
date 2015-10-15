package com.yawaranes.nestorscraper.network.gateway;

public class FakeGateway extends Gateway {

    @Override
    public void getSourceCode(final GatewayResponse.Listener<String> responseListener,
            final GatewayResponse.ErrorListener errorListener) {
        responseListener.onResponse(null);
    }
}
