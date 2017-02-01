
package ejb;
/*
This is the ejb and is the middle man between the data base entity and the backing bean. The ejb 
does some of the processing on it own then make query using entity manager to get data from
the database.
*/

import entities.Location;
import entities.Movie;
import entities.Showing;
import entities.Showtime;
import entities.Theater;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateful
public class MovieTheaterEJB {
    @PersistenceContext(unitName = "MovieTheaterSysPU")
    private EntityManager em;
    
    public void generateMovie(){
        
        List<Movie> movieList = new ArrayList<>();
        
        Movie m1 = new Movie("Star Trek Beyond",
                             "https://i.ytimg.com/vi/1QanXlRNiuY/maxresdefault.jpg",
                             "A mysterious suprise attack in outer space force the Enterprise to crash-land on an unknown world."
        );
        
        Movie m2 = new Movie("The Secret Life of Pets",
                             "https://upload.wikimedia.org/wikipedia/en/6/64/The_Secret_Life_of_Pets_poster.jpg",
                             "Wonder what your pets doing when you're not home? Secret Life of Pets show what your pet is capable of beside looking cute and behave when you're around."
        );
        
        Movie m3 = new Movie("Ice Age",
                             "https://i.jeded.com/i/ice-age.12536.jpg",
                             "Scrat's greed for acone led him to chase an acorn into outerspace where things turn for the worse. Could that be the end for the Ice Age?"
        );
        
        Movie m4 = new Movie("Ghostbusters",
                             "http://www.comingsoon.net/assets/uploads/gallery/ghostbusters-2016/ghostbusters_ver6_xlg.jpg",
                            "Do you believe ghost is real? Physicist in Ghostbusters go on a journey to prove which they do."
        );
        
        Movie m5 = new Movie("Finding Dory",
                             "https://upload.wikimedia.org/wikipedia/en/thumb/3/3e/Finding_Dory.jpg/220px-Finding_Dory.jpg",
                             "Dory is on a journey to find her birth parents once she got separated, however she got lost in the middle of the journey. Will Nemo and Marlin come to her aid on time?"
        );
        
        movieList.add(m1);
        movieList.add(m2);
        movieList.add(m3);
        movieList.add(m4);
        movieList.add(m5);
        
        for (Movie movie : movieList){
            em.persist(movie);
        }
        
        //add location
        List<Location> zipList = new ArrayList<>();
        Location l1 = new Location("75081", "Richardson");
        Location l2 = new Location("75044", "Garland");
        Location l3 = new Location("75024", "Plano");
        
        zipList.add(l1);
        zipList.add(l2);
        zipList.add(l3);
        
        for (Location location : zipList){
            em.persist(location);
        }
        
        //Making Theaters
        List<Theater> theaterList = new ArrayList<>();
        Theater t1 = new Theater("Alamo Drafthouse Cenema", "https://drafthouse.com/assets/img/logo-black-fill.png", zipList.get(0));
        Theater t2 = new Theater("Studio Movie Grill", "https://pbs.twimg.com/profile_images/1192996451/SMG_Twitter_Home_v1.jpg", zipList.get(0));
        Theater t3 = new Theater("AMC", "http://cdn1.thr.com/sites/default/files/2011/04/amc-theatre-logo-2011-a-l.jpg", zipList.get(0));
        
        Theater t4 = new Theater("AMC Firewheel", "http://popcultureblog.dallasnewsblogs.com/files/2012/08/NME_04FIREWHLAMC_3795239-11.jpg", zipList.get(1));
        Theater t5 = new Theater("Walnut Theater", "https://www.walnutstreettheatre.org/images/wst.png", zipList.get(1));
        Theater t6 = new Theater("Garland Cenema Inc", "http://www.cinemark.com/media/427391/38.jpg", zipList.get(1));

        Theater t7 = new Theater("Cinemark Movies 10", "http://www.marketplaceohio.com/mpo_graphic_files/photos/8509/large/cinemark_front.jpg", zipList.get(2));
        Theater t8 = new Theater("Cinemark Legacy & XD", "http://www.cinemark.com/media/442212/251.jpg", zipList.get(2));
        Theater t9 = new Theater("Cinemark West Plano & XD", "http://www.dallasnews.com/incoming/20120822-ng_21bestmovies_06_26905249.jpg.ece/BINARY/w940/NG_21BESTMOVIES_06_26905249.jpg", zipList.get(2));
        
        theaterList.add(t1);
        theaterList.add(t2);
        theaterList.add(t3);
        theaterList.add(t4);
        theaterList.add(t5);
        theaterList.add(t6);
        theaterList.add(t7);
        theaterList.add(t8);
        theaterList.add(t9);
        
        for (Theater theater : theaterList){
            em.persist(theater);
        }
        
        //Making Showing;
        List<Integer> showID = randomID(600000, 300000, (theaterList.size() * movieList.size()));    //generate random ID
        List<Showing> showing = new ArrayList<>();
        int count = 0;
        
        for (int i = 0; i < theaterList.size(); i++){
            for (int j = 0; j < movieList.size(); j++){
                Showing temp = new Showing(
                                        Integer.toString(showID.get(count)),
                                        theaterList.get(i),
                                        movieList.get(j)
                                   );
                
                showing.add(temp);
                count++;
            }
        }
        for (Showing show : showing){
            em.persist(show);

        }
        
        //Making Showtime
        List<Integer>showTimeID = randomID(999999, 700000, movieList.size() * 5);
        List<Showtime> showtime = new ArrayList<>();
        count = 0;
        for (int i = 0; i < movieList.size(); i++){
            List<Integer> time = randomID(22, 10, 3);
            for (int j = 0; j < 3; j++){
                Showtime temp = new Showtime(
                                        Integer.toString(showTimeID.get(count)),
                                        movieList.get(i)
                                    );
                
                if (time.get(j) < 12)
                    temp.setMvtime(Integer.toString(time.get(j)) + "AM");
                else
                    temp.setMvtime(Integer.toString(time.get(j)) + "PM");
                
                showtime.add(temp);
                count++;
            }
        }
        for (Showtime movieTime : showtime){
            em.persist(movieTime);
        }
    }
    
