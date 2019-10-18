package berwin.StockHandler.Others.MethodParameters;

import berwin.StockHandler.LogicLayer.Interfaces.IParamable;

public class Parameters<T,P> implements IParamable {

    private T elsoParam;
    public void setElsoParam(T elsoParam) { this.elsoParam = elsoParam; }
    public T getElsoParam() { return elsoParam; }

    private P masodikParam;
    public void setMasodikParam(P masodikParam) { this.masodikParam = masodikParam; }
    public P getMasodikParam() { return masodikParam; }

    public Parameters(T elsoParam, P masodikParam) {
        setElsoParam(elsoParam);
        setMasodikParam(masodikParam);
    }

    public Parameters(T elsoParam) {
        setElsoParam(elsoParam);
    }

    public Parameters() {}

}
