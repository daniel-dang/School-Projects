
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "SHOWING")
@NamedQueries({
    @NamedQuery(name = "Showing.findAll", query = "SELECT s FROM Showing s"),
    @NamedQuery(name = "Showing.findByShowingid", query = "SELECT s FROM Showing s WHERE s.showingid = :showingid"),
    @NamedQuery(name = "Showing.findByTheatername", query = "SELECT s FROM Showing s WHERE s.theatername =:theatername")
})

public class Showing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SHOWINGID")
    private String showingid;
    @JoinColumn(name = "MVNAME", referencedColumnName = "MVNAME")
    @ManyToOne
    private Movie mvname;
    @JoinColumn(name = "THEATERNAME", referencedColumnName = "THEATERNAME")
    @ManyToOne
    private Theater theatername;

    public Showing() {
    }

    public Showing(String showingID, Theater theaterName, Movie mvName){
        this.showingid = showingID;
        this.theatername = theaterName;
        this.mvname = mvName;
    }
    
    public Showing(String showingid) {
        this.showingid = showingid;
    }

    public String getShowingid() {
        return showingid;
    }

    public void setShowingid(String showingid) {
        this.showingid = showingid;
    }

    public Movie getMvname() {
        return mvname;
    }

    public void setMvname(Movie mvname) {
        this.mvname = mvname;
    }

    public Theater getTheatername() {
        return theatername;
    }

    public void setTheatername(Theater theatername) {
        this.theatername = theatername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (showingid != null ? showingid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Showing)) {
            return false;
        }
        Showing other = (Showing) object;
        if ((this.showingid == null && other.showingid != null) || (this.showingid != null && !this.showingid.equals(other.showingid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Showing[ showingid=" + showingid + " ]";
    }
    
}
