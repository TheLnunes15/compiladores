/* This file was generated by SableCC (http://www.sablecc.org/). */

package calculadora.node;

import java.util.*;
import calculadora.analysis.*;

@SuppressWarnings("nls")
public final class ABlocoDelimitExp extends PExp
{
    private final LinkedList<PDec> _esq_ = new LinkedList<PDec>();
    private PExp _dir_;

    public ABlocoDelimitExp()
    {
        // Constructor
    }

    public ABlocoDelimitExp(
        @SuppressWarnings("hiding") List<?> _esq_,
        @SuppressWarnings("hiding") PExp _dir_)
    {
        // Constructor
        setEsq(_esq_);

        setDir(_dir_);

    }

    @Override
    public Object clone()
    {
        return new ABlocoDelimitExp(
            cloneList(this._esq_),
            cloneNode(this._dir_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseABlocoDelimitExp(this);
    }

    public LinkedList<PDec> getEsq()
    {
        return this._esq_;
    }

    public void setEsq(List<?> list)
    {
        for(PDec e : this._esq_)
        {
            e.parent(null);
        }
        this._esq_.clear();

        for(Object obj_e : list)
        {
            PDec e = (PDec) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._esq_.add(e);
        }
    }

    public PExp getDir()
    {
        return this._dir_;
    }

    public void setDir(PExp node)
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
        if(this._esq_.remove(child))
        {
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
        for(ListIterator<PDec> i = this._esq_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PDec) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._dir_ == oldChild)
        {
            setDir((PExp) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
