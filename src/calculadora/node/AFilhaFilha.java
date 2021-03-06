/* This file was generated by SableCC (http://www.sablecc.org/). */

package calculadora.node;

import calculadora.analysis.*;

@SuppressWarnings("nls")
public final class AFilhaFilha extends PFilha
{
    private TFilhaDaClasse _filhaDaClasse_;
    private TClIdentificador _clIdentificador_;

    public AFilhaFilha()
    {
        // Constructor
    }

    public AFilhaFilha(
        @SuppressWarnings("hiding") TFilhaDaClasse _filhaDaClasse_,
        @SuppressWarnings("hiding") TClIdentificador _clIdentificador_)
    {
        // Constructor
        setFilhaDaClasse(_filhaDaClasse_);

        setClIdentificador(_clIdentificador_);

    }

    @Override
    public Object clone()
    {
        return new AFilhaFilha(
            cloneNode(this._filhaDaClasse_),
            cloneNode(this._clIdentificador_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFilhaFilha(this);
    }

    public TFilhaDaClasse getFilhaDaClasse()
    {
        return this._filhaDaClasse_;
    }

    public void setFilhaDaClasse(TFilhaDaClasse node)
    {
        if(this._filhaDaClasse_ != null)
        {
            this._filhaDaClasse_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._filhaDaClasse_ = node;
    }

    public TClIdentificador getClIdentificador()
    {
        return this._clIdentificador_;
    }

    public void setClIdentificador(TClIdentificador node)
    {
        if(this._clIdentificador_ != null)
        {
            this._clIdentificador_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._clIdentificador_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._filhaDaClasse_)
            + toString(this._clIdentificador_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._filhaDaClasse_ == child)
        {
            this._filhaDaClasse_ = null;
            return;
        }

        if(this._clIdentificador_ == child)
        {
            this._clIdentificador_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._filhaDaClasse_ == oldChild)
        {
            setFilhaDaClasse((TFilhaDaClasse) newChild);
            return;
        }

        if(this._clIdentificador_ == oldChild)
        {
            setClIdentificador((TClIdentificador) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
