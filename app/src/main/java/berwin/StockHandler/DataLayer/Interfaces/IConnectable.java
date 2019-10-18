package berwin.StockHandler.DataLayer.Interfaces;

import berwin.StockHandler.DataLayer.Enums.ServerState;

public interface IConnectable {

    ServerState connectToHUNGARY();

    ServerState connectToSOP();

    boolean disconnectFromHUNGARY();

    boolean disconnectFromSOP();

    ServerState getServerStatus();

}
