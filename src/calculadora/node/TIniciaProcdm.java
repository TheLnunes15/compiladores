/* This file was generated by SableCC (http://www.sablecc.org/). */

package calculadora.node;

import calculadora.analysis.*;

@SuppressWarnings("nls")
public final class TIniciaProcdm extends Token
{
    public TIniciaProcdm()
    {
        super.setText("=>");
    }

    public TIniciaProcdm(int line, int pos)
    {
        super.setText("=>");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TIniciaProcdm(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTIniciaProcdm(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TIniciaProcdm text.");
    }
}