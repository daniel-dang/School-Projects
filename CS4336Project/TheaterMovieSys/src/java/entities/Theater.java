
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "THEATER")
@NamedQueries({
    @NamedQuery(name = "Theater.findAll", query = "SELECT t FROM Theater t"),
    @NamedQuery(name = "Theater.findByTheatername", query = "SELECT t FROM Theater t WHERE t.theatername = :theatername"),
    @NamedQuery(name = "Theater.findByZipcode", query = "SELECT t FROM Theater t WHERE t.zipcode = :zipcode")
})
public class Theater implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "THEATERNAME")
    private String theatername;
    @Column(name = "THEATERURL")
    private String theaterurl;
    @OneToMany(mappedBy = "theatername")
    private Collection<Showing> showingCollection;
    @JoinColumn(name = "ZIPCODE", referencedColumnName = "ZIPCODE")
    @ManyToOne
    private Location zipcode;

    public Theater() {
    }

    public Theater(String theaterName, String url, Location zipcode){
        this.theatername = theaterName;
        this.theaterurl = url;
        this.zipcode = zipcode;
    }
    
    public Theater(String theatername) {
        this.theatername = theatername;
    }

    public String getTheaterurl() {
        return theaterurl;
    }

    public void setTheaterurl(String theaterurl) {
        this.theaterurl = theaterurl;
    }

    public String getTheatername() {
        return theatername;
    }

    public void setTheatername(String theatername) {
        this.theatername = theatername;
    }

    public Collection<Showing> getShowingCollection() {
        return showingCollection;
    }

    public void setShowingCollection(Collection<Showing> showingCollection) {
        this.showingCollection = showingCollection;
    }

    public Location getZipcode() {
        return zipcode;
    }

    public void setZipcode(Location zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (theatername != null ? theatername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Theater)) {
            return false;
        }
        Theater other = (Theater) object;
        if ((this.theatername == null && other.theatername != null) || (this.theatername != null && !this.theatername.equals(other.theatername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Theater[ theatername=" + theatername + " ]";
    }
    
}
