
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
@Table(name = "SHOWTIME")
@NamedQueries({
    @NamedQuery(name = "Showtime.findAll", query = "SELECT s FROM Showtime s"),
    @NamedQuery(name = "Showtime.findByViewid", query = "SELECT s FROM Showtime s WHERE s.viewid = :viewid"),
    @NamedQuery(name = "Showtime.findByMvtime", query = "SELECT s FROM Showtime s WHERE s.mvtime = :mvtime"),
    @NamedQuery(name = "Showtime.findBymvname", query = "SELECT s FROM Showtime s WHERE s.mvname = :mvname")
})
public class Showtime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "VIEWID")
    private String viewid;
    @Size(max = 255)
    @Column(name = "MVTIME")
    private String mvtime;
    @JoinColumn(name = "MVNAME", referencedColumnName = "MVNAME")
    @ManyToOne
    private Movie mvname;

    public Showtime() {
    }
    
    public Showtime(String viewID, Movie mvName){
        this.viewid = viewID;
        this.mvname = mvName;
    }

    public Showtime(String viewid) {
        this.viewid = viewid;
    }

    public String getViewid() {
        return viewid;
    }

    public void setViewid(String viewid) {
        this.viewid = viewid;
    }

    public String getMvtime() {
        return mvtime;
    }

    public void setMvtime(String mvtime) {
        this.mvtime = mvtime;
    }

    public Movie getMvname() {
        return mvname;
    }

    public void setMvname(Movie mvname) {
        this.mvname = mvname;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (viewid != null ? viewid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Showtime)) {
            return false;
        }
        Showtime other = (Showtime) object;
        if ((this.viewid == null && other.viewid != null) || (this.viewid != null && !this.viewid.equals(other.viewid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Showtime[ viewid=" + viewid + " ]";
    }
    
}