    private static List<Integer> randomID(int max, int min, int size){
        List<Integer> id = new ArrayList();
        Random random = new Random();
        int count = 0;
        int temp;
        do{
            temp = random.nextInt(max - min + 1) + min;
            if (!(id.contains(temp))){
                id.add(temp);
                count++;
            }
            if (count == size)
                break;
        }while (true);
        return id;
    }
    
    public List<Theater> getTheaters(Location zipcode){
        return em.createNamedQuery("Theater.findByZipcode", Theater.class)
                .setParameter("zipcode", zipcode)
                .getResultList();
    }
    
    public Theater getTheater(String theatername){
        return em.createNamedQuery("Theater.findByTheatername", Theater.class)
                .setParameter("theatername", theatername)
                .getSingleResult();
    }
    
    public List<Showing> getTheaterShowing(Theater theater){
        return em.createNamedQuery("Showing.findByTheatername", Showing.class)
                .setParameter("theatername", theater)
                .getResultList();
    }
    
    public List<Showing> getShowingMovies(String theater){
        return em.createNamedQuery("Showing.findByTheatername", Showing.class)
                .setParameter("theatername", theater)
                .getResultList();
    }
    
    public Movie getMovies(String mvName){
        return em.createNamedQuery("Movie.findByMvname", Movie.class)
                .setParameter("mvname", mvName)
                .getSingleResult();
    }
    
    public List<Showtime> getShowtimes(Movie mvname){
        return em.createNamedQuery("Showtime.findBymvname", Showtime.class)
                .setParameter("mvname", mvname)
                .getResultList();
    }
    
    public Location getLocation(String zipcode){
        return em.createNamedQuery("Location.findByZipcode", Location.class)
                .setParameter("zipcode", zipcode)
                .getSingleResult();
    }
}
