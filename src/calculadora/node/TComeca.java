/* This file was generated by SableCC (http://www.sablecc.org/). */

package calculadora.node;

import calculadora.analysis.*;

@SuppressWarnings("nls")
public final class TComeca extends Token
{
    public TComeca()
    {
        super.setText("começa");
    }

    public TComeca(int line, int pos)
    {
        super.setText("começa");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TComeca(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTComeca(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TComeca text.");
    }
}
