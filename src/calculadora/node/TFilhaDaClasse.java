/* This file was generated by SableCC (http://www.sablecc.org/). */

package calculadora.node;

import calculadora.analysis.*;

@SuppressWarnings("nls")
public final class TFilhaDaClasse extends Token
{
    public TFilhaDaClasse()
    {
        super.setText("filha da classe");
    }

    public TFilhaDaClasse(int line, int pos)
    {
        super.setText("filha da classe");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TFilhaDaClasse(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTFilhaDaClasse(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TFilhaDaClasse text.");
    }
}
