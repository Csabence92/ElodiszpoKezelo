package berwin.StockHandler.Others.MethodParameters;

import berwin.StockHandler.LogicLayer.Interfaces.IParamable;

public class Parameter<T> implements IParamable {

    private T elsoParam;
    public void setElsoParam(T elsoParam) { this.elsoParam = elsoParam; }
    public T getElsoParam() { return elsoParam; }

    public Parameter(T elsoParam) {
        setElsoParam(elsoParam);
    }

    public Parameter() {}
}
