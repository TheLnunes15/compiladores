/* This file was generated by SableCC (http://www.sablecc.org/). */

package calculadora.node;

import calculadora.analysis.*;

@SuppressWarnings("nls")
public final class AIdAtributoExp extends PExp
{
    private PObject _esq_;
    private PAtributo _dir_;

    public AIdAtributoExp()
    {
        // Constructor
    }

    public AIdAtributoExp(
        @SuppressWarnings("hiding") PObject _esq_,
        @SuppressWarnings("hiding") PAtributo _dir_)
    {
        // Constructor
        setEsq(_esq_);

        setDir(_dir_);

    }

    @Override
    public Object clone()
    {
        return new AIdAtributoExp(
            cloneNode(this._esq_),
            cloneNode(this._dir_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIdAtributoExp(this);
    }

    public PObject getEsq()
    {
        return this._esq_;
    }

    public void setEsq(PObject node)
    {
        if(this._esq_ != null)
        {
            this._esq_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._esq_ = node;
    }

    public PAtributo getDir()
    {
        return this._dir_;
    }

    public void setDir(PAtributo node)
    {
        if(this._dir_ != null)
        {
            this._dir_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._dir_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._esq_)
            + toString(this._dir_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._esq_ == child)
        {
            this._esq_ = null;
            return;
        }

        if(this._dir_ == child)
        {
            this._dir_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._esq_ == oldChild)
        {
            setEsq((PObject) newChild);
            return;
        }

        if(this._dir_ == oldChild)
        {
            setDir((PAtributo) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
