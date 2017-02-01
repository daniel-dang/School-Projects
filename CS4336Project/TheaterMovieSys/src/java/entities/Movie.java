
package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "MOVIE")
@NamedQueries({
    @NamedQuery(name = "Movie.findAll", query = "SELECT m FROM Movie m"),
    @NamedQuery(name = "Movie.findByMvname", query = "SELECT m FROM Movie m WHERE m.mvname = :mvname"),
    @NamedQuery(name = "Movie.findByMvdesc", query = "SELECT m FROM Movie m WHERE m.mvdesc = :mvdesc")})
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "MVNAME")
    private String mvname;
    @Size(max = 800)
    @Column(name = "MVDESC")
    private String mvdesc;
    @Column(name = "MVURL")
    private String mvurl;
    @OneToMany(mappedBy = "mvname")
    private Collection<Showing> showingCollection;
    @OneToMany(mappedBy = "mvname")
    private Collection<Showtime> showtimeCollection;

    public Movie() {
    }

    public Movie(String mvName, String url, String mvDesc){
        this.mvname = mvName;
        this.mvurl = url;
        this.mvdesc = mvDesc;
    }
    
    public Movie(String mvname) {
        this.mvname = mvname;
    }

    public String getMvurl() {
        return mvurl;
    }

    public void setMvurl(String mvurl) {
        this.mvurl = mvurl;
    }

    public String getMvname() {
        return mvname;
    }

    public void setMvname(String mvname) {
        this.mvname = mvname;
    }

    public String getMvdesc() {
        return mvdesc;
    }

    public void setMvdesc(String mvdesc) {
        this.mvdesc = mvdesc;
    }

    public Collection<Showing> getShowingCollection() {
        return showingCollection;
    }

    public void setShowingCollection(Collection<Showing> showingCollection) {
        this.showingCollection = showingCollection;
    }

    public Collection<Showtime> getShowtimeCollection() {
        return showtimeCollection;
    }

    public void setShowtimeCollection(Collection<Showtime> showtimeCollection) {
        this.showtimeCollection = showtimeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mvname != null ? mvname.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) object;
        if ((this.mvname == null && other.mvname != null) || (this.mvname != null && !this.mvname.equals(other.mvname))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Movie[ mvname=" + mvname + " ]";
    }
    
}
